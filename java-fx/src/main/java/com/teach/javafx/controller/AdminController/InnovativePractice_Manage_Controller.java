package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.models.DO.AttendanceInfo;
import com.teach.javafx.models.DO.InnovativePractice;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
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
import lombok.Setter;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InnovativePractice_Manage_Controller extends manage_MainFrame_controller {

    @FXML
    private TableView<InnovativePractice> dataTableView;
    @FXML
    private TableColumn<InnovativePractice, String> activityNameColumn;
    @FXML
    private TableColumn<InnovativePractice, String> beginTimeColumn;
    @FXML
    private TableColumn<InnovativePractice, String>endTimeColumn;
    @FXML
    private TableColumn<InnovativePractice, String>fileColumn;
    @FXML
    private TableColumn<InnovativePractice, String> studentColumn;
    @FXML
    private TableColumn<InnovativePractice, String>teacherNameColumn;
    @FXML
    private TableColumn<InnovativePractice, String>typeColumn;
    @FXML
    private TableColumn<InnovativePractice, String> achievementColumn;
    @FXML
    private TableColumn<InnovativePractice, String>changeColumn;
    @FXML
    private TableColumn<InnovativePractice, Void> deleteColumn;

    @FXML
    private TextField InquireField;
    @FXML
    private Button onAdd;
    @FXML
    private CheckBox findByStudent;
    @FXML
    private CheckBox findByType;
    @FXML
    private CheckBox findByName;
    @FXML
    private ComboBox<String>typeField;

    public static String[]typelist={"社会实践","学科竞赛","科技成果","培训讲座","创新项目","校外实习","志愿服务"};


    @Getter
    private static List<InnovativePractice> innovativePracticeList = new ArrayList<>();

    private static ObservableList<InnovativePractice> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<InnovativePractice> list){
        innovativePracticeList=list;
        observableList.clear();
        for(InnovativePractice innovativePractice:innovativePracticeList){
            observableList.addAll(FXCollections.observableArrayList(innovativePractice));
        }
    }

    public static void updateDataTableView(){
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),InnovativePractice.class));
    }

    public void initialize() {
        findByStudent.setSelected(true);
        //添加类型下拉框
        for(String s:typelist){
            typeField.getItems().add(s);
        }
        typeField.setVisibleRowCount(5);

        dataTableView.setItems(observableList);
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findAll",new DataRequest());
        innovativePracticeList= JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);

        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        activityNameColumn.setCellValueFactory(new PropertyValueFactory<>("activityName"));
        beginTimeColumn.setCellValueFactory(new PropertyValueFactory<>("beginTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        teacherNameColumn.setCellValueFactory(new PropertyValueFactory<>("teacherName"));
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        achievementColumn.setCellValueFactory(new PropertyValueFactory<>("achievement"));
        changeColumn.setCellFactory(new IPM_ButtonCellFactory<>("下载文件"));
        changeColumn.setCellFactory(new IPM_ButtonCellFactory<>("修改"));
        deleteColumn.setCellFactory(new IPM_ButtonCellFactory<>("删除"));

        TableView.TableViewSelectionModel<InnovativePractice> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(innovativePracticeList);
    }

    @FXML
    public void onAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_Addition.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加创新实践信息");
        stage.show();
    }


    public void onInquire() {
//        //根据名称查找
//        @PostMapping("/findByName")
//        public DataResponse findByName(@RequestBody DataRequest dataRequest){
//            return innovativePracticeService.findByName(JsonUtil.parse(dataRequest.get("name"), String.class));
//        }

        if (findByStudent.isSelected()) {
            String query=InquireField.getText();
            DataRequest req=new DataRequest();
            req.add("numName",query);
            DataResponse res=HttpRequestUtil.request("/api/student/findByStudentIdOrName",req);
            List<Student>studentList=JSON.parseArray(JSON.toJSONString(res.getData()), Student.class);
            List<InnovativePractice> newinnovativePracticeList = new ArrayList<>();
            for (Student s:studentList){
                List<InnovativePractice> Lists = new ArrayList<>();
                DataRequest request=new DataRequest();
                request.add("id",s.getStudentId());
                DataResponse response= HttpRequestUtil.request("/api/innovativePractice/findByStudent",request);
                Lists=JSON.parseArray(JSON.toJSONString(response.getData()), InnovativePractice.class);
                newinnovativePracticeList.addAll(Lists);
            }
            setDataTableView(newinnovativePracticeList);
        }
        else if (findByName.isSelected()){
            String query = InquireField.getText();
            DataRequest req1 = new DataRequest();
            req1.add("name", query);
            DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByName", req1);
            innovativePracticeList = JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);
            setDataTableView(innovativePracticeList);
        }
        else if (findByType.isSelected()){
            String query = typeField.getValue();
            DataRequest req1 = new DataRequest();
            req1.add("type", query);
            DataResponse res = HttpRequestUtil.request("/api/innovativePractice/findByType", req1);
            innovativePracticeList = JSON.parseArray(JSON.toJSONString(res.getData()), InnovativePractice.class);
            setDataTableView(innovativePracticeList);
        }

    }

    public void findByStudent(ActionEvent actionEvent) {
        findByType.setSelected(false);
        findByName.setSelected(false);

        InquireField.setVisible(true);
        typeField.setVisible(false);

        if (!(findByType.isSelected()&&findByName.isSelected()))
            findByStudent.setSelected(true);
    }

    public void findByType(ActionEvent actionEvent) {
        findByStudent.setSelected(false);
        findByName.setSelected(false);


        InquireField.setVisible(false);
        typeField.setVisible(true);

        if (!(findByStudent.isSelected()&&findByName.isSelected()))
            findByType.setSelected(true);
    }

    public void findByName(ActionEvent actionEvent) {
        findByType.setSelected(false);
        findByStudent.setSelected(false);

        InquireField.setVisible(true);
        typeField.setVisible(false);

        if (!(findByType.isSelected()&&findByStudent.isSelected()))
            findByName.setSelected(true);
    }

    class IPM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
        @Getter
        @Setter
        private Long studentId=null;

        @Getter
        @Setter
        private Integer innovativeId;

        @Getter
        private static Student student;

        @Getter
        private static Integer index;
        private final String property;
        public IPM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                            innovativeId=innovativePracticeList.get(getIndex()).getInnovativeId();
                            req.add("id", innovativeId);
                            DataResponse res = HttpRequestUtil.request("/api/innovativePractice/deleteById",req);
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
                            InnovativePractice_Change_Controller.setIndex(getIndex());
                            fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_Change.fxml"));
                            Scene scene = null;
                            try {
                                scene = new Scene(fxmlLoader.load(), 600, 677);
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.setTitle("修改创新实践信息");
                            stage.show();
                        }else if (Objects.equals(property, "下载文件")) {

                            String url = InnovativePractice_Manage_Controller.getInnovativePracticeList().get(getIndex()).getFile();
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
                        else{//不知道这里有啥用
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