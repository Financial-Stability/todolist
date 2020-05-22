package application;

public class TaskNode {
  protected TaskNode parent;
  protected TaskNode[] children;
  public String name;
  public boolean checked;
  public int duration;
  public String project;
  public boolean flagged;
  public String notes;
  public String[] tags;
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
  public TaskNode(TaskNode parent, TaskNode[] children, String name, boolean checked, int duration,
      String project, boolean flagged, String notes, String[] tags, String dueDate,
      String startDate) {
    super();
    this.parent = parent;
    this.children = children;
    this.name = name;
    this.checked = checked;
    this.duration = duration;
    this.project = project;
    this.flagged = flagged;
    this.notes = notes;
    this.tags = tags;
    this.dueDate = dueDate;
    this.startDate = startDate;
  }


}
