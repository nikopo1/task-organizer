package com.Android.skeleton;

import java.util.ArrayList;


public class MyApplication {
	private static final int MAX_NUMBER=10;
	
	public static ArrayList<Task> tasks = new ArrayList<Task>();
	private static Algorithm alg = new Algorithm();
	
	public static ArrayList<Task> priotasks = new ArrayList<Task>();
	public static MyAdapter prioadapter;
	
	public static ArrayList<Task> alltasks = new ArrayList<Task>();
	public static MyAdapter alladapter;
	
	public static void addTask(Task t) {
		
		tasks.add(t);
		
		alg.organizePrio(tasks, MAX_NUMBER, priotasks);
		
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
		
		priotasks.add(t1);
		
		prioadapter.notifyDataSetChanged();
		
		//alltasks = alg.organizeAll(tasks);
		//alladapter.notifyDataSetChanged();
	}
}
