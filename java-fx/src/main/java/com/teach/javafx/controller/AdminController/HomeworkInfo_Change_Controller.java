package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class HomeworkInfo_Change_Controller extends manage_MainFrame_controller {
    @FXML
    public TextField courseNumberField;
    @FXML
    public TextField courseNameField;
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

    public void initialize(){
        homeworkInfo = HomeworkInfo_Manage_Controller.getHomeworkInfoList().get(index);

        courseNumberField.setText(homeworkInfo.getCourse().getNumber());
        courseNameField.setText(homeworkInfo.getCourse().getName());
        homeworkNameField.setText(homeworkInfo.getName());
        fileField.setText(homeworkInfo.getFile());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( courseNumberField.getText().equals("")) {
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
        course.setNumber(courseNumberField.getText());
        course.setName(courseNameField.getText());
    }
}
