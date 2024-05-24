package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Teacher;
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

public class TeacherChange_Controller {
    @FXML
    private TextField teacherIdField;
    @FXML
    private TextField nameField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField politicsField;
    @FXML
    private TextField titleField;
    @FXML
    private TextField deptField;
    @FXML
    private TextField degreeField;
    @FXML
    private TextField cardField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private Button onCancel;

    @Getter
    private static Teacher teacher;

    private Long teacherId = null;
    private Integer personId = null;

    @Getter
    @Setter
    private static int index=0;

    public void initialize(){
        teacher = TeacherManageController.getTeacherList().get(index);
        teacherIdField.setText(String.valueOf(teacher.getTeacherId()));
        nameField.setText(String.valueOf(teacher.getPerson().getName()));
        genderField.setText(String.valueOf(teacher.getPerson().getGender()));
        politicsField.setText(String.valueOf(teacher.getPerson().getPoliticalStatus()));
        titleField.setText(String.valueOf(teacher.getTitle()));
        deptField.setText(String.valueOf(teacher.getPerson().getDept()));
        degreeField.setText(String.valueOf(teacher.getDegree()));
        cardField.setText(String.valueOf(teacher.getPerson().getCard()));
        phoneField.setText(String.valueOf(teacher.getPerson().getPhone()));
        emailField.setText(String.valueOf(teacher.getPerson().getEmail()));
        addressField.setText(String.valueOf(teacher.getPerson().getAddress()));
        birthdayPicker.setValue(LocalDate.now());
    }

    public void onConfirmation(javafx.event.ActionEvent actionEvent) {
        if(teacherIdField.getText().equals("")) {
            MessageDialog.showDialog("工号不能为空");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setTeacher(teacher);
        DataRequest req=new DataRequest();
        req.add("teacher",teacher);
        DataResponse res = HttpRequestUtil.request("/api/teacher/addOrUpdateTeacher",req);
        if(res.getCode()!=200){
            MessageDialog.showDialog(res.getMessage());
            ((Stage)onCancel.getScene().getWindow()).close();
            return;
        }

        MessageDialog.showDialog("修改成功！");
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();

        TeacherManageController.updateDataTableView();
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setTeacher(Teacher t) {
        Person p=t.getPerson();
        p.setIntroduce("");
//        t.setTeacherId(Long.valueOf(teacherIdField.getText()));
        p.setNumber(Long.valueOf(teacherIdField.getText()));
        p.setName(nameField.getText());
        p.setDept(deptField.getText());
        t.setDegree(degreeField.getText());
        t.setTitle(titleField.getText());
        p.setCard(cardField.getText());
        p.setGender(genderField.getText());
        p.setBirthday(birthdayPicker.getEditor().getText());
        p.setEmail(emailField.getText());
        p.setPhone(phoneField.getText());
        p.setAddress(addressField.getText());
        p.setPoliticalStatus(politicsField.getText());
    }
}
