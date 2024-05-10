package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.MainApplication;
import com.teach.javafx.models.DO.BeforeUniversity;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentBeforeInformation_Controller {

    @FXML
    private TextField addressColumn;

    @FXML
    private TextField emailColumn;

    @FXML
    private TextField nameColumn;

    @FXML
    private Button onConfirmation;

    @FXML
    private Button onReturn;

    @FXML
    private TextField phoneColumn;

    @FXML
    private TextField provinceColumn;

    @FXML
    private TextField schoolColumn;



    @Getter
    private static BeforeUniversity beforeUniversity;

    @Getter
    private static Student student;

    @Getter
    private static List<BeforeUniversity> beforeUniversitylist = new ArrayList<>();
    private static ObservableList<BeforeUniversity> observableList= FXCollections.observableArrayList();
    public Button onAddition;

    public void onAddition(ActionEvent actionEvent) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student-BeforeInformation-Addition.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加入学前信息");
        stage.show();
    }
    public  void  initialize(){
        student =StudentManageController.SM_ButtonCellFactory.getStudent();
        DataRequest req=new DataRequest();
        req.add("id",student.getStudentId());
        DataResponse res = HttpRequestUtil.request("/api/beforeUniversity/findByStudent",req);
        beforeUniversitylist= JSON.parseArray(JSON.toJSONString(res.getData()), BeforeUniversity.class);

        addressColumn.setText(beforeUniversity.getStudent().getPerson().getAddress());
        emailColumn.setText(beforeUniversity.getStudent().getPerson().getEmail());
        nameColumn.setText(beforeUniversity.getStudent().getPerson().getName());
        phoneColumn.setText(beforeUniversity.getStudent().getPerson().getPhone());
        provinceColumn.setText(beforeUniversity.getGraduatedProvince());
        schoolColumn.setText(beforeUniversity.getGraduatedSchool());
    }

    public void onConfirmation(ActionEvent actionEvent) {
    }

    public void onReturn(ActionEvent actionEvent) {
    }
}
