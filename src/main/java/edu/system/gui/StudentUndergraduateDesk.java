package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.cert.Certificate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StudentUndergraduateDesk {

    @FXML
    public MenuItem minor;
    public Menu profile;

    String lastLogIn;
    Stage stage;
    @FXML
    protected AnchorPane studentView;
    @FXML
    protected Label email;
    @FXML
    protected Label timeDate;
    @FXML
    protected Label username;
    @FXML
    protected Button logOut;
    @FXML
    protected Pane noidea;
    @FXML
    protected ImageView userImage;
    @FXML
    protected Label lastTimeLogIn;
    @FXML
    protected MenuItem lessonLists;
    @FXML
    protected Label condition;
    @FXML
    protected Label supervisor;
    @FXML
    protected MenuItem weeklyScheduleList;
    @FXML
    protected MenuItem studentExam;
    @FXML
    protected MenuItem Certificate;
    @FXML
    protected MenuItem Recomm;
    @FXML
    protected MenuItem WithdrawalEducation;

    static Logger log = LogManager.getLogger(StudentUndergraduateDesk.class);
    PauseTransition timer = new PauseTransition(Duration.seconds(10800));
    public void initialize() throws IOException, ParseException {
        log.info("Undergraduate student main, logged in");
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
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } );
        log.info("logged in");
        getLoginTime();
        setUserImage();
        timeDisplay();
        lastTimeLogIn.setText("last log in : " + getLoginTime());
        username.setText("User : " + getUsername());
        email.setText("Email : " + getEmail());
        condition.setText(getEducationalStatus());
        supervisor.setText(getSupervisor());
    }
    public void minorClicked(ActionEvent actionEvent) throws IOException {
        log.info("Minor request clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/Minor-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void WithdrawalEducation() throws IOException {
        log.info("Withdrawal from education request clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/withdrawalFromEducation-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void certificateClicked() throws IOException {
        log.info("Apply for certificate employment clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/ApplyCertificateEmployment-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void recommendationClicked() throws IOException {
        log.info("Recommendation request clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/Recommendation-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void scheduleClicked() throws IOException {
        log.info("Weekly schedule clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/weeklySchedule-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void examClicked() throws IOException {
        log.info("List of exams clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/examsLists.fxml"));
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
    public void logOut() throws IOException {
        log.info("Logged out from student main page");
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
    public String getLoginTime(){
        try{
            FileInputStream fstream = new FileInputStream("./src/main/resources/logs/userActivity.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                if (strLine.contains("StudentUndergraduateDesk")){
                    Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
                    Matcher matcher = pattern.matcher(strLine);
                    if(matcher.find()){
                        lastLogIn = matcher.group();
                    }
                    break;
                }
            }
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lastLogIn;
    }
    protected String getEmail() throws IOException, ParseException {
        log.info("Get current student Email");
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current student Username");
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
    protected String getEducationalStatus() throws IOException, ParseException {
        log.info("Get current student Educational status");
        MassageUserDesk EducationalStatus = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().educationalStatus(EducationalStatus);
    }
    protected String getSupervisor() throws IOException, ParseException {
        log.info("Get current student supervisor");
        MassageUserDesk supervisor = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().supervisor(supervisor);
    }
    protected String getUserType() throws IOException, ParseException {
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskType(massageStudentUndergraduateDesk);
    }
    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current student logged out himself");
        timer.pause();
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void setUserImage() throws IOException, ParseException {
        if (String.valueOf(HelloApplication.class.getResource("images/" + getUsername() + ".png")) == null) {
            log.info("Show image of user");
            ImageView userImage = new ImageView(String.valueOf(HelloApplication.class.getResource("images/" + getUsername() + ".png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            noidea.getChildren().add(userImage);
        }
        else {
            log.info("Show default image");
            ImageView userImage = new ImageView(String.valueOf(HelloApplication.class.getResource("images/default.png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            noidea.getChildren().add(userImage);
        }
    }
    public void lessonListsClicked() throws IOException {
        log.info("Lesson lists clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/lessonLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void teachersListsClicked() throws IOException {
        log.info("Teachers list clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void profileClicked(ActionEvent actionEvent) throws IOException {
        log.info("Profile clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentProfile.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void temporaryScoreClicked(ActionEvent actionEvent) throws IOException {
        log.info("Temporary score clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/temporaryScoreStudent-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();

    }
    public void studentsStatusClicked(ActionEvent actionEvent) throws IOException {
        log.info("Students status clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/StudentsEducationalStatus-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
}
