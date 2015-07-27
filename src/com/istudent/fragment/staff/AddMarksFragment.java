package com.istudent.fragment.staff;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.adapter.CustomOnItemSelectedListener;
import com.istudent.database.DatabaseHandler;
import com.istudent.model.Personal_Details;

public class AddMarksFragment extends Fragment {

	View rootView = null;

	Spinner spinner_stud = null;
	Spinner spinner_subj = null;
	EditText ed_maxmarks = null;
	EditText ed_marksobtained = null;

	List<String> list_stud = null;
	List<String> list_subject = null;
	String str_maxmarks = null;
	String str_marksobtained = null;

	private DatabaseHandler mySQLiteAdapter;
	List<Personal_Details> studList = null;
	String subjId = null;
	Button btn_create = null;

	public AddMarksFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		rootView = inflater.inflate(R.layout.fragment_add_marks, container,
				false);

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());
		spinner_stud = (Spinner) rootView.findViewById(R.id.spinner_student);
		spinner_subj = (Spinner) rootView.findViewById(R.id.spinner_subj);
		ed_maxmarks = (EditText) rootView.findViewById(R.id.ed_maxmarks);
		ed_marksobtained = (EditText) rootView
				.findViewById(R.id.ed_marksobtained);

		String ID = getArguments().getString("ID");
		String FIRST_NAME = getArguments().getString("FIRST_NAME");
		String LAST_NAME = getArguments().getString("LAST_NAME");
		String USERNAME = getArguments().getString("USERNAME");
		String MOBILE = getArguments().getString("MOBILE");
		String ROLE = getArguments().getString("ROLE");

		list_stud = new ArrayList<String>();
		studList = new ArrayList<Personal_Details>();
		studList = mySQLiteAdapter.getStudListwithDetails();
		for (int i = 0; i < studList.size(); i++) {
			list_stud.add(studList.get(i).getFirstName());
		}
		ArrayAdapter<String> dataAdapterStaff = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_stud);

		dataAdapterStaff
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_stud.setAdapter(dataAdapterStaff);

		list_subject = new ArrayList<String>();
		list_subject = mySQLiteAdapter.getAllSubject();
		ArrayAdapter<String> dataAdapterSubj = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item,
				list_subject);

		dataAdapterSubj
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_subj.setAdapter(dataAdapterSubj);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

		return rootView;
	}

	public void addListenerOnSpinnerItemSelection() {

		spinner_stud
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_subj
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}

	// get the selected dropdown list value

	public void addListenerOnButton() {

		spinner_stud = (Spinner) rootView.findViewById(R.id.spinner_student);
		spinner_subj = (Spinner) rootView.findViewById(R.id.spinner_subj);

		btn_create = (Button) rootView.findViewById(R.id.bt_submit);

		btn_create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_stud.getSelectedItem()),
				// Toast.LENGTH_LONG).show();

				String staffName = String.valueOf(spinner_stud
						.getSelectedItem());
				String studId = null;
				String name = null;
				for (int i = 0; i < studList.size(); i++) {
					name = studList.get(i).getFirstName();
					if (name.equals(staffName)) {
						studId = studList.get(i).getId();
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

				str_maxmarks = ed_maxmarks.getText().toString();
				str_marksobtained = ed_marksobtained.getText().toString();
				if (str_maxmarks.isEmpty()) {
					alertbox("Marks", "Max Marks not entered");
				} else if (str_marksobtained.isEmpty()) {
					alertbox("Marks", "Max Obtained not entered");
				} else {
					int maxMarks, marksObtained;
					maxMarks = Integer.parseInt(str_maxmarks);
					marksObtained = Integer.parseInt(str_marksobtained);
					if (marksObtained > maxMarks) {

						Toast.makeText(
								getActivity(),
								"Max Marks is " + maxMarks
										+ "! provide the valid Marks Obtained ",
								Toast.LENGTH_SHORT).show();

					} else {

						// To insert candidate values into the database
						boolean result = mySQLiteAdapter.onInsertMarks(studId,
								subjId, str_maxmarks, str_marksobtained);

						if (result == true) {

							Toast.makeText(getActivity(),
									"Marks inserted successfully",
									Toast.LENGTH_SHORT).show();

						} else {
							Toast.makeText(getActivity(),
									"Record not inserted ", Toast.LENGTH_SHORT)
									.show();
						}

					}
				}

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
		});

	}
}