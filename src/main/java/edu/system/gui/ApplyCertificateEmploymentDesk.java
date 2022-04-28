package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ApplyCertificateEmploymentDesk {
    public Button submitRequest;
    @FXML
    protected Label studentNumber;
    @FXML
    protected Label fieldName;
    @FXML
    protected Label universityName;
    @FXML
    protected Label dateName;
    @FXML
    protected Label warning;
    @FXML
    protected Label userName;

    Stage stage;

    static Logger log = LogManager.getLogger(HelloApplication.class);
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    public void initialize() throws IOException, ParseException {
        log.info("Open apply for certificate employment page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened!");
                throw new RuntimeException(e);
            }
        } );
        fieldName.setText(getUserFaculty());
        universityName.setText("Sharif University Of Technology");
        studentNumber.setText(getUserStudentNumber());
        userName.setText(getUsername());
        dateName.setText("00/00/00");


    }
    protected String getUserDegree() throws IOException, ParseException {
        log.info("Current user get degree");
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
    }
    public void submissionClicked(ActionEvent actionEvent){
        log.info("Submission");
        warning.setText("Done");
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get user name");
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
    protected String getUserFaculty() throws IOException, ParseException {
        log.info("Get user faculty");
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().getUserFaculty(massageStudentUndergraduateDesk);
    }
    protected String getUserStudentNumber() throws IOException, ParseException {
        log.info("Get student number");
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().getUserStudentNumber(massageStudentUndergraduateDesk);
    }
    public void logOut() throws IOException {
        log.info("Logged out out of time");
        stage = ((Stage) (userName).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }


}
