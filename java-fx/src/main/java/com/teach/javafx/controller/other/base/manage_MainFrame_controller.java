package com.teach.javafx.controller.other.base;

import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.AdminController.*;
import com.teach.javafx.controller.other.MessageDialog;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class manage_MainFrame_controller {
    /*
    public TableView dataTableView;
    public TableColumn courseNumberColumn;
    public TableColumn courseNameColumn;
    public TableColumn homeworkNameColumn;
    public TableColumn studentIdColumn;
    public TableColumn studentNameColumn;
    public TableColumn isSubmitColumn;
    public TableColumn submitTimeColumn;
    public TableColumn isCheckedColumn;
    public TableColumn checkColumn;
    public TableColumn fileColumn;
    public TableColumn homeworkScoreColumn;
    public TextField InquireField;
    public Button onInquire;
    public void onInquire(ActionEvent actionEvent) {
    }
     */
    public void initialize(){
//        Pagination pagination = new Pagination();
//        pagination.setPageCount(20);
//        pagination.setMaxPageIndicatorCount(10);
//        pagination.setCurrentPageIndex(5);
////设置样式
//        pagination.getStyleClass().add(Pagination.STYLE_CLASS_BULLET);
//        Scene scene=new Scene(pagination);
//        Stage stage=new Stage();
//        stage.setScene(scene);
//        stage.sizeToScene();
//        stage.show();
//        pagination.setPageFactory(new Callback<Integer, Node>() {
//            @Override
//            public Node call(Integer param) {
//                Label label = new Label("页面" + (param + 1));
//                label.setTextFill(Paint.valueOf("#0cc"));
//                label.setStyle("-fx-background-color: #6b3109");
//                label.setPrefWidth(200);
//                label.setPrefHeight(200);
//                label.setFont(Font.font(30));
//                label.setAlignment(Pos.CENTER);
//                return label;
//            }
//        });
    }
    @FXML
    void onAttendance() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/AttendanceManage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((Attendance_Manage_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onCourseManage() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseManage_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((CourseManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onDailyActivity() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivity_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((DailyActivity_Manage_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onFee() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/FeepManage.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((Fee_Manage_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onHomework() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeWork_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((HomeworkController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onHonor() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Honorpanel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((HonorManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @FXML
    void onInnovativePractice() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((InnovativePractice_Manage_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void onLeaveInformation() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/LeaveInformation_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((LeaveInfomationController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void onScore() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Score_panel.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((ScoreController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @FXML
    void onStudentManage() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/StudentManage_Frame.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((StudentManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void onTeacherManage() {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/TeacherManage_Frame.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((TeacherManageController) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void onExit() {
        int ret = MessageDialog.choiceDialog("请确认是否退出系统?");
        if(ret == MessageDialog.CHOICE_YES) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("base/login-view.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), 596, 358);
                MainApplication.loginStage("Login", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        else return;
    }

    public void onChangePassword() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Password_panel.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 300, 260);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("修改密码");
        stage.show();
    }
}
