package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.MassageLogin;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class EduWithdrawRequest {
    public TextField choiceUser;
    public Label source;
    Stage stage;
    @FXML
    protected ChoiceBox<String> chooseUser;

    protected String[] userNames;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(HelloApplication.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open withdrawal request as a education assistant");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened!", e);
                throw new RuntimeException(e);
            }
        } );
    getUsers();
    chooseUser.getItems().addAll(userNames);
    chooseUser.setOnAction(this :: getUser);
    }
    public void logOut() throws IOException {
        log.info("Logged out , out of time");
        stage = ((Stage) (source).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    protected void getUser(ActionEvent actionEvent){
        log.info("Get user name from choice box");
        choiceUser.setText(chooseUser.getValue());
    }
    protected void getUsers() throws IOException, ParseException {
        log.info("Get users");
        MassageLogin massageGetUsers = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        userNames =  Controller.getInstance().listOfUser(massageGetUsers);


    }
    public void returnBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Return back Button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (source).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();

    }
    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get user degree");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    protected void reject() throws IOException, ParseException {
        log.info("Reject withdrawal request");
        CurrentUser.getInstance().setUser(choiceUser.getText());
        MassageLogin massageStudentMasterDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null,null);
        Controller.getInstance().rejection(massageStudentMasterDesk);
    }
    protected void acception() throws IOException, ParseException {
        log.info("Accept user withdrawal request");
        CurrentUser.getInstance().setUser(choiceUser.getText());
        MassageLogin massageStudentMasterDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null,null);
        Controller.getInstance().accept(massageStudentMasterDesk);
    }
    protected void condition() throws IOException, ParseException {
        log.info("Change user condition");
        CurrentUser.getInstance().setUser(choiceUser.getText());
        MassageLogin massageStudentMasterDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        Controller.getInstance().changeCondition(massageStudentMasterDesk);
    }
    public void rejection(ActionEvent actionEvent) throws IOException, ParseException {
        reject();
    }
    public void accept(ActionEvent actionEvent) throws IOException, ParseException {
        condition();
        acception();
    }
}
