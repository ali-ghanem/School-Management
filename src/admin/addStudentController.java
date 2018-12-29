package admin;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class addStudentController {
	static adminController adController;

	@FXML
	private TextField tfStudentName, tfStudentPassword, tfParentName, tfParentPassword,
			tfContact, tfParentID;
	@FXML
	private ComboBox<String> classgradesList, classroomsList;
	ObservableList<String> cgList = FXCollections.observableArrayList("1","2","3","4");
	ObservableList<String> crList = FXCollections.observableArrayList("A","B");

	@FXML
	private GridPane parentGridPane, parentIDgridPane;
	@FXML
	private CheckBox cbParent;

	@FXML
	public void initialize() {
		classgradesList.setItems(cgList);
		classgradesList.setValue("1");
		classroomsList.setItems(crList);
		classroomsList.setValue("A");
		parentGridPane.visibleProperty().bind(cbParent.selectedProperty().not());
		parentIDgridPane.visibleProperty().bind(cbParent.selectedProperty());
	}

	@FXML
	public void onSaveStudent() {
		try {
			String student_name = tfStudentName.getText().trim();
			String student_password = tfStudentPassword.getText().trim();
			String class_grade = classgradesList.getValue();
			String classroom = classroomsList.getValue();
			if (cbParent.isSelected()) {
				String parent_id = tfParentID.getText().trim();
				adController.insert_student(student_name, student_password, class_grade, classroom, parent_id);
			} else {
				String parent_name = tfParentName.getText().trim();
				String parent_password = tfParentPassword.getText().trim();
				String contact = tfContact.getText().trim();
				adController.insert_student_with_parent(student_name, student_password, class_grade, classroom,
						parent_name, parent_password, contact);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onCancel() {
		adController.closeAddStudentWindow();
	}

}
