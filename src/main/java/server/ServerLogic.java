package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import currentUser.CurrentUser;
import message.Message;
import org.json.simple.parser.ParseException;
import passHash.PassHash;

import java.awt.event.MouseWheelEvent;
import java.awt.font.ShapeGraphicAttribute;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class ServerLogic {

    private static ServerLogic serverLogic;
    String userCondition;

    private ServerLogic() {
    }

    public static ServerLogic getInstance() {
        if (serverLogic == null)
            serverLogic = new ServerLogic();
        return serverLogic;
    }

    public void analyse(Message message) throws IOException, ParseException {
        if (message.getRequest().equals("log in")) {
            loggedIn(message);
        }
        if (message.getRequest().equals("set name")){
            nameSet(message);
        }
        if (message.getRequest().equals("get name")){
            getName(message);
        }
        if (message.getRequest().equals("get type")){
            getType(message);
        }
        if (message.getRequest().equals("get degree")){
            getDegree(message);
        }
        if (message.getRequest().equals("show data student")){
            getStudentData(message);
        }
        if (message.getRequest().equals("user data teacher")){
            getTeacherData(message);
        }
        if (message.getRequest().equals("edit email clicked teacher")){
            editTeacherEmail(message);
        }
        if (message.getRequest().equals("edit number clicked teacher")){
            editTeacherNumber(message);
        }
        if (message.getRequest().equals("edit email clicked student")){
            editStudentEmail(message);
        }
        if (message.getRequest().equals("edit number clicked student")){
            editStudentNumber(message);
        }
        if (message.getRequest().equals("set name and degree")){
            setDegreeUser(message);
        }


    }
    private void setDegreeUser(Message message) throws IOException, ParseException {
        String userName = message.getContent();
        String userDegree = Logic.getUserDegree(userName);
        String data = userName + "-" + userDegree;
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),data,"set name and degree"));
            }
        }
    }
    private void getName(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),Logic.getUserName(message.getContent()), "get name"));
            }
        }
    }
    private void editStudentEmail(Message message){
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.emailEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),"","student email edited"));
            }
        }
    }
    private void editStudentNumber(Message message){
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.passEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),"","student number edited"));
            }
        }
    }
    private void editTeacherNumber(Message message){
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.passEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),"","teacher number edited"));
            }
        }
    }
    private void editTeacherEmail(Message message) throws IOException, ParseException {
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.emailEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),"","teacher email edited"));
            }
        }
    }
    private void loggedIn(Message message) throws IOException, ParseException {
        String[] userPass = message.getContent().split("-");
        String username2 = userPass[0];
        String pass = userPass[1];
        String realCaptchaId = userPass[2];
        String insertedCaptcha = userPass[3];
        if (Objects.equals(realCaptchaId, insertedCaptcha)) {
            MassageInNetwork massage = new MassageInNetwork(username2, pass, null, null, null, null, null, null, null, null);
            CurrentUser.getInstance().setUser(username2);
            if (Controller.getInstance().login(massage)) {
                MassageInNetwork massageStudentUndergraduateDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
                String userCondition = Controller.getInstance().userCondition(massageStudentUndergraduateDesk);
                if (!Objects.equals(userCondition, "withdrawal from education")) {
                    for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
                        if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                            clientHandler.setUsername(username2);
                            clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "true", "log in"));
                        }
                    }
                } else {
                    //wrongUserPass.setText("Not allow");
                    for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
                        if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                            clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "Not allow", "log in"));
                        }
                    }
                }
            } else {
                //wrongUserPass.setText("wrong username or password");
                for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
                    if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                        clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "wrong username or password", "log in"));
                    }
                }
            }
        } else {
            for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
                if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                    clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "wrong captcha", "log in"));
                }
            }
        }
    }
    private void getType(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),Logic.getType(message.getContent()),"get type"));
            }
        }
    }
    private void getTeacherData(Message message) throws IOException, ParseException {
        String username = message.getContent();
        String email = Logic.getEmail(username);
        String faculty = Logic.getFaculty(username);
        String phoneNumber = Logic.getUserPhoneNumber(username);
        String degree = Logic.getDegree(username);
        String nationalId = Logic.getNationalId(username);
        String userId = Logic.getId(username);
        String roomNo = Logic.getRoomNo(username);
        String userData = username + "-" + email + "-" +faculty + "-" +phoneNumber + "-" +degree + "-" +
                nationalId + "-" +userId + "-" +roomNo;
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), userData,"show data teacher"));
            }
        }
    }
    private void getStudentData(Message message) throws IOException, ParseException {
        String username = message.getContent();
        String id = Logic.getId(username);
        String nationalId = Logic.getNationalId(username);
        String email = Logic.getEmail(username);
        String condition = Logic.getCondition(username);
        String supervisor = Logic.getSupervisor(username);
        String faculty = Logic.getFaculty(username);
        String phoneNumber = Logic.getUserPhoneNumber(username);
        String enteranceYear = Logic.getEnteringYear(username);
        String userType = Logic.getType(username);
        String score = Logic.getUserScore(username);
        String userData = username +"-"+ id +"-"+ nationalId +"-"+ email +"-"+ condition +"-"+ supervisor +"-"+ faculty +"-"+
                phoneNumber +"-"+ enteranceYear +"-"+ userType +"-"+ score;
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), userData,"show data student"));
            }
        }
    }
    private void getDegree(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),Logic.getDegree(message.getContent()),"get degree"));
            }
        }
    }
    private void nameSet(Message message){
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.setUsername(message.getContent());
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(),"name set","set name"));
            }
        }
    }

}
