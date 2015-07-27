package com.istudent.fragment.admin;

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

public class RegisterStaffFragment extends Fragment {

	EditText firstName = null;
	EditText lastName = null;
	EditText etUsername = null;
	EditText mobile = null;
	Spinner spinner_dept = null;
	Spinner spinner_title = null;
	EditText title = null;
	Button btn_create = null;

	String str_firstName = null;
	String str_lastName = null;
	String str_username = null;
	String str_mobile = null;

	String str_role = null;
	String str_id = null;
	String str_password = null;
	String deptId = null;
	String str_title = null;

	View rootView = null;

	private DatabaseHandler mySQLiteAdapter;
	List<String> list_dept = null;
	List<String> list_title = null;

	public RegisterStaffFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());

		rootView = inflater.inflate(R.layout.fragment_register_staff,
				container, false);

		btn_create = (Button) rootView.findViewById(R.id.bt_submit);
		firstName = (EditText) rootView.findViewById(R.id.et_firstname);
		lastName = (EditText) rootView.findViewById(R.id.et_lastname);
		etUsername = (EditText) rootView.findViewById(R.id.et_username);
		mobile = (EditText) rootView.findViewById(R.id.et_mobile);
		spinner_dept = (Spinner) rootView.findViewById(R.id.spinner_dept);
		spinner_title = (Spinner) rootView.findViewById(R.id.spinner_title);

		String ID = getArguments().getString("ID");
		String FIRST_NAME = getArguments().getString("FIRST_NAME");
		String LAST_NAME = getArguments().getString("LAST_NAME");
		String USERNAME = getArguments().getString("USERNAME");
		String MOBILE = getArguments().getString("MOBILE");
		String ROLE = getArguments().getString("ROLE");

		list_dept = new ArrayList<String>();
		list_title = new ArrayList<String>();
		list_title.add("Mr.");
		list_title.add("Ms.");
		list_title.add("Mrs.");
		// list_title.add("K.");
		loadSpinnerData();

		ArrayAdapter<String> dataAdapterDept = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_dept);

		dataAdapterDept
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_dept.setAdapter(dataAdapterDept);

		ArrayAdapter<String> dataAdapterTitle = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list_title);

		dataAdapterTitle
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

		spinner_title.setAdapter(dataAdapterTitle);

		// Spinner item selection Listener
		addListenerOnSpinnerItemSelection();

		// Button click Listener
		addListenerOnButton();

		return rootView;
	}

	private void loadSpinnerData() {

		list_dept = mySQLiteAdapter.getAllDept();

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

	// Add spinner data

	public void addListenerOnSpinnerItemSelection() {

		spinner_dept
				.setOnItemSelectedListener(new CustomOnItemSelectedListener());

	}

	// get the selected dropdown list value

	public void addListenerOnButton() {

		spinner_dept = (Spinner) rootView.findViewById(R.id.spinner_dept);

		btn_create = (Button) rootView.findViewById(R.id.bt_submit);

		btn_create.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : " + "\n"
				// + String.valueOf(spinner_sem.getSelectedItem()),
				// Toast.LENGTH_LONG).show();

				// Toast.makeText(
				// getActivity(),
				// "On Button Click : "
				// + "\n"
				// + String.valueOf(spinner_dept.getSelectedItem()),
				// Toast.LENGTH_LONG).show();

				String deptKey = String.valueOf(spinner_dept.getSelectedItem());
				deptId = mySQLiteAdapter.getDeptId(deptKey);

				// To get all the input values entered by the user
				str_firstName = firstName.getText().toString();
				str_lastName = lastName.getText().toString();
				str_username = etUsername.getText().toString();
				str_mobile = mobile.getText().toString();
				// str_title = title.getText().toString();

				str_title = String.valueOf(spinner_title.getSelectedItem());
				if (str_firstName.isEmpty()) {
					alertbox("User", "Please enter first name");
				} else if (str_lastName.isEmpty()) {
					alertbox("User", "Please enter last name");
				} else if (str_username.isEmpty()) {
					alertbox("User", "Please enter Email");
				} else if (str_mobile.isEmpty()) {
					alertbox("User", "Please enter mobile number");
				} else {
					// To connect to database
					mySQLiteAdapter = new DatabaseHandler(getActivity());

					// To automatically generate the id's of staff, student and
					// admin
					str_role = "Staff";
					str_id = mySQLiteAdapter.getID(str_role);
					// Toast.makeText(getActivity(), str_id, Toast.LENGTH_SHORT)
					// .show();

					// To set the default password of new candidate
					str_password = "mind";

					// To insert candidate values into the database
					boolean result = mySQLiteAdapter.onInsertStaff(str_id,
							str_firstName, str_lastName, str_username,
							str_mobile, str_role, deptId, str_title);

					if (result == true) {

						Toast.makeText(
								getActivity(),
								str_firstName
										+ "("
										+ str_role
										+ ")  created"
										+ " successfully\n Default Password = mind",
								Toast.LENGTH_SHORT).show();

					} else {
						Toast.makeText(getActivity(), "Record not inserted ",
								Toast.LENGTH_SHORT).show();
					}

				}
			}

		});

	}

}
