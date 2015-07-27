package com.istudent.fragment.staff;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.istudent.R;
import com.istudent.database.DatabaseHandler;

public class ChangeStaffPasswordFragment extends Fragment {

	EditText currentField = null;
	EditText newField = null;
	EditText confirmField = null;

	String currentText = null;
	String newText = null;
	String confirmText = null;

	Button btn_change = null;
	private DatabaseHandler mySQLiteAdapter;

	String id = null;
	String ROLE = null;

	public ChangeStaffPasswordFragment() {
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_change_password,
				container, false);

		currentField = (EditText) rootView
				.findViewById(R.id.et_current_password);
		newField = (EditText) rootView.findViewById(R.id.et_new_password);
		confirmField = (EditText) rootView.findViewById(R.id.et_password_again);

		btn_change = (Button) rootView.findViewById(R.id.btn_change_password);
		id = getArguments().getString("ID");
		String FIRST_NAME = getArguments().getString("FIRST_NAME");
		String LAST_NAME = getArguments().getString("LAST_NAME");
		String USERNAME = getArguments().getString("USERNAME");
		String MOBILE = getArguments().getString("MOBILE");
		ROLE = getArguments().getString("ROLE");

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getActivity());

		btn_change.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				currentText = currentField.getText().toString();
				newText = newField.getText().toString();
				confirmText = confirmField.getText().toString();

				if (currentText.isEmpty() && newText.isEmpty()
						&& confirmText.isEmpty()) {
					Toast.makeText(getActivity(), "enter all the fields",
							Toast.LENGTH_SHORT).show();

				} else if (currentText.isEmpty()) {
					Toast.makeText(getActivity(), "enter current password",
							Toast.LENGTH_SHORT).show();

				} else if (newText.isEmpty()) {
					Toast.makeText(getActivity(), "enter new password",
							Toast.LENGTH_SHORT).show();

				} else if (confirmText.isEmpty()) {
					Toast.makeText(getActivity(), "enter password again",
							Toast.LENGTH_SHORT).show();

				} else if (!(newText.equals(confirmText))) {
					Toast.makeText(getActivity(),
							"New password and password again are not same ",
							Toast.LENGTH_SHORT).show();

				} else if ((newText.equalsIgnoreCase(currentText))) {

					Toast.makeText(getActivity(),
							"New password is  same as current password ",
							Toast.LENGTH_SHORT).show();
				} else {
					String pass = null;
					List<String> list = mySQLiteAdapter.getPassword(id,ROLE);
					for (int i = 0; i < list.size(); i++) {

						pass = list.get(i) + ",";

					}
					if (!(pass.equalsIgnoreCase(currentText))) {
						boolean change = mySQLiteAdapter.changePassword(id,
								newText, ROLE);
						if (change == true) {
							Toast.makeText(getActivity(),
									"Password changed successfully ",
									Toast.LENGTH_SHORT).show();
						} else {

							Toast.makeText(getActivity(), "Try Again ",
									Toast.LENGTH_SHORT).show();
						}
					} else {
						Toast.makeText(getActivity(),
								"Invalid Current Password ", Toast.LENGTH_SHORT)
								.show();

					}
				}
			}
		});

		return rootView;
	}
}