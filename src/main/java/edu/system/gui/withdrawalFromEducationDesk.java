package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Objects;

public class withdrawalFromEducationDesk {
    public Label respond;
    @FXML
    protected Label request;
    
    Stage stage;


    public void initialize(){
        
    }

    public void requestClicked() throws IOException, ParseException {
        request.setText("Sent!");
        addRequest();

    }

    public void showResult() throws IOException, ParseException {
        if (Objects.equals(result(), "2")) respond.setText("Not respond yet!");
        else if(Objects.equals(result(), "1")) respond.setText("Accepted with your request!");
        else if (Objects.equals(result(), "0")) respond.setText("Rejected your request!");
    }


    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }


    public void backBtnClicked(javafx.event.ActionEvent actionEvent) throws IOException, ParseException {
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

    protected void addRequest() throws IOException, ParseException {
        MassageUserDesk massageStudentMasterDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        Controller.getInstance().withdrawRequest(massageStudentMasterDesk);
    }


    protected String result() throws IOException, ParseException {
        MassageUserDesk massageResult = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().withdrawResult(massageResult);
    }


}
