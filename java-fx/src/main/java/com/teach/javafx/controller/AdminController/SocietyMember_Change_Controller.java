package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.SocietyMember;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.ArrayList;

public class SocietyMember_Change_Controller {
    @FXML
    private TextField phoneField;
    @FXML
    private TextField genderField;
    @FXML
    private TextField nameField;

    @FXML
    private Button onConfirmation;

    @FXML
    private Button onReturn;

    @FXML
    private TextField relationField;

    @Setter
    private static SocietyMember societyMember;

    public void initialize(){
        societyMember =SocietyMember_Controller.getSocietyMemberList().get(SoM_ButtonCellFactory.getIndex());
        genderField.setText(societyMember.getGender());
        nameField.setText(societyMember.getName());
        relationField.setText(societyMember.getRelation());
        phoneField.setText(societyMember.getPhone());
    }
    public void onConfirmation(ActionEvent actionEvent) {
        Student student= SocietyMember_Controller.getStudent();
        societyMember.setGender(genderField.getText());
        societyMember.setRelation(relationField.getText());
        societyMember.setName(nameField.getText());
        societyMember.setPhone(phoneField.getText());
        societyMember.setStudent(student);
        DataRequest req=new DataRequest();
        req.add("societyMember",societyMember);
        DataResponse res= HttpRequestUtil.request("/api/societyMember/add",req);
        if (res.getCode() == 401){
            MessageDialog.showDialog("信息不完整,无法修改");
        }
        else {
            MessageDialog.showDialog("修改成功!!!");
            ((Stage)onConfirmation.getScene().getWindow()).close();
        }
    }

    public void onReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
