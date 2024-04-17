package com.teach.javafx;


import com.teach.javafx.controller.base.manage_MainFrame_conrtoller;
import com.teach.javafx.request.JwtResponse;

/**
 * 前端应用全程数据类
 * JwtResponse jwt 客户登录信息
 */
public class AppStore {
    private static JwtResponse jwt;
    private static manage_MainFrame_conrtoller manage_MainFrame_conrtoller;
    private AppStore(){
    }

    public static JwtResponse getJwt() {
        return jwt;
    }

    public static void setJwt(JwtResponse jwt) {
        AppStore.jwt = jwt;
    }

    public static manage_MainFrame_conrtoller getMainFrameController() {
        return manage_MainFrame_conrtoller;
    }

    public static void setMainFrameController(manage_MainFrame_conrtoller mainFrameController) {
        AppStore.manage_MainFrame_conrtoller = mainFrameController;
    }
}
