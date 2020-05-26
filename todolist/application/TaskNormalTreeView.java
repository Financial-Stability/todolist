package application;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
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
    StackPane view = new StackPane();
    TreeView<TaskNode> tree = generateTreeView();
    tree.setShowRoot(false);
    tree.setCellFactory(new TaskNodeCellFactory());
    view.getChildren().add(tree);
    return view;
  }

  private TreeView<TaskNode> generateTreeView() {
    TreeItem<TaskNode> root = new TreeItem<>(tree.getRoot());
    root.setExpanded(true);

    List<TreeItem<TaskNode>> toAdd = new ArrayList<>();
    for (TaskNode child : tree.getRoot().children) {
      toAdd.add(treeToViewHelper(child));
    }
    root.getChildren().addAll(toAdd);

    TreeView<TaskNode> treeView = new TreeView<TaskNode>(root);

    return treeView;
  }

  private TreeItem<TaskNode> treeToViewHelper(TaskNode node) {
    TreeItem<TaskNode> item = new CheckBoxTreeItem<>(node);

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
