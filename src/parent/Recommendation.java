package parent;

public class Recommendation {
	String student_name, teacher, notes, note_date;

	public Recommendation(String student_name, String teacher, String notes, String note_date) {
		super();
		this.student_name = student_name;
		this.teacher = teacher;
		this.notes = notes;
		this.note_date = note_date;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getTeacher() {
		return teacher;
	}

	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public String getNote_date() {
		return note_date;
	}

	public void setNote_date(String note_date) {
		this.note_date = note_date;
	}

}
