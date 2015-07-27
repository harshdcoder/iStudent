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
import com.istudent.fragment.staff.ModifyMarks;

public class InternalMarksFragment extends Fragment {

	ListView listView = null;
	private DatabaseHandler mySQLiteAdapter;

	String role_selected = null;

	List<com.istudent.model.Marks> object_all = null;
	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	String marksId, maxMarks, marksObtained, studId, subjId, sem, deptId;

	public InternalMarksFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_internal_marks,
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
		object_all = new ArrayList<com.istudent.model.Marks>();

		if (ROLE.equalsIgnoreCase("Staff")) {
			object_all = mySQLiteAdapter.getMarks();
		} else {

			object_all = mySQLiteAdapter.getMarks(ID);
		}

		int size = 0;
		size = object_all.size();

		String[] values = new String[size];
		int counter = 0;
		for (int i = 0; i < object_all.size(); i++) {
			values[i] = "Subject : " + object_all.get(i).getSubjId() + "\n"
					+ "Max Marks: " + object_all.get(i).getMaxMarks() + "\n"
					+ "Marks Obtained: " + object_all.get(i).getMarksObtained()
					+ "\n";

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

					marksId = object_all.get(itemposition).getMarksId();
					maxMarks = object_all.get(itemposition).getMaxMarks();
					marksObtained = object_all.get(itemposition)
							.getMarksObtained();
					studId = object_all.get(itemposition).getStudId();
					subjId = object_all.get(itemposition).getSubjId();

					Intent i = new Intent(getActivity(), ModifyMarks.class);
					i.putExtra("ID", ID);
					i.putExtra("FIRST_NAME", FIRST_NAME);
					i.putExtra("LAST_NAME", LAST_NAME);
					i.putExtra("USERNAME", USERNAME);
					i.putExtra("ROLE", ROLE);
					i.putExtra("MOBILE", MOBILE);

					i.putExtra("marksId", marksId);
					i.putExtra("maxMarks", maxMarks);
					i.putExtra("marksObtained", marksObtained);
					i.putExtra("studId", studId);
					i.putExtra("subjId", subjId);

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
