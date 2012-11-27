package com.Android.skeleton;

public class TaskWrapper 
{
	private Task t;
	private boolean sameDay;
	private boolean firstOrLast;
	private boolean fill;
	
	
	public TaskWrapper(Task t,boolean sameDay,boolean firstOrLast,boolean fill)
	{
		this.t=t;
		this.sameDay=sameDay;
		this.firstOrLast=firstOrLast;
		this.fill=fill;
	}
	
	public Task getTask() {
		return t;
	}
	public void setTask(Task t) {
		this.t = t;
	}
	public boolean isSameDay() {
		return sameDay;
	}
	public void setSameDay(boolean sameDay) {
		this.sameDay = sameDay;
	}
	public boolean isFirstOrLast() {
		return firstOrLast;
	}
	public void setFirstOrLast(boolean firstOrLast) {
		this.firstOrLast = firstOrLast;
	}
	public boolean isFill() {
		return fill;
	}
	public void setFill(boolean fill) {
		this.fill = fill;
	}
	
}
