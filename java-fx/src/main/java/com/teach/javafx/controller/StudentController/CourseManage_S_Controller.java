package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CourseManage_S_Controller extends student_MainFrame_controller{
    @FXML
    private TableView<Course> dataTableView;
    @FXML
    private TableColumn<Course,String> courseNumberColumn;
    @FXML
    private TableColumn<Course,String> courseNameColumn;
    @FXML
    private TableColumn<Course,String> creditColumn;
    @FXML
    private TableColumn<Course,String> courseTimeColumn;
    @FXML
    private TableColumn<Course,String> teacherNameColumn;
    @FXML
    private TableColumn<Course,String> courseBeginWeekColumn;
    @FXML
    private TableColumn<Course,String> wayOfTestColumn;
    @FXML
    private TableColumn<Course,String> locationColumn;
    @FXML
    private TableColumn<Course,String> typeColumn;
    @FXML
    private TableColumn<Course,String> fileColumn;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;
    @FXML
    private Button inSelectingCourse;

    @Getter
    private static List<Course> courseList = new ArrayList<>();

    private static ObservableList<Course> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Course> list){
        courseList=list;
        observableList.clear();
        for(Course course:courseList){
            observableList.addAll(FXCollections.observableArrayList(course));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Course.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        courseList= JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeekColumn.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

        TableView.TableViewSelectionModel<Course> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(courseList);
    }

    public void inSelectingCourse(ActionEvent actionEvent) {

    }

    public void onInquire(ActionEvent actionEvent) {
    }
}
