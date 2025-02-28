package com.school.service;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.school.model.Student;
import com.school.model.Subject;
import com.school.model.Teacher;
import com.school.repository.AdministerRepository;
import com.school.repository.LoginRepository;
import com.school.repository.StudentRepository;
import com.school.repository.SubjectRepository;
import com.school.repository.TakenSubjectRepository;
import com.school.repository.TeacherRepository;



public class SchoolService {
	
	
	//로그인 검사
	public static int checkLogin(int id, String password) {
		//1. ID가 존재하는지 확인 / 있으면 1, 없으면 0
		//2. ID에서의 비밀번호가 맞는지 확인 / 맞으면 1, 아니면 0 > 1이라면 role 받기 
		int resPass[]=new int[2];
		int resRole=0;
		int res=LoginRepository.checkLoginID(id);
		// login 테이블에 login id가 없는 경우
		if(res==0) {
			System.out.println("없는 아이디입니다.");
			System.out.println();
		}else { // login 테이블에 login id가 있는 경우
			resPass=LoginRepository.checkLoginPassword(id,password);
			if(resPass[0]==0) {
				System.out.println("비밀번호가 잘못됐습니다.");
				System.out.println();
			}else {
				resRole=resPass[1];
			}
		}
		return resRole;
	}
	


	

	
	//학생화면 1 내 점수 보기
	public static void showStudentScore(int id) {
		
		//  taken_subject와 subject를 inner join해서 score와 해당 과목이름 출력
		List<Map<String, Object>> takenSubjects = StudentRepository.showStudentScore(id);
		

		// 해당 학생 id가 듣는 과목이 없을 경우
		if(takenSubjects.isEmpty()) {
			System.out.println("현재 수강중인 과목이 없습니다.");
		}else { // 해당 학생 id가 듣는 과목이 있을 경우
			System.out.println();
			System.out.println("<수강 목록 및 점수>");
			
			for(Map<String, Object> row : takenSubjects) {
				System.out.print("과목ID: " + row.get("subject_id") + " | ");
				System.out.print("과목명: " + row.get("subject_name") + " | ");
				
				
				// 성적이 아직 입력 안된 경우 null 주기
				 Object scoreObj = row.get("score");
		            if (scoreObj == null) {
		                System.out.print("성적: null");
		            } else {
		                System.out.print("성적: " + scoreObj);
		            }
				System.out.println();
			}
			
			
		}
	}

	
	// 학생화면 2 과목 선택하기- 수강신청 전 과목 출력
	// 관리자 1 전체 과목 출력하기
	//관리자6 - 2.교사 추가 - 과목에 교사 명단에 추가
	public static void showSubject(int num) {
		// teacher와 subject이 teacher_id 기준으로 right join =>  subject 기준 조인한 이유는 
		// subject는 있어도 선생님이 배정되어 있지 않은 경우가 있으므로 subject 기준으로 해야
		// 선생님 배정되지 않은 과목이 출력됨.
		List<Map<String, Object>> Subjects = SubjectRepository.showSubject(num);
		
		
		// 시스템에 등록된 subject가 없는 경우
		if(Subjects.isEmpty()) {
			System.out.println("현재 시스템에 등록된 과목이 없습니다.");
		}else { // 시스템에 등록된 subject가 있는 경우
			System.out.println();
			System.out.println("<전체 과목 목록>");
			
			for(Map<String, Object> row : Subjects) {
				System.out.print("과목ID:" + row.get("subject_id") + " | ");
				System.out.print("과목명: " + row.get("subject_name") + " | ");
				System.out.print("교사명: " + row.get("teacher_name"));
				System.out.println();
			}
			
			
		}
	}
	

	
	//학생화면기준 2 수강신청에서 신청하는 과정
	public static void applySubject(String subject_name, int student_id) {
		// result가 1이면 이미 수강신청된 과목
		// result 0이면 수강신청 처리하기, 업데이트도 해주기
		
		// resultSubject 1이면 시스템에 등록되어 있는 과목
		int resultSubject = SubjectRepository.checkIsSubject(subject_name);
		if(resultSubject == 1) {
			
			// resultTakenSubject 내가 들은 과목인지 확인
			
			// subject_name 이용해서 subject_id 찾아야 하므로 subject와 taken_subject innerjoin한다.
			int resultTakenSubject = TakenSubjectRepository.checkIsTakenSubject(subject_name, student_id);
			if(resultTakenSubject== 404) { // 나는 듣지 않은 수업 즉 수강신청 가능
				
				int result = TakenSubjectRepository.insertTakenSubject(subject_name, student_id);
				
				if(result == 1) {
					System.out.println("수강 신청 성공");	
				}else {
					System.out.println("수강 신청 실패");
				}
			}else {// 나는 이미 들었기에 수강신청 불가능
				System.out.println("이미 수강하고 계신 강의입니다.");
			}
		}else if(resultSubject == 404){ // resultSubject 가 1이 아닌 값이면 시스템에 등록되지 않은 과목
			System.out.println("시스템에 등록되어 있지 않은 과목이라 수강신청이 불가능합니다.");
		}
	}
	
	
	
