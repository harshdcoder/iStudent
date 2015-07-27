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
import com.istudent.fragment.admin.ModifyEvent;

public class EventCalendarFragment extends Fragment {

	ListView listView = null;
	private DatabaseHandler mySQLiteAdapter;

	String role_selected = null;

	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	List<com.istudent.model.Events> object_all = null;

	public EventCalendarFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_eventcalendar,
				container, false);

		listView = (ListView) rootView.findViewById(R.id.lsv_reports);

		ID = getArguments().getString("ID");
		FIRST_NAME = getArguments().getString("FIRST_NAME");
		LAST_NAME = getArguments().getString("LAST_NAME");
		USERNAME = getArguments().getString("USERNAME");
		MOBILE = getArguments().getString("MOBILE");
		ROLE = getArguments().getString("ROLE");

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());
		object_all = new ArrayList<com.istudent.model.Events>();

		object_all = mySQLiteAdapter.getAllEvents();

		int size = 0;
		size = object_all.size();

		String[] values = new String[size];
		int counter = 0;
		for (int i = 0; i < object_all.size(); i++) {
			// values[i] = object_all.get(i).getEventName() + "\t"
			// + object_all.get(i).getStartDate() + "\t"
			// + object_all.get(i).getEndDate() + "\t"
			// + object_all.get(i).getEventTime() + "\n"
			// + object_all.get(i).getDeptKey() + "\t"
			// + object_all.get(i).getVenue() + "\t"
			// + object_all.get(i).getDescription();

			values[i] = "Event Name: " + object_all.get(i).getEventName()
					+ "\n Time: " + object_all.get(i).getEventTime()
					+ "\n Start-Date: " + object_all.get(i).getStartDate()
					+ "\n End-Date: " + object_all.get(i).getEndDate();

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

					String evtId = object_all.get(itemposition).getEvent_id();
					String deptKey = object_all.get(itemposition).getDeptKey();
					String desc = object_all.get(itemposition).getDescription();
					String endDate = object_all.get(itemposition).getEndDate();
					String startDate = object_all.get(itemposition)
							.getStartDate();
					String eventName = object_all.get(itemposition)
							.getEventName();
					String evntTime = object_all.get(itemposition)
							.getEventTime();
					String venue = object_all.get(itemposition).getVenue();

					Intent i = new Intent(getActivity(), ModifyEvent.class);
					i.putExtra("ID", ID);
					i.putExtra("FIRST_NAME", FIRST_NAME);
					i.putExtra("LAST_NAME", LAST_NAME);
					i.putExtra("USERNAME", USERNAME);
					i.putExtra("ROLE", ROLE);
					i.putExtra("MOBILE", MOBILE);

					i.putExtra("evtId", evtId);
					i.putExtra("deptKey", deptKey);
					i.putExtra("endDate", endDate);
					i.putExtra("desc", desc);
					i.putExtra("startDate", startDate);
					i.putExtra("eventName", eventName);
					i.putExtra("evntTime", evntTime);
					i.putExtra("venue", venue);

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
