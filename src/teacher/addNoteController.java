package teacher;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class addNoteController {
	static teacherController Tcontroller;
	@FXML
	private TextField tfStudentID;
	@FXML
	private TextArea taNote;

	@FXML
	public void onSaveNote() {
		String note = taNote.getText().trim();
		String student_id = tfStudentID.getText().trim();
		Tcontroller.saveNote(student_id, note);
	}

	@FXML
	public void onCancelNote() {
		Tcontroller.cancelNote();
	}

}
