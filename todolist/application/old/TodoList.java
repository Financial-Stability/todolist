package application.old;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableCell;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.CheckBoxTreeTableCell;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;
import javafx.util.Callback;

// Added features to do
// TODO: Add Date/Time Selectors

// Styling to do
// TODO: Style description input box (make borderless, perhaps wraparound text
// TODO: Get rid of blue highlights around tabs

// Bugs to do
// TODO: Tree refreshes out of order (can be fixed by adding information sorting methods)

// Potential future updates
// Bar Graph which shows how many actions per day under each category (help stay on top of put off topics)
// For web use AJAX? (https://www.w3schools.com/js/js_ajax_intro.asp)
// JSON vs MongoDB (https://stackshare.io/stackups/json-vs-mongodb) : JSON better for beginner projects
// Add selector for which tree to load (data can be switched by saving multiple diffrent .ser files)

public class TodoList {

	private Node displayPane;
	private TextArea textOutput;
	private Node settingsPanel;
	private TabPane TopTabPane = new TabPane();

	List<String> tabTitles = new ArrayList<>(); // Must be initialized with = or null point probable

	// Data Structure
	private Tree TaskData = new Tree("taskdata", "Base Tree object which all information stems from");

	public TodoList() {

//		// Filler Data
//		Tree school = new Tree("school", "");
//		school.addTask(new ToDoObj("Do Homework", "11/14/20", "13:07"));
//		school.addTask(new ToDoObj("Clear Whiteboard", "11/12/20", "14:23"));
//		Tree project1 = new Tree("Project1", "Research and propose an environmental system");
//		project1.addTask(new ToDoObj("Meet group members", "11/2/20", "5:10"));
//		project1.addTask(new ToDoObj("Write proposal", "11/2/20", "5:10"));
//		school.addTree("project1", project1);
//		TaskData.addTree("school", school);
//
//		Tree home = new Tree("home", "Things to be done around the house");
//		home.addTask(new ToDoObj("Do dishes", "fa", "14:23"));
//		home.addTask(new ToDoObj("Feed dog", "ada", "14:23"));
//		home.addTask(new ToDoObj("Wipe counter", "adfa", "14:23"));
//		Tree shootBird = new Tree("Shoot that annoying bird outside my window", "He tweet very loud");
//		shootBird.addTask(new ToDoObj("Triangulate position", "12/5/21", "14:23"));
//		shootBird.addTask(new ToDoObj("Calculate bullet trajectory", "1/1/41", "14:23"));
//		shootBird.addTask(new ToDoObj("Start oven", "1/1/41", "14:23"));
//		home.addTree("Shoot Bird", shootBird);
//		Tree buildDeck = new Tree("Build Deck", "");
//		buildDeck.addTask(new ToDoObj("Buy wood", "12/5/21", "14:23"));
//		buildDeck.addTask(new ToDoObj("Buy nails", "1/1/41", "14:23"));
//		Tree talkToJim = new Tree("Talk to jim", "He knows how to build decks, he's deaf though");
//		talkToJim.addTask(new ToDoObj("Learn to speak sign language", "", ""));
//		talkToJim.addTask(new ToDoObj("Kill Jim.", "", ""));
//		buildDeck.addTree("Talk to Jim", talkToJim);
//		home.addTree("Build Deck", buildDeck);
//		TaskData.addTree("home", home);

//		System.out.println(TaskData.getTree("home").getTitle()); // Typical call to DataStructure

		for (Entry<String, Tree> entry : TaskData.getTreeList().entrySet()) {

			tabTitles.add(entry.getKey());

		}

		// Create display panes
		ScrollPane scrollPane = new ScrollPane(createTabPane());
		scrollPane.fitToWidthProperty().set(true);
		displayPane = scrollPane;
		settingsPanel = createSettings();
	}

	public Node getDisplayPane() {
		return displayPane;
	}

	public void output(String message) {
		textOutput.appendText(message + "\n");
	}

