package application;

import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TodoList {

  private Node displayPane;
  private TreeView<String> treeView;
  private TextArea textOutput;

  public TodoList() {
    displayPane = createDisplayPane();
  }

  public Node getDisplayPane() {
    return displayPane;
  }

  public void output(String message) {
    textOutput.appendText(message + "\n");
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
    treeView.setCellFactory(CheckBoxTreeCell.<String>forTreeView());

    return treeView;
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
