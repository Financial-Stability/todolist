package application.DataType;

import java.util.HashMap;

// Possibly add recurring tasks in future

public class ToDoObj {
	
	private HashMap<String,String> attributes;
	
	/** 
	Base DataType for storing event data
	@param title : name of event 
	@param dueDate : event deadline / target completion date
	@param timeAdded : time added TODO: determine format
	*/
	
	public ToDoObj(String title, String dueDate, String timeAdded){
		this.attributes = new HashMap<String, String>() {{
			put("title", title);
			put("dueDate", dueDate);
			put("timeAdded", timeAdded);
		}};
	  }
	
//	public HashMap<String, String> getAttributes() {
//		return attributes;
//	}
	
	public String getAttribute(String key) {
		String attributeValue = attributes.get(key);
		return attributeValue;
	}
	
	public void setAttribute(String attribute, String value) {
		this.attributes.put(attribute, value);
	}
	
}
