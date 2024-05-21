package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.likeUseless.LoginController;
import com.teach.javafx.models.DO.AttendanceInfo;
import com.teach.javafx.models.DTO.AttendanceInfoInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Attendance_S_Controller extends student_MainFrame_controller{

    @FXML
    public TableView<AttendanceInfoInfo> dataTableView;
    @FXML
    public TableColumn<AttendanceInfoInfo,String> activityNameColumn;
    @FXML
    public TableColumn<AttendanceInfoInfo,String> typeColumn;
    @FXML
    public TableColumn<AttendanceInfoInfo,String> attendanceTimeColumn;
    @FXML
    public TableColumn<AttendanceInfoInfo,String> studentColumn;
    @FXML
    public TableColumn<AttendanceInfoInfo,Long> studentIdColumn;
    @FXML
    public TableColumn<AttendanceInfoInfo,String> isAttendedColumn;
    @FXML
    public TextField InquireField;
    @FXML
    public ComboBox<String>isAttendedField;

    @FXML
    public Button onInquire;

    @Getter
    private static List<AttendanceInfo> attendanceInfoList = new ArrayList<>();

    private static String studentid= LoginController.getNumber();

    private static ObservableList<AttendanceInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<AttendanceInfo> list){
        attendanceInfoList=list;
        observableList.clear();
        for(AttendanceInfo attendanceInfo:attendanceInfoList){
            observableList.addAll(FXCollections.observableArrayList(new AttendanceInfoInfo(attendanceInfo)));
        }
    }

    public static void updateDataTableView(){
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res = HttpRequestUtil.request("/api/attendance/findByStudent",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),AttendanceInfo.class));
    }

    public void initialize(){
        // 展示考勤情况下拉框,没有补充其他查询,暂时隐藏textField
        InquireField.setVisible(false);
        isAttendedField.getItems().add("是");
        isAttendedField.getItems().add("否");

        dataTableView.setItems(observableList);
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res = HttpRequestUtil.request("/api/attendance/findByStudent",req);
        attendanceInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), AttendanceInfo.class);

        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        attendanceTimeColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceTime"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        isAttendedColumn.setCellValueFactory(new PropertyValueFactory<>("isAttended"));

        TableView.TableViewSelectionModel<AttendanceInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(attendanceInfoList);
    }

    public void onInquire(){
        if (isAttendedField.isVisible()){
        String query = isAttendedField.getValue();
        DataRequest req = new DataRequest();
        req.add("isAttended", query);
        DataResponse res = HttpRequestUtil.request("/api/attendance/findByIsAttended", req);
        attendanceInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), AttendanceInfo.class);
        setDataTableView(attendanceInfoList);
        }
    }

}
