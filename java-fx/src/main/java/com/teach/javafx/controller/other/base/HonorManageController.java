package com.teach.javafx.controller.other.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.HomeworkInfo_Manage_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.HonorInfoInfo;
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

public class HonorManageController extends manage_MainFrame_controller {
    @FXML
    public CheckBox findByStudent;
    @FXML
    public CheckBox findByName;
    @FXML
    private TableView <HonorInfoInfo> dataTableView;
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
    private TableColumn<HonorInfoInfo, String> changeColumn;
    @FXML
    private TableColumn<HonorInfoInfo, String> deleteColumn;

    @FXML
    private TextField InquireField;

    @FXML
    private Button onAdd;
    @FXML
    private Button onInquire;



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
        fileColumn.setCellFactory(new HM_ButtonCellFactory<>("下载文件"));
        changeColumn.setCellFactory(new HM_ButtonCellFactory<>("修改"));
        deleteColumn.setCellFactory(new HM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<HonorInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(honorInfoList);
    }

    @FXML
    void onAdd(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HonorAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加荣誉信息");
        stage.show();
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



class HM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public HM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        HonorChangeController.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HonorChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改荣誉信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer honorInfoId=HonorManageController.getHonorInfoList().get(getIndex()).getHonorId();
                        DataRequest req=new DataRequest();
                        req.add("id",honorInfoId);
                        DataResponse response=HttpRequestUtil.request("/api/honorInfo/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            HonorManageController.updateDataTableView();
                        }
                    }else if (Objects.equals(property, "下载文件")) {
                        String url = HonorManageController.getHonorInfoList().get(getIndex()).getFile();
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