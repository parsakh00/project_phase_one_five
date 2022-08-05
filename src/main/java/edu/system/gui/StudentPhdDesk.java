package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.MassageInNetwork;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentPhdDesk {

    static Logger log = LogManager.getLogger(StudentPhdDesk.class);
    public MenuItem teacherslist;
    public MenuItem lessonLists;
    String lastLogIn;
    Stage stage;
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

    protected ImageView userImage;
    @FXML
    protected Label lastTimeLogIn;
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
    protected MenuItem WithdrawalEducation;
    @FXML
    protected MenuItem requestDefendDissertation;
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void logOut() throws IOException {
        log.info("Open Phd student main");
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
    @FXML
    protected void requestDefendDissertation() throws IOException {
        log.info("Open request defend dissertation");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/requestDefendDissertation-view.fxml"));
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
        log.info("Current user withdrawal education clicked");
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
        log.info("Current user certificate clicked");
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
    protected void scheduleClicked() throws IOException {
        log.info("Current user weekly schedule clicked");
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
        log.info("Current user exam clicked clicked");
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
    public void initialize() throws IOException, ParseException {
        log.info("Open Phd student main");
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
    public void timeDisplay(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }
    public String getLoginTime(){

        try{
            FileInputStream fstream = new FileInputStream("./src/main/resources/logs/userActivity.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null){
                if (strLine.contains("StudentPhdDesk")){
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
        log.info("Current user email");
        MassageInNetwork massageStudentPhdDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskEmail(massageStudentPhdDesk);

    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Current user username");
        MassageInNetwork massageStudentPhdDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskUserName(massageStudentPhdDesk);
    }
    protected String getUserType() throws IOException, ParseException {
        MassageInNetwork massageStudentPhdDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskType(massageStudentPhdDesk);
    }
    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user logged out by himself");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
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
    protected String getEducationalStatus() throws IOException, ParseException {
        log.info("Current user education status");
        MassageInNetwork EducationalStatus = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().educationalStatus(EducationalStatus);
    }
    protected String getSupervisor() throws IOException, ParseException {
        log.info("Current user supervisor");
        MassageInNetwork supervisor = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().supervisor(supervisor);
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
        log.info("Lessons list clicked");
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
        log.info("Teachers lists clicked");
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
        log.info("Current user profile clicked");
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
        log.info("Current user Temporary score clicked");
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
        log.info("Current user status clicked");
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
