package com.teach.javafx.controller.TeacherController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.Homework_Change_Controller;
import com.teach.javafx.controller.AdminController.Homework_Manage_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.models.DO.Homework;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.HomeworkView;
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

public class Homework_Controller extends Teacher_MainFrame_controller {

    @FXML
    public TableView<HomeworkView> dataTableView;
    @FXML
    public TableColumn<HomeworkView,String> courseNumberColumn;
    @FXML
    public TableColumn<HomeworkView,String> courseNameColumn;
    @FXML
    public TableColumn<HomeworkView,String> homeworkNameColumn;
    @FXML
    public TableColumn<HomeworkView,String> studentIdColumn;
    @FXML
    public TableColumn<HomeworkView,String> studentNameColumn;
    @FXML
    public TableColumn<HomeworkView,String> isSubmitColumn;
    @FXML
    public TableColumn<HomeworkView,String> submitTimeColumn;
    @FXML
    public TableColumn<HomeworkView,String> isCheckedColumn;
    @FXML
    public TableColumn<HomeworkView,String> checkTimeColumn;
    @FXML
    public TableColumn<HomeworkView,String> fileColumn;
    @FXML
    public TableColumn<HomeworkView,String> homeworkScoreColumn;
    @FXML
    public TableColumn<HomeworkView,String> checkColumn;
    @FXML
    public Button onInquire;
    @FXML
    public TextField InquireField;
    @FXML
    public Button onAdd;
    @FXML
    public CheckBox findByStudent;
    @FXML
    public CheckBox findByCourseNumberOrName;

    public void inHomeworkCenter(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeworkInfo_panel-T.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((HomeworkInfo_Manage_Controller) fxmlLoader.getController());
            MainApplication.resetStage("选课信息中心", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onInquire(ActionEvent event){
        if (findByStudent.isSelected()){
            //按照学生姓名或学号查询
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
            List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<Homework> newhomeworkList = new ArrayList<>();
            for (Student s:studentList){
                List<Homework> Lists = new ArrayList<>();
                DataRequest request=new DataRequest();
                request.add("id",s.getStudentId());
                DataResponse response= HttpRequestUtil.request("/api/homework/findByStudent",request);
                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), Homework.class);
                newhomeworkList.addAll(Lists);
            }
            setDataTableView(newhomeworkList);
        }
        else if (findByCourseNumberOrName.isSelected()){
//            根据课程编号或名称查询
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/homework/findByCourseNumberOrName",req);
            homeworkList=JSON.parseArray(JSON.toJSONString(res.getData()), Homework.class);
            setDataTableView(homeworkList);
        }
    }

    @Getter
    private static List<Homework> homeworkList = new ArrayList<>();

    private static ObservableList<HomeworkView> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Homework> list){
        homeworkList=list;
        observableList.clear();
        for(Homework homework:homeworkList){
            observableList.addAll(FXCollections.observableArrayList(new HomeworkView(homework)));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/homework/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Homework.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/homework/findAll",new DataRequest());
        homeworkList= JSON.parseArray(JSON.toJSONString(res.getData()), Homework.class);

        courseNumberColumn.setCellValueFactory(new PropertyValueFactory<>("courseNumber"));
        courseNameColumn.setCellValueFactory(new PropertyValueFactory<>("courseName"));
        homeworkNameColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkName"));
        studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        isSubmitColumn.setCellValueFactory(new PropertyValueFactory<>("isSubmit"));
        submitTimeColumn.setCellValueFactory(new PropertyValueFactory<>("submitTime"));
        isCheckedColumn.setCellValueFactory(new PropertyValueFactory<>("isChecked"));
        checkTimeColumn.setCellValueFactory(new PropertyValueFactory<>("checkTime"));
        homeworkScoreColumn.setCellValueFactory(new PropertyValueFactory<>("homeworkScore"));
        fileColumn.setCellFactory(new HomeworkM_ButtonCellFactory<>("下载文件"));
        checkColumn.setCellFactory(new HomeworkM_ButtonCellFactory<>("修改"));


        TableView.TableViewSelectionModel<HomeworkView> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(homeworkList);
    }

    @FXML
    void onAdd(ActionEvent event) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeworkAddition.fxml"));
        Scene scene;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("发布作业");
        stage.show();
    }
    public void findByCourseNumberOrName(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        if (!findByStudent.isSelected())
            findByCourseNumberOrName.setSelected(true);
    }

    public void findByStudent(ActionEvent actionEvent) {
        findByCourseNumberOrName.setSelected(false);

        if (!findByCourseNumberOrName.isSelected())
            findByStudent.setSelected(true);
    }

}
class HomeworkM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private final String property;
    public HomeworkM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        Homework_Change_Controller.setFromHomework(Homework_Controller.getHomeworkList().get(getIndex()));
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeworkChange.fxml"));
                        Scene scene;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改作业信息");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer homeworkId= Homework_Controller.getHomeworkList().get(getIndex()).getHomeworkId();
                        DataRequest req=new DataRequest();
                        req.add("id",homeworkId);
                        DataResponse response=HttpRequestUtil.request("/api/homework/deleteById",req);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            Homework_Manage_Controller.updateDataTableView();
                        }
                    }else if (Objects.equals(property, "下载文件")) {

                        String url = Homework_Controller.getHomeworkList().get(getIndex()).getFile();
                        if(url==null){
                            MessageDialog.showDialog("未上传文件!");
                            return;
                        }
                        DataRequest req = new DataRequest();
                        req.add("url", url);
                        String fileName=url.substring(url.lastIndexOf("\\")+1);
                        byte[] fileByte=HttpRequestUtil.requestByteData("/api/file/download", req);

                        if(fileByte==null){
                            MessageDialog.showDialog("下载失败!");
                            return;
                        }

                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setTitle("Save File");
                        fileChooser.setInitialFileName(fileName);
                        Path path = fileChooser.showSaveDialog(null).toPath();
                        if (path != null) {
                            FileOutputStream fos = null;
                            try {
                                fos = new FileOutputStream(path.toFile());
                                fos.write(fileByte);
                                fos.close();
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                            MessageDialog.showDialog("下载成功!");
                            return;
                        }
                        else {
                            MessageDialog.showDialog("下载失败!");
                            return;
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
