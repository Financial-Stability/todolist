package application;

public class TaskTree {
  private TaskNode root;

  TaskTree() {
    this.root = null;
  }

  public TaskNode getRoot() {
    return root;
  }

  public static void closeChildren(TaskNode parent) {

  }

  public static String getPath(TaskNode node) {
    return null;
  }
}
