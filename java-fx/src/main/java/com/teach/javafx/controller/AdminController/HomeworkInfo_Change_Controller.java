package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeworkInfo_Change_Controller extends manage_MainFrame_controller {
    @FXML
    public ComboBox<String> courseNumberField;
    @FXML
    public ComboBox<String> courseNameField;
    @FXML
    public TextField homeworkNameField;
    @FXML
    public TextField fileField;

    @FXML
    public Button onConfirmation;
    @FXML
    public Button onCancel;
    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static HomeworkInfo homeworkInfo;
    ArrayList<String> courseNameList = new ArrayList<>();
    ArrayList<String> courseNumberList = new ArrayList<>();

    private static List<Course> courseList = new ArrayList<>();

    public void initialize(){
        homeworkInfo = HomeworkInfo_Manage_Controller.getHomeworkInfoList().get(index);

        courseNumberField.setValue(homeworkInfo.getCourse().getNumber());
        courseNameField.setValue(homeworkInfo.getCourse().getName());
        homeworkNameField.setText(homeworkInfo.getName());
        fileField.setText(homeworkInfo.getFile());
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        courseList= JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);
        int i=0;
        for (;i<courseList.size();i++){
            courseNameList.add(courseList.get(i).getName());
            courseNumberList.add(courseList.get(i).getNumber());
        }
        courseNameField.setItems(FXCollections.observableArrayList(courseNameList));
        courseNumberField.setItems(FXCollections.observableArrayList(courseNumberList));
        courseNameField.setVisibleRowCount(5);
        courseNumberField.setVisibleRowCount(5);
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( courseNumberField.getValue().equals("")) {
            MessageDialog.showDialog("课程号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setHomeworkInfo(homeworkInfo);
        DataRequest req=new DataRequest();
        req.add("homeworkInfo",homeworkInfo);
        DataResponse res = HttpRequestUtil.request("/api/homeworkInfo/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该课程信息已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("课程号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            HomeworkInfo_Manage_Controller.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setHomeworkInfo(HomeworkInfo homeworkInfo) {
        Course course=homeworkInfo.getCourse();
        homeworkInfo.setName(homeworkNameField.getText());
        course.setNumber(courseNumberField.getValue());
        course.setName(courseNameField.getValue());
    }

    public void courseNumberField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumberField.getSelectionModel().getSelectedIndex());
    }

    public void courseNameField(ActionEvent actionEvent) {
        courseNumberField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }
}
