package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import message.Message;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class AccommodationDesk {
    @FXML
    protected Label reject;
    @FXML
    protected Label accept;

    Stage stage;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() {
        log.info("Open accommodation desk");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("Exception happened!");
                throw new RuntimeException(e);
            }
        });
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Current user get degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void logOut() throws IOException {
        log.info("Logged out out of time");
        stage = ((Stage) (reject).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    public void backBtnClicked(javafx.event.ActionEvent actionEvent) throws IOException, ParseException {
        log.info("back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "back to main page"));
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
        }

    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void requestClicked() {
        log.info("Current user clicked request");

        if ((int) ((Math.random() + 2) * 100) % 2 == 0) {
            accept.setText(null);
            reject.setText("Reject");
        } else {
            reject.setText(null);
            accept.setText("Accept");
        }
    }

}
