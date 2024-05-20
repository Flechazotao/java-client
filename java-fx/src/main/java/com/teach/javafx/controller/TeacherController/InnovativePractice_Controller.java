package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InnovativePractice_Controller extends Teacher_MainFrame_controller {
    @FXML
    private TableView<InnovativePractice> dataTableView;
    @FXML
    private TableColumn<InnovativePractice, String> activityNameColumn;
    @FXML
    private TableColumn<InnovativePractice, String> beginTimeColumn;
    @FXML
    private TableColumn<InnovativePractice, String>endTimeColumn;
    @FXML
    private TableColumn<InnovativePractice, String>fileColumn;
    @FXML
    private TableColumn<InnovativePractice, String> studentColumn;
    @FXML
    private TableColumn<InnovativePractice, String>teacherNameColumn;
    @FXML
    private TableColumn<InnovativePractice, String>typeColumn;
    @FXML
    private TableColumn<InnovativePractice, String> achievementColumn;

    @FXML
    private TextField InquireField;

    @Getter
    private static List<InnovativePractice> innovativePracticeList = new ArrayList<>();

    private static ObservableList<InnovativePractice> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<InnovativePractice> list){
        innovativePracticeList=list;
        observableList.clear();
        for(InnovativePractice innovativePractice:innovativePracticeList){
            observableList.addAll(FXCollections.observableArrayList(innovativePractice));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),InnovativePractice.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        innovativePracticeList= JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievement"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

        TableView.TableViewSelectionModel<InnovativePractice> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(innovativePracticeList);
    }

    @FXML
    public void onAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_Addition.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加创新实践信息");
        stage.show();
    }


    public void onInquire() {
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res=HttpRequestUtil.request("/api/innovativePractice/findByStudent",req);
        innovativePracticeList=JSON.parseArray(JSON.toJSONString(res.getData()),InnovativePractice.class);
        setDataTableView(innovativePracticeList);
    }

}
