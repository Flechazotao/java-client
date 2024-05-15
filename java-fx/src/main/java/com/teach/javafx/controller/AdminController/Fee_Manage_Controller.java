package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.FeeInfo;
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

public class Fee_Manage_Controller extends manage_MainFrame_controller {
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
    private TableColumn<FeeInfo, String> deleteCol;
    @FXML
    private TableColumn<FeeInfo, String> changeCol;

    @FXML
    private TextField InquireField;
    @FXML
    private Button onAddFee;
    @FXML
    private Button onInquire;


    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res= HttpRequestUtil.request("/api/fee/findByStudent",req);
        feeList= JSON.parseArray(JSON.toJSONString(res.getData()), Fee.class);
        setDataTableView(feeList);
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
        changeCol.setCellFactory(new FM_ButtonCellFactory<>("修改"));
        deleteCol.setCellFactory(new FM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<FeeInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(feeList);
    }

    @FXML
    void onAddFee(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/FeeAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加日常活动信息");
        stage.show();
    }

}



class FM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public FM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        Fee_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/FeeChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改消费信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer feeId= Fee_Manage_Controller.getFeeList().get(getIndex()).getFeeId();
                        DataRequest req=new DataRequest();
                        req.add("id",feeId);
                        DataResponse response=HttpRequestUtil.request("/api/fee/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            Fee_Manage_Controller.updateDataTableView();
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