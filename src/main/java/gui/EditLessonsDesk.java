package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.Message;
import org.json.simple.parser.ParseException;
import server.Controller;
import server.MassageInNetwork;


import java.io.IOException;
import java.util.Objects;

public class EditLessonsDesk {
    Stage stage;

    @FXML
    protected Button backBtn;
    @FXML
    protected TextField removeLesson;
    @FXML
    protected Label removeWarning;
    @FXML
    protected Button removeBtn;
    @FXML
    protected TextField editLesson;
    @FXML
    protected TextField editTime;
    @FXML
    protected TextField editTeacher;
    @FXML
    protected Label editWarning;
    @FXML
    protected Button editBtn;
    @FXML
    protected Button addBtn;
    @FXML
    protected TextField addLesson;
    @FXML
    protected TextField addUnity;
    @FXML
    protected TextField addStage;
    @FXML
    protected TextField addTime;
    @FXML
    protected TextField addId;
    @FXML
    protected TextField addTeacher;
    @FXML
    protected TextField addIsPresent;
    @FXML
    protected Label addWarning;

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void initialize() {
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
        removeLesson.setText(null);
        editTeacher.setText(null);
        editLesson.setText(null);
        editTime.setText(null);
        addIsPresent.setText(null);
        addLesson.setText(null);
        addId.setText(null);
        addStage.setText(null);
        addTeacher.setText(null);
        addTime.setText(null);
        addUnity.setText(null);
    }

    public void logOut() throws IOException {
        stage = ((Stage) (removeWarning).getScene().getWindow());
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

    public void backBtnClicked(ActionEvent actionEvent) throws IOException {
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "back to main page"));
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setTeachersListDesk(loader, stage);
    }

    @FXML
    protected void editBtnClicked() throws IOException, ParseException {
        if (editLesson.getText() != null) {
            if (editTeacher.getText() != null || editTime != null) {
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), editLesson.getText() + "-" + currentUserFaculty()
                        + "-" + editTime.getText() + "-" + editTeacher.getText()
                        , "edit btn clicked edit lesson desk"));
                editWarning.setText("Done");
            } else {
                editWarning.setText("At least fill time or teacher field");
            }
        } else {
            editWarning.setText("First fill lesson field");
        }
    }

    public void addBtnClicked() throws IOException, ParseException {
        if (addUnity.getText() != null && addId.getText() != null && addTime.getText() != null && addIsPresent.getText() != null && addTeacher.getText() != null && addStage.getText() != null) {
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(),
                    addLesson.getText() + "-" + currentUserFaculty() + "-" + addTime.getText() + "-" + addTeacher.getText() + "-" + addUnity.getText() + "-" + addStage.getText() + "-" + addId.getText() + "-" + presentValue(addIsPresent.getText())
                    , "add btn clicked edit lesson desk"));
            addWarning.setText("Done");
        } else {
            addWarning.setText("First fill all parts.");
        }
    }

    @FXML
    protected void removal() throws IOException, ParseException {
        if (removeLesson.getText() != null) {
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), removeLesson.getText() + "-" + currentUserFaculty()
                    , "removal edit lesson desk"));
            removeWarning.setText("Done");
        } else {
            removeWarning.setText("First set lesson");
        }
    }

    protected Boolean presentValue(String isPresent) {
        return Objects.equals(isPresent, "true");
    }

    protected String currentUserFaculty() throws IOException, ParseException {
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userFaculty(massageStudentMasterDesk);
    }
}
