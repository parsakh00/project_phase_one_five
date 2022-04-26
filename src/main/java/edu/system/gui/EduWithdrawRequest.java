package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;
import java.util.Stack;

public class EduWithdrawRequest {
    public TextField choiceUser;
    public Label source;
    Stage stage;
    @FXML
    protected ChoiceBox<String> chooseUser;

    protected String[] userNames;


    public void initialize() throws IOException, ParseException {
    getUsers();
    chooseUser.getItems().addAll(userNames);
    chooseUser.setOnAction(this :: getUser);
    }

    protected void getUser(ActionEvent actionEvent){
        choiceUser.setText(chooseUser.getValue());
    }


    protected void getUsers() throws IOException, ParseException {

        MassageUserDesk massageGetUsers = new MassageUserDesk(CurrentUser.getInstance().getUser());
        userNames =  Controller.getInstance().listOfUser(massageGetUsers);


    }
    public void returnBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        stage = ((Stage) (source).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();

    }
    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    protected void reject() throws IOException, ParseException {
        CurrentUser.getInstance().setUser(choiceUser.getText());
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        Controller.getInstance().rejection(massageStudentMasterDesk);
    }


    protected void acception() throws IOException, ParseException {
        CurrentUser.getInstance().setUser(choiceUser.getText());
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        Controller.getInstance().accept(massageStudentMasterDesk);
    }
    protected void condition() throws IOException, ParseException {
        CurrentUser.getInstance().setUser(choiceUser.getText());
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        Controller.getInstance().changeCondition(massageStudentMasterDesk);
    }



    public void rejection(ActionEvent actionEvent) throws IOException, ParseException {
        reject();
    }


    public void accept(ActionEvent actionEvent) throws IOException, ParseException {
        condition();
        acception();
    }
}
