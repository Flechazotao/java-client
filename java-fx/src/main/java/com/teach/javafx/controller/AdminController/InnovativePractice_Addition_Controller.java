package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.InnovativePractice;
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

public class InnovativePractice_Addition_Controller {

    @FXML
    private TextField activityNameField;
    @FXML
    private TextField teacherField;
    @FXML
    private TextField achievementField;
    @FXML
    private DatePicker beginTime;
    @FXML
    private DatePicker endTime;
    @FXML
    private TextField studentField;
    @FXML
    private TextField typeField;
    @FXML
    private Button onCancel;

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    public void onConformation(ActionEvent actionEvent) {
        if( activityNameField.getText().equals("")) {
            MessageDialog.showDialog("项目名为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        InnovativePractice ip=getInnovativePractice();
        DataRequest req=new DataRequest();
        req.add("innovativePractice",ip);
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该项目已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            InnovativePractice_Manage_Controller.updateDataTableView();
        }
    }

    private InnovativePractice getInnovativePractice() {
        InnovativePractice ip=new InnovativePractice();
        ip.setActivityName(activityNameField.getText());
        ip.setType(typeField.getText());
        ip.setBeginTime(beginTime.getValue()==null ? LocalDate.now().toString() : beginTime.getValue().toString());
        ip.setEndTime(endTime.getValue()==null ? LocalDate.now().toString() : endTime.getValue().toString());
        ip.setTeacherName(teacherField.getText());
        ip.setStudent(null);
        ip.setAchievement(achievementField.getText());
        ip.setFile("");
        return ip;
    }

}
