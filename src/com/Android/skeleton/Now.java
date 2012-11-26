package com.Android.skeleton;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;


public class Now extends Activity implements OnGestureListener {

	public static final int MOVE = 120;
	
	TabHost tabs;
	ListView listviewPrio;
	ArrayList<Task> tasks;
	int currenttab = 1;




	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);

		tabs = (TabHost)findViewById(R.id.tabhost);
		tabs.setup();
		//Tabul de add
		TabHost.TabSpec spec = tabs.newTabSpec("tag1");
		spec.setContent(R.id.tab1);
		spec.setIndicator("Add");
		tabs.addTab(spec);
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

		tasks = new ArrayList<Task>();
		Task t;

		for(int i = 0; i < 20; i++) {
			t = new Task();
			t.setBeginTime( i%24, 30);
			t.setEndTime( (i+2)%24, 45);
			t.setTitle("Task "+i);
			tasks.add(t);
		}

		listviewPrio = (ListView)findViewById(R.id.tab2);
	//	listview.setOnTouchListener(this);
		
		MyAdapter adapter = new MyAdapter(this, tasks);
		listviewPrio.setAdapter(adapter);
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
		Toast.makeText(getApplicationContext(), "muie", Toast.LENGTH_SHORT).show();
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
}