	//교사1 - 내 과목 조회 
	//교사2 - 학생 정보 조회 - 입력된 과목이름이 선생님이 담당하는 과목이 아닐 경우
	public static void viewSubjects(int id) {
		// teacher _id 이용해 teacher와 subject inner join한다.
		List<Subject> subjects = TeacherRepository.viewSubjects(id);
		if(subjects.isEmpty()) {
			System.out.println("가르치는 과목이 없습니다.");
		}else {
			System.out.println("<내 과목 목록>");
			Iterator<Subject> it = subjects.iterator();
			while (it.hasNext()) {
				Subject subject = (Subject) it.next();
				System.out.println(subject.toStringByTeacher());
			}
		}
	}
	
	//교사2 - 학생 정보 조회 - 입력된 과목이름이 교사가 담당하는 학생이 아닐 경우
	public static int checkTeacherStudent(int id, String student_name) {
		
		int result= TeacherRepository.checkTeacherStudent(id, student_name);	
		return result;
	}

	//교사2 - 학생 정보 조회 - 입력된 과목이름이 교사가 담당하는 과목이 아닐 경우
	public static int checkTeacherSubject(int id, String subject_name) {
		// teacher _id 이용해 teacher와 subject inner join한다.
		int result = 0;
		List<Subject> subjects = TeacherRepository.viewSubjects(id);
		if(subjects.isEmpty()) {
			System.out.println("가르치는 과목이 없습니다.");
		}else {
			Iterator<Subject> it = subjects.iterator();
			while (it.hasNext()) {
				Subject subject = (Subject) it.next();
				
				if(subject_name.equals(subject.getSubject_name())) {
					result = 1;
					break;
				}
			}
		}
		return result;
	}
	
	
	


	//교사2 - 학생 정보 조회 
	public static int viewStudentInfo(int id) {
		//학생 이름(student_name), 수강과목(subject_name), 성적 출력(taken_subject/score)
		int res=0;
		int result= TeacherRepository.viewStudentInfo(id);	
		// 해당 teacher_id로 열린 과목에 수업 듣는 학생이 없음.
		if(result==0) {
			res=0;
			System.out.println("학생 정보를 찾을 수 없습니다.");
		}else { // 있는 경우. TeacherRepository.viewStudentInfo(id)에서 출력되었음
			res=1;// 있는 경우 SchoolController에서 학생정보 수정할 수 있음
		}
		return res;
	}
	
	
	//교사2 - 학생 정보 조회 - 성적 수정 및 입력 
	public static void updateGrade(String student_name, String subject_name, int score) {
		int result=TakenSubjectRepository.updateGrade(student_name,subject_name,score);
		if (result==1) {
			System.out.println("수정 성공");
		}else {
			System.out.println("수정 실패");
		}
	}	
	
	
	

	
	//관리자화면 2 전체 학생 출력
	// 관리자화면 4 특정 학생 출력 전 한번 더 출력함
	public static void showStudent() {
		List<Student> students = AdministerRepository.showStudent();
		if(students.isEmpty()) {
			System.out.println("현재 시스템에 등록된 학생이 없습니다.");
		}else {
			System.out.println();
			System.out.println("<시스템에 등록되어 있는 학생>");
			Iterator<Student> it = students.iterator();
			while (it.hasNext()) {
				Student student = (Student) it.next();
				System.out.println(student.toString());
			}
		}
	}
	
	
	//관리자화면 3 전체 교사 출력
	// 관리자화면 5 특정 교사 출력 전 한번 더 출력함
	public static void showTeacher() {
		List<Teacher> teachers = AdministerRepository.showTeacher();
		if(teachers.isEmpty()) {
			System.out.println("현재 시스템에 등록된 교사가 없습니다.");
		}else {
			System.out.println();
			System.out.println("<시스템에 등록되어 있는 교사>");
			Iterator<Teacher> it = teachers.iterator();
			while (it.hasNext()) {
				Teacher teacher = (Teacher) it.next();
				System.out.println(teacher.toString());
			}
		}
	}
	
	
	
