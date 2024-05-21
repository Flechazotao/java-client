package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
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

    private byte[] fileContent;

    private File file;

    public List<Course> courses;

    public void initialize(){
        courseNumberField.getItems().clear();
        courseNameField.getItems().clear();
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        courses= JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);
        courseNumberField.getItems().add("请选择课程号");
        courseNumberField.getSelectionModel().select(0);
        courseNameField.getItems().add("请选择课程");
        courseNameField.getSelectionModel().select(0);
        for(Course course:courses){
            courseNumberField.getItems().add(course.getNumber().toString());
            courseNameField.getItems().add(course.getName());
        }
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
        DataResponse res1=HttpRequestUtil.uploadFile("/api/homeworkInfo/upload",file.getPath());
        if(res1.getCode()==200){
            MessageDialog.showDialog("文件上传成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("文件上传失败！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
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
        if (file != null) {
            // 这里可以添加代码来读取文件内容，并准备上传
            FileInputStream fileInputStream = new FileInputStream(file);
            // 读取文件内容到字节数组，准备上传
            fileContent = new byte[(int) file.length()];
            try {
                fileInputStream.read(fileContent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
