package admin;

public class Teacher {
	private String teacher_name, teacher_id, contact;

	public Teacher(String teacher_name, String teacher_id, String contact) {
		super();
		this.teacher_name = teacher_name;
		this.teacher_id = teacher_id;
		this.contact = contact;
	}

	public String getTeacher_name() {
		return teacher_name;
	}

	public void setTeacher_name(String teacher_name) {
		this.teacher_name = teacher_name;
	}

	public String getTeacher_id() {
		return teacher_id;
	}

	public void setTeacher_id(String teacher_id) {
		this.teacher_id = teacher_id;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

}
