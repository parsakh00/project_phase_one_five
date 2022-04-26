package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageLogin;
import edu.system.logic.MassageUserDesk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class EduMinorRequests {

    Stage stage;

    public TextField choiceUser;
    public Label source;

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
        MassageLogin GetUsers = new MassageLogin(CurrentUser.getInstance().getUser(), getUserFaculty());
        userNames =  Controller.getInstance().listOfUserMinor(GetUsers);
    }

    @FXML
    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "education assistant")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/educationalAssistantDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
    }

    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    protected String getUserFaculty() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userFaculty(massageUserDegree);
    }

    protected void reject() throws IOException, ParseException {
        MassageLogin GetUsers = new MassageLogin(choiceUser.getText(), getUserFaculty());
        Controller.getInstance().rejectMinorRequest(GetUsers);
    }
    protected void acception() throws IOException, ParseException {
        MassageLogin GetUsers = new MassageLogin(choiceUser.getText(), getUserFaculty());
        Controller.getInstance().acceptMinorRequest(GetUsers);
    }
    public void rejection(ActionEvent actionEvent) throws IOException, ParseException {
        reject();
    }
    public void accept(ActionEvent actionEvent) throws IOException, ParseException {
        acception();

    }





}
