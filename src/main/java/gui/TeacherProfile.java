package gui;

import ServerRunning.ServerMode;
import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import javafx.application.Platform;
import message.Message;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.awt.font.ShapeGraphicAttribute;
import java.io.IOException;
import java.util.Objects;

public class TeacherProfile {

    public Label email;
    public Label username;
    public Label faculty;
    public Label degree;
    public Pane imageOfUser;
    public Label id;
    public Label phoneNumber;
    public Label teacherId;
    public Label roomNo;
    public TextField changeNumber;
    public TextField changeEmail;
    public Label warningNumber;
    public Label warningEmail;
    public Label serverCondition;
    Stage stage;
    String name;
    ImageView userImage;
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(ClientMain.class);


    public void initialize() throws IOException, ParseException {
        log.info("Open teacher profile");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened", e);
                throw new RuntimeException(e);
            }
        });
        changeNumber.setText(null);
        changeEmail.setText(null);
        log.info("Show teacher data");
        Thread ping = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (ServerMode.getInstance().isOnline()) {
                        Platform.runLater(() -> {
                            serverCondition.setText("Server is online");
                        });
                        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "user data teacher"));
                        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "set name"));
                        try {
                            setUserImage();
                        } catch (IOException | ParseException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        Platform.runLater(() -> {
                            serverCondition.setText("server is offline");
                            try {
                                setProfileOffline();
                            } catch (IOException | ParseException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    // delay 5 seconds
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        ping.start();
    }

    private void setProfileOffline() throws IOException, ParseException {
        username.setText(CurrentUser.getInstance().getUserName());
        id.setText(CurrentUser.getInstance().getId());
        teacherId.setText(CurrentUser.getInstance().getId());
        phoneNumber.setText(CurrentUser.getInstance().getPhoneNumber());
        email.setText(CurrentUser.getInstance().getEmail());
        faculty.setText(CurrentUser.getInstance().getFaculty());
        roomNo.setText(CurrentUser.getInstance().getRoomNo());
        degree.setText(CurrentUser.getInstance().getDegree());
        setUserImage();
    }

    public void logOut() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Logged out, out of time");
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setHeight(650);
                stage.setWidth(800);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("educational system");
                stage.show();
                ClientLogic.getInstance().setLogOutDesk(loader, stage);
            });
        }
    }

    public void backBtn(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "back to main page"));
            });
        }
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(CurrentUser.getInstance().getDegree(), "education assistant")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
            ClientLogic.getInstance().setEducationalAssistantDesk(loader, stage);
        } else if (Objects.equals(CurrentUser.getInstance().getDegree(), "manager") || Objects.equals(CurrentUser.getInstance().getDegree(), "")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
            ClientLogic.getInstance().setTeacherDesk(loader, stage);
        }
    }

    public void showDataList(String data) {
        Platform.runLater(() -> {
            String[] dataList = data.split("-");
            email.setText(dataList[1]);
            username.setText(dataList[0]);
            faculty.setText(dataList[2]);
            phoneNumber.setText(dataList[3]);
            degree.setText(dataList[4]);
            id.setText(dataList[5]);
            teacherId.setText(dataList[6]);
            roomNo.setText(dataList[7]);
        });
    }

    public void setUserName(String name) {
        this.name = name;
    }

    public void setUserImage() throws IOException, ParseException {
        Platform.runLater(() -> {
            if (String.valueOf(ClientMain.class.getResource("images/" + name + ".png")) == null) {
                log.info("Show image of user");
                userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/" + name + ".png")));
            } else {
                log.info("Show default image");
                userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/default.png")));
            }
            userImage.setFitHeight(160);
            userImage.setFitWidth(140);
            imageOfUser.getChildren().add(userImage);
        });
    }

    public void changeEmailClicked(ActionEvent actionEvent) throws IOException, ParseException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                warningEmail.setText(null);
                log.info("Change email confirmation");
                if (!Objects.equals((String) changeEmail.getText(), (String) null)) {
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), changeEmail.getText() + "-" + CurrentUser.getInstance().getUserName(), "edit email clicked teacher"));
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "user data teacher"));
                } else {
                    warningEmail.setText("First Fill the field!");
                }
            });
        } else {
            Platform.runLater(() -> {
                warningEmail.setText("server is down!");
            });
        }
    }

    public void changeNumberClicked(ActionEvent actionEvent) throws IOException, ParseException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                warningNumber.setText(null);
                log.info("Change number confirmation");
                if (!Objects.equals((String) changeNumber.getText(), (String) null)) {
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), changeNumber.getText() + "-" + CurrentUser.getInstance().getUserName(), "edit number clicked teacher"));
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "user data teacher"));
                } else {
                    warningNumber.setText("First fill the field!");
                }
            });
        } else {
            Platform.runLater(() -> {
                warningNumber.setText("server is down!");
            });
        }
    }
}