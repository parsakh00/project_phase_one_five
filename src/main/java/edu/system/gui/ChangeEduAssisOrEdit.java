package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ChangeEduAssisOrEdit {
    Stage stage;
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


    public void initialize(){

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
    protected void newEduClicked(){
        warningSec.setText(null);
        warningRel.setText(null);
        if (newEdu.getText()!=null){
            if (isChosen()){
                warningSec.setText("First relegate previous education assistant");
                newEdu.setText(null);
            }
            else{
                warningSec.setText(null);
                promoteUser();
                changeChosen();
            }
        }

    }
    @FXML
    protected void preEduClicked() throws IOException, ParseException {
        warningSec.setText(null);
        warningRel.setText(null);
        if (preEdu.getText() != null){
            if (isChosen()){
                warningRel.setText(null);
                relegateUser();
                changeChosen();
            }
            else{
                preEdu.setText(null);
                warningRel.setText("Didn't choose before");
            }

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
}
