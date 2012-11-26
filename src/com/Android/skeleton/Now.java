package com.Android.skeleton;

import java.util.ArrayList;

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

	public static final int MOVE = 120;
	
	TabHost tabs;
	ListView listviewPrio;
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
		
		//Adaugare listener pentru cand vrem sa adaugam un task now
		tabs.getTabWidget().getChildAt(0).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(Now.this, AddTask.class);
				 startActivity(intent);
				 Toast.makeText(getApplicationContext(), "Back to square one", Toast.LENGTH_SHORT).show();
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

		Task t;
		/*
		for(int i = 0; i < 20; i++) {
			t = new Task();
			t.setBeginTime( i%24, 30);
			t.setEndTime( (i+2)%24, 45);
			t.setTitle("Task "+i);
			tasks.add(t);
		}
		*/
		

		listviewPrio = (ListView)findViewById(R.id.tab2);
		MyApplication.prioadapter = new MyAdapter(this, MyApplication.priotasks);
		listviewPrio.setAdapter(MyApplication.prioadapter);
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