package com.teach.javafx.controller.other.base;

import com.teach.javafx.models.DO.Course;
import com.teach.javafx.models.DTO.AttendanceInfoInfo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseSelectedS_Controller extends manage_MainFrame_controller{

    @FXML
    public TableView<Map> courseView;
    @FXML
    public TableColumn<Map,String> timeFixedColumn;
    @FXML
    public TableColumn<Map,String> week1;

    private static List<Map> courseList = new ArrayList<>();
    private static ObservableList<Map> observableList= FXCollections.observableArrayList();


    public void initialize() {
        courseView.setItems(observableList);
        timeFixedColumn.setCellValueFactory(new MapValueFactory<>("time"));
        week1.setCellValueFactory(new MapValueFactory<>("week1"));
        Map map1=new HashMap<>();
        map1.put("time","第一大节");
        map1.put("week1","高数");
        observableList.addAll(FXCollections.observableArrayList(map1));

    }
}
