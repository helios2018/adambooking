package com.adambooking.helper;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.adambooking.model.TimeSlot;
import com.adambooking.utilities.CalendarUtility;

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
		
		CalendarUtility calendarUtility = new CalendarUtility();
		List<TimeSlot> customizedTimeSlots = new ArrayList<TimeSlot>(); //this is the final list
		TimeSlot backupTimeSlot = null;
		for(TimeSlot outerTimeSlot : lTimeSlots){
			if (backupTimeSlot != null){
				if (calendarUtility.getDateFromIsoDate(outerTimeSlot.getStart()).
						equals(calendarUtility.getDateFromIsoDate(backupTimeSlot.getStart())) ){
					continue;//this will force it to go to next day
				}
			}
			ArrayList<TimeSlot> timeSlotsPerDay = new ArrayList<TimeSlot>(); //this is a temporarily list for each day
			for(TimeSlot innerTimeSlot :lTimeSlots){
				if (calendarUtility.getDateFromIsoDate(outerTimeSlot.getStart()).
						equals(calendarUtility.getDateFromIsoDate(innerTimeSlot.getStart()) ) ) {
					timeSlotsPerDay.add(innerTimeSlot);
				}
		     }
			//Before you go to the next day, keep only 3
			ArrayList<TimeSlot> customizedTimeSlotsPerDay = new ArrayList<TimeSlot>();
			int randomNumber = 0;
			int[] backupRandomNumbers = new int[3];
			if (timeSlotsPerDay.size() > 3){
				int i=0;
				while(customizedTimeSlotsPerDay.size() < 3){
					randomNumber = generateRandomNumber();
					//if random number has been taken already, generate a new number
					for(int r=0; r<backupRandomNumbers.length; r++){
						if (randomNumber == backupRandomNumbers[r] || randomNumber >= timeSlotsPerDay.size()){
							randomNumber = generateRandomNumber();
							r=-1;
						}
					}
					
					customizedTimeSlotsPerDay.add(timeSlotsPerDay.get(randomNumber));
					customizedTimeSlots.add(timeSlotsPerDay.get(randomNumber));
					backupRandomNumbers[i] = randomNumber;
					i++;
				}
			} else {
				customizedTimeSlots.addAll(timeSlotsPerDay);
			}
			
			System.out.println("***********************");
			for(int r=0; r<backupRandomNumbers.length; r++){
				System.out.println(backupRandomNumbers[r]);
			}
			System.out.println("***********************");
			
			backupTimeSlot = outerTimeSlot;
	    }
		
		return customizedTimeSlots;
		
		
	}
	
	public int generateRandomNumber(){
		double doubleNum = Math.random();
		String doubleNumStrLastNum = null;
		//System.out.println("doubleNum: " + doubleNum);
		if (doubleNum % 2 == 0){
			//System.out.println("even");
		}
		else{
			//System.out.println("odd");
		}
		
		String doubleNumStr = String.valueOf(doubleNum);
		//System.out.println("doubleNumStr: " + doubleNumStr);
		if (doubleNumStr.length() > 0){
			doubleNumStrLastNum = doubleNumStr.substring(doubleNumStr.length() -1 , doubleNumStr.length());
			//System.out.println("random number: "  + doubleNumStrLastNum);
		}
		
		return Integer.parseInt(doubleNumStrLastNum);
	}

}
