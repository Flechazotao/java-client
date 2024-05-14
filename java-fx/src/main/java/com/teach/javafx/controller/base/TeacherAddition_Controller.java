package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TeacherAddition_Controller {

    @FXML
    private TextField addressField;

    @FXML
    private TextField ageField;

    @FXML
    private DatePicker birthdayPicker;

    @FXML
    private TextField cardField;

    @FXML
    private TextField deptField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField genderField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField teacherIdField;

    @FXML
    private TextField titleField;

    @FXML
    private Button onCancel;


    public void onComfirmation(ActionEvent actionEvent) {
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }
}
