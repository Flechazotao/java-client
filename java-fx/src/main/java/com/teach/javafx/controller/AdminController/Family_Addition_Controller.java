package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.TeacherController.Student_Information_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.FamilyMember;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Family_Addition_Controller {
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
        familyMember.setPhone(phoneField.getText());
        familyMember.setStudent(Family_Manage_Controller.getStudent());
        DataRequest req=new DataRequest();
        req.add("familyMember",familyMember);
        DataResponse res = HttpRequestUtil.request("/api/familyMember/add",req);
        if (res.getCode()==401){
            MessageDialog.showDialog("添加失败!");
            Stage stage = (Stage) onReturn.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("添加成功!!!");
            Family_Manage_Controller.updateDataTableView();
            Stage stage = (Stage) onReturn.getScene().getWindow();
            stage.close();
        }
    }

    public void onReturn() {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
