package com.teach.javafx.controller.base;

import com.alibaba.fastjson2.JSON;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.BeforeUniversity;
import com.teach.javafx.models.DO.FamilyMember;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.models.DTO.StudentInfo;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
@Setter
@Getter
public class StudentChange_Controller {

    @FXML
    private TextField cardField;

    @FXML
    private TextField NameField;

    @FXML
    private TextField PoliticsField;

    @FXML
    private TextField SexField;

    @FXML
    private TextField numField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField classField;

    @FXML
    private TextField deptField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField majorField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button onCancel;

    @FXML
    private DatePicker birthdayPick;

    @FXML
    private TextField graduatedProvinceCol;

    @FXML
    private TextField graduatedSchoolCol;

    @Getter
    private static Student student;

    private Long studentId = null;
    private Integer personId = null;
    private static int index=0;

    private ObservableList<StudentInfo> observableList= FXCollections.observableArrayList();
    private static List<Student> studentList = new ArrayList<>();

    public void initialize(){
        student =StudentManageController.SM_ButtonCellFactory.getStudent();
        DataRequest req=new DataRequest();
        req.add("id",student.getStudentId());
        DataResponse res = HttpRequestUtil.request("/api/beforeUniversity/findByStudent",req);
        BeforeUniversity beforeUni= JSON.parseObject(JSON.toJSONString(res.getData()), BeforeUniversity.class);


        studentList=StudentManageController.getStudentList();
        numField.setText(String.valueOf(studentList.get(getIndex()).getStudentId()));
        NameField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getName()));
        cardField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getCard()));
        PoliticsField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getPoliticalStatus()));
        SexField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getGender()));
        addressField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getAddress()));
        classField.setText(String.valueOf(studentList.get(getIndex()).getClassName()));
        emailField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getEmail()));
        majorField.setText(String.valueOf(studentList.get(getIndex()).getMajor()));
        phoneField.setText(String.valueOf(studentList.get(getIndex()).getPerson().getPhone()));
        graduatedSchoolCol.setText(beforeUni.getGraduatedSchool());
        graduatedProvinceCol.setText(beforeUni.getGraduatedProvince());
    }

    public int getIndex() {
        return index;
    }

    public static void setIndex(int index){
        StudentChange_Controller.index =index;
    }

    public void onConformation(ActionEvent actionEvent) {
        if( numField.getText().equals("")) {
            MessageDialog.showDialog("学号不能为空");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        Student s=studentList.get(getIndex());
        setStudent(s);
        DataRequest req=new DataRequest();
        req.add("student",s);
        DataResponse res = HttpRequestUtil.request("/api/student/addOrUpdateStudent",req);

        BeforeUniversity beforeUniversity=new BeforeUniversity();
        beforeUniversity.setGraduatedProvince(graduatedProvinceCol.getText());
        beforeUniversity.setGraduatedSchool(graduatedSchoolCol.getText());
        beforeUniversity.setStudent(s);
        DataRequest request=new DataRequest();
        request.add("beforeUniversity",beforeUniversity);
        DataResponse response=HttpRequestUtil.request("/api/beforeUniversity/add",request);


        MessageDialog.showDialog("修改成功！");
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();

        StudentManageController.updateDataTableView();
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setStudent(Student s) {
        Person p=s.getPerson();
        s.setStudentId(Long.valueOf(numField.getText()));
        p.setIntroduce("");
        p.setNumber(Long.valueOf(numField.getText()));
        p.setName(NameField.getText());
        p.setDept(deptField.getText());
        s.setMajor(majorField.getText());
        s.setClassName(classField.getText());
        p.setCard(cardField.getText());
        p.setGender(SexField.getText());
        p.setBirthday(birthdayPick.getEditor().getText());
        p.setEmail(emailField.getText());
        p.setPhone(phoneField.getText());
        p.setAddress(addressField.getText());
        p.setPoliticalStatus(PoliticsField.getText());
        s.setPerson(p);
    }
}
