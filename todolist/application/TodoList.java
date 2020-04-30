package application;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import application.DataType.ToDoObj;
import application.DataType.Tree;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.VBox;

// TODO: Underneath current tab's list view, only display one set of tasks from parent category (possibly add a scroll)
// TODO: Add pop-up to add information when creating a new tab
// TODO: Edit tasks which are already in lists
// TODO: Add methods to add/remove tasks in lists
// TODO: Edit descriptions under Trees

public class TodoList {

	private Node displayPane;
	private TextArea textOutput;
	private Node settingsPanel;

	List<String> tabTitles = new ArrayList<>(); // Must be initialized with = or null point probable

	// Data Structure
	private Tree TaskData = new Tree("taskdata", "Base Tree object which all information stems from");

	public TodoList() {

		// Filler Data
		Tree school = new Tree("school", "");
		school.addTask(new ToDoObj("Do Homework", "11/14/20", "13:07"));
		school.addTask(new ToDoObj("Clear Whiteboard", "11/12/20", "14:23"));
		Tree project1 = new Tree("Project1", "Research and propose an environmental system");
		project1.addTask(new ToDoObj("Meet group members", "11/2/20", "5:10"));
		project1.addTask(new ToDoObj("Write proposal", "11/2/20", "5:10"));
		school.addTree("project1", project1);
		TaskData.addTree("school", school);

		Tree home = new Tree("home", "Things to be done around the house");
		home.addTask(new ToDoObj("Do dishes", "fa", "14:23"));
		home.addTask(new ToDoObj("Feed dog", "ada", "14:23"));
		home.addTask(new ToDoObj("Wipe counter", "adfa", "14:23"));
		Tree shootBird = new Tree("Shoot that annoying bird outside my window", "He tweet very loud");
		shootBird.addTask(new ToDoObj("Triangulate position", "12/5/21", "14:23"));
		shootBird.addTask(new ToDoObj("Calculate bullet trajectory", "1/1/41", "14:23"));
		shootBird.addTask(new ToDoObj("Eat pork", "1/1/41", "14:23"));
		home.addTree("Shoot Bird", shootBird);
		Tree buildDeck = new Tree("Build Deck", "");
		buildDeck.addTask(new ToDoObj("Buy wood", "12/5/21", "14:23"));
		buildDeck.addTask(new ToDoObj("Buy nails", "1/1/41", "14:23"));
		Tree talkToJim = new Tree("Talk to jim", "He knows how to build decks, he's deaf though");
		talkToJim.addTask(new ToDoObj("Learn to speak sign language", "", ""));
		talkToJim.addTask(new ToDoObj("Kill Jim.", "", ""));
		buildDeck.addTree("Talk to Jim", talkToJim);
		home.addTree("Build Deck", buildDeck);
		TaskData.addTree("home", home);

		System.out.println(TaskData.getTree("home").getTitle());

		for (Entry<String, Tree> entry : TaskData.getTreeList().entrySet()) {

			tabTitles.add(entry.getKey());

		}

		// Create display panes
		displayPane = createTabPane();
		settingsPanel = createSettings();
	}

	public Node getDisplayPane() {
		return displayPane;
	}

	public void output(String message) {
		textOutput.appendText(message + "\n");
	}

	/**
	 * @return the settings
	 */
	public Node getSettings() {
		return settingsPanel;
	}

	private Node createSettings() {
		VBox settings = new VBox();
		// components
		String letters[] = { "A", "B", "C", "D", "E" };
		ComboBox<String> combo_box = new ComboBox<String>(FXCollections.observableArrayList(letters));
		Label settingsTitle = new Label("Settings");

		// apply IDs for styling
		settings.setId("settings_panel");
		settings.getChildren().addAll(settingsTitle, combo_box);

		return settings;
	}

	private TabPane createTabPane() {

		TabPane tabPane = new TabPane();

		// For every head topic uses recursive loop to build tab layout
		for (String topTree : TaskData.getTreeList().keySet()) {
			System.out.println(topTree);
			Tab tab = recurseTabBuilder(TaskData.getTree(topTree));
			tabPane.getTabs().add(tab);
		}

		return tabPane;
	}

