package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import server.Controller;
import server.MassageInNetwork;

import java.io.IOException;
import java.util.Objects;

public class MinorDesk {

    public TextField facultyName;
    public Label minorWarning;
    public Label warningResult;
    Stage stage;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() {
        log.info("Open minor page ");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.info("exception error", e);
                throw new RuntimeException(e);
            }
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

    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (minorWarning).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get current user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    protected String getFacultyUser() throws IOException, ParseException {
        log.info("Get current user faculty");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userFaculty(massageUserDegree);
    }

    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
        }
    }

    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user name");
        MassageInNetwork massageStudentPhdDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskUserName(massageStudentPhdDesk);
    }

    public void sendRequestClicked() throws IOException, ParseException {
        log.info("Send request clicked");
        if (facultyName.getText() != null) {
            log.info("Send minor request");
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), getUsername() + "-" + getFacultyUser() + "-" + facultyName.getText(), "send minor request"));
            minorWarning.setText("Done");
        } else {
            minorWarning.setText("Fill faculty name first");
        }
    }

    public void showResultClicked() throws IOException, ParseException {
        log.info("Show result clicked");
        log.info("Show result");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show minor result"));
    }

    public void showResult(String str) {
        Platform.runLater(() -> {
            if (Objects.equals((String) str, "022")) warningResult.setText("Not Checked");
            else if (Objects.equals((String) str, "012")) warningResult.setText("Origin faculty approved");
            else if (Objects.equals((String) str, "021")) warningResult.setText("Destination university approved");
            else if (Objects.equals((String) str, "002") || Objects.equals((String) str, "001") || Objects.equals((String) str, "020") || Objects.equals((String) str, "010"))
                warningResult.setText("Rejected your request!");
            else if (Objects.equals((String) str, "011")) warningResult.setText("Your request approved!");
            else warningResult.setText("Nothing found!");
        });
    }
}