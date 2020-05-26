package application;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
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
    TreeView<TaskNode> view = generateTreeView();
    view.setShowRoot(false);
    view.setCellFactory(CheckBoxTreeCell.<TaskNode>forTreeView());
    return view;
  }

  private TreeView<TaskNode> generateTreeView() {
    CheckBoxTreeItem<TaskNode> root = new CheckBoxTreeItem<>(tree.getRoot());
    root.setExpanded(true);

    List<CheckBoxTreeItem<TaskNode>> toAdd = new ArrayList<>();
    for (TaskNode child : tree.getRoot().children) {
      toAdd.add(treeToViewHelper(child));
    }
    root.getChildren().addAll(toAdd);

    TreeView<TaskNode> treeView = new TreeView<TaskNode>(root);

    return treeView;
  }

  private CheckBoxTreeItem<TaskNode> treeToViewHelper(TaskNode node) {
    CheckBoxTreeItem<TaskNode> item = new CheckBoxTreeItem<>(node);
    // CheckBoxTreeItem<String> item =
    // new CheckBoxTreeItem<>(node.name, new Label("Label: " + node.name), node.checked);

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
