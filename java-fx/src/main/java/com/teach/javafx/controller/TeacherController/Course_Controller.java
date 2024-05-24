package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.CourseManageController;
import com.teach.javafx.controller.AdminController.CourseSelected_Controller;
import com.teach.javafx.controller.AdminController.Course_Change_Controller;
import com.teach.javafx.controller.StudentController.CourseManage_S_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
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

public class Course_Controller extends Teacher_MainFrame_controller {
    public TextField inqueryField;
    @FXML
    private TableView<SelectedCourseInfoInfo> dataTableView;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> creditColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseTimeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> teacherNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> courseBeginWeekColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> wayOfTestColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> locationColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> typeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> nowSelectNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> maxSelectedColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo, String> fileColumn;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

    private static ObservableList<SelectedCourseInfoInfo> observableList = FXCollections.observableArrayList();

    public static void setDataTableView(List<SelectedCourseInfo> list) {
        selectedCourseInfoList = list;
        observableList.clear();
        for (SelectedCourseInfo selectedCourseInfo : selectedCourseInfoList) {
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseInfoInfo(selectedCourseInfo)));
        }
    }

    public static void updateDataTableView() {
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll", new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll", new DataRequest());
        selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeekColumn.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        fileColumn.setCellFactory(new TCM_ButtonCellFactory<>("下载文件"));
        nowSelectNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSelected"));
        maxSelectedColumn.setCellValueFactory(new PropertyValueFactory<>("MaxNumberOfSelected"));

        TableView.TableViewSelectionModel<SelectedCourseInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseInfoList);
    }

    public void onquire(ActionEvent actionEvent) {
        //根据课程编号或名称查询
        String query = inqueryField.getText();
        DataRequest req = new DataRequest();
        req.add("numName", query);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findByCourseNumberOrName", req);
        selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);
        setDataTableView(selectedCourseInfoList);

    }
}
class TCM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public TCM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        String url = Course_Controller.getSelectedCourseInfoList().get(getIndex()).getCourse().getFile();
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
