package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CourseSelectStudent_Change_Controller {

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
    @Setter
    private static Integer index;

    @Getter
    private static SelectedCourse selectedCourse;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfos = new ArrayList<>();

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
        DataResponse dataResponse = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        selectedCourseInfos= JSON.parseArray(JSON.toJSONString(dataResponse.getData()), SelectedCourseInfo.class);
        for (SelectedCourseInfo selectedCourseInfo : selectedCourseInfos) {
            courseNameField.getItems().add(selectedCourseInfo.getCourse().getName());
            courseNumField.getItems().add(selectedCourseInfo.getCourse().getNumber());
        }
        selectedCourse=CourseSelectStudent_Manage_Controller.getSelectedCourseList().get(getIndex());
        studentIdField.setValue(String.valueOf(selectedCourse.getStudent().getStudentId()));
        studentNameField.setValue(selectedCourse.getStudent().getPerson().getName());
        courseNameField.setValue(selectedCourse.getSelectedCourseInfo().getCourse().getName());
        courseNumField.setValue(selectedCourse.getSelectedCourseInfo().getCourse().getNumber());
    }

    public void onCancel(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent event) {
        if( courseNameField.getSelectionModel().getSelectedIndex()<0) {
            MessageDialog.showDialog("未选择课程");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        if( studentIdField.getSelectionModel().getSelectedIndex()<0) {
            MessageDialog.showDialog("未选择学生");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setSelectedCourse(selectedCourse);

        DataRequest req=new DataRequest();
        req.add("selectedCourse",selectedCourse);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourse/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该学生选课已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            CourseSelectStudent_Manage_Controller.updateDataTableView();
        }
    }

    private void setSelectedCourse(SelectedCourse selectedCourse) {
        selectedCourse.setSelectedCourseInfo(selectedCourseInfos.get(courseNameField.getSelectionModel().getSelectedIndex()));
        selectedCourse.setStudent(students.get(studentIdField.getSelectionModel().getSelectedIndex()));
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

}
