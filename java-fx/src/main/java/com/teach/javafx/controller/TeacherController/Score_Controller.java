package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.Score;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.ScoreInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import lombok.Getter;


import java.util.ArrayList;
import java.util.List;

public class Score_Controller extends Teacher_MainFrame_controller {

    @FXML
    public TableView<ScoreInfo> dataTableView;
    @FXML
    public TableColumn<ScoreInfo,String> courseNumberColumn;
    @FXML
    public TableColumn<ScoreInfo,String> courseNameColumn;
    @FXML
    public TableColumn<ScoreInfo,String> creditColumn;
    @FXML
    public TableColumn<ScoreInfo,String> teacherColumn;
    @FXML
    public TableColumn<ScoreInfo,String> wayOfTest;
    @FXML
    public TableColumn<ScoreInfo,String> studentNameColumn;
    @FXML
    public TableColumn<ScoreInfo,String> studentNumberColumn;
    @FXML
    public TableColumn<ScoreInfo,String> markColumn;
    @FXML
    public TableColumn<ScoreInfo,String> markPointColumn;
    @FXML
    public TableColumn<ScoreInfo,String> rankingColumn;
    @FXML
    public TableColumn<ScoreInfo,String> changeColumn;
    @FXML
    public TableColumn<ScoreInfo,String> deleteColumn;

    @FXML
    public Button onAdd ;
    @FXML
    public TextField InquireField;
    @FXML
    public Button onInquire;
    @FXML
    public CheckBox findByStudent;
    @FXML
    public CheckBox findByCourseNumberOrName;

    @FXML
    void onInquire(ActionEvent event){
        if (findByStudent.isSelected()){
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
            List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<Score> newscoreList = new ArrayList<>();
            for (Student s:studentList){
                List<Score> Lists = new ArrayList<>();
                DataRequest request=new DataRequest();
                request.add("id",s.getStudentId());
                DataResponse response= HttpRequestUtil.request("/api/score/findByStudentId",request);
                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), Score.class);
                newscoreList.addAll(Lists);
            }
            setDataTableView(newscoreList);
        }
        else if (findByCourseNumberOrName.isSelected()) {
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/score/findByCourseNumberOrName",req);
            scoreList=JSON.parseArray(JSON.toJSONString(res.getData()), Score.class);
        }
        setDataTableView(scoreList);
    }

    @Getter
    private static List<Score> scoreList = new ArrayList<>();

    private static ObservableList<ScoreInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Score> list){
        scoreList=list;
        observableList.clear();
        for(Score score:scoreList){
            observableList.addAll(FXCollections.observableArrayList(new ScoreInfo(score)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/score/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Score.class));
    }

    public void initialize() {
        findByStudent.setSelected(true);
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/score/findAll",new DataRequest());
        scoreList= JSON.parseArray(JSON.toJSONString(res.getData()), Score.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
//        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
//        wayOfTest.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        markPointColumn.setCellValueFactory(new PropertyValueFactory<>("markPoint"));
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));

        TableView.TableViewSelectionModel<ScoreInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(scoreList);
    }
    public void findByStudent(ActionEvent actionEvent) {
        findByCourseNumberOrName.setSelected(false);
        if (!findByCourseNumberOrName.isSelected())
            findByStudent.setSelected(true);
    }

    public void findByCourseNumberOrName(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        if (!findByStudent.isSelected())
            findByCourseNumberOrName.setSelected(true);
    }
}
