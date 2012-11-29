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
	/*KNOWN BUGS
	 * Taskurile nu se suprapun
	 * Repetitie zilnica cu durata de 2 zile
	*/
	public static boolean CONSIDER_TIME=false;
	public static int PROGRAMMED_DAY_TASK_NUMBER=3;
	public static int FROM_HOUR_OF_DAY=8;
	
	public ArrayList<Task> getAllDayDisplayTasks(ArrayList<Task> allTasks)
	{
		return filterTasks(allTasks,0);
	}
	public void organizeDay(ArrayList<Task> allTasks,ArrayList<Task> result)
	{
		cleanupTasks(allTasks);
		result.clear();
		
		ArrayList<Task> eventsResult = new ArrayList<Task>();
		ArrayList<Task> events = filterTasks(allTasks,1);
		ArrayList<Task> projects = filterTasks(allTasks,2);	
		
		
		ArrayList<TaskWrapper> todaysTasks = getTodaysTasks(events);
		ArrayList<Integer> day = new ArrayList<Integer>();
		initializeArrayList(day);
		//printArrayList(day);
		for(TaskWrapper tw: todaysTasks)
		{
			Task t= tw.getTask();
			//daca un task umple toata ziua
			if(tw.isFill())
			{
				result.add(t);
				setArray(day,0, day.size());
				return;
			}
			//in interiorul zilei
			else if(tw.isSameDay())
			{
				setArray(day,t.getBeginTimeHour()*60+t.getBeginTimeMinute(),t.getEndTimeHour()*60+t.getEndTimeMinute());
			}
			//la inceputul zilei
			else if(tw.isFirstOrLast()==false && tw.isSameDay()==false)
			{
				setArray(day,t.getBeginTimeHour()*60+t.getBeginTimeMinute(),day.size());
			}
			else if(tw.isFirstOrLast()==true && tw.isSameDay()==false)
			{
				setArray(day,0,t.getEndTimeHour()*60+t.getEndTimeMinute());
			}
				
			eventsResult.add(t);
		}
		
		Collections.sort(eventsResult,new EventComparatorByStartTime());
		projects = internalOrganizeProjects(projects);
		ArrayList<ProjectWrapper> programableProjects = new ArrayList<ProjectWrapper>();
		
		float prioSum=getPrioSum(projects);
		int freeTime = getFreeTime(day);
		for(Task t:projects)
		{
			if(CONSIDER_TIME)
				programableProjects.add(new ProjectWrapper(t,(int)(t.getTotalPriority()*freeTime/prioSum)));
			else
				programableProjects.add(new ProjectWrapper(t,(int)(t.getPriority()*freeTime/prioSum)));
		}
		System.out.println("FREE TIME: "+getFreeTime(day));

		
		programmDayTasks(day,eventsResult,programableProjects,result);
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

	public void organizeAll(ArrayList<Task> allTasks,ArrayList<Task> result)
	{
		organizePrio(allTasks,Integer.MAX_VALUE,result);
	}
	private ArrayList<Task> internalOrganizeProjects(ArrayList<Task> projects)
	{
		ArrayList<Task> result = new ArrayList<Task>();
		if(CONSIDER_TIME==false)
		{
			Collections.sort(projects,new PrioComparator());
			for(int i=0;i<Math.min(PROGRAMMED_DAY_TASK_NUMBER,projects.size());++i)
			{
				result.add(projects.get(i));
			}
		}
		else
		{
			TaskUtils.calculateTimePriority(projects);
			TaskUtils.calculateTotalPriority(projects);
			Collections.sort(projects,new FullComparator());
			for(int i=0;i<Math.min(PROGRAMMED_DAY_TASK_NUMBER,projects.size());++i)
			{
				result.add(projects.get(i));
			}
		}
		return result;
	}
	private ArrayList<TaskWrapper> getTodaysTasks(ArrayList<Task> events)
	{
		updateTasksDate(events);
		GregorianCalendar c = new GregorianCalendar();
		ArrayList<TaskWrapper> result= new ArrayList<TaskWrapper>();
		for(Task t :events)
		{
			TaskWrapper tw = new TaskWrapper(t,false,false,false);

			if(TaskUtils.isDueToday(tw))
			{
				result.add(tw);
			}
		}
		return result;
	}
	public void getBirthdays(ArrayList<Task> allTasks,ArrayList<Task> result)
	{
		GregorianCalendar c = new GregorianCalendar();
		ArrayList<Task> allDay = filterTasks(allTasks,0);
		result.clear();
		for(Task t :allDay)
		{
			TaskWrapper tw = new TaskWrapper(t,false,false,false);

			if(TaskUtils.isDueToday(tw))
			{
				result.add(t);
			}
		}
	}
 	private void updateTasksDate(ArrayList<Task> events)
	{
		GregorianCalendar now= new GregorianCalendar();
		for(Task t:events)
		{
			if(t.getRepeat()==true)
			{
				if(TimeUtils.isInSameDay(t.getBeginTimeInGregorian(),now))
					continue;
				//Repeat dupa An
				if(t.getRepeatType()==3)
				{	
					//daca este ziua portivita
					if(TimeUtils.isInSameDayIgnoringYear(t.getBeginTimeInGregorian(),now))
					{
						
						//daca se indeplineste si intervalul de repeat
						if((now.get(Calendar.YEAR)-t.getBeginTimeInGregorian().get(Calendar.YEAR))%t.getRepeatInterval()==0)
						{
							int yearDifference = now.get(Calendar.YEAR)-t.getBeginTimeInGregorian().get(Calendar.YEAR);
							t.setBeginDateYear(t.getBeginDateYear()+yearDifference);
							t.setEndDateYear(t.getEndDateYear()+yearDifference);
							
							//System.out.println(t.getEndTimeInGregorian().getTime().toString());
						}
					}
				}
				else if(t.getRepeatType()==2)
				{
					GregorianCalendar taskTime = t.getBeginTimeInGregorian();
					//daca este in ziua din luna portivita
					if(taskTime.get(Calendar.DAY_OF_MONTH)==now.get(Calendar.DAY_OF_MONTH))
					{
						
						//daca se indeplineste si intervalul de repeat
						while(TimeUtils.isBeforeOrEqConsideringYMD(taskTime,now))
						{
							taskTime.add(Calendar.MONTH,t.getRepeatInterval());
							if(TimeUtils.isInSameDayIgnoringYear(taskTime,now))
							{
								GregorianCalendar endTaskTime = t.getEndTimeInGregorian();
								endTaskTime.add(Calendar.MONTH,t.getRepeatInterval());
								
								t.setBeginDateYear(taskTime.get(Calendar.YEAR));
								t.setBeginDateMonth(taskTime.get(Calendar.MONTH));
								
								t.setEndDateYear(endTaskTime.get(Calendar.YEAR));
								t.setEndDateMonth(endTaskTime.get(Calendar.YEAR));
							}
						}
					}
				}
				//daca se repeta saptamanal
				else if(t.getRepeatType()==1)
				{
					GregorianCalendar taskTime = t.getBeginTimeInGregorian();
					float tDays = TimeUtils.getDaysFromStart(taskTime);
					float nDays = TimeUtils.getDaysFromStart(now);
					float difference = nDays-tDays;
					if(difference%(t.getRepeatInterval()*7)==0)
					{
						GregorianCalendar endTaskTime = t.getEndTimeInGregorian();
						
						taskTime.add(Calendar.DAY_OF_MONTH,(int)difference);
						endTaskTime.add(Calendar.DAY_OF_MONTH,(int)difference);
						
						t.setBeginDate(taskTime.get(Calendar.YEAR),taskTime.get(Calendar.MONTH),taskTime.get(Calendar.DAY_OF_MONTH));
						t.setEndDate(endTaskTime.get(Calendar.YEAR),endTaskTime.get(Calendar.MONTH),endTaskTime.get(Calendar.DAY_OF_MONTH));
					}
				}
				//daca se repeta zilnic
				else if(t.getRepeatType()==0)
				{
					GregorianCalendar taskTime = t.getBeginTimeInGregorian();
					float tDays = TimeUtils.getDaysFromStart(taskTime);
					float nDays = TimeUtils.getDaysFromStart(now);
					float difference = nDays-tDays;
					if(difference%(t.getRepeatInterval())==0)
					{
						GregorianCalendar endTaskTime = t.getEndTimeInGregorian();
						
						taskTime.add(Calendar.DAY_OF_MONTH,(int)difference);
						endTaskTime.add(Calendar.DAY_OF_MONTH,(int)difference);
						
						t.setBeginDate(taskTime.get(Calendar.YEAR),taskTime.get(Calendar.MONTH),taskTime.get(Calendar.DAY_OF_MONTH));
						t.setEndDate(endTaskTime.get(Calendar.YEAR),endTaskTime.get(Calendar.MONTH),endTaskTime.get(Calendar.DAY_OF_MONTH));
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
	private void programmDayTasks(ArrayList<Integer> day,ArrayList<Task> events, ArrayList<ProjectWrapper> projects,ArrayList<Task> result)
	{
		int eventIndex=0;
		int projectIndex=0;
		int huntingFor=1;
		int changedEvent=0;
		for(int i=0;i<day.size();i++)
		{
			//daca sunt in intervalul in care il las si pe el sa se odihneasca()
			if(i<FROM_HOUR_OF_DAY*60)
			{
				if(day.get(i)==0)
				{
					if(huntingFor==1)
						continue;
					else
					{
						//s-a terminat evenimentul pe care am mers
						eventIndex++;
						huntingFor=1;
					}
						
				}
				else if(day.get(i)==1)
				{
					if(huntingFor==0)
						continue;
					else
					{
						result.add(events.get(eventIndex));
						huntingFor=0;
					}
					
				}
			}
			//daca ii programez taskuri de ii sar capacele
			else
			{
				if(day.get(i)==0)
				{
					if(huntingFor==1)
					{
						//nu am gasit inca eveniment, dar pot sa ii programez project
						//(daca nu a fost programat proiectul sau nu i s-a terminat cuanta pentru ca a fost programat alt event) 
						if(projects.get(projectIndex).getSoFar()==0 || projects.get(projectIndex).getEventIndexOnStart()!=eventIndex)
						{
							// prima oara cand pun proiectul
							result.add(projects.get(projectIndex).getTask());
							projects.get(projectIndex).incrementSoFar();
							projects.get(projectIndex).setEventIndexOnStart(eventIndex);
						}
						else
						{
							//daca am terminat de asignat
							if(projects.get(projectIndex).finishedAssigning())
							{
								projectIndex++;
							}
							else
							{
								projects.get(projectIndex).incrementSoFar();
							}
						}
					}
					else
					{
						//s-a terminat evenimentul pe care am mers
						eventIndex++;
						huntingFor=1;
					}
						
				}
				else if(day.get(i)==1)
				{
					if(huntingFor==0)
					{
						continue;
					}
					else
					{
						result.add(events.get(eventIndex));
						huntingFor=0;
					}
					
				}
			}
		}
	}
	private float getPrioSum(ArrayList<Task> projects)
	{
		float sum =0;
		for(Task t:projects)
		{
			if(CONSIDER_TIME)
				sum+=t.getTotalPriority();
			else
				sum+=t.getPriority();
		}
		return sum;
	}
	private void cleanupTasks(ArrayList<Task> tasks)
	{
		for(Iterator<Task> i=tasks.iterator();i.hasNext();)
		{
			Task t = i.next();
			if(t.getTaskType()==2 && t.getEndTimeInGregorian().before(new GregorianCalendar()))
			{
				System.out.println("REM: "+t);
				i.remove();
			}
		}
	}
	private void initializeArrayList(ArrayList<Integer> day)
	{
		for(int i=0;i<24*60;i++)
		{
			day.add(0);
		}
	}
	private void setArray(ArrayList<Integer> day,int startIndex, int endIndex)
	{
		for(int i=startIndex;i<endIndex;i++)
			day.set(i,1);
	}
	private int computeSum(ArrayList<Integer> day)
	{
		int sum=0;
		for(Integer i:day)
			sum+=i;
		return sum;
	}
	private int getFreeTime(ArrayList<Integer> day)
	{
		int occupied=0;
		for(int i=FROM_HOUR_OF_DAY*60;i<24*60;i++)
		{
			occupied+=day.get(i);
		}
		return (24-FROM_HOUR_OF_DAY)*60-occupied;
	}
	private void printArrayList(ArrayList<Integer> day)
	{
		for(Integer hour:day)
		{
			System.out.print(hour);
		}
		System.out.println();
	}
	
	public static void TESTPrintList(ArrayList<Task> tasks)
	{
		for(Task t:tasks)
		{
			System.out.println(t);
		}
	}
	
	public static void main(String[] args) 
	{
		ArrayList<Task> taskArr = new ArrayList<Task>();
		
		Task t1 = new Task();
		t1.setBeginDate(2012,10,27);
		t1.setBeginTime(9,0);
		t1.setEndDate(2012,10,27);
		t1.setEndTime(12,0);
		t1.setRepeat(true);
		t1.setRepeatType(0);
		t1.setRepeatInterval(3);
		t1.setFill(true);
		t1.setPriority(9);
		//t1.setTotalPriority(9);
		t1.TESTID=1;
		
		Task t2 = new Task();
		t2.setBeginDate(2012,10,27);
		t2.setBeginTime(14,0);
		t2.setEndDate(2012,10,27);
		t2.setEndTime(16,0);
		t2.setRepeat(false);
		t2.setRepeatType(1);
		t2.setRepeatInterval(1);
		t2.setFill(true);
		t2.setPriority(6);
		//t2.setTotalPriority(6);
		t2.TESTID=2;
		
		Task t3 = new Task();
		t3.setBeginDate(2012,10,27);
		t3.setBeginTime(16,30);
		t3.setEndDate(2012,10,27);
		t3.setEndTime(17,0);
		t3.setRepeat(false);
		t3.setRepeatType(1);
		t3.setRepeatInterval(1);
		t3.setFill(true);
		t3.setPriority(6);
		//t2.setTotalPriority(6);
		t3.TESTID=3;
		
		Task t4 = new Task();
		t4.setBeginDate(2012,10,17);
		t4.setBeginTime(0,0);
		t4.setEndDate(2012,10,30);
		t4.setEndTime(12,16);
		t4.setPriority(7);
		t4.setTotalPriority(7);
		t4.TESTID=4;
		
		Task t5 = new Task();
		t5.setBeginDate(2012,10,17);
		t5.setBeginTime(0,0);
		t5.setEndDate(2012,11,28);
		t5.setEndTime(14,30);
		t5.setPriority(9);
		t5.setTotalPriority(9);
		t5.TESTID=5;
		
		Task t6 = new Task();
		t6.setAllDay(true);
		t6.setBeginDate(2012,10,27);
		t6.setBeginTime(0,0);
		t6.setEndDate(2012,10,27);
		t6.setEndTime(14,30);
		t6.setPriority(9);
		t6.setTotalPriority(9);
		t6.TESTID=6;

		taskArr.add(t1);
		taskArr.add(t2);
		taskArr.add(t3);
		taskArr.add(t4);
		taskArr.add(t5);
		taskArr.add(t6);
		
		Algorithm al = new Algorithm();
		ArrayList<Task> results = new ArrayList<Task>();
		
		//GregorianCalendar now = new GregorianCalendar();
		//System.out.println(now.getTime().toString());
		//System.out.println(TimeUtils.isInSameDay(new GregorianCalendar(),t4.getEndTimeInGregorian()));
		//al.organizeDay(taskArr,results);
		al.getBirthdays(taskArr, results);
		TESTPrintList(results);
		//al.organizePrio(taskArr, 10);
		//System.out.println(TimeUtils.getIntervalInHours(new GregorianCalendar(), t3.getEndTimeInGregorian()));
	}
	

}
