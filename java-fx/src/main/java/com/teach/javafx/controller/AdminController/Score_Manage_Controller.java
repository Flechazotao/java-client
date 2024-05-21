package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.Score;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.ScoreInfo;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Score_Manage_Controller extends manage_MainFrame_controller {
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
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res= HttpRequestUtil.request("/api/score/findByStudentId",req);
        scoreList= JSON.parseArray(JSON.toJSONString(res.getData()), Score.class);
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
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/score/findAll",new DataRequest());
        scoreList= JSON.parseArray(JSON.toJSONString(res.getData()), Score.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        wayOfTest.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentNumberColumn.setCellValueFactory(new PropertyValueFactory<>("studentNumber"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));
        markPointColumn.setCellValueFactory(new PropertyValueFactory<>("markPoint"));
        rankingColumn.setCellValueFactory(new PropertyValueFactory<>("ranking"));
        changeColumn.setCellFactory(new ScoreM_ButtonCellFactory<>("修改"));
        deleteColumn.setCellFactory(new ScoreM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<ScoreInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(scoreList);
    }

    @FXML
    void onAdd(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/ScoreAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加成绩信息");
        stage.show();
    }

}



class ScoreM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public ScoreM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        Score_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/ScoreChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改成绩信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer scoreId=Score_Manage_Controller.getScoreList().get(getIndex()).getScoreId();
                        DataRequest req=new DataRequest();
                        req.add("id",scoreId);
                        DataResponse response=HttpRequestUtil.request("/api/score/deleteByScoreId",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            Score_Manage_Controller.updateDataTableView();
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
