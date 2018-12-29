package teacher;

public class Note {
	private String student_name, student_id, note_content, note_date;

	public Note(String student_name, String student_id, String note_content, String note_date) {
		super();
		this.student_name = student_name;
		this.student_id = student_id;
		this.note_content = note_content;
		this.note_date = note_date;
	}

	public String getStudent_name() {
		return student_name;
	}

	public void setStudent_name(String student_name) {
		this.student_name = student_name;
	}

	public String getStudent_id() {
		return student_id;
	}

	public void setStudent_id(String student_id) {
		this.student_id = student_id;
	}

	public String getNote_content() {
		return note_content;
	}

	public void setNote_content(String note_content) {
		this.note_content = note_content;
	}

	public String getNote_date() {
		return note_date;
	}

	public void setNote_date(String note_date) {
		this.note_date = note_date;
	}
}
