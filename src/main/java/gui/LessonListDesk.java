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
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
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
import server.Logic;
import server.MassageInNetwork;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

public class LessonListDesk {

    Stage stage;
    String[] facultyLessons;
    String degree;
    @FXML
    protected Label loginTIme;
    @FXML
    protected Label timeDate;
    @FXML
    protected Label email;
    @FXML
    protected Label userName;
    @FXML
    protected GridPane filterGrid;
    @FXML
    protected ChoiceBox<String> stageChoiceBox;
    @FXML
    protected ChoiceBox<String> unityChoiceBox;
    @FXML
    protected ChoiceBox<String> facultyChoiceBox;
    @FXML
    protected CheckBox stageCheckBox;
    @FXML
    protected CheckBox unityCheckBox;

    protected String[] stagee = {"undergraduate", "master", "phd"};

    protected String[] unity = {"0", "1", "2", "3", "4"};

    protected String[] faculty = {"Chemistry", "MathSci", "MechanicEng", "Physics", "ElectricalEng"};

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open lesson list desk page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception error", e);
                throw new RuntimeException(e);
            }
        });
        timeDisplay();
        email.setText("Email : " + getEmail());
        userName.setText("Username : " + getUsername());
        facultyChoiceBox.getItems().addAll(faculty);
        unityChoiceBox.getItems().addAll(unity);
        stageChoiceBox.getItems().addAll(stagee);
        facultyChoiceBox.setOnAction(this::getFaculty);
        unityChoiceBox.setOnAction(this::getUnit);
        stageChoiceBox.setOnAction(this::getStage);
    }

    public void logOut() throws IOException {
        log.info("Logged out , out of time");
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

    public void timeDisplay() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    protected String getEmail() throws IOException, ParseException {
        log.info("Get user email");
        return Logic.getEmail(CurrentUser.getInstance().getUserName());
    }

    protected String getUsername() throws IOException, ParseException {
        log.info("Get current username");
        return Logic.getUserName(CurrentUser.getInstance().getUserName());
    }

    protected void getFaculty(ActionEvent actionEvent) {
        log.info("get current user faculty");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), facultyChoiceBox.getValue(), "get faculty prop"));
    }

    protected void getStage(ActionEvent actionEvent) {
        log.info("Get user stage");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), facultyChoiceBox.getValue() + "-" + stageChoiceBox.getValue(), "get faculty prop stage"));
    }

    protected void getUnit(ActionEvent actionEvent) {
        log.info("Get array of unit");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), facultyChoiceBox.getValue() + "-" + unityChoiceBox.getValue(), "get faculty prop unit"));
    }

    public void getFacultyStageData(Message message) {
        if (message.getContent() != null) {
            this.facultyLessons = message.getContent().split("-");
            Platform.runLater(() -> {
                if (stageCheckBox.isSelected()) {
                    filterGrid.getChildren().clear();
                    for (int i = 0; i < 5; i++) {
                        Label label = new Label();
                        if (i == 0) label.setText("Stage");
                        if (i == 1) label.setText("Teacher");
                        if (i == 2) label.setText("Lesson");
                        if (i == 3) label.setText("Unity");
                        if (i == 4) label.setText("Id");
                        filterGrid.add(label, i, 0);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                    log.info("get faculty stage");
                    int i = 0;
                    int j = 1;
                    for (String eachElement : facultyLessons) {
                        if (!Objects.equals(eachElement, "null")) {
                            Label label = new Label();
                            if (i % 5 == 1) label.setText(eachElement);
                            else if (i % 5 == 2) label.setText(eachElement);
                            else if (i % 5 == 3) label.setText(eachElement);
                            else if (i % 5 == 4) label.setText(eachElement);
                            else if (i % 5 == 0) label.setText(eachElement);
                            label.setAlignment(Pos.CENTER);
                            GridPane.setHalignment(label, HPos.CENTER);
                            filterGrid.add(label, i % 5, j);
                            i += 1;
                            if (i % 5 == 0) j += 1;
                        }
                    }
                }
            });
        } else {
            Platform.runLater(() -> {
                if (unityCheckBox.isSelected()) {
                    filterGrid.getChildren().clear();
                    for (int i = 0; i < 5; i++) {
                        Label label = new Label();
                        if (i == 0) label.setText("Stage");
                        if (i == 1) label.setText("Teacher");
                        if (i == 2) label.setText("Lesson");
                        if (i == 3) label.setText("Unity");
                        if (i == 4) label.setText("Id");
                        filterGrid.add(label, i, 0);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            });
        }
    }

    public void getFacultyUnitData(Message message) {
        if (message.getContent() != null) {
            this.facultyLessons = message.getContent().split("-");
            Platform.runLater(() -> {
                if (unityCheckBox.isSelected()) {
                    filterGrid.getChildren().clear();
                    for (int i = 0; i < 5; i++) {
                        Label label = new Label();
                        if (i == 0) label.setText("Stage");
                        if (i == 1) label.setText("Teacher");
                        if (i == 2) label.setText("Lesson");
                        if (i == 3) label.setText("Unity");
                        if (i == 4) label.setText("Id");
                        filterGrid.add(label, i, 0);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                    log.info("get faculty unit");
                    int i = 0;
                    int j = 1;
                    for (String eachElement : facultyLessons) {
                        if (!Objects.equals(eachElement, "null")) {
                            Label label = new Label();
                            if (i % 5 == 1) label.setText(eachElement);
                            else if (i % 5 == 2) label.setText(eachElement);
                            else if (i % 5 == 3) label.setText(eachElement);
                            else if (i % 5 == 4) label.setText(eachElement);
                            else if (i % 5 == 0) label.setText(eachElement);
                            label.setAlignment(Pos.CENTER);
                            GridPane.setHalignment(label, HPos.CENTER);

                            filterGrid.add(label, i % 5, j);
                            i += 1;
                            if (i % 5 == 0) j += 1;
                        }
                    }
                }
            });
        } else {
            Platform.runLater(() -> {
                if (unityCheckBox.isSelected()) {
                    filterGrid.getChildren().clear();
                    for (int i = 0; i < 5; i++) {
                        Label label = new Label();
                        if (i == 0) label.setText("Stage");
                        if (i == 1) label.setText("Teacher");
                        if (i == 2) label.setText("Lesson");
                        if (i == 3) label.setText("Unity");
                        if (i == 4) label.setText("Id");
                        filterGrid.add(label, i, 0);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            });
        }
    }

    public void getFacultyData(Message message) {
        if (message.getContent() != null) {
            this.facultyLessons = message.getContent().split("-");
            Platform.runLater(() -> {
                filterGrid.getChildren().clear();
                for (int i = 0; i < 5; i++) {
                    Label label = new Label();
                    if (i == 0) label.setText("Stage");
                    if (i == 1) label.setText("Teacher");
                    if (i == 2) label.setText("Lesson");
                    if (i == 3) label.setText("Unity");
                    if (i == 4) label.setText("Id");
                    filterGrid.add(label, i, 0);
                    GridPane.setHalignment(label, HPos.CENTER);
                }
                int i = 0;
                int j = 1;
                for (String eachElement : facultyLessons) {
                    if (!Objects.equals(eachElement, "null")) {
                        Label label = new Label();
                        if (i % 5 == 1) label.setText(eachElement);
                        else if (i % 5 == 2) label.setText(eachElement);
                        else if (i % 5 == 3) label.setText(eachElement);
                        else if (i % 5 == 4) label.setText(eachElement);
                        else if (i % 5 == 0) label.setText(eachElement);
                        label.setAlignment(Pos.CENTER);
                        filterGrid.add(label, i % 5, j);
                        GridPane.setHalignment(label, HPos.CENTER);
                        i += 1;
                        if (i % 5 == 0) j += 1;
                    }
                }
            });
        } else {
            Platform.runLater(() -> {
                if (unityCheckBox.isSelected()) {
                    filterGrid.getChildren().clear();
                    for (int i = 0; i < 5; i++) {
                        Label label = new Label();
                        if (i == 0) label.setText("Stage");
                        if (i == 1) label.setText("Teacher");
                        if (i == 2) label.setText("Lesson");
                        if (i == 3) label.setText("Unity");
                        if (i == 4) label.setText("Id");
                        filterGrid.add(label, i, 0);
                        GridPane.setHalignment(label, HPos.CENTER);
                    }
                }
            });
        }
    }

    public void setDegree(String string) {
        this.degree = string;
    }

    public void returnBtn() throws IOException, ParseException {
        log.info("Return Button");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "get degree lesson list"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "back to main page"));
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
        } else if (Objects.equals(degree, "manager") || Objects.equals(degree, "-")) {
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
}
