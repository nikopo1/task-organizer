package com.Android.skeleton;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import com.Android.skeleton.SelectFilterActivity.MyGestureDetector;

public class Now extends Activity implements OnClickListener {

	private static final int SWIPE_MIN_DISTANCE = 120;
	private static final int SWIPE_MAX_OFF_PATH = 250;
	private static final int SWIPE_THRESHOLD_VELOCITY = 200;
	public GestureDetector gestureDetector;
	public View.OnTouchListener gestureListener;


	ListView listview;
	ArrayList<String> elems;
	ArrayList<Task> tasks;
	int currenttab = 1;
	TabHost tabs;

	class MyGestureDetector extends SimpleOnGestureListener {
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			try {
				if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
					return false;
				// right to left swipe
				if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					//Toast.makeText(Now.this, "Left Swipe", Toast.LENGTH_SHORT).show();
					currenttab = (currenttab+1)%4;
					tabs.setCurrentTab(currenttab);
				}  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					//Toast.makeText(Now.this, "Right Swipe", Toast.LENGTH_SHORT).show();
					currenttab = (currenttab-1)%4;
					tabs.setCurrentTab(currenttab);
					
				}
			} catch (Exception e) {
				// nothing
			}
			return false;
		}

	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub

	}

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

		listview = (ListView)findViewById(R.id.tab2);

		MyAdapter adapter = new MyAdapter(this, tasks);
		listview.setAdapter(adapter);

		gestureDetector = new GestureDetector((OnGestureListener) new MyGestureDetector());
		gestureListener = new View.OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return gestureDetector.onTouchEvent(event);
			}
		};
		
		listview.setOnTouchListener(gestureListener);
	}
}