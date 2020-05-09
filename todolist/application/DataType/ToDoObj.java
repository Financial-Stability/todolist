package application.DataType;

import java.io.Serializable;

// Possibly add recurring tasks in future

public class ToDoObj implements Serializable {
	
//	private HashMap<String,String> attributes;
	private String title = null;
	private String dueDate = null;
	private String time = null;
	private Boolean completed = null;
	
	/** 
	Base DataType for storing event data
	@param title : name of event 
	@param dueDate : event deadline / target completion date
	@param time : time added TODO: determine format
	*/
	
	public ToDoObj(String title, String dueDate, String time){
		this.title = title;
		this.dueDate = dueDate;
		this.time = time;
		this.completed = false;
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
	
}
