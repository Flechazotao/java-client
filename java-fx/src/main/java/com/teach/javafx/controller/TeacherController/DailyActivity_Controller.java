package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
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

public class DailyActivity_Controller extends Teacher_MainFrame_controller {

    @FXML
    private TableView<DailyActivity> dataTableView;
    @FXML
    private TableColumn<DailyActivity, String> activityTypeColumn;
    @FXML
    private TableColumn<DailyActivity, String>nameColumn;
    @FXML
    private TableColumn<DailyActivity, String>beginTimeColumn;
    @FXML
    private TableColumn<DailyActivity, String>endTimeColumn;
    @FXML
    private TableColumn<DailyActivity, String>locationColumn;
    @FXML
    private TableColumn<DailyActivity, String>studentColumn;

    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;

    @FXML
    private CheckBox findByStudent;
    @FXML
    private CheckBox findByType;
    @FXML
    public CheckBox findByName;
    @FXML
    private ComboBox<String>typeField;

    public static String[]typelists={"聚会","旅游","文艺演出","体育活动"};

    @FXML
    void onInquire(ActionEvent event){

        if (findByStudent.isSelected()){
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
            List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<DailyActivity> newdailyActivityList = new ArrayList<>();
            for (Student s:studentList){
                List<DailyActivity> Lists = new ArrayList<>();
                DataRequest request=new DataRequest();
                request.add("id",s.getStudentId());
                DataResponse response= HttpRequestUtil.request("/api/dailyActivity/findByStudent",request);
                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), DailyActivity.class);
                newdailyActivityList.addAll(Lists);
            }
            setDataTableView(newdailyActivityList);
        }
        else if (findByType.isSelected()) {
            String query=typeField.getValue();
            DataRequest req=new DataRequest();
            req.add("type",query);
            DataResponse res=HttpRequestUtil.request("/api/dailyActivity/findByType",req);
            dailyActivityList=JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);
            setDataTableView(dailyActivityList);
        }
        else if (findByName.isSelected()) {
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("name",query);
            DataResponse res=HttpRequestUtil.request("/api/dailyActivity/findByName",req);
            dailyActivityList=JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);
            setDataTableView(dailyActivityList);
        }
    }

    @Getter
    private static List<DailyActivity> dailyActivityList = new ArrayList<>();

    private static ObservableList<DailyActivity> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<DailyActivity> list){
        dailyActivityList=list;
        observableList.clear();
        for(DailyActivity dailyActivity:dailyActivityList){
            observableList.addAll(FXCollections.observableArrayList(dailyActivity));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),DailyActivity.class));
    }

    public void initialize() {
        findByStudent.setSelected(true);

        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findAll",new DataRequest());
        dailyActivityList= JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);

        activityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableView.TableViewSelectionModel<DailyActivity> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(dailyActivityList);

        //添加活动类型下拉框
        for (String s:typelists)
            typeField.getItems().add(s);
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
        findByStudent.setSelected(false);
        findByName.setSelected(false);

        InquireField.setVisible(true);
        typeField.setVisible(false);

        if (!(findByStudent.isSelected()&&findByType.isSelected()))
            findByName.setSelected(true);
    }
}
