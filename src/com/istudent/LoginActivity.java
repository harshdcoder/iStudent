package com.istudent;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.istudent.database.DatabaseHandler;
import com.istudent.model.Personal_Details;

public class LoginActivity extends Activity {

	EditText et_username = null;
	EditText et_password = null;
	RadioGroup radiogroup = null;

	private DatabaseHandler mySQLiteAdapter;

	String str_firstName = null;
	String str_lastName = null;
	String str_username = null;
	String str_mobile = null;
	String str_role = null;
	String str_id = null;
	String str_password = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		// To connect to database
		mySQLiteAdapter = new DatabaseHandler(getApplicationContext());

		Button btn_reset = (Button) findViewById(R.id.btn_reset);
		Button btn_login = (Button) findViewById(R.id.login);

		et_username = (EditText) findViewById(R.id.username);
		et_password = (EditText) findViewById(R.id.password);
		radiogroup = (RadioGroup) findViewById(R.id.grp_role);

		// Reset button functionality
		btn_reset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Log.i("Login Activity", "Reset button clicked");
				et_username.setText("");
				et_password.setText("");

			}
		});

		// Login button functionality
		btn_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				int checkedId = radiogroup.getCheckedRadioButtonId();

				if (checkedId == R.id.rb_student) {
					str_role = "Student";
				} else if (checkedId == R.id.rb_staff) {
					str_role = "Staff";

				} else if (checkedId == R.id.rb_admin) {
					str_role = "Admin";

				}

				Log.i("Login Activity", "Login button clicked");

				String str_username = et_username.getText().toString();
				String str_password = et_password.getText().toString();

				if (str_username.isEmpty() && str_password.isEmpty()) {

					Log.i("Login Activity", "enter username and password");

					Toast.makeText(getApplicationContext(),
							"enter username and password", Toast.LENGTH_SHORT)
							.show();

				} else if (str_username.isEmpty()) {

					Log.i("Login Activity", "enter username");
					Toast.makeText(getApplicationContext(), "enter username ",
							Toast.LENGTH_SHORT).show();

				} else if (str_password.isEmpty()) {
					Log.i("Login Activity", "enter password");
					Toast.makeText(getApplicationContext(), "enter password",
							Toast.LENGTH_SHORT).show();

				} else if (str_username.equalsIgnoreCase("Abhineel")
						&& str_password.equals("mind")) {

					if (str_role.equals("Student") || str_role.equals("Staff")) {
						Toast.makeText(getApplicationContext(),
								"Select the valid role", Toast.LENGTH_SHORT)
								.show();
					}

					else {
						Intent i = new Intent(LoginActivity.this,
								AdminActivity.class);
						i.putExtra("ID", "Admin");
						i.putExtra("FIRST_NAME", "Abhineel");
						i.putExtra("LAST_NAME", "Rai");
						i.putExtra("USERNAME", "Abhineel");
						i.putExtra("ROLE", "Admin");
						i.putExtra("MOBILE", "9845909908");
						startActivity(i);
					}

				} else {

					List<Personal_Details> candidateDetails = mySQLiteAdapter
							.login(str_username, str_password, str_role);
					if (!(candidateDetails == null)) {
						int size = candidateDetails.size();
						for (int i = 0; i < size; i++) {
							str_id = candidateDetails.get(i).getId();
							str_firstName = candidateDetails.get(i)
									.getFirstName();
							str_lastName = candidateDetails.get(i)
									.getLastName();
							str_mobile = candidateDetails.get(i).getMobile();
							str_role = candidateDetails.get(i).getRole();

						}

						// str_role = "Student";

						if (!(size == 0)) {

							if (str_role.equalsIgnoreCase("Student")) {

								Toast.makeText(getApplicationContext(),
										"Student", Toast.LENGTH_SHORT).show();

								Intent i = new Intent(LoginActivity.this,
										StudentActivity.class);
								i.putExtra("ID", str_id);
								i.putExtra("FIRST_NAME", str_firstName);
								i.putExtra("LAST_NAME", str_lastName);
								i.putExtra("USERNAME", str_username);
								i.putExtra("ROLE", str_role);
								i.putExtra("MOBILE", str_mobile);
								startActivity(i);

							} else if (str_role.equalsIgnoreCase("Staff")) {

								Toast.makeText(getApplicationContext(),
										"Staff", Toast.LENGTH_SHORT).show();

								Intent i = new Intent(LoginActivity.this,
										StaffActivity.class);
								i.putExtra("ID", str_id);
								i.putExtra("FIRST_NAME", str_firstName);
								i.putExtra("LAST_NAME", str_lastName);
								i.putExtra("USERNAME", str_username);
								i.putExtra("ROLE", str_role);
								i.putExtra("MOBILE", str_mobile);
								startActivity(i);
							}
							// else if (str_role.equalsIgnoreCase("Admin")) {
							//
							// Toast.makeText(getApplicationContext(),
							// "Admin", Toast.LENGTH_SHORT).show();
							//
							// Intent i = new Intent(LoginActivity.this,
							// AdminActivity.class);
							// i.putExtra("ID", str_id);
							// i.putExtra("FIRST_NAME", str_firstName);
							// i.putExtra("LAST_NAME", str_lastName);
							// i.putExtra("USERNAME", str_username);
							// i.putExtra("ROLE", str_role);
							// i.putExtra("MOBILE", str_mobile);
							// startActivity(i);
							//
							// }
						} else {
							Toast.makeText(getApplicationContext(),
									"Invalid Username & Password",
									Toast.LENGTH_SHORT).show();

						}
					} else {
						Toast.makeText(getApplicationContext(),
								"Invalid Username & Password",
								Toast.LENGTH_SHORT).show();
					}
				}

			}
		});

	}
}
