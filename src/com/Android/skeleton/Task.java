package com.Android.skeleton;

import java.sql.Time;
import java.util.Date;
import java.util.GregorianCalendar;

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
	//prioritatea calculata in functie de timp
	private float timePriority;
	//prioritatea totala
	private float totalPriority;
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
	
	private boolean fill;
	//0 all day , 1 event, 2 project
	private int taskType=2;
	
	
	public int TESTID;
	
	@Override
	public String toString()
	{
		return "ID "+TESTID;
	}
	
	public Task()
	{}
	
	
	public GregorianCalendar getBeginTimeInGregorian()
	{
		return new GregorianCalendar(getBeginDateYear(), 
									 getBeginDateMonth(), 
									 getBeginDateDay(), 
									 getBeginTimeHour(), 
									 getBeginTimeMinute());
		
	}
	public GregorianCalendar getEndTimeInGregorian()
	{
		return new GregorianCalendar(getEndDateYear(),
									 getEndDateMonth(),
									 getEndDateDay(),
									 getEndTimeHour(),
									 getEndTimeMinute()); 
	}
	
	
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
		if(allDay==true)
		{
			if(getFill()==false)
				setTaskType(0);
			else
				setTaskType(1);
		}
		else
		{
			if(getFill()==false)
				setTaskType(2);
			else
				setTaskType(1);
			
		}
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
		this.setBeginDateMonth(m);
		this.setBeginDateYear(y);
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
		this.setEndDateYear(y);
		this.setEndDateMonth(m);
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

	public boolean getFill() {
		return fill;
	}

	public void setFill(boolean fill) 
	{
		if(fill==true)
			setTaskType(1);
		else
		{
			if(getAllDay()==true)
				setTaskType(0);
			else
				setTaskType(2);	
		}
		this.fill = fill;
	}

	public int getTaskType() {
		return taskType;
	}

	private void setTaskType(int taskType) {
		this.taskType = taskType;
	}


	public float getTotalPriority() {
		return totalPriority;
	}


	public void setTotalPriority(float totalPriority) {
		this.totalPriority = totalPriority;
	}

	public float getTimePriority() {
		return timePriority;
	}

	public void setTimePriority(float timePriority) {
		this.timePriority = timePriority;
	}

	public void setBeginDateYear(int beginDateYear) {
		this.beginDateYear = beginDateYear;
	}

	public void setEndDateYear(int endDateYear) {
		this.endDateYear = endDateYear;
	}

	public void setBeginDateMonth(int beginDateMonth) {
		this.beginDateMonth = beginDateMonth;
	}

	public void setEndDateMonth(int endDateMonth) {
		this.endDateMonth = endDateMonth;
	}

	
	
}
