package com.teach.javafx.controller.base;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.DailyActivity;
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

public class DailyActivityAddition_Controller {
    @FXML
    private TextField activityNameField;

    @FXML
    private DatePicker endTime;

    @FXML
    private Button onCancel;

    @FXML
    private TextField studentField;

    @FXML
    private TextField locationField;

    @FXML
    private DatePicker beginTime;

    @FXML
    private TextField activityTypeField;

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(activityNameField.getText().equals("")) {
            MessageDialog.showDialog("项目名为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        DailyActivity daily=getDailyActivity();
        DataRequest req=new DataRequest();
        req.add("dailyActivity",daily);
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该项目已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            DailyActivityController.updateDataTableView();
        }
    }

    private DailyActivity getDailyActivity() {
        DailyActivity daily=new DailyActivity();
        daily.setActivityName(activityNameField.getText());
        daily.setActivityType(activityTypeField.getText());
        daily.setBeginTime(beginTime.getValue()==null ? LocalDate.now().toString() : beginTime.getValue().toString());
        daily.setEndTime(endTime.getValue()==null ? LocalDate.now().toString() : endTime.getValue().toString());
        daily.setStudent(null);
        daily.setLocation(locationField.getText());
        return daily;
    }

}
