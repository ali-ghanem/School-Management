package admin;

public class Course {
	private String course_id, courseName, class_grade, instructor_name, instructor_id, quiz, mid, fin;

	public Course(String courseName, String instructor_name, String instructor_id, String quiz, String mid,
			String fin) {
		super();
		this.courseName = courseName;
		this.instructor_name = instructor_name;
		this.instructor_id = instructor_id;
		this.quiz = quiz;
		this.mid = mid;
		this.fin = fin;
	}

	public Course(String course_id, String courseName, String class_grade, String instructor_name, String instructor_id) {
		super();
		this.course_id = course_id;
		this.courseName = courseName;
		this.instructor_name = instructor_name;
		this.instructor_id = instructor_id;
	}

	public Course(String course_id, String courseName, String class_grade) {
		this.course_id = course_id;
		this.courseName = courseName;
		this.class_grade = class_grade;
	}

	public String getClass_grade() {
		return class_grade;
	}

	public void setClass_grade(String class_grade) {
		this.class_grade = class_grade;
	}

	public String getCourse_id() {
		return course_id;
	}

	public void setCourse_id(String course_id) {
		this.course_id = course_id;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getInstructor_id() {
		return instructor_id;
	}

	public void setInstructor_id(String instructor_id) {
		this.instructor_id = instructor_id;
	}

	public String getInstructor_name() {
		return instructor_name;
	}

	public void setInstructor_name(String instructor_name) {
		this.instructor_name = instructor_name;
	}

	public String getQuiz() {
		return quiz;
	}

	public void setQuiz(String quiz) {
		this.quiz = quiz;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getFin() {
		return fin;
	}

	public void setFin(String fin) {
		this.fin = fin;
	}

}