	//관리자화면 4 특정 학생 출력 + 학생이름 통해-> 학생 성적( taken_subject) + 듣는 과목 이름(subject 테이블)
	public static void searchStudent(String student_name) {

		List<Map<String, Object>> students = AdministerRepository.searchStudent(student_name);
		
		if(students.isEmpty()) {
			System.out.println("현재 시스템에 등록되지 않은 학생 정보입니다.");
		}else {
			System.out.println();
			System.out.println("<해당 학생 정보>");
			
			for(Map<String, Object> row : students) {
				System.out.print("학생ID: " + row.get("student_id") + " | ");
				System.out.print("로그인ID: " + row.get("login_id") + " | ");
				System.out.print("학생명: " + row.get("student_name") + " | ");
				
				System.out.print("과목명: " + row.get("subject_name") + " | ");
				System.out.print("교사명: " + row.get("teacher_name") + " | ");
				
				
				 Object scoreObj = row.get("score");
		            if (scoreObj == null) {
		                System.out.print("성적: null");
		            } else {
		                System.out.print("성적: " + scoreObj);
		            }
				System.out.println();
			}
			
			
		}
	}
	
	
	//관리자화면 5 특정 선생 출력 + 선생이름 통해->  수업하는 과목 이름(subject테이블) teacher-subject
	public static void searchTeacher(String teacher_name) {
		List<Map<String, Object>> teachers = AdministerRepository.searchTeacher(teacher_name);
		
		if(teachers.isEmpty()) {
			System.out.println("현재 시스템에 등록되지 않은 선생님 정보입니다.");
		}else {
			System.out.println();
			System.out.println("<해당 교사 정보>");
			
			for(Map<String, Object> row : teachers) {
				System.out.print("교사ID: " + row.get("teacher_id") + " | ");
				System.out.print("교사명: " + row.get("teacher_name") + " | ");
				System.out.print("로그인ID: " + row.get("login_id") + " | ");
				 Object subject_idObj = row.get("subject_id");
		            if (subject_idObj == null) {
		                System.out.print("과목ID: null" + " | ");
		            } else {
		                System.out.print("과목ID: " + subject_idObj + " | ");
		            }
				System.out.print("과목명: " + row.get("subject_name"));
				System.out.println();
			}
			
			
		}
	}
	
	
	//관리자6 - 1.과목 추가
	public static void addSubject(String subjectName) {
		//과목이 이미 있는 경우 있다고 출력 및 추가 X
		//과목이 없는 경우 추가 후 성공 메시지
		
		// 과목이 시스템상에 등록 되어 있는지 확인
		int res= SubjectRepository.isSubjectExist(subjectName);
		if(res==1) {
			System.out.println("이미 존재하는 과목입니다.");
		}else {// 과목이 등록되어 있지 않기에 등록 할 수 있는 상황
			int result=AdministerRepository.addSubject(subjectName);
			if (result==1) {
				System.out.println("과목 추가 성공");
			}else {
				System.out.println("과목 추가 실패");
			}
		}
	}

	//관리자6 - 2.교사 추가 - 교사 명단에 추가
	public static void addTeacher(String teacherName, int loginId, String password) {
		//1. 교사 존재 유무 검사 있으면 1 없으면 0
		//교사 추가(1일 때) ////교사 이름 받아야 함. 
		int []res=new int [2];
		res= TeacherRepository.isTeacherExist(loginId);
		if(res[0]==1) {
			System.out.println("이미 존재하는 교사 ID 입니다.");
		}else {
			int result=AdministerRepository.addTeacher(teacherName,loginId,password);
			if (result==1) {
				System.out.println("교사 추가 성공");
			}else {
				System.out.println();
				System.out.println("교사 추가 실패");
			}
		}
		  
	}


	//관리자6 - 2.교사 추가 - 과목에 교사 추가 
	public static void addTeacherToSub(String subjectName1, String teacherName1) {
		//2. 과목에 교사 추가
				//교사이름, 과목명 받아야 함.
				//과목이 없을 때 출력 : 없는 과목입니다.
				//과목이 있을 때 : 과목명으로 교사이름 불러왔을 때 null이면 그 과목에 교사 추가
				//만약 null이 아니면 이미 해당 과목 교사가 존재합니다.
		int res=SubjectRepository.isSubjectExist(subjectName1);
		if(res==0) {
			System.out.println("없는 과목입니다. 과목부터 생성해주세요"); 
			System.out.println();
		}else {
			int resp=AdministerRepository.isTeacherNull(subjectName1);
			//교사가 현재 존재하는지 확인 
			int[] resTea=TeacherRepository.isTeacherExistName(teacherName1);
			if(resTea[0]==1) {
				if(resp==1) {
					int result=AdministerRepository.addTeacherToSubject(subjectName1,teacherName1);
					if (result==1) {
						System.out.println("과목에 교사 추가 성공");
					}else {
						System.out.println("과목에 교사 추가 실패");
					}
				}else {
					System.out.println("교사가 존재하는 과목입니다.");
				}
			}else {
				System.out.println("존재하지 않는 교사입니다. 교사부터 추가해주세요.");
			}
		}
	}

	//관리자6 - 학생 추가  
	//학생 이름이랑 학생 id 받기, 이미 id가 존재하면 존재하는 학생 
	public static void addStudent(String studentName, int loginId, String password) { 	
		int res= StudentRepository.isStudentExist(loginId);
		if(res==1) {
			System.out.println("이미 존재하는 ID입니다.");
		}else {
			int result=AdministerRepository.addStudent(studentName,loginId,password);
			if (result==1) {
				System.out.println("학생 추가 성공");
			}else {
				System.out.println("학생 추가 실패");
			}
		}
	}
}
	



