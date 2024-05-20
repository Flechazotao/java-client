package com.teach.javafx.controller.AdminController;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.TeacherController.Student_Information_Controller;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.FamilyMember;
import com.teach.javafx.models.DO.SocietyMember;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
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

public class SocietyMember_Controller {
    @FXML
    private TableColumn<SocietyMember, String> ChangeCol;

    @FXML
    private TableColumn<SocietyMember, String> DetectCol;

    @FXML
    private TableColumn<SocietyMember, String> RelationCol;

    @FXML
    private TableView<SocietyMember> dataTableView;

    @FXML
    private TableColumn<SocietyMember, String> genderCol;

    @FXML
    private TableColumn<SocietyMember, String> nameCol;

    @FXML
    private TableColumn<SocietyMember, String> phoneCol;

    @FXML
    private Button onAddition;

    @Getter
    private static  List<SocietyMember>societyMemberList=new ArrayList<>();

    @Getter
    private static Student student;
    private static ObservableList<SocietyMember> observableList= FXCollections.observableArrayList();

    public static void setDataTableView(List<SocietyMember> list){
        societyMemberList=list;
        observableList.clear();
        for(SocietyMember s:societyMemberList){
            observableList.addAll(FXCollections.observableArrayList(s));
        }
    }

    public static void updateDataTableView(){
        DataRequest req= new DataRequest();
        req.add("id",StudentManageController.SM_ButtonCellFactory.getStudent().getStudentId());
        DataResponse res = HttpRequestUtil.request("/api/societyMember/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),SocietyMember.class));
    }


    public void initialize(){
        if (StudentManageController.SM_ButtonCellFactory.getStudent()==null){
            student= Student_Information_Controller.getStudent();
        }
        else student= StudentManageController.SM_ButtonCellFactory.getStudent();
        Long studentId= student.getStudentId();
        DataRequest req=new DataRequest();
        req.add("id",studentId);
        DataResponse res= HttpRequestUtil.request("/api/societyMember/findByStudent",req);
        societyMemberList= JSON.parseArray(JSON.toJSONString(res.getData()),SocietyMember.class);

        RelationCol.setCellValueFactory(new PropertyValueFactory<>("relation"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        DetectCol.setCellFactory(new SoM_ButtonCellFactory<>("删除"));
        ChangeCol.setCellFactory(new SoM_ButtonCellFactory<>("修改"));

        TableView.TableViewSelectionModel<SocietyMember> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(societyMemberList);
    }
    public void onAddition(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_SocietyMember-Addition.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("修改家庭信息");
        stage.show();
    }
}
class SoM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Getter
    @Setter
    private  static Integer index= null;
    private final String property;
    public SoM_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        index=getIndex();
                        List<FamilyMember> familyMemberList = Family_Manage_Controller.getFamilyMemberList();
                        Integer memberid=familyMemberList.get(getIndex()).getMemberId();
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_SocietyMember-Change.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改社会关系");
                        stage.show();
                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }

                        List<SocietyMember> societyMemberList = SocietyMember_Controller.getSocietyMemberList();
                        Integer memberid=societyMemberList.get(getIndex()).getSocietyId();
                        DataRequest request=new DataRequest();
                        request.add("id",memberid);
                        DataResponse response= HttpRequestUtil.request("/api/societyMember/deleteById",request);

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            SocietyMember_Controller.updateDataTableView();
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