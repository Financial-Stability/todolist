package application;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;

public class DisplayManager {

  private MenuBar menuBar;
  private Node settingsPanel;
  private Boolean settingsVisible;

  public DisplayManager() {
    menuBar = createMenuBar();
    settingsPanel = createSettings();
    settingsVisible = true;
  }

  /**
   * @return the menuBar
   */
  public MenuBar getMenuBar() {
    return menuBar;
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

    toggle.setOnAction(new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent arg0) {
        if (settingsVisible) {
          settingsPanel.setVisible(false);
          settingsVisible = false;
        } else {
          settingsPanel.setVisible(true);
          settingsVisible = true;
        }
      }
    });

    return menuBar;
  }

}
