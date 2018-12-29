package admin;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class addTeacherController {

	static adminController adController;

	@FXML
	private TextField tfTeacherName, tfTeacherPassword, tfContact;

	@FXML
	public void initialize() {

	}

	@FXML
	public void onSaveTeacher() {
		try {
			String teacher_name = tfTeacherName.getText().trim();
			String teacher_password = tfTeacherPassword.getText().trim();
			String teacher_contact = tfContact.getText().trim();

			adController.insert_teacher(teacher_name, teacher_password, teacher_contact);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onCancel() {
		adController.closeAddTeacherWindow();
	}

}
