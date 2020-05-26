package application.gui.treeview;

import java.util.ArrayList;
import java.util.List;
import application.data.TaskNode;
import application.data.TaskTree;
import application.gui.View;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class TaskNormalTreeView implements View {

  private TaskTree tree;

  public TaskNormalTreeView() {
    tree = TaskTree.makeExampleTree();
  }

  @Override
  public Node getSettingsPane() {
    return new VBox();
  }

  @Override
  public Node getViewPane() {
    VBox view = new VBox();

    TreeView<TaskNode> tree = generateTreeView();
    tree.setShowRoot(false);
    tree.setCellFactory(new TaskNodeCellFactory());
    // tree.setCellFactory(p -> new CheckBoxTreeCell<TaskNode>() {
    // @Override
    // public void updateItem(TaskNode item, boolean empty) {
    // super.updateItem(item, empty);
    // if (item == null)
    // return;
    // if (!empty) {
    // setText(item.name);
    // } else {
    // setText(null);
    // }
    // }
    // });

    // tree.setMaxWidth(Region.USE_PREF_SIZE);

    tree.setFixedCellSize(24);
    // to hide buggy cells
    tree.expandedItemCountProperty().addListener(event -> {
      tree.setPrefHeight(tree.getExpandedItemCount() * tree.getFixedCellSize() + 2);
      tree.setPrefWidth(tree.getWidth());
      tree.autosize();
    });

    view.getChildren().addAll(tree);
    return view;
  }

  private TreeView<TaskNode> generateTreeView() {
    CheckBoxTreeItem<TaskNode> root = new CheckBoxTreeItem<TaskNode>(tree.getRoot());
    root.setExpanded(true);
    TreeView<TaskNode> treeView = new TreeView<TaskNode>(root);

    List<CheckBoxTreeItem<TaskNode>> toAdd = new ArrayList<>();
    for (TaskNode child : tree.getRoot().children) {
      toAdd.add(treeToViewHelper(child));
    }
    root.getChildren().addAll(toAdd);

    return treeView;
  }

  private CheckBoxTreeItem<TaskNode> treeToViewHelper(TaskNode node) {
    CheckBoxTreeItem<TaskNode> item = new CheckBoxTreeItem<TaskNode>(node);

    List<CheckBoxTreeItem<TaskNode>> toAdd = new ArrayList<>();
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
