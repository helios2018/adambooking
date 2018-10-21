package com.adambooking.model;

public class TimeSlot {
	
	/*
	 {
	      "title": "10:30",
	      "start": "2018-05-24T10:00:00",
	      "end": "2018-05-24T10:30:00",
	      "rendering": "backgroud",
	      "backgroundColor": "green",
	      "allDay": false,
	      "editable": false
	     
	   },
	 */
	
	private String title;
	private String start;
	private String end;
	private String rendering; 
	private String backgroundColor;
	private boolean allDay;
	private boolean editable;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	
	/*@Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof TimeSlot)) {
            return false;
        }
        TimeSlot otherTimeSlot = (TimeSlot)anObject;
        boolean flag = otherTimeSlot.getStart().equals(getStart()) && otherTimeSlot.getEnd().equals(getEnd());
        return flag;
    }*/
	
	@Override
    public boolean equals(Object anObject) {
        if (!(anObject instanceof TimeSlot)) {
            return false;
        }
        TimeSlot calendarTimeSlot = this;
        String calendarTimeSlotStart = calendarTimeSlot.getStart();
        String calendarTimeSlotEnd = calendarTimeSlot.getEnd();
        
        TimeSlot unAvailableTimeSlot = (TimeSlot)anObject;
        String unAvailableTimeSlotStart = unAvailableTimeSlot.getStart();
        String unAvailableTimeSlotEnd = unAvailableTimeSlot.getEnd();
        
        String logTimesStr = "calendarTimeSlotStart: " + calendarTimeSlotStart + "\n" +
        					 "calendarTimeSlotEnd: " + calendarTimeSlotEnd + "\n" + 
        					 "unAvailableTimeSlotStart: " + unAvailableTimeSlotStart + "\n" +
        					 "unAvailableTimeSlotEnd: " + unAvailableTimeSlotEnd + "\n" + "\n";
        System.out.print(logTimesStr);
        
        if ( (calendarTimeSlotStart.equals(unAvailableTimeSlotStart) && calendarTimeSlotEnd.equals(unAvailableTimeSlotEnd)) ||
        	 (greaterThan(calendarTimeSlotStart, unAvailableTimeSlotStart) && lessThan(calendarTimeSlotStart, unAvailableTimeSlotEnd)) ||
        	 (greaterThan(calendarTimeSlotEnd, unAvailableTimeSlotStart) && lessThan(calendarTimeSlotEnd, unAvailableTimeSlotEnd)) || 
        	 (calendarTimeSlotStart.equals(unAvailableTimeSlotStart) && lessThan(calendarTimeSlotEnd, unAvailableTimeSlotEnd)) || 
        	 (calendarTimeSlotStart.equals(unAvailableTimeSlotStart) && greaterThan(calendarTimeSlotEnd, unAvailableTimeSlotEnd)) ){
        	return true;
        }
        	
        
        //boolean flag = calendarTimeSlot.getStart().equals(getStart()) && calendarTimeSlot.getEnd().equals(getEnd());
        return false;
    }
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	
	public String getRendering() {
		return rendering;
	}
	public void setRendering(String rendering) {
		this.rendering = rendering;
	}
	
	public String getBackgroundColor() {
		return backgroundColor;
	}
	public void setBackgroundColor(String backgroundColor) {
		this.backgroundColor = backgroundColor;
	}
	
	public boolean isEditable() {
		return editable;
	}
	public void setEditable(boolean editable) {
		this.editable = editable;
	}
	
	public boolean isAllDay() {
		return allDay;
	}
	public void setAllDay(boolean allDay) {
		this.allDay = allDay;
	}
	
	private boolean greaterThan(String calendarTimeSlot, String unAvailableTimeSlot){
		if (calendarTimeSlot.compareTo(unAvailableTimeSlot) > 0){
			return true;
		}
		return false;
	}
	
   private boolean lessThan(String calendarTimeSlot, String unAvailableTimeSlot){
	   if (calendarTimeSlot.compareTo(unAvailableTimeSlot) < 0){
			return true;
		}
		return false;
	}
	

}
