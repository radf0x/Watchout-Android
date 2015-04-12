package com.ouhk.watchout;

import android.text.format.Time;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Utils {

	private static ArrayList<HashMap<String,String>> arraylist = new ArrayList<HashMap<String,String>>();
	private static ArrayList<HashMap<String,String>> showlist = new ArrayList<HashMap<String,String>>();
	private static Long[] tempTimeMillis = null;
	public static void put(HashMap<String,String> map){
		arraylist.add(map);
	}
	
	public static ArrayList<HashMap<String, String>> getList(){
		return arraylist;
	}
	
	public static HashMap<String, String> getItem(int position){
		return arraylist.get(position);
	}
	
	public static void sort(){
		int size = arraylist.size();
        for (int i =size-1; i>= 0; i--)
                for (int j = 0; j < i; j++)
                        if (Long.parseLong(arraylist.get(j).get("datetime"))< Long.parseLong(arraylist.get(j+1).get("datetime"))) {
                                HashMap<String,String> temp = arraylist.get(j);
                                arraylist.set(j, arraylist.get(j+1));
                                arraylist.set(j+1, temp);
                        }
	}
	
	public static void MillisToDate(ArrayList<HashMap<String,String>> arraylist){
		int size = arraylist.size();
		tempTimeMillis = new Long[size];
		for(int i=0;i<size;i++)
		{
			String temp = timeTransfer(i);
			arraylist.get(i).remove("datetime");
			arraylist.get(i).put("datetime", temp);
		}
	}
	
	public static void DateToMillis(ArrayList<HashMap<String,String>> arraylist){
		int size = arraylist.size();
		for(int i=0;i<size;i++){
			String temp = String.valueOf(tempTimeMillis[i]);
			arraylist.get(i).remove("datetime");
			arraylist.get(i).put("datetime", temp);
		}
	}
	public static String format(int hourOfDay) {
		String str = "" + hourOfDay;
	     if(str.length() == 1){
	      str = "0" + str;
	      }
	     return str;
	     }
	public static String toDateString(Calendar calendar){
		String day = null;
		switch(calendar.get(Calendar.DAY_OF_WEEK))
		{
		case 1: day = "Monday";break;
		case 2: day = "Tuesday";break;
		case 3: day = "Wednesday";break;
		case 4: day = "Thursday";break;
		case 5: day = "Friday";break;
		case 6: day = "Saturday";break;
		case 7: day = "Sunday";break;
		}
		return calendar.get(Calendar.YEAR)+"/"
				+Utils.format(calendar.get(Calendar.MONTH)+1)+"/"
				+calendar.get(Calendar.DAY_OF_MONTH)+"/"
				+"   "+ day;
	}
	public static String timeTransfer(int i){
		Time time = new Time();
		long tempLong = Long.parseLong(arraylist.get(i).get("datetime"));
		tempTimeMillis[i] = tempLong;
		time.setToNow();
		String dateNow = time.toString().substring(0,8);
		String timeNow = time.toString().substring(9,13);
		time.set(tempLong);
		String datePast = time.toString().substring(0,8);
		String timePast = time.toString().substring(9,13);
		long tempDate = Long.parseLong(dateNow)-Long.parseLong(datePast);
		long tempTime = Long.parseLong(timeNow)-Long.parseLong(timePast);
		if(tempDate == 0)
			return timePast.substring(0, 2)+":"+timePast.substring(2, 4);
		if(tempDate == 1)
			return "Yesterday";
		if(tempDate>=2)
			return datePast.substring(4,6)+"/"+datePast.substring(6,8);
		return null;
	}
	
	public static String timeTransfer(String i){
		Time time = new Time();
		time.setToNow();
		String dateNow = time.toString().substring(0,8);
		String timeNow = time.toString().substring(9,13);
		time.set(Long.parseLong(i));
		String dateSet = time.toString().substring(0,8);
		String timeSet = time.toString().substring(9,13);
		long tempDate = Long.parseLong(dateSet)-Long.parseLong(dateNow);
		long tempTime = Long.parseLong(timeSet)-Long.parseLong(timeNow);
		if(tempDate < 0 || (tempDate ==0 && tempTime<0))
			return "Expired";
		if(tempDate == 0 && tempTime > 0)
			return "Today"+timeSet.substring(0, 2)+":"+timeSet.substring(2, 4);
		if(tempDate == 1)
			return "Tomorrow"+timeSet.substring(0, 2)+":"+timeSet.substring(2, 4);
		if(tempDate == 2)
			return "In 2 days"+timeSet.substring(0, 2)+":"+timeSet.substring(2, 4);
		if(tempDate > 2)
			return dateSet.substring(4,6)+"/"+dateSet.substring(6,8);
		return null;
	}
}