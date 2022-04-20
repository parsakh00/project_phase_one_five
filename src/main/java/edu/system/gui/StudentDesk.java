package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageStudentDesk;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class StudentDesk {

    public AnchorPane studentView;
    @FXML
    public Label email;
    Stage stage;

    String username;


    public void initialize() throws IOException, ParseException {
        email.setText(getEmail());

    }

    protected String getEmail() throws IOException, ParseException {
        MassageStudentDesk massageStudentDesk = new MassageStudentDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().studentDeskEmail(massageStudentDesk);

    }







}
