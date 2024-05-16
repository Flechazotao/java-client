package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Score_Addition_Controller {
    @FXML
    public TextField courseNumberField;
    @FXML
    public TextField courseNameField;
    @FXML
    public TextField creditField;
    @FXML
    public TextField studentNameField;
    @FXML
    public TextField studentNumberField;
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

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(studentNumberField.getText().equals("")) {
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
        s.setStudentId(Long.valueOf(studentNumberField.getText()));
        person.setNumber(Long.valueOf(studentNumberField.getText()));
        person.setName(studentNameField.getText());
        s.setPerson(person);
        score.setStudent(s);
        Course c=new Course();
        c.setName(courseNameField.getText());
        c.setCredit(creditField.getText().isEmpty()?0.0:Double.parseDouble(creditField.getText()));
        c.setNumber(courseNumberField.getText());
        score.setCourse(c);
        score.setMark(markField.getText().isEmpty()?0.0:Double.parseDouble(markField.getText()));
        score.setRanking(rankingField.getText().isEmpty()?0:Integer.parseInt(rankingField.getText()));
        return score;
    }
}
