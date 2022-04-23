package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;

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



    public void initialize(){
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

    protected void addLesson() throws IOException, ParseException {
        SelectLesson.getInstance().setLesson(addLesson.getText());
        SelectLesson.getInstance().setFaculty(currentUserFaculty());
        SelectLesson.getInstance().setTeacher(addUnity.getText());
        SelectLesson.getInstance().setTime(addStage.getText());
        SelectLesson.getInstance().setTime(addTime.getText());
        SelectLesson.getInstance().setTime(addId.getText());
        SelectLesson.getInstance().setTime(addTeacher.getText());
        SelectLesson.getInstance().setTime(addIsPresent.getText());
        MassageLogin addSelectedLesson = new MassageLogin(SelectLesson.getInstance().getLesson(),SelectLesson.getInstance().getFaculty(),
                SelectLesson.getInstance().getTime(),SelectLesson.getInstance().getTeacher(),SelectLesson.getInstance().getUnity(),SelectLesson.getInstance().getStage(),SelectLesson.getInstance().getId()
                ,SelectLesson.getInstance().getPresent());
        Controller.getInstance().adding(addSelectedLesson);
    }

    protected void removeLesson() throws IOException, ParseException {
        SelectLesson.getInstance().setLesson(removeLesson.getText());
        SelectLesson.getInstance().setFaculty(currentUserFaculty());
        MassageLogin removeSelectedLesson = new MassageLogin(SelectLesson.getInstance().getLesson(),SelectLesson.getInstance().getFaculty());
        Controller.getInstance().removal(removeSelectedLesson);
    }
    private void editLesson() throws IOException, ParseException {
        SelectLesson.getInstance().setLesson(editLesson.getText());
        SelectLesson.getInstance().setFaculty(currentUserFaculty());
        SelectLesson.getInstance().setTeacher(editTeacher.getText());
        SelectLesson.getInstance().setTime(editTime.getText());
        MassageLogin editingSelectedLesson = new MassageLogin(SelectLesson.getInstance().getLesson(),SelectLesson.getInstance().getFaculty(),
                SelectLesson.getInstance().getTime(),SelectLesson.getInstance().getTeacher());
        Controller.getInstance().editing(editingSelectedLesson);
    }
//    protected String getUsername() throws IOException, ParseException {
//        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
//        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
//    }
    protected String currentUserFaculty() throws IOException, ParseException {
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userFaculty(massageStudentMasterDesk);
    }








}
