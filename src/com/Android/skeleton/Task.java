package com.Android.skeleton;

import java.sql.Time;
import java.util.Date;

public class Task {
	
	// titlul task-ului
	private String title;
	// daca este un eveniment care dureaza toata ziua
	private boolean allDay;
	// daca evenimentul se repeta
	private boolean repeat;
	// descrierea
	private String description;
	// unde are loc evenimentul
	private String location;
	// prioritatea evenimentului 
	// de la 0 la 10
	private int priority;
	// daca tii cont de prioritatea evenimentului atunci cand planifici
	private boolean considerPriority;
	// daca tii cont de data/ora evenimentului cand planifici
	private boolean considerTime;
	
	//private Date beginDate;
	private int beginDateYear;
	private int beginDateMonth;
	private int beginDateDay;
	
	//private Date endDate;
	private int endDateYear;
	private int endDateMonth;
	private int endDateDay;
	
	//private Time beginTime;
	private int beginTimeHour;
	private int beginTimeMinute;
	
	//private Time endTime;
	private int endTimeHour;
	private int endTimeMinute;
	
	// daca se repeta zilnic (0), saptamanal(1), lunar(2) sau anual(3) 
	private int repeatType;
	// intervalul la care se repeta
	// de la 1 la 10 
	private int repeatInterval;
	
	public Task()
	{}
	
	public void setRepeatType(int repeatType)
	{
		this.repeatType = repeatType;
	}
	public int getRepeatType()
	{
		return repeatType;
	}
	
	public void setRepeatInterval(int repeatInterval)
	{
		this.repeatInterval = repeatInterval;
	}
	public int getRepeatInterval()
	{
		return repeatInterval;
	}
	
	
	public void setTitle(String title)
	{
		this.title = title;
	}
	public String getTitle()
	{
		return title;
	}
	
	public void setAllDay(boolean allDay)
	{
		this.allDay = allDay;
	}
	public boolean getAllDay()
	{
		return allDay;
	}
	
	public void setRepeat(boolean repeat)
	{
		this.repeat = repeat;
	}
	public boolean getRepeat()
	{
		return repeat;
	}
	
	public void setDescirption(String description)
	{
		this.description = description;
	}
	public String getDescription()
	{
		return description;
	}
	
	public void setLocation(String location)
	{
		this.location = location;
	}
	public String getLocation()
	{
		return location;
	}
	
	public void setPriority(int priority)
	{
		this.priority = priority;
	}
	public int getPriority()
	{
		return priority;
	}
	
	public void setConsiderPriority(boolean considerPriority)
	{
		this.considerPriority = considerPriority;
	}
	public boolean getConsiderPriority()
	{
		return considerPriority;
	}
	
	public void setConsiderTime(boolean considerTime)
	{
		this.considerTime = considerTime;
	}
	public boolean getConsiderTime()
	{
		return considerTime;
	}
	
	public void setBeginDate(int y, int m, int d)
	{
		this.beginDateDay = d;
		this.beginDateMonth = m;
		this.beginDateYear = y;
	}
	public int getBeginDateYear()
	{
		return beginDateYear;
	}
	public int getBeginDateMonth()
	{
		return beginDateMonth;
	}
	public int getBeginDateDay()
	{
		return beginDateDay;
	}
	
	public void setEndDate(int y, int m, int d)
	{
		this.endDateYear = y;
		this.endDateMonth = m;
		this.endDateDay = d;
	}
	public int getEndDateYear()
	{
		return endDateYear;
	}
	public int getEndDateMonth()
	{
		return endDateMonth;
	}
	public int getEndDateDay()
	{
		return endDateDay;
	}
	
	public void setBeginTime(int h, int m)
	{
		this.beginTimeHour = h;
		this.beginTimeMinute = m;
	}
	public int getBeginTimeHour()
	{
		return beginTimeHour;
	}
	public int getBeginTimeMinute()
	{
		return beginTimeMinute;
	}
	
	public void setEndTime(int h, int m)
	{
		this.endTimeHour = h;
		this.endTimeMinute = m;
	}
	public int getEndTimeHour()
	{
		return endTimeHour;
	}
	public int getEndTimeMinute()
	{
		return endTimeMinute;
	}
	
	
}