	private Tab recurseTabBuilder(Tree toptree) {

		TabPane tabPane = new TabPane();
		TreeView<String> taskList = createListView(toptree.getTaskList()); // Create list content to be added to new tab

		// For each tree beneath this one call this function again. Trees with no other
		// trees under them run this loop 0 times
		for (String subTabName : toptree.getTreeList().keySet()) {
			Tab subTab = recurseTabBuilder(toptree.getTree(subTabName));
			tabPane.getTabs().add(subTab);
		}

		// Create new tab button by checking if last tab of tabDisplay is clicked each
		// time tabs are changed through event listener and adding new tab at the index
		// before it
		if (tabPane.getTabs().size() > 0) {
			Tab tibTab = new Tab();
			tabPane.getTabs().add(tibTab);
			tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
				@Override
				public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) {
					if (tabPane.getSelectionModel().getSelectedIndex() == tabPane.getTabs().size() - 1) {
						Tab tab = new Tab("NEW TAB✂🔓🎁🧨🎈");
						tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
						tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
					}
				}
			});
		}

		// Separates elements under each tab using VBox and creates the new, single tab
		// to return
//		VBox listTabContainer = new VBox(new Label(toptree.getDescription()), new Button("Add SubCategories"), tabPane, taskList);
		VBox listTabContainer = new VBox(new Label(toptree.getDescription()), tabPane, taskList);
		Tab newTab = new Tab(toptree.getTitle(), listTabContainer);

		return newTab;
	}

	/**
	 * @param TaskList<String> : a list of elements to be displayed
	 * @return ListView<TreeView<String>> : an element which can be added to panes
	 */
	private TreeView<String> createListView(ArrayList<String> taskList) {

		// Create a root to act as TreeView's base
		CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>();
		root.setExpanded(true);
		for (String task : taskList) {
			root.getChildren().add(new CheckBoxTreeItem<String>(task));
		}

		// TreeView, not showing base root
		final TreeView<String> listView = new TreeView<String>();
		listView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
		listView.setRoot(root);
		listView.setShowRoot(false);

		return listView;
	}

}

//  private TreeView<String> createTree() {
//    // create the tree model
//    List<CheckBoxTreeItem<String>> subtasks = new ArrayList<>();
//
//    for (int i = 0; i < 5; i++) {
//      subtasks.add(new CheckBoxTreeItem<String>("Subtask " + i));
//    }
//    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>();
//    root.setExpanded(true);
//    root.getChildren().addAll(subtasks);
//
//    // create the treeView
//    final TreeView<String> treeView = new TreeView<String>();
//    treeView.setRoot(root);
//    treeView.setShowRoot(false);
//
//    // set the cell factory
////    treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
//    
//    
//    // Uses https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm#BABEJCHA as refrence
//    // for a new cell factory
//    treeView.setEditable(true);
//    treeView.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
//        @Override
//        public TreeCell<String> call(TreeView<String> p) {
//            return new TextFieldTreeCellImpl();
//        }
//    });
//
//    return treeView;
//  }

//  private final class TextFieldTreeCellImpl extends TreeCell<String> {
// 	 
//      private TextField textField;
//
//      public TextFieldTreeCellImpl() {
//      }
//
//      @Override
//      public void startEdit() {
//          super.startEdit();
//
//          if (textField == null) {
//              createTextField();
//          }
//          setText(null);
//          setGraphic(textField);
//          textField.selectAll();
//      }
//
//      @Override
//      public void cancelEdit() {
//          super.cancelEdit();
//          setText((String) getItem());
//          setGraphic(getTreeItem().getGraphic());
//      }
//
//      @Override
//      public void updateItem(String item, boolean empty) {
//          super.updateItem(item, empty);
//
//          if (empty) {
//              setText(null);
//              setGraphic(null);
//          } else {
//              if (isEditing()) {
//                  if (textField != null) {
//                      textField.setText(getString());
//                  }
//                  setText(null);
//                  setGraphic(textField);
//              } else {
//                  setText(getString());
//                  setGraphic(getTreeItem().getGraphic());
//              }
//          }
//      }
//
//      private void createTextField() {
//          textField = new TextField(getString());
//          textField.setOnKeyReleased(new EventHandler<KeyEvent>() {
//
//              @Override
//              public void handle(KeyEvent t) {
//                  if (t.getCode() == KeyCode.ENTER) {
//                      commitEdit(textField.getText());
//                  } else if (t.getCode() == KeyCode.ESCAPE) {
//                      cancelEdit();
//                  }
//              }
//          });
//      }
//
//      private String getString() {
//          return getItem() == null ? "" : getItem().toString();
//      }
//  }

