module com.teach.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires javafx.swing;
    requires org.apache.pdfbox;
    requires java.logging;
    requires com.google.gson;
    requires java.net.http;
    requires spring.security.crypto;
    requires javafx.media;
    requires lombok;
    requires jakarta.persistence;
    requires com.alibaba.fastjson2;

    opens com.teach.javafx to com.alibaba.fastjson2;
    opens com.teach.javafx.models.DO to com.google.gson,com.alibaba.fastjson2;
    opens com.teach.javafx.models.DTO to com.alibaba.fastjson2,com.google.gson;
    opens com.teach.javafx.request to com.google.gson, javafx.fxml,com.alibaba.fastjson2;
    opens com.teach.javafx.controller.base to com.google.gson, javafx.fxml;
    opens com.teach.javafx.controller to com.google.gson, javafx.fxml;
    opens com.teach.javafx.useless.teach.models to javafx.base,com.google.gson;
    opens com.teach.javafx.useless.teach.util to com.google.gson, javafx.fxml;
    opens com.teach.javafx.controller.tryController to  javafx.graphics;

    exports com.teach.javafx.controller.tryController;
    exports com.teach.javafx;
    exports com.teach.javafx.models.DTO;
    exports com.teach.javafx.models.DO;
    exports com.teach.javafx.controller;
    exports com.teach.javafx.controller.base;
    exports com.teach.javafx.request;
    exports com.teach.javafx.useless.teach.util;
    exports com.teach.javafx.useless.request;
    opens com.teach.javafx.useless.request;
    exports com.teach.javafx.useless.controller;
    opens com.teach.javafx.useless.controller ;

}