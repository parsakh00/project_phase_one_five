package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageLogin;
import edu.system.logic.MassageUserDesk;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class LogOutDesk {
    public Pane forSource;
    Stage stage;

    public Label warningChange;

    public TextField newPassword;

    public TextField oldPassword;

    static Logger log = LogManager.getLogger(HelloApplication.class);

    public void initialize(){
        log.info("Logged out page");
        newPassword.setText(null);
        oldPassword.setText(null);

    }
    public void changeButton(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change password");
        if (changePassword()){
            stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else {
            warningChange.setText("Try again");
            newPassword.setText(null);
            oldPassword.setText(null);
        }

    }
    protected Boolean changePassword() throws IOException, ParseException {
        log.info("Change password boolean");
        MassageLogin massageEditPassword = new MassageLogin(getUsername(), newPassword.getText(), oldPassword.getText());
        return Controller.getInstance().editPassLogOut(massageEditPassword);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user , username");
        MassageUserDesk massageStudentUndergraduateDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
}
