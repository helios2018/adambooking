package com.adambooking.model;

import java.util.Map;

/**
 * This is the structure we get from the jQuery DataTables Editor plugin when
 * the user is inserting/updating/deleting things.
 *
 * @param <K> the type of key used by the class being wrapped.
 * @param <V> the class being wrapped.
 */
public class EditorRequest<K, V> {
 
    private String action;
    private Map<K, V> data;
  
    public boolean isInsert() {
        return "create".equals(getAction());
    }
    public boolean isUpdate() {
        return "edit".equals(getAction());
    }
    
    public boolean isDelete() {
        return "remove".equals(getAction());
    }
    
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	
	public Map<K, V> getData() {
		return data;
	}
	public void setData(Map<K, V> data) {
		this.data = data;
	}
}   