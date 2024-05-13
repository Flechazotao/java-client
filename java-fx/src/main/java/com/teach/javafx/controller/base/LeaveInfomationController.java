package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class LeaveInfomationController extends manage_MainFrame_controller {
    public TableView dataTableView;
    public TableColumn leaveTime;
    public TableColumn studentId;
    public TableColumn leaveReason;
    public TableColumn leaveBeginTime;
    public TableColumn leaveEndTime;
    public TableColumn approver;
    public TableColumn isBackSchool;
    public Button onInquire;
    public TextField InquireField;
    public Button onAdd;

    public void onInquire(ActionEvent actionEvent) {
    }

    public void onAdd(ActionEvent actionEvent) {
    }
}
