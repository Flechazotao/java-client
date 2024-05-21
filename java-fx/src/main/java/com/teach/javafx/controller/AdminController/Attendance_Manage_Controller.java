package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.AttendanceInfo;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.AttendanceInfoInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Attendance_Manage_Controller extends manage_MainFrame_controller {

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
    public TableColumn<AttendanceInfo,String> changeCol;
    @FXML
    public TableColumn<AttendanceInfo,String> deleteCol;

    @FXML
    public Button onInquire;
    @FXML
    public Button onAddAttendance;

    @FXML
    public ComboBox<String> isAttendedField;
    @FXML
    private CheckBox findByStudent;
    @FXML
    private CheckBox findByIsAttended;
    @FXML
    void onInquire(ActionEvent event){
//        String query=InquireField.getText();
//        DataRequest req=new DataRequest();
//        req.add("id",query);
//        DataResponse res= HttpRequestUtil.request("/api/attendance/findByStudent",req);
//        attendanceInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), AttendanceInfo.class);
//        setDataTableView(attendanceInfoList);
        if (InquireField.isVisible()){
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
            List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<AttendanceInfo> newattendanceInfoList = new ArrayList<>();
            for (Student s:studentList){
                List<AttendanceInfo> Lists = new ArrayList<>();
                DataRequest request=new DataRequest();
                request.add("id",s.getStudentId());
                DataResponse response= HttpRequestUtil.request("/api/attendance/findByStudent",request);
                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), AttendanceInfo.class);
                newattendanceInfoList.addAll(Lists);
            }
            setDataTableView(newattendanceInfoList);
        } else if (isAttendedField.isVisible()) {
            String query = isAttendedField.getValue();
            DataRequest req1 = new DataRequest();
            req1.add("isAttended", query);
            DataResponse res = HttpRequestUtil.request("/api/attendance/findByIsAttended", req1);
            attendanceInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), AttendanceInfo.class);
            setDataTableView(attendanceInfoList);
        }

    }

    @Getter
    private static List<AttendanceInfo> attendanceInfoList = new ArrayList<>();

    private static ObservableList<AttendanceInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<AttendanceInfo> list){
        attendanceInfoList=list;
        observableList.clear();
        for(AttendanceInfo attendanceInfo:attendanceInfoList){
            observableList.addAll(FXCollections.observableArrayList(new AttendanceInfoInfo(attendanceInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/attendance/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),AttendanceInfo.class));
    }

    public void initialize() {
        //展示考勤情况下拉框
        isAttendedField.getItems().add("是");
        isAttendedField.getItems().add("否");

        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/attendance/findAll",new DataRequest());
        attendanceInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), AttendanceInfo.class);

        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        attendanceTimeColumn.setCellValueFactory(new PropertyValueFactory<>("attendanceTime"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        isAttendedColumn.setCellValueFactory(new PropertyValueFactory<>("isAttended"));
        changeCol.setCellFactory(new AIM_ButtonCellFactory<>("修改"));
        deleteCol.setCellFactory(new AIM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<AttendanceInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(attendanceInfoList);
    }

    @FXML
    void onAddAttendance(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/AttendanceAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加考勤信息");
        stage.show();
    }

    public void findByStudent(ActionEvent actionEvent) {
        findByIsAttended.setSelected(false);
        isAttendedField.setVisible(false);
        InquireField.setVisible(true);
    }

    public void findByIsAttended(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        isAttendedField.setVisible(true);
        InquireField.setVisible(false);
    }
}



class AIM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public AIM_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);
            {
                button.setOnAction(event -> {

                    FXMLLoader fxmlLoader = null;

                    if (Objects.equals(property, "修改")){
                        Attendance_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/AttendanceChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改考勤信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer attendanceInfoId= Attendance_Manage_Controller.getAttendanceInfoList().get(getIndex()).getAttendanceId();
                        DataRequest req=new DataRequest();
                        req.add("id",attendanceInfoId);
                        DataResponse response=HttpRequestUtil.request("/api/attendance/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            Attendance_Manage_Controller.updateDataTableView();
                        }
                    }
                });
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
            }
        };
    }
}