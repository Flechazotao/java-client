package com.teach.javafx.controller.base;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.stage.Stage;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class SM_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Getter
    @Setter
    private Integer userId= null;

    private Integer studentId=null;

    private final String property;
    public SM_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }
    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);
            {
                button.setOnAction(event -> {

                    FXMLLoader fxmlLoader = null;

                    if (property=="删除"){
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if(ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        DataRequest req = new DataRequest();
                        studentId=getIndex();
                        req.add("studentId", studentId);
                        com.teach.javafx.models.DTO.DataResponse res = HttpRequestUtil.request("/api/student/deleteStudent",req);
                        if(res.getCode() == 401) {
                            MessageDialog.showDialog("信息不完整!");
                        }
                        else if (res.getCode() == 404){
                            MessageDialog.showDialog("该学生不存在!");
                        }
                        else {
                            MessageDialog.showDialog("删除成功!");

                        }

                    }

                    else if (property=="修改") {
                        studentId= getIndex();//获取正在编辑的单元格所在行序号
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_Change_panel.fxml"));

                    }

                    else if (property=="查看入学前信息"){
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_BeforeInformation_Change.fxml"));
                    }
                    else if (property=="查看家庭信息") {
                        fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_FamilyInformation.fxml"));
                    }

                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load(), 1045, 677);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.setTitle("添加学生");
                    stage.show();

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