package com.teach.javafx.controller.base;

import com.teach.javafx.models.DO.Fee;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class FeeController extends manage_MainFrame_controller {

    @FXML
    private TextField InquireField;

    @FXML
    private TableColumn<Fee, String> changeCol;

    @FXML
    private TableView<Fee> dataTableView;

    @FXML
    private TableColumn<Fee, String>dayColumn;

    @FXML
    private TableColumn<Fee, String> deleteCol;

    @FXML
    private TableColumn<Fee, String>descriptionColumn;

    @FXML
    private TableColumn<Fee, String> feeTypeColumn;

    @FXML
    private TableColumn<Fee, String> moneyColumn;

    @FXML
    private TableColumn<Fee, String> numberColumn;

    @FXML
    private Button onAddFee;

    @FXML
    private Button onInquire;

    @FXML
    private TableColumn<Fee, String> studentNameColumn;


    public void onInquire(ActionEvent actionEvent) {
    }

    public void onAddFee(ActionEvent actionEvent) {
    }
}
