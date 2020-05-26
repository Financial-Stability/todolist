package application.gui;

import javafx.scene.Node;

public interface View {

  public Node getSettingsPane();

  public Node getViewPane();

  public void refresh();

}
