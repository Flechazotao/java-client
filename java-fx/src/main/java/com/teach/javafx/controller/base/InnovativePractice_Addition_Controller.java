package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InnovativePractice_Addition_Controller {

    @FXML
    private TextField TeacherField;

    @FXML
    private TextField achievementField;

    @FXML
    private DatePicker endTime;

    @FXML
    private TextField memberField;

    @FXML
    private TextField nameField;

    @FXML
    private Button onCancel;

    @FXML
    private DatePicker startTime;

    @FXML
    private TextField typeField;

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    public void onChange(ActionEvent actionEvent) {
    }
}
