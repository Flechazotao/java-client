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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class Score_Addition_Controller {
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

    public List<Student> students;

    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

        public void initialize(){
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

//        markPointField.setText(String.valueOf(/10-5));
    }
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(studentNumberField.getValue().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Score score=getScore();
        DataRequest req=new DataRequest();
        req.add("score",score);
        DataResponse res = HttpRequestUtil.request("/api/score/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该成绩已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("学号或课程编号错误");
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

    private Score getScore() {
        Score score=new Score();
        Student s=new Student();
        Person person=new Person();
        s.setStudentId(Long.valueOf(studentNumberField.getValue()));
        person.setNumber(Long.valueOf(studentNumberField.getValue()));
        person.setName(studentNameField.getValue());
        s.setPerson(person);
        score.setStudent(s);
        Course c=new Course();
        c.setName(courseNameField.getValue());
        c.setNumber(courseNumberField.getValue());
        score.setCourse(c);
        score.setMark(markField.getText().isEmpty()?0.0:Double.parseDouble(markField.getText()));
        score.setRanking(rankingField.getText().isEmpty()?0:Integer.parseInt(rankingField.getText()));
        return score;
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
