package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import javafx.application.Platform;
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

public class withdrawalFromEducationDesk {
    public Label respond;
    @FXML
    protected Label request;

    Stage stage;
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() {
        log.info("Open withdrawal request as a educational assistant");
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
    }

    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (request).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void backBtnClicked(javafx.event.ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "back to main page"));
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
        } else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
        } else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentPhdDesk(loader, stage);
        }

    }

    public void requestClicked() throws IOException, ParseException {
        log.info("Request clicked");
        request.setText("Sent!");
        log.info("Add request");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "request for withdrawal"));
    }

    public void showResult() throws IOException, ParseException, InterruptedException {
        log.info("Show result");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "showWithdrawalResult"));
        Thread.sleep(100);
    }

    public void withdrawalResult(String result) {
        Platform.runLater(() -> {
            if (Objects.equals(result, "2")) respond.setText("Not respond yet!");
            else if (Objects.equals(result, "1")) respond.setText("Accepted with your request!");
            else if (Objects.equals(result, "0")) respond.setText("Rejected your request!");
        });
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

}
