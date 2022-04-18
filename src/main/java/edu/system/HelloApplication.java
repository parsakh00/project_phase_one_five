package edu.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Image icon = new Image(String.valueOf(HelloApplication.class.getResource("university logo.png")));
        stage.setOnCloseRequest(event ->{
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("close window");
            alert.setHeaderText("confirmation");
            alert.setContentText("Are you sure you want to close a window?");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(String.valueOf(HelloApplication.class.getResource("university logo.png"))));
            if (alert.showAndWait().get() == ButtonType.OK){
                stage.close();
            }
        });

        stage.getIcons().add(icon);
        stage.setTitle("Education System");
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}