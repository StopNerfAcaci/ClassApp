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
import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.time.ZoneId;
import java.util.*;

import com.example.demo.ClassApp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import utils.AlertMessage;
import utils.ConnectionPool;


public class ClassController implements Initializable {

    @FXML
    private Label total_current_value;

    @FXML
    private Label total_teacher_values;

    @FXML
    private Label total_values;

    @FXML
    private Button dashboard_btn;
    @FXML
    private Button class_btn;
    @FXML
    private TextField class_name;

    @FXML
    private TextField department;

    @FXML
    private ComboBox<String> status;

    @FXML
    private TextField instructor;

    @FXML
    private TextField semester;

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
    private TableColumn<ClassObject, String> addClass_col_instructor;

    @FXML
    private TableColumn<ClassObject, String> addClass_col_semester;

    @FXML
    private AnchorPane class_form;
    @FXML
    private AnchorPane dashboard_form;
    @FXML
    private Button addBtn;

    @FXML
    private Button updateBtn;

    @FXML
    private Button clearBtn;

    @FXML
    private Button deleteBtn;


    @FXML
    private LineChart<?, ?> classDataChart;

    @FXML
    private BarChart<?, ?> semesterClassChart;

    private int classID = 0;

    private AlertMessage alert = new AlertMessage();
    private  ClassFunction classFunction = new ClassFunctionImpl( ClassApp.getConnectionPool());
    private String[] statusList = {"active","inactive"};

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
        addClass_col_instructor.setCellValueFactory(new PropertyValueFactory<>("instructorName"));
        addClass_col_dateAdd.setCellValueFactory(new PropertyValueFactory<>("dateAdd"));
        addClass_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addClass_col_semester.setCellValueFactory(new PropertyValueFactory<>("semester"));

