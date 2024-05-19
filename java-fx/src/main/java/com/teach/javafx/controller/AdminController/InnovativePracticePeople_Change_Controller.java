package com.teach.javafx.controller.AdminController;

import com.teach.javafx.models.DO.Student;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import lombok.Setter;

import java.util.List;

public class InnovativePracticePeople_Change_Controller {
    @FXML
    public ComboBox<String> studentId;
    @FXML
    public ComboBox<String> studentName;
    @FXML
    public Button onConfirmation;
    @FXML
    public Button onCancel;

    @Setter
    public static List<Student> addedStudents;

    @Setter
    public static List<Student> students;

    public void initialize(){
        studentId.getItems().clear();
        studentName.getItems().clear();
        studentId.getItems().add("请选择学号");
        studentId.getSelectionModel().select(0);
        studentName.getItems().add("请选择学生");
        studentName.getSelectionModel().select(0);
        for(Student student:students){
            studentId.getItems().add(student.getStudentId().toString());
            studentName.getItems().add(student.getPerson().getName());
        }
    }


    public void studentId(ActionEvent actionEvent) {
        studentName.getSelectionModel().select(studentId.getSelectionModel().getSelectedIndex());
    }

    public void studentName(ActionEvent actionEvent) {
        studentId.getSelectionModel().select(studentName.getSelectionModel().getSelectedIndex());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        InnovativePractice_Change_Controller.getDeleteStudents().add(students.get(studentId.getSelectionModel().getSelectedIndex()-1));
        addedStudents.remove(students.get(studentId.getSelectionModel().getSelectedIndex()-1));
        ((Stage)onCancel.getScene().getWindow()).close();
    }

    public void onCancel(ActionEvent actionEvent) {
        ((Stage)onCancel.getScene().getWindow()).close();
    }

}
