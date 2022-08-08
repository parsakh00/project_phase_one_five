package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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

public class WeeklySchedule {

    @FXML
    protected Label saturday;
    @FXML
    protected Label sunday;
    @FXML
    protected Label monday;
    @FXML
    protected Label tuesday;
    @FXML
    protected Label wednesday;
    @FXML
    protected Label thursday;
    @FXML
    protected Label friday;
    //which lesson , what time , which day
    Stage stage;
    String[] lesson;
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open weekly schedule page");
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
        log.info("Show user schedule");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show weekly schedule"));
    }

    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (friday).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
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

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    protected String getUserDegree() throws IOException, ParseException {
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        log.info("Get user degree");
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void showStudentSchedule(String data) {
        Platform.runLater(() -> {
            String[] weekLessons = data.split("-");
            int i = 1;
            String Saturday = ".", Sunday = ".", Monday = ".", Tuesday = ".", Wednesday = ".", Thursday = ".", Friday = ".";
            for (String eachElement : weekLessons) {
                if (i <= weekLessons.length - 3) {

                    if (!Objects.equals(eachElement, "null")) {

                        if (i % 4 == 1) {
                            System.out.println(weekLessons[i + 1]);
                            if (Objects.equals(weekLessons[i + 1], "0"))
                                Saturday += eachElement + " = " + weekLessons[i] + " / ";
                            else if (Objects.equals(weekLessons[i + 1], "1"))
                                Sunday += eachElement + " = " + weekLessons[i] + " / ";
                            else if (Objects.equals(weekLessons[i + 1], "2"))
                                Monday += eachElement + " = " + weekLessons[i] + " / ";
                            else if (Objects.equals(weekLessons[i + 1], "3"))
                                Tuesday += eachElement + " = " + weekLessons[i] + " / ";
                            else if (Objects.equals(weekLessons[i + 1], "4"))
                                Wednesday += eachElement + " = " + weekLessons[i] + " / ";
                            else if (Objects.equals(weekLessons[i + 1], "5"))
                                Thursday += eachElement + " = " + weekLessons[i] + " / ";
                            else if (Objects.equals(weekLessons[i + 1], "6"))
                                Friday += eachElement + " = " + weekLessons[i] + " / ";
                        }
                        i += 1;
                    }
                }
            }
            saturday.setText(Saturday);
            sunday.setText(Sunday);
            monday.setText(Monday);
            tuesday.setText(Tuesday);
            wednesday.setText(Wednesday);
            thursday.setText(Thursday);
            friday.setText(Friday);
        });
    }
}
