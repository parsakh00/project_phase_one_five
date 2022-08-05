package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.*;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
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

    static Logger log = LogManager.getLogger(HelloApplication.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open weekly schedule page");
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
        showSchedule();
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (friday).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
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
    protected String getUserDegree() throws IOException, ParseException {
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        log.info("Get user degree");
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    protected void getUserLesson() throws IOException, ParseException {
        log.info("Get user lessons");
        MassageLogin massageGetUserLesson = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        lesson =  Controller.getInstance().userOfLessons(massageGetUserLesson);
    }
    protected void showSchedule() throws IOException, ParseException {
        log.info("Show user schedule");
        getUserLesson();
        int i = 1;
        String Saturday = ".",Sunday = ".",Monday = ".",Tuesday = ".",Wednesday = ".",Thursday = ".",Friday = ".";
        for (String eachElement : lesson){
            if (i <= lesson.length - 3) {

                if (!Objects.equals(eachElement, "null")) {

                    if (i % 4 == 1) {
                        if (Objects.equals(lesson[i + 2], "0")) Saturday += eachElement + " = " +lesson[i+1]+" / ";
                        else if (Objects.equals(lesson[i + 2], "1")) Sunday += eachElement + " = " +lesson[i+1]+" / ";
                        else if (Objects.equals(lesson[i + 2], "2")) Monday += eachElement+ " = " +lesson[i+1]+" / ";
                        else if (Objects.equals(lesson[i + 2], "3")) Tuesday += eachElement+ " = " +lesson[i+1]+" / ";
                        else if (Objects.equals(lesson[i + 2], "4")) Wednesday += eachElement+ " = " +lesson[i+1]+" / ";
                        else if (Objects.equals(lesson[i + 2], "5")) Thursday += eachElement+ " = " +lesson[i+1]+" / ";
                        else if (Objects.equals(lesson[i + 2], "6")) Friday += eachElement+ " = " +lesson[i+1]+" / ";
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
    }

}
