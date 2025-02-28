package com.school.controller;

import java.util.Scanner;

import com.school.service.SchoolService;



public class SchoolController {
	public static final Scanner scan = new Scanner(System.in);

	
	// 로그인 화면
	public static void startSchoolSystem() {
		esc: while (true) {
			System.out.println("=== 학사 관리 시스템 ===");
			System.out.print("로그인ID: ");

			int id = 0;
			String password = "";
			int roleNum = 0;
			try {
				id = scan.nextInt();
				System.out.print("비밀번호: ");
				password = scan.next();
				
				// 로그인 검사 및 역할 가져오기
				// 해당 로그인ID, 비밀번호를 가지고 가서 login 테이블에서 role 역할 받아오기
				// role =  1은 학생, role = 2 는 교사, role =  3 은 관리자
				roleNum = SchoolService.checkLogin(id, password);
			} catch (Exception e) {
				System.out.println("숫자를 입력해주세요.");
				System.out.println();
			}
			scan.nextLine();
			


			switch (roleNum) {

			case 1:
				showStudent(id);
				break esc;
			case 2:
				showTeacher(id);
				break esc;
			case 3:
				showAdminister();
				break esc;
			default:
				break;
			}

		}
	}

	// 학생화면
	private static void showStudent(int id) {
		esc: while (true) {
			System.out.println();
			System.out.println("<학생 화면>");
			System.out.println("1.내 점수 보기");
			System.out.println("2.과목 선택하기");
			System.out.println("3.로그아웃");
			System.out.print("입력: ");
			int choice = 0;
			try {
				choice = scan.nextInt();
			} catch (Exception e) {

			}
			scan.nextLine();
			switch (choice) {
			case 1: // 내 점수 보기
				SchoolService.showStudentScore(id);

				break;
			case 2: // 과목 선택하기(수강신청 할 과목목록 출력 + 수강신청)
				SchoolService.showSubject(0); //수강신청 할 과목목록 출력
				System.out.println();
				System.out.print("수강신청할 과목명을 입력해주세요: ");
				String subject = scan.next();
				SchoolService.applySubject(subject, id); // 수강신청

				break;
			case 3: // 로그아웃
				break esc;
			default:
				System.out.println("올바른 번호를 입력하세요.");
				break;
			}
		}

	}

	// 선생님 화면
	private static void showTeacher(int id) {
		esc: while (true) {
			System.out.println();
			System.out.println("<교사 화면>");
			System.out.println("1.내 과목 보기");
			System.out.println("2.학생 정보 조회 및 성적 수정");
			System.out.println("3.로그아웃");
			System.out.print("입력: ");
			int choice = 0;
			try {
				choice = scan.nextInt();
			} catch (Exception e) {

			}
			scan.nextLine();

			switch (choice) {
			case 1: // 내 과목 보기
				SchoolService.viewSubjects(id);
				break;
			case 2: // 학생 정보 조회
				int res = SchoolService.viewStudentInfo(id);
				System.out.println();
				if (res == 1) {
					System.out.println("성적을 수정 및 입력하시겠습니까?(y/n)");
					String select = scan.next();
					if (select.equalsIgnoreCase("y")) {
						String student_name= "";
						String subject_name= "";
						
						e: while(true) {
						System.out.print("학생명: ");
						student_name = scan.next();
						//교사2 - 학생 정보 조회 - 입력된 학생이름이 교사가 담당하는 학생이 아닐 경우
						int result1 = SchoolService.checkTeacherStudent(id, student_name);
						if(result1 == 0) {
							System.out.println("담당하는 학생이 아닙니다.");
							System.out.println("----------------------");
							System.out.println();
							continue e;
							
							}
						
						System.out.print("과목명: ");
						subject_name = scan.next();
						//교사2 - 학생 정보 조회 - 입력된 과목이름이 교사가 담당하는 과목이 아닐 경우
						int result2 = SchoolService.checkTeacherSubject(id, subject_name);
						if(result2 == 0) {
							System.out.println("담당하는 과목이 아닙니다.");
							System.out.println("----------------------");
							System.out.println();
							continue e;
							}
						
						else {
							break e;
						}
						}
						
						int score = 0;
						try {
							System.out.print("성적: ");
							score = scan.nextInt();
							SchoolService.updateGrade(student_name, subject_name, score);
						} catch (Exception e) {
							System.out.println("수정 실패");
						}
						scan.nextLine();
						continue;
					} else if (select.equalsIgnoreCase("n")) {
						break;
					} else {
						System.out.println("잘못된 값을 입력했습니다.");
						break;
					}
				}
			case 3: // 로그아웃
				break esc;
			default:
				System.out.println("올바른 번호를 입력하세요.");
				break;
			}
		}

	}

