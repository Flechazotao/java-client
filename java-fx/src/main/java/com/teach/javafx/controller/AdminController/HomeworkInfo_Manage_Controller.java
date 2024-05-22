package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DO.HomeworkInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.FeeInfo;
import com.teach.javafx.models.DTO.HomeworkInfoView;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.application.Platform;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.*;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HomeworkInfo_Manage_Controller extends manage_MainFrame_controller {

    @FXML
    public TableView<HomeworkInfoView> dataTableView;
    @FXML
    public TableColumn<HomeworkInfoView,String> homeworkInfoIdColumn;
    @FXML
    public TableColumn<HomeworkInfoView,String> courseNumberColumn;
    @FXML
    public TableColumn<HomeworkInfoView,String> courseNameColumn;
    @FXML
    public TableColumn<HomeworkInfoView,String> homeworkNameColumn;
    @FXML
    public TableColumn<HomeworkInfoView,String> fileColumn;
    @FXML
    public TableColumn<HomeworkInfoView,String> changeColumn;
    @FXML
    public TableColumn<HomeworkInfoView,String> deleteColumn;
    @FXML
    public Button onInquire;
    @FXML
    public TextField InquireField;
    @FXML
    public Button onAdd;
    @FXML
    public Button onReturn;

    public void onReturn(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Homework_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((Homework_Manage_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res= HttpRequestUtil.request("/api/homeworkInfo/findByCourseId",req);
        homeworkInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HomeworkInfo.class);
        setDataTableView(homeworkInfoList);
    }

    @Getter
    private static List<HomeworkInfo> homeworkInfoList = new ArrayList<>();

    @Getter
    private static List<HomeworkInfoView> homeworkInfoInfoList = new ArrayList<>();

    private static ObservableList<HomeworkInfoView> observableList = FXCollections.observableArrayList();

    public static void setDataTableView(List<HomeworkInfo> list){
        homeworkInfoList=list;
        observableList.clear();
        for(HomeworkInfo homeworkInfo:homeworkInfoList){
            observableList.addAll(FXCollections.observableArrayList(new HomeworkInfoView(homeworkInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/homeworkInfo/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),HomeworkInfo.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/homeworkInfo/findAll",new DataRequest());
        homeworkInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HomeworkInfo.class);

        homeworkInfoIdColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkInfoId"));
        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        homeworkNameColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkName"));
        fileColumn.setCellFactory(new HomeworkInfoM_ButtonCellFactory<>("下载文件"));
        changeColumn.setCellFactory(new HomeworkInfoM_ButtonCellFactory<>("修改"));
        deleteColumn.setCellFactory(new HomeworkInfoM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<HomeworkInfoView> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(homeworkInfoList);
    }

    @FXML
    void onAdd(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeworkInfoAddition.fxml"));
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
}



class HomeworkInfoM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public HomeworkInfoM_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);

            {
                button.setOnAction(event -> {

                    FXMLLoader fxmlLoader = null;

                    if (Objects.equals(property, "修改")) {
                        HomeworkInfo_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeworkInfoChange.fxml"));
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
                    } else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if (ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        DataRequest req1 = new DataRequest();
                        String url=HomeworkInfo_Manage_Controller.getHomeworkInfoList().get(getIndex()).getFile();
                        req1.add("url",url);
                        DataResponse res = HttpRequestUtil.request("/api/file/delete", req1);
                        if(res.getCode()!=200){
                            MessageDialog.showDialog("文件删除失败!");
                            return;
                        }

                        Integer homeworkInfoId = HomeworkInfo_Manage_Controller.getHomeworkInfoList().get(getIndex()).getHomeworkInfoId();
                        DataRequest req = new DataRequest();
                        req.add("id", homeworkInfoId);
                        DataResponse response = HttpRequestUtil.request("/api/homeworkInfo/deleteById", req);

                        if (response.getCode() == 401) {
                            MessageDialog.showDialog("信息不完整!");
                        } else {
                            MessageDialog.showDialog("删除成功!");
                            HomeworkInfo_Manage_Controller.updateDataTableView();
                        }
                    } else if (Objects.equals(property, "下载文件")) {

                        String url = HomeworkInfo_Manage_Controller.getHomeworkInfoList().get(getIndex()).getFile();
                        DataRequest req = new DataRequest();
                        req.add("url", url);
                        String fileName=url.substring(url.lastIndexOf("\\")+1);
                        byte[] fileByte=HttpRequestUtil.requestByteData("/api/file/download", req);

                        if(fileByte==null){
                            MessageDialog.showDialog("下载失败!");
                            return;
                        }

                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save File");
                        fileChooser.setInitialFileName(fileName);
                        Path path = fileChooser.showSaveDialog(null).toPath();
                        if (path != null) {
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(path.toFile());
                                fos.write(fileByte);
                                fos.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            MessageDialog.showDialog("下载成功!");
                            return;
                        }
                        else {
                            MessageDialog.showDialog("下载失败!");
                            return;
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