package application;

import javafx.application.Application;
import application.gui.DisplayManager;
import application.gui.treeview.TaskNormalTreeView;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  private static final int WINDOW_WIDTH = 1920;
  private static final int WINDOW_HEIGHT = 1080;
  private static final String APP_TITLE = "Todo App";

  DisplayManager dm;

  @Override
  public void start(Stage stage) throws Exception {

    stage.setOnCloseRequest(event -> {
    });

    // set the scene
    Scene mainScene = new Scene(createPane(), WINDOW_WIDTH, WINDOW_HEIGHT);
    // mainScene.getStylesheets().add(getClass().getResource("stylesheet.css").toString());
    mainScene.getStylesheets().add("/Resources/ToDoTheme.css");

    // set the stage
    stage.setTitle(APP_TITLE);
    stage.setScene(mainScene);
    stage.show();
  }

  private Pane createPane() {
    dm = new DisplayManager();
    // tl = new TodoList();
    TaskNormalTreeView treeView = new TaskNormalTreeView();

    VBox settings = new VBox();
    settings.getChildren().addAll(dm.getSettings(), treeView.getSettingsPane());

    // Main pane
    BorderPane root = new BorderPane();
    root.setTop(dm.getMenuBar());
    root.setLeft(settings);
    // root.setCenter(tl.getDisplayPane());
    root.setCenter(treeView.getViewPane());
    return root;
  }

  public static void main(String[] args) {
    launch(args);
  }

}
