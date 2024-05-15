package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.Fee;
import com.teach.javafx.models.DO.Person;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class Fee_Addition_Controller {

    @FXML
    private TextField feeTypeField;
    @FXML
    private TextField studentIdField;
    @FXML
    private TextField nameField;
    @FXML
    private DatePicker dayPicker;
    @FXML
    private TextField moneyField;
    @FXML
    private TextField descriptionField;

    @FXML
    private Button onCancel;

    @FXML
    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onConfirmation(ActionEvent actionEvent) {
        if(studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }
        Fee fee=getFee();
        DataRequest req=new DataRequest();
        req.add("fee",fee);
        DataResponse res = HttpRequestUtil.request("/api/fee/add",req);
        if(res.getCode()==401) {
            MessageDialog.showDialog("该消费已存在！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }else if(res.getCode()==404) {
            MessageDialog.showDialog("学号错误");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else {
            MessageDialog.showDialog("提交成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            Fee_Manage_Controller.updateDataTableView();
        }
    }

    private Fee getFee() {
        Fee fee=new Fee();
        Student s=new Student();
        Person person=new Person();
        fee.setFeeType(feeTypeField.getText());
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(nameField.getText());
        s.setPerson(person);
        fee.setStudent(s);
        fee.setDay(dayPicker.getValue()==null ? LocalDate.now().toString() : dayPicker.getValue().toString());
        fee.setMoney(moneyField.getText().isEmpty()?0.0:Double.parseDouble(moneyField.getText()));
        fee.setDescription(descriptionField.getText());
        return fee;
    }
}
