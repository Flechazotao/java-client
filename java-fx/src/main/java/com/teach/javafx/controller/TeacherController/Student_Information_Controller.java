package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.StudentChange_Controller;
import com.teach.javafx.controller.AdminController.StudentManageController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

public class Student_Information_Controller extends Teacher_MainFrame_controller {

    @FXML
    private TableView<StudentInfo> dataTableView;

    @FXML
    private TableColumn<StudentInfo, String> classNameColumn;

    @FXML
    private TableColumn<StudentInfo, String> deptColumn;

    @FXML
    private TableColumn<StudentInfo, String> genderColumn;

    @FXML
    private TableColumn<StudentInfo, String> majorColumn;

    @FXML
    private TableColumn<StudentInfo, String> nameColumn;

    @FXML
    private TableColumn<StudentInfo, Long> numberColumn;

    @FXML
    private TableColumn<StudentInfo, Long> GPAColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> BeforeUniversityInfoColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> FamilyInformationColumn;
    @FXML
    private TableColumn<StudentInfo, Integer> SocietyMemberColumn;

    @FXML
    private TextField InquireField;

    @Getter
    @Setter
    private static Student student=null;


    @Getter
    private static List<Student> studentList = new ArrayList<>();

    private static ObservableList<StudentInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Student> list){
        studentList=list;
        observableList.clear();
        for(Student s:studentList){
            StudentInfo studentInfo=new StudentInfo(s);
            studentInfo.setMajor(s.getMajor());
            studentInfo.setClassName(s.getClassName());
            observableList.addAll(FXCollections.observableArrayList(studentInfo));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Student.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);

//        for (Student s:studentList) {
//            DataRequest req = new DataRequest();
//            req.add("id", s.getStudentId());
//            DataResponse res=HttpRequestUtil.request("/api/score/getGradePointsByStudentId",req);
//            s.setGPA(String.valueOf(JSON.parseObject(JSON.toJSONString(res.getData()),double.class)));
//        }

        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        studentList= JSON.parseArray(JSON.toJSONString(res.getData()),Student.class);



        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        GPAColumn.setCellValueFactory(new PropertyValueFactory<>("GPA"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        BeforeUniversityInfoColumn.setCellFactory(new SIM_ButtonCellFactory<>("查看入学前信息"));
        FamilyInformationColumn.setCellFactory(new SIM_ButtonCellFactory<>("查看家庭信息"));
        SocietyMemberColumn.setCellFactory(new SIM_ButtonCellFactory<>("查看社会关系"));

        TableView.TableViewSelectionModel<StudentInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(studentList);
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

    public void onInquire() {
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numName",query);
        DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
        studentList=JSON.parseArray(JSON.toJSONString(res.getData()),Student.class);
        setDataTableView(studentList);
    }

    class SIM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Getter
        @Setter
        private Long studentId=null;

        @Getter
        private static Integer index;
        private final String property;
        public SIM_ButtonCellFactory(@NamedArg("property") String var1) {
            this.property = var1;
        }
        @Override
        public TableCell<S, T> call(TableColumn<S, T> param) {
            return new TableCell<S, T>() {
                private Button button = new Button(property);
                {
                    button.setOnAction(event -> {

                        FXMLLoader fxmlLoader = null;

                        if (property=="查看入学前信息"){
                            index=getIndex();
                            student=studentList.get(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_BeforeInformation_panel.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 600, 677);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("查看入学前信息");
                            stage.show();
                        }
                        else if (property=="查看家庭信息") {
                            student=studentList.get(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_FamilyInformation.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 1255, 714);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("查看家庭信息");
                            stage.show();
                        }
                        else if (property=="查看社会关系") {
                            student=studentList.get(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_SocietyMember.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 925, 714);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("查看社会关系");
                            stage.show();
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

}