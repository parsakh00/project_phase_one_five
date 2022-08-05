package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.*;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Login {
    Stage stage;
    String userCondition;
    private int i = 0;
    @FXML
    protected Label wrongCaptcha;
    @FXML
    public Label wrongUserPass;
    @FXML
    private Label Time;
    @FXML
    protected Button login;
    @FXML
    protected TextField UserNameTextField;
    @FXML
    protected Pane LoginBox;
    @FXML
    protected Label Username;
    @FXML
    protected Pane Captcha;
    @FXML
    protected Label Password;
    @FXML
    protected TextField CaptchaInput;
    @FXML
    protected CheckBox ShowPassWord;
    @FXML
    protected PasswordField PasswordField;
    @FXML
    protected TextField passwordVisibleTextField;
    @FXML
    protected Button captchaRepeat;
    static Logger log = LogManager.getLogger(HelloApplication.class);

    public void initialize () throws IOException, ParseException {
        log.info("Log in page");
        ImageView imageRepeat = new ImageView(String.valueOf(HelloApplication.class.getResource("images/repeat_icon.png")));
        ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_1.png")));
        imageRepeat.setFitHeight(18);
        imageRepeat.setFitWidth(18);
        captcha_image.setFitWidth(90);
        captcha_image.setFitHeight(31);
        Captcha.getChildren().add(captcha_image);
        Captcha.setId("625708");
        captchaRepeat.setGraphic(imageRepeat);
        timeDisplay();
    }
    public void timeDisplay(){
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }
    @FXML
    protected void onCheckBoxPassWordLogInClicked(){
        if (ShowPassWord.isSelected()){
            passwordVisibleTextField.setText(PasswordField.getText());
            passwordVisibleTextField.toFront();
        }
        else{
            PasswordField.setText(passwordVisibleTextField.getText());
            PasswordField.toFront();
        }
    }
    protected void randomCaptchaIcon(){
        i += 1;
        if (i%6 == 1){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_2.png")));
            setCaptchaImage(captcha_image, "071497003");
        }
        else if (i%6 == 2){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_3.png")));
            setCaptchaImage(captcha_image, "538112");
        }
        else if (i%6 == 3){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_4.png")));
            setCaptchaImage(captcha_image, "6626512");
        }
        else if (i%6 == 4){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_5.png")));
            setCaptchaImage(captcha_image, "571196");
        }
        else if (i%6 == 5){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_6.png")));
            setCaptchaImage(captcha_image, "6360424");
        }
        else if (i%6 == 0){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_1.png")));
            setCaptchaImage(captcha_image, "625708");
        }
    }
    private void setCaptchaImage(ImageView imageView, String id){
        imageView.setFitWidth(90);
        imageView.setFitHeight(31);
        imageView.setId(id);
        Captcha.getChildren().remove(0, 1);
        Captcha.getChildren().add(imageView);
        Captcha.setId(id);
        CaptchaInput.setText(null);
    }
    @FXML
    protected void repeatCaptchaIconClicked(){
        randomCaptchaIcon();
    }
    private void setStageProp(Stage stage, Scene scene){
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void LoginClicked(ActionEvent actionEvent) throws IOException {
        try {
            if (MainLogIn()) {
                LoginForJson loginForJson = new LoginForJson(UserNameTextField.getText(), PasswordField.getText());
                if (Objects.equals(loginForJson.getUserType(), "student")){
                    if(Objects.equals(loginForJson.getUserDegree(), "undergraduate")) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                    }
                    else if(Objects.equals(loginForJson.getUserDegree(), "master")) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                    }
                    else if(Objects.equals(loginForJson.getUserDegree(), "phd")) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentphd.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                    }
                }
                else if (Objects.equals(loginForJson.getUserType(), "teacher")){
                    if ((Objects.equals((String) loginForJson.getUserDegree(), (String) "-")) || (Objects.equals(loginForJson.getUserDegree(), "manager"))) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                    }
                    else{
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                    }
                }
            }
            else{
                if (Objects.equals(CaptchaInput.getText(), Captcha.getId()) && CaptchaInput.getText() != null) {
                    wrongCaptcha.setText(null);
                }
                else {
                    wrongCaptcha.setText("Enter numbers correctly");
                    i += 1;
                    randomCaptchaIcon();
                    CaptchaInput.setText(null);
                }
            }
        }catch (IOException ex){
            ex.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    protected void logInEntered(KeyEvent keyEvent) throws IOException{
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                if (MainLogIn()) {
                    LoginForJson loginForJson = new LoginForJson(UserNameTextField.getText(), PasswordField.getText());
                    if (Objects.equals(loginForJson.getUserType(), "student")){
                        if(Objects.equals(loginForJson.getUserDegree(), "undergraduate")) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                        }
                        else if(Objects.equals(loginForJson.getUserDegree(), "master")) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                        }
                        else if(Objects.equals(loginForJson.getUserDegree(), "phd")) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentPhd.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                        }
                    }
                    else if (Objects.equals(loginForJson.getUserType(), "teacher")){
                        if ((Objects.equals((String) loginForJson.getUserDegree(), (String) "-")) || (Objects.equals(loginForJson.getUserDegree(), "manager"))) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                        }
                        else if (Objects.equals(loginForJson.getUserDegree(), "education assistant")){
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                        }
                    }
                }
                else{
                    if (Objects.equals(CaptchaInput.getText(), Captcha.getId()) && CaptchaInput.getText() != null) {
                        wrongCaptcha.setText(null);

                    }
                    else {
                        wrongCaptcha.setText("Enter numbers correctly");
                        i += 1;
                        randomCaptchaIcon();
                        CaptchaInput.setText(null);
                    }
                }
            }catch (IOException ex){
                ex.printStackTrace();
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }
    protected Boolean MainLogIn() throws IOException, ParseException {
            if (Objects.equals(CaptchaInput.getText(), Captcha.getId()) && CaptchaInput.getText() != null) {
                wrongCaptcha.setText(null);
                MassageLogin massage = new MassageLogin(UserNameTextField.getText(), PasswordField.getText(), null,null,null,null,null,null,null,null);
                CurrentUser.getInstance().setUser(UserNameTextField.getText());
                if (Controller.getInstance().login(massage)) {
                    getCondition();
                    if (!Objects.equals(getCondition(), "withdrawal from education")) {
                        return true;
                    }
                    else {
                        wrongUserPass.setText("Not allow");
                    }
                } else {
                    wrongUserPass.setText("wrong username or password");
                }
            }
        return false;
    }
    protected String getCondition() throws IOException, ParseException {
        log.info("Check validity of condition for enterance");
        MassageLogin massageStudentUndergraduateDesk = new MassageLogin(CurrentUser.getInstance().getUser(), null,null);
        userCondition = Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
        return Controller.getInstance().userCondition(massageStudentUndergraduateDesk);
    }
}