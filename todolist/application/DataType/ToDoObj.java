package application.DataType;

import java.util.HashMap;

// Possibly add recurring tasks in future

public class ToDoObj {
	
	private HashMap<String,String> attributes;
	private String title = null;
	private String dueDate = null;
	private String time = null;
	
	/** 
	Base DataType for storing event data
	@param title : name of event 
	@param dueDate : event deadline / target completion date
	@param time : time added TODO: determine format
	*/
	
	public ToDoObj(String title, String dueDate, String time){
		this.attributes = new HashMap<String, String>() {{
			put("title", title);
			put("dueDate", dueDate);
			put("time", time);
		}};
		this.title = title;
		this.dueDate = dueDate;
		this.time = time;
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
	
	public String getAttribute(String key) {
		String attributeValue = attributes.get(key);
		return attributeValue;
	}
	
	public void setAttribute(String attribute, String value) {
		this.attributes.put(attribute, value);
	}
	
}
