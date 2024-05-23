package com.teach.javafx.controller.StudentController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.AdminController.StudentManageController;
import com.teach.javafx.controller.AdminController.Student_BeforeInfo_Manage_Controller;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.CourseSelectedS_Controller;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.HonorInfo;
import com.teach.javafx.models.DO.SelectedCourse;
import com.teach.javafx.models.DO.SelectedCourseInfo;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class firstPage_Controller extends student_MainFrame_controller {

    public Label phone;
    public Label name;
    public Label num;
    public Label email;
    public Label politic;
    public Label address;
    public TextArea honorTextArea;
    @FXML
    private javafx.scene.layout.GridPane GridPane;

    @FXML
    private Button onFridayFifth;

    @FXML
    private Button onFridayFirst;

    @FXML
    private Button onFridayFourth;

    @FXML
    private Button onFridaySecond;

    @FXML
    private Button onFridayThird;

    @FXML
    private Button onMondayFifth;

    @FXML
    private Button onMondayFirst;

    @FXML
    private Button onMondayFourth;

    @FXML
    private Button onMondaySecond;

    @FXML
    private Button onMondayThird;

    @FXML
    private Button onSaturdayFifth;

    @FXML
    private Button onSaturdayFirst;

    @FXML
    private Button onSaturdayFourth;

    @FXML
    private Button onSaturdaySecond;

    @FXML
    private Button onSaturdayThird;

    @FXML
    private Button onSundayFifth;

    @FXML
    private Button onSundayFirst;

    @FXML
    private Button onSundayFourth;

    @FXML
    private Button onSundaySecond;

    @FXML
    private Button onSundayThird;

    @FXML
    private Button onThursdayFifth;

    @FXML
    private Button onThursdayFirst;

    @FXML
    private Button onThursdayFourth;

    @FXML
    private Button onThursdaySecond;

    @FXML
    private Button onThursdayThird;

    @FXML
    private Button onTuesdayFifth;

    @FXML
    private Button onTuesdayFirst;

    @FXML
    private Button onTuesdayFourth;

    @FXML
    private Button onTuesdaySecond;

    @FXML
    private Button onTuesdayThird;

    @FXML
    private Button onWednesdayFifth;

    @FXML
    private Button onWednesdayFirst;

    @FXML
    private Button onWednesdayFourth;

    @FXML
    private Button onWednesdaySecond;

    @FXML
    private Button onWednesdayThird;

    ArrayList<Button> buttons=new ArrayList<>();

    @Getter
    @Setter
    private static Button[][] buttonView = new Button[5][7];

    @Getter
    @Setter
    private static SelectedCourseInfo[][] selectedCourseView = new SelectedCourseInfo[5][7];

    public static void calIndex(Integer[] index, String s){
        int rowIndex=0;
        int columnIndex=0;
        if(s.contains("第一大节"))rowIndex=0;
        else if(s.contains("第二大节"))rowIndex=1;
        else if(s.contains("第三大节"))rowIndex=2;
        else if(s.contains("第四大节"))rowIndex=3;
        else if(s.contains("第五大节"))rowIndex=4;
        if(s.contains("周一"))columnIndex=0;
        else if(s.contains("周二"))columnIndex=1;
        else if(s.contains("周三"))columnIndex=2;
        else if(s.contains("周四"))columnIndex=3;
        else if(s.contains("周五"))columnIndex=4;
        else if(s.contains("周六"))columnIndex=5;
        else if(s.contains("周七"))columnIndex=6;
        index[0]=rowIndex;
        index[1]=columnIndex;
        return;
    }
    public static void calIdIndex(Integer[] index, String s){
        Integer rowIndex=0;
        Integer columnIndex=0;
        if(s.contains("First"))rowIndex=0;
        else if(s.contains("Second"))rowIndex=1;
        else if(s.contains("Third"))rowIndex=2;
        else if(s.contains("Fourth"))rowIndex=3;
        else if(s.contains("Fifth"))rowIndex=4;
        if(s.contains("Monday"))columnIndex=0;
        else if(s.contains("Tuesday"))columnIndex=1;
        else if(s.contains("Wednesday"))columnIndex=2;
        else if(s.contains("Thursday"))columnIndex=3;
        else if(s.contains("Friday"))columnIndex=4;
        else if(s.contains("Saturday"))columnIndex=5;
        else if(s.contains("Sunday"))columnIndex=6;
        index[0]=rowIndex;
        index[1]=columnIndex;
        return;
    }
    private void AddButtonToList(){
        ObservableList<Node> nodes=GridPane.getChildren();
        for(Node node:nodes){
            if(node instanceof Button button){
                buttons.add((Button) node);
                String id=button.getId();
                Integer rowIndex = 0;
                Integer columnIndex = 0;
                Integer[] index=new Integer[2];
                calIdIndex(index,id);
                buttonView[index[0]][index[1]]=button;
            }
        }
    }

    public static List<SelectedCourseInfo> studentSelectedCourseInfoList=new ArrayList<>();
    @Getter
    private static List<HonorInfo> honorInfoList = new ArrayList<>();

    public void initialize(){


        DataRequest req1=new DataRequest();
        req1.add("numName",LoginController.getNumber());
        DataResponse res1= HttpRequestUtil.request("/api/student/findByStudentIdOrName",req1);
        List<Student> studentList= JSON.parseArray(JSON.toJSONString(res1.getData()), Student.class);
        Student student=studentList.get(0);

        phone.setText(student.getPerson().getPhone());
        name.setText(student.getPerson().getName());
        num.setText(String.valueOf(student.getStudentId()));
        email.setText(student.getPerson().getEmail());
        address.setText(student.getPerson().getAddress());
        politic.setText(student.getPerson().getAddress());
        res1= HttpRequestUtil.request("/api/honorInfo/findByStudent",req1);
        honorInfoList= JSON.parseArray(JSON.toJSONString(res1.getData()), HonorInfo.class);

        if (honorInfoList!=null){
        List<String>Texts=new ArrayList<>();
        for (HonorInfo honorInfo:honorInfoList){
            Texts.add(honorInfo.getHonorTime()+"\t"+honorInfo.getHonorName()+honorInfo.getLevel());
        }
        for (String s:Texts)
            honorTextArea.setText(s+"\n");
        }
        
        AddButtonToList();

        DataRequest req=new DataRequest();
        req.add("id", LoginController.getNumber());
        DataResponse res= HttpRequestUtil.request("/api/selectedCourse/findByStudentId",req);
        List<SelectedCourse> selectedCourses= JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class);
        studentSelectedCourseInfoList.clear();
        if(selectedCourses!=null){
            for (SelectedCourse selectedCourse:selectedCourses){
                studentSelectedCourseInfoList.add(selectedCourse.getSelectedCourseInfo());
                Integer rowIndex=0;
                Integer columnIndex=0;
                SelectedCourseInfo selectedCourseInfo=selectedCourse.getSelectedCourseInfo();
                Integer[] index=new Integer[2];
                CourseSelectedS_Controller.calIndex(index,selectedCourseInfo.getCourse().getCourseTime());
                rowIndex=index[0];
                columnIndex=index[1];
                Button button1= firstPage_Controller.getButtonView()[rowIndex][columnIndex];
                button1.setStyle(
                        "-fx-background-radius:20;"+//设置背景圆角
                                "-fx-background-color:#FFA07A;"+//设置背景颜色
                                "-fx-text-fill:#4a2107;"+        //设置字体颜色
                                "-fx-font-weight:bold;"+         //设置字体粗细
                                "-fx-font-size:16;"+             //设置字体颜色
                                "-fx-border-radius:10;"          //设置边框圆角
                );
                button1.setText(selectedCourseInfo.getCourse().getName());
                CourseSelectedS_Controller.getSelectedCourseView()[rowIndex][columnIndex]=selectedCourseInfo;
                button1.setOnAction((ActionEvent event1) -> {
                    MessageDialog.showDialog("课程名称: "+selectedCourseInfo.getCourse().getName()+"\n"+"授课教师: "+selectedCourseInfo.getCourse().getTeacherName()+"\n"+"上课地点: "+selectedCourseInfo.getCourse().getLocation()+"\n"+"学分: "+selectedCourseInfo.getCourse().getCredit());
                });
            }
        }

    }

    public void MondayFirst() {
    }

    public void TuesdayFirst() {
    }

    public void WednesdayFirst() {
    }

    public void ThursdayFirst() {
    }

    public void FridayFirst() {
    }

    public void SaturdayFirst() {
    }

    public void SaturdaySecond() {
    }

    public void MondaySecond() {
    }

    public void TuesdaySecond() {
    }

    public void WednesdaySecond() {
    }

    public void ThursdaySecond() {
    }

    public void FridaySecond() {
    }

    public void SundayFirst() {
    }

    public void SundaySecond() {
    }

    public void MondayThird() {
    }

    public void TuesdayThird() {
    }

    public void WednesdayThird() {
    }

    public void ThursdayThird() {
    }

    public void FridayThird() {
    }

    public void SaturdayThird() {
    }

    public void MondayFourth() {
    }

    public void SundayThird() {
    }

    public void TuesdayFourth() {
    }

    public void WednesdayFourth() {
    }

    public void ThursdayFourth() {
    }

    public void FridayFourth() {
    }

    public void SaturdayFourth() {
    }

    public void SundayFourth() {
    }

    public void TuesdayFifth() {
    }

    public void WednesdayFifth() {
    }

    public void ThursdayFifth() {
    }

    public void FridayFifth() {
    }

    public void SaturdayFifth() {
    }

    public void SundayFifth() {
    }

    public void MondayFifth() {
    }

}
