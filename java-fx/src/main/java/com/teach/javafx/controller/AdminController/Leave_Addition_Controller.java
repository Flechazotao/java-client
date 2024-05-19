package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.LeaveInfo;
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

public class Leave_Addition_Controller {
    @FXML
    public TextField isBackSchool;
    @FXML
    public TextField studentNameField;
    @FXML
    public TextField studentIdField;
    @FXML
    public TextField approverField;
    @FXML
    public DatePicker leaveTimePicker;
    @FXML
    public DatePicker beginTimePicker;
    @FXML
    public DatePicker endTimePicker;
    @FXML
    public Button onCancel;
    @FXML
    public Button onConfirmation;

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
        LeaveInfo leaveInfo=getLeaveInfo();
        DataRequest req=new DataRequest();
        req.add("leaveInfo",leaveInfo);
        DataResponse res = HttpRequestUtil.request("/api/leaveInfo/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该请假已存在！");
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
            LeaveInfo_Manage_Controller.updateDataTableView();
        }
    }

    private LeaveInfo getLeaveInfo() {
        LeaveInfo leaveInfo=new LeaveInfo();
        Student s=new Student();
        Person person=new Person();
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentNameField.getText());
        s.setPerson(person);
        leaveInfo.setStudent(s);
        leaveInfo.setLeaveTime(leaveTimePicker.getValue()==null ? LocalDate.now().toString() : leaveTimePicker.getValue().toString());
        leaveInfo.setLeaveBeginTime(beginTimePicker.getValue()==null ? LocalDate.now().toString() : beginTimePicker.getValue().toString());
        leaveInfo.setLeaveEndTime(endTimePicker.getValue()==null ? LocalDate.now().toString() : endTimePicker.getValue().toString());
        leaveInfo.setLeaveReason("没写嘞");
        leaveInfo.setApprover(approverField.getText());
        leaveInfo.setIsBackSchool(isBackSchool.getText());
        return leaveInfo;
    }
}
