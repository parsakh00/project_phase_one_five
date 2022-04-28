package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageSignUp;
import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.security.PublicKey;

public class SignUpStudent {
    Stage stage;
    @FXML
    protected Button returnBack;

    @FXML
    protected TextField username,id,phoneNumber,supervisor,faculty,degree,email,password,condition,enteringYear;

    @FXML
    protected Button picker;
    @FXML
    protected ChoiceBox<String> facultyList;
    @FXML
    protected ChoiceBox<String> conditionList;
    @FXML
    protected ChoiceBox<String> degreeList;
    @FXML
    protected Button signUpBtn;
    @FXML
    protected ImageView userPhoto;
    @FXML
    protected Label sigUpWarning;

    protected String[] facultyChoice = {"Chemistry","MathSci","MechanicEng"};

    protected String[] conditionChoice = {"withdrawal from education","studying","graduated"};

    protected String[] degreeChoice = {"phd","undergraduate","master"};

    static Logger log = LogManager.getLogger(HelloApplication.class);
    protected void initialize(){
        log.info("Open sign up student page");
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
        facultyList.getItems().addAll(facultyChoice);
        conditionList.getItems().addAll(conditionChoice);
        degreeList.getItems().addAll(degreeChoice);
        facultyList.setOnAction(this :: getFacultyChoice);
        conditionList.setOnAction(this :: getConditionChoice);
        degreeList.setOnAction(this :: getDegreeChoice);
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (sigUpWarning).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void backBtnClicked(ActionEvent actionEvent) throws IOException {
        log.info("Back Button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    protected void getFacultyChoice(ActionEvent actionEvent){
        log.info("Get faculty choice");
        faculty.setText(facultyList.getValue());
    }
    protected void getConditionChoice(ActionEvent actionEvent){
        log.info("Get condition choice");
        condition.setText(conditionList.getValue());
    }
    protected void getDegreeChoice(ActionEvent actionEvent){
        log.info("Get degree choice");
        degree.setText(degreeList.getValue());
    }
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    public void signUpBtnClicked(){
        log.info("sign up Button clicked");
        if (username.getText()!= null && id.getText()!= null && phoneNumber.getText()!= null && supervisor.getText()!= null && faculty.getText()!= null &&
                enteringYear.getText()!= null && condition.getText()!= null && password.getText()!= null && email.getText()!= null && degree.getText()!= null){
            signUpDone();
            sigUpWarning.setText("Done");
        }
        else{
            sigUpWarning.setText("Must fill all parts.");
        }
    }
    public void signUpDone(){
        log.info("Accomplish sign up");
        MassageSignUp massageSignUp = new MassageSignUp(username.getText(),id.getText(),phoneNumber.getText(),supervisor.getText(),
                faculty.getText(),enteringYear.getText(),condition.getText(),password.getText(), email.getText(), degree.getText());
        Controller.getInstance().signUpUser(massageSignUp);
    }
    @FXML
    protected void pickImage() throws IOException {
        log.info("Pick image for new user");
        if (username.getText() != null) {
            stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("choose an image");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                javafx.scene.image.Image originalPhoto = new javafx.scene.image.Image(selectedFile.toURI().toString());
                userPhoto.setImage(originalPhoto);
            }
            Image imageToBeSaved = userPhoto.getImage();
            File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\system\\images\\" + username.getText() + ".png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imageToBeSaved, null), "png", file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            sigUpWarning.setText("First fill username");
        }

    }
}