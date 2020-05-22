package application;

import java.util.ArrayList;
import java.util.List;

public class TaskNode {
  protected TaskNode parent;
  protected List<TaskNode> children;
  public String name;
  public boolean checked;
  public int duration;
  public String project;
  public boolean flagged;
  public String notes;
  public List<String> tags;
  public String dueDate;
  public String startDate;

  /**
   * @param parent
   * @param children
   * @param name
   * @param checked
   * @param duration
   * @param project
   * @param flagged
   * @param notes
   * @param tags
   * @param dueDate
   * @param startDate
   */
  public TaskNode(TaskNode parent, List<TaskNode> children, String name, boolean checked,
      int duration, String project, boolean flagged, String notes, List<String> tags,
      String dueDate, String startDate) {
    super();
    this.parent = parent;
    if (children == null) {
      this.children = new ArrayList<TaskNode>();
    } else {
      this.children = children;
    }
    this.name = name;
    this.checked = checked;
    this.duration = duration;
    this.project = project;
    this.flagged = flagged;
    this.notes = notes;
    if (tags == null) {
      this.tags = new ArrayList<String>();
    } else {
      this.tags = tags;
    }
    this.dueDate = dueDate;
    this.startDate = startDate;
  }

  @Override
  public String toString() {
    return name + " [duration=" + duration + ", project=" + project + ", flagged=" + flagged
        + ", tags=" + tags + ", dueDate=" + dueDate + ", startDate=" + startDate + "]";
  }

}
