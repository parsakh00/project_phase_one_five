package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import javafx.scene.control.*;
import message.Message;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

public class StudentUndergraduateDesk {

    @FXML
    public MenuItem minor;
    public Menu profile;
    public TextField messageForAdmin;
    public Label alertMessageForAdmin;

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
    PauseTransition timer = new PauseTransition(Duration.seconds(10000));
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
        messageForAdmin.setText(null);
    }
    public void minorClicked(ActionEvent actionEvent) throws IOException {
        log.info("Minor request clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/Minor-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setMinorDesk(loader, stage);
    }
    @FXML
    protected void WithdrawalEducation() throws IOException {
        log.info("Withdrawal from education request clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/withdrawalFromEducation-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setWithdrawalFromEducationDesk(loader, stage);
    }
    @FXML
    protected void certificateClicked() throws IOException {
        log.info("Apply for certificate employment clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/ApplyCertificateEmployment-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setApplyCertificateEmploymentDesk(loader, stage);
    }
    @FXML
    protected void recommendationClicked() throws IOException {
        log.info("Recommendation request clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/Recommendation-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setRecommendation(loader, stage);
    }
    @FXML
    protected void scheduleClicked() throws IOException {
        log.info("Weekly schedule clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/weeklySchedule-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setWeeklySchedule(loader, stage);
    }
    @FXML
    protected void examClicked() throws IOException {
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/examsLists.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setExamsList(loader, stage);
    }
    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current student logged out himself");
        timer.pause();
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "Current student logged out himself"));
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogin(loader, stage);
    }
    private void setStageProp(Stage stage, Scene scene) {
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
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
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
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current student Username");
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
    protected String getEducationalStatus() throws IOException, ParseException {
        log.info("Get current student Educational status");
        MassageInNetwork EducationalStatus = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().educationalStatus(EducationalStatus);
    }
    protected String getSupervisor() throws IOException, ParseException {
        log.info("Get current student supervisor");
        MassageInNetwork supervisor = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().supervisor(supervisor);
    }
    public void setUserImage() throws IOException, ParseException {
        if (String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")) == null) {
            log.info("Show image of user");
            ImageView userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            noidea.getChildren().add(userImage);
        }
        else {
            log.info("Show default image");
            ImageView userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/default.png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            noidea.getChildren().add(userImage);
        }
    }
    public void lessonListsClicked() throws IOException {
        log.info("Lesson lists clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to lessonList-view fxml"));
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/lessonLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setLessonListDesk(loader, stage);
    }
    public void teachersListsClicked() throws IOException {
        log.info("Teachers list clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to teacherLists-view fxml"));
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setTeachersListDesk(loader, stage);
    }
    public void profileClicked(ActionEvent actionEvent) throws IOException {
        log.info("Profile clicked");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to profile fxml"));
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentProfile.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setStudentProfile(loader, stage);
    }
    public void temporaryScoreClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Temporary score clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/temporaryScoreStudent-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        stage.setHeight(650);
//        stage.setWidth(800);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.setTitle("educational system");
//        stage.show();
    }
    public void studentsStatusClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Students status clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/StudentsEducationalStatus-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        stage.setHeight(650);
//        stage.setWidth(800);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.setTitle("educational system");
//        stage.show();
    }

    public void submitForAdmin(ActionEvent actionEvent) {
        if (messageForAdmin.getText()!= null){
            alertMessageForAdmin.setText(null);
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName() +"-"+
                    messageForAdmin.getText(), "message for admin"));
            alertMessageForAdmin.setText("Done!");
        }
        else{
            alertMessageForAdmin.setText("First write your message!");
        }
    }
}