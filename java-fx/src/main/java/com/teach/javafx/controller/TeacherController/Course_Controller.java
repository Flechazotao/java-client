package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.CourseManageController;
import com.teach.javafx.controller.AdminController.CourseSelected_Controller;
import com.teach.javafx.controller.AdminController.Course_Change_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.SelectedCourseInfoInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Course_Controller extends Teacher_MainFrame_controller {
    @FXML
    private TableView<SelectedCourseInfoInfo> dataTableView;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> creditColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseTimeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> teacherNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseBeginWeekColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> wayOfTestColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> locationColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> typeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> nowSelectNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> maxSelectedColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> fileColumn;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

    private static ObservableList<SelectedCourseInfoInfo> observableList = FXCollections.observableArrayList();

    public static void setDataTableView(List<SelectedCourseInfo> list) {
        selectedCourseInfoList = list;
        observableList.clear();
        for (SelectedCourseInfo selectedCourseInfo : selectedCourseInfoList) {
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseInfoInfo(selectedCourseInfo)));
        }
    }

    public static void updateDataTableView() {
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll", new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll", new DataRequest());
        selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeekColumn.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("type"));//需要做成按钮
        nowSelectNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSelected"));
        maxSelectedColumn.setCellValueFactory(new PropertyValueFactory<>("MaxNumberOfSelected"));

        TableView.TableViewSelectionModel<SelectedCourseInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseInfoList);
    }
}

