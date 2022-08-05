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

public class TeacherProfile {

    public Label email;
    public Label username;
    public Label faculty;
    public Label degree;
    public Pane imageOfUser;
    public Label id;
    public Label phoneNumber;
    public Label teacherId;
    public Label roomNo;
    public TextField changeNumber;
    public TextField changeEmail;
    public Label warningNumber;
    public Label warningEmail;
    Stage stage;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(HelloApplication.class);


    public void initialize(){
        log.info("Open teacher profile");
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
        changeNumber.setText(null);
        changeEmail.setText(null);
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
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    protected String getUserMastery() throws IOException, ParseException {
        log.info("Get teacher mastery");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userMastery(massageUserDegree);
    }

    public void backBtn(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "education assistant")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "manager") || Objects.equals(getUserDegree(), "")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherDesk-view.fxml"));
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
        log.info("Show teacher data");
        email.setText(getEmail());
        username.setText(getUsername());
        faculty.setText(getFacultyUser());
        degree.setText(getUserMastery());
        teacherId.setText(getUserId());
        phoneNumber.setText(phoneNumber());
        id.setText(getNationalId());
        roomNo.setText(getRoomNo());
        setUserImage();

    }
    protected String getFacultyUser() throws IOException, ParseException {
        log.info("Get user faculty");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userFaculty(massageUserDegree);
    }
    protected String getNationalId(){
        log.info("Get national id");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userNationalId(massageUserDegree);
    }
    protected String getRoomNo(){
        log.info("Get teacher room No.");
        MassageLogin massageUserDegree = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userRoomNo(massageUserDegree);
    }
    protected String getEmail() throws IOException, ParseException {
        log.info("Get teacher email");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get user, username");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
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
        log.info("Get teacher phone number");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userphoneNumber(massageStudentUndergraduateDesk);
    }
    protected String getUserId() throws IOException, ParseException {
        log.info("Get teacher id");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUserName(), null,null);
        return Controller.getInstance().userId(massageStudentUndergraduateDesk);
    }
    public void changeEmailClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change email confirmation");
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
        log.info("Change number confirmation");
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
        log.info("Edit teacher number");
        MassageLogin massageEditPassword = new MassageLogin(getUsername(), changeNumber.getText(), null,null,null,null,null,null,null,null);
        Controller.getInstance().editPassProfile(massageEditPassword);
    }
    protected void editEmail() throws IOException, ParseException {
        log.info("Edit teacher email");
        MassageLogin massageEditEmail = new MassageLogin(getUsername(), changeEmail.getText(), null,null,null,null,null,null,null,null);
        Controller.getInstance().editEmailProfile(massageEditEmail);
    }
}
