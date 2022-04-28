package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentFaculty;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TemporaryScoresStudent {

    public GridPane chart;
    public Label score;
    Stage stage;
    ArrayList<String> lesson = new ArrayList<String>();
    ArrayList<String> teachers = new ArrayList<String>();
    ArrayList<String> scores = new ArrayList<String>();
    ArrayList<String> respond = new ArrayList<String>();

    ArrayList<String> objection = new ArrayList<String>();

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(HelloApplication.class);


    public void initialize(){
        log.info("Open temporary student's score page");
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
        getScores();
        getLesson();
        getRespond();
        getTeachers();
        makeGrid();
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (score).getScene().getWindow());
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
        log.info("Get user degree");
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    public void backBtn(ActionEvent actionEvent) throws IOException, ParseException {
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
    protected void makeGrid(){
        log.info("Make grid");
        chart.getChildren().clear();
        for (int i = 0; i < 5; i++){
            javafx.scene.control.Label label = new Label();
            if (i == 0) label.setText("Respond");
            if (i == 1) label.setText("Objection");
            if (i == 2) label.setText("Score");
            if (i == 3) label.setText("Teacher");
            if (i == 4) label.setText("Lesson");
            label.setAlignment(Pos.CENTER_RIGHT);
            GridPane.setHalignment(label, HPos.CENTER);
            chart.setVgap(10);
            chart.add(label,i,0);
        }
        for (int j = 0; j < lesson.size(); j++){
            for (int i = 0; i < 5; i++){
                javafx.scene.control.Label label = new javafx.scene.control.Label();
                TextField textField = new TextField();
                if (i == 0) label.setText(respond.get(j));
                else if (i == 1) chart.add(textField, i,j+1);
                else if (i == 2) label.setText(scores.get(j));
                else if (i == 3) label.setText(teachers.get(j));
                else label.setText(lesson.get(j));
                label.setAlignment(Pos.CENTER_RIGHT);
                GridPane.setHalignment(label, HPos.CENTER);
                chart.setVgap(10);
                chart.add(label, i, j+1);
            }
        }

    }

    @FXML
    protected void saveObjection(){

    }

    protected void getLesson(){
        log.info("Get user lessons");
        MassageUserDesk massageUserLesson = new MassageUserDesk(CurrentUser.getInstance().getUser());
        lesson = Controller.getInstance().getLessons(massageUserLesson);
    }

    protected void getTeachers(){
        log.info("Get teachers");
        MassageUserDesk massageUserTeachers = new MassageUserDesk(CurrentUser.getInstance().getUser());
        teachers = Controller.getInstance().getTeachers(massageUserTeachers);
    }
    protected void getScores(){
        log.info("get user Scores");
        MassageUserDesk massageUserScores = new MassageUserDesk(CurrentUser.getInstance().getUser());
        scores = Controller.getInstance().getScores(massageUserScores);
    }
    protected void getRespond(){
        log.info("get teacher respond");
        MassageUserDesk massageUserScores = new MassageUserDesk(CurrentUser.getInstance().getUser());
        respond = Controller.getInstance().getRespond(massageUserScores);

    }
}
