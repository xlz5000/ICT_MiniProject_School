package com.school.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.school.model.Subject;



public class SubjectRepository {
	
	// 학생화면  2 수강신청 전 과목목록 출력
	// 관리자화면 1 전체 과목 출력
	public static List<Map<String, Object>> showSubject(int num) {
		List<Map<String, Object>> Subjects = new ArrayList<>();
		
		String query = "";
		if(num == 0) {
			query = "SELECT * " +
				"FROM teacher t " +
				"INNER JOIN subject sub ON t.teacher_id = sub.teacher_id";
		} else if (num == 1){
			query = "SELECT * " +
					"FROM teacher t " +
					"RIGHT JOIN subject sub ON t.teacher_id = sub.teacher_id";
		}
		
		try (Connection conn = DatabaseManager.getConnection();
				PreparedStatement stmt = conn.prepareStatement(query);
				ResultSet rs = stmt.executeQuery()) {
			
			while (rs.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
				// 관리자 화면에서 과목추가시 선생님 추가가 안되어있을 수도 있으므로 teacher_name, id는 null값으로 받아오게 했음.
				Object teacherObject1 = rs.getObject("teacher_name");
				row.put("teacher_name", teacherObject1);
				Object teacherObject2 = rs.getObject("teacher_id");
				row.put("teacher_id", teacherObject2);
				
				row.put("login_id", rs.getInt("login_id"));
				row.put("subject_id", rs.getInt("subject_id"));
				row.put("subject_name", rs.getString("subject_name"));
				
				Subjects.add(row);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return Subjects;
	}	
	
	// 학생화면 1-2 수강신청에서 과목이 존재하는지 확인
	public static int checkIsSubject(String subject_name) {
		List<Subject> Subjects = new ArrayList<>();
		String query = "SELECT * FROM subject WHERE subject_name = " + "'" + subject_name + "'";
		int result = 0;
		
		try(Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)){
			
			while (rs.next()) {
				Subject Subject = new Subject(
						rs.getInt("subject_id"),rs.getString("subject_name"),rs.getInt("teacher_id"));
				
				Subjects.add(Subject);
			}
			// 시스템에 해당 과목이 등록되어 있지 않음
			if(Subjects.size()== 0) {
				result = 404;
			}else { // 시스템에 해당 과목이 들어 있음
				result = 1;
			}
			
		}catch (Exception e) {
			
		}
		
		return result;
	}
	
	// 관리자화면 6 - 1. 과목 추가
	//과목 존재 유무 확인 
	//관리자6 - 2.교사 추가 - 과목에 교사 추가 
	public static int isSubjectExist(String subjectName) {
		List<Subject> list = new ArrayList<>();
		String query="SELECT * FROM subject WHERE subject_name = ?";
		try(Connection conn =DatabaseManager.getConnection();
				PreparedStatement pstmt= conn.prepareStatement(query)) {
			pstmt.setString(1, subjectName);
			try (ResultSet rs= pstmt.executeQuery()){
				while(rs.next()) {
					Subject bvo = new Subject(rs.getInt("subject_id"),rs.getString("subject_name"),rs.getInt("teacher_id"));
					list.add(bvo);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
			int res = 0;
			// 해당 과목 존재 x
			if(list.isEmpty()) {
				res=0;
			}else { // 해당 과목 존재 
				res=1;
			}
		
		return res;
	}

	


	

	

	
	
	
	
	
}
