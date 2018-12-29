package parent;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import admin.Course;
import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class parentController {

	private static String ID, NAME, CHILD_ID;
	private static Statement statement;

	@FXML
	private Label lblName, lblID, lblTitle;
	@FXML
	private RadioButton radioNotes, radioChildren;
	@FXML
	private TableView<Recommendation> notesTable;
	private ObservableList<Recommendation> listNotes = FXCollections.observableArrayList();

	@FXML
	private TableView<Course> coursesTable;
	private ObservableList<Course> coursesList = FXCollections.observableArrayList();

	@FXML
	private TableColumn<Course, String> colCourse, colInstructor, colQuiz, colMidterm, colFinal;
	@FXML
	private TableColumn<Recommendation, String> colName, colNotes, colNote_date, colTeacherName;
	@FXML
	private ComboBox<String> childrenList;
	private ObservableList<String> listChildren = FXCollections.observableArrayList();

	private ResultSet resultNotes, resultCourses, resultChildren;
	private HashMap<String, String> children_ids, children_classes;

	public static void setID(String id) {
		ID = id;
	}

	public static void setName(String name) {
		NAME = name;
	}

	public static void setStatement(Statement stm) {
		statement = stm;
	}

	@FXML
	public void initialize() {
		init_labels();
		loadNotes();
		loadChildren();
		init_notes_table();
		init_grades_table();
	}

	public void init_labels() {
		lblName.setText(NAME);
		lblID.setText(ID);
	}

	public void init_notes_table() {
		notesTable.visibleProperty().bind(radioNotes.selectedProperty());
		colName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
		colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacher"));
		colNotes.setCellValueFactory(new PropertyValueFactory<>("notes"));
		colNote_date.setCellValueFactory(new PropertyValueFactory<>("note_date"));
		notesTable.setItems(listNotes);
	}

	public void init_grades_table() {
		coursesTable.visibleProperty().bind(radioChildren.selectedProperty());
		colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
		colQuiz.setCellValueFactory(new PropertyValueFactory<>("quiz"));
		colMidterm.setCellValueFactory(new PropertyValueFactory<>("mid"));
		colFinal.setCellValueFactory(new PropertyValueFactory<>("fin"));
		coursesTable.setItems(coursesList);
	}

	public void loadChildren() {
		childrenList.visibleProperty().bind(radioChildren.selectedProperty());
		try {
			children_ids = new HashMap<>();
			children_classes = new HashMap<>();
			String selectChildren = String.format(
					"select students.student_name, students.student_id, concat(students.class_grade, students.classroom) as class"
							+ " from (students inner join parents using (parent_id)) where parents.parent_id=%s",
					ID);
			resultChildren = statement.executeQuery(selectChildren);
			String childName, childID, childClass;
			while (resultChildren.next()) {
				childName = resultChildren.getString(1);
				childID = resultChildren.getString(2);
				childClass = resultChildren.getString(3);
				children_ids.put(childName, childID);
				children_classes.put(childName, childClass);
				listChildren.add(childName);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		childrenList.setItems(listChildren);
	}

	public void loadCourses() {
		try {
			String selectClasses = String.format(
					"select courses.course_name, teachers.teacher_name, teachers.teacher_id, grade.quiz, grade.midterm, grade.final "
							+ "from (((grade inner join students using (student_id)) inner join courses using (course_id)) "
							+ "inner join teaches using (course_id, classroom)) inner join teachers using (teacher_id)"
							+ "where students.student_id = %s;",
					CHILD_ID);
			resultCourses = statement.executeQuery(selectClasses);

			Course crs;
			String courseName, instructor_name, instructor_id, quiz, mid, fin;
			coursesList.clear();
			while (resultCourses.next()) {
				courseName = resultCourses.getString("course_name");
				instructor_name = resultCourses.getString("teacher_name");
				instructor_id = resultCourses.getString("teacher_id");
				quiz = resultCourses.getString("quiz");
				mid = resultCourses.getString("midterm");
				fin = resultCourses.getString("final");
				crs = new Course(courseName, instructor_name, instructor_id, quiz, mid, fin);
				coursesList.add(crs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadNotes() {
		try {
			Recommendation rec;
			String child_name, teacherName, notes, note_date;
			listNotes.clear();
			String selectNotes = String.format("select student_name, teacher_name, note_content, note_date\r\n"
					+ "from (((notes inner join students using (student_id)) inner join teachers using (teacher_id)) inner join parents using (parent_id))\r\n"
					+ "where parent_id =%s;", ID);
			resultNotes = statement.executeQuery(selectNotes);
			while (resultNotes.next()) {
				child_name = resultNotes.getString("student_name");
				teacherName = resultNotes.getString("teacher_name");
				notes = resultNotes.getString("note_content");
				note_date = resultNotes.getString("note_date");
				rec = new Recommendation(child_name, teacherName, notes, note_date);
				listNotes.add(rec);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onRadioNotes() {
		lblTitle.setText("Recommendations");
	}

	@FXML
	public void onRadioChildren() {
		if (childrenList.getValue() == null)
			lblTitle.setText("Child:  \t Class: ");
		else
			lblTitle.setText(String.format("Child: %s \t Class: %s", childrenList.getValue(),
					children_classes.get(childrenList.getValue())));
	}

	@FXML
	public void onSelect() {
		CHILD_ID = children_ids.get(childrenList.getValue());
		loadCourses();
		childrenList.setItems(listChildren);
		lblTitle.setText(String.format("Child: %s \t Class: %s", childrenList.getValue(),
				children_classes.get(childrenList.getValue())));
	}

	@FXML
	public void onLogOut() throws IOException {
		Main.switchToMain();
	}
}