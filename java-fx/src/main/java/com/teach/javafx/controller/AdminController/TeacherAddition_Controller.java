package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Teacher;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeacherAddition_Controller {

    @FXML
    private TextField teacherIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField politicsField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField deptField;
    @FXML
    private TextField degreeField;
    @FXML
    private TextField cardField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker birthdayPicker;


    @FXML
    private Button onCancel;


    @FXML
    void onCancel() {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmation() {
        if( teacherIdField.getText().equals("")) {
            MessageDialog.showDialog("工号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Teacher t = getTeacher();

        DataRequest req=new DataRequest();
        req.add("teacher",t);
        DataResponse res = HttpRequestUtil.request("/api/teacher/addTeacher",req);

        if(res.getCode()==401) {
            MessageDialog.showDialog("该教师已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            TeacherManageController.updateDataTableView();
        }
    }

    private Teacher getTeacher() {
        Teacher t=new Teacher();
        Person p=new Person();
        p.setIntroduce("");
        t.setTeacherId(Long.valueOf(teacherIdField.getText()));
        p.setNumber(Long.valueOf(teacherIdField.getText()));
        p.setName(nameField.getText());
        p.setDept(deptField.getText());
        t.setDegree(degreeField.getText());
        t.setTitle(titleField.getText());
        p.setCard(cardField.getText());
        p.setGender(genderField.getText());
        p.setBirthday(birthdayPicker.getEditor().getText());
        p.setEmail(emailField.getText());
        p.setPhone(phoneField.getText());
        p.setAddress(addressField.getText());
        p.setPoliticalStatus(politicsField.getText());
        p.setType("teacher");
        t.setPerson(p);
        return t;
    }
}
