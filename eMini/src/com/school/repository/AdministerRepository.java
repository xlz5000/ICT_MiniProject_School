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
import com.school.model.Subject;
import com.school.model.Teacher;

public class AdministerRepository {
	
	// 관리자화면 2 전체 학생 출력
	// 관리자화면 4 특정 학생 출력 전 한번 더 출력함
	public static List<Student> showStudent() {
		List<Student> Students = new ArrayList<>();

		String query = "SELECT * FROM student";


		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Student Student = new Student(rs.getInt("student_id"),
						rs.getString("student_name"),rs.getInt("login_id"));

				Students.add(Student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Students;
	}
	
	
	// 관리자화면 3 전체 선생 출력
	public static List<Teacher> showTeacher() {
		List<Teacher> Teachers = new ArrayList<>();

		String query = "SELECT * FROM teacher";


		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Teacher Teacher = new Teacher(rs.getInt("teacher_id"),
						rs.getString("teacher_name"),rs.getInt("login_id"));

				Teachers.add(Teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return Teachers;
	}
	
	
	// 관리자화면 4 특정 학생 정보 출력
	public static List<Map<String, Object>> searchStudent(String student_name) {
		List<Map<String, Object>> takenSubjects = new ArrayList<>();

		
		String query = "SELECT * " +
				"FROM student st " +
				"LEFT JOIN taken_subject ts ON st.student_id = ts.student_id " +
				"LEFT JOIN subject sub ON sub.subject_id = ts.subject_id " +
				"LEFT JOIN teacher te ON sub.teacher_id = te.teacher_id " + 
				"WHERE st.student_name= " + "'" + student_name + "'";

		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
						row.put("student_name", rs.getString("student_name"));
						row.put("login_id", rs.getInt("login_id"));
						row.put("taken_id", rs.getInt("taken_id"));
					
						Object scoreObject = rs.getObject("score");
						row.put("score", scoreObject);
						row.put("subject_id", rs.getInt("subject_id"));
						row.put("student_id", rs.getInt("student_id"));
						row.put("subject_name", rs.getString("subject_name"));
						row.put("teacher_id", rs.getInt("teacher_id"));
						row.put("teacher_name", rs.getString("teacher_name"));
						

				takenSubjects.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return takenSubjects;
	}
	
	//관리자 화면 5 특정 선생님 정보 출력
	public static List<Map<String, Object>> searchTeacher(String teacher_name) {
		List<Map<String, Object>> teachers = new ArrayList<>();

		
		String query = "SELECT * " +
				"FROM teacher te " +
				"LEFT JOIN subject sub ON sub.teacher_id = te.teacher_id " +
				"WHERE te.teacher_name = " + "'" + teacher_name + "'";

		try (Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(query)) {

			while (rs.next()) {
				Map<String, Object> row = new HashMap<String, Object>();
						row.put("teacher_id", rs.getInt("teacher_id"));
						row.put("teacher_name", rs.getString("teacher_name"));
						row.put("login_id", rs.getInt("login_id"));
						Object subject_idObject = rs.getObject("subject_id");
						row.put("subject_id", subject_idObject);
						row.put("subject_name", rs.getString("subject_name"));

						teachers.add(row);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}


		return teachers;
	}
	
	// 관리자화면 6 - 1. 과목 추가
	//과목 추가
	public static int addSubject(String subjectName) {
		int result=0;
		String query="INSERT INTO subject (subject_name) VALUES('"+subjectName+"');";
		try(Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement()){
			//삽입, 수정, 삭제가 성공하면 0보다 크다. 
			result=stmt.executeUpdate(query);			
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	//관리자6-2-1 교사 추가 - 교사 명단에 추가
	public static int addTeacher(String teacherName, int loginId, String password) {
		
		String query="INSERT INTO login (login_id,password,role) VALUES('"+loginId+ "', '"+ password+"' ,2)";
		try(Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement()){
			//삽입, 수정, 삭제가 성공하면 0보다 크다. 
			stmt.executeUpdate(query);			
		} catch (Exception e) {
		}
		
		int result=0;
		String query2="INSERT INTO teacher (teacher_name, login_id) VALUES('"+teacherName+"','"+loginId+ "')";
		try(Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement()){
			//삽입, 수정, 삭제가 성공하면 0보다 크다. 
			result=stmt.executeUpdate(query2);			
		} catch (Exception e) {
		}
		return result;
	}
	
	
	//관리자 6-3-학생 리스트에 추가 
	public static int addStudent(String studentName, int loginId, String password) {
		String query="INSERT INTO login (login_id,password, role) VALUES('"+loginId+ "', '"+password+"' ,1)";
		try(Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement()){
			//삽입, 수정, 삭제가 성공하면 0보다 크다. 
			stmt.executeUpdate(query);			
		} catch (Exception e) {
		}
		
		int result=0;
		String query2="INSERT INTO student (student_name, login_id) VALUES('"+studentName+"','"+loginId+ "')";
		try(Connection conn = DatabaseManager.getConnection();
				Statement stmt = conn.createStatement()){
			//삽입, 수정, 삭제가 성공하면 0보다 크다. 
			result=stmt.executeUpdate(query2);			
		} catch (Exception e) {
		}
		return result;
	}

	//관리자6-2-2 과목에 교사 추가 - 교사가 Null인지 확인
	public static int isTeacherNull(String subjectName1) {
		int res= 0;
		List<Subject> list = new ArrayList<>();
		String query="SELECT * FROM subject WHERE subject_name = ? and teacher_id is null";
		try(Connection conn =DatabaseManager.getConnection();
				PreparedStatement pstmt= conn.prepareStatement(query)) {
			pstmt.setString(1, subjectName1);
			try (ResultSet rs= pstmt.executeQuery()){
				while(rs.next()) {
					Subject bvo = new Subject(rs.getInt("subject_id"), rs.getString("subject_name"), rs.getInt("teacher_id"));
							
					list.add(bvo);
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
			if(list.isEmpty()) {
				res=0;
			}else {
				res=1;
			}
		return res;
	}
	//관리자6-2-2 과목에 교사 추가 - 교사가 Null인지 확인 -  교사 넣기 
	public static int addTeacherToSubject(String subjectName1, String teacherName1) {
		//교사가 교사 table에 있는지 검사하고 교사가 없다면 교사 table에 추가하고 나서 과목에 교사 번호 할당 
		//교사 x: 교사 있는지 확인 ->교사 table에 추가 -> 교사 table에서 교사 번호 select -> 해당 번호 할당  
		//교사 o: 교사 있는지 확인 -> 교사 table에서 교사 번호 select -> 해당 번호 할당 
		
		//교사 있는지 확인 
		int[] existRes=TeacherRepository.isTeacherExistName(teacherName1);
		int result=0;
		if(existRes[0]==1) {
			//교사 table에서 교사 id 확인 -> update subject table의 교사 id
			
			String query="update subject set teacher_id=? WHERE subject_name = ?";
			try(Connection conn =DatabaseManager.getConnection();
					PreparedStatement pstmt =conn.prepareStatement(query)) {
				pstmt.setInt(1, existRes[1]);
				pstmt.setString(2, subjectName1);
				result=pstmt.executeUpdate();
			} catch (Exception e) {
				System.out.println(e);
			}
		}else {
			System.out.println("교사 추가 후 과목에 할당해주세요");
		}

		return result;
	}
	
	


}
