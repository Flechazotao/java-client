package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;

public class InnovativePracticePeople_Addition_Controller {

    @FXML
    public Button onConfirmation;
    @FXML
    public Button onCancel;
    @FXML
    public ComboBox<String> studentId;
    @FXML
    public ComboBox<String> studentName;

    @Setter
    public static List<Student> addedStudents;

    public List<Student> students;

    public void initialize(){
        studentId.getItems().clear();
        studentName.getItems().clear();
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        students= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        studentId.getItems().add("请选择学号");
        studentId.getSelectionModel().select(0);
        studentName.getItems().add("请选择学生");
        studentName.getSelectionModel().select(0);
        for(Student student:students){
            studentId.getItems().add(student.getStudentId().toString());
            studentName.getItems().add(student.getPerson().getName());
        }
    }

    public void studentId(ActionEvent actionEvent) {
        studentName.getSelectionModel().select(studentId.getSelectionModel().getSelectedIndex());
    }

    public void studentName(ActionEvent actionEvent) {
        studentId.getSelectionModel().select(studentName.getSelectionModel().getSelectedIndex());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        addedStudents.add(students.get(studentId.getSelectionModel().getSelectedIndex()-1));
        ((Stage)onCancel.getScene().getWindow()).close();
    }

    public void onCancel(ActionEvent actionEvent) {
        ((Stage)onCancel.getScene().getWindow()).close();
    }
}
