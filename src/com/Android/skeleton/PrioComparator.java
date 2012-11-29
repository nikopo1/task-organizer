package com.Android.skeleton;
import java.util.Comparator;

public class PrioComparator implements Comparator
{
	@Override
	public int compare(Object arg0, Object arg1) {
		Task t1 = (Task)arg0;
		Task t2 = (Task)arg1;
		return t2.getPriority()-t1.getPriority();
	}
}
