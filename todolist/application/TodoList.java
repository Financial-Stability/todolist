package application;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.util.Callback;

public class TodoList {

  private Node displayPane;
  private TreeView<String> treeView;
  private TextArea textOutput;
  private Node settingsPanel;

  public TodoList() {
    displayPane = createDisplayPane();
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
    String letters[] = {"A", "B", "C", "D", "E"};
    ComboBox<String> combo_box = new ComboBox<String>(FXCollections.observableArrayList(letters));
    Label settingsTitle = new Label("Settings");

    // apply ids for styling
    settings.setId("settings_panel");
    settings.getChildren().addAll(settingsTitle, combo_box);

    return settings;
  }

  private TreeView<String> createTree() {
    // create the tree model
    List<CheckBoxTreeItem<String>> subtasks = new ArrayList<>();

    for (int i = 0; i < 5; i++) {
      subtasks.add(new CheckBoxTreeItem<String>("Subtask " + i));
    }
    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>();
    root.setExpanded(true);
    root.getChildren().addAll(subtasks);

    // create the treeView
    final TreeView<String> treeView = new TreeView<String>();
    treeView.setRoot(root);
    treeView.setShowRoot(false);

    // set the cell factory
//    treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
    
    
    // Uses https://docs.oracle.com/javafx/2/ui_controls/tree-view.htm#BABEJCHA as refrence
    // for a new cell factory
    treeView.setEditable(true);
    treeView.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
        @Override
        public TreeCell<String> call(TreeView<String> p) {
            return new TextFieldTreeCellImpl();
        }
    });

    return treeView;
  }
  
  private final class TextFieldTreeCellImpl extends TreeCell<String> {
 	 
      private TextField textField;

      public TextFieldTreeCellImpl() {
      }

      @Override
      public void startEdit() {
          super.startEdit();

          if (textField == null) {
              createTextField();
          }
          setText(null);
          setGraphic(textField);
          textField.selectAll();
      }

      @Override
      public void cancelEdit() {
          super.cancelEdit();
          setText((String) getItem());
          setGraphic(getTreeItem().getGraphic());
      }

      @Override
      public void updateItem(String item, boolean empty) {
          super.updateItem(item, empty);

          if (empty) {
              setText(null);
              setGraphic(null);
          } else {
              if (isEditing()) {
                  if (textField != null) {
                      textField.setText(getString());
                  }
                  setText(null);
                  setGraphic(textField);
              } else {
                  setText(getString());
                  setGraphic(getTreeItem().getGraphic());
              }
          }
      }

      private void createTextField() {
          textField = new TextField(getString());
          textField.setOnKeyReleased(new EventHandler<KeyEvent>() {

              @Override
              public void handle(KeyEvent t) {
                  if (t.getCode() == KeyCode.ENTER) {
                      commitEdit(textField.getText());
                  } else if (t.getCode() == KeyCode.ESCAPE) {
                      cancelEdit();
                  }
              }
          });
      }

      private String getString() {
          return getItem() == null ? "" : getItem().toString();
      }
  }

  private Node createDisplayPane() {

    treeView = createTree();

    VBox pane = new VBox();
    TextField taskName = new TextField("Task Name");
    HBox buttons = new HBox();
    Button addTaskBtn = new Button("Add Task");
    Button removeTaskBtn = new Button("Remove Selected Task");
    Button clearAllBtn = new Button("Clear All");
    Button clearCheckedBtn = new Button("Clear Checked");
    textOutput = new TextArea();
    textOutput.setEditable(false);

    buttons.getChildren().addAll(addTaskBtn, removeTaskBtn, clearCheckedBtn, clearAllBtn);
    pane.getChildren().addAll(taskName, buttons, treeView, textOutput);

    Image bgImg = new Image("file:backgroundPattern.JPEG", true);
    BackgroundImage background = new BackgroundImage(bgImg, BackgroundRepeat.REPEAT,
        BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, BackgroundSize.DEFAULT);
    pane.setBackground(new Background(background));

    addTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        addTask(taskName.getText());
      }
    });

    removeTaskBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        removeTask();
      }
    });

    clearAllBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        List<TreeItem<String>> removalList = new ArrayList<>();
        for (TreeItem<String> item : treeView.getRoot().getChildren()) {
          removalList.add(item);
        }
        treeView.getRoot().getChildren().removeAll(removalList);
      }
    });

    clearCheckedBtn.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent event) {
        List<TreeItem<String>> removalList = new ArrayList<>();
        for (TreeItem<String> item : treeView.getRoot().getChildren()) {
          if (((CheckBoxTreeItem<String>) item).isSelected()) {
            removalList.add(item);
          }
          recursiveClearCheckedHelper(item);
        }
        treeView.getRoot().getChildren().removeAll(removalList);
      }
    });

    return pane;
  }

  private void recursiveClearCheckedHelper(TreeItem<String> node) {
    List<TreeItem<String>> removalList = new ArrayList<>();
    for (TreeItem<String> item : node.getChildren()) {
      if (((CheckBoxTreeItem<String>) item).isSelected()) {
        removalList.add(item);
      }
      recursiveClearCheckedHelper(item);
    }
    node.getChildren().removeAll(removalList);
  }

  private void addTask(String value) {
    TreeItem<String> parent = treeView.getSelectionModel().getSelectedItem();
    CheckBoxTreeItem<String> newTask = new CheckBoxTreeItem<String>(value);
    if (parent == null || treeView.getTreeItemLevel(parent) == 0) {
      parent = treeView.getRoot();
    }
    parent.getChildren().add(newTask);
    if (!parent.isExpanded()) {
      parent.setExpanded(true);
    }
  }

  private void removeTask() {
    TreeItem<String> parent;
    TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
    if (item != null) {
      parent = item.getParent();
    } else {
      parent = null;
      // no item selected to be removed
    }
    if (parent == null) {
      // System.out.println("Cannot remove the root node.");
    } else {
      parent.getChildren().remove(item);
    }
  }

}
