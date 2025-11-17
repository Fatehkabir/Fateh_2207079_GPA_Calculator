import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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

    resultcalculate result = new resultcalculate();
    double answer=0.0;
    @FXML
    public void setCourseList(ObservableList<course>courses){
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("gradeValue"));
        teacher1NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher1Name"));
        teacher2NameCol.setCellValueFactory(new PropertyValueFactory<>("teacher2Name"));

        courseTable.setItems(courses);
        answer=result.calculateCGPA(courses);
        resultshow.setText("Total CGPA: "+String.valueOf(answer));
    }




}
