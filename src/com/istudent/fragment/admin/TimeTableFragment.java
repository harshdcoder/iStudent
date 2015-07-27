package com.istudent.fragment.admin;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.adapter.CustomOnItemSelectedListener;
import com.istudent.database.DatabaseHandler;
import com.istudent.model.Personal_Details;

public class TimeTableFragment extends Fragment {

	View rootView = null;

	Spinner spinner_dept = null;
	Spinner spinner_staff = null;
	Spinner spinner_subj = null;
	Spinner spinner_sem = null;
	Spinner spinner_sec = null;
	Spinner spinner_timings = null;
	Spinner spinner_period = null;
	Spinner spinner_weekday = null;

	Button btn_create = null;

	private DatabaseHandler mySQLiteAdapter;
	List<String> list_dept = null;
	List<String> list_staff = null;
	List<String> list_subject = null;
	List<String> list_sem = null;
	List<String> list_sec = null;
	List<String> list_timings = null;
	List<String> list_period = null;
	List<String> list_weekday = null;

	String deptId, subjId;
	List<Personal_Details> staffList = null;

	public TimeTableFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());

		rootView = inflater.inflate(R.layout.fragment_time_table, container,
				false);
		spinner_dept = (Spinner) rootView.findViewById(R.id.spinner_dept);
		spinner_staff = (Spinner) rootView.findViewById(R.id.spinner_staff);
		spinner_subj = (Spinner) rootView.findViewById(R.id.spinner_subjct);
		spinner_sem = (Spinner) rootView.findViewById(R.id.spinner_sem);

		spinner_sec = (Spinner) rootView.findViewById(R.id.spinner_sec);
		spinner_timings = (Spinner) rootView.findViewById(R.id.spinner_timings);
		spinner_period = (Spinner) rootView.findViewById(R.id.spinner_period);
		spinner_weekday = (Spinner) rootView.findViewById(R.id.spinner_weekday);

		String ID = getArguments().getString("ID");
		String FIRST_NAME = getArguments().getString("FIRST_NAME");
		String LAST_NAME = getArguments().getString("LAST_NAME");
		String USERNAME = getArguments().getString("USERNAME");
		String MOBILE = getArguments().getString("MOBILE");
		String ROLE = getArguments().getString("ROLE");

		list_dept = new ArrayList<String>();
		list_dept = mySQLiteAdapter.getAllDept();

		ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_dept);

		dataAdapterDept
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_dept.setAdapter(dataAdapterDept);

		list_staff = new ArrayList<String>();
		staffList = new ArrayList<Personal_Details>();
		staffList = mySQLiteAdapter.getStaffListwithDetails();
		for (int i = 0; i < staffList.size(); i++) {
			list_staff.add(staffList.get(i).getFirstName());
		}
		ArrayAdapter<String> dataAdapterStaff = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_staff);

		dataAdapterStaff
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_staff.setAdapter(dataAdapterStaff);

		list_subject = new ArrayList<String>();
		//list_subject.add("Software Engineering");
		//list_subject.add("Systems Software");
		//list_subject.add("Operating Systems");
		//list_subject.add("Compiler Design");
		//list_subject.add("Computer Networks -II");
		//list_subject.add("Computer Graphics");
		list_subject = mySQLiteAdapter.getAllSubject();
		ArrayAdapter<String> dataAdapterSubj = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,list_subject);

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
				getActivity(), android.R.layout.simple_spinner_item, list_sem);

		dataAdapterSem
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_sem.setAdapter(dataAdapterSem);

		list_sec = new ArrayList<String>();
		list_sec.add("Sec A");
		list_sec.add("Sec B");
		ArrayAdapter<String> dataAdapterSection = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_sec);

		dataAdapterSection
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_sec.setAdapter(dataAdapterSection);

		list_timings = new ArrayList<String>();
		list_timings.add("8:00 - 9:00");
		list_timings.add("9:00 - 9:50");
		list_timings.add("9:50 - 10:40");
		list_timings.add("11:00 - 11:50");
		list_timings.add("11:50 - 12:40");

		ArrayAdapter<String> dataAdapterTimings = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				list_timings);

		dataAdapterTimings
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_timings.setAdapter(dataAdapterTimings);

		list_period = new ArrayList<String>();
		list_period.add("1");
		list_period.add("2");
		list_period.add("3");
		list_period.add("4");
		list_period.add("5");
		ArrayAdapter<String> dataAdapterPeriod = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				list_period);

		dataAdapterPeriod
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_period.setAdapter(dataAdapterPeriod);

		list_weekday = new ArrayList<String>();
		list_weekday.add("Day 1");
		list_weekday.add("Day 2");
		list_weekday.add("Day 3");
		list_weekday.add("Day 4");
		list_weekday.add("Day 5");
		list_weekday.add("Day 6");
		ArrayAdapter<String> dataAdapterWeekday = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				list_weekday);

		dataAdapterWeekday
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_weekday.setAdapter(dataAdapterWeekday);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

		return rootView;
	}

	// Add spinner data

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

	// get the selected dropdown list value

	public void addListenerOnButton() {

		spinner_dept = (Spinner) rootView.findViewById(R.id.spinner_dept);
		spinner_staff = (Spinner) rootView.findViewById(R.id.spinner_staff);
		spinner_subj = (Spinner) rootView.findViewById(R.id.spinner_subjct);
		spinner_sem = (Spinner) rootView.findViewById(R.id.spinner_sem);
		spinner_sec = (Spinner) rootView.findViewById(R.id.spinner_sec);
		spinner_timings = (Spinner) rootView.findViewById(R.id.spinner_timings);
		spinner_period = (Spinner) rootView.findViewById(R.id.spinner_period);
		spinner_weekday = (Spinner) rootView.findViewById(R.id.spinner_weekday);

		btn_create = (Button) rootView.findViewById(R.id.bt_submit);

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
				boolean result = mySQLiteAdapter.onInsertTimesheet(deptId,
						staffId, subjId, semester, section, timings, period,
						weekday);

				if (result == true) {

					Toast.makeText(
							getActivity(),
							"Time Table created for " + deptKey
									+ " successfully", Toast.LENGTH_SHORT)
							.show();

				} else {
					Toast.makeText(getActivity(), "Record not inserted ",
							Toast.LENGTH_SHORT).show();
				}

			}

		});

	}
}
