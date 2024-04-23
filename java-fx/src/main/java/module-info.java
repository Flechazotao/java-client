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

    opens com.teach.javafx to javafx.fxml;
    opens com.teach.javafx.models.DO to com.google.gson,com.alibaba.fastjson2;
    opens com.teach.javafx.models.DTO to com.alibaba.fastjson2;
    opens com.teach.javafx.request to com.google.gson, javafx.fxml,com.alibaba.fastjson2;
    opens com.teach.javafx.controller.base to com.google.gson, javafx.fxml;
    opens com.teach.javafx.controller to com.google.gson, javafx.fxml;
    opens com.teach.javafx.useless.teach.models to javafx.base,com.google.gson;
    opens com.teach.javafx.useless.teach.util to com.google.gson, javafx.fxml;
    opens com.teach.javafx.useless.teach.payload.request to com.google.gson, javafx.fxml,com.alibaba.fastjson2;
    opens com.teach.javafx.useless.teach.payload.response to com.google.gson, javafx.fxml,com.alibaba.fastjson2;
    opens com.teach.javafx.controller.tryController to  javafx.graphics;

    exports com.teach.javafx.controller.tryController;
    exports com.teach.javafx;
    exports com.teach.javafx.models.DTO;
    exports com.teach.javafx.models.DO to com.google.gson,com.alibaba.fastjson2;
    exports com.teach.javafx.controller;
    exports com.teach.javafx.controller.base;
    exports com.teach.javafx.request to com.alibaba.fastjson2;
    exports com.teach.javafx.useless.teach.util;
    opens com.teach.javafx.useless to com.alibaba.fastjson2, com.google.gson, javafx.base;
    exports com.teach.javafx.useless.request to com.alibaba.fastjson2;
    opens com.teach.javafx.useless.request to com.alibaba.fastjson2, com.google.gson, javafx.fxml;
    exports com.teach.javafx.useless.controller;
    opens com.teach.javafx.useless.controller to com.google.gson, javafx.fxml;

}