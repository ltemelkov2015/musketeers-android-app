package com.example.helloandroid;

import java.util.Calendar;





import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

public class HelloAndroid extends Activity {
	
   /* Data Fields */
	private TextView mDateDisplay, mDateChangedDisplay;
	private EditText mEditBox;
	private TextView mDateChangedDisplayDayofWeek;
	private Button mPickDate;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mYearChanged;
    private int mMonthChanged;
    private int mDayChanged;
    private int mAddDays=0;
    private int mDayofWeek;
    private Calendar c=null;//
    static final private int DATE_DIALOG_ID = 0;
    

    
  /* Called when Object Activity first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        //capture our View elements
        mEditBox = (EditText)findViewById(R.id.myEditText);
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mDateChangedDisplay = (TextView) findViewById(R.id.dateChangedDisplay);
        mDateChangedDisplayDayofWeek = (TextView) findViewById(R.id.dateChangedDisplayDayofWeek);
        c = Calendar.getInstance();//Get Current instance of calendar with current days
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        updateCalendar(mAddDays);
        
        
        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });
        
        
        
      //Assign the KeyListener to the DPad button to add new items
        mEditBox.setOnKeyListener(new OnKeyListener() {
          public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (event.getAction() == KeyEvent.ACTION_DOWN)
              if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
            	  //Add days update code here
            	  String s = mEditBox.getText().toString();
            	  int add_days = Integer.parseInt(s);
            	  mAddDays=add_days;
                  updateCalendar(mAddDays);
                  updateDisplay();
                  updateChangedDisplay();
                  updateChangedDisplayDayofWeek();
               
                return true; 
              }
            return false;
          }
        });
        
        updateDisplay();
        updateChangedDisplay();
        updateChangedDisplayDayofWeek();
        
          
    }
    
  
    
 /*When User clicks "pick date" , the date picker dialog will show up
  */
 @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
        case DATE_DIALOG_ID:
            return new DatePickerDialog(this,mDateSetListener,
                        mYear, mMonth, mDay);
        }
        return null;
    }
    
 // updates the date we display in the TextView
    private void updateDisplay() {
        mDateDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
            		.append("Current Date is: ")
                    .append(mMonth + 1).append("-")
                    .append(mDay).append("-")
                    .append(mYear).append(" "));
    }

    private void updateChangedDisplay() {
        mDateChangedDisplay.setText(
            new StringBuilder()
                    // Month is 0 based so add 1
                    .append(mMonthChanged + 1).append("-")
                    .append(mDayChanged).append("-")
                    .append(mYearChanged).append(" "));
    }

    private void updateChangedDisplayDayofWeek() {
    	String Dayofweek;
    	Dayofweek="";
    	
    	switch (mDayofWeek)
    	{
    	case 1:    		
    		Dayofweek="Sunday";
    		break;    		
    	case 2:
    		Dayofweek="Monday";
    		break;
    	case 3:
    		Dayofweek="Tuesday";
    		break;
    	case 4:
    		Dayofweek="Wednesday";
    		break;
    	case 5:
    		Dayofweek="Thursday";
    		break;
    	case 6:
    		Dayofweek="Friday";
    		break;
    	case 7:
    		Dayofweek="Saturday";
    		break;    		
    	default:
    		Dayofweek="Day of week Error";//should not be getting to this point
    		break;
    		
    	}
        mDateChangedDisplayDayofWeek.setText(Dayofweek);
                }

    
    
 // the callback received when the user "sets" the date in the dialog
    
    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year, 
                                      int monthOfYear, int dayOfMonth) {
                    
                	mYear = year;
                    mMonth = monthOfYear;
                    mDay = dayOfMonth;
                    c.set(Calendar.YEAR, mYear);
                    c.set(Calendar.MONTH, mMonth);
                    c.set(Calendar.DAY_OF_MONTH, mDay);
                    String s = mEditBox.getText().toString();
              	    int add_days = Integer.parseInt(s);
              	    mAddDays=add_days;
                    updateCalendar(mAddDays);
                    updateDisplay();
                    updateChangedDisplay();
                    updateChangedDisplayDayofWeek();
                }
            };
     
            
    private void updateCalendar(int days_offset){
    	c.add(Calendar.DAY_OF_MONTH, days_offset);
        mYearChanged = c.get(Calendar.YEAR);
        mMonthChanged = c.get(Calendar.MONTH);
        mDayChanged = c.get(Calendar.DAY_OF_MONTH);
        mDayofWeek = c.get(Calendar.DAY_OF_WEEK);
    	
    }
            
 }






