package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LeaveInfomationController extends manage_MainFrame_controller {
    @FXML
    private TableColumn<?, ?> ChangeCol;

    @FXML
    private TextField InquireField;

    @FXML
    private TableColumn<?, ?> approver;


    @FXML
    private TableView<?> dataTableView;

    @FXML
    private TableColumn<?, ?> deleteCol;

    @FXML
    private TableColumn<?, ?> isBackSchool;

    @FXML
    private TableColumn<?, ?> leaveBeginTime;

    @FXML
    private TableColumn<?, ?> leaveEndTime;

    @FXML
    private TableColumn<?, ?> leaveReason;

    @FXML
    private TableColumn<?, ?> leaveTime;

    @FXML
    private Button onAdd;

    @FXML
    private Button onInquire;

    @FXML
    private TableColumn<?, ?> studentId;

    public void onInquire(ActionEvent actionEvent) {
    }

    public void onAdd(ActionEvent actionEvent) {
    }
}
