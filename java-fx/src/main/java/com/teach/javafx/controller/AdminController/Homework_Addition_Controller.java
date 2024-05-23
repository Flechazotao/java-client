package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Homework_Addition_Controller {
    @FXML
    public ComboBox<String> courseNumField;
    @FXML
    public ComboBox<String> courseNameField;
    @FXML
    public ComboBox<String> homeworkNameField;
    @FXML
    public ComboBox<String> studentNameField;
    @FXML
    public ComboBox<String> studentIdField;
    @FXML
    public TextField submitStatusField;
    @FXML
    public TextField homeworkScoreField;
    @FXML
    public DatePicker checkTimeField;

    @FXML
    public Button onCancel;
    @FXML
    public Button onConfirmation;

    public List<Student> students;

    @FXML
    public Button onSelectFile;
    private File file;

    @Getter
    private static List<HomeworkInfo> homeworkInfoList = new ArrayList<>();


    public void initialize(){


        //学生有关信息下拉框
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        students= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        studentIdField.getItems().add("请选择学号");
        studentNameField.getItems().add("请选择学生");
        for(Student student:students){
            studentIdField.getItems().add(student.getStudentId().toString());
            studentNameField.getItems().add(student.getPerson().getName());
        }

        //课程有关信息下拉框

        DataResponse response = HttpRequestUtil.request("/api/homeworkInfo/findAll",new DataRequest());
        homeworkInfoList= JSON.parseArray(JSON.toJSONString(response.getData()), HomeworkInfo.class);
        for(HomeworkInfo homeworkInfo:homeworkInfoList){
            courseNameField.getItems().add(homeworkInfo.getCourse().getName());
            courseNumField.getItems().add(homeworkInfo.getCourse().getNumber());
            homeworkNameField.getItems().add(homeworkInfo.getName());
        }

    }

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(studentIdField.getValue().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Homework homework=getHomework();
        DataResponse res1=HttpRequestUtil.uploadFile("/api/file/upload", Paths.get(file.getPath()),"Homework"+"\\");
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
        homework.setFile(url);


        DataRequest req=new DataRequest();
        req.add("homework",homework);
        DataResponse res = HttpRequestUtil.request("/api/homework/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该作业已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("学号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            Homework_Manage_Controller.updateDataTableView();
        }
    }

    private Homework getHomework() {
        Homework homework=new Homework();
//        Student s=new Student();
//        Person person=new Person();
//        s.setStudentId(Long.valueOf(studentIdField.getValue()));
//        person.setNumber(Long.valueOf(studentIdField.getValue()));
//        person.setName(studentNameField.getValue());
//        s.setPerson(person);
        homework.setStudent(students.get(studentIdField.getSelectionModel().getSelectedIndex()));
        HomeworkInfo homeworkInfo=new HomeworkInfo();
        homework.setIsSubmit(submitStatusField.getText());
//        if(submitStatusField.getText().equals("未提交")){
//            homework.setIsSubmit("未提交");
//            homework.setIsChecked("未审核");
//        }else if(submitStatusField.getText().equals("未审核")){
//            homework.setIsSubmit("已提交");
//            homework.setIsChecked("未审核");
//        }
//        else if(submitStatusField.getText().equals("已审核")){
//            homework.setIsSubmit("已提交");
//            homework.setIsChecked("已审核");
//        }
        homework.setHomeworkInfo(homeworkInfoList.get(homeworkNameField.getSelectionModel().getSelectedIndex()));
        homework.setHomeworkScore(homeworkScoreField.getText());
//        homework.setSubmitTime(submitTimePicker.getValue()==null ? LocalDate.now().toString() : submitTimePicker.getValue().toString());
        return homework;
    }

    public void studentId(ActionEvent actionEvent) {
        studentNameField.getSelectionModel().select(studentIdField.getSelectionModel().getSelectedIndex());
    }

    public void studentName(ActionEvent actionEvent) {
        studentIdField.getSelectionModel().select(studentNameField.getSelectionModel().getSelectedIndex());
    }
    public void courseNameField(ActionEvent actionEvent) {
        courseNumField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
        homeworkNameField.getSelectionModel().select(courseNameField.getSelectionModel().getSelectedIndex());
    }
    public void courseNumField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(courseNumField.getSelectionModel().getSelectedIndex());
        homeworkNameField.getSelectionModel().select(courseNumField.getSelectionModel().getSelectedIndex());
    }


    public void homeworkNameField(ActionEvent actionEvent) {
        courseNameField.getSelectionModel().select(homeworkNameField.getSelectionModel().getSelectedIndex());
        courseNumField.getSelectionModel().select(homeworkNameField.getSelectionModel().getSelectedIndex());
    }

    public void onSelectFile(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        file = fileChooser.showOpenDialog(onSelectFile.getScene().getWindow());
    }
}
