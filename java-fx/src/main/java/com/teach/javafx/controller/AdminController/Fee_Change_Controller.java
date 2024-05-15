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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class Fee_Change_Controller {

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
    @Getter
    @Setter
    private static Integer index;

    @Getter
    private static Fee fee;

    public void initialize(){
        fee = Fee_Manage_Controller.getFeeList().get(index);

        feeTypeField.setText(fee.getFeeType());
        studentIdField.setText(String.valueOf(fee.getStudent().getStudentId()));
        nameField.setText(fee.getStudent().getPerson().getName());
        dayPicker.setValue(LocalDate.parse(fee.getDay()));
        moneyField.setText(String.valueOf(fee.getMoney()));
        descriptionField.setText(fee.getDescription());
    }

    public void onConfirmation(ActionEvent actionEvent) {
        if( studentIdField.getText().equals("")) {
            MessageDialog.showDialog("学号为空，不能添加");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        setFee(fee);
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

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setFee(Fee fee) {
        Student s=fee.getStudent();
        Person person=s.getPerson();
        fee.setFeeType(feeTypeField.getText());
        s.setStudentId(Long.valueOf(studentIdField.getText()));
        person.setNumber(Long.valueOf(studentIdField.getText()));
        person.setName(nameField.getText());
        s.setPerson(person);
        fee.setStudent(s);
        fee.setDay(dayPicker.getValue()==null ? LocalDate.now().toString() : dayPicker.getValue().toString());
        fee.setMoney(Double.valueOf(moneyField.getText()));
        fee.setDescription(descriptionField.getText());
    }
}
