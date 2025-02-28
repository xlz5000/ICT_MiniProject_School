package com.school.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.school.model.Student;



public class StudentRepository {
	
	
	//학생화면 1 내 점수 보기
	public static List<Map<String, Object>> showStudentScore(int student_id) {
		List<Map<String, Object>> takenSubjects = new ArrayList<>();
		
		
		String query = "SELECT * " +
				"FROM taken_subject ts " + 
				"INNER JOIN subject sub ON ts.subject_id = sub.subject_id "+
				"WHERE student_id= " + student_id;
		
		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {
			
			while (rs.next()) {
				// join 했기에 이들을 받을 만들어진 테이블이 없어서 map 객체로 받은 것.
				Map<String, Object> row = new HashMap<String, Object>();
				row.put("taken_id", rs.getInt("taken_id"));
				
				// mysql에서 int형 default값이 0일때 getInt로 받아오면 콘솔에 0으로 출력됨. 그래서 getObject()로 받아옴
				// score를 getObject()로 가져와 NULL 처리
				Object scoreObject = rs.getObject("score");
				row.put("score", scoreObject);  // score가 NULL이면, Map에 NULL로 저장됨
				
				row.put("student_id", rs.getInt("student_id"));
				row.put("subject_id", rs.getInt("subject_id"));
				row.put("teacher_id", rs.getInt("teacher_id"));
				row.put("subject_name", rs.getString("subject_name"));
				
				takenSubjects.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return takenSubjects;
	}
	
	
	//관리자 6-3 존재유무 
	//login_id가 같은지 확인 
	public static int isStudentExist(int loginId) {
		List<Student> list = new ArrayList<>();
		String query="SELECT * FROM student WHERE login_id = ?";
		try(Connection conn =DatabaseManager.getConnection();
				PreparedStatement pstmt= conn.prepareStatement(query)) {
			pstmt.setInt(1, loginId);
			try (ResultSet rs= pstmt.executeQuery()){
				while(rs.next()) {
					Student bvo = new Student(rs.getInt("student_id"),rs.getString("student_name"),rs.getInt("login_id"));
					list.add(bvo);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
			int res = 0;
			if(list.isEmpty()) {
				res=0;
			}else {
				res=1;
			}
		
		return res;
	}


	
	
	
}
