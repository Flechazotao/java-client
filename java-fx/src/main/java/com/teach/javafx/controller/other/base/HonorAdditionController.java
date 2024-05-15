package com.teach.javafx.controller.other.base;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
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

public class HonorAdditionController {

    @FXML
    private TextField studentIdField;
    @FXML
    private TextField studentNameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField honorNameField;
    @FXML
    private DatePicker honorTimePicker;
    @FXML
    private TextField levelField;
    @FXML
    private TextField honorFromField;

    @FXML
    private Button onCancel;
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
        }
        HonorInfo honorInfo=getHonorInfo();
        DataRequest req=new DataRequest();
        req.add("honorInfo",honorInfo);
        DataResponse res = HttpRequestUtil.request("/api/honorInfo/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该课程已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            HonorManageController.updateDataTableView();
        }
    }

    private HonorInfo getHonorInfo() {
        HonorInfo honorInfo=new HonorInfo();
        Student s=new Student();
        Person person=new Person();
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentNameField.getText());
        s.setPerson(person);
        honorInfo.setStudent(s);
        honorInfo.setType(typeField.getText());
        honorInfo.setHonorName(honorNameField.getText());
        honorInfo.setHonorTime(honorTimePicker.getValue()==null ? LocalDate.now().toString() : honorTimePicker.getValue().toString());
        honorInfo.setLevel(levelField.getText());
        honorInfo.setHonorFrom(honorFromField.getText());
        return honorInfo;
    }
}
