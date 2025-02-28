package com.school.model;

public class Administer {
	
	private int administer_id;
	private int login_id;

	public Administer(int administer_id, int login_id) {
		this.administer_id = administer_id;
		
		this.login_id = login_id;
	}
	



	public int getAdminister_id() {
		return administer_id;
	}



	public void setAdminister_id(int administer_id) {
		this.administer_id = administer_id;
	}


	public int getLogin_id() {
		return login_id;
	}



	public void setLogin_id(int login_id) {
		this.login_id = login_id;
	}



	@Override
	public String toString() {
		return "관리자ID : " + administer_id + 
				" | 로그인ID : " + login_id ;
	}
}
