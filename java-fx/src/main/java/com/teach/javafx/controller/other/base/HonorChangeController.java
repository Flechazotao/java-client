package com.teach.javafx.controller.other.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.AdminController.HomeworkInfo_Manage_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
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
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

public class HonorChangeController {

    @FXML
    private ComboBox<String> studentIdField;
    @FXML
    private ComboBox<String> studentNameField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField honorNameField;
    @FXML
    private DatePicker honorTimePicker;
    @FXML
    private ComboBox<String> levelField;
    @FXML
    private TextField honorFromField;
    @FXML
    private Button onCancel;
    @FXML
    public Button onConfirmation;
    @Getter
    @Setter
    private static Integer index;

    @FXML
    public Button onSelectFile;
    private File file;

    @Getter
    private static HonorInfo honor;

    public List<Student> students;

    public static String[] levelList ={"班级","年级","院级","校级","市级","省级","国家级","世界级"};

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

        //荣誉等级下拉框
        for (String s: levelList){
            levelField.getItems().add(s);
        }

        honor =HonorManageController.getHonorInfoList().get(index);

        studentIdField.setValue(honor.getStudent().getStudentId().toString());
        studentNameField.setValue(honor.getStudent().getPerson().getName());
        typeField.setText(honor.getType());
        honorNameField.setText(honor.getHonorName());
        typeField.setText(honor.getType());
        honorTimePicker.setValue(LocalDate.parse(honor.getHonorTime()));
        levelField.setValue(honor.getLevel());
        honorFromField.setText(honor.getHonorFrom());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getValue().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setHonorInfo(honor);

        if(honor.getFile()!=null&&file!=null){
            DataRequest req1 = new DataRequest();
            String url= HonorManageController.getHonorInfoList().get(getIndex()).getFile();
            req1.add("url",url);
            DataResponse res1 = HttpRequestUtil.request("/api/file/delete", req1);
            if(res1.getCode()!=200){
                MessageDialog.showDialog("文件删除失败!");
                return;
            }

            DataResponse res=HttpRequestUtil.uploadFile("/api/file/upload", Paths.get(file.getPath()),"HonorInfo"+"\\");
            if(res.getCode()==200){
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
            url=res.getMessage().substring(8);
            honor.setFile(url);
        }

        DataRequest req=new DataRequest();
        req.add("honorInfo",honor);
        DataResponse res = HttpRequestUtil.request("/api/honorInfo/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该荣誉已存在！");
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
            HonorManageController.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setHonorInfo(HonorInfo honorInfo) {
        Student s=honorInfo.getStudent();
        Person person=s.getPerson();
        s.setStudentId(Long.valueOf(studentIdField.getValue()));
        person.setNumber(Long.valueOf(studentIdField.getValue()));
        person.setName(studentNameField.getValue());
        honorInfo.setType(typeField.getText());
        honorInfo.setHonorName(honorNameField.getText());
        honorInfo.setHonorTime(honorTimePicker.getValue()==null ? LocalDate.now().toString() : honorTimePicker.getValue().toString());
        honorInfo.setLevel(levelField.getValue());
        honorInfo.setHonorFrom(honorFromField.getText());
    }

    public void studentIdField(ActionEvent actionEvent) {
        studentNameField.getSelectionModel().select(studentIdField.getSelectionModel().getSelectedIndex());
    }

    public void studentNameField(ActionEvent actionEvent) {
        studentIdField.getSelectionModel().select(studentNameField.getSelectionModel().getSelectedIndex());
    }

    public void onSelectFile(ActionEvent actionEvent) throws FileNotFoundException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("选择文件");
        file = fileChooser.showOpenDialog(onSelectFile.getScene().getWindow());
    }
}
