package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.FeeInfo;
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

public class Fee_Controller extends Teacher_MainFrame_controller {

    @FXML
    private TableView<FeeInfo> dataTableView;
    @FXML
    private TableColumn<FeeInfo, String> feeTypeColumn;
    @FXML
    private TableColumn<FeeInfo, Long> numberColumn;
    @FXML
    private TableColumn<FeeInfo, String> studentNameColumn;
    @FXML
    private TableColumn<FeeInfo, String>dayColumn;
    @FXML
    private TableColumn<FeeInfo, Double> moneyColumn;
    @FXML
    private TableColumn<FeeInfo, String>descriptionColumn;

    @FXML
    private TextField InquireField;
    @FXML
    private Button onInquire;


    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numName",query);
        DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
        List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        List<Fee> newfeeList = new ArrayList<>();
        for (Student s:studentList){
            List<Fee> Lists = new ArrayList<>();
            DataRequest request=new DataRequest();
            request.add("id",s.getStudentId());
            DataResponse response= HttpRequestUtil.request("/api/fee/findByStudent",request);
            Lists=JSON.parseArray(JSON.toJSONString(response.getData()), Fee.class);
            newfeeList.addAll(Lists);
        }
        setDataTableView(newfeeList);
    }

    @Getter
    private static List<Fee> feeList = new ArrayList<>();

    @Getter
    private static List<FeeInfo> feeInfoList = new ArrayList<>();

    private static ObservableList<FeeInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Fee> list){
        feeList=list;
        observableList.clear();
        for(Fee fee:feeList){
            observableList.addAll(FXCollections.observableArrayList(new FeeInfo(fee)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/fee/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Fee.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/fee/findAll",new DataRequest());
        feeList= JSON.parseArray(JSON.toJSONString(res.getData()), Fee.class);

        feeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("feeType"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        moneyColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableView.TableViewSelectionModel<FeeInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(feeList);
    }
}
