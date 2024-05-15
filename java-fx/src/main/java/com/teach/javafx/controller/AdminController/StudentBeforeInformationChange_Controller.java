package com.teach.javafx.controller.AdminController;

import com.teach.javafx.models.DO.BeforeUniversity;
import com.teach.javafx.models.DO.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentBeforeInformationChange_Controller {


    @FXML
    private TextField addressColumn;

    @FXML
    private TextField emailColumn;

    @FXML
    private TextField nameColumn;

    @FXML
    private Button onConfirmation;

    @FXML
    private Button onReturn;

    @FXML
    private TextField phoneColumn;

    @FXML
    private TextField provinceColumn;

    @FXML
    private TextField schoolColumn;

    Student student= StudentManageController.SM_ButtonCellFactory.getStudent();

    BeforeUniversity beforeUniversity= Student_BeforeInfo_Manage_Controller.getBeforeUni();

    public void initialize(){
        nameColumn.setText(student.getPerson().getName());
        emailColumn.setText(student.getPerson().getEmail());
        phoneColumn.setText(student.getPerson().getPhone());
        addressColumn.setText(student.getPerson().getAddress());
        provinceColumn.setText(beforeUniversity.getGraduatedProvince());
        schoolColumn.setText(beforeUniversity.getGraduatedSchool());
    }

    public void onConfirmation(ActionEvent actionEvent) {

    }

    public void onReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
