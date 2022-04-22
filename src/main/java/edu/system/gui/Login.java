package edu.system.gui;

import edu.system.HelloApplication;
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
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Login {
    Stage stage;
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

    public void initialize (){
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
            captcha_image.setFitWidth(90);
            captcha_image.setFitHeight(31);
            Captcha.getChildren().remove(0,1);
            Captcha.getChildren().add(captcha_image);
            Captcha.setId("071497003");
            CaptchaInput.setText(null);
        }
        else if (i%6 == 2){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_3.png")));
            captcha_image.setFitWidth(90);
            captcha_image.setFitHeight(31);
            Captcha.getChildren().remove(0,1);
            Captcha.getChildren().add(captcha_image);
            Captcha.setId("538112");
            CaptchaInput.setText(null);
        }
        else if (i%6 == 3){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_4.png")));
            captcha_image.setFitWidth(90);
            captcha_image.setFitHeight(31);
            captcha_image.setId("6626512");
            Captcha.getChildren().remove(0,1);
            Captcha.getChildren().add(captcha_image);
            Captcha.setId("6626512");
            CaptchaInput.setText(null);
        }
        else if (i%6 == 4){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_5.png")));
            captcha_image.setFitWidth(90);
            captcha_image.setFitHeight(31);
            captcha_image.setId("571196");
            Captcha.getChildren().remove(0,1);
            Captcha.getChildren().add(captcha_image);
            Captcha.setId("571196");
            CaptchaInput.setText(null);
        }
        else if (i%6 == 5){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_6.png")));
            captcha_image.setFitWidth(90);
            captcha_image.setFitHeight(31);
            captcha_image.setId("6360424");
            Captcha.getChildren().remove(0,1);
            Captcha.getChildren().add(captcha_image);
            Captcha.setId("6360424");
            CaptchaInput.setText(null);
        }
        else if (i%6 == 0){
            ImageView captcha_image = new ImageView(String.valueOf(HelloApplication.class.getResource("images/captcha_1.png")));
            captcha_image.setFitWidth(90);
            captcha_image.setFitHeight(31);
            captcha_image.setId("625708");
            Captcha.getChildren().remove(0,1);
            Captcha.getChildren().add(captcha_image);
            Captcha.setId("625708");
            CaptchaInput.setText(null);
        }
    }

    @FXML
    protected void repeatCaptchaIconClicked(){
        randomCaptchaIcon();
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
                        stage.setHeight(650);
                        stage.setWidth(800);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("educational system");
                        stage.show();
                    }
                    else if(Objects.equals(loginForJson.getUserDegree(), "master")) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
                        Scene scene = new Scene(loader.load());
                        stage.setHeight(650);
                        stage.setWidth(800);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("educational system");
                        stage.show();
                    }
                    else if(Objects.equals(loginForJson.getUserDegree(), "phd")) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
                        Scene scene = new Scene(loader.load());
                        stage.setHeight(650);
                        stage.setWidth(800);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("educational system");
                        stage.show();
                    }

                }
                else if (Objects.equals(loginForJson.getUserType(), "teacher")){
                    if ((loginForJson.getUserDegree() == null) || (Objects.equals(loginForJson.getUserDegree(), "manager"))) {
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        stage.setHeight(650);
                        stage.setWidth(800);
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.setTitle("educational system");
                        stage.show();
                    }
                    else{
                        CurrentUser.getInstance().setUser(loginForJson.getUserName());
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
                            stage.setHeight(650);
                            stage.setWidth(800);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("educational system");
                            stage.show();
                        }
                        else if(Objects.equals(loginForJson.getUserDegree(), "master")) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setHeight(650);
                            stage.setWidth(800);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("educational system");
                            stage.show();
                        }
                        else if(Objects.equals(loginForJson.getUserDegree(), "phd")) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentPhdDesk.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setHeight(650);
                            stage.setWidth(800);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("educational system");
                            stage.show();
                        }

                    }
                    else if (Objects.equals(loginForJson.getUserType(), "teacher")){
                        if ((loginForJson.getUserDegree() == null) || (Objects.equals(loginForJson.getUserDegree(), "manager"))) {
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setHeight(650);
                            stage.setWidth(800);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("educational system");
                            stage.show();
                        }
                        else{
                            CurrentUser.getInstance().setUser(loginForJson.getUserName());
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            stage.setHeight(650);
                            stage.setWidth(800);
                            stage.setResizable(false);
                            stage.setScene(scene);
                            stage.setTitle("educational system");
                            stage.show();
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

    protected Boolean MainLogIn(){
        if (Objects.equals(CaptchaInput.getText(), Captcha.getId()) && CaptchaInput.getText() != null) {
            wrongCaptcha.setText(null);
            //ToDo
            MassageLogin massage = new MassageLogin(UserNameTextField.getText(), PasswordField.getText());
            if (Controller.getInstance().login(massage)){
                return true;
            }
            else{
                wrongUserPass.setText("wrong username or password");
            }

        }
        return false;

    }
}