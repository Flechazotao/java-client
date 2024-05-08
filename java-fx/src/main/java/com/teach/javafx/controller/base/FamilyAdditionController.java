package com.teach.javafx.controller.base;

import com.teach.javafx.models.DO.FamilyMember;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class FamilyAdditionController {
    @FXML
    private TextField WorkplaceField;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private TextField genderField;

    @FXML
    private TextField nameField;

    @FXML
    private Button onComfirmation;

    @FXML
    private Button onReturn;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField relationField;

    public void onComfirmation() {

        if( nameField.getText().equals("")) {
            MessageDialog.showDialog("姓名为空，不能添加");
            Stage stage = (Stage) onReturn.getScene().getWindow();
            stage.close();
        }
        FamilyMember familyMember=new FamilyMember();
        familyMember.setName(nameField.getText());
        familyMember.setGender(genderField.getText());
        familyMember.setRelation(relationField.getText());
        familyMember.setUnit(WorkplaceField.getText());
        familyMember.setBirthday(birthdayPicker.getEditor().getText());
        DataRequest req=new DataRequest();
        req.add("familyMember",familyMember);
        DataResponse res = HttpRequestUtil.request("/api/familyMember/add",req);
        MessageDialog.showDialog("添加成功!!!");
    }

    public void onReturn() {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}