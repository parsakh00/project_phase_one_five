package edu.system;

import gui.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import message.Message;

import java.io.IOException;

public class ClientLogic {

    private static ClientLogic clientLogic;

    private Stage stage;

    private Login login;

    private StudentUndergraduateDesk studentUndergraduateDesk;
    private StudentMasterDesk studentMasterDesk;
    private StudentPhdDesk studentPhdDesk;
    private TeacherDesk teacherDesk;
    private EducationalAssistantDesk educationalAssistantDesk;
    private StudentProfile studentProfile;
    private TeacherProfile teacherProfile;
    private LogOutDesk logOutDesk;

    private ClientLogic() {

    }

    public static ClientLogic getInstance() {
        if (ClientLogic.clientLogic == null)
            ClientLogic.clientLogic = new ClientLogic();
        return ClientLogic.clientLogic;
    }

    public void analyse(Message message) {
        if (message.getRequest().equals("authToken")) {
            authToken(message);
        }
        if (message.getRequest().equals("log in")) {
            logInProcess(message.getContent());
        }
        if (message.getRequest().equals("get type")) {
            getUserType(message.getContent());
        }
        if (message.getRequest().equals("get degree")) {
            getUserDegree(message.getContent());
        }
        if (message.getRequest().equals("show data student")) {
            showStudentData(message);
        }
        if (message.getRequest().equals("show data teacher")) {
            showTeacherData(message);
        }
        if (message.getRequest().equals("set name and degree")) {
            showStudentDegreeName(message);
        }
        if (message.getRequest().equals("get name")) {
            showUserName(message);
        }

    }

    private void showStudentDegreeName(Message message){
        studentProfile.setUserDegreeAndName(message.getContent());
    }
    private void showUserName(Message message){
        teacherProfile.setUserName(message.getContent());
    }
    private void authToken(Message message) {
        Client.getClient().setAuthToken(message.getContent());
    }


    private void showTeacherData(Message message){
        teacherProfile.showDataList(message.getContent());
    }

    private void showStudentData(Message message) {
        studentProfile.showDataList(message.getContent());
    }

    private void getUserType(String typeUser) {
        login.setType(typeUser);
    }

    private void logInProcess(String content) {
        login.mainLogIn(content);
    }

    private void getUserDegree(String content) {
        login.setDegree(content);
    }

    public void setLogin(FXMLLoader fxmlLoader, Stage stage) throws IOException {
        System.out.println("log in desk");
        this.stage = stage;
        this.login = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("log in is null");
    }

    public void setStudentUndergraduateDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("undergraduate desk");
        this.stage = stage;
        this.studentUndergraduateDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("undergraduate is null");
    }

    public void setStudentPhdDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("phd desk");
        this.stage = stage;
        this.studentPhdDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("phd is null");
    }

    public void setStudentMasterDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("master desk");
        this.stage = stage;
        this.studentMasterDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("master is null");
    }

    public void setTeacherDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("teacher desk");
        this.stage = stage;
        this.teacherDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("teacher desk is null");
    }

    public void setEducationalAssistantDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("edu assistant desk");
        this.stage = stage;
        this.educationalAssistantDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("edu assistant desk is null");
    }

    public void setStudentProfile(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("student profile");
        this.stage = stage;
        this.studentProfile = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("student profile is null");
    }

    public void setTeacherProfile(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("teacher profile");
        this.stage = stage;
        this.teacherProfile = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("teacher profile is null");
    }
    public void setLogOutDesk(FXMLLoader fxmlLoader, Stage stage) {
        this.stage = stage;
        this.logOutDesk = fxmlLoader.getController();
    }
}
