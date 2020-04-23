package application;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Main extends Application {

  private static final int WINDOW_WIDTH = 1920;
  private static final int WINDOW_HEIGHT = 1080;
  private static final String APP_TITLE = "Todo App";

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
	// background image
	  Image background = new Image("/anthony-menecola-2DPtxJwv3BQ-unsplash.JPEG", true);
	  ImageView iv1 = new ImageView();
	  iv1.setImage(background);
	  
    // components

    String letters[] = {"A", "B", "C", "D", "E"};
    ComboBox<String> combo_box = new ComboBox<String>(FXCollections.observableArrayList(letters));
    Label settingsTitle = new Label("Settings");
    MenuBar menuBar = new MenuBar();
    VBox settings = new VBox();

    // apply ids for styling
    settings.setId("settings_panel");

    // do menu stuff
    final Menu menu = new Menu("Menu");
    final MenuItem toggle = new MenuItem("Toggle Settings");
    final MenuItem view1 = new MenuItem("view1");
    final MenuItem view2 = new MenuItem("view2");
    final MenuItem view3 = new MenuItem("view3");
    final Menu help = new Menu("Help");

    menu.getItems().addAll(toggle, view1, view2, view3);
    menuBar.getMenus().addAll(menu, help);

    settings.getChildren().addAll(settingsTitle, combo_box);

    // pane
    BorderPane root = new BorderPane();

    root.setTop(menuBar);
    root.setLeft(settings);
    root.setCenter(createDisplayPane());

    return root;
  }

  private Node createDisplayPane() {

    VBox pane = new VBox();
    TextField taskName = new TextField("Task Name");
    HBox buttons = new HBox();
    Button addTask = new Button("Add");
    Button clearAll = new Button("Clear All");
    Button clearChecked = new Button("Clear Checked");
    buttons.getChildren().addAll(addTask, clearAll, clearChecked);
    pane.getChildren().addAll(taskName, buttons);

    addTask.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        pane.getChildren().add(new CheckBox(taskName.getText()));
      }
    });

    clearAll.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        final List<Node> removeUs = new ArrayList<>();
        for (Node child : pane.getChildren()) {
          if (child instanceof CheckBox) {
            removeUs.add(child);
          }
        }
        pane.getChildren().removeAll(removeUs);
      }
    });

    clearChecked.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        final List<Node> removeUs = new ArrayList<>();
        for (Node child : pane.getChildren()) {
          if (child instanceof CheckBox) {
            if (((CheckBox) child).isSelected()) {
              removeUs.add(child);
            }
          }
        }
        pane.getChildren().removeAll(removeUs);
      }
    });

    return pane;

  }

  public static void main(String[] args) {
    launch(args);
  }

}
