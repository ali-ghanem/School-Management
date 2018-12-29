package teacher;

public class Grade {
	private String student_id, student_name, quiz, mid, fin;

	public Grade(String student_id, String student_name, String quiz, String mid, String fin) {
		super();
		this.student_id = student_id;
		this.student_name = student_name;
		this.quiz = quiz;
		this.mid = mid;
		this.fin = fin;
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
