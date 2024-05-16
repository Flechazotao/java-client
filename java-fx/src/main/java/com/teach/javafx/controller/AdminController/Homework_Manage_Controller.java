package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.Homework;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class Homework_Manage_Controller extends manage_MainFrame_controller {
    @FXML
    public TableView<Homework> dataTableView;
    @FXML
    public TableColumn<Homework,String> courseNumberColumn;
    @FXML
    public TableColumn<Homework,String> courseNameColumn;
    @FXML
    public TableColumn<Homework,String> homeworkNameColumn;
    @FXML
    public TableColumn<Homework,String> studentIdColumn;
    @FXML
    public TableColumn<Homework,String> studentNameColumn;
    @FXML
    public TableColumn<Homework,String> isSubmitColumn;
    @FXML
    public TableColumn<Homework,String> submitTimeColumn;
    @FXML
    public TableColumn<Homework,String> isCheckedColumn;
    @FXML
    public TableColumn<Homework,String> checkTimeColumn;
    @FXML
    public TableColumn<Homework,String> fileColumn;
    @FXML
    public TableColumn<Homework,String> homeworkScoreColumn;
    @FXML
    public TableColumn<Homework,String> changeColumn;
    @FXML
    public TableColumn<Homework,String> deleteColumn;

    @FXML
    public Button onInquire;
    @FXML
    public TextField InquireField;

    public void onInquire(ActionEvent actionEvent) {
    }
}
