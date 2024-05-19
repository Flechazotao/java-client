package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Score_Change_Controller {
    @FXML
    public ComboBox<String> courseNumberField;
    @FXML
    public ComboBox<String> courseNameField;

    @FXML
    public ComboBox<String> studentNameField;
    @FXML
    public ComboBox<String> studentNumberField;
    @FXML
    public TextField markField;
    @FXML
    public TextField markPointField;
    @FXML
    public TextField rankingField;

    @FXML
    public Button onConfirmation;
    @FXML
    public Button onCancel;

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static Score score;
    public List<Student> students;

    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

    public void initialize(){
        score = Score_Manage_Controller.getScoreList().get(index);

        courseNumberField.setValue(score.getCourse().getNumber());
        courseNameField.setValue(score.getCourse().getName());
        studentNameField.setValue(score.getStudent().getPerson().getName());
        studentNumberField.setValue(String.valueOf(score.getStudent().getStudentId()));
        markField.setText(String.valueOf(score.getMark()));
        markPointField.setText(String.valueOf(score.getMark()/10-5));
        rankingField.setText(String.valueOf(score.getRanking()));

        //学生有关信息下拉框
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        students= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        studentNumberField.getItems().add("请选择学号");
        studentNameField.getItems().add("请选择学生");
        for(Student student:students){
            studentNumberField.getItems().add(student.getStudentId().toString());
            studentNameField.getItems().add(student.getPerson().getName());
        }

        //课程有关信息下拉框
        DataResponse res1 = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        selectedCourseInfoList= JSON.parseArray(JSON.toJSONString(res1.getData()), SelectedCourseInfo.class);
        for(SelectedCourseInfo selectedCourseInfo:selectedCourseInfoList){
            courseNameField.getItems().add(selectedCourseInfo.getCourse().getName());
            courseNumberField.getItems().add(selectedCourseInfo.getCourse().getNumber());
        }

    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentNumberField.getValue().equals("")) {
            MessageDialog.showDialog("学号为空，不能修改");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setScore(score);
        DataRequest req=new DataRequest();
        req.add("score",score);
        DataResponse res = HttpRequestUtil.request("/api/score/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该成绩已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("学号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            Score_Manage_Controller.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setScore(Score score) {
        Student s=score.getStudent();
        Person person=s.getPerson();
        s.setStudentId(Long.valueOf(studentNumberField.getValue()));
        person.setNumber(Long.valueOf(studentNumberField.getValue()));
        person.setName(studentNameField.getValue());
        Course c=score.getCourse();
        c.setName(courseNameField.getValue());
        c.setNumber(courseNumberField.getValue());
        score.setMark(Double.valueOf(markField.getText()));
        score.setRanking(Integer.valueOf(rankingField.getText()));
    }

    public void courseNumberField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumberField.getSelectionModel().getSelectedIndex());
    }

    public void courseNameField(ActionEvent actionEvent) {
        courseNumberField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }

    public void studentNameField(ActionEvent actionEvent) {
        studentNumberField.getSelectionModel().select(studentNameField.getSelectionModel().getSelectedIndex());
    }

    public void studentNumberField(ActionEvent actionEvent) {
        studentNameField.getSelectionModel().select(studentNumberField.getSelectionModel().getSelectedIndex());

    }

    public void markField(ActionEvent actionEvent) {
        markPointField.setText(String.valueOf(Double.parseDouble(markField.getText())/10-5));
    }

    public void markPointField(ActionEvent actionEvent) {
        markField.setText(String.valueOf((Double.parseDouble(markPointField.getText())+5)*10));
    }
}