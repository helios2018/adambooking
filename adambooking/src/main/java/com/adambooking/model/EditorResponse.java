package com.adambooking.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the structure we send back to the jQuery DataTables Editor plugin
 * when the user is inserting/updating/deleting things.
 *
 * @param <T> the class being wrapped.
 */
public class EditorResponse<T> {
    public static class FieldError {
        String name;
        String status;
        public FieldError(String name, String status) {
            this.name = name;
            this.status = status;
        }
    }
    private List<T> data;
    private String error;
    private List<FieldError> fieldErrors;
 
    //  ... have your IDE generate getters & setters; plus:
 
    public void add(T newData) {
        if (getData() == null) setData(new ArrayList<T>());
        getData().add(newData);
    }
 
    public void addFieldError(String fieldID, String message) {
        if (getFieldErrors() == null) setFieldErrors(new ArrayList<FieldError>());
        getFieldErrors().add(new FieldError(fieldID, message));
    }

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public List<FieldError> getFieldErrors() {
		return fieldErrors;
	}

	public void setFieldErrors(List<FieldError> fieldErrors) {
		this.fieldErrors = fieldErrors;
	}
}