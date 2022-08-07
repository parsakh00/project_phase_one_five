package gui;

import edu.system.ClientMain;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
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

public class LogOutDesk {
    public Pane forSource;
    Stage stage;

    public Label warningChange;

    public TextField newPassword;

    public TextField oldPassword;

    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize(){
        log.info("Logged out page");
        newPassword.setText(null);
        oldPassword.setText(null);

    }
    public void changeButton(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Change password");
        if (changePassword()){
            stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/login-view.fxml"));
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
        MassageInNetwork massageEditPassword = new MassageInNetwork(getUsername(), newPassword.getText(),null,null,null,null, oldPassword.getText(),null,null,null);
        return Controller.getInstance().editPassLogOut(massageEditPassword);
    }
    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user , username");
        MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        return Controller.getInstance().userDeskUserName(massageStudentUndergraduateDesk);
    }
}
