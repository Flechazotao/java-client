package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentManageController extends manage_MainFrame_controller {
    @FXML
    private TableView<StudentInfo> dataTableView;

    @FXML
    private TableColumn<StudentInfo, String > classNameColumn;

    @FXML
    private TableColumn<StudentInfo, String> deptColumn;

    @FXML
    private TableColumn<StudentInfo, String> genderColumn;

    @FXML
    private TableColumn<StudentInfo, String> majorColumn;

    @FXML
    private TableColumn<StudentInfo, String> nameColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> numberColumn;

    private List<Student> studentList = new ArrayList<>();

    private ObservableList<StudentInfo> observableList= FXCollections.observableArrayList();
    void setDataTableView(){
        observableList.clear();
        for(Student s:studentList){
            StudentInfo studentInfo=new StudentInfo(s);
            studentInfo.setMajor(s.getMajor());
            studentInfo.setClassName(s.getClassName());
            observableList.addAll(FXCollections.observableArrayList(studentInfo));
        }
        dataTableView.setItems(observableList);
    }

    public void initialize() {
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        studentList= JSON.parseArray(JSON.toJSONString(res.getData()),Student.class);
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

        TableView.TableViewSelectionModel<StudentInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView();
    }

    protected void changeStudentInfo() {
        StudentInfo s = dataTableView.getSelectionModel().getSelectedItem();
        if(s == null) {
            clearPanel();
            return;
        }

    }

    public void clearPanel(){

    }


    public void onAddStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student-Addition-panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加学生");
        stage.show();
    }


    public void onInquire() {


    }
}
