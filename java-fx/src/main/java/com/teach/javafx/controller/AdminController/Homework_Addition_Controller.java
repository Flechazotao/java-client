package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Homework_Addition_Controller {
    @FXML
    public TextField homeworkInfoIdField;
    @FXML
    public TextField courseNameField;
    @FXML
    public TextField homeworkNameField;
    @FXML
    public TextField studentNameField;
    @FXML
    public TextField submitStatusField;
    @FXML
    public TextField studentIdField;
    @FXML
    public TextField homeworkScoreField;
    @FXML
    public DatePicker submitTimePicker;

    @FXML
    public Button onCancel;
    @FXML
    public Button onConfirmation;

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }else if(homeworkInfoIdField.getText().isEmpty()){
            MessageDialog.showDialog("作业信息编号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Homework homework=getHomework();
        DataRequest req=new DataRequest();
        req.add("homework",homework);
        DataResponse res = HttpRequestUtil.request("/api/homework/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该作业已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("学号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            Homework_Manage_Controller.updateDataTableView();
        }
    }

    private Homework getHomework() {
        Homework homework=new Homework();
        Student s=new Student();
        Person person=new Person();
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentNameField.getText());
        s.setPerson(person);
        homework.setStudent(s);
        HomeworkInfo homeworkInfo=new HomeworkInfo();
        homeworkInfo.setHomeworkInfoId(Integer.valueOf(homeworkInfoIdField.getText()));
        homework.setIsSubmit(submitStatusField.getText());
//        if(submitStatusField.getText().equals("未提交")){
//            homework.setIsSubmit("未提交");
//            homework.setIsChecked("未审核");
//        }else if(submitStatusField.getText().equals("未审核")){
//            homework.setIsSubmit("已提交");
//            homework.setIsChecked("未审核");
//        }
//        else if(submitStatusField.getText().equals("已审核")){
//            homework.setIsSubmit("已提交");
//            homework.setIsChecked("已审核");
//        }
        homework.setHomeworkInfo(homeworkInfo);
        homework.setHomeworkScore(homeworkScoreField.getText());
        homework.setSubmitTime(submitTimePicker.getValue()==null ? LocalDate.now().toString() : submitTimePicker.getValue().toString());
        return homework;
    }
}
