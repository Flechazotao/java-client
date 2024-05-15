package com.teach.javafx.controller.AdminController;

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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Attendance_Change_Controller {

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

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static AttendanceInfo attendance;

    public void initialize(){
        attendance = Attendance_Manage_Controller.getAttendanceInfoList().get(index);

        activityNameField.setText(attendance.getActivityName());
        typeField.setText(attendance.getType());
        studentField.setText(attendance.getStudent().getPerson().getName());
        studentIdField.setText(attendance.getStudent().getStudentId().toString());
        timePicker.setValue(LocalDate.parse(attendance.getAttendanceTime()));
        isAttendedField.setText(attendance.getIsAttended());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setAttendanceInfo(attendance);
        DataRequest req=new DataRequest();
        req.add("attendanceInfo",attendance);
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
            Attendance_Manage_Controller.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setAttendanceInfo(AttendanceInfo attendanceInfo) {
        Student s=attendanceInfo.getStudent();
        Person person=s.getPerson();
        attendanceInfo.setActivityName(activityNameField.getText());
        attendanceInfo.setType(typeField.getText());
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentField.getText());
        attendanceInfo.setIsAttended(isAttendedField.getText().isEmpty()?"否":isAttendedField.getText());
        attendanceInfo.setAttendanceTime(timePicker.getValue()==null ? LocalDate.now().toString() : timePicker.getValue().toString());
    }
}
