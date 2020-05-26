package application;

import java.util.ArrayList;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class TaskNormalTreeView implements View {

  private TaskTree tree;

  TaskNormalTreeView() {
    tree = TaskTree.makeExampleTree();
  }

  @Override
  public Node getSettingsPane() {
    return new VBox();
  }

  @Override
  public Node getViewPane() {
    // StackPane view = new StackPane();
    VBox view = new VBox();

    TreeView<TaskNode> tree = generateTreeView();
    tree.setShowRoot(false);
    tree.setCellFactory(new TaskNodeCellFactory());
    tree.setMaxWidth(Region.USE_PREF_SIZE);
    // tree.setMaxHeight(Region.USE_PREF_SIZE);

    // tree.prefHeightProperty().bind(tree.impl_treeItemCountProperty()
    // .multiply(treeRowHeight));
    tree.setFixedCellSize(24);
    // tree.prefHeightProperty()
    // .bind(tree.expandedItemCountProperty().multiply(tree.getFixedCellSize()).add(1));
    tree.expandedItemCountProperty().addListener(event -> {
      System.out.println("change");
      tree.setPrefHeight(tree.getExpandedItemCount() * tree.getFixedCellSize() + 2);
      tree.autosize();
    });
    // tree.expandedProperty().addListener(event -> {
    // @Override
    // public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean
    // newValue) {
    // System.out.println("newValue = " + newValue);
    // BooleanProperty bb = (BooleanProperty) observable;
    // System.out.println("bb.getBean() = " + bb.getBean());
    // TreeItem t = (TreeItem) bb.getBean();
    // // Do whatever with t
    // }
    // });

    TreeView<TaskNode> filler = new TreeView<>();

    ScrollPane pane = new ScrollPane();
    pane.prefWidthProperty().bind(tree.widthProperty());
    pane.prefHeightProperty().bind(tree.heightProperty());
    pane.setContent(tree);
    pane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);
    pane.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);

    Label label = new Label("foot");
    VBox footer = new VBox(20);
    footer.setBackground(Background.EMPTY);
    String style = "-fx-background-color: rgba(100, 100, 100, .5);";
    footer.setStyle(style);
    footer.setMaxWidth(Region.USE_PREF_SIZE);
    footer.setMaxHeight(Region.USE_PREF_SIZE);
    footer.getChildren().add(label);

    view.getChildren().addAll(tree);
    return view;
  }

  private TreeView<TaskNode> generateTreeView() {
    TreeItem<TaskNode> root = new TreeItem<TaskNode>(tree.getRoot());
    root.setExpanded(true);
    TreeView<TaskNode> treeView = new TreeView<TaskNode>(root);

    List<TreeItem<TaskNode>> toAdd = new ArrayList<>();
    for (TaskNode child : tree.getRoot().children) {
      toAdd.add(treeToViewHelper(child));
    }
    root.getChildren().addAll(toAdd);

    return treeView;
  }

  private TreeItem<TaskNode> treeToViewHelper(TaskNode node) {
    TreeItem<TaskNode> item = new TreeItem<TaskNode>(node);

    List<TreeItem<TaskNode>> toAdd = new ArrayList<>();
    for (TaskNode child : node.children) {
      toAdd.add(treeToViewHelper(child));
    }
    item.getChildren().addAll(toAdd);

    return item;
  }

  @Override
  public void refresh() {
    System.out.println("oho! refresh");
  }

}
