package com.teach.javafx.controller.base;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.AttendanceInfo;
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

import java.time.LocalDate;

public class AttendanceAdditionController {
    @FXML
    public TextField activityNameField;
    @FXML
    public TextField typeField;
    @FXML
    public TextField studentField;
    @FXML
    public TextField studentIdField;
    @FXML
    public TextField isAttendedField;
    @FXML
    public DatePicker timePicker;
    @FXML
    public Button onCancel;

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        AttendanceInfo attendanceInfo=getAttendanceInfo();
        DataRequest req=new DataRequest();
        req.add("attendanceInfo",attendanceInfo);
        DataResponse res = HttpRequestUtil.request("/api/attendance/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该考勤已存在！");
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
            AttendanceController.updateDataTableView();
        }
    }

    private AttendanceInfo getAttendanceInfo() {
        AttendanceInfo attendanceInfo=new AttendanceInfo();
        Student s=new Student();
        Person person=new Person();
        attendanceInfo.setActivityName(activityNameField.getText());
        attendanceInfo.setType(typeField.getText());
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentField.getText());
        s.setPerson(person);
        attendanceInfo.setStudent(s);
        attendanceInfo.setIsAttended(isAttendedField.getText().isEmpty()?"否":isAttendedField.getText());
        attendanceInfo.setAttendanceTime(timePicker.getValue()==null ? LocalDate.now().toString() : timePicker.getValue().toString());
        return attendanceInfo;
    }
}
