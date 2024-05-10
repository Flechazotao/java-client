package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class DailyActivityController extends manage_MainFrame_controller {
    public TableView dataTableView;
    public TableColumn activityIdColumn;
    public TableColumn activityTypeColumn;
    public TableColumn nameColumn;
    public TableColumn beginTimeColumn;
    public TableColumn endTimeColumn;
    public TableColumn locationColumn;
    public TextField InquireField;
    public Button onInquire;
    public Button onAddDailyActivity;

    public void onAddStudent(ActionEvent actionEvent) {
    }

    public void onInquire(ActionEvent actionEvent) {
    }
}
