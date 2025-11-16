import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.SQLOutput;

public class courseEntryController {
@FXML
    private TextField course_name;
@FXML
    private TextField course_code;
@FXML
    private TextField teacher1_name;
@FXML
    private TextField teacher2_name;
@FXML
    private TextField credit;
@FXML
    private ComboBox<String> grade;
@FXML
    private Button add_courses;

private ObservableList<course>courseList= FXCollections.observableArrayList();

@FXML
private TableView<course> courseTable;
@FXML
private TableColumn<course,Integer>courseCodeCol;
@FXML
private TableColumn<course,String>courseNameCol;
@FXML
private TableColumn<course,String>teacher1NameCol;
@FXML
private TableColumn<course,String>teacher2NameCol;
@FXML
private TableColumn<course,Double>creditCol;
@FXML
private TableColumn<course,String>gradeCol;



@FXML
public void initialize(){
    grade.getItems().addAll("A+","A","A-","B+","B","B-","C+","C","C-","D","F");
    courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
    courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
    teacher1NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher1Name"));
    teacher2NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher2Name"));
    creditCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));
    gradeCol.setCellValueFactory(new PropertyValueFactory<>("gradeValue"));

    courseTable.setItems(courseList);
}

public void addCourses(ActionEvent event) {

    try {
        String courseName = course_name.getText();
        int courseCode = Integer.parseInt(course_code.getText());
        String teacher1Name = teacher1_name.getText();
        String teacher2Name = teacher2_name.getText();
        Double creditValue = Double.parseDouble(credit.getText());
        String gradeValue = grade.getValue();

        if (courseName.isEmpty() || courseCode == 0 || teacher1Name.isEmpty() || teacher2Name.isEmpty() || creditValue == 0 || gradeValue.isEmpty()) {
            System.out.println("Please fill all the fields");
            return;
        }

        course c = new course(courseName, courseCode, teacher1Name, teacher2Name, creditValue, gradeValue);
        courseList.add(c);
        course_name.clear();
        course_code.clear();
        teacher1_name.clear();
        teacher2_name.clear();
        credit.clear();
        grade.setValue(null);

    } catch(NumberFormatException e){
        System.out.println("Invalid number added");
    }

}

public ObservableList<course> getCourseList() {
    return courseList;
}


}


