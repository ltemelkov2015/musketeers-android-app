package com.example.helloandroid;

import java.util.Calendar;



import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
    private int mAddDays=5;
    private int mDayofWeek;
    static final private int DATE_DIALOG_ID = 0;
    static final private int CALCULATE_DATE = Menu.FIRST;

    
  
    
    /* Called when Object Activity first created */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.main);
        
        
        /*
        Spinner s = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.days, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        */
       

        // capture our View elements
        mEditBox = (EditText)findViewById(R.id.myEditText);
        mDateDisplay = (TextView) findViewById(R.id.dateDisplay);
        mPickDate = (Button) findViewById(R.id.pickDate);
        mDateChangedDisplay = (TextView) findViewById(R.id.dateChangedDisplay);
        mDateChangedDisplayDayofWeek = (TextView) findViewById(R.id.dateChangedDisplayDayofWeek);
        
        
        // add a click listener to the button
        mPickDate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showDialog(DATE_DIALOG_ID);
            }
        });

        // get the current date
        //final Calendar c = Calendar.getInstance();
        Calendar c = Calendar.getInstance();        
        
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        
        c.add(Calendar.DAY_OF_MONTH, mAddDays);
        mYearChanged = c.get(Calendar.YEAR);
        mMonthChanged = c.get(Calendar.MONTH);
        mDayChanged = c.get(Calendar.DAY_OF_MONTH);
        
        mDayofWeek = c.get(Calendar.DAY_OF_WEEK);

        
        // display the current date
        updateDisplay();
        updateChangedDisplay();
        updateChangedDisplayDayofWeek();
        
          
    }
    
    /** This gets called When user hits "MENU" on Android */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      super.onCreateOptionsMenu(menu);      
      MenuItem itemCalc = menu.add(0, CALCULATE_DATE, Menu.NONE, R.string.calculate);
//      itemCalc.setIcon(R.drawable.beachw);
      return true;
    }
    
    
    
    
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
                    Calendar temp = Calendar.getInstance();
                    temp.set(Calendar.YEAR, mYear);
                    temp.set(Calendar.MONTH, mMonth);
                    temp.set(Calendar.DAY_OF_MONTH, mDay);
                    
                    temp.add(Calendar.DAY_OF_MONTH, mAddDays);
                    mYearChanged = temp.get(Calendar.YEAR);
                    mMonthChanged = temp.get(Calendar.MONTH);
                    mDayChanged = temp.get(Calendar.DAY_OF_MONTH);
                    
                    mDayofWeek = temp.get(Calendar.DAY_OF_WEEK);
                    
                    
                    
                    updateDisplay();
                    updateChangedDisplay();
                    updateChangedDisplayDayofWeek();
                }
            };
}