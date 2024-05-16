package com.teach.javafx.controller.other.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.*;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class CourseSelectedS_Controller extends manage_MainFrame_controller{
    @FXML
    private GridPane GridPane;

    @FXML
    private Button onFridayFifth;

    @FXML
    private Button onFridayFirst;

    @FXML
    private Button onFridayFourth;

    @FXML
    private Button onFridaySecond;

    @FXML
    private Button onFridayThird;

    @FXML
    private Button onMondayFifth;

    @FXML
    private Button onMondayFirst;

    @FXML
    private Button onMondayFourth;

    @FXML
    private Button onMondaySecond;

    @FXML
    private Button onMondayThird;

    @FXML
    private Button onSaturdayFifth;

    @FXML
    private Button onSaturdayFirst;

    @FXML
    private Button onSaturdayFourth;

    @FXML
    private Button onSaturdaySecond;

    @FXML
    private Button onSaturdayThird;

    @FXML
    private Button onSundayFifth;

    @FXML
    private Button onSundayFirst;

    @FXML
    private Button onSundayFourth;

    @FXML
    private Button onSundaySecond;

    @FXML
    private Button onSundayThird;

    @FXML
    private Button onThursdayFifth;

    @FXML
    private Button onThursdayFirst;

    @FXML
    private Button onThursdayFourth;

    @FXML
    private Button onThursdaySecond;

    @FXML
    private Button onThursdayThird;

    @FXML
    private Button onTuesdayFifth;

    @FXML
    private Button onTuesdayFirst;

    @FXML
    private Button onTuesdayFourth;

    @FXML
    private Button onTuesdaySecond;

    @FXML
    private Button onTuesdayThird;

    @FXML
    private Button onWednesdayFifth;

    @FXML
    private Button onWednesdayFirst;

    @FXML
    private Button onWednesdayFourth;

    @FXML
    private Button onWednesdaySecond;

    @FXML
    private Button onWednesdayThird;

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
    private TableColumn<SelectedCourseInfoInfo,String> nowSelectNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> maxSelectedColumn;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

    private static ObservableList<SelectedCourseInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<SelectedCourseInfo> list){
        selectedCourseInfoList =list;
        observableList.clear();
        for(SelectedCourseInfo selectedCourseInfo: selectedCourseInfoList){
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseInfoInfo(selectedCourseInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),SelectedCourseInfo.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
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
        nowSelectNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSelected"));
        maxSelectedColumn.setCellValueFactory(new PropertyValueFactory<>("MaxNumberOfSelected"));


        TableView.TableViewSelectionModel<SelectedCourseInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseInfoList);
    }


    public void MondayFirst(ActionEvent actionEvent) {
    }

    public void TuesdayFirst(ActionEvent actionEvent) {
    }

    public void WednesdayFirst(ActionEvent actionEvent) {
    }

    public void ThursdayFirst(ActionEvent actionEvent) {
    }

    public void FridayFirst(ActionEvent actionEvent) {
    }

    public void SaturdayFirst(ActionEvent actionEvent) {
    }

    public void SaturdaySecond(ActionEvent actionEvent) {
    }

    public void MondaySecond(ActionEvent actionEvent) {
    }

    public void TuesdaySecond(ActionEvent actionEvent) {
    }

    public void WednesdaySecond(ActionEvent actionEvent) {
    }

    public void ThursdaySecond(ActionEvent actionEvent) {
    }

    public void FridaySecond(ActionEvent actionEvent) {
    }

    public void SundayFirst(ActionEvent actionEvent) {
    }

    public void SundaySecond(ActionEvent actionEvent) {
    }

    public void MondayThird(ActionEvent actionEvent) {
    }

    public void TuesdayThird(ActionEvent actionEvent) {
    }

    public void WednesdayThird(ActionEvent actionEvent) {
    }

    public void ThursdayThird(ActionEvent actionEvent) {
    }

    public void FridayThird(ActionEvent actionEvent) {
    }

    public void SaturdayThird(ActionEvent actionEvent) {
    }

    public void MondayFourth(ActionEvent actionEvent) {
    }

    public void SundayThird(ActionEvent actionEvent) {
    }

    public void TuesdayFourth(ActionEvent actionEvent) {
    }

    public void WednesdayFourth(ActionEvent actionEvent) {
    }

    public void ThursdayFourth(ActionEvent actionEvent) {
    }

    public void FridayFourth(ActionEvent actionEvent) {
    }

    public void SaturdayFourth(ActionEvent actionEvent) {
    }

    public void SundayFourth(ActionEvent actionEvent) {
    }

    public void TuesdayFifth(ActionEvent actionEvent) {
    }

    public void WednesdayFifth(ActionEvent actionEvent) {
    }

    public void ThursdayFifth(ActionEvent actionEvent) {
    }

    public void FridayFifth(ActionEvent actionEvent) {
    }

    public void SaturdayFifth(ActionEvent actionEvent) {
    }

    public void SundayFifth(ActionEvent actionEvent) {
    }

    public void MondayFifth(ActionEvent actionEvent) {
    }


}
