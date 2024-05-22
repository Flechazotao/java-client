package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.TeacherController.LeaveInformation_Controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.LeaveInfo;
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

public class LeaveInfo_S_Controller extends student_MainFrame_controller{
    @FXML
    private TableView<LeaveInfoInfo> dataTableView;
    @FXML
    private TableColumn<LeaveInfoInfo, String> leaveTimeColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> approverColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> leaveReasonColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> leaveBeginTimeColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> leaveEndTimeColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> leaveStatusColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> cancelColumn;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;

    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res= HttpRequestUtil.request("/api/leaveInfo/findByStudent",req);
        leaveInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), LeaveInfo.class);
        setDataTableView(leaveInfoList);
    }

    private static Long studentId= Long.valueOf(LoginController.getNumber());

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
        DataRequest req= new DataRequest();
        req.add("id",studentId);
        DataResponse res = HttpRequestUtil.request("/api/leaveInfo/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),LeaveInfo.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataRequest req= new DataRequest();
        req.add("id",studentId);
        DataResponse res = HttpRequestUtil.request("/api/leaveInfo/findByStudent",req);
        leaveInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), LeaveInfo.class);

        leaveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveTime"));
        approverColumn.setCellValueFactory(new PropertyValueFactory<>("approver"));
        leaveReasonColumn.setCellValueFactory(new PropertyValueFactory<>("leaveReason"));
        leaveBeginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveBeginTime"));
        leaveEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveEndTime"));
        leaveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveTime"));
        leaveStatusColumn.setCellValueFactory(new PropertyValueFactory<>("leaveStatus"));
        cancelColumn.setCellFactory(new LICS_ButtonCellFactory<>("取消"));

        TableView.TableViewSelectionModel<LeaveInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(leaveInfoList);
    }
}

class LICS_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public LICS_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);
            {
                button.setOnAction(event -> {
                    FXMLLoader fxmlLoader = null;
                    if (Objects.equals(property, "取消")){
                        LeaveInfo leaveInfo= LeaveInformation_Controller.getLeaveInfoList().get(getIndex());
                        if(!Objects.equals(leaveInfo.getLeaveStatus(), "未审核")){
                            MessageDialog.showDialog("审核过的信息不可以取消");
                            return;
                        }
                        DataRequest req=new DataRequest();
                        req.add("id",leaveInfo.getLeaveId());
                        DataResponse response=HttpRequestUtil.request("/api/leaveInfo/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("取消成功!");
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
