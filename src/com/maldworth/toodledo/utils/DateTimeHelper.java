package com.maldworth.toodledo.utils;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.maldworth.toodledo.enums.DateFormat;

public final class DateTimeHelper
{	
	public static final long toUnixTime(DateTime dateTime)
	{
		return dateTime.getMillis() / 1000;
	}
	
	public static final DateTime fromUnixTime(long unixTime)
	{
		return new DateTime(unixTime * 1000);
	}
	
	public static final String toYMDHMS(DateTime dateTime, boolean isAstronomical)
	{
		DateTimeFormatter dtf;
		if(isAstronomical == true)
			dtf = DateTimeFormat.forPattern("YYYY-MM-dd H:mm:ss");
		else
			dtf = DateTimeFormat.forPattern("YYYY-MM-dd h:mm:ss");
		
		return dateTime.toString(dtf);//Calls the base toString
	}
	
	public static final String toYMDHMS(DateTime dateTime)
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd HH:mm:ss");
		
		return dateTime.toString(dtf);//Calls the base toString
	}
	
	public static final String toYMD(DateTime dateTime)
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("YYYY-MM-dd");
		return dateTime.toString(dtf);//Calls the base toString
	}
	
	public static final String toHHmmaa(DateTime dateTime, boolean isAstronomical)
	{
		DateTimeFormatter dtf;
		if(isAstronomical == true)
			dtf = DateTimeFormat.forPattern("H:mm");
		else
			dtf = DateTimeFormat.forPattern("h:mm aa");
		
		return dateTime.toString(dtf);//Calls the base toString
	}
	
	public static final String tohmmaa(DateTime dateTime)
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("h:mm aa");
		
		return dateTime.toString(dtf);//Calls the base toString
	}
	
	public static final String toHmm(DateTime dateTime)
	{
		DateTimeFormatter dtf = DateTimeFormat.forPattern("H:mm");
		
		return dateTime.toString(dtf);//Calls the base toString
	}
	
	public static final boolean isEqual(DateTime date1, DateTime date2)
	{
		if(date1 == null || date2 == null)
			return false;
		
		long unix1 = toUnixTime(date1);
		long unix2 = toUnixTime(date2);
		
		if(unix1 == unix2)
			return true;
		return false;
	}
	
	public static final boolean isEqual(DateTime date1, long date2)
	{
		if(date1 == null || date2 < 1L)
			return false;
		
		long unix1 = toUnixTime(date1);
		
		if(unix1 == date2)
			return true;
		return false;
	}
	
	public static final String toString(DateTime dateTime, DateFormat dateFormat)
	{
		String pattern;
		
		switch(dateFormat)
		{
		case MDYslashes:
			pattern = "MM/dd/yyyy";
			break;
		case DMYslashes:
			pattern = "dd/MM/yyyy";
			break;
		case YMDdashes:
			pattern = "yyyy-MM-dd";
			break;
		case MDYcommas:
		default:
			pattern = "MMM d, yyyy";
			break;
		}
		
		return dateTime.toString(DateTimeFormat.forPattern(pattern));
	}
	public static final DateTime fromYMDHMS(String dateString)
	{
		dateString = dateString.replace(' ', 'T');
		return new DateTime(dateString);
	}
}
