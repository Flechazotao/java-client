package com.teach.javafx.controller.base;

import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DO.User;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentAddition_controller {
    @FXML
    private TextField cardField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PoliticsField;

    @FXML
    private TextField SexField;

    @FXML
    private TextField numField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField classField;

    @FXML
    private TextField deptField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button onCancel;

    @FXML
    private DatePicker birthdayPick;

    @FXML
    private StudentManageController studentManageController;

    @FXML
    void onCancel() {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConformation() {
        if( numField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Student s = getStudent();
        DataRequest req=new DataRequest();
        User user = new User();
        req.add("student",s);
        req.add("user",user);
        DataResponse res = HttpRequestUtil.request("/api/student/addStudent",req);
        if(res.getCode()==401) {//
            MessageDialog.showDialog("已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            studentManageController.initialize();
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/StudentManage_Frame.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((StudentManageController) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private Student getStudent() {
        Student s=new Student();
        Person p=new Person();
        p.setIntroduce("");
        p.setNumber(numField.getText());
        p.setName(NameField.getText());
        p.setDept(deptField.getText());
        s.setMajor(majorField.getText());
        s.setClassName(classField.getText());
        p.setCard(cardField.getText());
        p.setGender(SexField.getText());
        p.setBirthday(birthdayPick.getEditor().getText());
        p.setEmail(emailField.getText());
        p.setPhone(phoneField.getText());
        p.setAddress(addressField.getText());
        p.setPoliticalStatus(PoliticsField.getText());
        p.setType("student");
        s.setPerson(p);
        return s;
    }
}
