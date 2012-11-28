package com.Android.skeleton;

import java.io.ObjectInputStream.GetField;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;


public class Algorithm 
{

	public static boolean CONSIDER_TIME=false;
	public static int PROGRAMMED_DAY_TASK_NUMBER=3;
	
	public ArrayList<Task> getAllDayDisplayTasks(ArrayList<Task> allTasks)
	{
		return filterTasks(allTasks,0);
	}
	public ArrayList<Task> organizeDay(ArrayList<Task> allTasks)
	{
		cleanupTasks(allTasks);
		//updateTasksDate(events);
		ArrayList<Task> fullDay = filterTasks(allTasks,0);
		ArrayList<Task> events = filterTasks(allTasks,1);
		ArrayList<Task> projects = filterTasks(allTasks,2);
		
		return getTodaysTasks(events);
	}
	public void organizePrio(ArrayList<Task> allTasks,int nbResults,ArrayList<Task> result)
	{
		result.clear();
		cleanupTasks(allTasks);
		if(CONSIDER_TIME==false)
		{
			ArrayList<Task> prio = filterPrio(allTasks);
			Collections.sort(prio,new PrioComparator());
			for(int i=0;i<Math.min(nbResults,prio.size());++i)
			{
				result.add(prio.get(i));
			}
		}
		else
		{
			ArrayList<Task> evAndProj = new ArrayList<Task>(); 
			ArrayList<Task> events = filterTasks(allTasks,1);
			for(Task t:events)
			{
				t.setTotalPriority(t.getPriority());
				evAndProj.add(t);
			}
			
			ArrayList<Task> projects = filterTasks(allTasks,2);
			TaskUtils.calculateTimePriority(projects);
			TaskUtils.calculateTotalPriority(projects);
			for(Task t:projects)
			{
				evAndProj.add(t);
			}
			
			Collections.sort(evAndProj,new FullComparator());
			for(int i=0;i<Math.min(nbResults,evAndProj.size());++i)
			{
				result.add(evAndProj.get(i));
			}
		}
	}
	public void organizeAll(ArrayList<Task> allTasks, ArrayList<Task> result)
	{
		organizePrio(allTasks,Integer.MAX_VALUE,result);
	}
	public ArrayList<Task> getTodaysTasks(ArrayList<Task> events)
	{
		GregorianCalendar c = new GregorianCalendar();
		ArrayList<Task> result= new ArrayList<Task>();
		for(Task t :events)
		{
			Boolean sameDay = new Boolean(false);
			Boolean firstOrLast = new Boolean(false);
			Boolean fill= new Boolean(false);
			if(t.getRepeat()==true)
			{
				System.out.println("INT "+TimeUtils.getIntervalInDays(t.getBeginTimeInGregorian(),t.getEndTimeInGregorian()));
				//return true;
			}
			else
			{
				
				if(TaskUtils.isDueToday(t,sameDay,firstOrLast,fill))
				{
					result.add(t);
				}
			}
		}
		return result;
	}
	private void updateTasksDate(ArrayList<Task> events)
	{
		for(Task t:events)
		{
			if(t.getRepeat()==true)
			{
				//Repeat dupa An
				if(t.getRepeatType()==3)
				{
					if(TimeUtils.isInSameDayIgnoringYear(t.getBeginTimeInGregorian(),new GregorianCalendar()))
					{
						//TODO SET YEAR
					}
				}
			}
		}
	}
	/**
	 * Metoda care filtreaza taskurile in functie de tip si intoarce un ArrayList cu taskurile care respecta criteriul
	 */
	private ArrayList<Task> filterTasks(ArrayList<Task> allTasks, int taskType)
	{
		ArrayList<Task> result = new ArrayList<Task>();
		for(int i=0;i<allTasks.size();++i)
		{
			if(allTasks.get(i).getTaskType()==taskType)
				result.add(allTasks.get(i));
		}
		return result;
	}
	/**
	 * Metoda care filtreaza taskurile in functie de tip si intoarce un ArrayList cu taskurile care respecta criteriul
	 */
	private ArrayList<Task> filterPrio(ArrayList<Task> allTasks)
	{
		ArrayList<Task> result = new ArrayList<Task>();
		for(int i=0;i<allTasks.size();++i)
		{
			if(allTasks.get(i).getTaskType()!=0)
				result.add(allTasks.get(i));
		}
		return result;
	}
	
	private void cleanupTasks(ArrayList<Task> tasks)
	{
		for(Iterator<Task> i=tasks.iterator();i.hasNext();)
		{
			Task t = i.next();
			if(t.getTaskType()==2 && t.getEndTimeInGregorian().before(new GregorianCalendar()))
			{
				i.remove();
			}
		}
	}
	public static void TESTPrintList(ArrayList<Task> tasks)
	{
		for(Task t:tasks)
		{
			System.out.println(t);
		}
	}
	/*
	public static void main(String[] args) 
	{
		ArrayList<Task> taskArr = new ArrayList<Task>();
		
		Task t1 = new Task();
		t1.setBeginDate(2012,10,21);
		t1.setBeginTime(9,0);
		t1.setEndDate(2012,10,22);
		t1.setEndTime(12,0);
		t1.setRepeat(true);
		t1.setRepeatType(1);
		t1.setRepeatInterval(1);
		t1.setFill(true);
		t1.setPriority(9);
		t1.setTotalPriority(9);
		t1.TESTID=1;
		
		Task t2 = new Task();
		t2.setBeginDate(2012,10,27);
		t2.setBeginTime(12,0);
		t2.setEndDate(2012,10,27);
		t2.setEndTime(14,0);
		t2.setRepeat(false);
		t2.setRepeatType(1);
		t2.setRepeatInterval(1);
		t2.setFill(true);
		t2.setPriority(6);
		t2.setTotalPriority(6);
		t2.TESTID=2;
		
		Task t3 = new Task();
		t3.setBeginDate(2012,10,17);
		t3.setBeginTime(0,0);
		t3.setEndDate(2012,10,26);
		t3.setEndTime(12,16);
		t3.setPriority(7);
		t3.setTotalPriority(7);
		t3.TESTID=3;
		
		Task t4 = new Task();
		t4.setBeginDate(2012,10,17);
		t4.setBeginTime(0,0);
		t4.setEndDate(2012,10,26);
		t4.setEndTime(14,30);
		t4.setPriority(9);
		t4.setTotalPriority(9);
		t4.TESTID=4;
		
		taskArr.add(t1);
		taskArr.add(t2);
		taskArr.add(t3);
		taskArr.add(t4);

		Algorithm al = new Algorithm();
		
		//System.out.println(TimeUtils.isInSameDay(new GregorianCalendar(),t4.getEndTimeInGregorian()));
		TESTPrintList(al.organizeDay(taskArr));
		//al.organizePrio(taskArr, 10);
		//System.out.println(TimeUtils.getIntervalInHours(new GregorianCalendar(), t3.getEndTimeInGregorian()));
	}
	*/

}
