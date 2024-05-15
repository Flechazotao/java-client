package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.HonorChangeController;
import com.teach.javafx.controller.other.base.HonorManageController;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HonorInfoInfo;
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

public class CourseManageController extends manage_MainFrame_controller {

    @FXML
    private TableView<Course> dataTableView;
    @FXML
    private TableColumn<Course,String> courseNumberColumn;
    @FXML
    private TableColumn<Course,String> courseNameColumn;
    @FXML
    private TableColumn<Course,String> creditColumn;
    @FXML
    private TableColumn<Course,String> courseTimeColumn;
    @FXML
    private TableColumn<Course,String> teacherNameColumn;
    @FXML
    private TableColumn<Course,String> courseBeginWeekColumn;
    @FXML
    private TableColumn<Course,String> wayOfTestColumn;
    @FXML
    private TableColumn<Course,String> locationColumn;
    @FXML
    private TableColumn<Course,String> typeColumn;
    @FXML
    private TableColumn<Course,String> fileColumn;
    @FXML
    private TableColumn<Course,String> changeColumn;
    @FXML
    private TableColumn<Course,String> deleteColumn;

    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;
    @FXML
    private Button onAddCourse;
    @FXML
    private Button inSelectingCourse;

    public void inSelectingCourse(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelect-S.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 1000, 600);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("选课中心");
        stage.show();
    }

    @FXML
    void onInquire(ActionEvent event){
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numberName",query);
        DataResponse res= HttpRequestUtil.request("/api/course/findByCourseNumberOrName",req);
        courseList= JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);
        setDataTableView(courseList);
    }

    @Getter
    private static List<Course> courseList = new ArrayList<>();

    private static ObservableList<Course> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Course> list){
        courseList=list;
        observableList.clear();
        for(Course course:courseList){
            observableList.addAll(FXCollections.observableArrayList(course));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Course.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/course/findAll",new DataRequest());
        courseList= JSON.parseArray(JSON.toJSONString(res.getData()), Course.class);

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
        changeColumn.setCellFactory(new CM_ButtonCellFactory<>("修改"));
        deleteColumn.setCellFactory(new CM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<Course> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(courseList);
    }

    @FXML
    void onAddCourse(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加课程信息");
        stage.show();
    }

}



class CM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public CM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        Course_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改课程信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer courseId= CourseManageController.getCourseList().get(getIndex()).getCourseId();
                        DataRequest req=new DataRequest();
                        req.add("id",courseId);
                        DataResponse response=HttpRequestUtil.request("/api/course/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            CourseManageController.updateDataTableView();
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
