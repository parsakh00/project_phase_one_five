package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
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
import org.json.simple.parser.ParseException;
import javafx.embed.swing.SwingNode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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


    public void initialize(){
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
    }

    public void returnBtn(ActionEvent actionEvent) throws IOException {
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
        if (userAdd.getText() != null && passAdd.getText() != null && emailAdd.getText() != null && roomAdd.getText() != null && phoneAdd.getText() != null){
            addUser();

        }
        else{
            addWarning.setText("Must fill out all parts");
        }
    }
    @FXML
    protected void pickImage() throws IOException {
        stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("choose an image");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files",  "*.png"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            Image originalPhoto = new Image(selectedFile.toURI().toString());
            image.setImage(originalPhoto);
        }
        Image imageToBeSaved = image.getImage();
        File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\system\\images\\" + userAdd.getText() + ".png");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(imageToBeSaved,null),"png",file);
        }catch (IOException e){
            e.printStackTrace();
        }


    }
    protected void addUser() throws IOException, ParseException {
        MassageSignUp massageSignUp = new MassageSignUp(userAdd.getText(),passAdd.getText(),
                emailAdd.getText(),phoneAdd.getText(),roomAdd.getText(), currentUserFaculty());
        Controller.getInstance().addUser(massageSignUp);
    }
    protected void editPassword(){
        MassageLogin massageEditPassword = new MassageLogin(editTeacher.getText(), editPassword.getText());
        Controller.getInstance().editPass(massageEditPassword);
    }
    protected void editEmail(){
        MassageLogin massageEditEmail = new MassageLogin(editTeacher.getText(), editEmail.getText());
        Controller.getInstance().editEmail(massageEditEmail);
    }
    @FXML
    protected void newEduClicked() throws IOException, ParseException {
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
        MassageUserDesk isChosenBefore = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().isChosen(isChosenBefore);

    }
    private void promoteUser(){
        CurrentFaculty.getInstance().setFaculty(newEdu.getText());
        MassageUserDesk promotion = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        Controller.getInstance().promotion(promotion);

    }
    private void relegateUser(){
        CurrentFaculty.getInstance().setFaculty(preEdu.getText());
        MassageUserDesk relegation = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        Controller.getInstance().relegation(relegation);

    }
    private void changeChosen(){
        MassageUserDesk changeChosen = new MassageUserDesk(CurrentUser.getInstance().getUser());
        Controller.getInstance().valueChanger(changeChosen);
    }
    private void deleteCourseTeacher() throws IOException, ParseException {
        MassageLogin massage = new MassageLogin(teacherForRemove.getText(), currentUserFaculty());
        Controller.getInstance().deletingCourse(massage);

    }
    private void deleteTeacher(){
        CurrentFaculty.getInstance().setFaculty(teacherForRemove.getText());
        MassageUserDesk deleting = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        Controller.getInstance().deletingTeacher(deleting);
    }
    protected String currentUserFaculty() throws IOException, ParseException {
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userFaculty(massageStudentMasterDesk);
    }
    private String SelectedUserFacultyForDelete() throws IOException, ParseException {
        CurrentFaculty.getInstance().setFaculty(teacherForRemove.getText());
        MassageUserDesk selectedFaculty = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }
    private String SelectedUserFacultyForNewEdu() throws IOException, ParseException {
        CurrentFaculty.getInstance().setFaculty(newEdu.getText());
        MassageUserDesk selectedFaculty = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }
    private String SelectedUserFacultyForPreEdu() throws IOException, ParseException {
        CurrentFaculty.getInstance().setFaculty(preEdu.getText());
        MassageUserDesk selectedFaculty = new MassageUserDesk(CurrentFaculty.getInstance().getFaculty());
        return Controller.getInstance().selectedUserFaculty(selectedFaculty);
    }

}
