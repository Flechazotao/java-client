package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.AdminController.CourseManageController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.LeaveInfo;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.LeaveInfoInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaveInformation_Controller extends Teacher_MainFrame_controller {
    @FXML
    private TableView<LeaveInfoInfo> dataTableView;
    @FXML
    private TableColumn<LeaveInfoInfo, String> leaveTimeColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, Long> studentIdColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> studentNameColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> leaveReasonColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> leaveBeginTimeColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> leaveEndTimeColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> leaveStatusColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> yesColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> noColumn;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;

    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numName",query);
        DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
        List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        List<LeaveInfo> newleaveInfoList = new ArrayList<>();
        for (Student s:studentList){
            List<LeaveInfo> Lists = new ArrayList<>();
            DataRequest request=new DataRequest();
            request.add("id",s.getStudentId());
            DataResponse response= HttpRequestUtil.request("/api/leaveInfo/findByStudent",request);
            Lists=JSON.parseArray(JSON.toJSONString(response.getData()), LeaveInfo.class);
            newleaveInfoList.addAll(Lists);
        }
        setDataTableView(newleaveInfoList);
    }

    @Getter
    private static List<LeaveInfo> leaveInfoList = new ArrayList<>();

    private static ObservableList<LeaveInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<LeaveInfo> list){
        leaveInfoList=list;
        observableList.clear();
        for(LeaveInfo leaveInfo:leaveInfoList){
            observableList.addAll(FXCollections.observableArrayList(new LeaveInfoInfo(leaveInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/leaveInfo/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),LeaveInfo.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/leaveInfo/findAll",new DataRequest());
        leaveInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), LeaveInfo.class);

        leaveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveTime"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        leaveReasonColumn.setCellValueFactory(new PropertyValueFactory<>("leaveReason"));
        leaveBeginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveBeginTime"));
        leaveEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveEndTime"));
        leaveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveTime"));
        leaveStatusColumn.setCellValueFactory(new PropertyValueFactory<>("leaveStatus"));
        yesColumn.setCellFactory(new LIC_ButtonCellFactory<>("同意"));
        noColumn.setCellFactory(new LIC_ButtonCellFactory<>("不同意"));

        TableView.TableViewSelectionModel<LeaveInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(leaveInfoList);
    }
}

class LIC_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public LIC_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);
            {
                button.setOnAction(event -> {
                    FXMLLoader fxmlLoader = null;
                    if (Objects.equals(property, "同意")){
                        LeaveInfo leaveInfo= LeaveInformation_Controller.getLeaveInfoList().get(getIndex());
                        if(!Objects.equals(leaveInfo.getLeaveStatus(), "未审核")){
                            MessageDialog.showDialog("您已审核过这条信息");
                            return;
                        }
                        int ret = MessageDialog.choiceDialog("确认批准请假吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        leaveInfo.setLeaveStatus("未销假");
                        DataRequest req=new DataRequest();
                        req.add("leaveInfo",leaveInfo);
                        DataResponse response=HttpRequestUtil.request("/api/leaveInfo/add",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("修改成功!");
                            LeaveInformation_Controller.updateDataTableView();
                        }
                    }
                    else if (Objects.equals(property, "不同意")) {
                        LeaveInfo leaveInfo= LeaveInformation_Controller.getLeaveInfoList().get(getIndex());
                        if(!Objects.equals(leaveInfo.getLeaveStatus(), "未审核")){
                            MessageDialog.showDialog("您已审核过这条信息");
                            return;
                        }
                        int ret = MessageDialog.choiceDialog("确认批准请假吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        leaveInfo.setLeaveStatus("未批准");
                        DataRequest req=new DataRequest();
                        req.add("leaveInfo",leaveInfo);
                        DataResponse response=HttpRequestUtil.request("/api/leaveInfo/add",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("修改成功!");
                            LeaveInformation_Controller.updateDataTableView();
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

