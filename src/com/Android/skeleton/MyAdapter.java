package com.Android.skeleton;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Task> taskArray = new ArrayList<Task>();
	public boolean allDayEnable = false; 
	
	private int[] colors = {
			 Color.rgb(255, 218, 185),
			 Color.rgb(255, 203, 164),
			 Color.rgb(255, 189, 136),
			 Color.rgb(244, 164, 96),
			 Color.rgb(255, 127, 80)
	};
	
	public MyAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}
	
	public MyAdapter(Context context, ArrayList<Task> taskArray) {
		mInflater = LayoutInflater.from(context);
		this.taskArray = taskArray;
	}
	
	public void setArray(ArrayList<Task> taskArray) {
		this.taskArray = taskArray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return taskArray.size();
	}

	@Override
	public Object getItem(int position) {
		return taskArray.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.listviewitem, null);
			holder = new ViewHolder();
			holder.timeinterval = (TextView) convertView.findViewById(R.id.TimeInterval);
			holder.title = (TextView) convertView.findViewById(R.id.Title);
			holder.location = (TextView) convertView.findViewById(R.id.Location);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Task t = taskArray.get(position);
		
		
		String interval = getHourMinuteString(t.getBeginTimeHour(),t.getBeginTimeMinute())+" - "+
			getHourMinuteString(t.getEndTimeHour(),t.getEndTimeMinute());

		holder.timeinterval.setText(interval);
		holder.timeinterval.setTextColor(Color.GRAY);
		
		holder.title.setText(t.getTitle());
		holder.title.setTextColor(Color.LTGRAY);
		
		holder.location.setText(t.getLocation());
		holder.location.setTextColor(Color.GRAY);
		

		if(allDayEnable && t.getAllDay()) {
			int textcolor = Color.rgb(255, 127, 80);
			
			holder.timeinterval.setTextColor(textcolor);
			holder.title.setTextColor(textcolor);
			holder.location.setTextColor(textcolor);
			convertView.setBackgroundColor(Color.DKGRAY);
		}
		else {
			int textcolor = colors[t.getPriority()-1];

			holder.timeinterval.setTextColor(textcolor);
			holder.title.setTextColor(textcolor);
			holder.location.setTextColor(textcolor);
			convertView.setBackgroundColor(Color.BLACK);
		}
		
		return convertView;
	}
	
	private String getHourMinuteString(int hour, int minute) {
		String res = "";
		if(hour<10)
			res += "0"+hour;
		else
			res += hour;
		
		res += ":";
		if(minute<10)
			res += "0"+minute;
		else
			res += minute;
		return res;
	}

	static class ViewHolder {
		TextView timeinterval;
		TextView title;
		TextView location;
	}
}
