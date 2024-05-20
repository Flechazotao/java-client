package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HomeworkInfoView;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Homework_Change_Controller {
    @FXML
    public ComboBox<String> courseNumField;
    @FXML
    public ComboBox<String> courseNameField;
    @FXML
    public ComboBox<String> homeworkNameField;
    @FXML
    public ComboBox<String> studentNameField;
    @FXML
    public ComboBox<String> studentIdField;
    @FXML
    public TextField submitStatusField;
    @FXML
    public TextField homeworkScoreField;
    @FXML
    public DatePicker checkTimeField;

    @FXML
    public Button onCancel;
    @FXML
    public Button onConfirmation;

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static Homework homework;

    public List<Student> students;

    @Getter
    private static List<HomeworkInfo> homeworkInfoList = new ArrayList<>();

    public void initialize(){
        homework = Homework_Manage_Controller.getHomeworkList().get(index);

        courseNumField.setValue(String.valueOf(homework.getHomeworkInfo().getCourse().getNumber()));
        courseNameField.setValue(String.valueOf(homework.getHomeworkInfo().getCourse().getName()));
        homeworkNameField.setValue(homework.getHomeworkInfo().getName());
        studentNameField.setValue(String.valueOf(homework.getStudent().getPerson().getName()));
        submitStatusField.setText(String.valueOf(homework.getIsSubmit()));
        studentIdField.setValue(String.valueOf(homework.getStudent().getStudentId()));
        homeworkScoreField.setText(String.valueOf(homework.getHomeworkScore()));
        checkTimeField.setValue(LocalDate.parse(homework.getSubmitTime()));

        //学生有关信息下拉框
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        students= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        studentIdField.getItems().add("请选择学号");
        studentNameField.getItems().add("请选择学生");
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
    public void studentId(ActionEvent actionEvent) {
        studentNameField.getSelectionModel().select(studentIdField.getSelectionModel().getSelectedIndex());
    }

    public void studentName(ActionEvent actionEvent) {
        studentIdField.getSelectionModel().select(studentNameField.getSelectionModel().getSelectedIndex());
    }
    public void courseNameField(ActionEvent actionEvent) {
        courseNumField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
        homeworkNameField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }
    public void courseNumField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumField.getSelectionModel().getSelectedIndex());
        homeworkNameField.getSelectionModel().select(courseNumField.getSelectionModel().getSelectedIndex());
    }


    public void homeworkNameField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(homeworkNameField.getSelectionModel().getSelectedIndex());
        courseNumField.getSelectionModel().select(homeworkNameField.getSelectionModel().getSelectedIndex());
    }


    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getValue().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setHomework(homework);
        DataRequest req=new DataRequest();
        req.add("homework",homework);
        DataResponse res = HttpRequestUtil.request("/api/homework/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该作业已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("学号或作业信息编号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            Homework_Manage_Controller.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setHomework(Homework homework) {
        Student s=homework.getStudent();
        Person person=s.getPerson();
        s.setStudentId(Long.valueOf(studentIdField.getValue()));
        person.setNumber(Long.valueOf(studentIdField.getValue()));
        person.setName(studentNameField.getValue());
        HomeworkInfo homeworkInfo=homework.getHomeworkInfo();
        homeworkInfo.setHomeworkInfoId(homework.getHomeworkInfo().getHomeworkInfoId());
        homework.setIsSubmit(submitStatusField.getText());
        homework.setHomeworkScore(homeworkScoreField.getText());
        homework.setSubmitTime(checkTimeField.getValue()==null ? LocalDate.now().toString() : checkTimeField.getValue().toString());
    }



}
