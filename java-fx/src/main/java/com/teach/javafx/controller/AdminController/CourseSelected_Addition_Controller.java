package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorChangeController;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseSelected_Addition_Controller {

    @FXML
    private ComboBox<String> courseName;

    @FXML
    private ComboBox<String> courseNumber;

    @FXML
    private TextField maxSelectedField;

    @FXML
    private TextField nowSelectNumberField;

    @FXML
    private Button onCancel;

    @FXML
    private Button onConfirmation;

    private static List<Course> courseList = CourseManageController.getCourseList();

    ArrayList<String> courseNameList = new ArrayList<>();
    ArrayList<String> courseNumberList = new ArrayList<>();

    public void initialize(){
        int i=0;
        for (;i<courseList.size();i++){
            courseNameList.add(courseList.get(i).getName());
            courseNumberList.add(courseList.get(i).getNumber());
        }
        courseName.setItems(FXCollections.observableArrayList(courseNameList));
        courseNumber.setItems(FXCollections.observableArrayList(courseNumberList));
        courseName.setVisibleRowCount(5);
        courseNumber.setVisibleRowCount(5);
    }
    public void onConfirmation(ActionEvent actionEvent) {
        Course c=new Course();
        c.setName(courseName.getValue());
        c.setNumber(courseNumber.getValue());
        SelectedCourseInfo s=new SelectedCourseInfo();
        s.setCourse(c);
        s.setMaxNumberOfSelected(Integer.valueOf(maxSelectedField.getText()));
        s.setNumberOfSelected(Integer.valueOf(nowSelectNumberField.getText()));
        DataRequest req=new DataRequest();
        req.add("selectedCourseInfo",s);
        DataResponse res= HttpRequestUtil.request("/api/selectedCourseInfo/add",req);
        if (res.getCode()==401){
            MessageDialog.showDialog("信息不完整!");
        }
        else {
            MessageDialog.showDialog("添加成功!!!");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        CourseSelected_Controller.updateDataTableView();
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

   public void courseNumber(ActionEvent actionEvent) {
       courseName.getSelectionModel().select(courseNumber.getSelectionModel().getSelectedIndex());

   }

 public void courseName(ActionEvent actionEvent) {
        courseNumber.getSelectionModel().select(courseName.getSelectionModel().getSelectedIndex());
   }


}