package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.*;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.SelectedCourseView;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseSelectStudent_Manage_Controller extends manage_MainFrame_controller {

    @FXML
    public CheckBox findByStudent;
    @FXML
    public Button onReturn;
    @FXML
    public Button onAdd;
    @FXML
    private TableView<SelectedCourseView> dataTableView;
    @FXML
    private TableColumn<SelectedCourseView,String> courseNumberColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> courseNameColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> creditColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> courseTimeColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> teacherNameColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> courseBeginWeekColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> wayOfTestColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> locationColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> nameColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> studentIdColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> changeColumn;
    @FXML
    private TableColumn<SelectedCourseView,String> deleteColumn;
    @FXML
    private ComboBox<String>typeField;
    @FXML
    private CheckBox findByCourseNumberOrName;
    @FXML
    private CheckBox findByCourseType;
    @FXML
    private Button onInquire;
    @FXML
    private TextField InquireField;

    private static String[] typelist = {"必修", "选修", "通选", "限选", "任选"};
    
    public void onInquire(ActionEvent actionEvent) {
        if (findByStudent.isSelected()){
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res= HttpRequestUtil.request("/api/selectedCourse/findByStudentNameOrNum",req);
            List<SelectedCourse> newList= JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class);
            setDataTableView(newList);
        }
        else if (findByCourseType.isSelected()){
            String query=typeField.getValue();
            DataRequest req=new DataRequest();
            req.add("courseType",query);
            DataResponse res= HttpRequestUtil.request("/api/selectedCourse/findByCourseType",req);
            List<SelectedCourse> newList= JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class);
            setDataTableView(newList);
        }
        else if (findByCourseNumberOrName.isSelected()){
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res= HttpRequestUtil.request("/api/selectedCourse/findByNumName",req);
            List<SelectedCourse> newList= JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class);
            setDataTableView(newList);
        }
    }

    public void onReturn(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseManage_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((CourseManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    private static List<SelectedCourse> selectedCourseList = new ArrayList<>();

    private static ObservableList<SelectedCourseView> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<SelectedCourse> list){
        selectedCourseList =list;
        observableList.clear();
        for(SelectedCourse selectedCourse: selectedCourseList){
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseView(selectedCourse)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class));
    }
    
    public void initialize(){
        findByCourseNumberOrName.setSelected(true);

        //展示课程类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }

        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourse/findAll",new DataRequest());
        selectedCourseList= JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeekColumn.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("courseLocation"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        changeColumn.setCellFactory(new CourseSelectS_ButtonCellFactory<>("修改"));
        deleteColumn.setCellFactory(new CourseSelectS_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<SelectedCourseView> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseList);
    }

    public void onAdd(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelect_Student_Addition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加学生选课信息");
        stage.show();
    }


    public void findByStudent(ActionEvent actionEvent) {
        findByCourseNumberOrName.setSelected(false);
        findByCourseType.setSelected(false);

        typeField.setVisible(false);
        InquireField.setVisible(true);

        if (!(findByCourseType.isSelected()&&findByCourseNumberOrName.isSelected()))
            findByStudent.setSelected(true);
    }

    public void findByCourseType(ActionEvent actionEvent) {
        findByCourseNumberOrName.setSelected(false);
        findByStudent.setSelected(false);

        typeField.setVisible(true);
        InquireField.setVisible(false);

        if (!(findByStudent.isSelected()&&findByCourseNumberOrName.isSelected()))
            findByCourseType.setSelected(true);
    }

    public void findByCourseNumberOrName(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        findByCourseType.setSelected(false);

        typeField.setVisible(false);
        InquireField.setVisible(true);

        if (!(findByStudent.isSelected()&&findByCourseType.isSelected()))
            findByCourseNumberOrName.setSelected(true);
    }
}
class CourseSelectS_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public CourseSelectS_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        CourseSelectStudent_Change_Controller.setIndex(getIndex());
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseSelect_Student_Change.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改学生选课信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer selectedId= CourseSelectStudent_Manage_Controller.getSelectedCourseList().get(getIndex()).getSelectedId();
                        DataRequest req=new DataRequest();
                        req.add("id",selectedId);
                        DataResponse response=HttpRequestUtil.request("/api/selectedCourse/deleteById",req);
                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            CourseSelectStudent_Manage_Controller.updateDataTableView();
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
