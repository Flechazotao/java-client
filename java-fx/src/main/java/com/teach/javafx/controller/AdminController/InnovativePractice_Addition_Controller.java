package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DO.Teacher;
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

public class InnovativePractice_Addition_Controller {

    @FXML
    private TextField activityNameField;
    @FXML
    private ComboBox<String> teacherField;
    @FXML
    private ComboBox<String> achievementField;
    @FXML
    private DatePicker beginTime;
    @FXML
    private DatePicker endTime;

    @FXML
    private ComboBox<String> typeField;
    @FXML
    private Button onCancel;

    public static List<Student> addedStudents;
    private static List<Teacher> teacherList = new ArrayList<>();
    public static String[]typelist={"社会实践","学科竞赛","科技成果","培训讲座","创新项目","校外实习","志愿服务"};
    public static String[]achievementlist={"特等奖","一等奖","二等奖","三等奖","优秀奖","金奖","银奖","铜奖","参与奖","校级优秀","院级优秀","专利奖"};


    public void initialize(){
        addedStudents=new ArrayList<>();

        //添加类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }
        typeField.setVisibleRowCount(5);

        //添加类型下拉框
        for(String s:achievementlist){
            achievementField.getItems().add(s);
        }
        achievementField.setVisibleRowCount(5);
        //添加教师下拉框
        DataResponse res = HttpRequestUtil.request("/api/teacher/getTeacherList",new DataRequest());
        teacherList= JSON.parseArray(JSON.toJSONString(res.getData()), Teacher.class);
        for(Teacher teacher:teacherList){
            teacherField.getItems().add(teacher.getPerson().getName());
        }
        teacherField.setVisibleRowCount(5);
    }

    public static String getStudentName(){
        String names="";
        for(Student student:addedStudents){
            names=names+student.getPerson().getName()+" ";
        }
        return names;
    }

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
        req.add("students",addedStudents);
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
        ip.setType(typeField.getValue());
        ip.setBeginTime(beginTime.getValue()==null ? LocalDate.now().toString() : beginTime.getValue().toString());
        ip.setEndTime(endTime.getValue()==null ? LocalDate.now().toString() : endTime.getValue().toString());
        ip.setTeacherName(teacherField.getValue());
        ip.setStudentName(getStudentName());
        ip.setAchievement(achievementField.getValue());
        ip.setFile("");
        return ip;
    }

    public void onAdd(ActionEvent actionEvent) {
        InnovativePracticePeople_Addition_Controller.setAddedStudents(addedStudents);
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePracticePeople_Addition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加创新实践参与人员");
        stage.show();
    }
}
