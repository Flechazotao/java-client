package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FeeChange_controller {
    @FXML
    private TextField NameField;

    @FXML
    private DatePicker dayPicker;

    @FXML
    private TextField descriptionField;

    @FXML
    private TextField feeTypeField;

    @FXML
    private TextField moneyField;

    @FXML
    private Button onCancel;

    @FXML
    private TextField studentidField;

    public void onConfirmation(ActionEvent actionEvent) {
    }
    public void onCancel(ActionEvent actionEvent) {
    }
}
