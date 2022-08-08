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
        if (message.getRequest().equals("set name")) {
            nameSet(message);
        }
        if (message.getRequest().equals("get name")) {
            getName(message);
        }
        if (message.getRequest().equals("get type")) {
            getType(message);
        }
        if (message.getRequest().equals("get degree")) {
            getDegree(message);
        }
        if (message.getRequest().equals("get degree lesson list")) {
            getDegreeLessonList(message);
        }
        if (message.getRequest().equals("get degree teacher list")) {
            getDegreeTeacherList(message);
        }
        if (message.getRequest().equals("show data student")) {
            getStudentData(message);
        }
        if (message.getRequest().equals("user data teacher")) {
            getTeacherData(message);
        }
        if (message.getRequest().equals("edit email clicked teacher")) {
            editTeacherEmail(message);
        }
        if (message.getRequest().equals("edit number clicked teacher")) {
            editTeacherNumber(message);
        }
        if (message.getRequest().equals("edit email clicked student")) {
            editStudentEmail(message);
        }
        if (message.getRequest().equals("edit number clicked student")) {
            editStudentNumber(message);
        }
        if (message.getRequest().equals("set name and degree")) {
            setDegreeUser(message);
        }
        if (message.getRequest().equals("get faculty prop")) {
            getFacultyProps(message);
        }
        if (message.getRequest().equals("get teacher list of faculty")) {
            getTeacherListFacultyProps(message);
        }
        if (message.getRequest().equals("get faculty prop unit")) {
            getFacultyPropsUnit(message);
        }
        if (message.getRequest().equals("get faculty prop stage")) {
            getFacultyPropsStage(message);
        }
        if (message.getRequest().equals("edit password in edu assistant")) {
            editPassEduAssis(message);
        }
        if (message.getRequest().equals("edit email in edu assistant")) {
            editEmailEduAssis(message);
        }
        if (message.getRequest().equals("add new teacher")) {
            addNewTeacher(message);
        }
        if (message.getRequest().equals("promote user")) {
            promoteUser(message);
        }
        if (message.getRequest().equals("change chosen")) {
            changeChosen(message);
        }
        if (message.getRequest().equals("relegate user")) {
            relegateUser(message);
        }
        if (message.getRequest().equals("delete course teacher")) {
            deleteCourseTeacher(message);
        }
        if (message.getRequest().equals("delete teacher")) {
            deleteTeacher(message);
        }
        if (message.getRequest().equals("edit btn clicked edit lesson desk")) {
            editEditLesson(message);
        }
        if (message.getRequest().equals("add btn clicked edit lesson desk")) {
            addEditLesson(message);
        }
        if (message.getRequest().equals("removal edit lesson desk")) {
            removalEditLesson(message);
        }
        if (message.getRequest().equals("sign up teacher")) {
            signUpTeacher(message);
        }
        if (message.getRequest().equals("sign up student")) {
            signUStudent(message);
        }
        if (message.getRequest().equals("reject recommendation")) {
            acceptRecommendation(message);
        }
        if (message.getRequest().equals("accept recommendation")) {
            rejectRecommendation(message);
        }
        if (message.getRequest().equals("reject minor")) {
            rejectMinor(message);
        }
        if (message.getRequest().equals("accept minor")) {
            acceptMinor(message);
        }
        if (message.getRequest().equals("reject withdraw")) {
            rejectWithdraw(message);
        }
        if (message.getRequest().equals("condition withdraw")) {
            conditionWithdraw(message);
        }
        if (message.getRequest().equals("accept withdraw")) {
            acceptWithdraw(message);
        }
    }

    private void rejectWithdraw(Message message) throws IOException, ParseException {
        String str = message.getContent();
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(str, null, null);
        Controller.getInstance().rejection(massageStudentMasterDesk);
    }

    private void conditionWithdraw(Message message) throws IOException, ParseException {
        String str = message.getContent();
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(str, null, null);
        Controller.getInstance().changeCondition(massageStudentMasterDesk);
    }

    private void acceptWithdraw(Message message) throws IOException, ParseException {
        String str = message.getContent();
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(str, null, null);
        Controller.getInstance().accept(massageStudentMasterDesk);
    }

    private void rejectMinor(Message message) throws IOException, ParseException {
        String[] data = message.getContent().split("-");
        MassageInNetwork GetUsers = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().rejectMinorRequest(GetUsers);
    }

    private void acceptMinor(Message message) throws IOException, ParseException {
        String[] data = message.getContent().split("-");
        MassageInNetwork GetUsers = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().acceptMinorRequest(GetUsers);
    }

    private void acceptRecommendation(Message message) throws IOException, ParseException {
        String[] data = message.getContent().split("-");
        MassageInNetwork acceptions = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().acceptRequest(acceptions);
    }

    private void rejectRecommendation(Message message) throws IOException, ParseException {
        String[] data = message.getContent().split("-");
        MassageInNetwork rejection = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().rejectionRequest(rejection);
    }

    private void signUpTeacher(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork massageSignUp = new MassageInNetwork(data[0], data[1], data[2], data[3],
                data[4], data[5], data[6], data[7], data[8], data[9], null);
        Controller.getInstance().signUpTeacher(massageSignUp);
    }

    private void signUStudent(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork massageSignUp = new MassageInNetwork(data[0], data[1], data[2], data[3],
                data[4], data[5], data[6], data[7], data[8], data[9], null);
        Controller.getInstance().signUpUser(massageSignUp);
    }

    private void editEditLesson(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork editingSelectedLesson = new MassageInNetwork(data[0], data[1],
                data[2], data[3], null, null, null, null, null, null);
        Controller.getInstance().editing(editingSelectedLesson);
    }

    private void addEditLesson(Message message) {
        String[] data = message.getContent().split("-");
        Boolean present = false;
        if (Objects.equals(data[7], "true")) present = true;
        MassageInNetwork addSelectedLesson = new MassageInNetwork(data[0], data[1],
                data[2], data[3], data[4], data[5], data[6]
                , present, null, null);
        Controller.getInstance().adding(addSelectedLesson);
    }

    private void removalEditLesson(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork removeSelectedLesson = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().removal(removeSelectedLesson);
    }

    private void deleteCourseTeacher(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork massage = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().deletingCourse(massage);
    }

    private void deleteTeacher(Message message) {
        MassageInNetwork deleting = new MassageInNetwork(message.getContent(), null, null);
        Controller.getInstance().deletingTeacher(deleting);
    }

    private void relegateUser(Message message) {
        MassageInNetwork relegation = new MassageInNetwork(message.getContent(), null, null);
        Controller.getInstance().relegation(relegation);
    }

    private void promoteUser(Message message) {
        MassageInNetwork promotion = new MassageInNetwork(message.getContent(), null, null);
        Controller.getInstance().promotion(promotion);
    }

    private void changeChosen(Message message) {
        MassageInNetwork changeChosen = new MassageInNetwork(message.getContent(), null, null);
        Controller.getInstance().valueChanger(changeChosen);
    }

    private void addNewTeacher(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork massageSignUp = new MassageInNetwork(data[0], data[1],
                data[2], data[3], data[4], data[5], data[6], data[7]);
        Controller.getInstance().addUser(massageSignUp);
    }

    private void editPassEduAssis(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork massageEditPassword = new MassageInNetwork(data[0], data[1], null, null, null
                , null, null, null, null, null);
        Controller.getInstance().editPass(massageEditPassword);
    }

    private void editEmailEduAssis(Message message) {
        String[] data = message.getContent().split("-");
        MassageInNetwork massageEditEmail = new MassageInNetwork(data[0], data[1], null, null, null, null, null, null, null, null);
        Controller.getInstance().editEmail(massageEditEmail);
    }

    private void getFacultyPropsUnit(Message message) {
        String[] data = message.getContent().split("-");
        String faculty = data[0];
        String unit = data[1];
        MassageInNetwork massageFacultyUnit = new MassageInNetwork(faculty, unit);
        String[] facultyLessons = Controller.getInstance().facultyUnitLessons(massageFacultyUnit);
        String str = "";
        for (String s : facultyLessons) {
            if (!Objects.equals(s, "null")) {
                if (!s.equals(facultyLessons[facultyLessons.length - 1])) str += s + "-";
                else str += s;
            }
        }
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), str, "get faculty prop unit"));
            }
        }


    }

    private void getFacultyPropsStage(Message message) {
        String[] data = message.getContent().split("-");
        String faculty = data[0];
        String stage = data[1];
        MassageInNetwork massageFacultyUnit = new MassageInNetwork(faculty, stage);
        String[] facultyLessons = Controller.getInstance().facultyStageLessons(massageFacultyUnit);
        String str = "";
        for (String s : facultyLessons) {
            if (!Objects.equals(s, "null")) {
                if (!s.equals(facultyLessons[facultyLessons.length - 1])) str += s + "-";
                else str += s;
            }
        }
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), str, "get faculty prop stage"));
            }
        }


    }

    private void getTeacherListFacultyProps(Message message) {
        MassageInNetwork massageTeacherListDesk = new MassageInNetwork(message.getContent(), null, null);
        String[] facultyTeachers = Controller.getInstance().facultyTeachers(massageTeacherListDesk);
        String str = "";
        for (String s : facultyTeachers) {
            if (!Objects.equals(s, "null")) {
                if (!s.equals(facultyTeachers[facultyTeachers.length - 1])) str += s + "-";
                else str += s;
            }
        }
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), str, "get teacher list of faculty"));
            }
        }
    }

    private void getFacultyProps(Message message) {
        MassageInNetwork massageLessonListDesk = new MassageInNetwork(message.getContent(), null, null);
        String[] facultyLessons = Controller.getInstance().facultyLessons(massageLessonListDesk);
        String str = "";
        for (String s : facultyLessons) {
            if (!Objects.equals(s, "null")) {
                if (!s.equals(facultyLessons[facultyLessons.length - 1])) str += s + "-";
                else str += s;
            }
        }
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), str, "get faculty prop"));
            }
        }
    }

    private void setDegreeUser(Message message) throws IOException, ParseException {
        String userName = message.getContent();
        String userDegree = Logic.getUserDegree(userName);
        String data = userName + "-" + userDegree;
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), data, "set name and degree"));
            }
        }
    }

    private void getName(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), Logic.getUserName(message.getContent()), "get name"));
            }
        }
    }

    private void editStudentEmail(Message message) {
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.emailEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "", "student email edited"));
            }
        }
    }

    private void editStudentNumber(Message message) {
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.passEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "", "student number edited"));
            }
        }
    }

    private void editTeacherNumber(Message message) {
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.passEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "", "teacher number edited"));
            }
        }
    }

    private void editTeacherEmail(Message message) throws IOException, ParseException {
        String[] data = message.getContent().split("-");
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                Logic editingEmail = new Logic();
                editingEmail.emailEditProfile(data[1], data[0]);
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "", "teacher email edited"));
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
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), Logic.getType(message.getContent()), "get type"));
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
        String userData = username + "-" + email + "-" + faculty + "-" + phoneNumber + "-" + degree + "-" +
                nationalId + "-" + userId + "-" + roomNo;
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), userData, "show data teacher"));
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
        String userData = username + "-" + id + "-" + nationalId + "-" + email + "-" + condition + "-" + supervisor + "-" + faculty + "-" +
                phoneNumber + "-" + enteranceYear + "-" + userType + "-" + score;
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), userData, "show data student"));
            }
        }
    }

    private void getDegree(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), Logic.getDegree(message.getContent()), "get degree"));
            }
        }
    }

    private void getDegreeTeacherList(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), Logic.getDegree(message.getContent()), "get degree teacher list"));
            }
        }
    }

    private void getDegreeLessonList(Message message) throws IOException, ParseException {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), Logic.getDegree(message.getContent()), "get degree lesson list"));
            }
        }
    }

    private void nameSet(Message message) {
        for (ClientHandler clientHandler : Server.getServer().getClientHandlers()) {
            if (clientHandler.getAuthToken().equals(message.getAuthToken())) {
                clientHandler.setUsername(message.getContent());
                clientHandler.sendMessage(new Message(clientHandler.getAuthToken(), "name set", "set name"));
            }
        }
    }

}
