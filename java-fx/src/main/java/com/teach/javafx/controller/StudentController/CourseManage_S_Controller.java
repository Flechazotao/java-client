package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.CourseManageController;
import com.teach.javafx.controller.AdminController.Course_Change_Controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.CourseSelectedS_Controller;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.SelectedCourse;
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

public class CourseManage_S_Controller extends student_MainFrame_controller{
    public CheckBox findByCourseType;
    public CheckBox findByNumName;
    public CheckBox findByTeacherName;
    @FXML
    private TableView<SelectedCourseInfoInfo> dataTableView;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> creditColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseTimeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> teacherNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseBeginWeekColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> wayOfTestColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> locationColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> typeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> fileColumn;
    @FXML
    private ComboBox<String> typeField;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;
    @FXML
    private Button inSelectingCourse;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();
    private static String[] typelist = {"必修", "选修", "通选", "限选", "任选"};

    private static ObservableList<SelectedCourseInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<SelectedCourseInfo> list){
        selectedCourseInfoList =list;
        observableList.clear();
        for(SelectedCourseInfo selectedCourseInfo: selectedCourseInfoList){
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseInfoInfo(selectedCourseInfo)));
        }
    }

    public static void updateDataTableView(){
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findByStudentId",req);
        List<SelectedCourse> lists = JSON.parseArray(JSON.toJSONString(res.getData()),SelectedCourse.class);
        List<SelectedCourseInfo> newlists = new ArrayList<>();
        for (SelectedCourse selectedCourse:lists)
            newlists.add(selectedCourse.getSelectedCourseInfo());
        selectedCourseInfoList=newlists;
        setDataTableView(selectedCourseInfoList);
    }

    public void initialize() {
        InquireField.setPromptText("请输入学号或学生姓名");
        findByNumName.setSelected(true);

        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findByStudentId",req);
        List<SelectedCourse> lists = JSON.parseArray(JSON.toJSONString(res.getData()),SelectedCourse.class);
        List<SelectedCourseInfo> newlists = new ArrayList<>();
        for (SelectedCourse selectedCourse:lists)
            newlists.add(selectedCourse.getSelectedCourseInfo());
        selectedCourseInfoList=newlists;

        dataTableView.setItems(observableList);
        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeekColumn.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        fileColumn.setCellFactory(new SCM_ButtonCellFactory<>("下载文件"));

        TableView.TableViewSelectionModel<SelectedCourseInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseInfoList);

        //展示课程类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }
    }
    public void inSelectingCourse(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelect-S.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((CourseSelectedS_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public void onInquire(ActionEvent actionEvent) {
        if (findByCourseType.isSelected()){
            String query = typeField.getValue();
            DataRequest req = new DataRequest();
            req.add("courseType", query);
            req.add("studentId", LoginController.getNumber());
            DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findByStudentIdAndCourseType", req);
            selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);
            setDataTableView(selectedCourseInfoList);
        }
        else if (findByNumName.isSelected()) {
            String query = InquireField.getText();
            DataRequest req = new DataRequest();
            req.add("numName", query);
            req.add("studentId", LoginController.getNumber());
            DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findByStudentIdAndNumName", req);
            selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);
            setDataTableView(selectedCourseInfoList);
        }
        else if (findByTeacherName.isSelected()) {
            String query = InquireField.getText();
            DataRequest req = new DataRequest();
            req.add("teacherName", query);
            req.add("studentId", LoginController.getNumber());
            DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findByStudentIdAndTeacherName", req);
            selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);
            setDataTableView(selectedCourseInfoList);
        }
    }

    public void findByCourseType(ActionEvent actionEvent) {
        typeField.setPromptText("请选择课程类型");
        findByNumName.setSelected(false);
        findByTeacherName.setSelected(false);

        if (!(findByTeacherName.isSelected()&&findByNumName.isSelected()))
            findByCourseType.setSelected(true);

        typeField.setVisible(true);
        InquireField.setVisible(false);
    }

    public void findByNumName(ActionEvent actionEvent) {
        InquireField.setPromptText("请输入学号或学生姓名");
        findByCourseType.setSelected(false);
        findByTeacherName.setSelected(false);

        if (!(findByTeacherName.isSelected()&&findByCourseType.isSelected()))
            findByNumName.setSelected(true);

        typeField.setVisible(false);
        InquireField.setVisible(true);
    }

    public void findByTeacherName(ActionEvent actionEvent) {
        InquireField.setPromptText("请输入教师姓名");
        findByCourseType.setSelected(false);
        findByNumName.setSelected(false);

        if (!(findByNumName.isSelected()&&findByCourseType.isSelected()))
            findByTeacherName.setSelected(true);

        typeField.setVisible(false);
        InquireField.setVisible(true);
    }
}
class SCM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public SCM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        String url = CourseManage_S_Controller.getSelectedCourseInfoList().get(getIndex()).getCourse().getFile();
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