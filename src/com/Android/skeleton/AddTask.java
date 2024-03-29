package com.Android.skeleton;


import java.util.Calendar;
import android.os.Bundle;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;


public class AddTask extends Activity implements CompoundButton.OnCheckedChangeListener, 
AdapterView.OnItemSelectedListener{

	CheckBox checkBoxAllDay;
	CheckBox checkBoxRepeat;
	CheckBox considerPriority;
	CheckBox considerTime;
	CheckBox checkBoxFill;
	EditText titleField;
	EditText description;
	EditText location;
	Button okButton;
	Button cancelButton;
	Button beginDate;
	Button endDate;
	Button beginTime;
	Button endTime;	
	TextView selection;
	TextView priorityLabel;
	TextView titleLabel;
	TextView errorLabel;
	Task task;

	Spinner repeatType1;
	Spinner repeatType2;
	TextView repeatType1Label;
	TextView repeatType2Label;
	TextView repeatType2After;

	Spinner priority;

	Calendar dateAndTime= Calendar.getInstance();

	OnDateSetListener beginDatePicker = new OnDateSetListener()
	{
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateBeginDate();
		}
	};
	OnDateSetListener endDatePicker = new OnDateSetListener()
	{
		public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth)
		{
			dateAndTime.set(Calendar.YEAR, year);
			dateAndTime.set(Calendar.MONTH, monthOfYear);
			dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
			updateEndDate();
		}
	};
	OnTimeSetListener beginTimePicker = new OnTimeSetListener()
	{
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			updateBeginTime();
		}
	};
	OnTimeSetListener endTimePicker = new OnTimeSetListener()
	{
		public void onTimeSet(TimePicker view, int hourOfDay, int minute)
		{
			dateAndTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
			dateAndTime.set(Calendar.MINUTE, minute);
			updateEndTime();
		}
	};



	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_add_task);

		if (MyApplication.edit)
		{
			task = MyApplication.tasks.get(MyApplication.IDtask);
		/*	priority.setSelection(task.getPriority()-1);
			
			checkBoxFill.setChecked(task.getFill());
			checkBoxAllDay.setChecked(task.getAllDay());
			checkBoxRepeat.setChecked(task.getRepeat());
			
			titleField.setText(task.getTitle());
			description.setText(task.getDescription());
			location.setText(task.getLocation());
			
			if (task.getRepeat())
			{
				repeatType1.setSelection(task.getRepeatType());
				repeatType2.setSelection(task.getRepeatInterval()-1);
				
				
		
				if (task.getRepeatType()==0)
					repeatType2After.setText("zile");
				else if (task.getRepeatType()==1)
					repeatType2After.setText("s�pt�m�ni");
				else if (task.getRepeatType()==2)
					repeatType2After.setText("luni");
				else if (task.getRepeatType()==3)
					repeatType2After.setText("ani");
			}
			*/
			
		}
		else
		{
			task = new Task();
			task.setRepeatInterval(1);
			task.setRepeatType(0);
			task.setPriority(0);
			task.setAllDay(false);
			task.setRepeat(false);
			task.setFill(false);
		}
		
		errorLabel = (TextView)findViewById(R.id.errorLabel);
		checkBoxAllDay = (CheckBox)findViewById(R.id.allDay);
		checkBoxRepeat = (CheckBox)findViewById(R.id.repeat);
		titleField = (EditText)findViewById(R.id.title);
		description = (EditText)findViewById(R.id.description);
		location = (EditText)findViewById(R.id.location);
		okButton = (Button)findViewById(R.id.ok);
		cancelButton = (Button)findViewById(R.id.cancel);
		beginDate = (Button)findViewById(R.id.beginDate);
		endDate = (Button)findViewById(R.id.endDate);
		beginTime = (Button)findViewById(R.id.beginTime);
		endTime = (Button)findViewById(R.id.endTime);
		checkBoxAllDay.setOnCheckedChangeListener(this);
		checkBoxRepeat.setOnCheckedChangeListener(this);
		checkBoxFill = (CheckBox)findViewById(R.id.fill);
		checkBoxFill.setOnCheckedChangeListener(this);
		priorityLabel = (TextView)findViewById(R.id.priorityLabel);
		titleLabel = (TextView)findViewById(R.id.labelTitle);
		repeatType1Label = (TextView)findViewById(R.id.repeatType1Label);
		repeatType2Label = (TextView)findViewById(R.id.repeatType2Label);
		repeatType2After = (TextView)findViewById(R.id.repeatType2After);
		repeatType1 = (Spinner)findViewById(R.id.repeatType1);
		repeatType2 = (Spinner)findViewById(R.id.repeatType2);
		priority = (Spinner)findViewById(R.id.priority);
		priority.setSelection(2);
		repeatType1.setOnItemSelectedListener(this);
		repeatType2.setOnItemSelectedListener(this);
		priority.setOnItemSelectedListener(this);

		okButton.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				readTitle();
				readDescription();
				readLocation();
				if (task.getTitle().equals(""))
				{
					// TODO : ce faci cand nu ai titlul completat
					// TODO: ce alte conditii mai pui
					//titleLabel.setTextColor(Color.RED);
					errorLabel.setVisibility(View.VISIBLE);
					errorLabel.setTextColor(Color.RED);
					//titleField.set
				}
				else
				{
					if (MyApplication.edit)
					{
						
					}
					else
					{
						MyApplication.addTask(task);
					}
					titleLabel.setTextColor(Color.BLACK);
					errorLabel.setVisibility(View.GONE);
					MyApplication.edit=false;
					finish();
					
				}
			}
		}
		);
		cancelButton.setOnClickListener(new Button.OnClickListener()
		{
			public void onClick(View v)
			{
				titleLabel.setTextColor(Color.BLACK);
				errorLabel.setVisibility(View.GONE);
				MyApplication.edit=false;
				finish();
			}
		});


		String beginDateValue, endDateValue, beginTimeValue, endTimeValue;
		if (MyApplication.edit)
		{
			beginDateValue = task.getBeginDateDay()+"."+task.getBeginDateMonth()+"."+task.getBeginDateYear();
			endDateValue = task.getEndDateDay()+"."+task.getEndDateMonth()+"."+task.getEndDateYear();
			
			if (task.getBeginTimeMinute()<10)
				beginTimeValue = task.getBeginTimeHour()+":0"+task.getBeginTimeMinute();
			else
				beginTimeValue = task.getBeginTimeHour()+":"+task.getBeginTimeMinute();
			
			if (task.getEndTimeMinute()<10)
				endTimeValue = task.getEndTimeHour()+":0"+task.getEndTimeMinute();
			else
				endTimeValue = task.getEndTimeHour()+":"+task.getEndTimeMinute();			
		}
		else
		{
			beginDateValue = dateAndTime.get(Calendar.DAY_OF_MONTH)+"."+dateAndTime.get(Calendar.MONTH)+"."+dateAndTime.get(Calendar.YEAR);
			task.setBeginDate(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
		
			
								
			if (dateAndTime.get(Calendar.MINUTE)<10)
				beginTimeValue = dateAndTime.get(Calendar.HOUR_OF_DAY)+":0"+dateAndTime.get(Calendar.MINUTE);
			else
				beginTimeValue = dateAndTime.get(Calendar.HOUR_OF_DAY)+":"+dateAndTime.get(Calendar.MINUTE);
			
			task.setBeginTime(dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE));
			
			if (dateAndTime.get(Calendar.HOUR)==23)
			{
				if (dateAndTime.get(Calendar.MINUTE)<10) 
				{
					endTimeValue="00"+":0"+dateAndTime.get(Calendar.MINUTE);
					task.setEndTime(0, dateAndTime.get(Calendar.MINUTE));
				}
				else
				{
					endTimeValue="00"+":"+dateAndTime.get(Calendar.MINUTE);
					task.setEndTime(0, dateAndTime.get(Calendar.MINUTE));
				}
				
				endDateValue = (dateAndTime.get(Calendar.DAY_OF_MONTH)+1)+"."+dateAndTime.get(Calendar.MONTH)+"."+dateAndTime.get(Calendar.YEAR);
				task.setEndDate(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), (dateAndTime.get(Calendar.DAY_OF_MONTH)+1));
			}
			else
			{
				if (dateAndTime.get(Calendar.MINUTE)<10)
				{
					endTimeValue=(dateAndTime.get(Calendar.HOUR_OF_DAY)+1)+":0"+dateAndTime.get(Calendar.MINUTE);
					task.setEndTime(dateAndTime.get(Calendar.HOUR_OF_DAY), dateAndTime.get(Calendar.MINUTE));
				}
				else
				{
					endTimeValue=(dateAndTime.get(Calendar.HOUR_OF_DAY)+1)+":"+dateAndTime.get(Calendar.MINUTE);
					task.setEndTime((dateAndTime.get(Calendar.HOUR_OF_DAY)+1), dateAndTime.get(Calendar.MINUTE));
				}
				
				endDateValue = dateAndTime.get(Calendar.DAY_OF_MONTH)+"."+dateAndTime.get(Calendar.MONTH)+"."+dateAndTime.get(Calendar.YEAR);
				task.setEndDate(dateAndTime.get(Calendar.YEAR), dateAndTime.get(Calendar.MONTH), dateAndTime.get(Calendar.DAY_OF_MONTH));
			}
			
			
		
		}
		
		beginDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(
						AddTask.this, 
						beginDatePicker, 
						dateAndTime.get(Calendar.YEAR),
						dateAndTime.get(Calendar.MONTH),			
						dateAndTime.get(Calendar.DAY_OF_MONTH)).show(); 			                          
			}
		});
		beginDate.setText(beginDateValue);
		

		endDate.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new DatePickerDialog(
						AddTask.this, 
						endDatePicker, 
						dateAndTime.get(Calendar.YEAR),
						dateAndTime.get(Calendar.MONTH),			
						dateAndTime.get(Calendar.DAY_OF_MONTH)).show(); 			                          
			}
		});
		endDate.setText(endDateValue);
	
		beginTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new TimePickerDialog(
						AddTask.this, 
						beginTimePicker,
						dateAndTime.get(Calendar.HOUR),
						dateAndTime.get(Calendar.MINUTE),
						true).show();

			}
		});
		beginTime.setText(beginTimeValue);
		
		
		endTime.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				
				new TimePickerDialog(
						AddTask.this, 
						endTimePicker,
						dateAndTime.get(Calendar.HOUR),
						dateAndTime.get(Calendar.MINUTE),
						true).show();
				

			}
		});
		endTime.setText(endTimeValue);
		
		if (MyApplication.edit)
		{
		
			priority.setSelection(task.getPriority());
			
			checkBoxFill.setChecked(task.getFill());
			checkBoxAllDay.setChecked(task.getAllDay());
			checkBoxRepeat.setChecked(task.getRepeat());
			
			titleField.setText(task.getTitle());
			description.setText(task.getDescription());
			location.setText(task.getLocation());
			
			if (task.getRepeat())
			{
				repeatType1.setSelection(task.getRepeatType());
				repeatType2.setSelection(task.getRepeatInterval()-1);
				
				
		
				if (task.getRepeatType()==0)
					repeatType2After.setText("zile");
				else if (task.getRepeatType()==1)
					repeatType2After.setText("s�pt�m�ni");
				else if (task.getRepeatType()==2)
					repeatType2After.setText("luni");
				else if (task.getRepeatType()==3)
					repeatType2After.setText("ani");
			}
			
			
		}
		
	}

	public void readTitle()
	{
		task.setTitle(TextUtils.htmlEncode(titleField.getText().toString()));
	}


	public void readDescription()
	{
		task.setDescirption(TextUtils.htmlEncode(description.getText().toString()));
	}

	public void readLocation()
	{
		task.setLocation(TextUtils.htmlEncode(location.getText().toString()));
	}

	public void onCheckedChanged(CompoundButton button, boolean isChecked)
	{
		if (button==checkBoxFill)
		{
			if (isChecked)
				task.setFill(true);
			else
				task.setFill(false);

		}
		if (button==checkBoxAllDay)
		{
			if (isChecked)
			{
				task.setAllDay(true);
				beginTime.setVisibility(View.INVISIBLE);
				endTime.setVisibility(View.INVISIBLE);
				priority.setVisibility(View.INVISIBLE);
				/*considerPriority.setVisibility(View.INVISIBLE);
				considerTime.setVisibility(View.INVISIBLE);*/

				priorityLabel.setVisibility(View.INVISIBLE);
			}
			else
			{
				task.setAllDay(false);
				beginTime.setVisibility(View.VISIBLE);
				endTime.setVisibility(View.VISIBLE);
				priority.setVisibility(View.VISIBLE);
			/*	considerPriority.setVisibility(View.VISIBLE);
				considerTime.setVisibility(View.VISIBLE);*/
				priorityLabel.setVisibility(View.VISIBLE);
			}
		}
		else if (button == checkBoxRepeat)
		{
			if (isChecked)
			{
				task.setRepeat(true);
				repeatType1.setVisibility(View.VISIBLE);
				repeatType1Label.setVisibility(View.VISIBLE);
				repeatType2.setVisibility(View.VISIBLE);
				repeatType2Label.setVisibility(View.VISIBLE);
				repeatType2After.setVisibility(View.VISIBLE);

			}
			else
			{
				task.setRepeat(false);
				repeatType1.setVisibility(View.INVISIBLE);
				repeatType1Label.setVisibility(View.INVISIBLE);
				repeatType2.setVisibility(View.INVISIBLE);
				repeatType2Label.setVisibility(View.INVISIBLE);
				repeatType2After.setVisibility(View.INVISIBLE);
			}
		}
		/*	else if (button==considerPriority)
    	{
    		if (isChecked)
    			task.setConsiderPriority(true);
    		else
    			task.setConsiderPriority(false);
    	}
    	else if (button==considerTime)
    	{
    		if (isChecked)
    			task.setConsiderTime(true);
    		else
    			task.setConsiderTime(false);
    	}*/

	}

	public void updateBeginDate()
	{
		int day = dateAndTime.get(Calendar.DAY_OF_MONTH);	
		int month = dateAndTime.get(Calendar.MONTH);
		int year = dateAndTime.get(Calendar.YEAR);

		String s = ""+day+"."+month+"."+year;

		beginDate.setText(s);	
		task.setBeginDate(year, month, day);
	}

	public void updateEndDate()
	{
		int day = dateAndTime.get(Calendar.DAY_OF_MONTH);	
		int month = dateAndTime.get(Calendar.MONTH);
		int year = dateAndTime.get(Calendar.YEAR);

		String s = ""+day+"."+month+"."+year;

		endDate.setText(s);
		task.setEndDate(year, month, day);
	}

	public void updateBeginTime()
	{
		int hour = dateAndTime.get(Calendar.HOUR_OF_DAY);
		int minute = dateAndTime.get(Calendar.MINUTE);
		String s;
		if (minute<10)
			s = ""+hour+":0"+minute;
		else
			s = ""+hour+":"+minute;
		beginTime.setText(s);
		task.setBeginTime(hour, minute);
	}

	public void updateEndTime()
	{
		int hour = dateAndTime.get(Calendar.HOUR_OF_DAY);
		int minute = dateAndTime.get(Calendar.MINUTE);
		String s;
		if (minute<10)
			s = ""+hour+":0"+minute;
		else
			s = ""+hour+":"+minute;
		endTime.setText(s);
		task.setEndTime(hour, minute);
	}

	public void onItemSelected(AdapterView parent, View v, int position, long id)
	{
		if (parent==repeatType1)
		{
			task.setRepeatType(position);
			if (position==0)
				repeatType2After.setText("zile");
			else if (position==1)
				repeatType2After.setText("s�pt�m�ni");
			else if (position==2)
				repeatType2After.setText("luni");
			else if (position==3)
				repeatType2After.setText("ani");
		}
		else if (parent==repeatType2)
		{
			task.setRepeatInterval((position+1));
		}
		else if (parent==priority)
		{
			task.setPriority(position);
		}
	}
	public void onNothingSelected(AdapterView parent)
	{
		repeatType2After.setText("");
	}
}

