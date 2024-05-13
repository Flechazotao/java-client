package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class DailyActivityChange_Controller {

    @FXML
    private TextField NameField;

    @FXML
    private DatePicker endTime;

    @FXML
    private Button onCancel;

    @FXML
    private TextField participantsField;

    @FXML
    private TextField placeField;

    @FXML
    private DatePicker startTime;

    @FXML
    private TextField typeField;

    @FXML
    void onCancel(ActionEvent event) {

    }

    @FXML
    void onConformation(ActionEvent event) {

    }
}
