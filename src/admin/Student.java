package admin;

public class Student {
	private String student_id, student_name, class_grade, classroom, parent_id, parent_name, contact;

	public Student(String student_id, String student_name, String class_grade, String classroom, String parent_id,
			String parent_name, String contact) {
		super();
		this.student_id = student_id;
		this.student_name = student_name;
		this.class_grade = class_grade;
		this.classroom = classroom;
		this.parent_id = parent_id;
		this.parent_name = parent_name;
		this.contact = contact;
	}

	public String getParent_id() {
		return parent_id;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getClass_grade() {
		return class_grade;
	}

	public void setClass_grade(String class_grade) {
		this.class_grade = class_grade;
	}

	public String getClassroom() {
		return classroom;
	}

	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}

	public String getParent_name() {
		return parent_name;
	}

	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
