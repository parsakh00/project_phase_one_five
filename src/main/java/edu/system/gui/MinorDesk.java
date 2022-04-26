package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Objects;

public class MinorDesk {

    public TextField facultyName;
    public Label minorWarning;
    public Label warningResult;
    Stage stage;

    public void initialize(){

    }




    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    protected String getFacultyUser() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userFaculty(massageUserDegree);
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
    }
    protected String getUsername() throws IOException, ParseException {
        MassageUserDesk massageStudentPhdDesk = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDeskUserName(massageStudentPhdDesk);
    }

    public void sendRequestClicked() throws IOException, ParseException {
        if (facultyName.getText()!= null){
            sendMinorRequest();
            minorWarning.setText("Done");
        }
        else {
            minorWarning.setText("Fill faculty name first");
        }
    }

    public void sendMinorRequest() throws IOException, ParseException {
        MassageFacultyUnit sendMinor = new MassageFacultyUnit(getUsername(),getFacultyUser(),facultyName.getText());
        Controller.getInstance().addMinorRequest(sendMinor);
    }

    public void showResultClicked() throws IOException, ParseException {
        showResult();
        if (Objects.equals((String) showResult(), "022")) warningResult.setText("Not Checked");
        else if (Objects.equals((String) showResult(), "012")) warningResult.setText("Origin faculty approved");
        else if (Objects.equals((String) showResult(), "021")) warningResult.setText("Destination university approved");
        else if (Objects.equals((String) showResult(), "002")||Objects.equals((String) showResult(), "001")||Objects.equals((String) showResult(), "020")||Objects.equals((String) showResult(), "010")) warningResult.setText("Rejected your request!");
        else if (Objects.equals((String) showResult(), "011")) warningResult.setText("Your request approved!");
    }
    public String showResult() throws IOException, ParseException {
        MassageUserDesk howManyFacultyMinor = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().facultyForMinors(howManyFacultyMinor);
    }

}
