import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class resultcontroller {
    @FXML
    private TableView<course> courseTable;
    @FXML
    private TableColumn<course,Integer> courseCodeCol;
    @FXML
    private TableColumn<course,String>courseNameCol;
    @FXML
    private TableColumn<course,Double>creditCol;
    @FXML
    private TableColumn<course,String>teacher1NameCol;
    @FXML
    private TableColumn<course,String>teacher2NameCol;
    @FXML
    private TableColumn<course,String>gradeCol;

    @FXML
    private Label resultshow;

    @FXML
    private TextField rollField;
    @FXML
    private TextField nameField;

    @FXML
    private TableView<studentinfo> studentTable;
    @FXML
    private TableColumn<studentinfo,Integer> rollCol;
    @FXML
    private TableColumn<studentinfo,String> nameCol;
    @FXML
    private TableColumn<studentinfo,Double> gpaCol;

    resultcalculate result = new resultcalculate();
    double answer = 0.0;

    private ObservableList<studentinfo> studentList;

    @FXML
    public void initialize() {
        // Setup student table columns
        rollCol.setCellValueFactory(new PropertyValueFactory<>("roll"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        gpaCol.setCellValueFactory(new PropertyValueFactory<>("gpa"));

        // Create table if not exists
        studentTrack.createTable();

        // Load data from database
        studentList = studentTrack.getAllStudents();
        studentTable.setItems(studentList);
    }

    @FXML
    public void setCourseList(ObservableList<course> courses) {
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("gradeValue"));
        teacher1NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher1Name"));
        teacher2NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher2Name"));

        courseTable.setItems(courses);

        // Calculate CGPA
        answer = result.calculateCGPA(courses);
        resultshow.setText("Total CGPA: " + String.format("%.2f", answer));
    }

    @FXML
    private void handleSaveStudent() {
        String rollText = rollField.getText();
        String nameText = nameField.getText();

        if (!rollText.isEmpty() && !nameText.isEmpty()) {
            try {
                int roll = Integer.parseInt(rollText);
                double gpa = Math.round(answer * 100.0) / 100.0;

                // Add student to TableView
                studentinfo student = new studentinfo(roll, nameText, gpa);
                studentList.add(student);

                // Save student to database
                studentTrack.insertStudent(roll, nameText, gpa);

                // Clear input fields
                rollField.clear();
                nameField.clear();

            } catch (NumberFormatException e) {
                System.out.println("Invalid roll number!");
            }
        }
    }
}
