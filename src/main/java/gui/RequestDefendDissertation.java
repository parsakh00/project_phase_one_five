package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDate;

public class RequestDefendDissertation {

    @FXML
    protected DatePicker datePicker;

    @FXML
    protected Label dateLabel;
    Stage stage;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    static Logger log = LogManager.getLogger(ClientMain.class);

    protected void initialize() {
        log.info("Open request for defend of dissertation");
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
        stage = ((Stage) (datePicker).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    public void returnBtn(ActionEvent actionEvent) throws IOException {
        log.info("return back button");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "back to main page"));
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    @FXML
    protected void getDate(ActionEvent actionEvent) {
        log.info("Get data from date picker");
        LocalDate localDate = datePicker.getValue();
        dateLabel.setText(localDate.toString());
    }
}
