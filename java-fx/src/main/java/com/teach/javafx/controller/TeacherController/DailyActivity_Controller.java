package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class DailyActivity_Controller extends Teacher_MainFrame_controller {

    @FXML
    private TableView<DailyActivity> dataTableView;
    @FXML
    private TableColumn<DailyActivity, String> activityTypeColumn;
    @FXML
    private TableColumn<DailyActivity, String>nameColumn;
    @FXML
    private TableColumn<DailyActivity, String>beginTimeColumn;
    @FXML
    private TableColumn<DailyActivity, String>endTimeColumn;
    @FXML
    private TableColumn<DailyActivity, String>locationColumn;
    @FXML
    private TableColumn<DailyActivity, String>studentColumn;
    @FXML
    private TableColumn<DailyActivity, String>changeCol;
    @FXML
    private TableColumn<DailyActivity, String>deleteCol;
    @FXML
    private Button onAddDailyActivity;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;

    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res= HttpRequestUtil.request("/api/dailyActivity/findByStudent",req);
        dailyActivityList= JSON.parseArray(JSON.toJSONString(res.getData()),DailyActivity.class);
        setDataTableView(dailyActivityList);
    }

    @Getter
    private static List<DailyActivity> dailyActivityList = new ArrayList<>();

    private static ObservableList<DailyActivity> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<DailyActivity> list){
        dailyActivityList=list;
        observableList.clear();
        for(DailyActivity dailyActivity:dailyActivityList){
            observableList.addAll(FXCollections.observableArrayList(dailyActivity));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),DailyActivity.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findAll",new DataRequest());
        dailyActivityList= JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);

        activityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableView.TableViewSelectionModel<DailyActivity> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(dailyActivityList);
    }
}
