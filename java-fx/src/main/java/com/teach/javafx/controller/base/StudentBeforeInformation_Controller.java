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
    private Button onChange;

    @FXML
    private Button onReturn;

    @FXML
    private TextField phoneColumn;

    @FXML
    private TextField provinceColumn;

    @FXML
    private TextField schoolColumn;


    @Getter
    private static BeforeUniversity beforeUni;

    @Getter
    private static Student student;



    public  void  initialize(){
        student =StudentManageController.SM_ButtonCellFactory.getStudent();
        DataRequest req=new DataRequest();
        req.add("id",student.getStudentId());
        DataResponse res = HttpRequestUtil.request("/api/beforeUniversity/findByStudent",req);
        beforeUni= JSON.parseObject(JSON.toJSONString(res.getData()), BeforeUniversity.class);

        addressColumn.setText(getStudent().getPerson().getAddress());
        emailColumn.setText(getStudent().getPerson().getEmail());
        nameColumn.setText(getStudent().getPerson().getName());
        phoneColumn.setText(getStudent().getPerson().getPhone());
        provinceColumn.setText(beforeUni.getGraduatedProvince());
        schoolColumn.setText(beforeUni.getGraduatedSchool());
    }

    public void onReturn() {
        Stage stage = (Stage) onReturn.getScene().getWindow();
        stage.close();
    }
}
