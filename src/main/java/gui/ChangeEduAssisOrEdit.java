package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.PauseTransition;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import server.Controller;
import server.MassageInNetwork;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class ChangeEduAssisOrEdit {
    @FXML
    protected Button remove;
    Stage stage;
    @FXML
    protected ImageView image;
    @FXML
    protected Button saveImage;
    @FXML
    protected Button drawBack;
    @FXML
    protected TextField preEdu;
    @FXML
    protected TextField newEdu;
    @FXML
    protected Button okPreEduChange;
    @FXML
    protected Button okNewEduChange;
    @FXML
    protected Label warningRel;
    @FXML
    protected Label warningSec;
    @FXML
    protected TextField teacherForRemove;
    @FXML
    protected Label warningDel;
    @FXML
    protected Button okeyBtn;
    @FXML
    protected TextField editEmail;
    @FXML
    protected TextField editPassword;
    @FXML
    protected TextField editTeacher;
    @FXML
    protected Label warningEdit;
    @FXML
    protected Label editEmailWarning;
    @FXML
    protected Button addBtn;
    @FXML
    protected TextField userAdd;
    @FXML
    protected TextField passAdd;
    @FXML
    protected TextField phoneAdd;
    @FXML
    protected TextField roomAdd;
    @FXML
    protected TextField emailAdd;
    @FXML
    protected Label addWarning;
    @FXML
    protected Button pickker;
    @FXML
    protected TextField masterDegreeAdd;
    @FXML
    protected TextField idAdd;

    static Logger log = LogManager.getLogger(ClientMain.class);

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    public void initialize() {
        log.info("Open change education assistant or edit");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened!");
                throw new RuntimeException(e);
            }
        });
        teacherForRemove.setText(null);
        preEdu.setText(null);
        newEdu.setText(null);
        editEmail.setText(null);
        editPassword.setText(null);
        editTeacher.setText(null);
        userAdd.setText(null);
        passAdd.setText(null);
        phoneAdd.setText(null);
        roomAdd.setText(null);
        emailAdd.setText(null);
        masterDegreeAdd.setText(null);
        idAdd.setText(null);
    }

    public void logOut() throws IOException {
        log.info("Logged out out of time");
        stage = ((Stage) (addWarning).getScene().getWindow());
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

    public void returnBtn(ActionEvent actionEvent) throws IOException {
        log.info("Return back");
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
    protected void editTeacher() throws IOException, ParseException {
        log.info("Edit teacher info");
        if (editTeacher != null) {
            if (editPassword.getText() != null) {
                log.info("Edited password");
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), editTeacher.getText() + "-" + editPassword.getText()
                        , "edit password in edu assistant"));
                warningEdit.setText("password changed");
                editPassword.setText(null);
            }
            if (editEmail.getText() != null) {
                log.info("Email edited");
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), editTeacher.getText() + "-" + editEmail.getText()
                        , "edit email in edu assistant"));
                editEmailWarning.setText("email changed");
                editEmail.setText(null);

            }
        } else {
            warningEdit.setText("First specify teacher");
            editEmail.setText(null);
            editPassword.setText(null);
        }
    }

    @FXML
    protected void addBtnClicked() throws IOException, ParseException {
        log.info("Add button clicked");
        if (userAdd.getText() != null && passAdd.getText() != null && emailAdd.getText() != null && roomAdd.getText() != null && phoneAdd.getText() != null) {
            log.info("Add user");
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(),
                    userAdd.getText() + "-" + passAdd.getText() + "-" + emailAdd.getText() + "-" + phoneAdd.getText() + "-" + roomAdd.getText()
                            + "-" + currentUserFaculty() + "-" + masterDegreeAdd.getText() + "-" + idAdd.getId(), "add new teacher"));
        } else {
            addWarning.setText("Must fill out all parts");
        }
    }

    @FXML
    protected void pickImage() throws IOException {
        log.info("Pick image");
        if (userAdd.getText() != null) {
            stage = new Stage();
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("choose an image");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png"));
            File selectedFile = fileChooser.showOpenDialog(stage);
            if (selectedFile != null) {
                Image originalPhoto = new Image(selectedFile.toURI().toString());
                image.setImage(originalPhoto);
            }
            Image imageToBeSaved = image.getImage();
            File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\system\\images\\" + userAdd.getText() + ".png");
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(imageToBeSaved, null), "png", file);
            } catch (IOException e) {
                log.error("exception happened");
                e.printStackTrace();
            }
        } else {
            addWarning.setText("First fill username");
        }


    }

    @FXML
    protected void newEduClicked() throws IOException, ParseException {
        log.info("New education assistant submit");
        warningSec.setText(null);
        warningRel.setText(null);
        if (newEdu.getText() != null) {
            if (isChosen()) {
                warningSec.setText("First relegate previous education assistant");
                newEdu.setText(null);
            } else {
                if (Objects.equals(currentUserFaculty(), SelectedUserFacultyForNewEdu())) {
                    warningSec.setText(null);
                    log.info("Promote user");
                    log.info("change is chose education assistant boolean");
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), newEdu.getText(), "promote user"));
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "change chosen"));
                    warningSec.setText("Done");
                    newEdu.setText(null);
                } else {
                    newEdu.setText(null);
                    warningSec.setText("Illegal");
                }
            }
        }
    }

    @FXML
    protected void preEduClicked() throws IOException, ParseException {
        log.info("previous education assistant confirmation");
        warningSec.setText(null);
        warningRel.setText(null);
        if (preEdu.getText() != null) {
            if (isChosen()) {
                if (Objects.equals(currentUserFaculty(), SelectedUserFacultyForPreEdu())) {
                    warningRel.setText(null);
                    log.info("Relegate user");
                    log.info("change is chose education assistant boolean");
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), preEdu.getText(), "relegate user"));
                    Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "change chosen"));
                    warningRel.setText("Done");
                    preEdu.setText(null);
                } else {
                    preEdu.setText(null);
                    warningRel.setText("Illegal");
                }

            } else {
                preEdu.setText(null);
                warningRel.setText("Didn't choose before");
            }

        }
    }

    @FXML
    protected void removeClicked() throws IOException, ParseException {
        log.info("Removal");
        if (teacherForRemove.getText() != null) {
            if (Objects.equals(currentUserFaculty(), SelectedUserFacultyForDelete())) {
                log.info("Deleting course of teacher");
                log.info("Delete teacher");
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), teacherForRemove.getText() + "-" + currentUserFaculty(), "delete course teacher"));
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), teacherForRemove.getText(), "delete teacher"));
                warningDel.setText("Done");
                teacherForRemove.setText(null);
            } else {
                warningDel.setText("Illegal removal");
            }
        } else {
            warningDel.setText("First select teacher");
        }
    }

    private Boolean isChosen() {
        log.info("Check is education assistant chose or not");
        MassageInNetwork isChosenBefore = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().isChosen(isChosenBefore);
    }

    protected String currentUserFaculty() throws IOException, ParseException {
        log.info("Find current user faculty");
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userFaculty(massageStudentMasterDesk);
    }

    private String SelectedUserFacultyForDelete() throws IOException, ParseException {
        log.info("Select user faculty for deleting");
        MassageInNetwork selectedFaculty = new MassageInNetwork(teacherForRemove.getText(), null, null);
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }

    private String SelectedUserFacultyForNewEdu() throws IOException, ParseException {
        log.info("new educational assistant faculty");
        MassageInNetwork selectedFaculty = new MassageInNetwork(newEdu.getText(), null, null);
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }

    private String SelectedUserFacultyForPreEdu() throws IOException, ParseException {
        log.info("previous educational assistant faculty");
        MassageInNetwork selectedFaculty = new MassageInNetwork(preEdu.getText(), null, null);
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }
}