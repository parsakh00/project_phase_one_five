package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.currentUser.CurrentUser;
import edu.system.logic.*;
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
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

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

    static Logger log = LogManager.getLogger(HelloApplication.class);

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    public void initialize(){
        log.info("Open change education assistant or edit");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened!");
                throw new RuntimeException(e);
            }
        } );
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
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
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
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/teacherLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    @FXML
    protected void editTeacher() throws IOException, ParseException {
        log.info("Edit teacher info");
        if (editTeacher != null){
            if (editPassword.getText() != null){
                editPassword();
                warningEdit.setText("password changed");
                editPassword.setText(null);
            }
            if (editEmail.getText() != null){
                editEmail();
                editEmailWarning.setText("email changed");
                editEmail.setText(null);

            }
        }
        else {
            warningEdit.setText("First specify teacher");
            editEmail.setText(null);
            editPassword.setText(null);
        }
    }
    @FXML
    protected void addBtnClicked() throws IOException, ParseException {
        log.info("Add button clicked");
        if (userAdd.getText() != null && passAdd.getText() != null && emailAdd.getText() != null && roomAdd.getText() != null && phoneAdd.getText() != null){
            addUser();

        }
        else{
            addWarning.setText("Must fill out all parts");
        }
    }
    @FXML
    protected void pickImage() throws IOException {
        log.info("Pick image");
        if (userAdd.getText()!=null) {
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
        }
        else {
            addWarning.setText("First fill username");
        }


    }
    protected void addUser() throws IOException, ParseException {
        log.info("Add user");
        MassageLogin massageSignUp = new MassageLogin(userAdd.getText(),passAdd.getText(),
                emailAdd.getText(),phoneAdd.getText(),roomAdd.getText(), currentUserFaculty()
                ,masterDegreeAdd.getText(),idAdd.getId());
        Controller.getInstance().addUser(massageSignUp);
    }
    protected void editPassword(){
        log.info("Edited password");
        MassageLogin massageEditPassword = new MassageLogin(editTeacher.getText(), editPassword.getText(), null,null,null,null,null,null,null,null);
        Controller.getInstance().editPass(massageEditPassword);
    }
    protected void editEmail(){
        log.info("Email edited");
        MassageLogin massageEditEmail = new MassageLogin(editTeacher.getText(), editEmail.getText(), null,null,null,null,null,null,null,null);
        Controller.getInstance().editEmail(massageEditEmail);
    }
    @FXML
    protected void newEduClicked() throws IOException, ParseException {
        log.info("New education assistant submit");
        warningSec.setText(null);
        warningRel.setText(null);
        if (newEdu.getText()!=null){
            if (isChosen()){
                warningSec.setText("First relegate previous education assistant");
                newEdu.setText(null);
            }
            else{
                if (Objects.equals(currentUserFaculty(), SelectedUserFacultyForNewEdu())) {
                    warningSec.setText(null);
                    promoteUser();
                    changeChosen();
                    warningSec.setText("Done");
                    newEdu.setText(null);
                }
                else {
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
        if (preEdu.getText() != null){
            if (isChosen()){
                if (Objects.equals(currentUserFaculty(), SelectedUserFacultyForPreEdu())) {
                    warningRel.setText(null);
                    relegateUser();
                    changeChosen();
                    warningRel.setText("Done");
                    preEdu.setText(null);
                }
                else{
                    preEdu.setText(null);
                    warningRel.setText("Illegal");
                }

            }
            else{
                preEdu.setText(null);
                warningRel.setText("Didn't choose before");
            }

        }
    }
    @FXML
    protected void removeClicked() throws IOException, ParseException {
        log.info("Removal");
        if (teacherForRemove.getText() != null){
            if (Objects.equals(currentUserFaculty(), SelectedUserFacultyForDelete())) {
                deleteCourseTeacher();
                deleteTeacher();
                warningDel.setText("Done");
                teacherForRemove.setText(null);
            }
            else {
                warningDel.setText("Illegal removal");
            }
        }
        else{
            warningDel.setText("First select teacher");
        }
    }
    private Boolean isChosen(){
        log.info("Check is education assistant chose or not");
        MassageLogin isChosenBefore = new MassageLogin(CurrentUser.getInstance().getUser(),null,null);
        return Controller.getInstance().isChosen(isChosenBefore);

    }
    private void promoteUser(){
        log.info("Promote user");
        CurrentFaculty.getInstance().setFaculty(newEdu.getText());
        MassageLogin promotion = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        Controller.getInstance().promotion(promotion);

    }
    private void relegateUser(){
        log.info("Relegate user");
        CurrentFaculty.getInstance().setFaculty(preEdu.getText());
        MassageLogin relegation = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        Controller.getInstance().relegation(relegation);

    }
    private void changeChosen(){
        log.info("change is chose education assistant boolean");
        MassageLogin changeChosen = new MassageLogin(CurrentUser.getInstance().getUser(),null,null);
        Controller.getInstance().valueChanger(changeChosen);
    }
    private void deleteCourseTeacher() throws IOException, ParseException {
        log.info("Deleting course of teacher");
        MassageLogin massage = new MassageLogin(teacherForRemove.getText(), currentUserFaculty(), null,null,null,null,null,null,null,null);
        Controller.getInstance().deletingCourse(massage);

    }
    private void deleteTeacher(){
        log.info("Delete teacher");
        CurrentFaculty.getInstance().setFaculty(teacherForRemove.getText());
        MassageLogin deleting = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        Controller.getInstance().deletingTeacher(deleting);
    }
    protected String currentUserFaculty() throws IOException, ParseException {
        log.info("Find current user faculty");
        MassageLogin massageStudentMasterDesk = new MassageLogin(CurrentUser.getInstance().getUser(),null,null);
        return Controller.getInstance().userFaculty(massageStudentMasterDesk);
    }
    private String SelectedUserFacultyForDelete() throws IOException, ParseException {
        log.info("Select user faculty for deleting");
        CurrentFaculty.getInstance().setFaculty(teacherForRemove.getText());
        MassageLogin selectedFaculty = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }
    private String SelectedUserFacultyForNewEdu() throws IOException, ParseException {
        log.info("new educational assistant faculty");
        CurrentFaculty.getInstance().setFaculty(newEdu.getText());
        MassageLogin selectedFaculty = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }
    private String SelectedUserFacultyForPreEdu() throws IOException, ParseException {
        log.info("previous educational assistant faculty");
        CurrentFaculty.getInstance().setFaculty(preEdu.getText());
        MassageLogin selectedFaculty = new MassageLogin(CurrentFaculty.getInstance().getFaculty(),null,null);
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }

}
