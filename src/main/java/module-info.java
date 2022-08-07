module edu.system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires json.simple;
    requires log4j;
    requires java.desktop;
    requires javafx.swing;

    exports message;
    exports edu.system;
    opens edu.system to javafx.fxml;
    exports currentUser;
    opens currentUser to javafx.fxml;
    exports gui;
    opens gui to javafx.fxml;
    exports server;
    opens server to javafx.fxml;
    opens message to com.google.gson;
}