	public Tree getTaskData() {
		return TaskData;
	}

	public void setTaskData(Tree tree) {
		this.TaskData = tree;
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
		Button refreshTree = new Button("Refresh Tree");
		Tooltip toolTip = new Tooltip("(o.o) - you found me!");
		refreshTree.setTooltip(toolTip);
		refreshTree.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				refreshTree(true);
			}
		});
		Button newProject = new Button("New Project");
		newProject.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				createNewProject(doTextInputDialog("New Project", "Project Name: "));
			}
		});

		// apply IDs for styling
		settings.setId("settings_panel");
		settings.getChildren().addAll(settingsTitle, combo_box, refreshTree, newProject);

		return settings;
	}

	private TabPane createTabPane() {

//		TopTab = recurseTabBuilder(TaskData);
//		TopTab.setClosable(false);
//		TopTabPane.getTabs().add(TopTab);

		for (String key : TaskData.getTreeList().keySet()) {
			Tree project = TaskData.getTree(key);
			Tab projectTab = recurseTabBuilder(project);

			projectTab.setOnClosed(new EventHandler<Event>() {
				@Override
				public void handle(Event t) {
					System.out.print("remobe");
					TaskData.removeTree(key);
				}
			});

			TopTabPane.getTabs().add(projectTab);
		}

		return TopTabPane;
	}

	/**
	 * Creates a new project, adds its tab and its tree to their respective places.
	 * 
	 * @param projectName (String) name of new project
	 */
	private void createNewProject(String projectName) {
		if (projectName != "") {
			Tree newProject = new Tree(projectName, "");
			Tab newProjectTab = recurseTabBuilder(newProject);

			TaskData.addTree(projectName, newProject);
			TopTabPane.getTabs().add(newProjectTab);
			TopTabPane.getSelectionModel().select(newProjectTab);
		}
	}

	/**
	 * @param taskList : a list of elements to be displayed ArrayList(String)
	 * @return ListView<TreeView<String>> : an element which can be added to panes
	 */
	private TreeTableView<ToDoObj> createListContent(ArrayList<ToDoObj> taskList) {

		TreeTableView<ToDoObj> treeTableView = new TreeTableView<ToDoObj>();

		TreeTableColumn<ToDoObj, Boolean> col0 = new TreeTableColumn<ToDoObj, Boolean>(" ");
		TreeTableColumn<ToDoObj, String> col1 = new TreeTableColumn<>("Title");
		TreeTableColumn<ToDoObj, String> col2 = new TreeTableColumn<>("Due Date");
		TreeTableColumn<ToDoObj, String> col3 = new TreeTableColumn<>("Time");
		TreeTableColumn<ToDoObj, String> col4 = new TreeTableColumn<>("Description");

		col0.setCellValueFactory(new TreeItemPropertyValueFactory<ToDoObj, Boolean>("completed"));
		col1.setCellValueFactory(new TreeItemPropertyValueFactory<>("title"));
		col2.setCellValueFactory(new TreeItemPropertyValueFactory<>("dueDate"));
		col3.setCellValueFactory(new TreeItemPropertyValueFactory<>("time"));
		col4.setCellValueFactory(new TreeItemPropertyValueFactory<>("description"));

		TreeItem<ToDoObj> taskListRoot = new TreeItem<ToDoObj>(new ToDoObj("Root", "filler", "text", "description"));
		for (ToDoObj task : taskList) {
			TreeItem<ToDoObj> taskItem = new TreeItem<ToDoObj>(task);
			taskListRoot.getChildren().add(taskItem);
		}

		// Super useful resource for editing treeTableCells, also shows how to do
		// drop-down menus
		// https://www.youtube.com/watch?v=BNvVSU9nHDY
		// You can add collumns to collumns :
		// https://o7planning.org/en/11149/javafx-treetableview-tutorial

		col0.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<ToDoObj, Boolean>, //
				ObservableValue<Boolean>>() {

			@Override
			public ObservableValue<Boolean> call(TreeTableColumn.CellDataFeatures<ToDoObj, Boolean> param) {
				TreeItem<ToDoObj> treeItem = param.getValue();
				ToDoObj emp = treeItem.getValue();
				SimpleBooleanProperty booleanProp = new SimpleBooleanProperty(emp.isCompleted());

				// Note: singleCol.setOnEditCommit(): Not work for
				// CheckBoxTreeTableCell.
				// When "Single?" column change.
				booleanProp.addListener(new ChangeListener<Boolean>() {

					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
							Boolean newValue) {
						emp.setCompleted(newValue);
						System.out.println(newValue);
					}
				});
				return booleanProp;
			}
		});

		col0.setCellFactory(new Callback<TreeTableColumn<ToDoObj, Boolean>, TreeTableCell<ToDoObj, Boolean>>() {
			@Override
			public TreeTableCell<ToDoObj, Boolean> call(TreeTableColumn<ToDoObj, Boolean> p) {
				CheckBoxTreeTableCell<ToDoObj, Boolean> cell = new CheckBoxTreeTableCell<ToDoObj, Boolean>();
				cell.setAlignment(Pos.CENTER);
				return cell;
			}
		});

		col1.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col1.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ToDoObj, String>>() {
			@Override
			public void handle(TreeTableColumn.CellEditEvent<ToDoObj, String> event) {
				TreeItem<ToDoObj> editingTask = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
				editingTask.getValue().setTitle(event.getNewValue());
			}
		});
		col2.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col2.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ToDoObj, String>>() {
			@Override
			public void handle(TreeTableColumn.CellEditEvent<ToDoObj, String> event) {
				TreeItem<ToDoObj> editingTask = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
				editingTask.getValue().setDueDate(event.getNewValue());
			}
		});
		col3.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col3.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ToDoObj, String>>() {
			@Override
			public void handle(TreeTableColumn.CellEditEvent<ToDoObj, String> event) {
				TreeItem<ToDoObj> editingTask = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
				editingTask.getValue().setTime(event.getNewValue());
			}
		});
		
		col4.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
		col4.setOnEditCommit(new EventHandler<TreeTableColumn.CellEditEvent<ToDoObj, String>>() {
			@Override
			public void handle(TreeTableColumn.CellEditEvent<ToDoObj, String> event) {
				TreeItem<ToDoObj> editingTask = treeTableView.getTreeItem(event.getTreeTablePosition().getRow());
				editingTask.getValue().setDescription(event.getNewValue());
			}
		});

		treeTableView.getColumns().addAll(col0, col1, col2, col3, col4);
		treeTableView.setShowRoot(false);
		treeTableView.setEditable(true);
		treeTableView.setRoot(taskListRoot);

		return treeTableView;
	}

	/**
	 * 
	 * @param toptree : (Tree) base tree of DataType
	 * @return
	 */
	private Tab recurseTabBuilder(Tree topTree) {

		TabPane tabPane = new TabPane();
		TreeTableView<ToDoObj> taskList = createListContent(topTree.getTaskObjects()); // List content to be added to
																						// new tab
		taskList.setMaxHeight(100);

		// For each tree beneath this one call this function again. Trees with no other
		// trees under them run this loop 0 times
		for (String subTabName : topTree.getTreeList().keySet()) {
			Tab subTab = recurseTabBuilder(topTree.getTree(subTabName));
			tabPane.getTabs().add(subTab);

			subTab.setOnClosed(new EventHandler<Event>() {
				@Override
				public void handle(Event t) {
					topTree.removeTree(subTabName);
				}
			});
		}

		TextField description = new TextField();
		if (topTree.getDescription() == "") {
			description.setText("Description");
		} else {
			description.setText(topTree.getDescription());
		}
		description.textProperty().addListener((observable, oldValue, newValue) -> {
			topTree.setDescription(newValue);
		});
		description.focusedProperty().addListener(new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue ov, Boolean t, Boolean t1) {
		        Platform.runLater(new Runnable() {
		            @Override
		            public void run() {
		                if (description.isFocused() && !description.getText().isEmpty()) {
		                    description.selectAll();
		                }
		            }
		        });
			}
		});
		description.setOnKeyPressed(new EventHandler<KeyEvent>()
	    {
	        @Override
	        public void handle(KeyEvent ke)
	        {
	            if (ke.getCode().equals(KeyCode.ENTER))
	            {
	                tabPane.requestFocus();
	            }
	        }
	    });

		Button newTaskButton = new Button("New Task");
		newTaskButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
