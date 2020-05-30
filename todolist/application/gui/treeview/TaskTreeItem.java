package application.gui.treeview;

import application.data.TaskNode;
import javafx.scene.control.TreeItem;

public class TaskTreeItem extends TreeItem<TaskNode> {

  TaskTreeItem() {
    super();
    this.addEventHandler(TreeItem.branchExpandedEvent(), (event -> {
      System.out.println("Height change");
    }));
    this.addEventHandler(TreeItem.branchCollapsedEvent(), (event -> {
      System.out.println("Height change");
    }));
    // getBranchExpandedEventHandler();
    // getBranchCollapsedEventHandler();
  }

  TaskTreeItem(TaskNode node) {
    super(node);
    this.addEventHandler(TreeItem.branchExpandedEvent(), (event -> {
      System.out.println("Height change");
    }));
    this.addEventHandler(TreeItem.branchCollapsedEvent(), (event -> {
      System.out.println("Height change");
    }));
    // getBranchExpandedEventHandler();
    // getBranchCollapsedEventHandler();
  }
}
