package com.Android.skeleton;

public class ProjectWrapper 
{
	private Task t;
	private int minutesAssigned;
	private int soFar;
	//indexul evenimentului cand acest proiect a inceput sa fie programat
	private int eventIndexOnStart;
	
	public ProjectWrapper(Task task, int minutesAssigned)
	{
		this.t=task;
		this.minutesAssigned=minutesAssigned;
		this.soFar=0;
	}
	public void incrementSoFar()
	{
		soFar++;
	}
	public boolean finishedAssigning()
	{
		if(soFar==minutesAssigned)
			return true;
		else
			return false;
	}
	public Task getTask() {
		return t;
	}
	public void setTask(Task t) {
		this.t = t;
	}
	public int getMinutesAssigned() {
		return minutesAssigned;
	}
	public void setMinutesAssigned(int minutesAssigned) {
		this.minutesAssigned = minutesAssigned;
	}
	public int getSoFar() {
		return soFar;
	}
	public void setSoFar(int soFar) {
		this.soFar = soFar;
	}
	public int getEventIndexOnStart() {
		return eventIndexOnStart;
	}
	public void setEventIndexOnStart(int eventIndexOnStart) {
		this.eventIndexOnStart = eventIndexOnStart;
	}
}
