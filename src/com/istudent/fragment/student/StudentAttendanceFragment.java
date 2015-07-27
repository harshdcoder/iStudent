package com.istudent.fragment.student;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.database.DatabaseHandler;
import com.istudent.fragment.staff.ModifyAttendance;

public class StudentAttendanceFragment extends Fragment {

	ListView listView = null;
	private DatabaseHandler mySQLiteAdapter;
	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	String deptId, sem;
	List<com.istudent.model.Attendance> object_all = null;
	String studId, subjId, attendanceId, attendedClasses, totalClasses,
			percentage;

	public StudentAttendanceFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_student_attendance,
				container, false);
		listView = (ListView) rootView.findViewById(R.id.lsv_reports);
		ID = getArguments().getString("ID");
		FIRST_NAME = getArguments().getString("FIRST_NAME");
		LAST_NAME = getArguments().getString("LAST_NAME");
		USERNAME = getArguments().getString("USERNAME");
		MOBILE = getArguments().getString("MOBILE");
		ROLE = getArguments().getString("ROLE");
		deptId = getArguments().getString("DEPT");
		sem = getArguments().getString("SEM");

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());
		object_all = new ArrayList<com.istudent.model.Attendance>();

		if (ROLE.equalsIgnoreCase("Staff")) {
			object_all = mySQLiteAdapter.getAttendance();
		} else {

			object_all = mySQLiteAdapter.getAttendance(ID);
		}

		int size = 0;
		size = object_all.size();

		String[] values = new String[size];
		int counter = 0;
		for (int i = 0; i < object_all.size(); i++) {
			values[i] = "Subject : " + object_all.get(i).getSUBJ_Id() + "\n"
					+ "Total Classes: " + object_all.get(i).getTOTAL_CLASSES()
					+ "\n" + "Attended Classes: "
					+ object_all.get(i).getATTENDED_CLASSES() + "\n"
					+ "Percentage: " + object_all.get(i).getPERCENTAGE() + "\n";

		}

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int itemposition = position;
				if (ROLE.equalsIgnoreCase("Staff")) {

					studId = object_all.get(itemposition).getSTUD_ID();
					subjId = object_all.get(itemposition).getSUBJ_Id();
					attendanceId = object_all.get(itemposition)
							.getATTENDANCE_Id();
					attendedClasses = object_all.get(itemposition)
							.getATTENDED_CLASSES();
					totalClasses = object_all.get(itemposition)
							.getTOTAL_CLASSES();

					percentage = object_all.get(itemposition).getPERCENTAGE();

					Intent i = new Intent(getActivity(), ModifyAttendance.class);
					i.putExtra("ID", ID);
					i.putExtra("FIRST_NAME", FIRST_NAME);
					i.putExtra("LAST_NAME", LAST_NAME);
					i.putExtra("USERNAME", USERNAME);
					i.putExtra("ROLE", ROLE);
					i.putExtra("MOBILE", MOBILE);

					i.putExtra("studId", studId);
					i.putExtra("subjId", subjId);
					i.putExtra("attendanceId", attendanceId);
					i.putExtra("attendedClasses", attendedClasses);
					i.putExtra("totalClasses", totalClasses);
					i.putExtra("percentage", percentage);

					startActivity(i);

				} else {

					String itemValue = (String) listView
							.getItemAtPosition(position);
					Toast.makeText(getActivity(), " " + itemValue,
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		return rootView;
	}
}