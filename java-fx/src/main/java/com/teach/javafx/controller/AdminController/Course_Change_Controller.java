package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Course_Change_Controller {
    @FXML
    public TextField courseNumberField;
    @FXML
    public TextField courseNameField;
    @FXML
    public TextField creditField;
    @FXML
    public TextField teacherNameField;
    @FXML
    public TextField courseWeekField;
    @FXML
    public TextField courseTimeField;
    @FXML
    public TextField wayOfTestField;
    @FXML
    public TextField locationField;
    @FXML
    public TextField typeField;
    @FXML
    public Button onCancel;
    @FXML
    public Button onConfirmation;

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static Course course;

    public void initialize(){
        course = CourseManageController.getCourseList().get(index);
        courseNumberField.setText(course.getNumber());
        courseNameField.setText(course.getName());
        creditField.setText(course.getCredit());
        teacherNameField.setText(course.getTeacherName());
        courseWeekField.setText(course.getCourseBeginWeek());
        courseTimeField.setText(course.getCourseTime());
        wayOfTestField.setText(course.getWayOfTest());
        locationField.setText(course.getLocation());
        typeField.setText(course.getType());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( courseNumberField.getText().equals("")) {
            MessageDialog.showDialog("课程号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setCourse(course);
        DataRequest req=new DataRequest();
        req.add("course",course);
        DataResponse res = HttpRequestUtil.request("/api/course/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该课程已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            CourseManageController.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setCourse(Course course) {
        course.setNumber(courseNumberField.getText());
        course.setName(courseNameField.getText());
        course.setCredit(creditField.getText());
        course.setTeacherName(teacherNameField.getText());
        course.setCourseBeginWeek(courseWeekField.getText());
        course.setCourseTime(courseTimeField.getText());
        course.setWayOfTest(wayOfTestField.getText());
        course.setLocation(locationField.getText());
        course.setType(typeField.getText());
    }
}