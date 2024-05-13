package com.teach.javafx.controller.base;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HomeworkController extends manage_MainFrame_controller {
    public TableView dataTableView;
    public TableColumn courseNumberColumn;
    public TableColumn courseNameColumn;
    public TableColumn homeworkNameColumn;
    public TableColumn studentIdColumn;
    public TableColumn studentNameColumn;
    public TableColumn isSubmitColumn;
    public TableColumn submitTimeColumn;
    public TableColumn isCheckedColumn;
    public TableColumn checkTimeColumn;
    public TableColumn fileColumn;
    public TableColumn homeworkScoreColumn;

    public Button onInquire;
    public TextField InquireField;

    public void onInquire(ActionEvent actionEvent) {
    }
}
