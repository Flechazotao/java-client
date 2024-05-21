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
import java.util.List;

public class SocietyMember_Change_Controller {
    @FXML
    private TextField phoneField;
    @FXML
    private TextField genderField;

    @FXML
    private TextField nameField;

    @FXML
    private Button onComfirmation;

    @FXML
    private Button onReturn;

    @FXML
    private TextField relationField;

    @Setter
    private static List<SocietyMember>societyMemberList=new ArrayList<>();

    public void initialize(){
        societyMemberList=SocietyMember_Controller.getSocietyMemberList();
        genderField.setText(societyMemberList.get(SoM_ButtonCellFactory.getIndex()).getGender());
        nameField.setText(societyMemberList.get(SoM_ButtonCellFactory.getIndex()).getName());
        relationField.setText(societyMemberList.get(SoM_ButtonCellFactory.getIndex()).getRelation());
        phoneField.setText(societyMemberList.get(SoM_ButtonCellFactory.getIndex()).getRelation());

    }
    public void onComfirmation(ActionEvent actionEvent) {
        Student student= SocietyMember_Controller.getStudent();
        SocietyMember societyMember = new SocietyMember();
        societyMember.setGender(genderField.getText());
        societyMember.setRelation(relationField.getText());
        societyMember.setName(nameField.getText());
        societyMember.setStudent(student);
        DataRequest req=new DataRequest();
        req.add("societyMember",societyMember);
        DataResponse res= HttpRequestUtil.request("/api/societyMember/add",req);
        if (res.getCode() == 401){
            MessageDialog.showDialog("信息不完整,无法修改");
        }
        else MessageDialog.showDialog("修改成功!!!");

    }

    public void onReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
