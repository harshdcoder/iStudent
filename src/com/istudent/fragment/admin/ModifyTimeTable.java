package com.istudent.fragment.admin;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.adapter.CustomOnItemSelectedListener;
import com.istudent.database.DatabaseHandler;
import com.istudent.model.Personal_Details;

public class ModifyTimeTable extends Activity implements OnClickListener {

	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	String timeId, deptId, period, sem, staff, subject, timings, weekDay;
	Spinner spinner_dept = null;
	Spinner spinner_staff = null;
	Spinner spinner_subj = null;
	Spinner spinner_sem = null;
	Spinner spinner_sec = null;
	Spinner spinner_timings = null;
	Spinner spinner_period = null;
	Spinner spinner_weekday = null;

	Button btn_update = null;

	private DatabaseHandler mySQLiteAdapter;
	List<String> list_dept = null;
	List<String> list_staff = null;
	List<String> list_subject = null;
	List<String> list_sem = null;
	List<String> list_sec = null;
	List<String> list_timings = null;
	List<String> list_period = null;
	List<String> list_weekday = null;

	String subjId;
	List<Personal_Details> staffList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getApplicationContext());

		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_modify_timetable);

		Bundle extras = getIntent().getExtras();
		if (!(extras == null)) {
			ID = extras.getString("ID");
			FIRST_NAME = extras.getString("FIRST_NAME");
			LAST_NAME = extras.getString("LAST_NAME");
			USERNAME = extras.getString("USERNAME");
			MOBILE = extras.getString("MOBILE");
			ROLE = extras.getString("ROLE");

			timeId = extras.getString("timeId");
			deptId = extras.getString("deptId");
			period = extras.getString("period");
			sem = extras.getString("sem");
			staff = extras.getString("staff");
			subject = extras.getString("subject");
			timings = extras.getString("timings");
			weekDay = extras.getString("weekDay");

		}

		spinner_dept = (Spinner) findViewById(R.id.spinner_dept);
		spinner_staff = (Spinner) findViewById(R.id.spinner_staff);
		spinner_subj = (Spinner) findViewById(R.id.spinner_subjct);
		spinner_sem = (Spinner) findViewById(R.id.spinner_sem);

		spinner_sec = (Spinner) findViewById(R.id.spinner_sec);
		spinner_timings = (Spinner) findViewById(R.id.spinner_timings);
		spinner_period = (Spinner) findViewById(R.id.spinner_period);
		spinner_weekday = (Spinner) findViewById(R.id.spinner_weekday);

		list_dept = new ArrayList<String>();
		// list_dept.add(deptId);
		list_dept = mySQLiteAdapter.getAllDept();

		ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_dept);

		dataAdapterDept
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_dept.setAdapter(dataAdapterDept);

		list_staff = new ArrayList<String>();
		staffList = new ArrayList<Personal_Details>();
		staffList = mySQLiteAdapter.getStaffListwithDetails();
		//list_staff.add(staff);
		for (int i = 0; i < staffList.size(); i++) {

			list_staff.add(staffList.get(i).getFirstName());
		}
		ArrayAdapter<String> dataAdapterStaff = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_staff);

		dataAdapterStaff
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_staff.setAdapter(dataAdapterStaff);

		list_subject = new ArrayList<String>();
		//list_subject.add(subject);
		list_subject = mySQLiteAdapter.getAllSubject();
		ArrayAdapter<String> dataAdapterSubj = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_subject);

		dataAdapterSubj
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_subj.setAdapter(dataAdapterSubj);

		list_sem = new ArrayList<String>();
		list_sem.add("1");
		list_sem.add("2");
		list_sem.add("3");
		list_sem.add("4");
		list_sem.add("5");
		list_sem.add("6");
		list_sem.add("7");
		list_sem.add("8");

		ArrayAdapter<String> dataAdapterSem = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_sem);

		dataAdapterSem
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_sem.setAdapter(dataAdapterSem);

		list_sec = new ArrayList<String>();
		// list_sec.add(s)
		list_sec.add("Sec A");
		list_sec.add("Sec B");
		ArrayAdapter<String> dataAdapterSection = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_sec);

		dataAdapterSection
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_sec.setAdapter(dataAdapterSection);

		list_timings = new ArrayList<String>();
		//list_timings.add(timings);
		list_timings.add("8:00 - 9:00");
		list_timings.add("9:00 - 9:50");
		list_timings.add("9:50 - 10:40");
		list_timings.add("11:00 - 11:50");
		list_timings.add("11:50 - 12:40");

		ArrayAdapter<String> dataAdapterTimings = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_timings);

		dataAdapterTimings
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_timings.setAdapter(dataAdapterTimings);

		list_period = new ArrayList<String>();
		//list_period.add(period);
		list_period.add("1");
		list_period.add("2");
		list_period.add("3");
		list_period.add("4");
		list_period.add("5");
		ArrayAdapter<String> dataAdapterPeriod = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_period);

		dataAdapterPeriod
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_period.setAdapter(dataAdapterPeriod);

		list_weekday = new ArrayList<String>();
		//list_weekday.add(weekDay);
		list_weekday.add("Day 1");
		list_weekday.add("Day 2");
		list_weekday.add("Day 3");
		list_weekday.add("Day 4");
		list_weekday.add("Day 5");
		list_weekday.add("Day 6");
		ArrayAdapter<String> dataAdapterWeekday = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_weekday);

		dataAdapterWeekday
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_weekday.setAdapter(dataAdapterWeekday);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

	}

	public void addListenerOnSpinnerItemSelection() {

		spinner_dept
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_staff
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_subj
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_sem
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_sec
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_timings
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

		spinner_period
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_weekday
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}

	public void addListenerOnButton() {

		spinner_dept = (Spinner) findViewById(R.id.spinner_dept);
		spinner_staff = (Spinner) findViewById(R.id.spinner_staff);
		spinner_subj = (Spinner) findViewById(R.id.spinner_subjct);
		spinner_sem = (Spinner) findViewById(R.id.spinner_sem);
		spinner_sec = (Spinner) findViewById(R.id.spinner_sec);
		spinner_timings = (Spinner) findViewById(R.id.spinner_timings);
		spinner_period = (Spinner) findViewById(R.id.spinner_period);
		spinner_weekday = (Spinner) findViewById(R.id.spinner_weekday);

		btn_update = (Button) findViewById(R.id.bt_submit);

		btn_update.setOnClickListener(new OnClickListener() {

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

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_staff
				// .getSelectedItem()), Toast.LENGTH_LONG)
				// .show();

				String staffName = String.valueOf(spinner_staff
						.getSelectedItem());
				String staffId = null;
				String name = null;
				for (int i = 0; i < staffList.size(); i++) {
					name = staffList.get(i).getFirstName();
					if (name.equals(staffName)) {
						staffId = staffList.get(i).getId();
					}
				}

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_subj.getSelectedItem()),
				// Toast.LENGTH_LONG).show();

				String subjName = String.valueOf(spinner_subj.getSelectedItem());
				subjId = mySQLiteAdapter.getSubjId(subjName);

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : " + "\n"
				// + String.valueOf(spinner_sem.getSelectedItem()),
				// Toast.LENGTH_LONG).show();

				String semester = String.valueOf(spinner_sem.getSelectedItem());

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : " + "\n"
				// + String.valueOf(spinner_sec.getSelectedItem()),
				// Toast.LENGTH_LONG).show();
				String section = String.valueOf(spinner_sec.getSelectedItem());

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_timings
				// .getSelectedItem()), Toast.LENGTH_LONG)
				// .show();
				String timings = String.valueOf(spinner_timings
						.getSelectedItem());

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_period
				// .getSelectedItem()), Toast.LENGTH_LONG)
				// .show();
				String period = String.valueOf(spinner_period.getSelectedItem());

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_weekday
				// .getSelectedItem()), Toast.LENGTH_LONG)
				// .show();
				String weekday = String.valueOf(spinner_weekday
						.getSelectedItem());

				// To insert candidate values into the database
				boolean result = mySQLiteAdapter.updateTimeSheet(deptId,
						staffId, subjId, semester, section, timings, period,
						weekday,timeId);

				if (result == true) {

					Toast.makeText(
							getApplicationContext(),
							"Time Table updated for " + deptKey
									+ " successfully", Toast.LENGTH_SHORT)
							.show();

				} else {
					Toast.makeText(getApplicationContext(),
							"Record not updated ", Toast.LENGTH_SHORT).show();
				}

			}

		});

	}

	@Override
	public void onClick(View v) {

	}
}


