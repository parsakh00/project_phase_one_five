package gui;

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

import java.io.IOException;
import java.util.Objects;

public class StudentProfile {

    public Label userName;
    public Label email;
    public Label faculty;
    public Label supervisor;
    public Label condition;
    public Label userStage;
    public Pane imageOfUser;
    public Label id;
    public Label phoneNumber;
    public Label studentId;
    public Label score;
    public Label enteranceYear;

    public TextField changeNumber;
    public TextField changeEmail;
    public Label warningNumber;
    public Label warningEmail;
    public ImageView imageUser;
    String username;
    String userDegree;

    Stage stage;

    static Logger log = LogManager.getLogger(ClientMain.class);
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void initialize() throws IOException, ParseException, InterruptedException {
        log.info("Open student profile page");
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
        log.info("Show data");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show data student"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "set name and degree"));
        setUserImage();
    }

    public void logOut() throws IOException {
        log.info("Logged out, out of time");
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    public void setUserDegreeAndName(String message) {
        String[] degreeUser = message.split("-");
        username = degreeUser[0];
        userDegree = degreeUser[1];
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void backBtn(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "back to main page"));
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(userDegree, "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
        } else if (Objects.equals(userDegree, "phd")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentPhdDesk(loader, stage);
        } else if (Objects.equals(userDegree, "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
        }
    }

    public void showDataList(String data) {
        Platform.runLater(() -> {
            String[] dataList = data.split("-");
            userName.setText(dataList[0]);
            studentId.setText(dataList[1]);
            id.setText(dataList[2]);
            email.setText(dataList[3]);
            condition.setText(dataList[4]);
            supervisor.setText(dataList[5]);
            faculty.setText(dataList[6]);
            phoneNumber.setText(dataList[7]);
            enteranceYear.setText(dataList[8]);
            userStage.setText(dataList[9]);
            score.setText(dataList[10]);
        });
    }

    public void setUserImage() throws IOException, ParseException {
        log.info("Show image of user");
        if (String.valueOf(ClientMain.class.getResource("images/" + username + ".png")) == null) {
            imageUser = new ImageView(String.valueOf(ClientMain.class.getResource("images/" + username + ".png")));
        } else {
            imageUser = new ImageView(String.valueOf(ClientMain.class.getResource("images/default.png")));
        }
        imageUser.setFitHeight(160);
        imageUser.setFitWidth(140);
        imageOfUser.getChildren().add(imageUser);
    }

    public void changeEmailClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change email clicked");
        if (!Objects.equals((String) changeEmail.getText(), (String) null)) {
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), changeEmail.getText() + "-" + CurrentUser.getInstance().getUserName(), "edit email clicked student"));
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show data student"));
        } else {
            warningEmail.setText("First Fill the field!");
        }
    }

    public void changeNumberClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change number clicked");
        if (!Objects.equals((String) changeNumber.getText(), (String) null)) {
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), changeNumber.getText() + "-" + CurrentUser.getInstance().getUserName(), "edit number clicked student"));
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show data student"));
        } else {
            warningNumber.setText("First fill the field!");
        }
    }
}