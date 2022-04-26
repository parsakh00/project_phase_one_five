package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;

public class ApplyCertificateEmploymentDesk {
    public Button submitRequest;
    @FXML
    protected Label studentNumber;
    @FXML
    protected Label fieldName;
    @FXML
    protected Label universityName;
    @FXML
    protected Label dateName;
    @FXML
    protected Label warning;
    @FXML
    protected Label userName;

    Stage stage;


    


    public void initialize() throws IOException, ParseException {
        fieldName.setText(getUserFaculty());
        universityName.setText("Sharif University Of Technology");
        studentNumber.setText(getUserStudentNumber());
        userName.setText(getUsername());
        dateName.setText("00/00/00");


    }

    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
    }
    public void submissionClicked(ActionEvent actionEvent){
        warning.setText("Done");
    }
    protected String getUsername() throws IOException, ParseException {
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
    protected String getUserFaculty() throws IOException, ParseException {
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().getUserFaculty(massageStudentUndergraduateDesk);
    }
    protected String getUserStudentNumber() throws IOException, ParseException {
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().getUserStudentNumber(massageStudentUndergraduateDesk);
    }


}
