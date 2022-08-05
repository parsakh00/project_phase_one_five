package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.MassageInNetwork;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class StudentsEducationalStatus {


    public Label status;
    public GridPane chart;
    public Label totalScoreShow;
    Stage stage;
    double totalUnit;
    double totalScore;

    double finalScore;

    ArrayList<String> lesson = new ArrayList<String>();
    ArrayList<String> teachers = new ArrayList<String>();
    ArrayList<String> scores = new ArrayList<String>();
    ArrayList<String> unit = new ArrayList<>();
    ArrayList<String> lessonNew = new ArrayList<String>();
    ArrayList<String> teachersNew = new ArrayList<String>();
    ArrayList<String> scoreNew = new ArrayList<String>();
    ArrayList<String> unitNew = new ArrayList<String>();

    static Logger log = LogManager.getLogger(HelloApplication.class);

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void initialize() throws IOException, ParseException {
        log.info("Open students educational status");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened");
                throw new RuntimeException(e);
            }
        } );
        getScores();
        getLesson();
        getTeachers();
        getUnit();
        getScoresNew();
        getLessonNew();
        getTeachersNew();
        getUnitNew();
        combineToPermanentTemporaryData();
        getTotalScore();
        makeGrid();
        setNewScore();
        }
    protected void makeGrid(){
        log.info("Make grid");
        chart.getChildren().clear();
        for (int i = 0; i < 5; i++){
            javafx.scene.control.Label label = new Label();
            if (i == 0) label.setText("state");
            if (i == 1) label.setText("score");
            if (i == 2) label.setText("unit");
            if (i == 3) label.setText("Teacher");
            if (i == 4) label.setText("Lesson");
            label.setAlignment(Pos.CENTER_RIGHT);
            GridPane.setHalignment(label, HPos.CENTER);
            chart.setVgap(10);
            chart.add(label,i,0);
        }
        for (int j = 0; j < lessonNew.size(); j++){
            for (int i = 0; i < 5; i++){
                javafx.scene.control.Label label = new javafx.scene.control.Label();
                if (i == 0) {
                    if (Objects.equals(scoreNew.get(j), "N/A")){
                        label.setText("-");
                    }
                    else if (Double.parseDouble(scoreNew.get(j)) >= 12){
                        label.setText("pass");
                    }
                    else label.setText("fail");
                }
                if (i == 1) label.setText(scoreNew.get(j));
                if (i == 2) label.setText(unitNew.get(j));
                if (i == 3) label.setText(teachersNew.get(j));
                if (i == 4) label.setText(lessonNew.get(j));
                label.setAlignment(Pos.CENTER_RIGHT);
                GridPane.setHalignment(label, HPos.CENTER);
                chart.setVgap(10);
                chart.add(label, i, j+1);
            }
        }

    }

    protected void setNewScore() throws IOException, ParseException {
        log.info("set New score for student in profile");
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), String.valueOf(finalScore), null, null);
        Controller.getInstance().userNewScore(massageStudentUndergraduateDesk);
    }

    protected String getScore() throws IOException, ParseException {
        log.info("Get user score");
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userScore(massageStudentUndergraduateDesk);
    }
    protected void combineToPermanentTemporaryData(){
        scoreNew.addAll(scores);
        teachersNew.addAll(teachers);
        unitNew.addAll(unit);
        lessonNew.addAll(lesson);
    }
    protected void getTotalScore(){
        log.info("Calculate total score of student");
        for (int i = 0; i < scoreNew.size(); i ++){
            if(!Objects.equals(scoreNew.get(i), "N/A")){
                totalScore += Double.parseDouble(scoreNew.get(i))*Double.parseDouble(unitNew.get(i));
                totalUnit += Double.parseDouble(unitNew.get(i));
            }
            finalScore = totalScore/totalUnit;
            finalScore = Math.round(finalScore*100/100);
        }

    }
    protected void getLessonNew(){
        log.info("Get user lessons from permanent scores");
        MassageInNetwork massageUserLesson = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        lessonNew = Controller.getInstance().getLessonsNew(massageUserLesson);
    }
    protected void getTeachersNew(){
        log.info("Get teachers from permanent scores");
        MassageInNetwork massageUserTeachers = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        teachersNew = Controller.getInstance().getTeachersNew(massageUserTeachers);
    }
    protected void getScoresNew(){
        log.info("get user Scores from permanent scores");
        MassageInNetwork massageUserScores = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        scoreNew = Controller.getInstance().getScoresNew(massageUserScores);
    }
    protected void getUnitNew(){
        log.info("get lesson's unit from permanent scores");
        MassageInNetwork massageUserScores = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        unitNew = Controller.getInstance().getUnitsNew(massageUserScores);
    }
    protected void getLesson(){
        log.info("Get user lessons from temporary scores");
        MassageInNetwork massageUserLesson = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        lesson = Controller.getInstance().getLessons(massageUserLesson);
    }
    protected void getTeachers(){
        log.info("Get teachers from temporary scores");
        MassageInNetwork massageUserTeachers = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        teachers = Controller.getInstance().getTeachers(massageUserTeachers);
    }
    protected void getScores(){
        log.info("get user Scores from temporary scores");
        MassageInNetwork massageUserScores = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        scores = Controller.getInstance().getScores(massageUserScores);
    }
    protected void getUnit(){
        log.info("get lesson's unit from temporary scores");
        MassageInNetwork massageUserScores = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        unit = Controller.getInstance().getUnits(massageUserScores);
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (status).getScene().getWindow());
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
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null,null);
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
}
