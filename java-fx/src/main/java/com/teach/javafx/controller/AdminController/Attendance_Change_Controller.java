package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.TeacherController.Attendance_panel_Controller;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

public class Attendance_Change_Controller {
    @FXML
    public TextField activityNameField;
    @FXML
    public ComboBox<String> typeField;
    @FXML
    public ComboBox<String> studentField;
    @FXML
    public ComboBox<String> studentIdField;
    @FXML
    public ComboBox<String> isAttendedField;
    @FXML
    public DatePicker timePicker;
    @FXML
    public Button onCancel;
    public List<Student> students;
    // "上课考勤","会议考勤","活动考勤"
    public static String[]typelist={"上课考勤","会议考勤","活动考勤"};

    public static String[]isAttendedlist={"是","否"};

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static AttendanceInfo attendance;

    public void initialize(){
        attendance = Attendance_Manage_Controller.getAttendanceInfoList().get(index);

        activityNameField.setText(attendance.getActivityName());
        typeField.setValue(attendance.getType());
        studentField.setValue(attendance.getStudent().getPerson().getName());
        studentIdField.setValue(attendance.getStudent().getStudentId().toString());
        timePicker.setValue(LocalDate.parse(attendance.getAttendanceTime()));
        isAttendedField.setValue(attendance.getIsAttended());

        //学生有关信息下拉框
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        students= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        studentIdField.getItems().add("请选择学号");
        studentField.getItems().add("请选择学生");
        for(Student student:students){
            studentIdField.getItems().add(student.getStudentId().toString());
            studentField.getItems().add(student.getPerson().getName());
        }

        //展示类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }

        //展示是否考勤下拉框
        for(String s:isAttendedlist){
            isAttendedField.getItems().add(s);
        }
    }

    public void studentIdField(ActionEvent actionEvent) {
        studentField.getSelectionModel().select(studentIdField.getSelectionModel().getSelectedIndex());
    }

    public void studentField(ActionEvent actionEvent) {
        studentIdField.getSelectionModel().select(studentField.getSelectionModel().getSelectedIndex());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getValue().equals("")) {
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
            Attendance_panel_Controller.updateDataTableView();
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
        attendanceInfo.setType(typeField.getValue());
        s.setStudentId(Long.valueOf(studentIdField.getValue()));
        person.setNumber(Long.valueOf(studentIdField.getValue()));
        person.setName(studentField.getValue());
        attendanceInfo.setIsAttended(isAttendedField.getValue().isEmpty()?"否":isAttendedField.getValue());
        attendanceInfo.setAttendanceTime(timePicker.getValue()==null ? LocalDate.now().toString() : timePicker.getValue().toString());
    }
}
