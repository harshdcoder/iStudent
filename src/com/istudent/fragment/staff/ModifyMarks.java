package com.istudent.fragment.staff;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.adapter.CustomOnItemSelectedListener;
import com.istudent.database.DatabaseHandler;
import com.istudent.model.Personal_Details;

public class ModifyMarks extends Activity {

	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	private DatabaseHandler mySQLiteAdapter;
	String marksId, maxMarks, marksObtained, studId, subjId, sem, deptId;

	Spinner spinner_stud = null;
	Spinner spinner_subj = null;
	EditText ed_maxmarks = null;
	EditText ed_marksobtained = null;

	List<String> list_stud = null;
	List<String> list_subject = null;
	String str_maxmarks = null;
	String str_marksobtained = null;
	Button btn_update = null;
	List<Personal_Details> studList = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_modify_marks);

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getApplicationContext());
		spinner_stud = (Spinner) findViewById(R.id.spinner_student);
		spinner_subj = (Spinner) findViewById(R.id.spinner_subj);
		ed_maxmarks = (EditText) findViewById(R.id.ed_maxmarks);
		ed_marksobtained = (EditText) findViewById(R.id.ed_marksobtained);

		Bundle extras = getIntent().getExtras();
		if (!(extras == null)) {
			ID = extras.getString("ID");
			FIRST_NAME = extras.getString("FIRST_NAME");
			LAST_NAME = extras.getString("LAST_NAME");
			USERNAME = extras.getString("USERNAME");
			MOBILE = extras.getString("MOBILE");
			ROLE = extras.getString("ROLE");

			marksId = extras.getString("marksId");
			maxMarks = extras.getString("maxMarks");
			marksObtained = extras.getString("marksObtained");
			studId = extras.getString("studId");
			subjId = extras.getString("subjId");

		}
		ed_maxmarks.setText(maxMarks);
		ed_marksobtained.setText(marksObtained);

		list_stud = new ArrayList<String>();
		studList = new ArrayList<Personal_Details>();
		studList = mySQLiteAdapter.getStudListwithDetails();

		String name = mySQLiteAdapter.getStudName(studId);
		list_stud.add(name);

		for (int i = 0; i < studList.size(); i++) {

			if (!(studList.get(i).getId() == studId)) {
				list_stud.add(studList.get(i).getFirstName());
			}
		}
		ArrayAdapter<String> dataAdapterStaff = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_stud);

		dataAdapterStaff
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_stud.setAdapter(dataAdapterStaff);

		String sub = mySQLiteAdapter.getSubName(subjId);
		list_subject = new ArrayList<String>();
		list_subject.add(sub);
		list_subject = mySQLiteAdapter.getAllSubject();

		ArrayAdapter<String> dataAdapterSubj = new ArrayAdapter<String>(
				getApplicationContext(), android.R.layout.simple_spinner_item,
				list_subject);

		dataAdapterSubj
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_subj.setAdapter(dataAdapterSubj);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();
	}

	public void addListenerOnSpinnerItemSelection() {

		spinner_stud
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		spinner_subj
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}

	// get the selected dropdown list value

	public void addListenerOnButton() {

		spinner_stud = (Spinner) findViewById(R.id.spinner_student);
		spinner_subj = (Spinner) findViewById(R.id.spinner_subj);

		btn_update = (Button) findViewById(R.id.bt_submit);

		btn_update.setOnClickListener(new OnClickListener() {

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
								getApplicationContext(),
								"Max Marks is " + maxMarks
										+ "! provide the valid Marks Obtained ",
								Toast.LENGTH_SHORT).show();

					} else {

						// To insert candidate values into the database
						boolean result = mySQLiteAdapter.updateMarks(studId,
								subjId, str_maxmarks, str_marksobtained,marksId);

						if (result == true) {

							Toast.makeText(getApplicationContext(),
									"Marks updated successfully",
									Toast.LENGTH_SHORT).show();

						} else {
							Toast.makeText(getApplicationContext(),
									"Record not updated ", Toast.LENGTH_SHORT)
									.show();
						}

					}
				}

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
		});

	}
}
