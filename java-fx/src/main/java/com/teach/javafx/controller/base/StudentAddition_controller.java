package com.teach.javafx.controller.base;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DO.User;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    private Integer studentId = null;
    private Integer personId = null;


    @FXML
    void onCancel() {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConformation() {
        if( cardField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            return;
        }
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
        p.setType("student");
        s.setPerson(p);
        DataRequest req=new DataRequest();
        User user = new User();
        req.add("student",s);
        req.add("user",user);
        DataResponse res = HttpRequestUtil.request("/api/student/addStudent",req);
        if(res.getCode()==401) {//
            MessageDialog.showDialog("已存在！");
        }
        else {

            MessageDialog.showDialog("提交成功！");
        }
    }
}
