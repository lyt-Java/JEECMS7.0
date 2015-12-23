package com.jeecms.common.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils extends org.apache.commons.lang.time.DateFormatUtils{
	private DateFormatUtils(){}
	
	public static String formatDate(Date date){
		return DateFormat.getDateInstance().format(date);
	}
	
	public static String formatTime(Date date){
		return DateFormat.getTimeInstance().format(date);
	}
	
	public static String formatDateTime(Date date){
		if(DateFormat.getDateTimeInstance().format(date).contains("0:00:00")){
			return formatDate(date);
		}
		return DateFormat.getDateTimeInstance().format(date);
	}
	
	public static Date parseTime(Date time){
		DateFormat format = new SimpleDateFormat("HH:mm:ss"); 
		Date result=time;
		try {
			result = format.parse(formatTime(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return result;
	}
	
	public static Date parseDate(Date time){
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd"); 
		Date result=time;
		try {
			result = format.parse(formatDate(time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return result;
	}
}
