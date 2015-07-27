package com.istudent.model;

public class TimeTable {

	String Id = null;

	public String getId() {
		return Id;
	}

	public void setId(String id) {
		Id = id;
	}

	String Dept = null;
	String Staff = null;
	String Subject = null;
	String Sem = null;
	String Sec = null;
	String Timings = null;
	String Period = null;
	String Weekday = null;

	public String getDept() {
		return Dept;
	}

	public void setDept(String dept) {
		Dept = dept;
	}

	public String getStaff() {
		return Staff;
	}

	public void setStaff(String staff) {
		Staff = staff;
	}

	public String getSubject() {
		return Subject;
	}

	public void setSubject(String subject) {
		Subject = subject;
	}

	public String getSem() {
		return Sem;
	}

	public void setSem(String sem) {
		Sem = sem;
	}

	public String getSec() {
		return Sec;
	}

	public void setSec(String sec) {
		Sec = sec;
	}

	public String getTimings() {
		return Timings;
	}

	public void setTimings(String timings) {
		Timings = timings;
	}

	public String getPeriod() {
		return Period;
	}

	public void setPeriod(String period) {
		Period = period;
	}

	public String getWeekday() {
		return Weekday;
	}

	public void setWeekday(String weekday) {
		Weekday = weekday;
	}

}
