package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.InnovativePractice_Change_Controller;
import com.teach.javafx.controller.AdminController.InnovativePractice_Manage_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.models.DO.InnovativePractice;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
        fileColumn.setCellFactory(new  SIPM_ButtonCellFactory<>("下载文件"));


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
class SIPM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Getter
    @Setter
    private Long studentId=null;

    @Getter
    @Setter
    private Integer innovativeId;

    private final String property;
    public SIPM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        String url = InnovativePractice_S_Controller.getInnovativePracticeList().get(getIndex()).getFile();
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
                    else{//不知道这里有啥用
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load(), 1255, 714);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("查看信息");
                        stage.show();
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