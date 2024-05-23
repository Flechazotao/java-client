package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class InnovativePractice_S_Controller extends student_MainFrame_controller{
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
    private TableColumn<InnovativePractice, String>teacherNameColumn;
    @FXML
    private TableColumn<InnovativePractice, String>typeColumn;
    @FXML
    private TableColumn<InnovativePractice, String> achievementColumn;
    @FXML
    private ComboBox<String> typeField;


    public static String[]typelist={"社会实践","学科竞赛","科技成果","培训讲座","创新项目","校外实习","志愿服务"};

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
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),InnovativePractice.class));
    }

    public void initialize() {
        //添加类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }
        typeField.setVisibleRowCount(5);

        dataTableView.setItems(observableList);
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByStudent",req);
        innovativePracticeList= JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievement"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));


        TableView.TableViewSelectionModel<InnovativePractice> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(innovativePracticeList);
    }


    public void onquire(ActionEvent actionEvent) {
        String query = typeField.getValue();
        DataRequest req1 = new DataRequest();
        req1.add("type", query);
        req1.add("id",LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByStudentIdAndType", req1);
        innovativePracticeList = JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);
        setDataTableView(innovativePracticeList);

    }
}
