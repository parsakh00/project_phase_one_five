package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.*;
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
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class TemporaryScoresStudent {

    public Label confirm;

    public GridPane chart;
    public Label score;
    public TextField textField1;
    public TextField textField2;
    public TextField textField4;
    public TextField textField3;
    public TextField textField5;
    public TextField textField6;
    public TextField textField7;
    public TextField textField8;

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
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
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
        textField1.setVisible(false);
        textField2.setVisible(false);
        textField3.setVisible(false);
        textField4.setVisible(false);
        textField5.setVisible(false);
        textField6.setVisible(false);
        textField7.setVisible(false);
        textField8.setVisible(false);
        log.info("Make grid");
        chart.getChildren().clear();
        for (int i = 0; i < 5; i++){
            javafx.scene.control.Label label = new Label();
            if (i == 0) label.setText("Respond");
            if (i == 1) label.setText("Score");
            if (i == 2) label.setText("Teacher");
            if (i == 3) label.setText("Lesson");
            label.setAlignment(Pos.CENTER_RIGHT);
            GridPane.setHalignment(label, HPos.CENTER);
            chart.setVgap(10);
            chart.add(label,i,0);
        }
        for (int j = 0; j < lesson.size(); j++){
            if (j == 0) textField1.setVisible(true);
            else if (j == 1) textField2.setVisible(true);
            else if (j == 2) textField3.setVisible(true);
            else if (j == 3) textField4.setVisible(true);
            else if (j == 4) textField5.setVisible(true);
            else if (j == 5) textField6.setVisible(true);
            else if (j == 6) textField7.setVisible(true);
            else if (j == 7) textField8.setVisible(true);
            for (int i = 0; i < 5; i++){
                javafx.scene.control.Label label = new javafx.scene.control.Label();
                if (i == 0) label.setText(respond.get(j));
                if (i == 1) label.setText(scores.get(j));
                if (i == 2) label.setText(teachers.get(j));
                if (i == 3) label.setText(lesson.get(j));
                label.setAlignment(Pos.CENTER_RIGHT);
                GridPane.setHalignment(label, HPos.CENTER);
                chart.setVgap(10);
                chart.add(label, i, j+1);
            }
        }

    }

    @FXML
    protected void saveObjection(){
        for (int j = 0; j < lesson.size(); j++){
            if (j == 0) objection.add(textField1.getText());
            else if (j == 1) objection.add(textField2.getText());
            else if (j == 2) objection.add(textField3.getText());
            else if (j == 3) objection.add(textField4.getText());
            else if (j == 4) objection.add(textField5.getText());
            else if (j == 5) objection.add(textField6.getText());
            else if (j == 6) objection.add(textField7.getText());
            else if (j == 7) objection.add(textField8.getText());
        }
        setObjections();
        confirm.setText("Done");

    }

    protected void getLesson(){
        log.info("Get user lessons");
        MassageLogin massageUserLesson = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        lesson = Controller.getInstance().getLessons(massageUserLesson);
    }
    protected void setObjections(){
        log.info("set objections of each student");
        MassageLogin massageUserLesson = new MassageLogin(CurrentUser.getInstance().getUserName(), objection);
        Controller.getInstance().setObjection(massageUserLesson);
    }
    protected void getTeachers(){
        log.info("Get teachers");
        MassageLogin massageUserTeachers = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        teachers = Controller.getInstance().getTeachers(massageUserTeachers);
    }
    protected void getScores(){
        log.info("get user Scores");
        MassageLogin massageUserScores = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        scores = Controller.getInstance().getScores(massageUserScores);
    }
    protected void getRespond(){
        log.info("get teacher respond");
        MassageLogin massageUserScores = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        respond = Controller.getInstance().getRespond(massageUserScores);

    }
}
