package com.teach.javafx.controller.base;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.DailyActivity;
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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class DailyActivityChange_Controller {

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

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static DailyActivity dailyActivity;

    public void initialize(){
        dailyActivity =DailyActivityController.getDailyActivityList().get(index);

        activityNameField.setText(dailyActivity.getActivityName());
        activityTypeField.setText(dailyActivity.getActivityType());
        beginTime.setValue(LocalDate.parse(dailyActivity.getBeginTime()));
        endTime.setValue(LocalDate.parse(dailyActivity.getEndTime()));
        studentField.setText("");
        locationField.setText(dailyActivity.getLocation());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( activityNameField.getText().equals("")) {
            MessageDialog.showDialog("项目名称不能为空");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        DailyActivity daily=dailyActivity;
        setDailyActivity(daily);
        DataRequest req=new DataRequest();
        req.add("dailyActivity",daily);
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/add",req);
        if(res.getCode()==401){
            MessageDialog.showDialog("信息不完整！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else if(res.getCode()==200){
            MessageDialog.showDialog("修改成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            DailyActivityController.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setDailyActivity(DailyActivity daily) {
        daily.setActivityName(activityNameField.getText());
        daily.setActivityType(activityTypeField.getText());
        daily.setBeginTime(beginTime.getValue()==null ? LocalDate.now().toString() : beginTime.getValue().toString());
        daily.setEndTime(endTime.getValue()==null ? LocalDate.now().toString() : endTime.getValue().toString());
        daily.setStudent(null);
        daily.setLocation(locationField.getText());
    }
}
