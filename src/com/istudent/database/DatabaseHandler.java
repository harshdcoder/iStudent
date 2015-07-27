package com.istudent.database;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.istudent.model.Attendance;
import com.istudent.model.Events;
import com.istudent.model.Marks;
import com.istudent.model.Personal_Details;
import com.istudent.model.TimeTable;

public class DatabaseHandler extends SQLiteOpenHelper {

	private static String DB_NAME = "/data/data/com.istudent/databases/iStudentDB2.sqlite";
	public static final int MYDATABASE_VERSION = 1;
	private SQLiteDatabase myDataBase;
	private final Context myContext;

	public String TABLE_User_Student = "Student";
	public String TABLE_User_Staff = "Staff";
	public String TABLE_Events = "Events";
	public String TABLE_Dept = "Department";
	public String TABLE_Subj = "Subject";
	public String TABLE_Attendance = "Attendance";
	public String TABLE_TimeTable = "TimeTable";
	public String TABLE_Marks = "Marks";

	public String TABLE_TimeTable1 = "TimeTable1";

	String CREATE_DEPT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_Dept
			+ " ( Dept_Id VARCHAR PRIMARY KEY  NOT NULL , Dept_Name VARCHAR NOT NULL , Dept_Key VARCHAR NOT NULL)";

	String CREATE_STUDENT_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_User_Student
			+ " ( StudId VARCHAR NOT NULL,FirstName VARCHAR NOT NULL ,LastName VARCHAR NOT NULL ,Username VARCHAR NOT NULL , Dept_Id references Department(Dept_Id),SEM VARCHAR NOT NULL, Mobile VARCHAR NOT NULL,Password VARCHAR NOT NULL)";

	String CREATE_STAFF_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_User_Staff
			+ " ( StaffId VARCHAR NOT NULL,FirstName VARCHAR NOT NULL ,LastName VARCHAR NOT NULL ,Username VARCHAR NOT NULL , Dept_Id references Department(Dept_Id), Mobile VARCHAR NOT NULL ,TITLE VARCHAR NOT NULL,Password VARCHAR NOT NULL)";

	String CREATE_SUBJ_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_Subj
			+ " ( SUBJ_Id Integer PRIMARY KEY  AUTOINCREMENT , Subj_Name VARCHAR NOT NULL , Dep_Id references Department(DEPT_Id),Staff_Id references Staff(StaffId), SEM VARCHAR NOT NULL)";

	String CREATE_ATTENDANCE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_Attendance
			+ " (ATTENDANCE_Id Integer PRIMARY KEY  AUTOINCREMENT , STUD_ID references Student(StudId) , SUBJ_Id references Subject(SUBJ_Id), TOTAL_CLASSES INTEGER NOT NULL, ATTENDED_CLASSES INTEGER, PERCENTAGE VARCHAR)";

	String CREATE_TIMETABLE_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_TimeTable
			+ " (TT_Id Integer PRIMARY KEY  AUTOINCREMENT, Staff_Id references Staff(StaffId) , SUBJ_Id references Subject(SUBJ_Id), TIMINGS VARCHAR NOT NULL, PERIOD INTEGER, WEEKDAY INTEGER)";

	String CREATE_MARKS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_Marks
			+ " (Marks_Id Integer PRIMARY KEY  AUTOINCREMENT , Max_MARKS INTEGER , MARKS_OBTAINED INTEGER, STUD_ID references Student(StudId)  ,SUBJ_Id references Subject(SUBJ_Id))";

	String CREATE_EVENTS_TABLE = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_Events
			+ " ( Event_Id Integer PRIMARY KEY  AUTOINCREMENT , Event_Name VARCHAR NOT NULL , Start_Date DATETIME NOT NULL , End_Date DATETIME NOT NULL , EVENT_MESSAGE VARCHAR,Event_Time VARCHAR NOT NULL,VENUE VARCHAR NOT NULL, Dept_Id references Department(DEPT_Id))";

	String insertQuery_Dept = "Insert into Department (DEPT_Id,Dept_Name,Dept_Key) values ('DEPT1','Computers','CSE'),('DEPT2','Information Science','ISE')";

	// String insertQuery_User =
	// "Insert into Personal_Details (UserId,FirstName,LastName,Username,SEM,Dept,Mobile,Role,Password,OldPassword) values ('Admin1','Abhineel','DM','abra11is@cmrit.ac.in','1','DEPT1','9900165295','Admin','mind','xyz')";

	String subjQuery1 = "insert into Subject (Subj_Name, Dep_Id,Staff_Id, SEM) values ('Software Engineering','DEPT1','Staff1','5')";
	String subjQuery2 = "insert into Subject (Subj_Name, Dep_Id,Staff_Id, SEM) values ('Systems Software','DEPT1','Staff1','5')";
	String subjQuery3 = "insert into Subject (Subj_Name, Dep_Id,Staff_Id, SEM) values ('Operating Systems','DEPT1','Staff1','5')";
	String subjQuery4 = "insert into Subject (Subj_Name, Dep_Id,Staff_Id, SEM) values ('Compiler Design','DEPT2','Staff2','6')";
	String subjQuery5 = "insert into Subject (Subj_Name, Dep_Id,Staff_Id, SEM) values ('Computer Networks -II','DEPT2','Staff2','6')";
	String subjQuery6 = "insert into Subject (Subj_Name, Dep_Id,Staff_Id, SEM) values ('Computer Graphics','DEPT2','Staff2','6')";
	

