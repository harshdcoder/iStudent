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

public class ModifyAttendance extends Activity {

	String ID, ROLE, FIRST_NAME, LAST_NAME, USERNAME, MOBILE;
	private DatabaseHandler mySQLiteAdapter;
	Spinner spinner_stud = null;
	Spinner spinner_subj = null;
	EditText ed_totclasses = null;
	EditText ed_attendedclasses = null;
	EditText ed_percentage = null;
	List<String> list_stud = null;
	List<String> list_subject = null;
	String str_totclasses = null;
	String str_attendedclasses = null;
	String str_percentage = null;

	List<Personal_Details> studList = null;

	Button btn_update = null;

	String studId, subjId, attendanceId, attendedClasses, totalClasses,
			percentage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_modify_attendance);

		// (ATTENDANCE_Id Integer PRIMARY KEY AUTOINCREMENT ,
		// STUD_ID references Student(StudId) , SUBJ_Id references
		// Subject(SUBJ_Id),
		// TOTAL_CLASSES INTEGER NOT NULL, ATTENDED_CLASSES INTEGER, PERCENTAGE
		// VARCHAR)";

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getApplicationContext());
		// To connect to database
		spinner_stud = (Spinner) findViewById(R.id.spinner_student);
		spinner_subj = (Spinner) findViewById(R.id.spinner_subj);
		ed_totclasses = (EditText) findViewById(R.id.ed_totalclasses);
		ed_attendedclasses = (EditText) findViewById(R.id.ed_attendedclasses);
		ed_percentage = (EditText) findViewById(R.id.ed_percentage);

		Bundle extras = getIntent().getExtras();
		if (!(extras == null)) {
			ID = extras.getString("ID");
			FIRST_NAME = extras.getString("FIRST_NAME");
			LAST_NAME = extras.getString("LAST_NAME");
			USERNAME = extras.getString("USERNAME");
			MOBILE = extras.getString("MOBILE");
			ROLE = extras.getString("ROLE");

			studId = extras.getString("studId");
			subjId = extras.getString("subjId");
			attendanceId = extras.getString("attendanceId");
			attendedClasses = extras.getString("attendedClasses");
			totalClasses = extras.getString("totalClasses");
			percentage = extras.getString("percentage");

		}
		ed_totclasses.setText(totalClasses);
		ed_attendedclasses.setText(attendedClasses);
		ed_percentage.setText(percentage);

		list_stud = new ArrayList<String>();
		String name = mySQLiteAdapter.getStudName(studId);
		list_stud.add(name);
		studList = new ArrayList<Personal_Details>();
		studList = mySQLiteAdapter.getStudListwithDetails();
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

		list_subject = new ArrayList<String>();
		String subName = mySQLiteAdapter.getSubName(subjId);
		list_subject.add(subName);
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

				str_attendedclasses = ed_attendedclasses.getText().toString();
				str_totclasses = ed_totclasses.getText().toString();
				str_percentage = ed_percentage.getText().toString();
				
				if (str_attendedclasses.isEmpty()) {
					alertbox("Attendance", "Attended Classes not entered");
				} else if (str_totclasses.isEmpty()) {
					alertbox("Attendance", "Total Classes not entered");
				} else if (str_percentage.isEmpty()) {
					alertbox("Attendance", "Percentage not entered");
				} else {
					int attendClasses = Integer.parseInt(str_attendedclasses);
					int totClasses = Integer.parseInt(str_totclasses);

					if (attendClasses > totClasses) {

						Toast.makeText(getApplicationContext(),
								"Invalid Attended Classes", Toast.LENGTH_SHORT)
								.show();

					} else {

						// To insert candidate values into the database
						boolean result = mySQLiteAdapter.updateAttendance(
								studId, subjId, str_totclasses,
								str_attendedclasses, str_percentage,
								attendanceId);

						if (result == true) {

							Toast.makeText(getApplicationContext(),
									"Attendance updated successfully",
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
