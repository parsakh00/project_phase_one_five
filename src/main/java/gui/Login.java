package gui;

import ServerRunning.ServerMode;
import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
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
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import server.Controller;
import server.MassageInNetwork;
import server.ServerLogic;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class Login {
    Stage stage;
    Boolean isOk = false;
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

    String userType;

    String userName;

    String userDegree;
    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() throws IOException, ParseException {
        log.info("Log in page");
        ImageView imageRepeat = new ImageView(String.valueOf(ClientMain.class.getResource("images/repeat_icon.png")));
        ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_1.png")));
        imageRepeat.setFitHeight(18);
        imageRepeat.setFitWidth(18);
        captcha_image.setFitWidth(90);
        captcha_image.setFitHeight(31);
        Captcha.getChildren().add(captcha_image);
        Captcha.setId("625708");
        captchaRepeat.setGraphic(imageRepeat);
        timeDisplay();
    }

    public String getCaptchaId() {
        return Captcha.getId();
    }

    public void timeDisplay() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                Time.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    @FXML
    protected void onCheckBoxPassWordLogInClicked() {
        if (ShowPassWord.isSelected()) {
            passwordVisibleTextField.setText(PasswordField.getText());
            passwordVisibleTextField.toFront();
        } else {
            PasswordField.setText(passwordVisibleTextField.getText());
            PasswordField.toFront();
        }
    }

    protected void randomCaptchaIcon() {
        i += 1;
        if (i % 6 == 1) {
            ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_2.png")));
            setCaptchaImage(captcha_image, "071497003");
        } else if (i % 6 == 2) {
            ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_3.png")));
            setCaptchaImage(captcha_image, "538112");
        } else if (i % 6 == 3) {
            ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_4.png")));
            setCaptchaImage(captcha_image, "6626512");
        } else if (i % 6 == 4) {
            ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_5.png")));
            setCaptchaImage(captcha_image, "571196");
        } else if (i % 6 == 5) {
            ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_6.png")));
            setCaptchaImage(captcha_image, "6360424");
        } else if (i % 6 == 0) {
            ImageView captcha_image = new ImageView(String.valueOf(ClientMain.class.getResource("images/captcha_1.png")));
            setCaptchaImage(captcha_image, "625708");
        }
    }

    private void setCaptchaImage(ImageView imageView, String id) {
        imageView.setFitWidth(90);
        imageView.setFitHeight(31);
        imageView.setId(id);
        Captcha.getChildren().remove(0, 1);
        Captcha.getChildren().add(imageView);
        Captcha.setId(id);
        CaptchaInput.setText(null);
    }

    @FXML
    protected void repeatCaptchaIconClicked() {
        randomCaptchaIcon();
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void setType(String type) {
        this.userType = type;
    }

    public void setDegree(String degree) {
        this.userDegree = degree;
    }

    public String getUserDegree() {
        return userDegree;
    }

    public String getUserType() {
        return userType;
    }

    @FXML
    protected void LoginClicked(ActionEvent actionEvent) throws IOException {
        try {

            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), UserNameTextField.getText() + "-" + PasswordField.getText()
                    + "-" + getCaptchaId() + "-" + CaptchaInput.getText(), "log in"));

            Thread.sleep(500);

            if (isOk) {
                this.userName = UserNameTextField.getText();
                CurrentUser.getInstance().setUser(userName);
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), userName, "set name"));
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), userName, "get type"));
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), userName, "get degree"));
                Thread.sleep(500);

                if (Objects.equals(getUserType(), "student")) {
                    if (Objects.equals(getUserDegree(), "undergraduate")) {
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                        ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
                    } else if (Objects.equals(getUserDegree(), "master")) {
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                        ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
                    } else if (Objects.equals(getUserDegree(), "phd")) {
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                        ClientLogic.getInstance().setStudentPhdDesk(loader, stage);
                    }
                } else if (Objects.equals(CurrentUser.getInstance().getType(), "teacher")) {
                    if ((Objects.equals((String) getUserDegree(), (String) "-")) || (Objects.equals(CurrentUser.getInstance().getDegree(), "manager"))) {
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                        ClientLogic.getInstance().setTeacherDesk(loader, stage);
                    } else {
                        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
                        Scene scene = new Scene(loader.load());
                        setStageProp(stage, scene);
                        ClientLogic.getInstance().setEducationalAssistantDesk(loader, stage);
                    }
                }
            } else {
                if (Objects.equals(CaptchaInput.getText(), Captcha.getId()) && CaptchaInput.getText() != null) {
                    wrongCaptcha.setText(null);
                } else {
                    wrongCaptcha.setText("Enter numbers correctly");
                    i += 1;
                    randomCaptchaIcon();
                    CaptchaInput.setText(null);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (InterruptedException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void logInEntered(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            try {
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), UserNameTextField.getText() + "-" + PasswordField.getText()
                        + "-" + getCaptchaId() + "-" + CaptchaInput.getText(), "log in"));

                Thread.sleep(500);

                if (isOk) {
                    this.userName = UserNameTextField.getText();
                    CurrentUser.getInstance().setUser(userName);
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), userName, "set name"));
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), userName, "get type"));
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), userName, "get degree"));

                    Thread.sleep(500);

                    if (Objects.equals(getUserType(), "student")) {
                        if (Objects.equals(getUserDegree(), "undergraduate")) {
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                            ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
                        } else if (Objects.equals(getUserDegree(), "master")) {
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                            ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
                        } else if (Objects.equals(getUserDegree(), "phd")) {
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                            ClientLogic.getInstance().setStudentPhdDesk(loader, stage);
                        }
                    } else if (Objects.equals(CurrentUser.getInstance().getType(), "teacher")) {
                        if ((Objects.equals((String) getUserDegree(), (String) "-")) || (Objects.equals(CurrentUser.getInstance().getDegree(), "manager"))) {
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                            ClientLogic.getInstance().setTeacherDesk(loader, stage);
                        } else if (Objects.equals(CurrentUser.getInstance().getDegree(), "education assistant")) {
                            stage = ((Stage) ((Node) (keyEvent.getSource())).getScene().getWindow());
                            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
                            Scene scene = new Scene(loader.load());
                            setStageProp(stage, scene);
                            ClientLogic.getInstance().setEducationalAssistantDesk(loader, stage);
                        }
                    }
                } else {
                    if (Objects.equals(CaptchaInput.getText(), Captcha.getId()) && CaptchaInput.getText() != null) {
                        wrongCaptcha.setText(null);
                    } else {
                        wrongCaptcha.setText("Enter numbers correctly");
                        i += 1;
                        randomCaptchaIcon();
                        CaptchaInput.setText(null);
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (InterruptedException | ParseException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void mainLogIn(String content) {
        if (!Objects.equals(content, "wrong captcha")) {
            if (Objects.equals(content, "true")) {
                isOk = true;
            }
            if (Objects.equals(content, "Not allow")) {
                wrongUserPass.setText("Not allow");
                isOk = false;
            }
            if (Objects.equals(content, "wrong username or password")) {
                wrongUserPass.setText("wrong username or password");
                isOk = false;
            }
        } else {
            isOk = false;
        }
    }
}