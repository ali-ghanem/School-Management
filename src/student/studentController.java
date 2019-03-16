package student;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import admin.Course;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class studentController {

	private static String ID, NAME, ClassGrade;
	private static Statement statement;
	private ResultSet resultCourses, resultClass;

	@FXML
	private Label lblName, lblID, lblClass;
	@FXML
	private TableView<Course> coursesTable;
	@FXML
	private TableColumn<Course, String> colCourse, colInstructor, colQuiz, colMidterm, colFinal;

	private ObservableList<Course> coursesList = FXCollections.observableArrayList();

	public static void setID(String id) {
		ID = id;
	}

	public static void setName(String name) {
		NAME = name;
	}

	public static void setClassGrade(String classGrade) {
		ClassGrade = classGrade;
	}

	public static void setStatement(Statement stm) {
		statement = stm;
	}

	@FXML
	public void initialize() {
		getClassGrade();
		loadCourses();
		init_grades_table();
		init_labels();
	}

	public void getClassGrade() {
		try {
			String getClassGrade = String.format("select concat(class_grade, class_room) as class"
					+ " from students inner join classes using (class_id) where students.student_id = %s;", ID);
			resultClass = statement.executeQuery(getClassGrade);
			resultClass.next();
			ClassGrade = resultClass.getString("class");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void init_grades_table() {
		colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
		colQuiz.setCellValueFactory(new PropertyValueFactory<>("quiz"));
		colMidterm.setCellValueFactory(new PropertyValueFactory<>("mid"));
		colFinal.setCellValueFactory(new PropertyValueFactory<>("fin"));
		coursesTable.setItems(coursesList);
	}

	public void init_labels() {
		lblName.setText(NAME);
		lblID.setText(ID);
		lblClass.setText(ClassGrade);
	}

	public void loadCourses() {
		try {
			String selectClasses = String.format(
					"select course_name, teacher_name, teacher_id, quiz, midterm, final from (((grade inner join students using (student_id)) inner join courses using (course_id)) inner join teaches using (class_id, course_id)) left join teachers using (teacher_id) where students.student_id = %s;",
					ID);
			resultCourses = statement.executeQuery(selectClasses);

			Course crs;
			String courseName, teacherName, teacherID, quiz, mid, fin;

			while (resultCourses.next()) {
				courseName = resultCourses.getString("course_name");
				teacherName = resultCourses.getString("teacher_name");
				teacherID = resultCourses.getString("teacher_id");
				quiz = resultCourses.getString("quiz");
				mid = resultCourses.getString("midterm");
				fin = resultCourses.getString("final");
				crs = new Course(courseName, teacherName, teacherID, quiz, mid, fin);
				coursesList.add(crs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onLogOut() throws IOException {
		Main.switchToMain();
	}
}
