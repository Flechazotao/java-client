package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
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

public class InnovativePractice_Controller extends Teacher_MainFrame_controller {
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
    private TableColumn<InnovativePractice, String> studentColumn;
    @FXML
    private TableColumn<InnovativePractice, String>teacherNameColumn;
    @FXML
    private TableColumn<InnovativePractice, String>typeColumn;
    @FXML
    private TableColumn<InnovativePractice, String> achievementColumn;

    @FXML
    private TextField InquireField;
    @FXML
    private CheckBox findByStudent;
    @FXML
    private CheckBox findByType;
    @FXML
    private CheckBox findByName;
    @FXML
    private ComboBox<String>typeField;


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
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),InnovativePractice.class));
    }

    public void initialize() {

        findByStudent.setSelected(true);
        //添加类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }
        typeField.setVisibleRowCount(5);


        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        innovativePracticeList= JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievement"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));

        TableView.TableViewSelectionModel<InnovativePractice> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(innovativePracticeList);
    }

    @FXML
    public void onAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_Addition.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加创新实践信息");
        stage.show();
    }


    public void onInquire() {
        if (findByStudent.isSelected()) {
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
            List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<InnovativePractice> newinnovativePracticeList = new ArrayList<>();
            for (Student s:studentList){
                List<InnovativePractice> Lists = new ArrayList<>();
                DataRequest request=new DataRequest();
                request.add("id",s.getStudentId());
                DataResponse response= HttpRequestUtil.request("/api/innovativePractice/findByStudent",request);
                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), InnovativePractice.class);
                newinnovativePracticeList.addAll(Lists);
            }
            setDataTableView(newinnovativePracticeList);
        }
        else if (findByName.isSelected()){
            String query = InquireField.getText();
            DataRequest req1 = new DataRequest();
            req1.add("name", query);
            DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByName", req1);
            innovativePracticeList = JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);
            setDataTableView(innovativePracticeList);
        }
        else if (findByType.isSelected()){
            String query = typeField.getValue();
            DataRequest req1 = new DataRequest();
            req1.add("type", query);
            DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByType", req1);
            innovativePracticeList = JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);
            setDataTableView(innovativePracticeList);
        }
    }
    public void findByStudent(ActionEvent actionEvent) {
        findByType.setSelected(false);
        findByName.setSelected(false);

        InquireField.setVisible(true);
        typeField.setVisible(false);

        if (!(findByType.isSelected()&&findByName.isSelected()))
            findByStudent.setSelected(true);
    }

    public void findByType(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        findByName.setSelected(false);


        InquireField.setVisible(false);
        typeField.setVisible(true);

        if (!(findByStudent.isSelected()&&findByName.isSelected()))
            findByType.setSelected(true);
    }

    public void findByName(ActionEvent actionEvent) {
        findByType.setSelected(false);
        findByStudent.setSelected(false);

        InquireField.setVisible(true);
        typeField.setVisible(false);

        if (!(findByType.isSelected()&&findByStudent.isSelected()))
            findByName.setSelected(true);
    }

}
