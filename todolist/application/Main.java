package application;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TreeView.EditEvent;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.control.cell.TextFieldTreeCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  private static final int WINDOW_WIDTH = 1920;
  private static final int WINDOW_HEIGHT = 1080;
  private static final String APP_TITLE = "Todo App";
  private TreeView<String> treeView; // TODO this really shouldn't be here

  @Override
  public void start(Stage stage) throws Exception {

    // scene
    Scene mainScene = new Scene(createPane(), WINDOW_WIDTH, WINDOW_HEIGHT);

    mainScene.getStylesheets().add(getClass().getResource("stylesheet.css").toString());

    stage.setTitle(APP_TITLE);
    stage.setScene(mainScene);
    stage.show();
  }

  private Pane createPane() {
    // Main pane
    BorderPane root = new BorderPane();

    root.setTop(createMenuBar());
    root.setLeft(createSettings());
    root.setCenter(createDisplayPane());

    return root;
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

  private MenuBar createMenuBar() {
    MenuBar menuBar = new MenuBar();
    // do menu stuff
    final Menu menu = new Menu("Menu");
    final MenuItem toggle = new MenuItem("Toggle Settings");
    final MenuItem view1 = new MenuItem("view1");
    final MenuItem view2 = new MenuItem("view2");
    final MenuItem view3 = new MenuItem("view3");
    final Menu help = new Menu("Help");

    menu.getItems().addAll(toggle, view1, view2, view3);
    menuBar.getMenus().addAll(menu, help);

    return menuBar;
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
    buttons.getChildren().addAll(addTaskBtn, removeTaskBtn, clearAllBtn);
    pane.getChildren().addAll(taskName, buttons, treeView);

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
        List<TreeItem<String>> removeUs = new ArrayList<>();
        for (TreeItem<String> item : treeView.getRoot().getChildren()) {
          removeUs.add(item);
        }
        treeView.getRoot().getChildren().removeAll(removeUs);
      }
    });

    return pane;
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

    TreeItem<String> item = treeView.getSelectionModel().getSelectedItem();
    TreeItem<String> parent = item.getParent();
    if (parent == null) {
      System.out.println("Cannot remove the root node.");
    } else {
      parent.getChildren().remove(item);
    }

  }

  public static void main(String[] args) {
    launch(args);
  }

}
