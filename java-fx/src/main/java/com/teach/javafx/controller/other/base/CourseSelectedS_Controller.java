package com.teach.javafx.controller.other.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.LoginController;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.*;
import com.teach.javafx.models.DTO.*;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CourseSelectedS_Controller extends student_MainFrame_controller{
    @FXML
    private GridPane GridPane;

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
    }public static void calIdIndex(Integer[] index, String s){
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

    @FXML
    private TableView<SelectedCourseInfoInfo> dataTableView;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> creditColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseTimeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> teacherNameColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> courseBeginWeekColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> wayOfTestColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> locationColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> typeColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> nowSelectNumberColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> maxSelectedColumn;
    @FXML
    private TableColumn<SelectedCourseInfoInfo,String> workButton;

    @Getter
    private static List<SelectedCourseInfo> selectedCourseInfoList = new ArrayList<>();

    private static ObservableList<SelectedCourseInfoInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<SelectedCourseInfo> list){
        selectedCourseInfoList =list;
        observableList.clear();
        for(SelectedCourseInfo selectedCourseInfo: selectedCourseInfoList){
            observableList.addAll(FXCollections.observableArrayList(new SelectedCourseInfoInfo(selectedCourseInfo)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),SelectedCourseInfo.class));
    }

    public void initialize() {
        AddButtonToList();
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/selectedCourseInfo/findAll",new DataRequest());
        selectedCourseInfoList = JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourseInfo.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        creditColumn.setCellValueFactory(new PropertyValueFactory<>("credit"));
        courseTimeColumn.setCellValueFactory(new PropertyValueFactory<>("courseTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        courseBeginWeekColumn.setCellValueFactory(new PropertyValueFactory<>("courseBeginWeek"));
        wayOfTestColumn.setCellValueFactory(new PropertyValueFactory<>("wayOfTest"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        nowSelectNumberColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfSelected"));
        maxSelectedColumn.setCellValueFactory(new PropertyValueFactory<>("MaxNumberOfSelected"));
        workButton.setCellFactory(new CourseSS_ButtonCellFactory<>("选课"));
        TableView.TableViewSelectionModel<SelectedCourseInfoInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(selectedCourseInfoList);

        DataRequest req=new DataRequest();
        req.add("id",studentId);
        res=HttpRequestUtil.request("/api/selectedCourse/findByStudentId",req);
        List<SelectedCourse> selectedCourses=JSON.parseArray(JSON.toJSONString(res.getData()), SelectedCourse.class);
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
                Button button1=CourseSelectedS_Controller.getButtonView()[rowIndex][columnIndex];
                button1.setStyle(
                        "-fx-background-radius:20;"+//设置背景圆角
                                "-fx-background-color:bisque;"+//设置背景颜色
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


    public static String studentId = LoginController.getNumber();

    public static List<SelectedCourseInfo> studentSelectedCourseInfoList=new ArrayList<>();;
    public void onSave() {
        Student student=new Student();
        student.setStudentId(Long.valueOf(studentId));
        DataRequest req=new DataRequest();
        req.add("student",student);
        req.add("selectedCourseInfos",studentSelectedCourseInfoList);
        DataResponse res=HttpRequestUtil.request("/api/selectedCourse/addByStudentAndList",req);
        if(res.getCode()!=200){
            MessageDialog.showDialog("保存失败！");
            return;
        }
        MessageDialog.showDialog("保存成功！");
        return;
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
class CourseSS_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Setter
    private String property;
    public CourseSS_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);
            {

                button.setOnAction(event -> {
                    FXMLLoader fxmlLoader = null;
                    if (Objects.equals(property, "选课")){
                        Integer rowIndex=0;
                        Integer columnIndex=0;
                        SelectedCourseInfo selectedCourseInfo=CourseSelectedS_Controller.getSelectedCourseInfoList().get(getIndex());
                        Integer[] index=new Integer[2];
                        CourseSelectedS_Controller.calIndex(index,selectedCourseInfo.getCourse().getCourseTime());
                        rowIndex=index[0];
                        columnIndex=index[1];
                        Button button1=CourseSelectedS_Controller.getButtonView()[rowIndex][columnIndex];
                        button1.setStyle(
                                "-fx-background-radius:20;"+//设置背景圆角
                                        "-fx-background-color:bisque;"+//设置背景颜色
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
                        button.setText("退课");
                        property="退课";
                        CourseSelectedS_Controller.studentSelectedCourseInfoList.add(selectedCourseInfo);
                    }
                    else if (Objects.equals(property, "退课")) {
                        Integer rowIndex=0;
                        Integer columnIndex=0;
                        SelectedCourseInfo selectedCourseInfo=CourseSelectedS_Controller.getSelectedCourseInfoList().get(getIndex());
                        Integer[] index=new Integer[2];
                        CourseSelectedS_Controller.calIndex(index,selectedCourseInfo.getCourse().getCourseTime());
                        rowIndex=index[0];
                        columnIndex=index[1];
                        Button button1=CourseSelectedS_Controller.getButtonView()[rowIndex][columnIndex];
                        button1.setStyle(
                                "-fx-background-color: peachpuff;"
                        );
                        button1.setText("");
                        button1.setOnAction(null);
                        button.setText("选课");
                        property="选课";
                        CourseSelectedS_Controller.studentSelectedCourseInfoList.remove(selectedCourseInfo);
                    }
                }

                );
            }

            @Override
            protected void updateItem(T item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(button);
                }
                property="选课";
                button.setText("选课");
                if(getIndex()!=-1&&getIndex()<CourseSelectedS_Controller.getSelectedCourseInfoList().size()&& CourseSelectedS_Controller.studentSelectedCourseInfoList.contains(CourseSelectedS_Controller.getSelectedCourseInfoList().get(getIndex()))){
                    Integer rowIndex=0;
                    Integer columnIndex=0;
                    SelectedCourseInfo selectedCourseInfo=CourseSelectedS_Controller.getSelectedCourseInfoList().get(getIndex());
                    Integer[] index=new Integer[2];
                    CourseSelectedS_Controller.calIndex(index,selectedCourseInfo.getCourse().getCourseTime());
                    rowIndex=index[0];
                    columnIndex=index[1];
                    Button button1=CourseSelectedS_Controller.getButtonView()[rowIndex][columnIndex];
                    button1.setStyle(
                            "-fx-background-radius:20;"+//设置背景圆角
                                    "-fx-background-color:bisque;"+//设置背景颜色
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
                    button.setText("退课");
                    property="退课";
                }
            }
        };
    }
}
