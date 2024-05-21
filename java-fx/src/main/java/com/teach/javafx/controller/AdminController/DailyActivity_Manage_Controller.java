package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.AttendanceInfo;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
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

public class DailyActivity_Manage_Controller extends manage_MainFrame_controller {

    @FXML
    private TableView<DailyActivity> dataTableView;
    @FXML
    private TableColumn<DailyActivity, String>activityTypeColumn;
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

//        String query=InquireField.getText();
//        DataRequest req=new DataRequest();
//        req.add("id",query);
//        DataResponse res=HttpRequestUtil.request("/api/dailyActivity/findByStudent",req);
//        dailyActivityList=JSON.parseArray(JSON.toJSONString(res.getData()),DailyActivity.class);
//        setDataTableView(dailyActivityList);

        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numName",query);
        DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
        List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
        List<DailyActivity> newdailyActivityList = new ArrayList<>();
        for (Student s:studentList){
            List<DailyActivity> Lists = new ArrayList<>();
            DataRequest request=new DataRequest();
            request.add("id",s.getStudentId());
            DataResponse response= HttpRequestUtil.request("/api/dailyActivity/findByStudent",request);
            Lists=JSON.parseArray(JSON.toJSONString(response.getData()), DailyActivity.class);
            newdailyActivityList.addAll(Lists);
        }
        setDataTableView(newdailyActivityList);
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
        changeCol.setCellFactory(new DA_ButtonCellFactory<>("修改"));
        deleteCol.setCellFactory(new DA_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<DailyActivity> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(dailyActivityList);
    }

    @FXML
    void onAddDailyActivity(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivity_Addition.fxml"));
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



class DA_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public DA_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        DailyActivity_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivity_Change.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改日常活动信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer dailyId= DailyActivity_Manage_Controller.getDailyActivityList().get(getIndex()).getActivityId();
                        DataRequest req=new DataRequest();
                        req.add("id",dailyId);
                        DataResponse response=HttpRequestUtil.request("/api/dailyActivity/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            DailyActivity_Manage_Controller.updateDataTableView();
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
