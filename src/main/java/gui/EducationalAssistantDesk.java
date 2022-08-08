package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
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

public class EducationalAssistantDesk {

    public MenuItem lessonlists;

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
    @FXML
    protected ImageView userImage;
    @FXML
    protected Label lastTimeLogIn;
    @FXML
    protected Button signupBtn;
    static Logger log = LogManager.getLogger(EducationalAssistantDesk.class);
    PauseTransition timer = new PauseTransition(Duration.seconds(10800));


    public void initialize() throws IOException, ParseException {
        log.info("Open educational assistant main page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("logged in");
        getLoginTime();
        setUserImage();
        timeDisplay();
        lastTimeLogIn.setText("last log in : " + getLoginTime());
        username.setText("User : " + getUsername());
        email.setText("Email : " + getEmail());
    }

    public void signUpBtnClicked(ActionEvent actionEvent) throws IOException {
        log.info("sign up new student clicked");
        timer.pause();
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "go to signup student fxml"));
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/signupStudent-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setSignUpStudent(loader, stage);
    }

    public void signUpBtnTeacherClicked(ActionEvent actionEvent) throws IOException {
        log.info("sign up new teacher clicked");
        timer.pause();
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "go to signup teacher fxml"));
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/signupTeacher.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setSignUpTeacher(loader, stage);
    }

    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user Logged out by him self");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogin(loader, stage);
    }

    public void timeDisplay() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    public String getLoginTime() {

        try {
            FileInputStream fstream = new FileInputStream("./src/main/resources/logs/userActivity.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("EducationalAssistantDesk")) {
                    Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
                    Matcher matcher = pattern.matcher(strLine);
                    if (matcher.find()) {
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
        MassageInNetwork massageEducationalAssistantDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskEmail(massageEducationalAssistantDesk);

    }

    protected String getUsername() throws IOException, ParseException {
        log.info("Current user name");
        MassageInNetwork massageducationalAssistantDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskUserName(massageducationalAssistantDesk);
    }

    public void setUserImage() throws IOException, ParseException {
        if (String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")) == null) {
            log.info("Show image of user");
            ImageView userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            noidea.getChildren().add(userImage);
        } else {
            log.info("Show default image");
            ImageView userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/default.png")));
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            noidea.getChildren().add(userImage);
        }
    }

    public void logOut() throws IOException {
        log.info("Current user logged out ");
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void lessonListsClicked() throws IOException {
        log.info("Current user lessons lists");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
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
        log.info("Current user teachers lists");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
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

    public void recommendationRequest() throws IOException {
        log.info("Recommendation request clicked");
        stage = ((Stage) (email).getScene().getWindow());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "change fxml to teacherRecommendRequest fxml"));
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherRecommendRequest-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setTeacherRecommendRequest(loader, stage);
    }

    public void withdrawRequest(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Current user withdraw request");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "change fxml to EduWithdrawRequest-view fxml"));
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/EduWithdrawRequest-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setEduWithdrawRequest(loader, stage);

    }

    public void minorRequest(ActionEvent actionEvent) throws IOException {
        log.info("Current user minor request");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "change fxml to eduMinorRequest-view fxml"));
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/eduMinorRequest-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setEduMinorRequests(loader, stage);
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("get Current user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void profileClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user profile");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to profile fxml"));
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherProfile.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setTeacherProfile(loader, stage);
    }

    public void temporaryScoreClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Temporary score clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/temporaryScoreTeacher-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        setStageProp(stage, scene);
    }

    public void studentsStatusClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("students status clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/StudentsEducationalStatusEdu-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        setStageProp(stage, scene);
    }
}
