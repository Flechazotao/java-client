package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.models.DO.Teacher;
import com.teach.javafx.models.DO.Teacher;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.TeacherInfo;
import com.teach.javafx.models.DTO.TeacherInfo;
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
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherManageController extends manage_MainFrame_controller {

    @FXML
    public TableView<TeacherInfo> dataTableView;
    @FXML
    public TableColumn<TeacherInfo,Long> numberColumn;
    @FXML
    public TableColumn<TeacherInfo,String> nameColumn;
    @FXML
    public TableColumn<TeacherInfo,String> cardColumn;
    @FXML
    public TableColumn<TeacherInfo,String> deptColumn;
    @FXML
    public TableColumn<TeacherInfo,String> titleColumn;
    @FXML
    public TableColumn<TeacherInfo,String> degreeColumn;
    @FXML
    public TableColumn<TeacherInfo,String> genderColumn;
    @FXML
    public TableColumn<TeacherInfo,String> politicalStatusColumn;
    @FXML
    public TableColumn<TeacherInfo,String> birthdayColumn;
    @FXML
    public TableColumn<TeacherInfo,String> emailColumn;
    @FXML
    public TableColumn<TeacherInfo,String> phoneColumn;
    @FXML
    public TableColumn<TeacherInfo,String> addressColumn;

    public Button onInquire;
    public Button onAddTeacher;
    @FXML
    public TextField InquireField;

    @FXML
    private TableColumn<TeacherInfo, Integer> DeleteColumn;

    @FXML
    private TableColumn<Teacher, Integer> ChangeColumn;

    private static List<Teacher> teacherList = new ArrayList<>();

    private static ObservableList<TeacherInfo> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<Teacher> list){
        teacherList=list;
        observableList.clear();
        for(Teacher t:teacherList){
            TeacherInfo teacherInfo=new TeacherInfo(t);
            teacherInfo.setDegree(t.getDegree());
            teacherInfo.setTitle(t.getTitle());
            observableList.addAll(FXCollections.observableArrayList(teacherInfo));
        }
    }
    
    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/teacher/getTeacherList",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),Teacher.class));
    }

    public void initialize() {
        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/teacher/getTeacherList",new DataRequest());
        teacherList= JSON.parseArray(JSON.toJSONString(res.getData()),Teacher.class);

        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cardColumn.setCellValueFactory(new PropertyValueFactory<>("card"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        degreeColumn.setCellValueFactory(new PropertyValueFactory<>("degree"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));
        politicalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("politicalStatus"));
        birthdayColumn.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        DeleteColumn.setCellFactory(new TeacherManageController.TM_ButtonCellFactory<>("删除"));
        ChangeColumn.setCellFactory(new TeacherManageController.TM_ButtonCellFactory<>("修改"));

        TableView.TableViewSelectionModel<TeacherInfo> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(teacherList);
    }
    
    public void onAddTeacher(ActionEvent actionEvent) {
    }

    public void onInquire(ActionEvent actionEvent) {
    }

    class TM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Getter
        @Setter
        private Integer userId= null;

        private Long teacherId=null;

        private final String property;
        public TM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                            teacherId=teacherList.get(getIndex()).getTeacherId();
                            req.add("teacher", teacherId);
                            DataResponse res = HttpRequestUtil.request("/api/teacher/deleteTeacher",req);
                            if(res.getCode() == 401) {
                                MessageDialog.showDialog("信息不完整!");
                            }
                            else if (res.getCode() == 404){
                                MessageDialog.showDialog("该教师不存在!");
                            }
                            else {
                                MessageDialog.showDialog("删除成功!");
                                updateDataTableView();
                            }
                        }

                        else if (property=="修改") {
                            TeacherChange_Controller.setIndex(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Teacher-Change_panel.fxml"));
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
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Teacher-BeforeInformation-Change.fxml"));
                        }
                        else if (property=="查看家庭信息") {
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Teacher-FamilyInformation.fxml"));
                        }

                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load(), 1045, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("添加学生");
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
