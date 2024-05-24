package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.FeeInfo;
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

public class Fee_S_Controller extends student_MainFrame_controller {

    public DatePicker datePicker;
    @FXML
    private TableView<FeeInfo> dataTableView;
    @FXML
    private TableColumn<FeeInfo, String> feeTypeColumn;
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
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/fee/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Fee.class));
    }

    public void initialize() {

        dataTableView.setItems(observableList);
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/fee/findByStudent",req);
        feeList= JSON.parseArray(JSON.toJSONString(res.getData()), Fee.class);

        feeTypeColumn.setCellValueFactory(new PropertyValueFactory<>("feeType"));
        dayColumn.setCellValueFactory(new PropertyValueFactory<>("day"));
        moneyColumn.setCellValueFactory(new PropertyValueFactory<>("money"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

        TableView.TableViewSelectionModel<FeeInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(feeList);
    }
    public void onInquire(ActionEvent actionEvent) {
//        //根据学生id和日期查询
//        @PostMapping("/findByStudentIdAndDay")
//        public DataResponse findByStudentIdAndDay(@RequestBody DataRequest dataRequest){
//            return feeService.findByStudentIdAndDay(JsonUtil.parse(dataRequest.get("id"), Long.class), JsonUtil.parse(dataRequest.get("day"), String.class));
//        }
        DataRequest req=new DataRequest();
        req.add("id",LoginController.getNumber());
        req.add("day", datePicker.getValue().toString());
        DataResponse res= HttpRequestUtil.request("/api/fee/findByStudentIdAndDay",req);
        feeList= JSON.parseArray(JSON.toJSONString(res.getData()), Fee.class);
        setDataTableView(feeList);


    }
}
