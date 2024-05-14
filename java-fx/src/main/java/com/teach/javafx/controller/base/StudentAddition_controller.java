package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.BeforeUniversity;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DO.User;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import com.teach.javafx.utils.JsonUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentAddition_controller {
    @FXML
    private TextField numberField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField politicsField;
    @FXML
    private TextField deptField;
    @FXML
    private TextField majorField;
    @FXML
    private TextField classField;
    @FXML
    private TextField cardField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker birthdayPick;

    @FXML
    private TextField graduatedProvince;
    @FXML
    private TextField graduatedSchool;

    @FXML
    private Button onCancel;

    @FXML
    void onCancel() {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmation() {
        if( numberField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Student s = getStudent();
        BeforeUniversity beforeUniversity=new BeforeUniversity();
        beforeUniversity.setGraduatedProvince(graduatedProvince.getText());
        beforeUniversity.setGraduatedSchool(graduatedSchool.getText());

        DataRequest req=new DataRequest();
        User user = new User();
        req.add("student",s);
        req.add("user",user);
        DataResponse res = HttpRequestUtil.request("/api/student/addStudent",req);
        s= JSON.parseObject(JSON.toJSONString(res.getData()),Student.class);
        beforeUniversity.setStudent(s);
        DataRequest request=new DataRequest();
        request.add("beforeUniversity",beforeUniversity);
        DataResponse response=HttpRequestUtil.request("/api/beforeUniversity/add",request);

        if(res.getCode()==401) {
            MessageDialog.showDialog("该学生已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            StudentManageController.updateDataTableView();
        }
    }

    private Student getStudent() {
        Student s=new Student();
        Person p=new Person();

        p.setIntroduce("");
        s.setStudentId(Long.valueOf(numberField.getText()));
        p.setNumber(Long.valueOf(numberField.getText()));
        p.setName(nameField.getText());
        p.setDept(deptField.getText());
        s.setMajor(majorField.getText());
        s.setClassName(classField.getText());
        p.setCard(cardField.getText());
        p.setGender(genderField.getText());
        p.setBirthday(birthdayPick.getEditor().getText());
        p.setEmail(emailField.getText());
        p.setPhone(phoneField.getText());
        p.setAddress(addressField.getText());
        p.setPoliticalStatus(politicsField.getText());
        p.setType("student");
        s.setPerson(p);
        return s;
    }
}
