package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.models.DO.FamilyMember;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
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

public class StudentFamilyInformation_Controller {
    @FXML
    private TableView<FamilyMember> dataTableView;

    @FXML
    private TableColumn<FamilyMember, Void> ChangeCol;

    @FXML
    private TableColumn<FamilyMember, Void> DetectCol;

    @FXML
    private TableColumn<FamilyMember, String> RelationCol;

    @FXML
    private TableColumn<FamilyMember, String> WorkPlaceCol;

    @FXML
    private TableColumn<FamilyMember, String> birthdayCol;

    @FXML
    private TableColumn<FamilyMember, String> genderCol;

    @FXML
    private TableColumn<FamilyMember, String> nameCol;

    @FXML
    private Button onAddition;

    @FXML
    private TableColumn<FamilyMember, String> phoneCol;

    @Getter
    private static Student student;

    @Getter
    private static List<FamilyMember> familyMemberList = new ArrayList<>();
    private static ObservableList<FamilyMember> observableList= FXCollections.observableArrayList();


    public static void setDataTableView(List<FamilyMember> list){
        familyMemberList=list;
        observableList.clear();
        for(FamilyMember s:familyMemberList){
            observableList.addAll(FXCollections.observableArrayList(s));
        }
    }

    public static void updateDataTableView(){
        DataRequest req= new DataRequest();
        req.add("id",student.getStudentId());
        DataResponse res = HttpRequestUtil.request("/api/familyMember/findByStudent",req);
        setDataTableView(JSON.parseArray(JSON.toJSONString(res.getData()),FamilyMember.class));
    }



    public void onAddition() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student-FamilyInformation-Change.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加家庭信息");
        stage.show();
    }

    public void initialize(){
        student=StudentManageController.SM_ButtonCellFactory.getStudent();
        dataTableView.setItems(observableList);
        DataRequest req= new DataRequest();
        req.add("id",student.getStudentId());
        DataResponse res = HttpRequestUtil.request("/api/familyMember/findByStudent",req);
        familyMemberList= JSON.parseArray(JSON.toJSONString(res.getData()),FamilyMember.class);

        RelationCol.setCellValueFactory(new PropertyValueFactory<>("relation"));
        WorkPlaceCol.setCellValueFactory(new PropertyValueFactory<>("unit"));
        birthdayCol.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("gender"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
//        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        DetectCol.setCellFactory(new FI_ButtonCellFactory<>("删除"));
        ChangeCol.setCellFactory(new FI_ButtonCellFactory<>("修改"));

        TableView.TableViewSelectionModel<FamilyMember> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
        setDataTableView(familyMemberList);
    }



}
class FI_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Getter
    @Setter
    private Integer userId= null;
    private final String property;
    public FI_ButtonCellFactory(@NamedArg("property") String var1) {
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


                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer memberId=StudentFamilyInformation_Controller.getFamilyMemberList().get(getIndex()).getMemberId();
                        DataRequest req=new DataRequest();
                        req.add("id",memberId);
                        DataResponse res=HttpRequestUtil.request("/api/familyMember/deleteById",req);
                        if (res.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            StudentFamilyInformation_Controller.updateDataTableView();
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