package com.Android.skeleton;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TimeUtils 
{
	private static GregorianCalendar universalStart = new GregorianCalendar(1900,Calendar.JANUARY,1,0,0);
	public static float getDaysFromStart(GregorianCalendar date)
	{
		int year = date.get(Calendar.YEAR);
		float days = getNumberOfDaysByYear(year);
		days += date.get(Calendar.DAY_OF_YEAR);
		return days;
	}
	
	private static float getNumberOfDaysByYear(int year)
	{
		float sum=0;
		for(int i = universalStart.get(Calendar.YEAR);i<year;i++)
		{
			if(universalStart.isLeapYear(i))
				sum+=366;
			else 
				sum+=365;
		}
		return sum;
	}
	
	public static float getIntervalInHours(GregorianCalendar first, GregorianCalendar last)
	{
		
		float fDays = getDaysFromStart(first);
		float lDays = getDaysFromStart(last);
		
		float gDays = lDays-fDays-1;
		float gHours = 24-first.get(Calendar.HOUR_OF_DAY)+last.get(Calendar.HOUR_OF_DAY)-1;
		float gMinutes = 1-transformMinutesInHours(first.get(Calendar.MINUTE))+transformMinutesInHours(last.get(Calendar.MINUTE));
				
		return gDays*24+gHours+gMinutes;
	}
	public static float getIntervalInDays(GregorianCalendar first,GregorianCalendar last)
	{
		float fDays= getDaysFromStart(first);
		float lDays= getDaysFromStart(last);
		
		return lDays-fDays;
	}
	
	public static boolean isInSameDay(GregorianCalendar date1, GregorianCalendar date2)
	{
		if(date1.get(Calendar.YEAR)==date2.get(Calendar.YEAR))
		{
			if(date1.get(Calendar.MONTH)==date2.get(Calendar.MONTH))
			{
				if(date1.get(Calendar.DAY_OF_MONTH)==date2.get(Calendar.DAY_OF_MONTH))
					return true;
			}
		}
		return false;
	}
	public static boolean isInSameDayIgnoringYear(GregorianCalendar date1, GregorianCalendar date2)
	{
		if(date1.get(Calendar.MONTH)==date2.get(Calendar.MONTH))
		{
			if(date1.get(Calendar.DAY_OF_MONTH)==date2.get(Calendar.DAY_OF_MONTH))
				return true;
		}
		return false;
	}
	public static boolean isBeforeOrEqConsideringYMD(GregorianCalendar date1, GregorianCalendar date2)
	{
		if(date2.get(Calendar.YEAR)>date1.get(Calendar.YEAR))
		{
			return true;
		}
		else if(date2.get(Calendar.YEAR)==date1.get(Calendar.YEAR))
		{
			if(date2.get(Calendar.MONTH)>date1.get(Calendar.MONTH))
			{
				return true;
			}
			else if(date2.get(Calendar.MONTH)==date1.get(Calendar.MONTH))
			{
				if(date2.get(Calendar.DAY_OF_MONTH)>=date1.get(Calendar.DAY_OF_MONTH))
				{
					return true;
				}
			}
		}
		return false;
	}
	private static float transformMinutesInHours(int minutes)
	{
		return (float) (minutes/60.0);
	}
}
