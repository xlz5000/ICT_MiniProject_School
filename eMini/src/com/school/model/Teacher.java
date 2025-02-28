package com.school.model;

public class Teacher {
	
	private int teacher_id;
	private String teacher_name;
	private int login_id;
	
	public Teacher(int teacher_id, String teacher_name, int login_id) {
		this.teacher_id = teacher_id;
		this.teacher_name = teacher_name;
		this.login_id = login_id;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public int getLogin_id() {
		return login_id;
	}

	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}





	@Override
	public String toString() {
		return "교사ID : " + teacher_id + " | 교사명 : " + teacher_name + 
				" | 로그인 ID : " + login_id ;
	}
}
