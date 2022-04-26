package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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

    protected void getFaculty(ActionEvent actionEvent) {
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
                i += 1;
                if (i%5 == 0) j+= 1;
            }
        }

    }
    protected void getStage(ActionEvent actionEvent){
        //ToDo
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

                    filterGrid.add(label,i%5 ,j);
                    i += 1;
                    if (i%5 == 0) j+= 1;
                }
            }
        }
    }
    protected void getUnit(ActionEvent actionEvent){
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

                    filterGrid.add(label,i%5 ,j);
                    i += 1;
                    if (i%5 == 0) j+= 1;
                }
            }
        }

    }
    protected void getFacultyData() {
        MassageUserDesk massageLessonListDesk = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        facultyLessons =  Controller.getInstance().facultyLessons(massageLessonListDesk);
    }
    protected void getFacultyUnit(){
        MassageFacultyUnit massageFacultyUnit = new MassageFacultyUnit(CurrentFacultyUnit.getInstance().getFaculty(), CurrentFacultyUnit.getInstance().getUnit());
        facultyLessons = Controller.getInstance().facultyUnitLessons(massageFacultyUnit);
    }
    protected void getFacultyStage(){
        MassageFacultyUnit massageFacultyUnit = new MassageFacultyUnit(CurrentFacultyUnit.getInstance().getFaculty(), CurrentFacultyUnit.getInstance().getUnit());
        facultyLessons = Controller.getInstance().facultyStageLessons(massageFacultyUnit);
    }

    protected String getUserType() throws IOException, ParseException {
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskType(massageStudentMasterDesk);
    }

    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskDegreee(massageStudentMasterDesk);
    }

    public void returnBtn() throws IOException, ParseException {

        //ToDo
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
        else if (Objects.equals(getUserDegree(), "manager") || Objects.equals(getUserDegree(),"")){
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
            System.out.println("true");
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
