package edu.system;

import constants.Constants;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import passHash.PassHash;

import java.io.*;
import java.net.Socket;

public class ClientMain extends Application {
    static Logger log = LogManager.getLogger(ClientMain.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ClientMain.class.getResource("fxml/login-view.fxml"));
        Parent root = fxmlLoader.load();
        ClientLogic.getInstance().setLogin(fxmlLoader, stage);
        Scene scene = new Scene(root, 320, 240);
        Image icon = new Image(String.valueOf(ClientMain.class.getResource("images/university logo.png")));
        stage.setOnCloseRequest(event ->{
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("close window");
            alert.setHeaderText("confirmation");
            alert.setContentText("Are you sure you want to close a window?");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(String.valueOf(ClientMain.class.getResource("images/university logo.png"))));
            if (alert.showAndWait().get() == ButtonType.OK){
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(),Client.getClient().getName(),"kill the client"));
                stage.close();
            }
        });
        int port = Constants.CONFIG.getProperty(Integer.class, "serverPort");
        Socket socket = new Socket("localhost", port);
        Client client = new Client(socket);
        new Thread(client).start();
        stage.getIcons().add(icon);
        stage.setTitle("Education System");
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {
        log.info("launch program");
        launch();

    }
}