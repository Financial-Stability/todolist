package application.old;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

// Used reference:
// https://stackoverflow.com/questions/23673180/create-generic-tree-with-more-than-two-child-each-may-have-unique-proprties

// TODO: Stop HashMap keys from being case-sensitive (all incoming keys get .lower'd)
// TODO: Add method for returning iterable list of keys for HashMap

/**
 * - TaskList holds all events which will be displayed on tab - TreeList holds
 * other lists
 * 
 * For example, class structure may look as follows: Top Level: | School | Work
 * | Dancing | Second Level: | Class1, Class2 | Bring papers, Arrange Meeting |
 * Make Shoes, practice |
 * 
 * (Class structure continues down with separation of tasks and other lists
 * through sub task and tree lists)
 */

public class Tree implements Serializable {

	private String title; // Name of list
	private String description; // Notes/Description of list
	private ArrayList<ToDoObj> TaskList = new ArrayList<ToDoObj>(); // List of tasks
	private HashMap<String, Tree> TreeList = new HashMap<String, Tree>(); // List of other lists
	
	/**
	 * @param title
	 * @param description
	 */
	public Tree(String title, String description) {
		this.title = title;
		this.description = description;
	}
	
	// Title
	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
	}

	// Description
	public String getDescription() {
		return description;
	}

	public void setDescription(String newDescription) {
		this.description = newDescription;
	}

	// Tree List
	public HashMap<String, Tree> getTreeList() {
		return TreeList;
	}

	public void addTree(String treeName, Tree tree) {
		this.TreeList.put(treeName, tree);
	}

	public Tree getTree(String key) {
		return TreeList.get(key);
	}
	
	public void removeTree(String key) {
		TreeList.remove(key);
	}

	// Task List
	public ArrayList<ToDoObj> getTaskObjects() {
		return TaskList;
	}

	public ArrayList<String> getTaskList() {

		ArrayList<String> taskList = new ArrayList<String>();
		for (ToDoObj obj : TaskList) {
			taskList.add(obj.getTitle());
		}

		return taskList;
	}

	public void addTask(ToDoObj task) {
		TaskList.add(task);
	}

	public void removeTask(String taskTitle) {
		int index = 0;
		int removeIndex = -1;
		for (ToDoObj task : TaskList) {
			if (task.getTitle() == taskTitle) {
				removeIndex = index;
			}
			index++;
		}
		if (removeIndex != -1) {
			TaskList.remove(removeIndex);
		}
	}

}
