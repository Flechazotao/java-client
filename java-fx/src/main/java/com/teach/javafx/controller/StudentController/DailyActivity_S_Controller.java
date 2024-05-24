package com.teach.javafx.controller.StudentController;
import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.models.DO.DailyActivity;
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

public class DailyActivity_S_Controller extends student_MainFrame_controller{
    @FXML
    private TextField InquireField;

    @FXML
    private TableColumn<DailyActivity, String> activityTypeColumn;

    @FXML
    private TableColumn<DailyActivity, String> beginTimeColumn;

    @FXML
    private TableView<DailyActivity> dataTableView;

    @FXML
    private TableColumn<DailyActivity, String> endTimeColumn;

    @FXML
    private TableColumn<DailyActivity, String> locationColumn;

    @FXML
    private TableColumn<DailyActivity, String> nameColumn;

    @FXML
    private Button onInquire;
    @FXML
    private ComboBox<String> typeField;

    public static String[]typelists={"聚会","旅游","文艺演出","体育活动"};

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

        DataRequest request=new DataRequest();
        request.add("id",LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findByStudent",request);
        dailyActivityList= JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);
        
    }
//    //查找某学生的信息
//    @PostMapping("/findByStudent")
//    public DataResponse findByStudentId(@RequestBody DataRequest dataRequest){
//        return dailyActivityService.findByStudentId(JsonUtil.parse(dataRequest.get("id"), Long.class));
//    }
    public void initialize() {
        dataTableView.setItems(observableList);
        DataRequest request=new DataRequest();
        request.add("id",LoginController.getNumber());
        DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findByStudent",request);
        dailyActivityList= JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);


        activityTypeColumn.setCellValueFactory(new PropertyValueFactory<>("activityType"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        TableView.TableViewSelectionModel<DailyActivity> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(dailyActivityList);

        //添加种类下拉框
        for (String s:typelists)
            typeField.getItems().add(s);

        //未添加其他查询,先让textField不可见
        InquireField.setVisible(false);
        typeField.setVisible(true);
    }

    public void onInquire(ActionEvent actionEvent) {
        if (typeField.isVisible()){
            String query = typeField.getValue();
            DataRequest req = new DataRequest();
            req.add("type", query);
            req.add("id", LoginController.getNumber());
            DataResponse res = HttpRequestUtil.request("/api/dailyActivity/findByStudentIdAndType", req);
            dailyActivityList = JSON.parseArray(JSON.toJSONString(res.getData()), DailyActivity.class);
            setDataTableView(dailyActivityList);
        }
    }
}
