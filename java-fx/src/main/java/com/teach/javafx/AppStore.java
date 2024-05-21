package com.teach.javafx;


import com.teach.javafx.controller.StudentController.Fee_S_Controller;
import com.teach.javafx.controller.other.base.Teacher_MainFrame_controller;
import com.teach.javafx.controller.other.base.manage_MainFrame_controller;
import com.teach.javafx.controller.other.base.student_MainFrame_controller;
import com.teach.javafx.models.DO.Student;
import com.teach.javafx.request.JwtResponse;

/**
 * 前端应用全程数据类
 * JwtResponse jwt 客户登录信息
 */
public class AppStore {
    private static JwtResponse jwt;
    private static manage_MainFrame_controller manage_MainFrame_controller;

    private static student_MainFrame_controller student_MainFrame_controller;
    private static Teacher_MainFrame_controller teacherMainFrameController;

    private AppStore(){
    }

    public static JwtResponse getJwt() {
        return jwt;
    }

    public static void setJwt(JwtResponse jwt) {
        AppStore.jwt = jwt;
    }

    public static manage_MainFrame_controller getMainFrameController() {
        return manage_MainFrame_controller;
    }

    public static void setMainFrameController(manage_MainFrame_controller mainFrameController) {
        AppStore.manage_MainFrame_controller = mainFrameController;
    }

    public static void setMainFrameController(student_MainFrame_controller controller) {
        AppStore.student_MainFrame_controller = controller;
    }
    public static void setMainFrameController(Teacher_MainFrame_controller controller) {
        AppStore.teacherMainFrameController = controller;
    }

}
