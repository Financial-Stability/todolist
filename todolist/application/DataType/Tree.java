package application.DataType;

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

public class Tree {

	private String title; // Name of list
//  private String description;            // Notes/Description of list
	private ArrayList<ToDoObj> TaskList = new ArrayList<ToDoObj>(); // List of tasks
	private HashMap<String, Tree> TreeList = new HashMap<String, Tree>(); // List of other lists

	public Tree(String title) {
		this.title = title;
	}

	// Title
	public String getTitle() {
		return title;
	}

	public void setTitle(String newTitle) {
		this.title = newTitle;
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

	// Task List
	public ArrayList<ToDoObj> getTaskList() {
		return TaskList;
	}

	public void addTask(ToDoObj task) {
		TaskList.add(task);
	}

}
