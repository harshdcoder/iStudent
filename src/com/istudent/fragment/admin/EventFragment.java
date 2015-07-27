package com.istudent.fragment.admin;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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

public class EventFragment extends Fragment {

	private Calendar cal;
	private int day;
	private int month;
	private int year;
	private EditText et_startDate;
	private EditText et_endDate;
	private Button btn_create;
	private EditText et_eventname;
	private EditText et_venue;
	private EditText et_description_new;
	// TimePicker timePicker;
	static Button timePickerView;
	private DatabaseHandler mySQLiteAdapter;
	private Calendar calendar;

	static final int DATE_DIALOG_ID1 = 0;
	static final int DATE_DIALOG_ID = 1;

	Spinner spinner_dept = null;
	String deptId = null;
	List<String> list_dept = null;
	View rootView = null;
	String format = "";
	static String strTime = null;

	public EventFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());

		rootView = inflater.inflate(R.layout.fragment_event, container, false);

		btn_create = (Button) rootView.findViewById(R.id.bt_create);
		et_eventname = (EditText) rootView.findViewById(R.id.et_eventname);
		et_venue = (EditText) rootView.findViewById(R.id.et_venue);
		et_description_new = (EditText) rootView
				.findViewById(R.id.et_description);

		spinner_dept = (Spinner) rootView.findViewById(R.id.spinner_dept);
		// timePicker = (TimePicker) rootView.findViewById(R.id.timePicker1);
		timePickerView = (Button) rootView.findViewById(R.id.timePicker1);

		list_dept = new ArrayList<String>();
		list_dept = mySQLiteAdapter.getAllDept();
		ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_dept);

		dataAdapterDept
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_dept.setAdapter(dataAdapterDept);

		cal = Calendar.getInstance();
		day = cal.get(Calendar.DAY_OF_MONTH);
		month = cal.get(Calendar.MONTH);
		year = cal.get(Calendar.YEAR);

		et_startDate = (EditText) rootView.findViewById(R.id.dp_startdate);
		// et_startDate.setOnTouchListener(new View.OnClickListener() {
		// // public boolean onTouch(View arg0, MotionEvent arg1) {
		// //
		// // DialogFragment newFragment = new SelectDateFragment();
		// // newFragment.show(getFragmentManager(), "DatePicker");
		// //
		// // return true;
		// // }
		//
		// public void onClick(View v) {
		//
		// DialogFragment newFragment = new SelectDateFragment();
		// newFragment.show(getFragmentManager(), "DatePicker");
		// }
		// });

		et_startDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new SelectDateFragment();
				newFragment.show(getFragmentManager(), "DatePicker");
			}
		});

		et_endDate = (EditText) rootView.findViewById(R.id.dp_enddate);
		// et_endDate.setOnTouchListener(new OnTouchListener() {
		// public boolean onTouch(View arg0, MotionEvent arg1) {
		// DialogFragment newFragment = new SelectEndDateFragment();
		// newFragment.show(getFragmentManager(), "DatePicker");
		//
		// return true;
		// }
		// });

		et_endDate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new SelectEndDateFragment();
				newFragment.show(getFragmentManager(), "DatePicker");
			}
		});

		timePickerView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogFragment newFragment = new TimePickerFragment();
				newFragment.show(getActivity().getSupportFragmentManager(),
						"timePicker");

			}
		});
		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();
		return rootView;
	}

	public void addListenerOnSpinnerItemSelection() {

		spinner_dept
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}
	

	// get the selected dropdown list value

	public void addListenerOnButton() {

		spinner_dept = (Spinner) rootView.findViewById(R.id.spinner_dept);

		btn_create = (Button) rootView.findViewById(R.id.bt_create);

		btn_create.setOnClickListener(new OnClickListener() {

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
				String startdate = et_startDate.getText().toString();
				String enddate = et_endDate.getText().toString();
				String description = et_description_new.getText().toString();
				String venue = et_venue.getText().toString();
				String format = "";
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
				} else {
					mySQLiteAdapter = new DatabaseHandler(getActivity());
					// id = mySQLiteAdapter.createEventid();
					boolean flag = mySQLiteAdapter.insertEvent(eventname,
							startdate, enddate, description, venue, strTime,
							deptId);
					if (flag == true)
						Toast.makeText(getActivity(),
								"Event Created Successfully",
								Toast.LENGTH_SHORT).show();
					else
						Toast.makeText(getActivity(), "Event Not Created",
								Toast.LENGTH_SHORT).show();

				}

			}

			// public void showTime(int hour, int min) {
			// if (hour == 0) {
			// hour += 12;
			// format = "AM";
			// } else if (hour == 12) {
			// format = "PM";
			// } else if (hour > 12) {
			// hour -= 12;
			// format = "PM";
			// } else {
			// format = "AM";
			// }
			//
			// StringBuilder time = new StringBuilder().append(hour)
			// .append(" : ").append(min).append(" ").append(format);
			// strTime = time.toString();
			//
			// }

		});

	}

	// Dialog functionality
	protected void alertbox(String title, String mymessage) {
		new AlertDialog.Builder(getActivity())
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

	@SuppressLint("NewApi")
	public class SelectDateFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			populateSetDate(yy, mm + 1, dd);
		}

		public void populateSetDate(int year, int month, int day) {
			et_startDate.setText(month + "/" + day + "/" + year);
		}

	}

	@SuppressLint("NewApi")
	public class SelectEndDateFragment extends DialogFragment implements
			DatePickerDialog.OnDateSetListener {

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) {
			final Calendar calendar = Calendar.getInstance();
			int yy = calendar.get(Calendar.YEAR);
			int mm = calendar.get(Calendar.MONTH);
			int dd = calendar.get(Calendar.DAY_OF_MONTH);
			return new DatePickerDialog(getActivity(), this, yy, mm, dd);
		}

		public void onDateSet(DatePicker view, int yy, int mm, int dd) {
			populateSetDate(yy, mm + 1, dd);
		}

		public void populateSetDate(int year, int month, int day) {
			et_endDate.setText(month + "/" + day + "/" + year);
		}

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
			timePickerView.setText(hourOfDay + ":" + minute);
			strTime = timePickerView.getText().toString();
		}

	}

}
