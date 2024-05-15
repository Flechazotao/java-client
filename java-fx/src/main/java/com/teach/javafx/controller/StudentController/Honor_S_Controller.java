package com.teach.javafx.controller.StudentController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.likeUseless.LoginController;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HonorInfoInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Honor_S_Controller extends student_MainFrame_controller {

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

    private static ObservableList<HonorInfoInfo> observableList= FXCollections.observableArrayList();


    private static String studentid= LoginController.getNumber();

    public void initialize(){
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res= HttpRequestUtil.request("/api/honorInfo/findByStudent",req);
        honorInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HonorInfo.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        honorNameColumn.setCellValueFactory(new PropertyValueFactory<>("honorName"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        honorFromColumn.setCellValueFactory(new PropertyValueFactory<>("honorFrom"));
        honorTimeColumn.setCellValueFactory(new PropertyValueFactory<>("honorTime"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

        TableView.TableViewSelectionModel<HonorInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(honorInfoList);
    }

    public static void updateDataTableView(){
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res= HttpRequestUtil.request("/api/honorInfo/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),HonorInfo.class));
    }

    public static void setDataTableView(List<HonorInfo> list){
        honorInfoList=list;
        observableList.clear();
        for(HonorInfo honorInfo:honorInfoList){
            observableList.addAll(FXCollections.observableArrayList(new HonorInfoInfo(honorInfo)));
        }
    }

}
