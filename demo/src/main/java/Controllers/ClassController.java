package Controllers;


import Functions.ClassFunction;
import Functions.ClassFunctionImpl;
import Objects.ClassObject;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import utils.AlertMessage;
import utils.ConnectionPool;


public class ClassController implements Initializable {
    @FXML
    private TextField class_name;

    @FXML
    private TextField department;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TableView<ClassObject> addClass_tableView;
    @FXML
    private TableColumn<ClassObject, String> addClass_col_dateAdd;

    @FXML
    private TableColumn<ClassObject, String> addClass_col_department;

    @FXML
    private TableColumn<ClassObject, String> addClass_col_name;

    @FXML
    private TableColumn<ClassObject, String> addClass_col_status;
    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button deleteBtn;

    private int classID = 0;
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;
    private AlertMessage alert = new AlertMessage();
    private  ClassFunction classFunction = new ClassFunctionImpl((ConnectionPool) null);
    private String[] statusList = {"Active","Inactive","Full","Finish"};

    public ObservableList<ClassObject> addClassGetData() {
        ObservableList<ClassObject> listData = FXCollections.observableArrayList();
        ArrayList<ClassObject> classes = classFunction.getClasses(new ClassObject(),0,(byte)10);
        listData.addAll(classes);
        return listData;
    }



    public void addClassDisplayData() {
        ObservableList<ClassObject> addClassList = addClassGetData();

        addClass_col_name.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        addClass_col_department.setCellValueFactory(new PropertyValueFactory<>("department"));
        addClass_col_dateAdd.setCellValueFactory(new PropertyValueFactory<>("dateAdd"));
        addClass_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        addClass_tableView.setItems(addClassList);
    }


    public void addClassSelectItem() {
        ClassObject classObject = addClass_tableView.getSelectionModel().getSelectedItem();
        int num = addClass_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        class_name.setText(classObject.getClass_name());
        department.setText(classObject.getDepartment());
        status.getSelectionModel().select(classObject.getStatus());
        classID = classObject.getClass_id();
    }
    public void addBtn(){
        if (class_name.getText().isEmpty()
                || department.getText().isEmpty()
                || status.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            ClassObject classObject = new ClassObject();
            classObject.setClass_name(class_name.getText());
            classObject.setDepartment(department.getText());
            classObject.setStatus(status.getSelectionModel().getSelectedItem());
            ClassFunction classFunction = new ClassFunctionImpl((ConnectionPool) null);
            if(classFunction.addClass(classObject)){
                addClassDisplayData();
                alert.successMessage("Added Successfully!");
                addClassClear();
            }
        }
    }
    public void updateBtn(){
        if (class_name.getText().isEmpty()
                || department.getText().isEmpty()
                || status.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            ClassObject classObject = new ClassObject();
            classObject.setClass_name(class_name.getText());
            classObject.setDepartment(department.getText());
            classObject.setStatus(status.getSelectionModel().getSelectedItem());
            classObject.setClass_id(classID);
            ClassFunction classFunction = new ClassFunctionImpl((ConnectionPool) null);
            if(classFunction.editClass(classObject)){
                addClassDisplayData();
                alert.successMessage("Edit Successfully!");
                addClassClear();
            }
        }
    }
    public void addClassClear() {
        class_name.clear();
        department.clear();
        status.getSelectionModel().clearSelection();
    }
    public void deleteBtn(){
        if (class_name.getText().isEmpty()
                || department.getText().isEmpty()
                || status.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.errorMessage("No data to delete");
        } else {
            ClassObject classObject = new ClassObject();
            classObject.setClass_id(classID);
            ClassFunction classFunction = new ClassFunctionImpl((ConnectionPool) null);
            if(classFunction.deleteClass(classObject)){
                addClassDisplayData();
                alert.successMessage("Delete Successfully!");
                addClassClear();
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status.getItems().addAll(statusList);
        addClassDisplayData();
    }
}
