package com.school.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.school.model.Login;

public class LoginRepository {
	
	
	//아이디 검사
	public static int checkLoginID(int id) {
		List<Login> list = new ArrayList<>();
		String query="SELECT * FROM login WHERE login_id = ?";
		try(Connection conn =DatabaseManager.getConnection();
				PreparedStatement pstmt= conn.prepareStatement(query)) {
			pstmt.setInt(1, id);
			try (ResultSet rs= pstmt.executeQuery()){
				while(rs.next()) {
					Login bvo = new Login(rs.getInt("login_id"),rs.getString("password"),rs.getInt("role"));
					list.add(bvo);
				}
			}
		} catch (Exception e) {

		}
			int res = 0;
			// login 테이블에 해당하는 login id가 없는 경우
			if(list.isEmpty()) {
				res=0;
			}else { // login 테이블에 해당하는 login id가 있는 경우 
				res=1;
			}
		
		return res;
	}
	
	//비밀번호 검사
	public static int [] checkLoginPassword(int id, String password) {
		List<Login> list = new ArrayList<>();
		String query="SELECT * FROM login WHERE login_id = " + id + " AND password = "+ password;
		int res [] = {0,0};
		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt=conn.createStatement();
				ResultSet rs=stmt.executeQuery(query)
				){
			while(rs.next()) {
				Login bvo = new Login(rs.getInt("login_id"),rs.getString("password"),rs.getInt("role"));
				list.add(bvo);
			}
			
			if(list.size()==0) {
				res[0]= 0;
			}else if(list.size()==1) {
				res[0]=1;
				if(list.get(0).getRole()==1) {
					res[1]=1;
				}else if(list.get(0).getRole()==2) {
					res[1]=2;
				}else if(list.get(0).getRole()==3){
					res[1]=3;
				}
			}
			
			
		} catch (Exception e) {
		}
		return res;
	}

}
