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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.json.simple.parser.ParseException;


import java.io.IOException;
import java.util.Objects;

public class EditLessonsDesk {
    Stage stage;

    @FXML
    protected Button backBtn;
    @FXML
    protected TextField removeLesson;
    @FXML
    protected Label removeWarning;
    @FXML
    protected Button removeBtn;
    @FXML
    protected TextField editLesson;
    @FXML
    protected TextField editTime;
    @FXML
    protected TextField editTeacher;
    @FXML
    protected Label editWarning;
    @FXML
    protected Button editBtn;
    @FXML
    protected Button addBtn;
    @FXML
    protected TextField addLesson;
    @FXML
    protected TextField addUnity;
    @FXML
    protected TextField addStage;
    @FXML
    protected TextField addTime;
    @FXML
    protected TextField addId;
    @FXML
    protected TextField addTeacher;
    @FXML
    protected TextField addIsPresent;
    @FXML
    protected Label addWarning;

    public void logOut() throws IOException {
        stage = ((Stage) (removeWarning).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void initialize(){
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } );
        removeLesson.setText(null);
        editTeacher.setText(null);
        editLesson.setText(null);
        editTime.setText(null);
        addIsPresent.setText(null);
        addLesson.setText(null);
        addId.setText(null);
        addStage.setText(null);
        addTeacher.setText(null);
        addTime.setText(null);
        addUnity.setText(null);
    }
    public void backBtnClicked(ActionEvent actionEvent) throws IOException {
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    protected void editBtnClicked() throws IOException, ParseException {
        if (editLesson.getText() != null) {
            if(editTeacher.getText() != null || editTime != null) {
                editLesson();
                editWarning.setText("Done");
            }
            else{
                editWarning.setText("At least fill time or teacher field");
            }
        }
        else {
            editWarning.setText("First fill lesson field");
        }
    }
    public void addBtnClicked() throws IOException, ParseException {
        if (addUnity.getText() != null && addId.getText() != null && addTime.getText() != null && addIsPresent.getText() != null && addTeacher.getText() != null && addStage.getText() != null){
            addLesson();
            addWarning.setText("Done");
        }
        else{
            addWarning.setText("First fill all parts.");
        }
    }
    protected void removal() throws IOException, ParseException {
        if (removeLesson.getText() != null) {
            removeLesson();
            removeWarning.setText("Done");
        }
        else{
            removeWarning.setText("First set lesson");
        }
    }
    protected Boolean presentValue(String isPresent){
        return Objects.equals(isPresent, "true");
    }
    protected void addLesson() throws IOException, ParseException {
        MassageInNetwork addSelectedLesson = new MassageInNetwork(addLesson.getText(),currentUserFaculty(),
                addTime.getText(),addTeacher.getText(),addUnity.getText(),addStage.getText(),addId.getText()
                ,presentValue(addIsPresent.getText()),null,null);
        Controller.getInstance().adding(addSelectedLesson);
    }
    protected void removeLesson() throws IOException, ParseException {
        MassageInNetwork removeSelectedLesson = new MassageInNetwork(removeLesson.getText(),currentUserFaculty(), null,null,null,null,null,null,null,null);
        Controller.getInstance().removal(removeSelectedLesson);
    }
    private void editLesson() throws IOException, ParseException {
        MassageInNetwork editingSelectedLesson = new MassageInNetwork(editLesson.getText(),currentUserFaculty(),
                editTime.getText(),editTeacher.getText(),null,null,null,null,null,null);
        Controller.getInstance().editing(editingSelectedLesson);
    }
    protected String currentUserFaculty() throws IOException, ParseException {
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userFaculty(massageStudentMasterDesk);
    }
}
