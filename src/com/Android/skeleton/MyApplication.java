package com.Android.skeleton;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;

import android.content.Context;


public class MyApplication {
	private static final int MAX_NUMBER=10;
	
	public static ArrayList<Task> tasks = new ArrayList<Task>();
	private static Algorithm alg = new Algorithm();
	
	public static ArrayList<Task> priotasks = new ArrayList<Task>();
	public static MyAdapter prioadapter;
	
	public static ArrayList<Task> alltasks = new ArrayList<Task>();
	public static MyAdapter alladapter;
	
	public static ArrayList<Task> daytasks = new ArrayList<Task>();
	public static MyAdapter dayadapter;
	
	public static void init()
	{	
		//priotasks.clear();
		alg.organizePrio(tasks, MAX_NUMBER, priotasks);
		alg.organizeDay(tasks, daytasks);
		

		prioadapter.notifyDataSetChanged();
		dayadapter.notifyDataSetChanged();
		
	}
	
	public static void addTask(Task t) {
		

		tasks.add(t);
		
		try {
			PrintWriter pw = new PrintWriter(Now.now.openFileOutput("data.tm", Context.MODE_APPEND));
			pw.println(t.getTitle());
			pw.println(t.getAllDay());
			pw.println(t.getRepeat());
			pw.println(t.getDescription());
			pw.println(t.getLocation());
			pw.println(t.getPriority());
			pw.println(t.getTimePriority());
			pw.println(t.getTotalPriority());
			pw.println(t.getConsiderPriority());
			pw.println(t.getConsiderTime());
			
			pw.println(t.getBeginDateYear());
			pw.println(t.getBeginDateMonth());
			pw.println(t.getBeginDateDay());
			
			pw.println(t.getEndDateYear());
			pw.println(t.getEndDateMonth());
			pw.println(t.getEndDateDay());
			
			pw.println(t.getBeginTimeHour());
			pw.println(t.getBeginTimeMinute());
			
			pw.println(t.getEndTimeHour());
			pw.println(t.getEndTimeMinute());
			
			pw.println(t.getRepeatType());
			pw.println(t.getRepeatInterval());
			pw.println(t.getFill());
			pw.println(t.getTaskType());
			
			
			
			
			pw.flush();
			pw.close(); 
			
		} catch (FileNotFoundException e)
		{
			
		} catch (IOException e) {
		}
	
		try {
			DataOutputStream oos = new DataOutputStream(Now.now.openFileOutput("taskNo.tm",Context.MODE_PRIVATE));
			oos.writeInt(tasks.size());
			oos.flush();
			oos.close();
		} catch (IOException e) {
		}
		  
		/*
		try {
			DataInputStream oos = new DataInputStream(Now.now.openFileInput("taskNo.tm"));
			size = oos.readInt();
			oos.close();
		} catch (IOException e) {
		}
		tasks.clear();
		//t.setTitle(t.getTitle() + " " + size);
		//tasks.add(t);
		 
		try {
			BufferedReader ois = new BufferedReader(new InputStreamReader(Now.now.openFileInput("data.tm")));
			 //MyApplication.tasks.add((Task)ois.readObject());
			for(int i = 0; i < size; i++)
			{
				Task tt = new Task();
				tt.setTitle(ois.readLine());
				tt.setAllDay(Boolean.parseBoolean(ois.readLine()));
				tt.setRepeat(Boolean.parseBoolean(ois.readLine()));
				tt.setDescirption(ois.readLine());
				tt.setLocation(ois.readLine());
				tt.setPriority(Integer.parseInt(ois.readLine()));
				tt.setTimePriority((Float.parseFloat(ois.readLine())));
				tt.setTotalPriority((Float.parseFloat(ois.readLine())));
				tt.setConsiderPriority(Boolean.parseBoolean(ois.readLine()));
				tt.setConsiderTime(Boolean.parseBoolean(ois.readLine()));
				tt.setBeginDate(
						Integer.parseInt(ois.readLine()), 
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				tt.setEndDate(
						Integer.parseInt(ois.readLine()), 
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				tt.setBeginTime(
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				tt.setEndTime(
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				tt.setRepeatType(Integer.parseInt(ois.readLine()));
				tt.setRepeatInterval(Integer.parseInt(ois.readLine()));
				tt.setFill(Boolean.parseBoolean(ois.readLine()));
				tt.setTaskType(Integer.parseInt(ois.readLine()));
				tasks.add(tt);
			}
			
		} catch (IOException e) {
			tasks.get(0).setTitle("error" + " " + size + e.getMessage());  
			System.out.println(e.getMessage());
		} 
	 	*/
	
		/*
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
		t1.setTitle("blabla");
		t1.setAllDay(true);
		*/
		
		
		alg.organizePrio(tasks, MAX_NUMBER, priotasks);
		alg.organizeDay(tasks, daytasks);

		
		//priotasks.add(t1);
		
		prioadapter.notifyDataSetChanged();
		dayadapter.notifyDataSetChanged();
		
		//alltasks = alg.organizeAll(tasks);
		//alladapter.notifyDataSetChanged();
	}
}
