package com.teach.javafx.controller.base;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.AttendanceInfo;
import com.teach.javafx.models.DO.HonorInfo;
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

public class HonorChangeController {

    @FXML
    private TextField studentIdField;
    @FXML
    private TextField studentNameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField honorNameField;
    @FXML
    private DatePicker honorTimePicker;
    @FXML
    private TextField levelField;
    @FXML
    private TextField honorFromField;

    @FXML
    private Button onCancel;
    @FXML
    public Button onConfirmation;

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static HonorInfo honor;

    public void initialize(){
        honor =HonorManageController.getHonorInfoList().get(index);

        studentIdField.setText(honor.getStudent().getStudentId().toString());
        studentNameField.setText(honor.getStudent().getPerson().getName());
        typeField.setText(honor.getType());
        honorNameField.setText(honor.getHonorName());
        typeField.setText(honor.getType());
        honorTimePicker.setValue(LocalDate.parse(honor.getHonorTime()));
        levelField.setText(honor.getLevel());
        honorFromField.setText(honor.getHonorFrom());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setHonorInfo(honor);
        DataRequest req=new DataRequest();
        req.add("honorInfo",honor);
        DataResponse res = HttpRequestUtil.request("/api/honorInfo/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该荣誉已存在！");
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
            HonorManageController.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setHonorInfo(HonorInfo honorInfo) {
        Student s=honorInfo.getStudent();
        Person person=s.getPerson();
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(studentNameField.getText());
        honorInfo.setType(typeField.getText());
        honorInfo.setHonorName(honorNameField.getText());
        honorInfo.setHonorTime(honorTimePicker.getValue()==null ? LocalDate.now().toString() : honorTimePicker.getValue().toString());
        honorInfo.setLevel(levelField.getText());
        honorInfo.setHonorFrom(honorFromField.getText());
    }
}
