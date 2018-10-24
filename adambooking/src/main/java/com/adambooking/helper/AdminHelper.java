package com.adambooking.helper;

import java.util.ArrayList;
import java.util.List;

import com.adambooking.model.TimeSlot;

public class AdminHelper {
	
	
	/***
	 * Purpose: This method will only keep 3 slots per day
	 * 
	 */
	public List<TimeSlot> customizeAvailableSlotsList(List<TimeSlot> lTimeSlots, int availableSlotsCount){
		/*for (int i=0; i<lTimeSlots.size(); i++){
			if (lTimeSlots.size() > availableSlotsCount){
				lTimeSlots.remove(i);
			}
		}*/
		/*
		while(lTimeSlots.size() > 3){
			lTimeSlots.remove(0);
		}*/
		
		List<TimeSlot> customizedTimeSlots = new ArrayList<TimeSlot>();
		
		
		for(TimeSlot outerTimeSlot : lTimeSlots){
			System.out.println(outerTimeSlot.getStart());
			ArrayList<TimeSlot> tempTimeSlots = new ArrayList<TimeSlot>();
			for(TimeSlot innerTimeSlot :lTimeSlots){
				System.out.println(innerTimeSlot.getStart());
				
				if (outerTimeSlot.getStart().equals(innerTimeSlot.getStart())){
					tempTimeSlots.add(innerTimeSlot);
				}
		     }
			
			//Before you go to the next day, keep only 3
			ArrayList<TimeSlot> timeSlotsPerDay = new ArrayList<TimeSlot>();
			if (tempTimeSlots.size() > 3){
				while(timeSlotsPerDay.size() < 3){
					timeSlotsPerDay.add(tempTimeSlots.get(generateRandomNumber()));
					customizedTimeSlots.add(tempTimeSlots.get(generateRandomNumber()));
				}
			}
	    }
		
		
		
		return customizedTimeSlots;
		
		
	}
	
	public int generateRandomNumber(){
		double doubleNum = Math.random();
		String doubleNumStrLastNum = null;
		System.out.println("doubleNum: " + doubleNum);
		if (doubleNum % 2 == 0){
			System.out.println("even");
		}
		else{
			System.out.println("odd");
		}
		
		String doubleNumStr = String.valueOf(doubleNum);
		System.out.println("doubleNumStr: " + doubleNumStr);
		if (doubleNumStr.length() > 0){
			doubleNumStrLastNum = doubleNumStr.substring(doubleNumStr.length() -1 , doubleNumStr.length());
			System.out.println("doubleNumStrLastNum: "  + doubleNumStrLastNum);
		}
		
		return Integer.parseInt(doubleNumStrLastNum);
	}

}
