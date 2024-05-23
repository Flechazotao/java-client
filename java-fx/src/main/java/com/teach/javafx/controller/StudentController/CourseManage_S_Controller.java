package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.controller.other.base.CourseSelectedS_Controller;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.SelectedCourse;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.SelectedCourseInfoInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

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
//        //根据学号和课程种类查询
//        @PostMapping("/findByStudentIdAndCourseType")
//        public DataResponse findByStudentIdAndCourseType(@RequestBody DataRequest dataRequest){
//            return selectedCourseService.findByStudentIdAndCourseType(JsonUtil.parse(dataRequest.get("studentId"), Long.class),JsonUtil.parse(dataRequest.get("courseType"), String.class));
//        }
//
//        //根据学号和(课程编号或名称)查询
//        @PostMapping("/findByStudentIdAndNumName")
//        public DataResponse findByStudentIdAndNumName(@RequestBody DataRequest dataRequest){
//            return selectedCourseService.findByStudentIdAndNumName(JsonUtil.parse(dataRequest.get("studentId"), Long.class),JsonUtil.parse(dataRequest.get("numName"), String.class));
//        }
//
//        //根据学号和教师名称
//        @PostMapping("/findByStudentIdAndTeacherName")
//        public DataResponse findByStudentIdAndTeacherName(@RequestBody DataRequest dataRequest){
//            return selectedCourseService.findByStudentIdAndTeacherName(JsonUtil.parse(dataRequest.get("studentId"), Long.class),JsonUtil.parse(dataRequest.get("teacherName"), String.class));
//        }
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
        findByNumName.setSelected(false);
        findByTeacherName.setSelected(false);

        if (!(findByTeacherName.isSelected()&&findByNumName.isSelected()))
            findByCourseType.setSelected(true);

        typeField.setVisible(true);
        InquireField.setVisible(false);
    }

    public void findByNumName(ActionEvent actionEvent) {
        findByCourseType.setSelected(false);
        findByTeacherName.setSelected(false);

        if (!(findByTeacherName.isSelected()&&findByCourseType.isSelected()))
            findByNumName.setSelected(true);

        typeField.setVisible(false);
        InquireField.setVisible(true);
    }

    public void findByTeacherName(ActionEvent actionEvent) {
        findByCourseType.setSelected(false);
        findByNumName.setSelected(false);

        if (!(findByNumName.isSelected()&&findByCourseType.isSelected()))
            findByTeacherName.setSelected(true);

        typeField.setVisible(false);
        InquireField.setVisible(true);
    }
}