	String CREATE_TIMETABLE_TABLE1 = "CREATE TABLE IF NOT EXISTS "
			+ TABLE_TimeTable1
			+ " (TT_Id Integer PRIMARY KEY  AUTOINCREMENT, Dep_Id references Department(DEPT_Id), Staff_Id references Staff(StaffId) , SUBJ_Id references Subject(SUBJ_Id), SEM VARCHAR NOT NULL, SECTION VARCHAR NOT NULL, TIMINGS VARCHAR NOT NULL, PERIOD INTEGER, WEEKDAY INTEGER)";

	String CREATE_FORUM = "Create Table IF NOT EXISTS FORUM(Id Integer PRIMARY KEY  AUTOINCREMENT, STUD_ID references Student(StudId),MESSAGE VARCHAR NOT NULL)";

	public DatabaseHandler(Context context) {
		super(context, DB_NAME, null, 1);
		myContext = context;
		try {
			myDataBase = myContext.openOrCreateDatabase(DB_NAME, 1, null);

			try {
				myDataBase.execSQL(CREATE_DEPT_TABLE);
				myDataBase.execSQL(CREATE_STUDENT_TABLE);
				myDataBase.execSQL(CREATE_STAFF_TABLE);
				myDataBase.execSQL(CREATE_EVENTS_TABLE);
				myDataBase.execSQL(CREATE_SUBJ_TABLE);
				myDataBase.execSQL(CREATE_ATTENDANCE_TABLE);
				myDataBase.execSQL(CREATE_TIMETABLE_TABLE);
				myDataBase.execSQL(CREATE_TIMETABLE_TABLE1);
				myDataBase.execSQL(CREATE_MARKS_TABLE);
				myDataBase.execSQL(CREATE_FORUM);

				myDataBase.execSQL(insertQuery_Dept);
				myDataBase.execSQL(subjQuery1);
				myDataBase.execSQL(subjQuery2);
				myDataBase.execSQL(subjQuery3);
				myDataBase.execSQL(subjQuery4);
				myDataBase.execSQL(subjQuery5);
				myDataBase.execSQL(subjQuery6);
				
				// myDataBase.execSQL(insertQuery_User);

			} catch (Exception e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public String getID(String role) {

		boolean status = false;
		String query = null;
		if (role.equals("Student")) {
			query = "select count(StudId) from " + TABLE_User_Student;
		} else if (role.equals("Staff")) {
			query = "select count(StaffId) from " + TABLE_User_Staff;
		}

		int id = 0;
		String str_id = null;

		System.out.println(query);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				status = true;
			} while (c.moveToNext());

		}

		if (status == false) {
			id = 1;

		} else {
			id = id + 1;
		}

		str_id = role + id;
		System.out.println(query);

		return str_id;
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		try {

		} catch (Exception e) {

			e.printStackTrace();
		}

	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		Log.w("DATABASE UPGRADE", "Upgrading database from version "
				+ oldVersion + " to " + newVersion
				+ ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_User_Student);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_Events);
		onCreate(db);

	}

