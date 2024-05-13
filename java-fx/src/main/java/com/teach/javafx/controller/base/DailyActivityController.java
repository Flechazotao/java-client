package com.teach.javafx.controller.base;

import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DO.DailyActivity;
import com.teach.javafx.models.DO.FamilyMember;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DailyActivityController extends manage_MainFrame_controller {
    @FXML
    private TextField InquireField;

    @FXML
    private TableColumn<DailyActivity, String>activityTypeColumn;

    @FXML
    private TableColumn<DailyActivity, String> beginTimeColumn;

    @FXML
    private TableColumn<DailyActivity, String>changeCol;

    @FXML
    private TableView<?> dataTableView;

    @FXML
    private TableColumn<DailyActivity, String> deleteCol;

    @FXML
    private TableColumn<DailyActivity, String> endTimeColumn;

    @FXML
    private TableColumn<DailyActivity, String>locationColumn;

    @FXML
    private TableColumn<DailyActivity, String>locationColumn1;

    @FXML
    private TableColumn<DailyActivity, String> nameColumn;

    @FXML
    private Button onAddDailyActivity;

    @FXML
    private Button onInquire;


    @FXML
    void onInquire(ActionEvent event) {

    }

    @FXML
    void onAddDailyActivity(ActionEvent event) {

        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivity_Addition.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 600, 677);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("修改日常活动信息");
        stage.show();

    }

}



class DA_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Getter
    @Setter
    private  static Integer index= null;
    private final String property;
    public DA_ButtonCellFactory(@NamedArg("property") String var1) {
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
                        index=getIndex();
                        List<FamilyMember> familyMemberList = FamilyInformation_Controller.getFamilyMemberList();
                        Integer memberid=familyMemberList.get(getIndex()).getMemberId();
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivity_Change.fxml"));
                        Scene scene = null;
                        try {
                            scene = new Scene(fxmlLoader.load(), 600, 677);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.setTitle("修改日常活动信息");
                        stage.show();

                    }
                    else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        List<DailyActivity> dailyActivityList = new ArrayList<>();
                        DataResponse response=HttpRequestUtil.request("/api/dailyActivity/findAll",new DataRequest());

                        if (response.getCode()==401){
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");
                            FamilyInformation_Controller.updateDataTableView();
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
