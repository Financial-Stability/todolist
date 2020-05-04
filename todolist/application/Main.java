package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

  private static final int WINDOW_WIDTH = 1920;
  private static final int WINDOW_HEIGHT = 1080;
  private static final String APP_TITLE = "Todo App";

  @Override
  public void start(Stage stage) throws Exception {

    // set the scene
    Scene mainScene = new Scene(createPane(), WINDOW_WIDTH, WINDOW_HEIGHT);
//    mainScene.getStylesheets().add(getClass().getResource("stylesheet.css").toString());
    mainScene.getStylesheets().add("/Resources/ToDoTheme.css");

    // set the stage
    stage.setTitle(APP_TITLE);
    stage.setScene(mainScene);
    stage.show();
  }

  private Pane createPane() {
    DisplayManager dm = new DisplayManager();
    TodoList tl = new TodoList();
    
    VBox settings = new VBox();
    settings.getChildren().addAll(dm.getSettings(), tl.getSettings());

    // Main pane
    BorderPane root = new BorderPane();
    root.setTop(dm.getMenuBar());
    root.setLeft(settings);
    root.setCenter(tl.getDisplayPane());
    return root;
  }

  public static void main(String[] args) {
    launch(args);
  }

}
