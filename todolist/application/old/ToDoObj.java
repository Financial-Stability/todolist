package application.old;

import java.io.Serializable;

// Possibly add recurring tasks in future

public class ToDoObj implements Serializable {
	
//	private HashMap<String,String> attributes;
	private Boolean completed = null;
	private String title = null;
	private String dueDate = null;
	private String time = null;
	private String description = null;
	
	/** 
	Base DataType for storing event data
	@param title : name of event 
	@param dueDate : event deadline / target completion date
	@param time : time added TODO: determine format
	*/
	
	public ToDoObj(String title, String dueDate, String time, String description){
		this.title = title;
		this.dueDate = dueDate;
		this.time = time;
		this.completed = false;
		this.description = description;
	  }
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String newTitle) {
		title = newTitle;
	}
	
	public String getDueDate() {
		return dueDate;
	}
	
	public void setDueDate(String newDueDate) {
		dueDate = newDueDate;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String newTime) {
		time = newTime;
	}
	
	public Boolean isCompleted() {
		return completed;
	}
	
	public void setCompleted(Boolean a) {
		completed = a;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String newDescription) {
		this.description = newDescription;
	}
	
}
