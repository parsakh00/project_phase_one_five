package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LessonListDesk {

    @FXML
    protected Label loginTIme;
    @FXML
    protected Label timeDate;
    @FXML
    protected Label email;
    @FXML
    protected Label userName;
    @FXML
    protected ChoiceBox<String> stageChoiceBox;
    @FXML
    protected ChoiceBox<String> unityChoiceBox;
    @FXML
    protected ChoiceBox<String> facultyChoiceBox;
    @FXML
    protected CheckBox stageCheckBox;
    @FXML
    protected CheckBox unityCheckBox;

    protected String[] stagee = {"undergraduate","master","phd"};

    protected String[] unity = {"0","1","2","3","4"};

    protected String[] faculty = {"Chemistry","MathSci","MechanicEng"};


    public void initialize() throws IOException, ParseException {
        timeDisplay();
        email.setText("Email : " + getEmail());
        userName.setText("Username : " + getUsername());
        facultyChoiceBox.getItems().addAll(faculty);
        unityChoiceBox.getItems().addAll(unity);
        stageChoiceBox.getItems().addAll(stagee);
        facultyChoiceBox.setOnAction(this::getFaculty);
        unityChoiceBox.setOnAction(this::getUnit);
        stageChoiceBox.setOnAction(this::getStage);

    }

    public void timeDisplay(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    protected String getEmail() throws IOException, ParseException {
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);

    }

    protected String getUsername() throws IOException, ParseException {
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }

    protected String getLoginTime(){
        //ToDo
        return null;
    }



    protected String getFaculty(ActionEvent actionEvent){
        return facultyChoiceBox.getValue();
    }
    protected String getStage(ActionEvent actionEvent){
        if (stageCheckBox.isSelected()) {
            return stageChoiceBox.getValue();
        }
        else return null;
    }

    protected String getUnit(ActionEvent actionEvent){
        if (unityCheckBox.isSelected()) {
            return unityChoiceBox.getValue();
        }
        else return null;
    }




}
