package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.MassageLogin;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class StudentProfile {

    public Label userName;
    public Label email;
    public Label faculty;
    public Label supervisor;
    public Label condition;
    public Label userStage;
    public Pane imageOfUser;
    public Label id;
    public Label phoneNumber;
    public Label studentId;
    public Label score;
    public Label enteranceYear;
    
    public TextField changeNumber;
    public TextField changeEmail;
    public Label warningNumber;
    public Label warningEmail;
    Stage stage;

    static Logger log = LogManager.getLogger(HelloApplication.class);
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));


    public void initialize() throws IOException, ParseException {
        log.info("Open student profile page");
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
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
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
    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get user degree");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    public void backBtn(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
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
        else if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
    }
    public void showData(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Show data");
        email.setText(getEmail());
        userName.setText(getUsername());
        condition.setText(getEducationalStatus());
        supervisor.setText(getSupervisor());
        userStage.setText(getUserType());
        faculty.setText(getFacultyUser());
        phoneNumber.setText(phoneNumber());
        studentId.setText(getId());
        id.setText(getNationalId());
        score.setText(getScore());
        enteranceYear.setText(getYear());
        setUserImage();
    }
    protected String getId() throws IOException, ParseException {
        log.info("Get user id");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userId(massageStudentUndergraduateDesk);
    }
    protected String getEmail() throws IOException, ParseException {
        log.info("Get user email");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get username");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
    protected String getScore() throws IOException, ParseException {
        log.info("Get user score");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userScore(massageStudentUndergraduateDesk);
    }
    protected String getYear() throws IOException, ParseException {
        log.info("Get entering year");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userYear(massageStudentUndergraduateDesk);
    }
    protected String getSupervisor() throws IOException, ParseException {
        log.info("Get supervisor");
        MassageLogin supervisor = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().supervisor(supervisor);
    }
    protected String getNationalId(){
        log.info("Get national id");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userNationalId(massageUserDegree);
    }
    protected String getEducationalStatus() throws IOException, ParseException {
        log.info("Get education status");
        MassageLogin EducationalStatus = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().educationalStatus(EducationalStatus);
    }
    protected String getUserType() throws IOException, ParseException {
        log.info("Get user type");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskType(massageStudentUndergraduateDesk);
    }
    protected String getFacultyUser() throws IOException, ParseException {
        log.info("Get faculty pf user");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userFaculty(massageUserDegree);
    }
    public void setUserImage() throws IOException, ParseException {
        if (String.valueOf(HelloApplication.class.getResource("images/" + getUsername() + ".png")) == null) {
            log.info("Show image of user");
            ImageView userImage = new ImageView(String.valueOf(HelloApplication.class.getResource("images/" + getUsername() + ".png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            imageOfUser.getChildren().add(userImage);
        }
        else {
            log.info("Show default image");
            ImageView userImage = new ImageView(String.valueOf(HelloApplication.class.getResource("images/default.png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            imageOfUser.getChildren().add(userImage);
        }
    }
    protected String phoneNumber() throws IOException, ParseException {
        log.info("Get phone number");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userphoneNumber(massageStudentUndergraduateDesk);
    }
    public void changeEmailClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change email clicked");
        if (!Objects.equals((String) changeEmail.getText(), (String) null)) {
            editEmail();
            warningEmail.setText("Done");
            changeEmail.setText(null);
        }
        else {
            warningEmail.setText("First Fill the field!");
        }
    }
    public void changeNumberClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change number clicked");
        if (!Objects.equals((String) changeNumber.getText(), (String) null)) {
            editNumber();
            warningNumber.setText("Done");
            changeNumber.setText(null);
        }
        else {
            warningNumber.setText("First fill the field!");
        }
    }
    protected void editNumber() throws IOException, ParseException {
        MassageLogin massageEditPassword = new MassageLogin(getUsername(), changeNumber.getText(), null,null,null,null,null,null,null,null);
        Controller.getInstance().editPassProfile(massageEditPassword);
    }
    protected void editEmail() throws IOException, ParseException {
        MassageLogin massageEditEmail = new MassageLogin(getUsername(), changeEmail.getText(), null,null,null,null,null,null,null,null);
        Controller.getInstance().editEmailProfile(massageEditEmail);
    }
}
