package com.teach.javafx.controller.base;

import com.teach.javafx.models.DO.AttendanceInfo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AttendanceController extends manage_MainFrame_controller {

    @FXML
    public TableView<AttendanceInfo> dataTableView;
    @FXML
    public TableColumn<AttendanceInfo,String> activityNameColumn;
    @FXML
    public TableColumn<AttendanceInfo,String> typeColumn;
    @FXML
    public TableColumn<AttendanceInfo,String> attendanceTimeColumn;
    @FXML
    public TableColumn<AttendanceInfo,String> studentColumn;
    @FXML
    public TableColumn<AttendanceInfo,Long> studentIdColumn;
    @FXML
    public TableColumn<AttendanceInfo,String> isAttendedColumn;
    @FXML
    public TextField InquireField;
    @FXML
    public TableColumn<AttendanceInfo,String> changeCol;
    @FXML
    public TableColumn<AttendanceInfo,String> deleteCol;

    @FXML
    public Button onInquire;

    public void onInquire(ActionEvent actionEvent) {
    }


}
