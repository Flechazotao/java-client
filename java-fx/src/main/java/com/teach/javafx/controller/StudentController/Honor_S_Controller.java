package com.teach.javafx.controller.StudentController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.HonorChangeController;
import com.teach.javafx.controller.AdminController.HonorManageController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HonorInfoInfo;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Honor_S_Controller extends student_MainFrame_controller {

    @FXML
    private TextField InquireField;

    @FXML
    private TableView<HonorInfoInfo> dataTableView;


    @FXML
    private TableColumn<HonorInfoInfo, String> fileColumn;

    @FXML
    private TableColumn<HonorInfoInfo, String>honorFromColumn;

    @FXML
    private TableColumn<HonorInfoInfo, String> honorNameColumn;

    @FXML
    private TableColumn<HonorInfoInfo, String>honorTimeColumn;

    @FXML
    private TableColumn<HonorInfoInfo, String> levelColumn;

    @FXML
    private TableColumn<HonorInfoInfo, String>typeColumn;

    @FXML
    private Button onInquire;

    @Getter
    private static List<HonorInfo> honorInfoList = new ArrayList<>();

    private static ObservableList<HonorInfoInfo> observableList= FXCollections.observableArrayList();


    private static String studentid= LoginController.getNumber();

    public void initialize(){
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res= HttpRequestUtil.request("/api/honorInfo/findByStudent",req);
        honorInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HonorInfo.class);
        dataTableView.setItems(observableList);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        honorNameColumn.setCellValueFactory(new PropertyValueFactory<>("honorName"));
        levelColumn.setCellValueFactory(new PropertyValueFactory<>("level"));
        honorFromColumn.setCellValueFactory(new PropertyValueFactory<>("honorFrom"));
        honorTimeColumn.setCellValueFactory(new PropertyValueFactory<>("honorTime"));
        fileColumn.setCellFactory(new SHM_ButtonCellFactory<>("下载文件"));

        TableView.TableViewSelectionModel<HonorInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(honorInfoList);
    }

    public static void updateDataTableView(){
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res= HttpRequestUtil.request("/api/honorInfo/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),HonorInfo.class));
    }

    public static void setDataTableView(List<HonorInfo> list){
        honorInfoList=list;
        observableList.clear();
        for(HonorInfo honorInfo:honorInfoList){
            observableList.addAll(FXCollections.observableArrayList(new HonorInfoInfo(honorInfo)));
        }
    }

    public void onInquire(ActionEvent actionEvent) {
//        //根据学号和荣誉名称查询
//        @PostMapping("/findByStudentIdAndHonorName")
//        public DataResponse findByStudentIdAndHonorName(@RequestBody DataRequest dataRequest){
//            return honorInfoService.findByStudentIdAndHonorName(JsonUtil.parse(dataRequest.get("id"),Long.class),JsonUtil.parse(dataRequest.get("name"), String.class));
//        }
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        req.add("name",InquireField.getText());
        DataResponse res= HttpRequestUtil.request("/api/honorInfo/findByStudentIdAndHonorName",req);
        honorInfoList= JSON.parseArray(JSON.toJSONString(res.getData()), HonorInfo.class);
        setDataTableView(honorInfoList);

    }
}
class SHM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public SHM_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);
            {
                button.setOnAction(event -> {
                    FXMLLoader fxmlLoader = null;
                    if (Objects.equals(property, "下载文件")) {
                        String url = Honor_S_Controller.getHonorInfoList().get(getIndex()).getFile();
                        if(url==null){
                            MessageDialog.showDialog("未上传文件!");
                            return;
                        }
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
