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

import java.net.http.HttpRequest;

public class SocietyMember_Addition_Controller {

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
    public void onComfirmation(ActionEvent actionEvent) {
        Student student= StudentManageController.SM_ButtonCellFactory.getStudent();
        SocietyMember societyMember = new SocietyMember();
        societyMember.setGender(genderField.getText());
        societyMember.setRelation(relationField.getText());
        societyMember.setName(nameField.getText());
        societyMember.setStudent(student);
        DataRequest req=new DataRequest();
        req.add("societyMember",societyMember);
        DataResponse res= HttpRequestUtil.request("/api/societyMember/add",req);
        if (res.getCode() == 401){
            MessageDialog.showDialog("信息不完整,无法添加");
        }
        else {
            MessageDialog.showDialog("添加成功!!!");
            Stage stage = (Stage) onReturn.getScene().getWindow();
            stage.close();
        }
    }

    public  void onReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
