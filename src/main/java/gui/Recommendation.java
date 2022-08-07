package gui;

import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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
import server.Controller;
import server.MassageInNetwork;

import java.io.IOException;
import java.util.Objects;

public class Recommendation {
    String condition;
    String teacher;
    String lessons;
    String scores;
    String ta;
    @FXML
    protected Label teacherName;
    @FXML
    protected Label id;
    @FXML
    protected Label lessonsPassed;
    @FXML
    protected Label gradePassed;
    @FXML
    protected Label taPassed;
    @FXML
    protected Label resultShow;
    @FXML
    protected TextField nameField;
    @FXML
    protected TextField lessonField;
    @FXML
    protected TextField scoreField;
    @FXML
    protected Label sentWarning;
    @FXML
    protected TextField taField;
    Stage stage;

    static Logger log = LogManager.getLogger(ClientMain.class);

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    protected void initialize(){
        log.info("Open recommendation page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened", e);
                throw new RuntimeException(e);
            }
        } );
    }
    public void logOut() throws IOException {
        log.info("Logged out , out of time");
        stage = ((Stage) (sentWarning).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
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
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    public void sendClicked() throws IOException, ParseException {
        log.info("Send request clicked");
        if (lessonField != null && nameField != null && scoreField != null){
            addRequest();
            sentWarning.setText("sent");
        }
        else{
            sentWarning.setText("Fill all parts!");
        }
    }
    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
    }
    protected void addRequest() throws IOException, ParseException {
        log.info("Add user request for recommendation");
        MassageInNetwork massageInNetwork = new MassageInNetwork(nameField.getText(), lessonField.getText(), scoreField.getText(),
                taField.getText(),null,null, getUsername(),null,null,null);
        Controller.getInstance().addRecommendRequest(massageInNetwork);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user user name");
        MassageInNetwork massageStudentPhdDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userDeskUserName(massageStudentPhdDesk);
    }
    protected String getUserId() throws IOException, ParseException {
        log.info("Get current user student Id");
        MassageInNetwork massageStudentPhdDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userId(massageStudentPhdDesk);
    }
    protected void getCondition() throws IOException, ParseException {
        log.info("get user condition");
        MassageInNetwork massageResult = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null,null);
        condition = Controller.getInstance().recommendResult(massageResult);
    }
    public void getTeacher() throws IOException, ParseException {
        log.info("Get user name for recommendation");
        MassageInNetwork massageResult = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        teacher = Controller.getInstance().recommendGetTeacher(massageResult);
    }
    public void getLessons() throws IOException, ParseException {
        log.info("get lessons that user passed with related teacher");
        MassageInNetwork massageResult = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        lessons = Controller.getInstance().recommendGetLessons(massageResult);
    }
    public void getScores() throws IOException, ParseException {
        log.info("Get scores for write in recommendation");
        MassageInNetwork massageResult = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        scores = Controller.getInstance().recommendGetScores(massageResult);
    }
    public void getTa() throws IOException, ParseException {
        log.info("Get if user has been ta or not");
        MassageInNetwork massageResult = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null,null);
        ta = Controller.getInstance().recommendGetTa(massageResult);

    }
    public void getResult(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("get results");
        getCondition();
        getLessons();
        getScores();
        getTa();
        getTeacher();
        if (Objects.equals(condition, "2")) resultShow.setText("Not respond yet");
        else if (Objects.equals(condition, "1")){
            resultShow.setText("Accepted with your request");
            teacherName.setText(teacher);
            lessonsPassed.setText(lessons);
            gradePassed.setText(scores);
            taPassed.setText(ta);
            id.setText(getUserId());
        }
        else if(Objects.equals(condition, "0")){
            resultShow.setText("Rejected your request");
        }
    }

}
