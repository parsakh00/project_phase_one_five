package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.LoginForJson;
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
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherDesk {
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
    static Logger log = LogManager.getLogger(TeacherDesk.class);

    PauseTransition timer = new PauseTransition(Duration.seconds(10800));
    public void initialize() throws IOException, ParseException {
        log.info("Open teacher main, logged in");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened");
                throw new RuntimeException(e);
            }
        } );

        getLoginTime();
        setUserImage();
        timeDisplay();
        lastTimeLogIn.setText("last log in : " + getLoginTime());
        username.setText("User : " + getUsername());
        email.setText("Email : " + getEmail());
    }
    public void recommendationRequest() throws IOException {
        log.info("open recommendation requests");
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherRecommendRequest-view.fxml"));
        System.out.println("sag");
        Scene scene = new Scene(loader.load());
        System.out.println("pedar");
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void logOut() throws IOException {
        log.info("Logged out(out of time)");
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
                if (strLine.contains("TeacherDesk")){
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
        log.info("Get current user Ebail");
        MassageUserDesk massageTeacherDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskEmail(massageTeacherDesk);

    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user username");
        MassageUserDesk massageTeacherDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageTeacherDesk);
    }
    protected String getUserType() throws IOException, ParseException {
        MassageUserDesk massageTeacherDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskType(massageTeacherDesk);
    }
    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        log.info("User logged out by himself");
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
        log.info("Set current user image");
        userImage = new ImageView(String.valueOf(HelloApplication.class.getResource("images/" + getUsername() + ".png")));
        userImage.setFitHeight(160);
        userImage.setFitWidth(140);
        noidea.getChildren().add(userImage);
    }
    public void lessonListsClicked() throws IOException {
        log.info("Current user clicked lesson lists");
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
        log.info("Current user clicked teachers list");
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
    protected String getUserDegree() throws IOException, ParseException {
        log.info("Current user degree");
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    public void profileClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user profile clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherProfile.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void temporaryScoreClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user temporary score clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/temporaryScoreTeacher.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();

    }
}
