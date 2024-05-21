package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.base.CourseSelectedS_Controller;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.io.IOException;
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
    private ComboBox<String> typeField;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;
    @FXML
    private Button inSelectingCourse;

    @Getter
    private static List<Course> courseList = new ArrayList<>();
    private static String[] typelist = {"必修", "选修", "通选", "限选", "任选"};

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
        //展示课程类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }
    }

    public void inSelectingCourse(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelect-S.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((CourseSelectedS_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onInquire(ActionEvent actionEvent) {
        String query = typeField.getValue();
        DataRequest req1 = new DataRequest();
        req1.add("type", query);
        DataResponse res = HttpRequestUtil.request("/api/course/findByCourseType", req1);
        courseList = JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);
        setDataTableView(courseList);
    }
}
