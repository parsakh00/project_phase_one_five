module edu.system {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires json.simple;

    opens edu.system to javafx.fxml;
    opens edu.system.gui to javafx.fxml;
    exports edu.system;
    exports edu.system.logic;
    opens edu.system.logic to javafx.fxml;
}