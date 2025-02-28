package com.school.model;

public class Subject {
	
private int subject_id;
private String subject_name;
private int teacher_id;
	


	public Subject(int subject_id, String subject_name, int teacher_id) {
		this.subject_id = subject_id;
		this.subject_name = subject_name;
		this.teacher_id = teacher_id;
	}



	public int getSubject_id() {
		return subject_id;
	}


	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	
	public String getSubject_name() {
		return subject_name;
	}
	
	
	public void setSubject_name(String subject_name) {
		this.subject_name = subject_name;
	}

	public int getTeacher_id() {
		return teacher_id;
	}



	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}



	public String toStringByTeacher() {
		return "과목ID: "+subject_id+" | 과목명: "+subject_name;
	}
	

	@Override
	public String toString() {
		return "과목ID : " + subject_id + " | 과목명 :  " + subject_name + 
				" |선생님 ID : " + teacher_id ;
	}
}
