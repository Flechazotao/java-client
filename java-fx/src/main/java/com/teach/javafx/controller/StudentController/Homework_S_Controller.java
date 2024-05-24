package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;

import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.Homework_Change_Controller;
import com.teach.javafx.controller.AdminController.Homework_Manage_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.models.DO.Homework;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HomeworkView;
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

public class Homework_S_Controller extends student_MainFrame_controller{
    @FXML
    public TableView<HomeworkView> dataTableView;
    @FXML
    public TableColumn<HomeworkView,String> courseNumberColumn;
    @FXML
    public TableColumn<HomeworkView,String> courseNameColumn;
    @FXML
    public TableColumn<HomeworkView,String> homeworkNameColumn;
    @FXML
    public TableColumn<HomeworkView,String> isSubmitColumn;
    @FXML
    public TableColumn<HomeworkView,String> submitTimeColumn;
    @FXML
    public TableColumn<HomeworkView,String> isCheckedColumn;
    @FXML
    public TableColumn<HomeworkView,String> checkTimeColumn;
    @FXML
    public TableColumn<HomeworkView,String> fileColumn;
    @FXML
    public TableColumn<HomeworkView,String> homeworkScoreColumn;
    @FXML
    public Button onInquire;
    @FXML
    public TextField InquireField;


    private static String studentid= LoginController.getNumber();

    @Getter
    private static List<Homework> homeworkList = new ArrayList<>();

    private static ObservableList<HomeworkView> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Homework> list){
        homeworkList=list;
        observableList.clear();
        for(Homework homework:homeworkList){
            observableList.addAll(FXCollections.observableArrayList(new HomeworkView(homework)));
        }
    }

    public static void updateDataTableView(){
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res = HttpRequestUtil.request("/api/homework/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Homework.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataRequest req=new DataRequest();
        req.add("id",studentid);
        DataResponse res = HttpRequestUtil.request("/api/homework/findByStudent",req);
        homeworkList= JSON.parseArray(JSON.toJSONString(res.getData()), Homework.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        homeworkNameColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkName"));
        isSubmitColumn.setCellValueFactory(new PropertyValueFactory<>("isSubmit"));
        submitTimeColumn.setCellValueFactory(new PropertyValueFactory<>("submitTime"));
        isCheckedColumn.setCellValueFactory(new PropertyValueFactory<>("isChecked"));
        checkTimeColumn.setCellValueFactory(new PropertyValueFactory<>("checkTime"));
        homeworkScoreColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkScore"));
        fileColumn.setCellFactory(new SCM_ButtonCellFactory<>("下载文件"));

        TableView.TableViewSelectionModel<HomeworkView> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(homeworkList);
    }

    public void onInquire(ActionEvent actionEvent) {

        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numName",query);
        req.add("studentId",LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/homework/findByStudentIdAndNumName",req);
        homeworkList= JSON.parseArray(JSON.toJSONString(res.getData()), Homework.class);
        setDataTableView(homeworkList);

    }
}
class SHomeworkM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public SHomeworkM_ButtonCellFactory(@NamedArg("property") String var1) {
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

                        String url = Homework_S_Controller.getHomeworkList().get(getIndex()).getFile();
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
