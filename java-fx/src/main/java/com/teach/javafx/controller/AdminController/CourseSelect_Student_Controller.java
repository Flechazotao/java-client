package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.base.*;
import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.SelectedCourseInfoInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CourseSelect_Student_Controller extends manage_MainFrame_controller {

    public CheckBox findByStudent;
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
    private TableColumn<Course,String> nameColumn;
    @FXML
    private TableColumn<Course,String> studentIdColumn;
    @FXML
    private TableColumn<Course,String> changeColumn;
    @FXML
    private TableColumn<Course,String> deleteColumn;
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
//            String query=InquireField.getText();
//            DataRequest req=new DataRequest();
//            req.add("numName",query);
//            DataResponse res= HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
//            List<Student> studentList= JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
//            List<Fee> newfeeList = new ArrayList<>();
//            for (Student s:studentList){
//                List<Fee> Lists = new ArrayList<>();
//                DataRequest request=new DataRequest();
//                request.add("id",s.getStudentId());
//                DataResponse response= HttpRequestUtil.request("/api/fee/findByStudent",request);
//                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), Fee.class);
//                newfeeList.addAll(Lists);
//            }
//            setDataTableView(newfeeList);

        }
        else if (findByCourseType.isSelected()){

        }
        else if (findByCourseNumberOrName.isSelected()){


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
        public void initialize(){


            //展示课程类型下拉框
            for(String s:typelist){
                typeField.getItems().add(s);
            }




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
