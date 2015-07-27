package com.istudent.fragment.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.text.format.DateFormat;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.adapter.CustomOnItemSelectedListener;
import com.istudent.database.DatabaseHandler;

@SuppressLint("NewApi")
public class ModifyEvent extends Activity implements OnClickListener {

	EditText et_eventname, dp_startdate, dp_enddate, et_description, et_venue;
	Spinner spinner_dept;
	static Button timePicker1;
	Button bt_update;
	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;

	String evtId, deptKey, desc, endDate, startDate, eventName, evntTime,
			venue;

	String deptId = null;
	List<String> list_dept = null;
	private DatabaseHandler mySQLiteAdapter;
	private Calendar cal;
	private int day;
	private int month;
	private int year;

	static final int DATE_DIALOG_ID1 = 0;
	static final int DATE_DIALOG_ID = 1;
	static String strTime = null;
    //String strTime = null;
	private int hour;
	private int minute;

	static final int TIME_DIALOG_ID = 999;

	private DatePicker datePicker;
	private Calendar calendar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_modify_event);

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getApplicationContext());

		et_eventname = (EditText) findViewById(R.id.et_eventname);
		dp_startdate = (EditText) findViewById(R.id.dp_startdate);
		dp_enddate = (EditText) findViewById(R.id.dp_enddate);
		et_description = (EditText) findViewById(R.id.et_description);
		et_venue = (EditText) findViewById(R.id.et_venue);

		spinner_dept = (Spinner) findViewById(R.id.spinner_dept);
		timePicker1 = (Button) findViewById(R.id.timePicker1);
		bt_update = (Button) findViewById(R.id.bt_update);

		Bundle extras = getIntent().getExtras();
		if (!(extras == null)) {
			ID = extras.getString("ID");
			FIRST_NAME = extras.getString("FIRST_NAME");
			LAST_NAME = extras.getString("LAST_NAME");
			USERNAME = extras.getString("USERNAME");
			MOBILE = extras.getString("MOBILE");
			ROLE = extras.getString("ROLE");

			evtId = extras.getString("evtId");
			deptKey = extras.getString("deptKey");
			endDate = extras.getString("endDate");
			desc = extras.getString("desc");
			startDate = extras.getString("startDate");
			eventName = extras.getString("eventName");
			evntTime = extras.getString("evntTime");
			venue = extras.getString("venue");

		}

		et_eventname.setText(eventName);
		dp_startdate.setText(startDate);
		dp_enddate.setText(endDate);
		et_description.setText(desc);
		et_venue.setText(venue);

		timePicker1.setText(evntTime);

		list_dept = new ArrayList<String>();
		list_dept = mySQLiteAdapter.getAllDept();
		ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_dept);

		dataAdapterDept
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_dept.setAdapter(dataAdapterDept);

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);
		dp_startdate.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {

				// DialogFragment newFragment = new SelectDateFragment();
				// // newFragment.show(getFragmentManager(), "DatePicker");

				showDialog(DATE_DIALOG_ID);

				return true;
			}
		});
		dp_enddate.setOnTouchListener(new OnTouchListener() {
			public boolean onTouch(View arg0, MotionEvent arg1) {
				// DialogFragment newFragment = new SelectEndDateFragment();
				// // newFragment.show(getFragmentManager(), "DatePicker");

				showDialog(DATE_DIALOG_ID1);

				return true;
			}
		});

		timePicker1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDialog(TIME_DIALOG_ID);

			}
		});
		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

	}

	@Override
	public void onClick(View v) {

	}

	public void addListenerOnSpinnerItemSelection() {

		spinner_dept
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}

	// get the selected dropdown list value

	public void addListenerOnButton() {

		spinner_dept = (Spinner) findViewById(R.id.spinner_dept);

		bt_update = (Button) findViewById(R.id.bt_update);

		bt_update.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_dept.getSelectedItem()),
				// Toast.LENGTH_LONG).show();

				String deptKey = String.valueOf(spinner_dept.getSelectedItem());
				deptId = mySQLiteAdapter.getDeptId(deptKey);

				String eventname = et_eventname.getText().toString();
				String startdate = dp_startdate.getText().toString();
				String enddate = dp_enddate.getText().toString();
				String description = et_description.getText().toString();
				String venue = et_venue.getText().toString();
				String strTime = timePicker1.getText().toString();
				//
				// calendar = Calendar.getInstance();
				// int hour = calendar.get(Calendar.HOUR_OF_DAY);
				// int min = calendar.get(Calendar.MINUTE);

				// showTime(hour, min);

				String id = null;
				if (eventname.isEmpty()) {
					alertbox("Event", "Event name not entered");
				} else if (startdate.isEmpty()) {
					alertbox("Event", "Start date not entered");
				} else if (enddate.isEmpty()) {
					alertbox("Event", "End date not entered");
				} else if (description.isEmpty()) {
					alertbox("Event", "Description not entered");
				}else if (venue.isEmpty()) {
                    alertbox("Event", "Venue not entered");
                }else if (strTime.isEmpty()) {
                    alertbox("Event", "Time not entered");
                }

                else {
					mySQLiteAdapter = new DatabaseHandler(
							getApplicationContext());

					boolean flag = mySQLiteAdapter.updateEvent(evtId,
							eventname, startdate, enddate, description, venue,
							strTime, deptId);

					if (flag == true)
						Toast.makeText(getApplicationContext(),
								"Event updated Successfully",
								Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getApplicationContext(),
								"Event Not Updated", Toast.LENGTH_SHORT).show();

				}

			}

		});

	}

	// Dialog functionality
	protected void alertbox(String title, String mymessage) {
		new AlertDialog.Builder(getApplicationContext())
				.setMessage(mymessage)
				.setTitle(title)
				.setCancelable(true)
				.setNeutralButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int whichButton) {
							}
						}).show();
	}

	public static class TimePickerFragment extends DialogFragment implements
			TimePickerDialog.OnTimeSetListener {
		public TimePickerFragment() {
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			return new TimePickerDialog(getActivity(), this, hour, minute,
					DateFormat.is24HourFormat(getActivity()));
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			timePicker1.setText(hourOfDay + ":" + minute);
			strTime = timePicker1.getText().toString();
		}

	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		case DATE_DIALOG_ID1:
			return new DatePickerDialog(this, datePickerListener1, year, month,
					day);
		case TIME_DIALOG_ID:
			// set time picker as current time
			return new TimePickerDialog(this, timePickerListener, hour, minute,
					false);

		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			dp_startdate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};

	private DatePickerDialog.OnDateSetListener datePickerListener1 = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			year = selectedYear;
			month = selectedMonth;
			day = selectedDay;

			// set selected date into textview
			dp_enddate.setText(new StringBuilder().append(month + 1)
					.append("-").append(day).append("-").append(year)
					.append(" "));

		}
	};

	private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {
		public void onTimeSet(TimePicker view, int selectedHour,
				int selectedMinute) {
			hour = selectedHour;
			minute = selectedMinute;

			// set current time into textview
			timePicker1.setText(new StringBuilder().append(hour)
					.append(":").append(minute));

		}
	};

	private static String pad(int c) {
		if (c >= 10)
			return String.valueOf(c);
		else
			return "0" + String.valueOf(c);
	}

}
