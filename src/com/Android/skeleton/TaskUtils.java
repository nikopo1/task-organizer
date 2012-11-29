package com.Android.skeleton;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class TaskUtils 
{
	private static final float MAX_PERIOD=31*24;
	private static final float MIN_PERIOD=0;
	public static MinMax getMinMaxMaxWorkingTime(ArrayList<Task> tasks)
	{
		MinMax m = new MinMax();
		Task t = tasks.get(0);
		m.setMax(TimeUtils.getIntervalInHours(new GregorianCalendar(), t.getEndTimeInGregorian()));
		m.setMin(TimeUtils.getIntervalInHours(new GregorianCalendar(), t.getEndTimeInGregorian()));
		for(int i=1;i<tasks.size();i++)
		{
			t=tasks.get(i);
			float remainingTime = TimeUtils.getIntervalInHours(new GregorianCalendar(),t.getEndTimeInGregorian());
			
			if(remainingTime>m.getMax())
				m.setMax(remainingTime);
			if(remainingTime<m.getMin())
				m.setMin(remainingTime);
		}
		return m;
		
	}
	public static void calculateTimePriority(ArrayList<Task> tasks)
	{

		for(Task t:tasks)
		{
			float remainingTime = TimeUtils.getIntervalInHours(new GregorianCalendar(),t.getEndTimeInGregorian());
			if(remainingTime>MAX_PERIOD)
				t.setTimePriority(0);
			else
			{
				t.setTimePriority(Math.abs(10-(remainingTime/MAX_PERIOD)*10));
			}
		}
		
	}
	public static void calculateTotalPriority(ArrayList<Task> tasks)
	{
		for(Task t:tasks)
		{
			float priority=(t.getPriority()+t.getTimePriority())/2;
			t.setTotalPriority(priority);
		}
	}
	/**
	 * Metoda care intoarce true daca un task trebuie executat in ziua curenta.
	 * Single day o sa fie true daca evenimentul dureaza o singura zi
	 * O sa intoarca true sau false in fill daca taskul umple toata ziua sau nu
	 * In cazul in care una din zilele de start sau de end se afla in ziua curenta
	 * firstOrLast = false pt ziua de start si true pt zua de sfarsit
	 */
	public static boolean isDueToday(TaskWrapper tw)
	{
		Task t=tw.getTask();
		GregorianCalendar  start = t.getBeginTimeInGregorian();
		GregorianCalendar end = t.getEndTimeInGregorian();
		GregorianCalendar startPlusOne = t.getBeginTimeInGregorian();
		startPlusOne.add(Calendar.DAY_OF_MONTH,1);
		//CAZ 1
		if(TimeUtils.isInSameDay(start,end))
		{
			if(TimeUtils.isInSameDay(new GregorianCalendar(),t.getEndTimeInGregorian()))
			{
				tw.setSameDay(true);
				return true;
			}
		}
		//CAZ 2
		else if(TimeUtils.isInSameDay(startPlusOne,end))
		{
			
			if(TimeUtils.isInSameDay(new GregorianCalendar(),t.getBeginTimeInGregorian()))
			{
				return true;
			}
			if(TimeUtils.isInSameDay(new GregorianCalendar(),t.getEndTimeInGregorian()))
			{
				tw.setFirstOrLast(true);
				return true;
			}
		}
		//CAZ 3
		else
		{
			GregorianCalendar now = new GregorianCalendar();
			if(TimeUtils.isInSameDay(now,start))
			{
				return true;
			}
			else if(TimeUtils.isInSameDay(now, end))
			{
				tw.setFirstOrLast(true);
				return true;
			}
			else if(now.after(start) && now.before(end))
			{
					tw.setFill(true);
					return true;
			}
		}
		return false;
	}
}
