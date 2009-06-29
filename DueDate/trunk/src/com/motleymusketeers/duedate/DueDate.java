/* DueDate Application 
 * Created by: Rohit Majhi
 * Date:June 26 2009
 * rev. 1.0
 * Description:
 * This application calculates a due date for accomplishing a project
 * It skips only the weekends to give a true project due date.
 *
 * Notes:
 * --Reduced  Calendar objects 
 * --Reduced EditText KeyListener code
 * --the "compute_day_date(Calendar c, int projectdays)" 
 *       the third argument in this method is omitted, added case statement for faster execution
 * --On date changed code fixed so that date picker works up/down to handle properly the dates 
 * --When Edit Text Box is empty , going up the date picker , app crashes -  fixed
 * --When user enters "0" the result Due Date lags by 1 day-fixed!
 */


package com.motleymusketeers.duedate;
import java.util.Calendar;
import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.util.Log;
import android.view.View;
//import android.view.View.OnKeyListener;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;


public class DueDate extends Activity {
    
	protected static final String TAG = DueDate.class.getSimpleName();
    private EditText WorkingDays;
	private TextView Result;
	private TextView StartDateText;
	private DatePicker StartDate;
	private Calendar cal;
	private final int SAT_OFFSET = 2;// Offset allows user to go from Friday to Monday
    private final int SUN_OFFSET = -2;//goes from Monday to Friday
    private int projectdays;
    
   
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Get References to the View Objects
        setContentView(R.layout.main);
        WorkingDays = (EditText) findViewById(R.id.TxtBox_WorkingDays);
        StartDate = (DatePicker) findViewById(R.id.StartDate);
        StartDateText = (TextView) findViewById(R.id.TxtView_Start);
        Result = (TextView) findViewById(R.id.TxtView_Result);
        cal = Calendar.getInstance();        
        
        //Init the GUI
        int dayofweek = cal.get(Calendar.DAY_OF_WEEK);      
        if (dayofweek == Calendar.SATURDAY) {
       	cal.add(Calendar.DATE, SAT_OFFSET);
       }else if (dayofweek == Calendar.SUNDAY) {
       	cal.add(Calendar.DATE, SUN_OFFSET);
       }
   
        StartDate.init(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH),cal.get(Calendar.DAY_OF_MONTH),dateListen);
        StartDateText.setText(String.format("%1$ta, %1$te %1$tb %1$ty", cal));

        
       
        View.OnKeyListener onKeyListener = new View.OnKeyListener() {
			public boolean onKey(View view, int keyCode, KeyEvent event) {
				if ((KeyEvent.KEYCODE_0 <= keyCode && keyCode <= KeyEvent.KEYCODE_9)
				        || keyCode == KeyEvent.KEYCODE_PERIOD || keyCode == KeyEvent.KEYCODE_DEL) {
					
					if (view instanceof EditText) {
						EditText editText = (EditText) view;
						cal.set(StartDate.getYear(), StartDate.getMonth(), StartDate.getDayOfMonth());
				        Calendar updatedc;
						if (editText.equals(WorkingDays)) {
							try {
								projectdays = Integer.valueOf(editText.getText().toString());
								if(projectdays!=0)
								  {
								    updatedc = compute_day_date(cal,projectdays); 
								    Result.setText(String.format("%1$ta, %1$te %1$tb %1$ty", updatedc));
								  }
							} catch (Throwable t) {
								Log.e(TAG, t.getMessage());
								return false;
							}
						} 
					}
					
					
				
				}
				return false;
			}
		};
		WorkingDays.setOnKeyListener(onKeyListener);
      
}//onCreate
	
	
	
	private Calendar compute_day_date(Calendar c, int projectdays) {
        int totaldays = 0;
        int padding = 0; // to account for weekends
        final int workingweek = 5;
        int dayofweek = c.get(Calendar.DAY_OF_WEEK);
        
        switch(dayofweek){
        case Calendar.MONDAY:
        {
        	if(projectdays > 5) {
            	padding = 1 + ( projectdays - 5 )/workingweek;
                if ((projectdays-5)%workingweek == 0) {
                	padding = padding - 1; // to take care of edge cases
                }
        	}else {
        		padding = 0;
        	}
        	
        }
        break;
        case Calendar.TUESDAY: {
        	if(projectdays > 4) {
            	padding = 1 + ( projectdays - 4 )/workingweek;
                if ((projectdays-4)%workingweek == 0) {
                	padding = padding - 1; // to take care of edge cases
                }
        	}else {
        		padding = 0;
        	}
        }
        break;
        
        case Calendar.WEDNESDAY: {
        	if(projectdays > 3) {
            	padding = 1 + ( projectdays - 3 )/workingweek;
                if ((projectdays-3)%workingweek == 0) {
                	padding = padding - 1; // to take care of edge cases
                }
        	}else {
        		padding = 0;
        	}
        }
        break;
        
        case Calendar.THURSDAY: {
        	if(projectdays > 2) {
            	padding = 1 + ( projectdays - 2 )/workingweek;
                if ((projectdays-2)%workingweek == 0) {
                	padding = padding - 1; // to take care of edge cases
                }
        	}else {
        		padding = 0;
        	}
        }
        break;
        case Calendar.FRIDAY: {
        	if(projectdays > 1) {
            	padding = 1 + ( projectdays - 1 )/workingweek;
                if ((projectdays-1)%workingweek == 0) {
                	padding = padding - 1; // to take care of edge cases
                }
        	}else {
        		padding = 0;
        	}
        }
        break;
        default:
        	break;
        }//End Switch statement
        
        
        totaldays = projectdays-1 + (padding*2); //assumes that the project starts today 
        c.add(Calendar.DATE, totaldays);
        dayofweek = c.get(Calendar.DAY_OF_WEEK);
        if (dayofweek == Calendar.SATURDAY) {
        	c.add(Calendar.DATE, SAT_OFFSET);
        }else if (dayofweek == Calendar.SUNDAY) {
        	c.add(Calendar.DATE, SUN_OFFSET);
        }
        return c ;
	}

	

        private DatePicker.OnDateChangedListener dateListen = 
             new DatePicker.OnDateChangedListener(){
            
		public void onDateChanged(DatePicker view, int year,
                    int monthOfYear, int dayOfMonth){
		    	
				cal.set(year, monthOfYear,dayOfMonth);
				int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
				
				if (dayofweek == Calendar.SATURDAY) 
					cal.add(Calendar.DATE, SAT_OFFSET);
				
				else if (dayofweek == Calendar.SUNDAY) 
					cal.add(Calendar.DATE, SUN_OFFSET);

				view.updateDate(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH));
				StartDateText.setText(String.format("%1$ta, %1$te %1$tb %1$ty", cal));
				Calendar cc;
                //Once date is updated , calculate the due date on the fly
				if(WorkingDays.getText().toString().equals(""))   
					Result.setText(String.format("%1$ta, %1$te %1$tb %1$ty", cal));
				else if(Integer.valueOf(WorkingDays.getText().toString())==0)
					Result.setText(String.format("%1$ta, %1$te %1$tb %1$ty", cal));
				else{
					projectdays = Integer.valueOf(WorkingDays.getText().toString());
					cc=compute_day_date(cal,projectdays);
					Result.setText(String.format("%1$ta, %1$te %1$tb %1$ty", cc));
				}
		}			
        };
}
	