//  private Node createDisplayPane() {
//
//    treeView = createTree();
//	  
//
//    VBox pane = new VBox();
//    TextField taskName = new TextField("Task Name");
//    HBox buttons = new HBox();
//    Button addTaskBtn = new Button("Add Task");
//    Button removeTaskBtn = new Button("Remove Selected Task");
//    Button clearAllBtn = new Button("Clear All");
//    Button clearCheckedBtn = new Button("Clear Checked");
//    textOutput = new TextArea();
//    textOutput.setEditable(false);
//
//    buttons.getChildren().addAll(addTaskBtn, removeTaskBtn, clearCheckedBtn, clearAllBtn);
//    pane.getChildren().addAll(taskName, buttons, treeView, textOutput);
//
//    Image bgImg = new Image("file:backgroundPattern.JPEG", true);
//    BackgroundImage background = new BackgroundImage(bgImg, BackgroundRepeat.REPEAT,
//        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
//    pane.setBackground(new Background(background));
//
//    addTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        addTask(taskName.getText());
//      }
//    });
//
//    removeTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        removeTask();
//      }
//    });
//
//    clearAllBtn.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        List<TreeItem<String>> removalList = new ArrayList<>();
//        for (TreeItem<String> item : treeView.getRoot().getChildren()) {
//          removalList.add(item);
//        }
//        treeView.getRoot().getChildren().removeAll(removalList);
//      }
//    });
//
//    clearCheckedBtn.setOnAction(new EventHandler<ActionEvent>() {
//      @Override
//      public void handle(ActionEvent event) {
//        List<TreeItem<String>> removalList = new ArrayList<>();
//        for (TreeItem<String> item : treeView.getRoot().getChildren()) {
//          if (((CheckBoxTreeItem<String>) item).isSelected()) {
//            removalList.add(item);
//          }
//          recursiveClearCheckedHelper(item);
//        }
//        treeView.getRoot().getChildren().removeAll(removalList);
//      }
//    });
//
//    return pane;
//  }

//  private void recursiveClearCheckedHelper(TreeItem<String> node) {
//    List<TreeItem<String>> removalList = new ArrayList<>();
//    for (TreeItem<String> item : node.getChildren()) {
//      if (((CheckBoxTreeItem<String>) item).isSelected()) {
//        removalList.add(item);
//      }
//      recursiveClearCheckedHelper(item);
//    }
//    node.getChildren().removeAll(removalList);
//  }
//
//  private void addTask(String value) {
//    TreeItem<String> parent = treeView.getSelectionModel().getSelectedItem();
//    CheckBoxTreeItem<String> newTask = new CheckBoxTreeItem<String>(value);
//    if (parent == null || treeView.getTreeItemLevel(parent) == 0) {
//      parent = treeView.getRoot();
//    }
//    parent.getChildren().add(newTask);
//    if (!parent.isExpanded()) {
//      parent.setExpanded(true);
//    }
//  }
//
//  private void removeTask() {
//    TreeItem<String> parent;
//    TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
//    if (item != null) {
//      parent = item.getParent();
//    } else {
//      parent = null;
//      // no item selected to be removed
//    }
//    if (parent == null) {
//      // System.out.println("Cannot remove the root node.");
//    } else {
//      parent.getChildren().remove(item);
//    }
//  }
//
//}
