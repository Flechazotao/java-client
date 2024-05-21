package com.teach.javafx.controller.AdminController;

import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DailyActivity_Addition_Controller {

    @FXML
    private TextField activityNameField;
    @FXML
    private DatePicker endTime;
    @FXML
    private DatePicker beginTime;
    @FXML
    private Button onCancel;
    @FXML
    private TextField locationField;
    @FXML
    private ComboBox<String> activityTypeField;

    public static String[]typelists={"聚会","旅游","文艺演出","体育活动"};

    public static List<Student> addedStudents;

    public void initialize(){
        addedStudents=new ArrayList<>();
        //添加活动类型下拉框
        for (String s:typelists)
            activityTypeField.getItems().add(s);
    }

    public static String getStudentName(){
        String names="";
        for(Student student:addedStudents){
            names=names+student.getPerson().getName()+" ";
        }
        return names;
    }

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
        req.add("students",addedStudents);
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
            DailyActivity_Manage_Controller.updateDataTableView();
        }
    }

    private DailyActivity getDailyActivity() {
        DailyActivity daily=new DailyActivity();
        daily.setActivityName(activityNameField.getText());
        daily.setActivityType(activityTypeField.getValue());
        daily.setBeginTime(beginTime.getValue()==null ? LocalDate.now().toString() : beginTime.getValue().toString());
        daily.setEndTime(endTime.getValue()==null ? LocalDate.now().toString() : endTime.getValue().toString());
        daily.setStudentName(getStudentName());
        daily.setLocation(locationField.getText());
        return daily;
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
