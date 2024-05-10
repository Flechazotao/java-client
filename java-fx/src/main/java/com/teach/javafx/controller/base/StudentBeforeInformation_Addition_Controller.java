package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class StudentBeforeInformation_Addition_Controller {

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
    public void onConfirmation(ActionEvent actionEvent) {

        
    }

    public void onReturn(ActionEvent actionEvent) {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
