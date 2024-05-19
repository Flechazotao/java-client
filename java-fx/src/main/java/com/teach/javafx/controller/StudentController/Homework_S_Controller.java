package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;

import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.likeUseless.LoginController;
import com.teach.javafx.models.DO.Homework;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HomeworkView;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

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
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

        TableView.TableViewSelectionModel<HomeworkView> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(homeworkList);
    }



    public void onInquire(ActionEvent actionEvent) {


    }
}
