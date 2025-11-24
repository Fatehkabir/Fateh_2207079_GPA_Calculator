import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;

public class courseEntryController {
    @FXML private TextField course_name;
    @FXML private TextField course_code;
    @FXML private TextField teacher1_name;
    @FXML private TextField teacher2_name;
    @FXML private TextField credit;
    @FXML private ComboBox<String> grade;
    @FXML private Button add_courses;
    @FXML private Button calculate;
    @FXML private Button update_course;
    @FXML private Button delete_course;
    @FXML private Label updateLabel;

    @FXML private TableView<course> courseTable;
    @FXML private TableColumn<course,Integer> courseCodeCol;
    @FXML private TableColumn<course,String> courseNameCol;
    @FXML private TableColumn<course,String> teacher1NameCol;
    @FXML private TableColumn<course,String> teacher2NameCol;
    @FXML private TableColumn<course,Double> creditCol;
    @FXML private TableColumn<course,String> gradeCol;

    private ObservableList<course> courseList;

    @FXML
    public void initialize() {
        DatabaseHelper.initDatabase();
        grade.getItems().addAll("A+","A","A-","B+","B","B-","C+","C","D","F");

        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        teacher1NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher1Name"));
        teacher2NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher2Name"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("gradeValue"));

        courseList = FXCollections.observableArrayList();
        courseTable.setItems(courseList);

        courseTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if(newSelection != null) {
                course_name.setText(newSelection.getCourseName());
                course_code.setText(String.valueOf(newSelection.getCourseCode()));
                teacher1_name.setText(newSelection.getTeacher1Name());
                teacher2_name.setText(newSelection.getTeacher2Name());
                credit.setText(String.valueOf(newSelection.getCreditValue()));
                grade.setValue(newSelection.getGradeValue());
            }
        });

        loadCoursesFromDatabase();
    }

    private void loadCoursesFromDatabase() {
        Task<ObservableList<course>> task = new Task<>() {
            @Override
            protected ObservableList<course> call() {
                return DatabaseHelper.getAllCourses();
            }
        };

        task.setOnSucceeded(e -> courseList.setAll(task.getValue()));
        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            updateLabel.setText("Failed to load courses.");
        });

        startDbTask(task);
    }

    public void calculateGPA(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("resultscreen.fxml"));
        Parent root= loader.load();
        resultcontroller resultcontrol=loader.getController();
        resultcontrol.setCourseList(courseList);
        Stage stage=(Stage)((Button)event.getSource()).getScene().getWindow();
        Scene scene=new Scene(root);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void addCourses(ActionEvent event) {
        if (course_name.getText().isEmpty() ||
                course_code.getText().isEmpty() ||
                teacher1_name.getText().isEmpty() ||
                teacher2_name.getText().isEmpty() ||
                credit.getText().isEmpty() ||
                grade.getValue() == null) {

            updateLabel.setText("Please fill all fields");
            return;
        }

        try {
            int code = Integer.parseInt(course_code.getText());
            double cr = Double.parseDouble(credit.getText());

            course c = new course(
                    course_name.getText(),
                    code,
                    teacher1_name.getText(),
                    teacher2_name.getText(),
                    cr,
                    grade.getValue()
            );

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    DatabaseHelper.insertCourse(c);
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                courseList.add(c);
                updateLabel.setText("Course added");
                clearFields();
            });

            task.setOnFailed(e -> {
                task.getException().printStackTrace();
                updateLabel.setText("Error adding course");
            });

            startDbTask(task);

        } catch (NumberFormatException e) {
            updateLabel.setText("Invalid number for code or credit");
        }
    }

    @FXML
    public void updateCourse(ActionEvent event) {
        course selected = courseTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        if (course_name.getText().isEmpty() ||
                course_code.getText().isEmpty() ||
                teacher1_name.getText().isEmpty() ||
                teacher2_name.getText().isEmpty() ||
                credit.getText().isEmpty() ||
                grade.getValue() == null) {

            updateLabel.setText("Please fill all fields");
            return;
        }

        try {
            int code = Integer.parseInt(course_code.getText());
            double cr = Double.parseDouble(credit.getText());

            course updated = new course(
                    course_name.getText(),
                    code,
                    teacher1_name.getText(),
                    teacher2_name.getText(),
                    cr,
                    grade.getValue()
            );

            Task<Void> task = new Task<>() {
                @Override
                protected Void call() {
                    DatabaseHelper.updateCourse(updated);
                    return null;
                }
            };

            task.setOnSucceeded(e -> {
                courseList.set(courseTable.getSelectionModel().getSelectedIndex(), updated);
                updateLabel.setText("Course updated");
                clearFields();
            });

            task.setOnFailed(e -> {
                task.getException().printStackTrace();
                updateLabel.setText("Error updating course");
            });

            startDbTask(task);

        } catch (NumberFormatException e) {
            updateLabel.setText("Invalid number for code or credit");
        }
    }

    @FXML
    public void deleteCourse(ActionEvent event) {
        course selected = courseTable.getSelectionModel().getSelectedItem();
        if(selected == null) return;

        Task<Void> task = new Task<>() {
            @Override
            protected Void call() {
                DatabaseHelper.deleteCourse(selected.getCourseCode());
                return null;
            }
        };

        task.setOnSucceeded(e -> {
            courseList.remove(selected);
            updateLabel.setText("Course deleted");
            clearFields();
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
            updateLabel.setText("Error deleting course");
        });

        startDbTask(task);
    }

    private void clearFields() {
        course_name.clear();
        course_code.clear();
        teacher1_name.clear();
        teacher2_name.clear();
        credit.clear();
        grade.setValue(null);
    }

    private <T> void startDbTask(Task<T> task) {
        Thread t = new Thread(task);
        t.setDaemon(true);
        t.start();
    }
}
