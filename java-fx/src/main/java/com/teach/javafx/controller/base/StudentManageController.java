package com.teach.javafx.controller.base;

import com.teach.javafx.MainApplication;
import com.teach.javafx.useless.Student;
import com.teach.javafx.useless.request.SQLiteJDBC;
import com.teach.javafx.useless.teach.util.CommonMethod;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class StudentManageController extends manage_MainFrame_controller {
    @FXML
    private TableView<Student> dataTableView;

    @FXML
    private TableColumn<Student, String > classNameColumn;

    @FXML
    private TableColumn<Student, String> deptColumn;

    @FXML
    private TableColumn<Student, String> genderColumn;

    @FXML
    private TableColumn<Student, String> majorColumn;

    @FXML
    private TableColumn<Student, String> nameColumn;

    @FXML
    private TableColumn<Student, Integer> numColumn;


    public void initialize() {
        numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        deptColumn.setCellValueFactory(new PropertyValueFactory<>("dept"));
        majorColumn.setCellValueFactory(new PropertyValueFactory<>("major"));
        classNameColumn.setCellValueFactory(new PropertyValueFactory<>("className"));
        genderColumn.setCellValueFactory(new PropertyValueFactory<>("genderName"));

        TableView.TableViewSelectionModel<Student> tsm = dataTableView.getSelectionModel();
        ObservableList<Integer> list = tsm.getSelectedIndices();
    }

    protected void changeStudentInfo() {
        Student s = dataTableView.getSelectionModel().getSelectedItem();
        if(s == null) {
            clearPanel();
            return;
        }

    }

    public void clearPanel(){

    }


    public void onAddStudent() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student-Addition-panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 677);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("添加学生");
        stage.show();
    }


    public void onInquire() {


    }
}
