package com.teach.javafx.controller.AdminController;


import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CourseSelected_Controller extends manage_MainFrame_controller {
//    @FXML
//    private TextField InquireField;
//
//    @FXML
//    private TableColumn<?, ?> change;
//
//    @FXML
//    private TableColumn<?, ?> courseBeginWeek;
//
//    @FXML
//    private TableColumn<?, ?> courseName;
//
//    @FXML
//    private TableColumn<?, ?> courseNumber;
//
//    @FXML
//    private TableColumn<?, ?> courseTime;
//
//    @FXML
//    private TableColumn<?, ?> credit;
//
//    @FXML
//    private TableView<SelectedCourseInfo> dataTableView;
//
//    @FXML
//    private TableColumn<?, ?> delete;
//
//    @FXML
//    private TableColumn<?, ?> location;
//
//    @FXML
//    private TableColumn<?, ?> maxSelectedColumn;
//
//    @FXML
//    private TableColumn<?, ?> nowSelectNumberColumn;
//
//    @FXML
//    private Button onAddCourse;
//
//    @FXML
//    private Button onInquire;
//
//    @FXML
//    private TableColumn<?, ?> teacherName;
//
//    @FXML
//    private TableColumn<?, ?> type;
//
//    @FXML
//    private TableColumn<?, ?> wayOfTest;


    public void onInquire(ActionEvent actionEvent) {

    }

    public void onAddCourse(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelected-Addition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加选课信息");
        stage.show();
    }

    public void onReturn(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseManage_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((CourseManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

