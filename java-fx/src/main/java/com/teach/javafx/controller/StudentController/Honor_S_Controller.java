package com.teach.javafx.controller.StudentController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Honor_S_Controller extends manage_MainFrame_controller {

    @FXML
    private TextField InquireField;

    @FXML
    private TableView<HonorInfo> dataTableView;

    @FXML
    private AnchorPane deleteCol;

    @FXML
    private TableColumn<HonorInfo, String> fileColumn;

    @FXML
    private TableColumn<HonorInfo, String> honorFromColumn;

    @FXML
    private TableColumn<HonorInfo, String> honorNameColumn;

    @FXML
    private TableColumn<HonorInfo, String> honorTimeColumn;

    @FXML
    private TableColumn<HonorInfo, String> levelColumn;

    @FXML
    private TableColumn<HonorInfo, String> typeColumn;

    @FXML
    private MenuItem onAttendance;

    @FXML
    private MenuItem onChangePassword;

    @FXML
    private MenuItem onCourseManage;

    @FXML
    private Button onDailyActivity;

    @FXML
    private MenuItem onExit;

    @FXML
    private Button onInquire;

    @Getter
    private static List<HonorInfo> honorInfoList = new ArrayList<>();


    public void initialize(){


    }
    public void onInquire(ActionEvent actionEvent) {
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res= HttpRequestUtil.request("/api/honorInfo/findByStudent",req);
        honorInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HonorInfo.class);
//        setDataTableView(honorInfoList);
    }
}
