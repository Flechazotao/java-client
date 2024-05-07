package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
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

import java.util.ArrayList;
import java.util.List;

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

    private Integer studentId = null;
    private Integer personId = null;

    private List<Student> studentList = new ArrayList<>();
    public void initialize(){
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        studentList= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);




    }
    public void onConformation(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) {

        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }
}
