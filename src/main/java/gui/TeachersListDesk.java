package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import server.Controller;
import server.MassageInNetwork;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class TeachersListDesk {
    @FXML
    protected Button forEduAssis;
    @FXML
    protected Label email;
    @FXML
    protected Label username;
    Stage stage;
    String degree;
    @FXML
    protected Button drawBackBtn;
    String[] facultyTeachers;

    protected String[] faculty = {"Chemistry", "MathSci", "MechanicEng"};

    @FXML
    protected Label timeDate;
    @FXML
    protected ChoiceBox<String> teacherChoiceBox;
    @FXML
    protected GridPane TeachersLists;

    @FXML
    protected Button editEduBtn;
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open teacher list desk");
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
        timeDisplay();
        teacherChoiceBox.getItems().addAll(faculty);
        teacherChoiceBox.setOnAction(this::getFaculty);
        email.setText(getEmail());
        username.setText(getUsername());

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

    public void editingEduLesson(ActionEvent actionEvent) throws IOException, ParseException, InterruptedException {
        log.info("Editing lessons");
        timer.pause();
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "get degree teacher list"));
        Thread.sleep(100);
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        if (Objects.equals(getUserType(), "teacher")) {
            if (Objects.equals(degree, "education assistant")) {
                stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/EditLesson-view.fxml"));
                Scene scene = new Scene(loader.load());
                setStageProp(stage, scene);
                ClientLogic.getInstance().setEditLessonsDesk(loader, stage);
            }
        }
    }

    protected String getEmail() throws IOException, ParseException {
        log.info("Get user email");
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskEmail(massageStudentUndergraduateDesk);

    }

    protected String getUsername() throws IOException, ParseException {
        log.info("Get user, username");
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }

    protected String getUserType() throws IOException, ParseException {
        log.info("Get user type");
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskType(massageStudentMasterDesk);
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void setDegree(String str) {
        this.degree = str;
    }

    public void returnBtn() throws IOException, ParseException, InterruptedException {
        log.info("Return button click");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "get degree teacher list"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "back to main page"));
        Thread.sleep(200);
        stage = ((Stage) (email).getScene().getWindow());
        if (Objects.equals(degree, "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
        } else if (Objects.equals(degree, "phd")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentPhdDesk(loader, stage);
        } else if (Objects.equals(degree, "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
        } else if (Objects.equals(degree, "manager") || Objects.equals(getUserType(), "-")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setTeacherDesk(loader, stage);
        } else if (Objects.equals(degree, "education assistant")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setEducationalAssistantDesk(loader, stage);
        }
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

    protected void getFaculty(ActionEvent actionEvent) {
        log.info("Get user faculty");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), teacherChoiceBox.getValue(), "get teacher list of faculty"));
    }

    public void getTeacherListData(Message message) {
        if (message.getContent() != null) {
            this.facultyTeachers = message.getContent().split("-");
            Platform.runLater(() -> {
                TeachersLists.getChildren().clear();
                for (int i = 0; i < 4; i++) {
                    Label label = new Label();
                    if (i == 0) label.setText("Room");
                    if (i == 1) label.setText("Phone");
                    if (i == 2) label.setText("Email");
                    if (i == 3) label.setText("Name");
                    TeachersLists.add(label, i, 0);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                int i = 0;
                int j = 1;
                for (String eachElement : facultyTeachers) {
                    if (!Objects.equals(eachElement, "null")) {
                        Label label = new Label();
                        if (i % 4 == 1) label.setText(eachElement);
                        else if (i % 4 == 2) label.setText(eachElement);
                        else if (i % 4 == 3) label.setText(eachElement);
                        else if (i % 4 == 0) label.setText(eachElement);
                        label.setAlignment(Pos.CENTER);
                        GridPane.setHalignment(label, HPos.CENTER);
                        TeachersLists.add(label, i % 4, j);
                        i += 1;
                        if (i % 4 == 0) j += 1;
                    }
                }
            });
        } else {
            Platform.runLater(() -> {
                TeachersLists.getChildren().clear();
                for (int i = 0; i < 4; i++) {
                    Label label = new Label();
                    if (i == 0) label.setText("Room");
                    if (i == 1) label.setText("Phone");
                    if (i == 2) label.setText("Email");
                    if (i == 3) label.setText("Name");
                    TeachersLists.add(label, i, 0);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
            });
        }
    }

    public void changingEdiAssis(ActionEvent actionEvent) throws IOException, ParseException, InterruptedException {
        log.info("Change education assistant");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "get degree teacher list"));
        Thread.sleep(100);
        timer.pause();
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "change to changeEduAssisOrEdit-view fxml"));
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        if (Objects.equals(getUserType(), "teacher")) {
            if (Objects.equals(degree, "manager")) {
                stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/changeEduAssisOrEdit-view.fxml"));
                Scene scene = new Scene(loader.load());
                setStageProp(stage, scene);
                ClientLogic.getInstance().setChangeEduAssisOrEdit(loader, stage);
            }
        }
    }
}
