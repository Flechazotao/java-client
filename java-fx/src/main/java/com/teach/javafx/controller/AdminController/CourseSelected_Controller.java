package com.teach.javafx.controller.AdminController;


import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.*;
import com.teach.javafx.models.DTO.SelectedCourseInfoInfo;
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

public class CourseSelected_Controller extends manage_MainFrame_controller {
    @FXML
    private TextField InquireField;

    @FXML
    private TableView<SelectedCourseInfoInfo> dataTableView;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> change;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> delete;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseBeginWeek;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseName;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseNumber;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseTime;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> credit;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> locationColumn;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> maxSelectedColumn;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> nowSelectNumberColumn;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> teacherName;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> type;

    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> wayOfTest;

    @FXML
    private Button onAddCourse;

    @FXML
    private Button onInquire;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

    private static ObservableList<SelectedCourseInfoInfo> observableList= FXCollections.observableArrayList();

    public void initialize(){

        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        selectedCourseInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);

        courseNumber.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseName.setCellValueFactory(new PropertyValueFactory<>("name"));
        credit.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTime.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherName.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeek.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTest.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("courseLocation"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        nowSelectNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSelected"));
        maxSelectedColumn.setCellValueFactory(new PropertyValueFactory<>("MaxNumberOfSelected"));
        change.setCellFactory(new CS_ButtonCellFactory<>("修改"));
        delete.setCellFactory(new CS_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<SelectedCourseInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseInfoList);
    }

    public static void setDataTableView(List<SelectedCourseInfo> list){
        selectedCourseInfoList=list;
        observableList.clear();
        for(SelectedCourseInfo selectedCourseInfo:selectedCourseInfoList){
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseInfoInfo(selectedCourseInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),SelectedCourseInfo.class));
    }



    public void onInquire(ActionEvent actionEvent) {


    }

    public void onAddCourse(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelected-Addition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加选课信息");
        stage.show();
    }

    public void onReturn(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseManage_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((CourseManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
class CS_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public CS_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        CourseSelected_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelected-Change.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改选课信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer selectedCourseInfoId= CourseSelected_Controller.getSelectedCourseInfoList().get(getIndex()).getSelectedCourseInfoId();
                        DataRequest req=new DataRequest();
                        req.add("id",selectedCourseInfoId);
                        DataResponse response= HttpRequestUtil.request("/api/selectedCourseInfo/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            CourseSelected_Controller.updateDataTableView();
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

