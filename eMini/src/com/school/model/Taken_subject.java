package com.school.model;

public class Taken_subject{
	
	
	private int taken_id;
	private int score;
	private int student_id;
	private int subject_id;
	private int teacher_id;
	
	public Taken_subject(int taken_id, int score, int student_id, int subject_id, int teacher_id) {
		this.taken_id = taken_id;
		this.score = score;
		this.student_id = student_id;
		this.subject_id = subject_id;
		this.teacher_id = teacher_id;
	}

	public int getTaken_id() {
		return taken_id;
	}

	public void setTaken_id(int taken_id) {
		this.taken_id = taken_id;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getStudent_id() {
		return student_id;
	}

	public void setStudent_id(int student_id) {
		this.student_id = student_id;
	}

	public int getSubject_id() {
		return subject_id;
	}

	public void setSubject_id(int subject_id) {
		this.subject_id = subject_id;
	}

	public int getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(int teacher_id) {
		this.teacher_id = teacher_id;
	}	
}