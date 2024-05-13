package com.teach.javafx.controller.base;

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

import java.util.List;

public class FamilyChangeController {
        @FXML
        private TextField WorkplaceField;

        @FXML
        private DatePicker birthdayPicker;

        @FXML
        private TextField genderField;

        @FXML
        private TextField nameField;

        @FXML
        private Button onConfirmation;

        @FXML
        private Button onReturn;

        @FXML
        private TextField phoneField;

        @FXML
        private TextField relationField;

        public void initialize(){
            List<FamilyMember> familyMemberList = FamilyInformation_Controller.getFamilyMemberList();
            FamilyMember familyMember=familyMemberList.get(FI_ButtonCellFactory.getIndex());
            WorkplaceField.setText(familyMember.getUnit());
            birthdayPicker.getEditor().setText(familyMember.getBirthday());
            genderField.setText(familyMember.getGender());
            nameField.setText(familyMember.getName());
            phoneField.setText(familyMember.getPhone());
            relationField.setText(familyMember.getRelation());
        }
        public void onConfirmation() {
            if( nameField.getText().equals("")) {
                MessageDialog.showDialog("姓名为空，不能修改!!!");
                Stage stage = (Stage) onReturn.getScene().getWindow();
                stage.close();
            }
            FamilyMember familyMember=new FamilyMember();
            familyMember.setName(nameField.getText());
            familyMember.setGender(genderField.getText());

            familyMember.setUnit(WorkplaceField.getText());
            familyMember.setBirthday(birthdayPicker.getEditor().getText());
            familyMember.setMemberId(FamilyInformation_Controller.getFamilyMemberList().get(FI_ButtonCellFactory.getIndex()).getMemberId());
            familyMember.setStudent(StudentManageController.SM_ButtonCellFactory.getStudent());
            DataRequest req=new DataRequest();
            req.add("familyMember",familyMember);
            DataResponse res = HttpRequestUtil.request("/api/familyMember/add",req);
            if (res.getCode()==401){
                MessageDialog.showDialog("修改失败!");
                Stage stage = (Stage) onReturn.getScene().getWindow();
                stage.close();
            }
            else {
                MessageDialog.showDialog("修改成功!!!");
                FamilyInformation_Controller.updateDataTableView();
                Stage stage = (Stage) onReturn.getScene().getWindow();
                stage.close();
            }
        }
        public void onReturn() {
            Stage stage = (Stage) onReturn.getScene().getWindow();
            stage.close();
        }
}

