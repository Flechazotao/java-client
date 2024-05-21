package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class LeaveInfo_Manage_Controller extends manage_MainFrame_controller {
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
    private TableColumn<LeaveInfoInfo, String> approverColumn;
    @FXML
    private TableColumn<LeaveInfoInfo,String> isBackSchoolColumn;
    @FXML
    private TableColumn<LeaveInfoInfo, String> ChangeCol;
    @FXML
    private TableColumn<LeaveInfoInfo, String> deleteCol;

    @FXML
    private Button onAdd;
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
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        leaveReasonColumn.setCellValueFactory(new PropertyValueFactory<>("leaveReason"));
        leaveBeginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveBeginTime"));
        leaveEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveEndTime"));
        approverColumn.setCellValueFactory(new PropertyValueFactory<>("approver"));
        leaveTimeColumn.setCellValueFactory(new PropertyValueFactory<>("leaveTime"));
        isBackSchoolColumn.setCellValueFactory(new PropertyValueFactory<>("isBackSchool"));
        ChangeCol.setCellFactory(new LIM_ButtonCellFactory<>("修改"));
        deleteCol.setCellFactory(new LIM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<LeaveInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(leaveInfoList);
    }

    @FXML
    void onAdd(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/LeaveInfoAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加请假信息");
        stage.show();
    }
}



class LIM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public LIM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        Leave_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/LeaveInfoChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改请假信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer leaveInfoId= LeaveInfo_Manage_Controller.getLeaveInfoList().get(getIndex()).getLeaveId();
                        DataRequest req=new DataRequest();
                        req.add("id",leaveInfoId);
                        DataResponse response=HttpRequestUtil.request("/api/leaveInfo/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            LeaveInfo_Manage_Controller.updateDataTableView();
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
