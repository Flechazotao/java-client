package com.teach.javafx.controller.other.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
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
                calIndex(index,id);
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
    }


    public void MondayFirst(ActionEvent actionEvent) {
    }

    public void TuesdayFirst(ActionEvent actionEvent) {
    }

    public void WednesdayFirst(ActionEvent actionEvent) {
    }

    public void ThursdayFirst(ActionEvent actionEvent) {
    }

    public void FridayFirst(ActionEvent actionEvent) {
    }

    public void SaturdayFirst(ActionEvent actionEvent) {
    }

    public void SaturdaySecond(ActionEvent actionEvent) {
    }

    public void MondaySecond(ActionEvent actionEvent) {
    }

    public void TuesdaySecond(ActionEvent actionEvent) {
    }

    public void WednesdaySecond(ActionEvent actionEvent) {
    }

    public void ThursdaySecond(ActionEvent actionEvent) {
    }

    public void FridaySecond(ActionEvent actionEvent) {
    }

    public void SundayFirst(ActionEvent actionEvent) {
    }

    public void SundaySecond(ActionEvent actionEvent) {
    }

    public void MondayThird(ActionEvent actionEvent) {
    }

    public void TuesdayThird(ActionEvent actionEvent) {
    }

    public void WednesdayThird(ActionEvent actionEvent) {
    }

    public void ThursdayThird(ActionEvent actionEvent) {
    }

    public void FridayThird(ActionEvent actionEvent) {
    }

    public void SaturdayThird(ActionEvent actionEvent) {
    }

    public void MondayFourth(ActionEvent actionEvent) {
    }

    public void SundayThird(ActionEvent actionEvent) {
    }

    public void TuesdayFourth(ActionEvent actionEvent) {
    }

    public void WednesdayFourth(ActionEvent actionEvent) {
    }

    public void ThursdayFourth(ActionEvent actionEvent) {
    }

    public void FridayFourth(ActionEvent actionEvent) {
    }

    public void SaturdayFourth(ActionEvent actionEvent) {
    }

    public void SundayFourth(ActionEvent actionEvent) {
    }

    public void TuesdayFifth(ActionEvent actionEvent) {
    }

    public void WednesdayFifth(ActionEvent actionEvent) {
    }

    public void ThursdayFifth(ActionEvent actionEvent) {
    }

    public void FridayFifth(ActionEvent actionEvent) {
    }

    public void SaturdayFifth(ActionEvent actionEvent) {
    }

    public void SundayFifth(ActionEvent actionEvent) {
    }

    public void MondayFifth(ActionEvent actionEvent) {
    }




}
class CourseSS_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
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
                        button1.setText(selectedCourseInfo.getCourse().getName());
                        CourseSelectedS_Controller.getSelectedCourseView()[rowIndex][columnIndex]=selectedCourseInfo;
                        button1.setOnAction((ActionEvent event1) -> {
                            MessageDialog.showDialog(selectedCourseInfo.getCourse().getName()+"\n"+selectedCourseInfo.getCourse().getTeacherName()+"\n"+selectedCourseInfo.getCourse().getLocation()+"\n"+selectedCourseInfo.getCourse().getCredit());
                        });
                        button.setText("退课");
                        property="退课";
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
                        button1.setText("");
                        button1.setOnAction(null);
                        button.setText("选课");
                        property="选课";
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
