package com.Android.skeleton;

import java.util.Comparator;

public class EventComparatorByStartTime implements Comparator
{
	@Override
	public int compare(Object arg0, Object arg1) {
		Task t1 = (Task)arg0;
		Task t2 = (Task)arg1;
		
		int sum1 = t1.getBeginTimeHour()*60+t1.getBeginTimeMinute();
		int sum2 = t2.getBeginTimeHour()*60+t2.getBeginTimeMinute();
		
		return sum1-sum2;
	}
}
