package com.Android.skeleton;
import java.util.Comparator;


public class FullComparator implements Comparator {

	@Override
	public int compare(Object arg0, Object arg1) {
		Task t1 = (Task)arg0;
		Task t2 = (Task)arg1;
		if(t2.getTotalPriority()-t1.getTotalPriority()==0)
			return 0;
		else if(t2.getTotalPriority()-t1.getTotalPriority()>0)
			return 1;
		else 
			return -1;
	}

}
