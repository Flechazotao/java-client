package com.teach.javafx.controller.base;

import com.teach.javafx.controller.other.MessageDialog;
import com.teach.javafx.models.DTO.DataRequest;
import com.teach.javafx.models.DTO.DataResponse;
import com.teach.javafx.request.HttpRequestUtil;
import javafx.beans.NamedArg;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.util.Callback;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

class FI_ButtonCellFactory<S, T> implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    @Getter
    @Setter
    private Integer userId = null;
    private final String property;

    public FI_ButtonCellFactory(@NamedArg("property") String var1) {
        this.property = var1;
    }

    @Override
    public TableCell<S, T> call(TableColumn<S, T> param) {
        return new TableCell<S, T>() {
            private Button button = new Button(property);

            {
                button.setOnAction(event -> {

                    FXMLLoader fxmlLoader = null;

                    if (Objects.equals(property, "修改")) {


                    } else if (Objects.equals(property, "删除")) {
                        int ret = MessageDialog.choiceDialog("确认要删除吗?");
                        if (ret != MessageDialog.CHOICE_YES) {
                            return;
                        }
                        Integer memberId = FamilyInformation_Controller.getFamilyMemberList().get(getIndex()).getMemberId();
                        DataRequest req = new DataRequest();
                        req.add("id", memberId);
                        DataResponse res = HttpRequestUtil.request("/api/familyMember/deleteById", req);
                        if (res.getCode() == 401) {
                            MessageDialog.showDialog("信息不完整!");
                        } else {
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
