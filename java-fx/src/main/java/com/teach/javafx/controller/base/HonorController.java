package com.teach.javafx.controller.base;

import com.teach.javafx.models.DO.HonorInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class HonorController extends manage_MainFrame_controller {
    @FXML
    private TextField InquireField;

    @FXML
    private TableColumn<HonorInfo, String> changeCol;

    @FXML
    private TableView <HonorInfo> dataTableView;


    @FXML
    private TableColumn<HonorInfo, String> deleteColumn;

    @FXML
    private TableColumn<HonorInfo, String>file;

    @FXML
    private TableColumn<HonorInfo, String> honorFrom;

    @FXML
    private TableColumn<HonorInfo, String> honorName;

    @FXML
    private TableColumn<HonorInfo, String> honorTime;

    @FXML
    private TableColumn<HonorInfo, String> level;

    @FXML
    private Button onAdd;

    @FXML
    private Button onInquire;

    @FXML
    private TableColumn<HonorInfo, String>studentId;

    @FXML
    private TableColumn<HonorInfo, String> studentName;

    @FXML
    private TableColumn<HonorInfo, String> type;


    public void onInquire(ActionEvent actionEvent) {
    }

    public void onAdd(ActionEvent actionEvent) {
    }
}
