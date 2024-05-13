package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
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
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InnovativePracticeController extends manage_MainFrame_controller {

    @FXML
    private TextField InquireField;

    @FXML
    private TableColumn<InnovativePractice, String> achievementColumn;

    @FXML
    private TableColumn<InnovativePractice, String> activityNameColumn;

    @FXML
    private TableColumn<InnovativePractice, String> beginTimeColumn;

    @FXML
    private TableColumn<InnovativePractice, String>changeColumn;

    @FXML
    private TableView<InnovativePractice> dataTableView;

    @FXML
    private TableColumn<InnovativePractice, Void> deleteColumn;

    @FXML
    private TableColumn<InnovativePractice, Void>endTimeColumn;

    @FXML
    private TableColumn<InnovativePractice, String>fileColumn;

    @FXML
    private Button onAdd;


    @FXML
    private TableColumn<InnovativePractice, String> studentColumn;

    @FXML
    private TableColumn<InnovativePractice, String>teacherNameColumn;

    @FXML
    private TableColumn<InnovativePractice, String>typeColumn;

    @FXML
    void onAdd(ActionEvent event) {

    }

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
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        innovativePracticeList= JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("student"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievement"));
        fileColumn.setCellValueFactory(new PropertyValueFactory<>("file"));
        changeColumn.setCellFactory(new IPM_ButtonCellFactory<>("删除"));
        deleteColumn.setCellFactory(new IPM_ButtonCellFactory<>("修改"));

        TableView.TableViewSelectionModel<InnovativePractice> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(innovativePracticeList);
    }

    protected void changeStudentInfo() {
        InnovativePractice innovativePractice = dataTableView.getSelectionModel().getSelectedItem();
        if(innovativePractice == null) {
            clearPanel();
            return;
        }
    }

    public void clearPanel(){
        innovativePracticeList.clear();
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

    @FXML
    public void onInquire() {
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("id",query);
        DataResponse res=HttpRequestUtil.request("/api/innovativePractice/findByStudent",req);
        innovativePracticeList=JSON.parseArray(JSON.toJSONString(res.getData()),InnovativePractice.class);
        setDataTableView(innovativePracticeList);
    }

    class IPM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Getter
        @Setter
        private Long studentId=null;

        @Getter
        @Setter
        private Integer innovativeId;

        @Getter
        private static Student student;

        @Getter
        private static Integer index;
        private final String property;
        public IPM_ButtonCellFactory(@NamedArg("property") String var1) {
            this.property = var1;
        }
        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<S, T>() {
                private Button button = new Button(property);
                {
                    button.setOnAction(event -> {

                        FXMLLoader fxmlLoader = null;

                        if (property=="删除"){
                            int ret = MessageDialog.choiceDialog("确认要删除吗?");
                            if(ret != MessageDialog.CHOICE_YES) {
                                return;
                            }
                            DataRequest req = new DataRequest();
                            innovativeId=innovativePracticeList.get(getIndex()).getInnovativeId();
                            req.add("id", innovativeId);
                            DataResponse res = HttpRequestUtil.request("/api/innovativePractice/deleteById",req);
                            if(res.getCode() == 401) {
                                MessageDialog.showDialog("信息不完整!");
                            }
                            else if (res.getCode() == 404){
                                MessageDialog.showDialog("该学生不存在!");
                            }
                            else {
                                MessageDialog.showDialog("删除成功!");
                                updateDataTableView();
                            }
                        }

                        else if (property=="修改") {
                            StudentChange_Controller.setIndex(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_Change.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 600, 677);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("修改创新实践信息");
                            stage.show();
                        }

                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load(), 1255, 714);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("查看信息");
                        stage.show();

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
}