        addClass_tableView.setItems(addClassList);
    }


    public void drawSemesterChart(){
        int[] semester={0,0,0,0,0,0};
        semesterClassChart.getData().clear();
        ArrayList<ClassObject> classes = classFunction.getClasses(new ClassObject(),"full");
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Semester");
        for(int i=0;i<classes.size();i++){
            switch(classes.get(i).getSemester()){
                case 1:
                    semester[0]++;
                    break;
                case 2:
                    semester[1]++;
                    break;
                case 3:
                    semester[2]++;
                    break;
                case 4:
                    semester[3]++;
                    break;
                case 5:
                    semester[4]++;
                    break;
                case 6:
                    semester[5]++;
                    break;
                default:
                    break;
            }

        }
        for(int i=0;i<6;i++){
            series.getData().add(new XYChart.Data<>( "Học kì "+String.valueOf(i+1),semester[i]));
        }
        semesterClassChart.getData().add(series);
    }

    public void drawDataChart(){
        int[] class_data = new int[12];
        classDataChart.getData().clear();
        ArrayList<ClassObject> classes = classFunction.getClasses(new ClassObject(),2024);
        XYChart.Series series = new XYChart.Series<>();
        series.setName("Month");

        for(int i=0;i<classes.size();i++){
            LocalDate dateAdd = classes.get(i).getDateAdd().toLocalDate();
            Month month = dateAdd.getMonth();
            switch(month){
                case JANUARY:
                    class_data[0]++;
                    break;
                case FEBRUARY:
                    class_data[1]++;
                    break;
                case MARCH:
                    class_data[2]++;
                    break;
                case APRIL:
                    class_data[3]++;
                    break;
                case MAY:
                    class_data[4]++;
                    break;
                case JUNE:
                    class_data[5]++;
                    break;
                case JULY:
                    class_data[6]++;
                    break;
                case AUGUST:
                    class_data[7]++;
                    break;
                case SEPTEMBER:
                    class_data[8]++;
                    break;
                case OCTOBER:
                    class_data[9]++;
                    break;
                case NOVEMBER:
                    class_data[10]++;
                    break;
                case DECEMBER:
                    class_data[11]++;
                    break;
                default:
                    break;
            }

        }
        series.getData().add(new XYChart.Data<>( "Jan",class_data[0]));
        series.getData().add(new XYChart.Data<>( "Feb",class_data[1]));
        series.getData().add(new XYChart.Data<>( "Mar",class_data[2]));
        series.getData().add(new XYChart.Data<>( "Apr",class_data[3]));
        series.getData().add(new XYChart.Data<>( "May",class_data[4]));
        series.getData().add(new XYChart.Data<>( "June",class_data[5]));
        series.getData().add(new XYChart.Data<>( "July",class_data[6]));
        series.getData().add(new XYChart.Data<>( "Aug",class_data[7]));
        series.getData().add(new XYChart.Data<>( "Sep",class_data[8]));
        series.getData().add(new XYChart.Data<>( "Oct",class_data[9]));
        series.getData().add(new XYChart.Data<>( "Nov",class_data[10]));
        series.getData().add(new XYChart.Data<>( "Dec",class_data[11]));
        classDataChart.getData().add(series);
    }

    int activeClassCount=0;
    public void displayTotalData(){
        ArrayList<ClassObject> classes = classFunction.getClasses(new ClassObject(),0,(byte)10);
        for(int i=0;i<classes.size();i++){
            if(Objects.equals(classes.get(i).getStatus(), "active")){
                activeClassCount++;
            }
        }
        total_values.setText(String.valueOf(classes.size()));
        total_current_value.setText(String.valueOf(activeClassCount));
    }
    public void displayTotalInstructor(){
        ArrayList<ClassObject> classes = classFunction.getClasses(new ClassObject(),0,(byte)10);
        List<String> stringList = new ArrayList<>();
        for(int i=0;i<classes.size();i++){
            stringList.add(classes.get(i).getInstructorName());
        }
        Set<String> distinctName = new HashSet<>(stringList);
        total_teacher_values.setText(String.valueOf(distinctName.size()));

    }

    public void addClassSelectItem() {
        ClassObject classObject = addClass_tableView.getSelectionModel().getSelectedItem();
        int num = addClass_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        class_name.setText(classObject.getClass_name());
        department.setText(classObject.getDepartment());
        instructor.setText(classObject.getInstructorName());
        status.getSelectionModel().select(classObject.getStatus());
        semester.setText(String.valueOf(classObject.getSemester()));
        classID = classObject.getClass_id();
    }
    public void addBtn(){
        if (class_name.getText().isEmpty()
                || department.getText().isEmpty()
                || instructor.getText().isEmpty()
                || semester.getText().isEmpty()
                || status.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            ClassObject classObject = new ClassObject();
            classObject.setClass_name(class_name.getText());
            classObject.setDepartment(department.getText());
            classObject.setInstructorName(instructor.getText());
            classObject.setSemester(Integer.parseInt(semester.getText()));
            classObject.setStatus(status.getSelectionModel().getSelectedItem());
            ClassFunction classFunction = new ClassFunctionImpl(ClassApp.getConnectionPool());
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
                || instructor.getText().isEmpty()
                || semester.getText().isEmpty()
                || status.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.errorMessage("Please fill all blank fields");
        } else {
            ClassObject classObject = new ClassObject();
            classObject.setClass_name(class_name.getText());
            classObject.setDepartment(department.getText());
            classObject.setStatus(status.getSelectionModel().getSelectedItem());
            classObject.setInstructorName(instructor.getText());
            classObject.setSemester(Integer.parseInt(semester.getText()));
            classObject.setClass_id(classID);
            ClassFunction classFunction = new ClassFunctionImpl(ClassApp.getConnectionPool());
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
        instructor.clear();
        semester.clear();
        status.getSelectionModel().clearSelection();
    }
    public void deleteBtn(){
        if (class_name.getText().isEmpty()
                || department.getText().isEmpty()
                || instructor.getText().isEmpty()
                || semester.getText().isEmpty()
                || status.getSelectionModel().getSelectedItem().isEmpty()) {
            alert.errorMessage("No data to delete");
        } else {
            ClassObject classObject = new ClassObject();
            classObject.setClass_id(classID);
            ClassFunction classFunction = new ClassFunctionImpl(ClassApp.getConnectionPool());
            if(classFunction.deleteClass(classObject)){
                addClassDisplayData();
                alert.successMessage("Delete Successfully!");
                addClassClear();
            }
        }
    }
    public void switchForm(ActionEvent event) {

        if (event.getSource() == dashboard_btn) {

            dashboard_form.setVisible(true);
            class_form.setVisible(false);
        }else if(event.getSource() == class_btn){
            dashboard_form.setVisible(false);
            class_form.setVisible(true);
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        status.getItems().addAll(statusList);
        addClassDisplayData();
        drawSemesterChart();
        displayTotalData();
        drawDataChart();
        displayTotalInstructor();
    }
}
