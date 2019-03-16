package admin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class adminController {

	private static String ID, NAME;
	private static Stage primaryStage;
	private Stage addStudentWindow, addTeacherWindow, addCourseWindow;
	private static Statement statement;
	private ResultSet resultStudents;

	@FXML
	private Label lblName, lblID;

	@FXML
	private TableView<Student> studentsTable;
	@FXML
	private TableColumn<Student, String> colStudentID, colStudentName, colStudentClassGrade, colStudentClassroom,
			colParentName, colParentID, colParentContact;
	private ObservableList<Student> studentsList = FXCollections.observableArrayList();

	@FXML
	private TableView<Teacher> teachersTable;
	@FXML
	private TableColumn<Teacher, String> colTeacherID, colTeacherName, colTeacherContact;
	private ObservableList<Teacher> teachersList = FXCollections.observableArrayList();

	@FXML
	private TableView<Course> coursesTable;
	@FXML
	private TableColumn<Course, String> colCourse, colInstructorID, colInstructorName;
	private ObservableList<Course> coursesList = FXCollections.observableArrayList();

	@FXML
	private ComboBox<String> classgradesList, classroomsList;
	ObservableList<String> cgList = FXCollections.observableArrayList("--class grade", "1", "2", "3", "4");
	ObservableList<String> crList = FXCollections.observableArrayList("--classroom", "A", "B");

	@FXML
	private RadioButton radioStudents, radioTeachers, radioCourses;
	@FXML
	private HBox studentsBox, teachersBox, coursesBox;

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
	public void initialize() {
		init_labels();

		loadStudents();
		init_students_table();

		loadTeachers();
		init_teachers_table();

		init_courses_table();

	}

	public void init_labels() {
		lblID.setText(ID);
		lblName.setText(NAME);
	}

	public void init_students_table() {
		studentsTable.visibleProperty().bind(radioStudents.selectedProperty());
		studentsBox.visibleProperty().bind(radioStudents.selectedProperty());
		colStudentID.setCellValueFactory(new PropertyValueFactory<>("student_id"));
		colStudentID.setCellFactory(TextFieldTableCell.forTableColumn());
		colStudentName.setCellValueFactory(new PropertyValueFactory<>("student_name"));
		colStudentName.setCellFactory(TextFieldTableCell.forTableColumn());
		colStudentClassGrade.setCellValueFactory(new PropertyValueFactory<>("class_grade"));
		colStudentClassGrade.setCellFactory(TextFieldTableCell.forTableColumn());
		colStudentClassroom.setCellValueFactory(new PropertyValueFactory<>("classroom"));
		colStudentClassroom.setCellFactory(TextFieldTableCell.forTableColumn());
		colParentName.setCellValueFactory(new PropertyValueFactory<>("parent_name"));
		colParentName.setCellFactory(TextFieldTableCell.forTableColumn());
		colParentID.setCellValueFactory(new PropertyValueFactory<>("parent_id"));
		colParentID.setCellFactory(TextFieldTableCell.forTableColumn());
		colParentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		colParentContact.setCellFactory(TextFieldTableCell.forTableColumn());
		studentsTable.setItems(studentsList);
	}

	public void init_teachers_table() {
		teachersTable.visibleProperty().bind(radioTeachers.selectedProperty());
		teachersBox.visibleProperty().bind(radioTeachers.selectedProperty());
		colTeacherID.setCellValueFactory(new PropertyValueFactory<>("teacher_id"));
		colTeacherID.setCellFactory(TextFieldTableCell.forTableColumn());
		colTeacherName.setCellValueFactory(new PropertyValueFactory<>("teacher_name"));
		colTeacherName.setCellFactory(TextFieldTableCell.forTableColumn());
		colTeacherContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
		colTeacherContact.setCellFactory(TextFieldTableCell.forTableColumn());
		teachersTable.setItems(teachersList);
	}

	public void init_courses_table() {
		coursesBox.visibleProperty().bind(radioCourses.selectedProperty());
		classgradesList.visibleProperty().bind(radioCourses.selectedProperty());
		classgradesList.setItems(cgList);
		classroomsList.visibleProperty().bind(radioCourses.selectedProperty());
		classroomsList.setItems(crList);
		colCourse.setCellValueFactory(new PropertyValueFactory<>("courseName"));
		colCourse.setCellFactory(TextFieldTableCell.forTableColumn());
		colInstructorName.setCellValueFactory(new PropertyValueFactory<>("instructor_name"));
		colInstructorName.setCellFactory(TextFieldTableCell.forTableColumn());
		colInstructorID.setCellValueFactory(new PropertyValueFactory<>("instructor_id"));
		colInstructorID.setCellFactory(TextFieldTableCell.forTableColumn());
		coursesTable.visibleProperty().bind(radioCourses.selectedProperty());
		coursesTable.setItems(coursesList);
	}

	public void loadStudents() {
		try {
			Student std;
			String student_id, student_name, class_grade, classroom, parent_id, parent_name, contact;
			String selectStudents = "select student_id, student_name, class_grade, "
					+ "class_room, parent_id, parent_name, parent_contact from ((students "
					+ "inner join parents using (parent_id)) inner join classes using (class_id));";
			resultStudents = statement.executeQuery(selectStudents);
			while (resultStudents.next()) {
				student_id = resultStudents.getString("student_id");
				student_name = resultStudents.getString("student_name");
				class_grade = resultStudents.getString("class_grade");
				classroom = resultStudents.getString("class_room");
				parent_id = resultStudents.getString("parent_id");
				parent_name = resultStudents.getString("parent_name");
				contact = resultStudents.getString("parent_contact");
				std = new Student(student_id, student_name, class_grade, classroom, parent_id, parent_name, contact);
				studentsList.add(std);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadTeachers() {
		try {
			Teacher teacher;
			String teacher_id, teacher_name, contact;
			String selectTeachers = "select teacher_id, teacher_name, teacher_contact from teachers;";
			ResultSet result = statement.executeQuery(selectTeachers);
			while (result.next()) {
				teacher_name = result.getString("teacher_name");
				teacher_id = result.getString("teacher_id");
				contact = result.getString("teacher_contact");
				teacher = new Teacher(teacher_name, teacher_id, contact);
				teachersList.add(teacher);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void loadCourses() {
		try {
			coursesList.clear();
			String class_grade = classgradesList.getValue();
			String classroom = classroomsList.getValue();
			Course crs;
			String course_id, course_name, instructor_name, instructor_id;
			// select course_name, class_grade, class_room, teacher_id, teacher_name from
			// ((courses inner join classes using (class_grade)) inner join teaches using
			// (course_id, class_id)) inner join teachers using (teacher_id);
			String selectCourses = String.format(
					"select course_name, course_id, class_grade, class_room, teacher_name, teacher_id from ((courses inner join classes using (class_grade)) left join teaches using (course_id, class_id)) left join teachers using (teacher_id) "
							+ "where class_grade=%s and class_room='%s';",
					class_grade, classroom);
			ResultSet result = statement.executeQuery(selectCourses);
			while (result.next()) {
				course_id = result.getString("course_id");
				course_name = result.getString("course_name");
				instructor_name = result.getString("teacher_name");
				instructor_id = result.getString("teacher_id");
				crs = new Course(course_id, course_name, class_grade, instructor_name, instructor_id);
				coursesList.add(crs);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void showClassrooms() {
		classroomsList.setValue("--classroom");
	}

	/*
	 * STUDENTS TABLE
	 */

	@FXML
	public void onEditStudentID(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String old_student_id = edittedCell.getOldValue();
			String new_student_id = edittedCell.getNewValue();
			std.setStudent_id(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE students SET student_id='%s' WHERE student_id=%s;",
					new_student_id, old_student_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditStudentName(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String student_id = std.getStudent_id();
			String new_student_name = edittedCell.getNewValue();
			std.setStudent_name(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE students SET student_name='%s' WHERE student_id=%s;",
					new_student_name, student_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditStudentClassGrade(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String student_id = std.getStudent_id();
			String new_class_grade = edittedCell.getNewValue();
			std.setClass_grade(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE students SET class_grade='%s' WHERE student_id=%s;",
					new_class_grade, student_id);
			statement.executeUpdate(selectStuff);
			insert_student_courses(std);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditStudentClassroom(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String student_id = std.getStudent_id();
			String new_classroom = edittedCell.getNewValue();
			std.setClassroom(new_classroom);
			String selectStuff = String.format("UPDATE students SET classroom='%s' WHERE student_id=%s;", new_classroom,
					student_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditStudentParentName(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String parent_id = std.getParent_id();
			String new_parent_name = edittedCell.getNewValue();
			std.setParent_name(new_parent_name);
			String selectStuff = String.format("UPDATE parents SET parent_name='%s' WHERE parent_id=%s;",
					new_parent_name, parent_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditStudentParentID(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String parent_id = std.getParent_id();
			String new_parent_id = edittedCell.getNewValue();
			std.setParent_id(new_parent_id);
			String selectStuff = String.format("UPDATE parents SET parent_id='%s' WHERE parent_id=%s;", new_parent_id,
					parent_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditStudentContact(CellEditEvent<Student, String> edittedCell) {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String parent_id = std.getParent_id();
			String new_contact = edittedCell.getNewValue();
			std.setContact(edittedCell.getNewValue());
			String selectStuff = String.format("UPDATE parents SET parent_contact='%s' WHERE parent_id=%s;",
					new_contact, parent_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onDeleteStudent() {
		try {
			Student std = studentsTable.getSelectionModel().getSelectedItem();
			String student_id = std.getStudent_id();
			String parent_id = std.getParent_id();
			String countParents = String.format("select count(parent_id) from students where parent_id=%s;", parent_id);
			ResultSet res = statement.executeQuery(countParents);
			res.next();
			int count = res.getInt(1);
			if (count == 1) {
				String deleteParent = String.format("delete from parents where parent_id=%s;", parent_id);
				statement.executeUpdate(deleteParent);
			}
			String deleteStudent = String.format("delete from students where student_id=%s;", student_id);
			statement.executeUpdate(deleteStudent);
			studentsList.remove(std);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert_student_courses(Student std) throws SQLException {
		String student_id = std.getStudent_id();
		// insert into grade (student_id, course_id) select student_id, course_id from
		// (students inner join classes using (class_id)) inner join courses using
		// (class_grade) where st
		String insertGrade = String.format("insert into grade (student_id, course_id) select student_id, course_id "
				+ "from (students inner join classes using (class_id)) inner join courses using (class_grade) where student_id=%s;",
				student_id);
		statement.executeUpdate(insertGrade);
	}

	public String getClassId(String class_grade, String class_room) {
		String getId = String.format("select class_id from classes where class_grade=%s and class_room='%s';",
				class_grade, class_room);
		ResultSet set;
		String class_id = null;
		try {
			set = statement.executeQuery(getId);
			set.next();
			class_id = set.getString("class_id");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return class_id;
	}

	public void insert_student_with_parent(String student_name, String student_password, String class_grade,
			String classroom, String parent_name, String parent_password, String contact) {
		try {
			// insert parent
			String insertParent = String.format(
					"insert into parents (parent_name, parent_password, parent_contact) values ('%s','%s','%s');",
					parent_name, parent_password, contact);
			statement.executeUpdate(insertParent);
			// get parent id
			String getParentID = "select max(parent_id) from parents;";
			ResultSet res = statement.executeQuery(getParentID);
			res.next();
			String parent_id = res.getString(1);
			// insert student
			String class_id = getClassId(class_grade, classroom);
			String insertStudent = String
					.format("insert into students (student_name, student_password, class_id, parent_id) "
							+ "values ('%s', '%s', '%s', '%s')", student_name, student_password, class_id, parent_id);
			statement.executeUpdate(insertStudent);
			// get student id
			String getStudentID = "select max(student_id) from students;";
			ResultSet resStd = statement.executeQuery(getStudentID);
			resStd.next();
			String student_id = resStd.getString(1);
			Student std = new Student(student_id, student_name, class_grade, classroom, parent_id, parent_name,
					contact);
			// insert student to grade table
			insert_student_courses(std);
			studentsList.add(std);
			addStudentWindow.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert_student(String student_name, String student_password, String class_grade, String classroom,
			String parent_id) {
		try {
			// insert student
			String class_id = getClassId(class_grade, classroom);

			String insertStudent = String
					.format("insert into students (student_name, student_password, class_id, parent_id) "
							+ "values ('%s', '%s', '%s', '%s')", student_name, student_password, class_id, parent_id);
			statement.executeUpdate(insertStudent);
			// get student id
			String getStudentID = "select max(student_id) from students;";
			ResultSet resStd = statement.executeQuery(getStudentID);
			resStd.next();
			String student_id = resStd.getString(1);
			// get parent info:
			String getParentInfo = String.format("select parent_name, parent_contact from parents where parent_id=%s;",
					parent_id);
			ResultSet resultParent = statement.executeQuery(getParentInfo);
			resultParent.next();
			String parent_name = resultParent.getString("parent_name");
			String contact = resultParent.getString("parent_contact");
			// refresh table
			Student std = new Student(student_id, student_name, class_grade, classroom, parent_id, parent_name,
					contact);
			insert_student_courses(std);
			studentsList.add(std);
			// studentsTable.refresh();
			addStudentWindow.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onAddStudent() throws IOException {
		addStudentWindow = new Stage();
		addStudentWindow.initModality(Modality.WINDOW_MODAL);
		addStudentWindow.initOwner(primaryStage);
		addStudentWindow.setTitle("Add a new student");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addStudent.fxml"));
		addStudentController.adController = this;
		addStudentWindow.setScene(new Scene(loader.load()));
		InputStream file;

		try {
			file = new FileInputStream(new File("assets/primary-school.png"));
			addStudentWindow.getIcons().add(new Image(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		addStudentWindow.show();
	}

	public void closeAddStudentWindow() {
		addStudentWindow.close();
	}

	/*
	 * TEACHERS TABLE
	 */

	@FXML
	public void onEditTeacherName(CellEditEvent<Teacher, String> edittedCell) {
		try {
			Teacher teacher = teachersTable.getSelectionModel().getSelectedItem();
			String teacher_id = teacher.getTeacher_id();
			String new_name = edittedCell.getNewValue();
			teacher.setTeacher_name(new_name);
			String selectStuff = String.format("UPDATE teachers SET teacher_name='%s' WHERE teacher_id=%s;", new_name,
					teacher_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@FXML
	public void onEditTeacherID(CellEditEvent<Teacher, String> edittedCell) {
		try {
			Teacher teacher = teachersTable.getSelectionModel().getSelectedItem();
			String teacher_id = teacher.getTeacher_id();
			String new_id = edittedCell.getNewValue();
			teacher.setTeacher_id(new_id);
			String selectStuff = String.format("UPDATE teachers SET teacher_id='%s' WHERE teacher_id=%s;", new_id,
					teacher_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditTeacherContact(CellEditEvent<Teacher, String> edittedCell) {
		try {
			Teacher teacher = teachersTable.getSelectionModel().getSelectedItem();
			String teacher_id = teacher.getTeacher_id();
			String new_contact = edittedCell.getNewValue();
			teacher.setContact(new_contact);
			String selectStuff = String.format("UPDATE teachers SET teacher_contact='%s' WHERE teacher_id=%s;",
					new_contact, teacher_id);
			statement.executeUpdate(selectStuff);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert_teacher(String teacher_name, String teacher_password, String teacher_contact)
			throws SQLException {
		Teacher teacher = new Teacher(teacher_name, teacher_password, teacher_contact);
		String insert_teacher = String.format(
				"insert into teachers (teacher_name, teacher_password, teacher_contact)" + "values ('%s', '%s', '%s')",
				teacher_name, teacher_password, teacher_contact);
		statement.executeUpdate(insert_teacher);
		String get_teacher_id = "select max(teacher_id) from teachers;";
		ResultSet result = statement.executeQuery(get_teacher_id);
		if (result.next())
			teacher.setTeacher_id(result.getString(1));
		teachersList.add(teacher);
		addTeacherWindow.close();
	}

	@FXML
	public void onAddTeacher() throws IOException {
		addTeacherWindow = new Stage();
		addTeacherWindow.initModality(Modality.WINDOW_MODAL);
		addTeacherWindow.initOwner(primaryStage);
		addTeacherWindow.setTitle("Add a new teacher");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addTeacher.fxml"));
		addTeacherController.adController = this;
		addTeacherWindow.setScene(new Scene(loader.load()));
		InputStream file;

		try {
			file = new FileInputStream(new File("assets/primary-school.png"));
			addTeacherWindow.getIcons().add(new Image(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		addTeacherWindow.show();
	}

	public void closeAddTeacherWindow() {
		addTeacherWindow.close();

	}

	@FXML
	public void onDeleteTeacher() {
		try {
			Teacher teacher = teachersTable.getSelectionModel().getSelectedItem();
			String teacher_id = teacher.getTeacher_id();
			String deleteTeacher = String.format("delete from teachers " + "where teacher_id=%s;", teacher_id);
			statement.executeUpdate(deleteTeacher);
			teachersList.remove(teacher);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * COURSES TABLE
	 */

	@FXML
	public void onEditCourseName(CellEditEvent<Course, String> edittedCell) {
		try {
			Course crs = coursesTable.getSelectionModel().getSelectedItem();
			String course_id = crs.getCourse_id();
			String new_course_name = edittedCell.getNewValue();
			crs.setCourseName(new_course_name);
			String update_course_name = String.format("update courses set course_name='%s' " + "where course_id=%s;",
					new_course_name, course_id);
			statement.executeUpdate(update_course_name);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onEditCourseInstructorID(CellEditEvent<Course, String> edittedCell) {
		try {
			Course crs = coursesTable.getSelectionModel().getSelectedItem();
			String classroom = classroomsList.getValue();
			String course_id = crs.getCourse_id();
			String new_instructor_id = edittedCell.getNewValue();
			String new_instructor_name = null;
			crs.setInstructor_id(new_instructor_id);

			// get the new instructor name
			String get_instructor_name = String.format("select teacher_name from teachers where teacher_id=%s;",
					new_instructor_id);
			ResultSet result = statement.executeQuery(get_instructor_name);
			if (result.next())
				new_instructor_name = result.getString("teacher_name");
			crs.setInstructor_name(new_instructor_name);

			// get the class id
			String class_grade = classgradesList.getValue();
			String getClsID = String.format("select class_id from classes where class_grade=%s and class_room='%s';",
					class_grade, classroom);
			ResultSet res = statement.executeQuery(getClsID);
			res.next();
			String class_id = res.getString("class_id");

			String update_instructor = String.format(
					"update teaches set teacher_id=%s where course_id=%s and class_id=%s;", new_instructor_id,
					course_id, class_id);
			statement.executeUpdate(update_instructor);

			coursesTable.refresh();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void insert_course(String courseName, String class_grade) throws SQLException {
		String insertCourse = String.format("insert into courses (course_name, class_grade) values " + "('%s', %s);",
				courseName, class_grade);
		statement.executeUpdate(insertCourse);
		String getCourseID = "select max(course_id) from courses;";
		ResultSet result = statement.executeQuery(getCourseID);
		String course_id = null;
		if (result.next())
			course_id = result.getString(1);

		// insert to 'teaches' table
		String insertToTeaches = String.format(
				"insert into teaches (course_id, class_id) select %s, class_id from classes where class_grade=%s;",
				course_id, class_grade);
		statement.executeUpdate(insertToTeaches);

		Course crs = new Course(course_id, courseName, class_grade);
		coursesList.add(crs);
		addCourseWindow.close();
	}

	@FXML
	public void onAddCourse() throws IOException {
		addCourseWindow = new Stage();
		addCourseWindow.initModality(Modality.WINDOW_MODAL);
		addCourseWindow.initOwner(primaryStage);
		addCourseWindow.setTitle("Add a new course");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("addCourse.fxml"));
		addCourseController.adController = this;
		addCourseWindow.setScene(new Scene(loader.load()));
		InputStream file;

		try {
			file = new FileInputStream(new File("assets/primary-school.png"));
			addCourseWindow.getIcons().add(new Image(file));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		addCourseWindow.show();
	}

	public void closeAddCourseWindow() {
		addCourseWindow.close();
	}

	@FXML
	public void onDeleteCourse() {
		try {
			Course crs = coursesTable.getSelectionModel().getSelectedItem();
			String coures_id = crs.getCourse_id();
			String deleteCourse = String.format("delete from courses " + "where course_id=%s;", coures_id);
			statement.executeUpdate(deleteCourse);
			coursesList.remove(crs);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void onLogOut() throws IOException {
		Main.switchToMain();
	}

}
