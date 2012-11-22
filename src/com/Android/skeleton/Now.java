package com.Android.skeleton;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;


import java.util.Date;
public class Now extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {
	TableLayout priority;
	@Override
	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		TabHost tabs = (TabHost)findViewById(R.id.tabhost);
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
		tabs.setCurrentTab(1);
		
		
		priority = (TableLayout)findViewById(R.id.prio);
		TableRow row;
		
		
	}
	
	
	@Override
	public void onCheckedChanged(CompoundButton arg0, boolean arg1) {

	}


	@Override
	public void onClick(View v) {
		
	}
}