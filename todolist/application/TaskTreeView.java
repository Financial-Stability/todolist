package application;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.CheckBoxTreeItem;
import javafx.scene.control.Label;
import javafx.scene.control.TreeView;
import javafx.scene.control.cell.CheckBoxTreeCell;
import javafx.scene.layout.VBox;

public class TaskTreeView implements View {

  private TaskTree tree;

  TaskTreeView() {
    tree = TaskTree.makeExampleTree();
  }

  @Override
  public Node getSettingsPane() {
    return new VBox();
  }

  @Override
  public Node getViewPane() {
    TreeView<String> view = treeToView();
    view.setShowRoot(false);
    view.setCellFactory(CheckBoxTreeCell.<String>forTreeView());
    return view;
  }

  private TreeView<String> treeToView() {
    CheckBoxTreeItem<String> root = new CheckBoxTreeItem<String>(tree.getRoot().name);
    root.setExpanded(true);

    List<CheckBoxTreeItem<String>> toAdd = new ArrayList<>();
    for (TaskNode child : tree.getRoot().children) {
      toAdd.add(treeToViewHelper(child));
    }
    root.getChildren().addAll(toAdd);

    TreeView<String> treeView = new TreeView<String>(root);

    return treeView;
  }

  private CheckBoxTreeItem<String> treeToViewHelper(TaskNode node) {
    CheckBoxTreeItem<String> item = new CheckBoxTreeItem<>(node.toString());
    // CheckBoxTreeItem<String> item =
    // new CheckBoxTreeItem<>(node.name, new Label("Label: " + node.name), node.checked);

    List<CheckBoxTreeItem<String>> toAdd = new ArrayList<>();
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
