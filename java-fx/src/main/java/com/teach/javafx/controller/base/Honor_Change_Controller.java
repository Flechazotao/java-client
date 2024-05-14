package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Honor_Change_Controller {

    @FXML
    private TextField NameField;

    @FXML
    private TextField honorFromField;

    @FXML
    private DatePicker honorFromPicker;

    @FXML
    private TextField honorNameField;

    @FXML
    private TextField levelField;

    @FXML
    private Button onCancel;

    @FXML
    private TextField studentIdField;

    @FXML
    private TextField tupeField;

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    public void onConformation(ActionEvent actionEvent) {
    }
}
