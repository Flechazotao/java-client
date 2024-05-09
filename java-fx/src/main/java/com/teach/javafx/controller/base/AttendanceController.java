package com.teach.javafx.controller.base;

import com.teach.javafx.models.DO.AttendanceInfo;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class AttendanceController extends manage_MainFrame_controller {

    public Button onInquire;
    public TableView<AttendanceInfo> dataTableView;
    public TableColumn<AttendanceInfo,String> activityNameColumn;
    public TableColumn<AttendanceInfo,String> typeColumn;
    public TableColumn<AttendanceInfo,String> attendanceTimeColumn;
    public TableColumn<AttendanceInfo,String> studentColumn;
    public TableColumn<AttendanceInfo,Long> studentIdColumn;
    public TableColumn<AttendanceInfo,String> isAttendedColumn;
    public TextField InquireField;

    public void onInquire(ActionEvent actionEvent) {
    }


}