	// 관리자화면
	private static void showAdminister() {
		esc: while (true) {

			System.out.println();
			System.out.println("<관리자 화면>");
			System.out.println("1.전체 과목 보기");
			System.out.println("2.전체 학생 보기");
			System.out.println("3.전체 교사 보기");
			System.out.println("4.특정 학생 정보 보기");
			System.out.println("5.특정 교사 정보 보기");
			System.out.println("6.추가");
			System.out.println("7.로그아웃");
			System.out.print("입력: ");

			int choice = 0;
			try {
				choice = scan.nextInt();
			} catch (Exception e) {

			}
			scan.nextLine();

			switch (choice) {
			case 1: // 전체 과목 보기
				// 교사 NULL 값인 과목까지 다 보임
				SchoolService.showSubject(1);
				break;
			case 2: // 전체 학생 보기
				SchoolService.showStudent();
				break;
			case 3: // 전체 교사 보기
				SchoolService.showTeacher();
				break;
			case 4: // 특정 학생 정보 보기
				SchoolService.showStudent();
				System.out.println();
				System.out.print("학생명: ");
				String student_name = scan.next();
				SchoolService.searchStudent(student_name);
				break;
			case 5: // 특정 선생 정보 보기

				SchoolService.showTeacher();
				System.out.println();

				System.out.print("교사명: ");
				String teacher_name = scan.next();
				SchoolService.searchTeacher(teacher_name);
				break;
			case 6:
				int k = AddByAdminister();
				if (k == 0) {
					break esc;
				} else {
					break;
				}
			case 7:
				break esc;
			default:
				System.out.println("올바른 번호를 입력하세요.");
				break;

			}
		}
	}

	// 관리자화면 6
	private static int AddByAdminister() {
		System.out.println();
		System.out.println("1.과목 추가");
		System.out.println("2.교사 추가");
		System.out.println("3.학생 추가");
		System.out.println("4.로그아웃");
		System.out.print("입력: ");
		int choice = 0;
		try {
			choice = scan.nextInt();
		} catch (Exception e) {

		}
		scan.nextLine();

		int temp = 1;// 로그아웃 안됨.
		switch (choice) {
		case 1:// 과목 추가
			System.out.print("추가할 과목명: ");
			String subjectName = scan.next();
			scan.nextLine();
			SchoolService.addSubject(subjectName);
			break;
		case 2:// 교사 추가
			System.out.println();
			System.out.println("<교사 추가 화면>");
			System.out.println("1.교사 명단에 교사 추가");
			System.out.println("2.과목에 교사 할당");
			System.out.println("3. 로그아웃");
			System.out.print("입력: ");

			int choice2 = 0;
			try {
				choice2 = scan.nextInt();

			} catch (Exception e) {

			}
			switch (choice2) {
			case 1: //1.교사 명단에 교사 추가
				System.out.print("교사명: ");
				String teacherName = scan.next();

				int loginId = 0;
				try {
					System.out.print("아이디: ");
					loginId = scan.nextInt();

				} catch (Exception e) {

				}

				System.out.print("비밀번호: ");
				String password = scan.next();
				SchoolService.addTeacher(teacherName, loginId, password);
				break;
			case 2: //2.과목에 교사 할당
				// 교사 NULL 값인 과목까지 다 보임
				SchoolService.showSubject(1);
				System.out.println();
				System.out.println("-----------------");
				SchoolService.showTeacher();
				System.out.print("과목명: ");
				String subjectName1 = scan.next();
				System.out.print("교사명: ");
				String teacherName1 = scan.next();
				SchoolService.addTeacherToSub(subjectName1, teacherName1);
				break;
			case 3: //3. 로그아웃
				temp = 0;
				break;
			default:
				System.out.println("올바른 번호를 입력하세요.");
				break;
			}

			break;
		case 3:
			System.out.print("학생명: ");
			String studentName = scan.next();

			int loginId = 0;
			try {
				System.out.print("아이디: ");
				loginId = scan.nextInt();

			} catch (Exception e) {

			}
			scan.nextLine();
			System.out.print("비밀번호: ");
			String password = scan.next();
			SchoolService.addStudent(studentName, loginId, password);
			break;
		case 4: // 로그아웃
			temp = 0;
			break;
		default:
			System.out.println("올바른 번호를 입력하세요.");
			break;
		}
		return temp;
	}
}
