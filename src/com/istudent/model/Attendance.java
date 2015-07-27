package com.istudent.model;

public class Attendance {

	String ATTENDANCE_Id;
	String STUD_ID;
	String SUBJ_Id;
	String TOTAL_CLASSES;
	String ATTENDED_CLASSES;
	String PERCENTAGE;

	public String getATTENDANCE_Id() {
		return ATTENDANCE_Id;
	}

	public void setATTENDANCE_Id(String aTTENDANCE_Id) {
		ATTENDANCE_Id = aTTENDANCE_Id;
	}

	public String getSTUD_ID() {
		return STUD_ID;
	}

	public void setSTUD_ID(String sTUD_ID) {
		STUD_ID = sTUD_ID;
	}

	public String getSUBJ_Id() {
		return SUBJ_Id;
	}

	public void setSUBJ_Id(String sUBJ_Id) {
		SUBJ_Id = sUBJ_Id;
	}

	public String getTOTAL_CLASSES() {
		return TOTAL_CLASSES;
	}

	public void setTOTAL_CLASSES(String tOTAL_CLASSES) {
		TOTAL_CLASSES = tOTAL_CLASSES;
	}

	public String getATTENDED_CLASSES() {
		return ATTENDED_CLASSES;
	}

	public void setATTENDED_CLASSES(String aTTENDED_CLASSES) {
		ATTENDED_CLASSES = aTTENDED_CLASSES;
	}

	public String getPERCENTAGE() {
		return PERCENTAGE;
	}

	public void setPERCENTAGE(String pERCENTAGE) {
		PERCENTAGE = pERCENTAGE;
	}

}
