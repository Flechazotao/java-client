package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DO.User;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class StudentChange_Controller {

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

    private Long studentId = null;
    private Integer personId = null;
    private int index=0;

    private ObservableList<StudentInfo> observableList= FXCollections.observableArrayList();
    private List<Student> studentList = new ArrayList<>();
    public void initialize(){
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        studentList= JSON.parseArray(JSON.toJSONString(res.getData()),Student.class);
        numField.setText(String.valueOf(studentList.get(getIndex()).getStudentId()));
        NameField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getName()));
        cardField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getCard()));
        PoliticsField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getPoliticalStatus()));
        SexField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getGender()));
        addressField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getAddress()));
        classField.setText(String.valueOf(studentList.get(getIndex()).getClassName()));
        emailField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getEmail()));
        majorField.setText(String.valueOf(studentList.get(getIndex()).getMajor()));
        phoneField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getPhone()));
    }
    public void onConformation(ActionEvent actionEvent) {
        if( numField.getText().equals("")) {
            MessageDialog.showDialog("学号不能为空");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Student s=studentList.get(getIndex());
        setStudent(s);
        DataRequest req=new DataRequest();
        req.add("student",s);
        DataResponse res = HttpRequestUtil.request("/api/student/addOrUpdateStudent",req);

        MessageDialog.showDialog("修改成功！");
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/StudentManage_Frame.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((StudentManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onCancel(ActionEvent actionEvent) {

        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setStudent(Student s) {
        Person p=s.getPerson();
        s.setStudentId(Long.valueOf(numField.getText()));
        p.setIntroduce("");
        p.setNumber(Long.valueOf(numField.getText()));
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
        s.setPerson(p);
    }
}
