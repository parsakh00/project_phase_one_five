package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class MinorDesk {

    public TextField facultyName;
    public Label minorWarning;
    public Label warningResult;
    Stage stage;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    static Logger log = LogManager.getLogger(HelloApplication.class);

    public void initialize(){
        log.info("Open minor page ");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.info("exception error", e);
                throw new RuntimeException(e);
            }
        } );
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (minorWarning).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get current user degree");
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    protected String getFacultyUser() throws IOException, ParseException {
        log.info("Get current user faculty");
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userFaculty(massageUserDegree);
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
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user name");
        MassageUserDesk massageStudentPhdDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentPhdDesk);
    }
    public void sendRequestClicked() throws IOException, ParseException {
        log.info("Send request clicked");
        if (facultyName.getText()!= null){
            sendMinorRequest();
            minorWarning.setText("Done");
        }
        else {
            minorWarning.setText("Fill faculty name first");
        }
    }
    public void sendMinorRequest() throws IOException, ParseException {
        log.info("Send minor request");
        MassageFacultyUnit sendMinor = new MassageFacultyUnit(getUsername(),getFacultyUser(),facultyName.getText());
        Controller.getInstance().addMinorRequest(sendMinor);
    }
    public void showResultClicked() throws IOException, ParseException {
        log.info("Show result clicked");
        showResult();
        if (Objects.equals((String) showResult(), "022")) warningResult.setText("Not Checked");
        else if (Objects.equals((String) showResult(), "012")) warningResult.setText("Origin faculty approved");
        else if (Objects.equals((String) showResult(), "021")) warningResult.setText("Destination university approved");
        else if (Objects.equals((String) showResult(), "002")||Objects.equals((String) showResult(), "001")||Objects.equals((String) showResult(), "020")||Objects.equals((String) showResult(), "010")) warningResult.setText("Rejected your request!");
        else if (Objects.equals((String) showResult(), "011")) warningResult.setText("Your request approved!");
    }
    public String showResult() throws IOException, ParseException {
        log.info("Show result");
        MassageUserDesk howManyFacultyMinor = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().facultyForMinors(howManyFacultyMinor);
    }
}
