package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
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
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Honor_Controller extends Teacher_MainFrame_controller {

    @FXML
    private TableView<HonorInfoInfo> dataTableView;
    @FXML
    private TableColumn<HonorInfoInfo, String> typeColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String> honorNameColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String> studentNameColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String>studentIdColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String> levelColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String> honorFromColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String> honorTimeColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String>fileColumn;

    @FXML
    private TextField InquireField;

    @FXML
    private Button onInquire;
    @FXML
    public CheckBox findByStudent;
    @FXML
    public CheckBox findByName;


    @FXML
    void onInquire(ActionEvent event){
        if (findByStudent.isSelected()) {
            String query = InquireField.getText();
            DataRequest req = new DataRequest();
            req.add("numName", query);
            DataResponse res = HttpRequestUtil.request("/api/student/findByStudentIdOrName", req);
            List<Student> studentList = JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<HonorInfo> newhonorInfoList = new ArrayList<>();
            for (Student s : studentList) {
                List<HonorInfo> Lists = new ArrayList<>();
                DataRequest request = new DataRequest();
                request.add("id", s.getStudentId());
                DataResponse response = HttpRequestUtil.request("/api/honorInfo/findByStudent", request);
                Lists = JSON.parseArray(JSON.toJSONString(response.getData()), HonorInfo.class);
                newhonorInfoList.addAll(Lists);
            }
            setDataTableView(newhonorInfoList);
        }
        else if (findByName.isSelected()){
            String query = InquireField.getText();
            DataRequest req = new DataRequest();
            req.add("name", query);
            DataResponse res = HttpRequestUtil.request("/api/honorInfo/findByName", req);
            honorInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), HonorInfo.class);
            setDataTableView(honorInfoList);
        }
    }

    @Getter
    private static List<HonorInfo> honorInfoList = new ArrayList<>();

    private static ObservableList<HonorInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<HonorInfo> list){
        honorInfoList=list;
        observableList.clear();
        for(HonorInfo honorInfo:honorInfoList){
            observableList.addAll(FXCollections.observableArrayList(new HonorInfoInfo(honorInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/honorInfo/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),HonorInfo.class));
    }

    public void initialize() {
        findByStudent.setSelected(true);

        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/honorInfo/findAll",new DataRequest());
        honorInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HonorInfo.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        honorNameColumn.setCellValueFactory(new PropertyValueFactory<>("honorName"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        honorFromColumn.setCellValueFactory(new PropertyValueFactory<>("honorFrom"));
        honorTimeColumn.setCellValueFactory(new PropertyValueFactory<>("honorTime"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

        TableView.TableViewSelectionModel<HonorInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(honorInfoList);
    }

    public void findByStudent(ActionEvent actionEvent) {
        findByName.setSelected(false);

        if (!findByName.isSelected())
            findByStudent.setSelected(true);
    }

    public void findByName(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        if (!findByStudent.isSelected())
            findByName.setSelected(true);
    }
}
