package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Course_Addition_Controller {
    @FXML
    public TextField courseNumberField;
    @FXML
    public TextField courseNameField;
    @FXML
    public TextField creditField;
    @FXML
    public ComboBox<String> teacherNameField;
    @FXML
    public TextField courseWeekField;
    @FXML
    public ComboBox<String> courseTimeField;
    @FXML
    public ComboBox<String> wayOfTestField;
    @FXML
    public TextField locationField;
    @FXML
    public ComboBox<String> typeField;
    @FXML
    public Button onCancel;
    @FXML
    public Button onConfirmation;

    @FXML
    public Button onSelectFile;
    private File file;

    private static List<Teacher> teacherList = new ArrayList<>();

    //课程时间
    private static String[] timeList = {"周一第一大节", "周一第二大节", "周一第三大节", "周一第四大节", "周一第五大节","周二第一大节", "周二第二大节", "周二第三大节", "周二第四大节", "周二第五大节","周二第一大节", "周二第二大节", "周二第三大节", "周二第四大节", "周二第五大节","周三第一大节", "周三第二大节", "周三第三大节", "周三第四大节", "周三第五大节","周四第一大节", "周四第二大节", "周四第三大节", "周四第四大节", "周四第五大节","周五第一大节", "周五第二大节", "周五第三大节", "周五第四大节", "周五第五大节"};

    //    "必修", "选修", "通选", "限选", "任选"
    private static String[] typelist = {"必修", "选修", "通选", "限选", "任选"};
    //        考试、无、项目答辩、提交报告、其它
    private static String[] wayOfTestlist= {"无", "考试", "项目答辩", "提交报告", "其它"};

    public void initialize(){
        //展示授课教师下拉框
        DataResponse res = HttpRequestUtil.request("/api/teacher/getTeacherList",new DataRequest());
        teacherList= JSON.parseArray(JSON.toJSONString(res.getData()), Teacher.class);
        for(Teacher teacher:teacherList){
            teacherNameField.getItems().add(teacher.getPerson().getName());
        }
        //展示课程类型下拉框

        for(String s:typelist){
            typeField.getItems().add(s);
        }

        //展示考核方式下拉框
        for (String s:wayOfTestlist){
            wayOfTestField.getItems().add(s);
        }

        //展示课程时间下拉框
        for (String s:timeList){
            courseTimeField.getItems().add(s);
        }
    }
    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(courseNumberField.getText().equals("")) {
            MessageDialog.showDialog("课程号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Course course=getCourse();
        DataResponse res1=HttpRequestUtil.uploadFile("/api/file/upload", Paths.get(file.getPath()),"Course"+"\\");
        if(res1.getCode()==200){
            MessageDialog.showDialog("文件上传成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("文件上传失败！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        String url=res1.getMessage().substring(8);
        course.setFile(url);


        DataRequest req=new DataRequest();
        req.add("course",course);
        DataResponse res = HttpRequestUtil.request("/api/course/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该课程已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            CourseManageController.updateDataTableView();
        }
    }

    private Course getCourse() {
        Course course=new Course();
        course.setNumber(courseNumberField.getText());
        course.setName(courseNameField.getText());
        course.setCredit(creditField.getText().isEmpty()?0.0: Double.parseDouble(creditField.getText()));
        course.setTeacherName(teacherNameField.getValue());
        course.setCourseBeginWeek(courseWeekField.getText());
        course.setCourseTime(courseTimeField.getValue());
        course.setWayOfTest(wayOfTestField.getValue());
        course.setLocation(locationField.getText());
        course.setType(typeField.getValue());
        return course;
    }

    public void onSelectFile(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        file = fileChooser.showOpenDialog(onSelectFile.getScene().getWindow());
    }
}
