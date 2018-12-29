package teacher;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class teacherController {
	@FXML
	private Label lblName, lblID, lblTitle;
	@FXML
	private TableView<Note> notesTable;
	@FXML
	private TableColumn<Note, String> colID, colName, colNote, colNote_date;
	private ObservableList<Note> listNotes = FXCollections.observableArrayList();
	@FXML
	private TableView<Grade> gradesTable;
	@FXML
	private TableColumn<Grade, String> colID2, colName2, colQuiz, colMidterm, colFinal;
	private ObservableList<Grade> listGrades = FXCollections.observableArrayList();
	private ObservableList<String> listCourses = FXCollections.observableArrayList();
	@FXML
	private RadioButton radioNotes, radioCourses;
	@FXML
	private GridPane gridNotes, gridCourses;
	@FXML
	private ComboBox<String> coursesList;
	@FXML
	private Button btnAddNote;
	private static String ID, NAME;
	private static Stage primaryStage;
	private static Statement statement;
	private ResultSet resultNotes, resultGrades, resultClasses;
	private String courseID, classGrade;
	private HashMap<String, String> courses_ids;
	private Stage noteWindow;

	public static void setPrimaryStage(Stage stage) {
		primaryStage = stage;
	}

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
	public void initialize() throws IOException {
		init_labels();
		init_notes_table();
		init_grades_table();
		loadNotes();
		loadCourses();
	}

	public void init_labels() {
		lblName.setText(NAME);
		lblID.setText(ID);
	}

	public void init_notes_table() {
		notesTable.visibleProperty().bind(radioNotes.selectedProperty());
		btnAddNote.visibleProperty().bind(radioNotes.selectedProperty());
		colID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
		colName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
		colNote.setCellValueFactory(new PropertyValueFactory<>("note_content"));
		colNote.setCellFactory(TextFieldTableCell.forTableColumn());
		colNote_date.setCellValueFactory(new PropertyValueFactory<>("note_date"));
		notesTable.setItems(listNotes);
	}

	public void init_grades_table() {
		gradesTable.visibleProperty().bind(radioCourses.selectedProperty());
		coursesList.visibleProperty().bind(radioCourses.selectedProperty());
		colID2.setCellValueFactory(new PropertyValueFactory<>("student_id"));
		colName2.setCellValueFactory(new PropertyValueFactory<>("student_name"));
		colQuiz.setCellValueFactory(new PropertyValueFactory<>("quiz"));
		colMidterm.setCellValueFactory(new PropertyValueFactory<>("mid"));
		colFinal.setCellValueFactory(new PropertyValueFactory<>("fin"));
		colQuiz.setCellFactory(TextFieldTableCell.forTableColumn());
		colMidterm.setCellFactory(TextFieldTableCell.forTableColumn());
		colFinal.setCellFactory(TextFieldTableCell.forTableColumn());
	}

	public void loadNotes() {
		try {
			Note note;
			String id, name, note_content, note_date;
			listNotes.clear();
			String selectNotes = "SELECT students.student_name, students.student_id, notes.note_content, notes.note_date"
					+ " FROM  notes, students" + " WHERE students.student_id = notes.student_id"
					+ " AND notes.teacher_id = " + ID + " ;";
			resultNotes = statement.executeQuery(selectNotes);
			while (resultNotes.next()) {
				id = resultNotes.getString(2);
				name = resultNotes.getString(1);
				note_content = resultNotes.getString(3);
				note_date = resultNotes.getString(4);
				note = new Note(name, id, note_content, note_date);
				listNotes.add(note);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadCourses() {
		try {
			String selectClasses = String.format(
					"select concat(courses.course_name, ', ', courses.class_grade, teaches.classroom) as course_class, "
							+ " courses.course_id " + "from (teaches inner join courses using (course_id)) "
							+ " where teacher_id = %s;",
					ID);
			courses_ids = new HashMap<>();
			resultClasses = statement.executeQuery(selectClasses);
			while (resultClasses.next()) {
				listCourses.add(resultClasses.getString("course_class"));
				courses_ids.put(resultClasses.getString("course_class"), resultClasses.getString("course_id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		coursesList.setItems(listCourses);
	}

	@FXML
	public void onEditNotes(CellEditEvent<Note, String> edittedCell) {
		try {
			Note note = notesTable.getSelectionModel().getSelectedItem();
			note.setNote_content(edittedCell.getNewValue());
			String updateNote = String.format(
					"UPDATE notes SET note_content='%s' WHERE student_id=%s and teacher_id=%s;",
					edittedCell.getNewValue(), note.getStudent_id(), ID);
			String updateDate = String.format("update notes set note_date=now() where student_id=%s and teacher_id=%s;",
					note.getStudent_id(), ID);
			statement.executeUpdate(updateNote);
			statement.executeUpdate(updateDate);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditQuiz(CellEditEvent<Grade, String> edittedCell) {
		try {
			courseID = courses_ids.get(coursesList.getValue());
			Grade grade = gradesTable.getSelectionModel().getSelectedItem();
			grade.setQuiz(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE grade SET quiz='%s' WHERE student_id=%s and course_id=%s;",
					edittedCell.getNewValue(), grade.getStudent_id(), courseID);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditMidterm(CellEditEvent<Grade, String> edittedCell) {
		try {
			courseID = courses_ids.get(coursesList.getValue());
			Grade grade = gradesTable.getSelectionModel().getSelectedItem();
			grade.setQuiz(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE grade SET midterm='%s' WHERE student_id=%s and course_id=%s;",
					edittedCell.getNewValue(), grade.getStudent_id(), courseID);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditFinal(CellEditEvent<Grade, String> edittedCell) {
		try {
			courseID = courses_ids.get(coursesList.getValue());
			Grade grade = gradesTable.getSelectionModel().getSelectedItem();
			grade.setQuiz(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE grade SET final='%s' WHERE student_id=%s and course_id=%s;",
					edittedCell.getNewValue(), grade.getStudent_id(), courseID);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onRadioNotes() {
		lblTitle.setText("Recommendations");
	}

	@FXML
	public void onRadioCourses() {
		if (coursesList.getValue() == null)
			lblTitle.setText("Class:  \t Course: ");
		else
			lblTitle.setText(String.format("Class: %s \t Course: %s", classGrade, coursesList.getValue().split(",")[0]));
	}

	@FXML
	public void onAddNote() throws IOException {
		noteWindow = new Stage();
		noteWindow.initModality(Modality.WINDOW_MODAL);
		noteWindow.initOwner(primaryStage);
		noteWindow.setTitle("Add a new note");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addNoteView.fxml"));
		addNoteController.Tcontroller = this;

		noteWindow.setScene(new Scene(loader.load()));
		noteWindow.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent e) {
				e.consume();
			}
		});

		InputStream file;

		try {
			file = new FileInputStream(new File("assets/primary-school.png"));
			noteWindow.getIcons().add(new Image(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		noteWindow.show();
	}

	@FXML
	public void onLogOut() throws IOException {
		Main.switchToMain();
	}

	public void saveNote(String student_id, String note) {
		try {
			String insert = String.format("insert into notes values (null, %s, %s, '%s', now());", ID, student_id,
					note);
			statement.executeUpdate(insert);
			loadNotes();
			notesTable.refresh();
			noteWindow.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void cancelNote() {
		noteWindow.close();
	}

	public void onSelect() {
		try {
			String selectedValue = coursesList.getValue();
			courseID = courses_ids.get(selectedValue);
			String classroom = "" + selectedValue.split(",")[1].charAt(2);

			Grade grade = null;
			String id, name, quiz, mid, fin;
			String selectGrades = String.format(
					"select students.student_id, students.student_name, quiz, midterm ,final "
							+ "from (grade inner join students using (student_id)) inner join teaches using (course_id, classroom)"
							+ "where teaches.teacher_id = %s and grade.course_id = %s and teaches.classroom='%s';",
					ID, courseID, classroom);
			resultGrades = statement.executeQuery(selectGrades);

			listGrades.clear();
			while (resultGrades.next()) {
				id = resultGrades.getString(1);
				name = resultGrades.getString(2);
				quiz = resultGrades.getString(3);
				mid = resultGrades.getString(4);
				fin = resultGrades.getString(5);
				grade = new Grade(id, name, quiz, mid, fin);
				listGrades.add(grade);
			}

			gradesTable.setItems(listGrades);
			classGrade = coursesList.getValue().split(",")[1].trim();
			lblTitle.setText(
					String.format("Class: %s \t Course: %s", classGrade, coursesList.getValue().split(",")[0]));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
