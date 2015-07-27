package com.istudent.fragment.student;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.database.DatabaseHandler;

public class CmritForum extends Fragment {

	ListView listView;
	EditText editTextMsg;
	Button btnsendMsg;
	View rootView;
	String ID;
	ArrayAdapter<String> adapter = null;
	private DatabaseHandler mySQLiteAdapter;
	List<String> list_message = null;

	public CmritForum() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_forum, container, false);
		listView = (ListView) rootView.findViewById(R.id.listView1);
		editTextMsg = (EditText) rootView.findViewById(R.id.editTextMsg);
		btnsendMsg = (Button) rootView.findViewById(R.id.button1);

		list_message = new ArrayList<String>();

		ID = getArguments().getString("ID");
		String FIRST_NAME = getArguments().getString("FIRST_NAME");
		String LAST_NAME = getArguments().getString("LAST_NAME");
		String USERNAME = getArguments().getString("USERNAME");
		String MOBILE = getArguments().getString("MOBILE");
		String ROLE = getArguments().getString("ROLE");
		String deptId = getArguments().getString("DEPT");
		String sem = getArguments().getString("SEM");

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());
		list_message = mySQLiteAdapter.getForum();

		adapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, android.R.id.text1,
				list_message);
		listView.setAdapter(adapter);

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				int itemposition = position;

				String itemValue = (String) listView
						.getItemAtPosition(position);
				Toast.makeText(getActivity(), " " + itemValue,
						Toast.LENGTH_SHORT).show();

			}
		});

		btnsendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				String msg = editTextMsg.getText().toString();
				boolean result = mySQLiteAdapter.insertMessage(ID, msg);
				if (result == true) {
					list_message.add(msg);
					Toast.makeText(getActivity(),
							"Message shared successfully ", Toast.LENGTH_SHORT)
							.show();
					adapter.notifyDataSetChanged();

				} else {
					Toast.makeText(getActivity(), "Record not inserted ",
							Toast.LENGTH_SHORT).show();
				}
			}
		});

		return rootView;
	}
}