//				String result = doTextInputDialogue("New Task", "Name: ");

				Optional dialog = createToDoInput().showAndWait();
				if (dialog.isPresent()) {
					ToDoObj newToDo = (ToDoObj) dialog.get();

					String newToDoName = newToDo.getTitle();
					if (newToDoName.length() > 0) {
						TreeItem newTask = new TreeItem(newToDo);
						taskList.getRoot().getChildren().add(0, newTask);
						;
//					taskList.setPrefHeight(taskList.getRoot().getChildren().size() * 30);
						taskList.setMaxHeight(100);
						topTree.addTask(newToDo); // Updating data structure
					}
				}
			}
		});

		Button deleteTaskButton = new Button("Delete Task");
		deleteTaskButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				TreeItem<ToDoObj> parent;
				TreeItem<ToDoObj> item = taskList.getSelectionModel().getSelectedItem();
				String itemTitle = item.getValue().getTitle();

				parent = item.getParent();
				if (parent == null) { // God tier if statement
					// Do nothing;
				} else {
					parent.getChildren().remove(item);
				}

				topTree.removeTask(itemTitle); // Updating data structure

			}
		});

		HBox buttonContainer = new HBox(newTaskButton, deleteTaskButton);

		// Create a "new tab" button by checking if last tab of tabDisplay is clicked
		// each time tabs are changed through event listener and adding new tab at the
		// index before it
		Button newProjectButton = new Button("Click here to begin adding subprojects to this project");
		newProjectButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				newTabButton(tabPane, topTree, true);
			}
		});

		Tab newTabTab = new Tab("+", newProjectButton);
		newTabTab.setClosable(false);
		tabPane.getTabs().add(newTabTab);
		tabPane.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Tab>() {
			@Override
			public void changed(ObservableValue<? extends Tab> ov, Tab t, Tab t1) { // When tab selection changes,
				if (tabPane.getSelectionModel().getSelectedIndex() == tabPane.getTabs().size() - 1) {
					newTabButton(tabPane, topTree, false);
				}
			}
		});

		// Separates elements under each tab using VBox and creates the new, single tab
		// to return
		VBox listTabContainer = new VBox(description, buttonContainer, taskList, tabPane);
		Tab newTab = new Tab(topTree.getTitle(), listTabContainer);

		return newTab;
	}

	private void newTabButton(TabPane tabPane, Tree topTree, Boolean newProjectButton) {
		if (tabPane.getTabs().size() > 1 || newProjectButton) {
			String result = doTextInputDialog("New Project:", "Title: ");
			if (result != "") {
				Tree newTree = new Tree(result, "");
				topTree.addTree(result, newTree);
				Tab createdTab = recurseTabBuilder(newTree);
				tabPane.getTabs().add(tabPane.getTabs().size() - 1, createdTab);
			}
			tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
		}
		refreshTree(true);
	}

	/**
	 * A normal text input pop-up
	 * 
	 * @param header  : appears in center of window
	 * @param content : appears beside input box
	 */
	private String doTextInputDialog(String header, String content) {
		TextInputDialog dialog = new TextInputDialog("");
//		dialog.setTitle("New project"); // Title removed when StageStyle.UNDECORATED
		dialog.setHeaderText(header);
		dialog.setContentText(content);
		dialog.initStyle(StageStyle.UNDECORATED); // THIS GETS RID OF BORDERS AROUND WINDOWS!!!!!!!!!!
		Optional<String> result = dialog.showAndWait(); // Get response value from dialogue
		if (result.isPresent() && !result.isEmpty() && result.get().length() > 0) {
			return result.get();
		} else {
			return "";
		}
	}

	/**
	 * Creates a dialog which returns a ToDoObj on close. Edit arrays to change
	 * default display values
	 * 
	 * @return Dialog object
	 */
	private Dialog createToDoInput() {
		ArrayList<String> labels = new ArrayList<String>() {
			{ // Makes a label element for each string in here
				add("Task Name");
				add("Due Date");
				add("Time");
				add("Description");
			}
		};

		ArrayList<TextField> elements = new ArrayList<TextField>() {
			{
				add(new TextField());
				add(new TextField());
				add(new TextField());
				add(new TextField());
			}
		};

		Dialog<ToDoObj> dialog = new Dialog<ToDoObj>();
		GridPane gridPane = new GridPane();

		for (int i = 0; i < labels.size(); i++) { // Adding elements to gridPane
			Label label = new Label(labels.get(i));
			TextField element = elements.get(i);
			gridPane.add(label, i, 1);
			gridPane.add(element, i, 2);
		}

		ButtonType buttonTypeOk = new ButtonType("Ok", ButtonData.OK_DONE);
		ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonTypeOk, buttonTypeCancel);

		dialog.getDialogPane().setContent(gridPane);
		dialog.setResultConverter(new Callback<ButtonType, ToDoObj>() {
			@Override
			public ToDoObj call(ButtonType b) {

				if (b == buttonTypeOk) {
					return new ToDoObj(elements.get(0).getText(), elements.get(1).getText(), elements.get(2).getText(), elements.get(3).getText());
				}

				return null;
			}
		});

		return dialog;
	}

	/**
	 * Reloads the tree from database
	 * 
	 * @param toTab : When refreshing the tree, it will default to the first element
	 *              of each tab, by setting boolean to true, preserve user's spot in
	 *              the hierarchy
	 */
	public void refreshTree(Boolean toTab) {

		List<Integer> tabPath = currentTabPath(TopTabPane); // This should ideally be only if toTab but scope problems
//		if (tabPath.size() < 2) {
//			tabPath = new ArrayList<Integer>();
//		}

		TopTabPane.getTabs().clear();
		for (String key : TaskData.getTreeList().keySet()) {
			Tree project = TaskData.getTree(key);
			Tab projectTab = recurseTabBuilder(project);
			projectTab.setOnClosed(new EventHandler<Event>() {
				@Override
				public void handle(Event t) {
					TaskData.removeTree(key);
				}
			});
			TopTabPane.getTabs().add(projectTab);
		}

		if (toTab && tabPath.get(0) != -1) { // -1 means nothing there
			TabPane tp = TopTabPane;
			for (int i : tabPath) {
				tp.getSelectionModel().select(i);
//				System.out.println(tabPath);
				tp = (TabPane) tp.getSelectionModel().getSelectedItem().getContent().lookup(".tab-pane");
			}
		}
//		TopTab = TopTabPane.getTabs().get(0);
//		TopTab.setClosable(false);
	}

	private List<Integer> currentTabPath(TabPane rootPane) {

		List<Integer> tabPath = new ArrayList<Integer>();

		// Get selected index
		tabPath.add(rootPane.getSelectionModel().getSelectedIndex());

		try { // Returns null point when tab pane doesn't exist (typically when starting
				// application)
				// Get next object
			TabPane nextTabPane = (TabPane) rootPane.getSelectionModel().getSelectedItem().getContent()
					.lookup(".tab-pane");
			if (nextTabPane.getTabs().size() > 1) { // If next pane isn't the bottom of the tree,
				tabPath.addAll(currentTabPath(nextTabPane));
			}
		} catch (NullPointerException ex) {
			System.out.print(ex);
		}
		System.out.println(tabPath);

		return tabPath;
	}

}
