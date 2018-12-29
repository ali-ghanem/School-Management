package admin;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class addCourseController {

	static adminController adController;

	@FXML
	private TextField tfCourseName;
	@FXML
	private ComboBox<String> classesList;

	@FXML
	public void initialize() {
		classesList.setItems(FXCollections.observableArrayList("1", "2", "3", "4"));
	}

	@FXML
	public void onSaveCourse() {
		try {
			String courseName = tfCourseName.getText().trim();
			String class_grade = classesList.getValue();
			adController.insert_course(courseName, class_grade);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onCancel() {
		adController.closeAddCourseWindow();
	}

}
