package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import message.Message;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import java.util.Objects;

public class EduMinorRequests {

    Stage stage;

    public TextField choiceUser;
    public Label source;
    @FXML
    protected ChoiceBox<String> chooseUser;
    protected String[] userNames;

    static Logger log = LogManager.getLogger(ClientMain.class);
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void initialize() throws IOException, ParseException {
        log.info("Open education assistant minor request");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened", e);
                throw new RuntimeException(e);
            }
        });
        getUsers();
        chooseUser.getItems().addAll(userNames);
        chooseUser.setOnAction(this::getUser);
    }

    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (source).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    protected void getUser(ActionEvent actionEvent) {
        log.info("Get user from choice box");
        choiceUser.setText(chooseUser.getValue());
    }

    protected void getUsers() throws IOException, ParseException {
        MassageInNetwork GetUsers = new MassageInNetwork(CurrentUser.getInstance().getUserName(), getUserFaculty(), null, null, null, null, null, null, null, null);
        userNames = Controller.getInstance().listOfUserMinor(GetUsers);
    }

    @FXML
    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "education assistant")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "back to main page"));
            ClientLogic.getInstance().setEducationalAssistantDesk(loader, stage);
        }
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get current user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    protected String getUserFaculty() throws IOException, ParseException {
        log.info("Get current user faculty");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userFaculty(massageUserDegree);
    }

    public void rejection(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Reject request");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), choiceUser.getText() + "-" + getUserFaculty(), "reject minor"));
    }

    public void accept(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Accept minor");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), choiceUser.getText() + "-" + getUserFaculty(), "accept minor"));
    }
}