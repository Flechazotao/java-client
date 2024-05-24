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

import java.util.ArrayList;
import java.util.List;

public class CourseSelectStudent_Addition_Controller {

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
        

    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent event) {
        SelectedCourse selectedCourse=new SelectedCourse();
        selectedCourse.setSelectedCourseInfo(selectedCourseInfos.get(courseNameField.getSelectionModel().getSelectedIndex()));
        selectedCourse.setStudent(students.get(studentIdField.getSelectionModel().getSelectedIndex()));
        DataRequest req=new DataRequest();
        req.add("selectedCourse",selectedCourse);
        DataResponse res= HttpRequestUtil.request("/api/selectedCourse/add",req);
        if (res.getCode()==401){
            MessageDialog.showDialog("信息不完整!");
        }
        else {
            MessageDialog.showDialog("添加成功!!!");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        CourseSelectStudent_Manage_Controller.updateDataTableView();
    }

    public void courseNameField(ActionEvent actionEvent) {
        courseNumField .getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }

    public void studentName(ActionEvent actionEvent) {
        studentIdField .getSelectionModel().select(studentNameField.getSelectionModel().getSelectedIndex());
    }

    public void studentId(ActionEvent actionEvent) {
        studentNameField.getSelectionModel().select(studentIdField.getSelectionModel().getSelectedIndex());
    }

    public void courseNumField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumField.getSelectionModel().getSelectedIndex());
    }


}
