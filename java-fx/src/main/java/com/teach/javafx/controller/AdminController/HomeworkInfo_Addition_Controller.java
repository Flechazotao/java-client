package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class HomeworkInfo_Addition_Controller extends manage_MainFrame_controller {

    @FXML
    public ComboBox<String> courseNumberField;
    @FXML
    public ComboBox<String> courseNameField;
    @FXML
    public TextField homeworkNameField;
    @FXML
    public TextField fileField;

    @FXML
    public Button onConfirmation;
    @FXML
    public Button onCancel;
    @FXML
    public Button onSelectFile;

    private File file;

    ArrayList<String> courseNameList = new ArrayList<>();
    ArrayList<String> courseNumberList = new ArrayList<>();

    private static List<Course> courseList = new ArrayList<>();
    public void initialize(){
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        courseList= JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);
        courseNumberList.clear();
        courseNameList.clear();
        for (Course course : courseList) {
            courseNameList.add(course.getName());
            courseNumberList.add(course.getNumber());
        }
        courseNameField.setItems(FXCollections.observableArrayList(courseNameList));
        courseNumberField.setItems(FXCollections.observableArrayList(courseNumberList));
        courseNameField.setVisibleRowCount(5);
        courseNumberField.setVisibleRowCount(5);
    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(courseNumberField.getValue().equals("")) {
            MessageDialog.showDialog("课程号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        HomeworkInfo homeworkInfo=getHomeworkInfo();
        DataResponse res1=HttpRequestUtil.uploadFile("/api/file/upload", Paths.get(file.getPath()),"HomeworkInfo"+"\\");
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
        homeworkInfo.setFile(url);

        DataRequest req=new DataRequest();
        req.add("homeworkInfo",homeworkInfo);
        DataResponse res = HttpRequestUtil.request("/api/homeworkInfo/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该选课信息已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("课程号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            HomeworkInfo_Manage_Controller.updateDataTableView();
        }
    }

    private HomeworkInfo getHomeworkInfo() {
        HomeworkInfo homeworkInfo=new HomeworkInfo();
        Course course=new Course();
        homeworkInfo.setName(homeworkNameField.getText());
        course.setNumber(courseNumberField.getValue());
        course.setName(courseNameField.getValue());
        homeworkInfo.setCourse(course);
        return homeworkInfo;
    }

    public void courseNumberField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumberField.getSelectionModel().getSelectedIndex());
    }

    public void courseNameField(ActionEvent actionEvent) {
        courseNumberField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }

    public void onSelectFile(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        file = fileChooser.showOpenDialog(onSelectFile.getScene().getWindow());
    }
}
