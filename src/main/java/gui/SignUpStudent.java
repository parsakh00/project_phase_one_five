package gui;

import edu.system.ClientMain;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

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

    protected String[] facultyChoice = {"Chemistry","MathSci","MechanicEng","Physics","ElectricalEng"};

    protected String[] conditionChoice = {"withdrawal from education","studying","graduated"};

    protected String[] degreeChoice = {"phd","undergraduate","master"};

    static Logger log = LogManager.getLogger(ClientMain.class);
    public void initialize(){
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
        username.setText(null);
        id.setText(null);
        phoneNumber.setText(null);
        faculty.setText(null);
        supervisor.setText(null);
        degree.setText(null);
        email.setText(null);
        password.setText(null);
        condition.setText(null);
        enteringYear.setText(null);
        facultyList.getItems().addAll(facultyChoice);
        conditionList.getItems().addAll(conditionChoice);
        degreeList.getItems().addAll(degreeChoice);
        degreeList.setOnAction(this :: getDegreeChoice);
        facultyList.setOnAction(this :: getFacultyChoice);
        conditionList.setOnAction(this :: getConditionChoice);
    }
    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (sigUpWarning).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
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
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
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
        if (!Objects.equals((String) username.getText(), (String) "") && !Objects.equals((String) id.getText(), (String) "") && !Objects.equals((String) phoneNumber.getText(), (String) "") && !Objects.equals((String) supervisor.getText(), (String) "") && !Objects.equals((String) faculty.getText(), (String) "") &&
                !Objects.equals((String) enteringYear.getText(), (String) "") && !Objects.equals((String) condition.getText(), (String) "") && !Objects.equals((String) password.getText(), (String) "") && !Objects.equals((String) email.getText(), (String) "") && !Objects.equals((String) degree.getText(), (String) "")){
            signUpDone();
            sigUpWarning.setText("Done");
        }
        else{
            sigUpWarning.setText("Must fill all parts.");
        }
    }
    public void signUpDone(){
        log.info("Accomplish sign up");
        MassageInNetwork massageSignUp = new MassageInNetwork(username.getText(),id.getText(),phoneNumber.getText(),supervisor.getText(),
                faculty.getText(),enteringYear.getText(),condition.getText(),password.getText(), email.getText(), degree.getText(),null);
        Controller.getInstance().signUpUser(massageSignUp);
    }
    @FXML
    protected void pickImage(ActionEvent actionEvent) throws IOException {
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
                log.error("exception happened", e);
                e.printStackTrace();
            }
        }
        else {
            sigUpWarning.setText("First fill username");
        }

    }
}