	public boolean onInsertStudent(String str_id, String str_firstName,
			String str_lastName, String str_username, String str_mobile,
			String str_role, String deptId, String sem) {

		String idText = str_id;
		String passwordText = "mind";
		String firstNameText = str_firstName;
		String lastNameText = str_lastName;
		String usernameText = str_username;
		String mobileText = str_mobile;

		// String roleText = str_role;

		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into "
					+ TABLE_User_Student
					+ "(StudId,FirstName,LastName,Username,Mobile,Password,Dept_Id,SEM) values ('"
					+ idText + "','" + firstNameText + "','" + lastNameText
					+ "','" + usernameText + "','" + mobileText + "','"
					+ passwordText + "','" + deptId + "','" + sem + "')";
			System.out.println("insertTable:" + insertTable);

			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}

	}

	public boolean onInsertStaff(String str_id, String str_firstName,
			String str_lastName, String str_username, String str_mobile,
			String str_role, String deptId, String str_title) {

		String idText = str_id;
		String passwordText = "mind";
		String firstNameText = str_firstName;
		String lastNameText = str_lastName;
		String usernameText = str_username;
		String mobileText = str_mobile;

		// String roleText = str_role;

		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into "
					+ TABLE_User_Staff
					+ "(StaffId,FirstName,LastName,Username,Mobile,Password,Dept_Id,TITLE) values ('"
					+ idText + "','" + firstNameText + "','" + lastNameText
					+ "','" + usernameText + "','" + mobileText + "','"
					+ passwordText + "','" + deptId + "','" + str_title + "')";
			System.out.println("insertTable:" + insertTable);

			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}

	}

	public List<Personal_Details> login(String userID, String password,
			String role) {

		String user = userID;
		String passWord = password;
		boolean flag = false;
		String query;

		List<Personal_Details> obj_personal = new ArrayList<Personal_Details>();

		if (role == "Student") {

			query = "SELECT StudId,FirstName,LastName,Username,Mobile,Password,Dept_Id,SEM FROM "
					+ TABLE_User_Student + " where Username='" + user + "'";

			Cursor c = myDataBase.rawQuery(query, null);

			if (c != null) {
				if (c.moveToNext()) {
					do {
						String userName = c.getString(c
								.getColumnIndex("Username"));
						String pass = c.getString(c.getColumnIndex("Password"));
						String id = c.getString(c.getColumnIndex("StudId"));
						String fname = c.getString(c
								.getColumnIndex("FirstName"));
						String lname = c
								.getString(c.getColumnIndex("LastName"));

						String mobile = c.getString(c.getColumnIndex("Mobile"));
						String deptId = c
								.getString(c.getColumnIndex("Dept_Id"));
						String sem = c.getString(c.getColumnIndex("SEM"));

						// logic for login check
						if (userName.equalsIgnoreCase(user)
								&& pass.equalsIgnoreCase(passWord)) {
							flag = true;

							Personal_Details obj = new Personal_Details();
							obj.setId(id);
							obj.setFirstName(fname);
							obj.setLastName(lname);
							obj.setUsername(userName);
							obj.setMobile(mobile);
							obj.setRole(role);
							obj.setPassword(pass);
							obj.setDeptId(deptId);
							obj.setSem(sem);
							obj_personal.add(obj);

						} else {
							flag = false;

						}

					} while (c.moveToNext());
				}
			}
		} else if (role == "Staff") {

			query = "SELECT StaffId,FirstName,LastName,Username,Mobile,Password,Dept_Id FROM "
					+ TABLE_User_Staff + " where Username='" + user + "'";

			Cursor c = myDataBase.rawQuery(query, null);

			if (c != null) {
				if (c.moveToNext()) {
					do {
						String userName = c.getString(c
								.getColumnIndex("Username"));
						String pass = c.getString(c.getColumnIndex("Password"));
						String id = c.getString(c.getColumnIndex("StaffId"));

						String fname = c.getString(c
								.getColumnIndex("FirstName"));
						String lname = c
								.getString(c.getColumnIndex("LastName"));

						String mobile = c.getString(c.getColumnIndex("Mobile"));
						String deptId = c
								.getString(c.getColumnIndex("Dept_Id"));

						// logic for login check
						if (userName.equalsIgnoreCase(user)
								&& pass.equalsIgnoreCase(passWord)) {
							flag = true;

							Personal_Details obj = new Personal_Details();
							obj.setId(id);
							obj.setFirstName(fname);
							obj.setLastName(lname);
							obj.setUsername(userName);
							obj.setMobile(mobile);
							obj.setRole(role);
							obj.setPassword(pass);
							obj.setDeptId(deptId);
							obj_personal.add(obj);

						} else {
							flag = false;

						}

					} while (c.moveToNext());
				}
			}
		}

		if (flag == true) {
			return obj_personal;
		} else {
			return null;
		}

	}

	public List<String> getAllDept() {

		List<String> list = new ArrayList<String>();

		// Select all query
		String selectQuery = "Select * from Department";
		Log.i("Reset Password", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(2));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public List<String> getForum() {

		List<String> list = new ArrayList<String>();

		// Select all query
		String selectQuery = "Select STUD_ID,Message from forum";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				String id = cursor.getString(0);
				String name = getStudName(id);
				list.add("Name: " + name + "\nMessage: "
						+ cursor.getString(1));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public String getDeptId(String deptKey) {

		String deptId = null;

		// Select all query
		String selectQuery = "Select Dept_Id from " + TABLE_Dept
				+ " where Dept_Key = " + "'" + deptKey + "'";
		Log.i("Reset Password", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				deptId = cursor.getString(0);

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return deptId;
	}

	public String getDeptKey(String deptId) {
		
		String dep_key=null;

		// Select all query
		String selectQuery = "Select Dept_Key from " + TABLE_Dept
				+ " where Dept_Id = " + "'" + deptId + "'";
		Log.i("Reset Password", selectQuery);

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {

				dep_key = cursor.getString(0);

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return dep_key;
	}

	public boolean insertEvent(String eventname, String startdate,
			String enddate, String description, String venue, String strTime,
			String deptId) {
		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into "
					+ TABLE_Events
					+ "(Event_Name,Start_Date ,End_Date,EVENT_MESSAGE,EVENT_TIME,VENUE,Dept_id) values ('"
					+ eventname + "','" + startdate + "','" + enddate + "','"
					+ description + "','" + strTime + "','" + venue + "','"
					+ deptId + "')";
			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}

	}

	public String createEventid() {
		boolean status = false;
		int id = 0;
		String str_id = null;
		String query = "select count(Event_Id) from " + TABLE_Events + "";
		System.out.println(query);
		SQLiteDatabase db = this.getWritableDatabase();
		Cursor c = db.rawQuery(query, null);
		if (c.moveToFirst()) {
			do {
				id = c.getInt(0);
				status = true;
			} while (c.moveToNext());

		}

		if (status == false) {
			id = 1;

		} else {
			id = id + 1;
		}

		str_id = "Event" + id;
		System.out.println(query);

		return str_id;

	}

	public List<String> getEventName() {

		List<String> list = new ArrayList<String>();

		String status = "Pending";
		// Select all query
		String selectQuery = "Select Event_Name from " + TABLE_Events
				+ " where Status = " + "'" + status + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public List<String> getEventDetails(String selected_event) {
		List<String> list = new ArrayList<String>();

		// Select all query
		String selectQuery = "Select * from " + TABLE_Events
				+ " where Event_Name = " + "'" + selected_event + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(1));
				list.add(cursor.getString(2));
				list.add(cursor.getString(3));
				list.add(cursor.getString(4));
				// list.add(cursor.getString(5));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public List<String> getEmails() {
		List<String> list = new ArrayList<String>();

		String student = "Student";
		// Select all query
		String selectQuery = "Select Username from " + TABLE_User_Student
				+ " where Role = " + "'" + student + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public List<String> getMobile() {
		List<String> list = new ArrayList<String>();

		String student = "Student";
		// Select all query
		String selectQuery = "Select Mobile from " + TABLE_User_Student
				+ " where Role = " + "'" + student + "'";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public List<Personal_Details> getStaffListwithDetails() {
		String idText = null;
		String fnameText = null;
		String lnameText = null;
		String usernameText = null;
		String mobileText = null;
		String Dept_IdText = null;
		String titleText = null;

		List<Personal_Details> obj_allstaff = new ArrayList<Personal_Details>();
		Cursor c = myDataBase.rawQuery(
				"SELECT StaffId,FirstName,LastName,Username,Mobile,Dept_Id,TITLE FROM "
						+ TABLE_User_Staff, null);

		if (c != null) {
			if (c.moveToNext()) {

				do {
					idText = c.getString(c.getColumnIndex("StaffId"));
					fnameText = c.getString(c.getColumnIndex("FirstName"));
					lnameText = c.getString(c.getColumnIndex("LastName"));
					usernameText = c.getString(c.getColumnIndex("Username"));
					mobileText = c.getString(c.getColumnIndex("Mobile"));
					Dept_IdText = c.getString(c.getColumnIndex("Dept_Id"));
					titleText = c.getString(c.getColumnIndex("TITLE"));

					Personal_Details obj = new Personal_Details();
					obj.setId(idText);
					obj.setFirstName(fnameText);
					obj.setLastName(lnameText);
					obj.setUsername(usernameText);
					obj.setMobile(mobileText);
					obj.setTITLE(titleText);

					obj_allstaff.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allstaff;
	}

	public List<Personal_Details> getStudListwithDetails() {

		String idText = null;
		String fnameText = null;
		String lnameText = null;
		String usernameText = null;
		String mobileText = null;
		String Dept_IdText = null;
		String titleText = null;

		List<Personal_Details> obj_allstaff = new ArrayList<Personal_Details>();
		Cursor c = myDataBase.rawQuery(
				"SELECT StudId,FirstName,LastName,Username,Mobile,Dept_Id FROM "
						+ TABLE_User_Student, null);

		if (c != null) {
			if (c.moveToNext()) {

				do {
					idText = c.getString(c.getColumnIndex("StudId"));
					fnameText = c.getString(c.getColumnIndex("FirstName"));
					lnameText = c.getString(c.getColumnIndex("LastName"));
					usernameText = c.getString(c.getColumnIndex("Username"));
					mobileText = c.getString(c.getColumnIndex("Mobile"));
					Dept_IdText = c.getString(c.getColumnIndex("Dept_Id"));
					// titleText = c.getString(c.getColumnIndex("Sem"));

					Personal_Details obj = new Personal_Details();
					obj.setId(idText);
					obj.setFirstName(fnameText);
					obj.setLastName(lnameText);
					obj.setUsername(usernameText);
					obj.setMobile(mobileText);
					// obj.setTITLE(titleText);

					obj_allstaff.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allstaff;
	}

	// public boolean updateUser(String fnameNew, String lanameNew,
	// String emailNew, String mobileNew, String roleNew,
	// String addressNew, String id) {
	//
	// String updateQuery = "UPDATE   " + TABLE_User_Student
	// + " SET  FirstName = '" + fnameNew + "',  LastName = '"
	// + lanameNew + "',  Username= '" + emailNew + "', Mobile = '"
	// + mobileNew + "',  Role = '" + roleNew + "',  Address = '"
	// + addressNew + "'  WHERE  UserId = '" + id + "'  ";
	//
	// SQLiteDatabase db = this.getWritableDatabase();
	// try {
	// db.execSQL(updateQuery);
	// return true;
	// } catch (SQLException e) {
	//
	// e.printStackTrace();
	// return false;
	// }
	// }

	// public boolean deleteUser(String selected_name, String role_selected,
	// String id) {
	//
	// boolean result = false;
	// try {
	// String selectquery = "delete from  " + TABLE_User_Student
	// + " where UserId='" + id + "'";
	// System.out.println("Delete query " + selectquery);
	// SQLiteDatabase db = this.getWritableDatabase();
	//
	// myDataBase.execSQL(selectquery);
	//
	// result = true;
	// } catch (Exception e) {
	//
	// e.printStackTrace();
	//
	// result = false;
	// }
	//
	// return result;
	//
	// }
	public List<Attendance> getAttendance(String StudId) {

		String ATTENDANCE_Id;
		String STUD_ID;
		String str_subject;
		String TOTAL_CLASSES;
		String ATTENDED_CLASSES;
		String PERCENTAGE;

		List<Attendance> obj_allevetns = new ArrayList<Attendance>();
		Cursor c = myDataBase
				.rawQuery(
						"SELECT ATTENDANCE_Id,STUD_ID,SUBJ_Id,TOTAL_CLASSES,ATTENDED_CLASSES,PERCENTAGE FROM "
								+ TABLE_Attendance
								+ " where STUD_ID='"
								+ StudId + "'", null);
		if (c != null) {
			if (c.moveToNext()) {

				do {
					ATTENDANCE_Id = c.getString(c
							.getColumnIndex("ATTENDANCE_Id"));
					STUD_ID = c.getString(c.getColumnIndex("STUD_ID"));
					String id = c.getString(2);
					str_subject = getSubName(id);
					TOTAL_CLASSES = c.getString(c
							.getColumnIndex("TOTAL_CLASSES"));

					ATTENDED_CLASSES = c.getString(c
							.getColumnIndex("ATTENDED_CLASSES"));
					PERCENTAGE = c.getString(c.getColumnIndex("PERCENTAGE"));

					Attendance obj = new Attendance();
					obj.setATTENDANCE_Id(ATTENDANCE_Id);
					obj.setSTUD_ID(STUD_ID);
					obj.setSUBJ_Id(str_subject);
					obj.setTOTAL_CLASSES(TOTAL_CLASSES);
					obj.setATTENDED_CLASSES(ATTENDED_CLASSES);
					obj.setPERCENTAGE(PERCENTAGE);
					obj_allevetns.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allevetns;
	}

	public List<Attendance> getAttendance() {

		String ATTENDANCE_Id;
		String STUD_ID;
		String SUBJ_Id;
		String TOTAL_CLASSES;
		String ATTENDED_CLASSES;
		String PERCENTAGE;

		List<Attendance> obj_allevetns = new ArrayList<Attendance>();
		Cursor c = myDataBase
				.rawQuery(
						"SELECT ATTENDANCE_Id,STUD_ID,SUBJ_Id,TOTAL_CLASSES,ATTENDED_CLASSES,PERCENTAGE FROM "
								+ TABLE_Attendance, null);
		if (c != null) {
			if (c.moveToNext()) {

				do {
					ATTENDANCE_Id = c.getString(c
							.getColumnIndex("ATTENDANCE_Id"));
					STUD_ID = c.getString(c.getColumnIndex("STUD_ID"));
					SUBJ_Id = c.getString(c.getColumnIndex("SUBJ_Id"));
					TOTAL_CLASSES = c.getString(c
							.getColumnIndex("TOTAL_CLASSES"));

					ATTENDED_CLASSES = c.getString(c
							.getColumnIndex("ATTENDED_CLASSES"));
					PERCENTAGE = c.getString(c.getColumnIndex("PERCENTAGE"));

					Attendance obj = new Attendance();
					obj.setATTENDANCE_Id(ATTENDANCE_Id);
					obj.setSTUD_ID(STUD_ID);
					obj.setSUBJ_Id(SUBJ_Id);
					obj.setTOTAL_CLASSES(TOTAL_CLASSES);
					obj.setATTENDED_CLASSES(ATTENDED_CLASSES);
					obj.setPERCENTAGE(PERCENTAGE);
					obj_allevetns.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allevetns;
	}

	public List<Marks> getMarks() {

		String Marks_Id = null;
		String Max_MARKS = null;
		String MARKS_OBTAINED = null;
		String SUBJ_Id = null;
		String StudId = null;
		List<Marks> obj_allevetns = new ArrayList<Marks>();
		Cursor c = myDataBase.rawQuery(
				"SELECT Marks_Id,Max_MARKS,MARKS_OBTAINED,SUBJ_Id,STUD_ID FROM "
						+ TABLE_Marks, null);
		if (c != null) {
			if (c.moveToNext()) {

				do {
					Marks_Id = c.getString(c.getColumnIndex("Marks_Id"));
					Max_MARKS = c.getString(c.getColumnIndex("Max_MARKS"));
					MARKS_OBTAINED = c.getString(c
							.getColumnIndex("MARKS_OBTAINED"));
					StudId = c.getString(c.getColumnIndex("STUD_ID"));
					SUBJ_Id = c.getString(c.getColumnIndex("SUBJ_Id"));
				//	SUBJ_Id = getSubjId(SUBJ_Id);

					Marks obj = new Marks();
					obj.setMarksId(Marks_Id);
					obj.setStudId(StudId);
					obj.setSubjId(SUBJ_Id);
					obj.setMaxMarks(Max_MARKS);
					obj.setMarksObtained(MARKS_OBTAINED);
					obj_allevetns.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allevetns;
	}

	public List<Marks> getMarks(String StudId) {

		String Marks_Id;
		String Max_MARKS;
		String MARKS_OBTAINED;
		String STUD_ID;
		String str_subject;

		List<Marks> obj_allevetns = new ArrayList<Marks>();
		Cursor c = myDataBase
				.rawQuery(
						"SELECT Marks_Id,Max_MARKS,MARKS_OBTAINED,STUD_ID,SUBJ_Id FROM "
								+ TABLE_Marks + " where STUD_ID='" + StudId
								+ "'", null);
		if (c != null) {
			if (c.moveToNext()) {

				do {
					Marks_Id = c.getString(c.getColumnIndex("Marks_Id"));
					Max_MARKS = c.getString(c.getColumnIndex("Max_MARKS"));
					MARKS_OBTAINED = c.getString(c
							.getColumnIndex("MARKS_OBTAINED"));
					String id = c.getString(4);
					str_subject = getSubName(id);
					//SUBJ_Id = c.getString(c.getColumnIndex("SUBJ_Id"));
					//SUBJ_Id = getSubjId(SUBJ_Id);
					STUD_ID = c.getString(c.getColumnIndex("STUD_ID"));

					Marks obj = new Marks();
					obj.setMarksId(Marks_Id);
					obj.setStudId(STUD_ID);
					obj.setSubjId(str_subject);
					obj.setMaxMarks(Max_MARKS);
					obj.setMarksObtained(MARKS_OBTAINED);
					obj_allevetns.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allevetns;
	}

	public List<Events> getAllEvents() {

		String idText = null;
		String nameText = null;
		String sDateText = null;
		String eDateText = null;
		String description = null;
		String venue = null;
		String deptId = null;
		String deptKey = null;
		String time = null;

		List<Events> obj_allevetns = new ArrayList<Events>();
		Cursor c = myDataBase
				.rawQuery(
						"SELECT Event_Id,Event_Name,Start_Date,End_Date,EVENT_MESSAGE,VENUE,Dept_id,Event_Time FROM "
								+ TABLE_Events, null);
		if (c != null) {
			if (c.moveToNext()) {

				do {
					idText = c.getString(c.getColumnIndex("Event_Id"));
					nameText = c.getString(c.getColumnIndex("Event_Name"));
					sDateText = c.getString(c.getColumnIndex("Start_Date"));
					eDateText = c.getString(c.getColumnIndex("End_Date"));
					time = c.getString(c.getColumnIndex("Event_Time"));
					description = c
							.getString(c.getColumnIndex("EVENT_MESSAGE"));
					venue = c.getString(c.getColumnIndex("VENUE"));
					deptId = c.getString(c.getColumnIndex("Dept_Id"));
					deptKey = getDeptKey(deptId);

					Events obj = new Events();
					obj.setEvent_id(idText);
					obj.setEventName(nameText);
					obj.setStartDate(sDateText);
					obj.setEndDate(eDateText);
					obj.setDescription(description);
					obj.setVenue(venue);
					obj.setDeptKey(deptKey);
					obj.setEventTime(time);
					obj_allevetns.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allevetns;
	}

	public List<TimeTable> getTimeTable(String deptId, String semester) {
		
		String str_id = null;
		String str_dept = null;
		String str_staff = null;
		String str_subject = null;
		String str_sem = null;
		String str_sec = null;
		String str_timings = null;
		String str_period = null;
		String str_weekday = null;

		List<TimeTable> obj_alltimeTable = new ArrayList<TimeTable>();
		// Cursor c = myDataBase
		// .rawQuery(
		// "SELECT TT_Id, Dep_Id, Staff_Id,SUBJ_Id,SEM,SECTION,TIMINGS,PERIOD,WEEKDAY   FROM "
		// + TABLE_TimeTable1
		// + " where Dep_Id='"
		// + deptId
		// + "' AND SEM = '" + semester + "'", null);

		Cursor c = myDataBase
				.rawQuery(
						"SELECT TT_Id, Dep_Id, Staff_Id,SUBJ_Id,SEM,SECTION,TIMINGS,PERIOD,WEEKDAY   FROM "
								+ TABLE_TimeTable1, null);

		if (c != null) {
			if (c.moveToNext()) {

				do {
					String id;
					String id1;			
					String id2;
					str_id = c.getString(c.getColumnIndex("TT_Id"));
					
					 id1 = c.getString(1);
					 str_dept = getDeptName(id1);
					
					// str_dept = c.getString(c.getColumnIndex("Dep_Id"));
					
					 id2 = c.getString(2);
					 str_staff = getStaffName(id2);
					
					// str_staff = c.getString(c.getColumnIndex("Staff_Id"));
					// str_subject = c.getString(c.getColumnIndex("SUBJ_Id"));
					id = c.getString(3);
					str_subject = getSubName(id);
					
					str_sem = c.getString(c.getColumnIndex("SEM"));
					str_sec = c.getString(c.getColumnIndex("SECTION"));
					str_timings = c.getString(c.getColumnIndex("TIMINGS"));
					str_period = c.getString(c.getColumnIndex("PERIOD"));
					str_weekday = c.getString(c.getColumnIndex("WEEKDAY"));

					TimeTable obj = new TimeTable();
					obj.setId(str_id);
					obj.setDept(str_dept);
					obj.setStaff(str_staff);
					obj.setSubject(str_subject);
					obj.setSem(str_sem);
					obj.setSec(str_sec);
					obj.setTimings(str_timings);
					obj.setPeriod(str_period);
					obj.setWeekday(str_weekday);

					obj_alltimeTable.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_alltimeTable;
	}

	public List<Events> getEventFromName(String name) {

		String idText = null;
		String nameText = null;
		String sDateText = null;
		String eDateText = null;
		String description = null;

		List<Events> obj_allevetns = new ArrayList<Events>();
		Cursor c = myDataBase.rawQuery(
				"SELECT Event_Id,Event_Name,Start_Date,End_Date,Descripttion FROM "
						+ TABLE_Events + " WHERE EVENT_NAME = '" + name + "'",
				null);
		if (c != null) {
			if (c.moveToNext()) {

				do {
					idText = c.getString(c.getColumnIndex("Event_Id"));
					nameText = c.getString(c.getColumnIndex("Event_Name"));
					sDateText = c.getString(c.getColumnIndex("Start_Date"));
					eDateText = c.getString(c.getColumnIndex("End_Date"));
					description = c.getString(c.getColumnIndex("Descripttion"));

					Events obj = new Events();
					obj.setEvent_id(idText);
					obj.setEventName(nameText);
					obj.setStartDate(sDateText);
					obj.setEndDate(eDateText);
					obj.setDescription(description);

					obj_allevetns.add(obj);

				} while (c.moveToNext());

			}

		}
		return obj_allevetns;
	}

	public boolean resetPassword(String id) {

		String pass = "mca";
		String updateQuery = "UPDATE   " + TABLE_User_Student
				+ " SET  Password = '" + pass + "'  WHERE  Id = '" + id + "'  ";

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}

	}

	public boolean changePassword(String id, String newText, String role) {
		String updateQuery = null;

		if (role.equals("Student")) {
			updateQuery = "UPDATE   " + TABLE_User_Student
					+ " SET  Password = '" + newText + "'  WHERE  StudId = '"
					+ id + "'  ";

		} else if (role.equals("Staff")) {
			updateQuery = "UPDATE  " + TABLE_User_Staff + " SET  Password = '"
					+ newText + "'  WHERE  StaffId = '" + id + "'  ";

		}

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}

	}

	public List<String> getPassword(String id, String role) {
		List<String> list = new ArrayList<String>();

		String selectQuery = "";
		// Select all query

		if (role.equals("Student")) {

			selectQuery = "Select Password from " + TABLE_User_Student
					+ " Where StudId = '" + id + "'  ";

		} else if (role.equals("Staff")) {
			selectQuery = "Select Password from " + TABLE_User_Staff
					+ " Where StaffId = '" + id + "'  ";

		}

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(0));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public boolean updateEvent(String idNew, String nameNew, String sDateNew,
			String eDateNew, String descriptionNew) {

		String updateQuery = "UPDATE   " + TABLE_Events
				+ " SET  EVENT_Name = '" + nameNew + "',  Start_Date = '"
				+ sDateNew + "',  End_Date = '" + eDateNew
				+ "', Descripttion = '" + descriptionNew
				+ "'  WHERE  Event_Id = '" + idNew + "'  ";

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public boolean deleteEvent(String selected_event) {

		boolean result = false;
		try {
			String selectquery = "delete from  " + TABLE_Events
					+ " where Event_Name = '" + selected_event + "'";
			System.out.println("Delete query " + selectquery);
			SQLiteDatabase db = this.getWritableDatabase();

			myDataBase.execSQL(selectquery);

			result = true;
		} catch (Exception e) {

			e.printStackTrace();

			result = false;
		}

		return result;

	}

	public List<String> getAllSubject() {

		List<String> list = new ArrayList<String>();

		String selectQuery = "Select * from Subject";

		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(selectQuery, null);

		if (cursor.moveToFirst()) {
			do {
				list.add(cursor.getString(1));
				System.out.println("Subj name: " + cursor.getString(1));

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();

		return list;
	}

	public String getSubjId(String subjName) {

		int id = 0;
		String str_id = null;
		String query = "select SUBJ_Id from " + TABLE_Subj
				+ " where Subj_Name = '" + subjName + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				str_id = cursor.getString(0);

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();
		return str_id;
	}
	
	
	public String getDeptName(String deptId) {

		int id = 0;
		String str_name = null;
		String query = "select Dept_Key from " + TABLE_Dept
				+ " where DEPT_Id = '" + deptId + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				str_name = cursor.getString(0);

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();
		return str_name;
	}

	public String getSubName(String subjId) {

		int id = 0;
		String str_name = null;
		String query = "select Subj_Name from " + TABLE_Subj
				+ " where SUBJ_Id = '" + subjId + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				str_name = cursor.getString(0);

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();
		return str_name;
	}

	public String getStudName(String studId) {

		int id = 0;
		String str_name = null;
		String query = "select FirstName from " + TABLE_User_Student
				+ " where StudId = '" + studId + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				str_name = cursor.getString(0);

			} while (cursor.moveToNext());

		}

		cursor.close();
		db.close();
		return str_name;
	}

	public String getStaffName(String staffId) {

		int id = 0;
		String str_fname = null;
		String str_lname = null;
		String str_title = null;
		String str_name = null;
		String query = "select FirstName, LastName, TITLE from " + TABLE_User_Staff
				+ " where StaffId = '" + staffId + "'";
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor cursor = db.rawQuery(query, null);

		if (cursor.moveToFirst()) {
			do {
				str_fname = cursor.getString(0);
				str_lname = cursor.getString(1);
				str_title = cursor.getString(2);
				str_name = str_title+" "+str_fname+" "+str_lname;
			} while (cursor.moveToNext());

		}
		
		cursor.close();
		db.close();
		return str_name;
	}

	
	public boolean onInsertTimesheet(String deptId, String staffId,
			String subjId, String semester, String section, String timings,
			String period, String weekday) {

		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into "
					+ TABLE_TimeTable1
					+ "(Dep_Id,Staff_Id ,SUBJ_Id,SEM,SECTION,TIMINGS,PERIOD,WEEKDAY) values ('"
					+ deptId + "','" + staffId + "','" + subjId + "','"
					+ semester + "','" + section + "','" + timings + "','"
					+ period + "','" + weekday + "')";
			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}

	}

	public boolean onInsertMarks(String studId, String subjId,
			String str_maxmarks, String str_marksobtained) {

		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into " + TABLE_Marks
					+ "(Max_MARKS,MARKS_OBTAINED ,STUD_ID,SUBJ_Id) values ('"
					+ str_maxmarks + "','" + str_marksobtained + "','" + studId
					+ "','" + subjId + "')";
			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}
	}

	public boolean onInsertAttendance(String studId, String subjId,
			String str_totclasses, String str_attendedclasses,
			String str_percentage) {
		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into "
					+ TABLE_Attendance
					+ "(TOTAL_CLASSES,ATTENDED_CLASSES ,STUD_ID,SUBJ_Id,PERCENTAGE) values ('"
					+ str_totclasses + "','" + str_attendedclasses + "','"
					+ studId + "','" + subjId + "','" + str_percentage + "')";
			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}
	}

	public boolean insertMessage(String iD, String msg) {
		boolean result = false;

		try {
			SQLiteDatabase db2 = this.getWritableDatabase();

			String insertTable = "insert into forum"
					+ "(STUD_ID,Message) values ('" + iD + "','" + msg + "')";
			db2.execSQL(insertTable);
			result = true;
			return result;

		} catch (SQLException e) {
			result = false;
			e.printStackTrace();
			return result;
		}
	}

	public boolean updateEvent(String evtId, String eventname,
			String startdate, String enddate, String description, String venue,
			String strTime, String deptId) {

		String updateQuery = "UPDATE   " + TABLE_Events
				+ " SET  EVENT_Name = '" + eventname + "',  Start_Date = '"
				+ startdate + "',  End_Date = '" + enddate
				+ "', EVENT_MESSAGE = '" + description + "',Event_Time = '"
				+ strTime + "',VENUE = '" + venue + "',Dept_Id = '" + deptId
				+ "'  WHERE  Event_Id = '" + evtId + "'  ";

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public boolean updateTimeSheet(String deptId, String staffId,
			String subjId, String semester, String section, String timings,
			String period, String weekday, String timeId) {

		String updateQuery = "UPDATE   " + TABLE_TimeTable1
				+ " SET Dep_Id = '" + deptId + "', Staff_Id = '" + staffId 
				+ "', SUBJ_Id = '" + subjId + "', SEM = '" + semester 
				+ "', SECTION = '" + section
				+ "',TIMINGS = '" + timings + "',PERIOD = '" + period
				+ "',WEEKDAY = '" + weekday + "'  WHERE  TT_Id = '" + timeId + "'  ";

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public boolean updateMarks(String studId, String subjId,
			String str_maxmarks, String str_marksobtained, String marksId) {

		String updateQuery = "UPDATE   " + TABLE_Marks + " SET  Max_MARKS = '"
				+ str_maxmarks + "',  MARKS_OBTAINED = '" + str_marksobtained
				+ "',  STUD_ID = '" + studId + "', SUBJ_Id = '" + subjId
				+ "' WHERE  Marks_Id = '" + marksId + "'  ";

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}
	}

	public boolean updateAttendance(String studId, String subjId,
			String str_totclasses, String str_attendedclasses,
			String str_percentage, String attendanceId) {

		String updateQuery = "UPDATE   " + TABLE_Attendance
				+ " SET  TOTAL_CLASSES = '" + str_totclasses
				+ "',  ATTENDED_CLASSES = '" + str_attendedclasses
				+ "',PERCENTAGE = '" + str_percentage + "',  STUD_ID = '"
				+ studId + "', SUBJ_Id = '" + subjId
				+ "' WHERE  ATTENDANCE_Id = '" + attendanceId + "'  ";

		SQLiteDatabase db = this.getWritableDatabase();
		try {
			db.execSQL(updateQuery);
			return true;
		} catch (SQLException e) {

			e.printStackTrace();
			return false;
		}

	}

}
