package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
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

@Setter
@Getter
public class StudentManageController extends manage_MainFrame_controller {

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
    private TableColumn<StudentInfo, Integer> BeforeUniversityInfoColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> FamilyInformationColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> DeleteColumn;

    @FXML
    private TableColumn<StudentInfo, Integer> ChangeColumn;

    @FXML
    private TextField InquireField;


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
        DataResponse res = HttpRequestUtil.request("/api/student/getStudentList",new DataRequest());
        studentList= JSON.parseArray(JSON.toJSONString(res.getData()),Student.class);

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        BeforeUniversityInfoColumn.setCellFactory(new SM_ButtonCellFactory<>("查看入学前信息"));
        FamilyInformationColumn.setCellFactory(new SM_ButtonCellFactory<>("查看家庭信息"));
        DeleteColumn.setCellFactory(new SM_ButtonCellFactory<>("删除"));
        ChangeColumn.setCellFactory(new SM_ButtonCellFactory<>("修改"));

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


    public void onAddStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_Addition_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加学生");
        stage.show();
    }


    public void onInquire() {
        String query=InquireField.getText();
        DataRequest req=new DataRequest();
        req.add("numName",query);
        DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
        studentList=JSON.parseArray(JSON.toJSONString(res.getData()),Student.class);
        setDataTableView(studentList);
    }

    class SM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Getter
        @Setter
        private Long studentId=null;

        @Getter
        private static Student student;

        @Getter
        private static Integer index;
        private final String property;
        public SM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                            studentId=studentList.get(getIndex()).getStudentId();
                            req.add("studentId", studentId);
                            DataResponse res = HttpRequestUtil.request("/api/student/deleteStudent",req);
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
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_Change_panel.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 600, 677);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("修改学生");
                            stage.show();
                        }
                        else if (property=="查看入学前信息"){
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
                            stage.setTitle("修改学生");
                            stage.show();
                        }
                        else if (property=="查看家庭信息") {
                            student=studentList.get(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_FamilyInformation.fxml"));
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
