import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
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
    private TableColumn<course,String>gradeCol;



    @FXML
    public void setCourseList(ObservableList<course>courses){
        courseNameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        courseCodeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
        creditCol.setCellValueFactory(new PropertyValueFactory<>("creditValue"));
        gradeCol.setCellValueFactory(new PropertyValueFactory<>("gradeValue"));

        courseTable.setItems(courses);

    }

}
