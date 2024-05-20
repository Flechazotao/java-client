package com.teach.javafx.controller.other.base;

import com.teach.javafx.AppStore;
import com.teach.javafx.MainApplication;
import com.teach.javafx.controller.StudentController.*;
import com.teach.javafx.controller.TeacherController.*;
import com.teach.javafx.controller.other.MessageDialog;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class Teacher_MainFrame_controller {

        @FXML
        void onAttendance() {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Attendance_panel-T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((Attendance_panel_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        public void onExit(ActionEvent actionEvent) {
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

        public void onChangePassword(ActionEvent actionEvent) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Password_panel.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 300, 260);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("修改密码");
            stage.show();
        }

        public void onFee(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Fee_T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((Fee_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onLeaveInformation(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/LeaveInformation_panel-T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((LeaveInformation_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onDailyActivity(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/DailyActivity_panel-T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((DailyActivity_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onHonor(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Honorpanel_S.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((Honor_S_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onInnovativePractice(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/InnovativePractice_T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((InnovativePractice_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onScore(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Score_panel_T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((Score_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onHomework(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/HomeWork_T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((Homework_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

        public void onCourseManage(ActionEvent actionEvent) {
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/CourseManage_T.fxml"));
            try {
                Scene scene = new Scene(fxmlLoader.load(), -1, -1);
                AppStore.setMainFrameController((Course_Controller) fxmlLoader.getController());
                MainApplication.resetStage("教学管理系统", scene);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    public void onStudentInfo(ActionEvent actionEvent) {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("Base_Fxml/Student_Information_T.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load(), -1, -1);
            AppStore.setMainFrameController((Student_Information_Controller) fxmlLoader.getController());
            MainApplication.resetStage("教学管理系统", scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

