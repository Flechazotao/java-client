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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Score_Change_Controller {
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

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static Score score;

    public void initialize(){
        score = Score_Manage_Controller.getScoreList().get(index);

        courseNumberField.setText(score.getCourse().getNumber());
        courseNameField.setText(score.getCourse().getName());
        creditField.setText(score.getCourse().getCredit());
        studentNameField.setText(score.getStudent().getPerson().getName());
        studentNumberField.setText(String.valueOf(score.getStudent().getStudentId()));
        markField.setText(String.valueOf(score.getMark()));
        markPointField.setText(String.valueOf(score.getMark()/10-5));
        rankingField.setText(String.valueOf(score.getRanking()));
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentNumberField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
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
        s.setStudentId(Long.valueOf(studentNumberField.getText()));
        person.setNumber(Long.valueOf(studentNumberField.getText()));
        person.setName(studentNameField.getText());
        Course c=score.getCourse();
        c.setName(courseNameField.getText());
        c.setCredit(creditField.getText());
        c.setNumber(courseNumberField.getText());
        score.setMark(Double.valueOf(markField.getText()));
        score.setRanking(Integer.valueOf(rankingField.getText()));
    }
}