package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.HomeworkInfo;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.Getter;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class CourseSelectStudentAddition_Controller {

    @FXML
    private ComboBox<String> courseNameField;

    @FXML
    private ComboBox<String> courseNumField;

    @FXML
    private Button onCancel;

    @FXML
    private Button onConfirmation;

    @FXML
    private ComboBox<String> studentIdField;

    @FXML
    private ComboBox<String> studentNameField;

    @Getter
    private static List<HomeworkInfo> homeworkInfoList = new ArrayList<>();

    public List<Student> students;
    public void initialize(){


        //学生有关信息下拉框
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        students= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        for(Student student:students){
            studentIdField.getItems().add(student.getStudentId().toString());
            studentNameField.getItems().add(student.getPerson().getName());
        }

        //课程有关信息下拉框

        DataResponse response = HttpRequestUtil.request("/api/homeworkInfo/findAll",new DataRequest());
        homeworkInfoList= JSON.parseArray(JSON.toJSONString(response.getData()), HomeworkInfo.class);
        for(HomeworkInfo homeworkInfo:homeworkInfoList){
            courseNameField.getItems().add(homeworkInfo.getCourse().getName());
            courseNumField.getItems().add(homeworkInfo.getCourse().getNumber());
        }

    }

    public void onCancel(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void onConfirmation(ActionEvent event) {

    }

    public void courseNameField(javafx.event.ActionEvent actionEvent) {
        courseNumField .getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }

    public void studentName(javafx.event.ActionEvent actionEvent) {
        studentIdField .getSelectionModel().select(studentNameField.getSelectionModel().getSelectedIndex());
    }

    public void studentId(javafx.event.ActionEvent actionEvent) {
        studentNameField.getSelectionModel().select(studentIdField.getSelectionModel().getSelectedIndex());
    }

    public void courseNumField(javafx.event.ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumField.getSelectionModel().getSelectedIndex());
    }

    public void onConfirmation(javafx.event.ActionEvent actionEvent) {
    }
}
