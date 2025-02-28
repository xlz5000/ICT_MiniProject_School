package com.school.model;



public class Student {
	
	private int student_id;
	private String student_name;
	private int login_id;
	
	public Student(int student_id, String student_name, int login_id) {
		this.student_id = student_id;
		this.student_name = student_name;
		this.login_id = login_id;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}



	@Override
	public String toString() {
		return "학생ID: " + student_id + " | 학생명: " + student_name + 
				" | 로그인ID: " + login_id ;
	}
	
	
	

}
