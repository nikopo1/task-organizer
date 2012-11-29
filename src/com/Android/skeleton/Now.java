package com.Android.skeleton;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;






public class Now extends Activity implements OnGestureListener {

	class TaskNo implements Serializable{
		public static final long serialVersionUID = 42L;
		public int taskNo;
		
		TaskNo(int _taskNo)
		{
			taskNo = _taskNo;
		}
	}
	
	public static Now now;
	public static final int MOVE = 120;
	public static ObjectOutputStream oos_tasks;
	public static ObjectOutputStream oos_taskNo;
	
	TabHost tabs;
	ListView listviewPrio;
	ListView listviewDay;
	ListView listviewAll;
	int currenttab = 1;
	


	@Override
	public void onCreate(Bundle icicle) {
		now = this;
		super.onCreate(icicle);
		setContentView(R.layout.main);

		oos_tasks = null;
		oos_taskNo = null;
		 
		
		
		int taskNo = 0; 
		try {
			DataInputStream ois = new DataInputStream(openFileInput("taskNo.tm"));
			taskNo = ois.readInt();
			ois.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		
		try {
			BufferedReader ois = new BufferedReader(new InputStreamReader(openFileInput("data.tm")));
			 //MyApplication.tasks.add((Task)ois.readObject());
			MyApplication.tasks.clear();
			for(int i = 0; i < taskNo; i++)
			{
				Task t = new Task();
				t.setTitle(ois.readLine());
				t.setAllDay(Boolean.parseBoolean(ois.readLine()));
				t.setRepeat(Boolean.parseBoolean(ois.readLine()));
				t.setDescirption(ois.readLine());
				t.setLocation(ois.readLine());
				t.setPriority(Integer.parseInt(ois.readLine()));
				t.setTimePriority((Float.parseFloat(ois.readLine())));
				t.setTotalPriority((Float.parseFloat(ois.readLine())));
				t.setConsiderPriority(Boolean.parseBoolean(ois.readLine()));
				t.setConsiderTime(Boolean.parseBoolean(ois.readLine()));
				t.setBeginDate(
						Integer.parseInt(ois.readLine()), 
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				t.setEndDate(
						Integer.parseInt(ois.readLine()), 
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				t.setBeginTime(
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				t.setEndTime(
						Integer.parseInt(ois.readLine()),
						Integer.parseInt(ois.readLine())
						);
				t.setRepeatType(Integer.parseInt(ois.readLine()));
				t.setRepeatInterval(Integer.parseInt(ois.readLine()));
				t.setFill(Boolean.parseBoolean(ois.readLine()));
				// in functie de campurile completate mai sus se completeaza automat task type
				// t.setTaskType(Integer.parseInt(ois.readLine()));
				
				MyApplication.tasks.add(t);
			}
			
			ois.close();
		}catch(Exception e)
		{
		}
		
		/*
		try {
			oos_tasks = new ObjectOutputStream(openFileOutput("data.tm",Context.MODE_APPEND));
		} catch (Exception e) {
			oos_tasks = null;
		}		
		
		try {
			oos_taskNo = new ObjectOutputStream(openFileOutput("taskNo.tm",Context.MODE_PRIVATE));
		} catch (Exception e) {
			oos_taskNo = null;
		}
		*/
		
		tabs = (TabHost)findViewById(R.id.tabhost);
		tabs.setup();
		//Tabul de add
		TabHost.TabSpec spec = tabs.newTabSpec("tag1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Add");
		tabs.addTab(spec);
		
		//Adaugare listener pentru cand vrem sa adaugam un task now
		tabs.getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(Now.this, AddTask.class);
				 startActivity(intent);
				 //Toast.makeText(getApplicationContext(), "Back to square one", Toast.LENGTH_SHORT).show();
			}
		});
		
		
		//Tabul de priority
		spec=tabs.newTabSpec("tag2");
		spec.setContent(R.id.tab2);
		spec.setIndicator("Prio");
		tabs.addTab(spec);
		//Tabul de day
		spec=tabs.newTabSpec("tag3");
		spec.setContent(R.id.tab3);
		spec.setIndicator("Day");
		tabs.addTab(spec);
		//Tabul de all
		spec=tabs.newTabSpec("tag4");
		spec.setContent(R.id.tab4);
		spec.setIndicator("All");
		tabs.addTab(spec);
		//Default incepem pe priority
		tabs.setCurrentTab(currenttab);
		

		listviewPrio = (ListView)findViewById(R.id.tab2);
		MyApplication.prioadapter = new MyAdapter(this, MyApplication.priotasks);
		listviewPrio.setAdapter(MyApplication.prioadapter);
		
		listviewDay = (ListView)findViewById(R.id.tab3);
		MyApplication.dayadapter = new MyAdapter(this, MyApplication.daytasks);
		MyApplication.dayadapter.allDayEnable = true;
		listviewDay.setAdapter(MyApplication.dayadapter);
		
		listviewAll = (ListView)findViewById(R.id.tab4);
		MyApplication.alladapter = new MyAdapter(this, MyApplication.alltasks);
		listviewAll.setAdapter(MyApplication.alladapter); 
		
		
		MyApplication.init();
	}


	@Override
	public boolean onDown(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float arg2,
			float arg3) {
		
		if(e2.getX() - e1.getX() > MOVE){
            int id = listviewPrio.pointToPosition((int) e1.getX(), (int) e1.getY());
            Toast.makeText(getApplicationContext(), "id="+id, Toast.LENGTH_SHORT).show();
            //Reminder temp = (Reminder) adapter.getItem((id));
            return true;
        }

        return false;
	}

	@Override
	public void onLongPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onScroll(MotionEvent arg0, MotionEvent arg1, float arg2,
			float arg3) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		
		return false;
	}
	
	
	@Override
	protected void onStop()
	{
		super.onStop();
		/*
		if(oos_tasks != null)
		{
			
			try {
				oos_tasks.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		if(oos_taskNo != null)
		{
			
			try {
				oos_taskNo.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	*/
	}
	
	
	
	
	
	
}