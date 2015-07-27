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
import com.istudent.fragment.admin.ModifyTimeTable;

public class ViewTimeTable extends Fragment {

	ListView listView;
	View rootView;
	private DatabaseHandler mySQLiteAdapter;
	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	List<com.istudent.model.TimeTable> object_all = null;

	public ViewTimeTable() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_timesheet, container,
				false);
		listView = (ListView) rootView.findViewById(R.id.listView1);

		ID = getArguments().getString("ID");
		FIRST_NAME = getArguments().getString("FIRST_NAME");
		LAST_NAME = getArguments().getString("LAST_NAME");
		USERNAME = getArguments().getString("USERNAME");
		MOBILE = getArguments().getString("MOBILE");
		ROLE = getArguments().getString("ROLE");
		String deptId = getArguments().getString("DEPT");
		String sem = getArguments().getString("SEM");

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());
		object_all = new ArrayList<com.istudent.model.TimeTable>();

		object_all = mySQLiteAdapter.getTimeTable(deptId, sem);

		int size = 0;
		size = object_all.size();

		String[] values = new String[size];
		int counter = 0;
		for (int i = 0; i < object_all.size(); i++) {
			values[i] = "Day : " + object_all.get(i).getWeekday() + "\n"
					+ "Dept: " + object_all.get(i).getDept() + "\n" 
					+ "Sem: " + object_all.get(i).getSem() + "\n"
					+ "Sec: " + object_all.get(i).getSec() + "\n" + 
					"Staff: " + object_all.get(i).getStaff() + "\n" +
					"Timings: "	+ object_all.get(i).getTimings() + "\n"
					+ "Subject: " + object_all.get(i).getSubject();

		}
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1, values);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int itemposition = position;

				// String itemValue = (String) listView
				// .getItemAtPosition(position);
				// Toast.makeText(getActivity(), " " + itemValue,
				// Toast.LENGTH_SHORT).show();

				if (ROLE.equalsIgnoreCase("Admin")) {

					String timeId = object_all.get(itemposition).getId();
					String deptId = object_all.get(itemposition).getDept();
					String period = object_all.get(itemposition).getPeriod();
					String section = object_all.get(itemposition).getSec();
					String sem = object_all.get(itemposition).getSem();
					String staff = object_all.get(itemposition).getStaff();
					String subject = object_all.get(itemposition).getSubject();
					String timings = object_all.get(itemposition).getTimings();
					String weekDay = object_all.get(itemposition).getWeekday();

					Intent i = new Intent(getActivity(), ModifyTimeTable.class);
					i.putExtra("ID", ID);
					i.putExtra("FIRST_NAME", FIRST_NAME);
					i.putExtra("LAST_NAME", LAST_NAME);
					i.putExtra("USERNAME", USERNAME);
					i.putExtra("ROLE", ROLE);
					i.putExtra("MOBILE", MOBILE);

					i.putExtra("timeId", timeId);
					i.putExtra("deptId", deptId);
					i.putExtra("period", period);
					i.putExtra("section", section);
					i.putExtra("sem", sem);
					i.putExtra("staff", staff);
					i.putExtra("subject", subject);
					i.putExtra("timings", timings);
					i.putExtra("weekDay", weekDay);

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