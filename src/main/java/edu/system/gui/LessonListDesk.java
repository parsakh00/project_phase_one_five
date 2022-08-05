package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.*;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LessonListDesk {

    Stage stage;
    String[] facultyLessons;
    @FXML
    protected Label loginTIme;
    @FXML
    protected Label timeDate;
    @FXML
    protected Label email;
    @FXML
    protected Label userName;
    @FXML
    protected GridPane filterGrid;
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

    protected String[] faculty = {"Chemistry","MathSci","MechanicEng","Physics","ElectricalEng"};

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(HelloApplication.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open lesson list desk page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception error", e);
                throw new RuntimeException(e);
            }
        } );
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
    public void logOut() throws IOException {
        log.info("Logged out , out of time");
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
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
        log.info("Get user email");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);

    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current username");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
    protected void getFaculty(ActionEvent actionEvent) {
        log.info("get current user faculty");
        String filter1 =  facultyChoiceBox.getValue();
        filterGrid.getChildren().clear();
        for (int i = 0; i < 5; i++){
            Label label = new Label();
            if (i == 0) label.setText("Stage");
            if (i == 1) label.setText("Teacher");
            if (i == 2) label.setText("Lesson");
            if (i == 3) label.setText("Unity");
            if (i == 4) label.setText("Id");
            filterGrid.add(label,i,0);
            GridPane.setHalignment(label, HPos.CENTER);
        }
        CurrentFaculty.getInstance().setFaculty(facultyChoiceBox.getValue());
        getFacultyData();
        int i = 0;
        int j = 1;
        for (String eachElement : facultyLessons){
            if (!Objects.equals(eachElement, "null")){
                Label label = new Label();
                if (i%5 == 1) label.setText(eachElement);
                else if (i%5 == 2) label.setText(eachElement);
                else if (i%5 == 3) label.setText(eachElement);
                else if (i%5 == 4) label.setText(eachElement);
                else if (i%5 == 0) label.setText(eachElement);
                label.setAlignment(Pos.CENTER);
                filterGrid.add(label,i%5 ,j);
                GridPane.setHalignment(label, HPos.CENTER);
                i += 1;
                if (i%5 == 0) j+= 1;
            }
        }

    }
    protected void getStage(ActionEvent actionEvent){
        log.info("Get user stage");
        if (stageCheckBox.isSelected()) {
            String filter1 =  facultyChoiceBox.getValue();
            filterGrid.getChildren().clear();
            for (int i = 0; i < 5; i++){
                Label label = new Label();
                if (i == 0) label.setText("Stage");
                if (i == 1) label.setText("Teacher");
                if (i == 2) label.setText("Lesson");
                if (i == 3) label.setText("Unity");
                if (i == 4) label.setText("Id");
                filterGrid.add(label,i,0);
                GridPane.setHalignment(label, HPos.CENTER);
            }
            CurrentFacultyUnit.getInstance().setFaculty(facultyChoiceBox.getValue());
            CurrentFacultyUnit.getInstance().setUnit(stageChoiceBox.getValue());
            getFacultyStage();
            int i = 0;
            int j = 1;
            for (String eachElement : facultyLessons){
                if (!Objects.equals(eachElement, "null")){
                    Label label = new Label();
                    if (i%5 == 1) label.setText(eachElement);
                    else if (i%5 == 2) label.setText(eachElement);
                    else if (i%5 == 3) label.setText(eachElement);
                    else if (i%5 == 4) label.setText(eachElement);
                    else if (i%5 == 0) label.setText(eachElement);
                    label.setAlignment(Pos.CENTER);
                    GridPane.setHalignment(label, HPos.CENTER);
                    filterGrid.add(label,i%5 ,j);
                    i += 1;
                    if (i%5 == 0) j+= 1;
                }
            }
        }
    }
    protected void getUnit(ActionEvent actionEvent){
        log.info("Get array of unit");
        if (unityCheckBox.isSelected()) {
            String filter1 =  facultyChoiceBox.getValue();
            filterGrid.getChildren().clear();
            for (int i = 0; i < 5; i++){
                Label label = new Label();
                if (i == 0) label.setText("Stage");
                if (i == 1) label.setText("Teacher");
                if (i == 2) label.setText("Lesson");
                if (i == 3) label.setText("Unity");
                if (i == 4) label.setText("Id");
                filterGrid.add(label,i,0);
                GridPane.setHalignment(label, HPos.CENTER);
            }
            CurrentFacultyUnit.getInstance().setFaculty(facultyChoiceBox.getValue());
            CurrentFacultyUnit.getInstance().setUnit(unityChoiceBox.getValue());
            getFacultyUnit();
            int i = 0;
            int j = 1;
            for (String eachElement : facultyLessons){
                if (!Objects.equals(eachElement, "null")){
                    Label label = new Label();
                    if (i%5 == 1) label.setText(eachElement);
                    else if (i%5 == 2) label.setText(eachElement);
                    else if (i%5 == 3) label.setText(eachElement);
                    else if (i%5 == 4) label.setText(eachElement);
                    else if (i%5 == 0) label.setText(eachElement);
                    label.setAlignment(Pos.CENTER);
                    GridPane.setHalignment(label, HPos.CENTER);

                    filterGrid.add(label,i%5 ,j);
                    i += 1;
                    if (i%5 == 0) j+= 1;
                }
            }
        }


    }
    protected void getFacultyData() {
        log.info("get faculty data");
        MassageLogin massageLessonListDesk = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        facultyLessons =  Controller.getInstance().facultyLessons(massageLessonListDesk);
    }
    protected void getFacultyUnit(){
        log.info("get faculty unit");
        MassageLogin massageFacultyUnit = new MassageLogin(CurrentFacultyUnit.getInstance().getFaculty(), CurrentFacultyUnit.getInstance().getUnit());
        facultyLessons = Controller.getInstance().facultyUnitLessons(massageFacultyUnit);
    }
    protected void getFacultyStage(){
        log.info("get faculty stage");
        MassageLogin massageFacultyUnit = new MassageLogin(CurrentFacultyUnit.getInstance().getFaculty(), CurrentFacultyUnit.getInstance().getUnit());
        facultyLessons = Controller.getInstance().facultyStageLessons(massageFacultyUnit);
    }

    protected String getUserType() throws IOException, ParseException {
        MassageLogin massageStudentMasterDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskType(massageStudentMasterDesk);
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get current user degree");
        MassageLogin massageStudentMasterDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskDegreee(massageStudentMasterDesk);
    }

    public void returnBtn() throws IOException, ParseException {
        log.info("Return Button");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "phd")){
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "undergraduate")){
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "manager") || Objects.equals(getUserDegree(),"-")){
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }

        else if (Objects.equals(getUserDegree(), "education assistant")){
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
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
