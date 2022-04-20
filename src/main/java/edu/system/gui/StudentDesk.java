package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageStudentDesk;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class StudentDesk {

    Stage stage;
    @FXML
    protected AnchorPane studentView;
    @FXML
    protected Label email;
    @FXML
    protected Label timeDate;
    @FXML
    protected Label username;
    @FXML
    protected Button logOut;
    @FXML
    protected Pane noidea;
    @FXML
    protected ImageView userImage;


    public void initialize() throws IOException, ParseException {
        setUserImage();
        timeDisplay();
        username.setText("User : " + getUsername());
        email.setText("Email : " + getEmail());

    }

    public void timeDisplay(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    protected String getEmail() throws IOException, ParseException {
        MassageStudentDesk massageStudentDesk = new MassageStudentDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().studentDeskEmail(massageStudentDesk);

    }

    protected String getUsername() throws IOException, ParseException {
        MassageStudentDesk massageStudentDesk = new MassageStudentDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().studentDeskUserName(massageStudentDesk);
    }

    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void setUserImage() throws IOException, ParseException {
        userImage = new ImageView(String.valueOf(HelloApplication.class.getResource("images/" + getUsername() + ".png")));
        userImage.setFitHeight(160);
        userImage.setFitWidth(140);
        noidea.getChildren().add(userImage);
    }
}
