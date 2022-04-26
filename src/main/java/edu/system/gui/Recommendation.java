package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

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


    protected void initialize(){

    }

    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void sendClicked() throws IOException, ParseException {
        if (lessonField != null && nameField != null && scoreField != null){
            addRequest();
            sentWarning.setText("sent");
        }
        else{
            sentWarning.setText("Fill all parts!");
        }
    }

    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
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



    protected void addRequest() throws IOException, ParseException {
        MassageLogin massageLogin = new MassageLogin(nameField.getText(), lessonField.getText(), scoreField.getText(),
                taField.getText(), getUsername());
        Controller.getInstance().addRecommendRequest(massageLogin);
    }

    protected String getUsername() throws IOException, ParseException {
        MassageUserDesk massageStudentPhdDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentPhdDesk);
    }
    protected String getUserId() throws IOException, ParseException {
        MassageUserDesk massageStudentPhdDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userId(massageStudentPhdDesk);
    }
    protected void getCondition() throws IOException, ParseException {
        MassageUserDesk massageResult = new MassageUserDesk(CurrentUser.getInstance().getUser());
        condition = Controller.getInstance().recommendResult(massageResult);
    }

    public void getTeacher() throws IOException, ParseException {
        MassageUserDesk massageResult = new MassageUserDesk(CurrentUser.getInstance().getUser());
        teacher = Controller.getInstance().recommendGetTeacher(massageResult);
    }
    public void getLessons() throws IOException, ParseException {
        MassageUserDesk massageResult = new MassageUserDesk(CurrentUser.getInstance().getUser());
        lessons = Controller.getInstance().recommendGetLessons(massageResult);
    }
    public void getScores() throws IOException, ParseException {
        MassageUserDesk massageResult = new MassageUserDesk(CurrentUser.getInstance().getUser());
        scores = Controller.getInstance().recommendGetScores(massageResult);
    }
    public void getTa() throws IOException, ParseException {
        MassageUserDesk massageResult = new MassageUserDesk(CurrentUser.getInstance().getUser());
        ta = Controller.getInstance().recommendGetTa(massageResult);

    }

    public void getResult(ActionEvent actionEvent) throws IOException, ParseException {
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
