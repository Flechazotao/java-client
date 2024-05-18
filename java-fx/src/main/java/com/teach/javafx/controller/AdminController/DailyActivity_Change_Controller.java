package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DO.DailyActivityStudent;
import com.teach.javafx.models.DO.Student;
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
import java.util.ArrayList;
import java.util.List;

public class DailyActivity_Change_Controller {

    @FXML
    private TextField activityNameField;
    @FXML
    private DatePicker beginTime;
    @FXML
    private DatePicker endTime;
    @FXML
    private TextField locationField;
    @FXML
    private TextField activityTypeField;

    @FXML
    private Button onAdd;
    @FXML
    private Button onDelete;
    @FXML
    private Button onCancel;

    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static DailyActivity dailyActivity;

    @Getter
    @Setter
    private static List<Student> addedStudents;

    @Getter
    @Setter
    private static List<Student> deleteStudents;

    private List<DailyActivityStudent> dailyActivityStudents;

    public static String getStudentName(){
        String names="";
        for(Student student:addedStudents){
            names=names+student.getPerson().getName()+" ";
        }
        return names;
    }

    public void initialize(){
        dailyActivity = DailyActivity_Manage_Controller.getDailyActivityList().get(index);

        activityNameField.setText(dailyActivity.getActivityName());
        activityTypeField.setText(dailyActivity.getActivityType());
        beginTime.setValue(LocalDate.parse(dailyActivity.getBeginTime()));
        endTime.setValue(LocalDate.parse(dailyActivity.getEndTime()));
        locationField.setText(dailyActivity.getLocation());

        addedStudents=new ArrayList<>();
        deleteStudents=new ArrayList<>();
        DataRequest req=new DataRequest();
        req.add("id",dailyActivity.getActivityId());
        DataResponse res=HttpRequestUtil.request("/api/dailyActivityStudent/findByDailyActivity",req);
        dailyActivityStudents= JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivityStudent.class);
        for(DailyActivityStudent dailyActivityStudent:dailyActivityStudents){
            addedStudents.add(dailyActivityStudent.getStudent());
        }
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( activityNameField.getText()==null) {
            MessageDialog.showDialog("项目名称不能为空");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        DailyActivity daily=dailyActivity;
        setDailyActivity(daily);

        DataRequest req=new DataRequest();
        req.add("id",daily.getActivityId());
        req.add("students",deleteStudents);
        DataResponse res = HttpRequestUtil.request("/api/dailyActivityStudent/deleteByIdAndStudents",req);
        if(res.getCode()!=200){
            MessageDialog.showDialog("信息不完整！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }

        req=new DataRequest();
        req.add("dailyActivity",daily);
        req.add("students",addedStudents);
        res = HttpRequestUtil.request("/api/dailyActivity/add",req);
        if(res.getCode()==401){
            MessageDialog.showDialog("信息不完整！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else if(res.getCode()==200){
            if(res.getData() instanceof String){
                MessageDialog.showDialog((String) res.getData());
            }
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
        daily.setStudentName(getStudentName());
        daily.setLocation(locationField.getText());
    }

    public void onDelete(ActionEvent actionEvent) {
        DailyActivityPeople_Change_Controller.setStudents(addedStudents);
        DailyActivityPeople_Change_Controller.setAddedStudents(addedStudents);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivityPeople_Delete.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("删除参与人员");
        stage.show();
    }

    public void onAdd(ActionEvent actionEvent) {
        DailyActivityPeople_Addition_Controller.setAddedStudents(addedStudents);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivityPeople_Addition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加参与人员");
        stage.show();
    }

}
