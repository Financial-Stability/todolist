package data;

import java.util.List;

public class TaskTree {
  private TaskNode root;

  TaskTree() {
    this.root = null;
  }

  TaskTree(TaskNode node) {
    this.root = node;
  }

  public TaskNode getRoot() {
    return root;
  }

  public static void closeChildren(TaskNode parent) {

  }

  public static String getPath(TaskNode node) {
    return null;
  }

  public static TaskTree makeExampleTree() {
    TaskNode root = new TaskNode(null, null, "root", false, 0, null, false, null, null, null, null);
    TaskTree tree = new TaskTree(root);

    TaskNode parent;
    List<TaskNode> children;
    String name;
    boolean checked;
    int duration;
    String project;
    boolean flagged;
    String notes;
    List<String> tags;
    String dueDate;
    String startDate;

    parent = root;
    children = null;
    checked = false;
    duration = 0;
    project = null;
    flagged = false;
    notes = null;
    tags = null;
    dueDate = null;
    startDate = null;

    for (int i = 0; i < Math.random() * 10 + 3; i++) {
      name = "task_" + i;
      root.children.add(new TaskNode(parent, children, name, checked, duration, project, flagged,
          notes, tags, dueDate, startDate));
    }

    for (TaskNode child : root.children) {
      if (Math.random() < 0.8) {
        for (int i = 0; i < Math.random() * 10 + 1; i++) {
          name = "subtask_" + i;
          parent = child;
          child.children.add(new TaskNode(parent, children, name, checked, duration, project,
              flagged, notes, tags, dueDate, startDate));
        }
      }
      for (TaskNode subchild : child.children) {
        if (Math.random() < 0.3) {
          for (int i = 0; i < Math.random() * 10 + 1; i++) {
            name = "subsubtask_" + i;
            parent = subchild;
            subchild.children.add(new TaskNode(parent, children, name, checked, duration, project,
                flagged, notes, tags, dueDate, startDate));
          }
        }
      }
    }
    return tree;
  }

  public void printTree() {
    treePrintHelper(this.root, 0);
  }

  private void treePrintHelper(TaskNode node, int depth) {
    for (int i = 0; i < depth; i++) {
      System.out.print("\t");
    }
    System.out.println(node.name);
    for (TaskNode child : node.children) {
      treePrintHelper(child, depth + 1);
    }
  }

  public static void main(String[] args) {
    TaskTree tree = TaskTree.makeExampleTree();
    tree.printTree();
  }
}
