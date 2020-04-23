package application;

import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GUIManager extends Application {

  private List<String> args;
  private static final int WINDOW_WIDTH = 500;
  private static final int WINDOW_HEIGHT = 500;
  private static final String APP_TITLE = "Todo App";

  @Override
  public void start(Stage stage) throws Exception {
	  
    args = this.getParameters().getRaw();
    
    int x;
    x = 10;
    
    Label bottomLabel;
    bottomLabel = new Label("a label");

    // components
    Label topLabel = new Label(APP_TITLE);
    String letters[] = {"A", "B", "C", "D", "E"};
    ComboBox<String> combo_box = new ComboBox<String>(FXCollections.observableArrayList(letters));
    Button button = new Button("Done");

    // pane
    BorderPane root = new BorderPane();

    root.setTop(topLabel);
    root.setLeft(combo_box);
    root.setBottom(button);

    // scene
    Scene mainScene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
    
    stage.setTitle("JavaFX Application");
    stage.setScene(mainScene);
    stage.show();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
