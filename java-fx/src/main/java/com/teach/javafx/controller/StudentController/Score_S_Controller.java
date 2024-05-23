package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Score_S_Controller extends student_MainFrame_controller{

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
    public TableColumn<ScoreInfo,String> markColumn;
    @FXML
    public TableColumn<ScoreInfo,String> markPointColumn;
    @FXML
    public TableColumn<ScoreInfo,String> rankingColumn;
    @FXML
    public TextField InquireField;
    @FXML
    public Button onInquire;

//    //    根据学号和(课程编号或名称)查询
//    @PostMapping("/findByStudentIdAndNumName")
//    public DataResponse findByStudentIdAndNumName(@RequestBody DataRequest dataRequest){
//        return scoreService.findByStudentIdAndNumName(JsonUtil.parse(dataRequest.get("studentId"), Long.class),JsonUtil.parse(dataRequest.get("numName"), String.class));
//    }
    @FXML
    void onInquire(ActionEvent event){
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("studentId",LoginController.getNumber());
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/score/findByStudentIdAndNumName",req);
            scoreList=JSON.parseArray(JSON.toJSONString(res.getData()), Score.class);
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
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/score/findByStudentId",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Score.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/score/findByStudentId",req);
        scoreList= JSON.parseArray(JSON.toJSONString(res.getData()), Score.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        wayOfTest.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        markPointColumn.setCellValueFactory(new PropertyValueFactory<>("markPoint"));
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));

        TableView.TableViewSelectionModel<ScoreInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(scoreList);
    }
}

