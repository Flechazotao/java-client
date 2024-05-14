package com.teach.javafx.controller.base;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class TeacherChange_Controller {
    public static void setIndex(int index) {
    }
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
    private Button onCancel;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField teacherIdField;

    @FXML
    private TextField titleField;


    public void onComfirmation(javafx.event.ActionEvent actionEvent) {
    }

    public void onCancel(javafx.event.ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }
}
