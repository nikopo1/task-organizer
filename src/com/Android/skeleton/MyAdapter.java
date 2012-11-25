package com.Android.skeleton;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter {

	private LayoutInflater mInflater;
	private ArrayList<Task> taskArray = new ArrayList<Task>();

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
			holder.text = (TextView) convertView.findViewById(R.id.TimeInterval);
			holder.text2 = (TextView) convertView.findViewById(R.id.Title);

			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		Task t = taskArray.get(position);
		
		String interval = t.getBeginTimeHour()+":"+t.getBeginTimeMinute()+" - "+
			t.getEndTimeHour()+":"+t.getEndTimeMinute();

		holder.text.setText(interval);
		holder.text2.setText(t.getTitle());

		return convertView;
	}

	static class ViewHolder {
		TextView text;
		TextView text2;
	}
}
