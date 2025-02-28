package com.school.model;


public class Login {

	private int login_id;
	private String password;
	private int role;
	


	public Login(int login_id, String password, int role) {
		super();
		this.login_id = login_id;
		this.password = password;
		this.role = role;
	}
	
	
	public int getLogin_id() {
		return login_id;
	}


	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}




	public int getRole() {
		return role;
	}



	public void setRole(int role) {
		this.role = role;
	}


	@Override
	public String toString() {
		return "로그인 ID : " + login_id + " | 비밀번호 : " + password + " | 역할 : " + role;
	}

}
