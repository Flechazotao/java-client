package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.Homework;
import com.teach.javafx.models.DO.HomeworkInfo;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Homework_Change_Controller {
    @FXML
    public TextField homeworkInfoIdField;
    @FXML
    public TextField courseNameField;
    @FXML
    public TextField homeworkNameField;
    @FXML
    public TextField studentNameField;
    @FXML
    public TextField studentIdField;
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

    public void initialize(){
        homework = Homework_Manage_Controller.getHomeworkList().get(index);

        homeworkInfoIdField.setText(String.valueOf(homework.getHomeworkInfo().getHomeworkInfoId()));
        courseNameField.setText(String.valueOf(homework.getHomeworkInfo().getCourse().getName()));
        homeworkNameField.setText(homework.getHomeworkInfo().getName());
        studentNameField.setText(String.valueOf(homework.getStudent().getPerson().getName()));
        submitStatusField.setText(String.valueOf(homework.getIsSubmit()));
        studentIdField.setText(String.valueOf(homework.getStudent().getStudentId()));
        homeworkScoreField.setText(String.valueOf(homework.getHomeworkScore()));
        checkTimeField.setValue(LocalDate.parse(homework.getCheckTime()));
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }else if(homeworkInfoIdField.getText().isEmpty()){
            MessageDialog.showDialog("作业信息编号为空，不能添加");
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
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentNameField.getText());
        HomeworkInfo homeworkInfo=homework.getHomeworkInfo();
        homeworkInfo.setHomeworkInfoId(Integer.valueOf(homeworkInfoIdField.getText()));
        homework.setIsSubmit(submitStatusField.getText());
        homework.setHomeworkScore(homeworkScoreField.getText());
        homework.setSubmitTime(checkTimeField.getValue()==null ? LocalDate.now().toString() : checkTimeField.getValue().toString());
    }
}
