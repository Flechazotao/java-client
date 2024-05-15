package com.teach.javafx.controller.AdminController;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.InnovativePractice;
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

public class InnovativePractice_Change_Controller {

    public Button onCancel;
    @FXML
    public TextField activityNameField;
    @FXML
    public DatePicker beginTime;
    @FXML
    public DatePicker endTime;
    @FXML
    public TextField studentField;
    @FXML
    public TextField teacherNameField;
    @FXML
    public TextField typeField;
    @FXML
    public TextField achievementField;
//    @FXML
//    public TextField fileField;

    @Getter
    private static InnovativePractice innovativePractice;

    @Getter
    @Setter
    private static int index=0;

    public void initialize(){
        innovativePractice = InnovativePractice_Manage_Controller.getInnovativePracticeList().get(index);

        activityNameField.setText(innovativePractice.getActivityName());
        beginTime.setValue(LocalDate.parse(innovativePractice.getBeginTime()));
        endTime.setValue(LocalDate.parse(innovativePractice.getEndTime()));
        studentField.setText("");
        teacherNameField.setText(innovativePractice.getTeacherName());
        typeField.setText(innovativePractice.getType());
        achievementField.setText(innovativePractice.getAchievement());
    }

    public void onChange(ActionEvent actionEvent) {
        if( activityNameField.getText().equals("")) {
            MessageDialog.showDialog("项目名称不能为空");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            return;
        }

        InnovativePractice ip=innovativePractice;
        setInnovativePractice(ip);
        DataRequest req=new DataRequest();
        req.add("innovativePractice",ip);
        DataResponse res = HttpRequestUtil.request("/api/innovativePractice/add",req);
        if(res.getCode()==401){
            MessageDialog.showDialog("信息不完整！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
        }
        else if(res.getCode()==200){
            MessageDialog.showDialog("修改成功！");
            Stage stage = (Stage) onCancel.getScene().getWindow();
            stage.close();
            InnovativePractice_Manage_Controller.updateDataTableView();
        }
    }

    public void onCancel(ActionEvent actionEvent) {
        Stage stage = (Stage) onCancel.getScene().getWindow();
        stage.close();
    }

    private void setInnovativePractice(InnovativePractice ip) {
        ip.setActivityName(activityNameField.getText());
        ip.setBeginTime(beginTime.getValue()==null ? LocalDate.now().toString() : beginTime.getValue().toString());
        ip.setEndTime(endTime.getValue()==null ? LocalDate.now().toString() : endTime.getValue().toString());
        ip.setTeacherName(teacherNameField.getText());
        ip.setType(typeField.getText());
        ip.setAchievement(achievementField.getText());
    }
}
