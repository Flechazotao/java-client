package com.teach.javafx.controller.AdminController;

import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;

public class DailyActivity_Change_Controller {

    @FXML
    private TextField activityNameField;

    @FXML
    private DatePicker endTime;

    @FXML
    private Button onCancel;

    @FXML
    private Button onChange;

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
        dailyActivity = DailyActivity_Manage_Controller.getDailyActivityList().get(index);

        activityNameField.setText(dailyActivity.getActivityName());
        activityTypeField.setText(dailyActivity.getActivityType());
        beginTime.setValue(LocalDate.parse(dailyActivity.getBeginTime()));
        endTime.setValue(LocalDate.parse(dailyActivity.getEndTime()));
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
            DailyActivity_Manage_Controller.updateDataTableView();
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

    public void onChange(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivityPeople_Change.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("修改参与人员");
        stage.show();
    }
}
