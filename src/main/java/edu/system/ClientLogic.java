package edu.system;

import gui.*;
import javafx.fxml.FXMLLoader;
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
    private TeachersListDesk teachersListDesk;
    private LessonListDesk lessonListDesk;
    private ChangeEduAssisOrEdit changeEduAssisOrEdit;
    private EditLessonsDesk editLessonsDesk;
    private LogOutDesk logOutDesk;
    private SignUpStudent signUpStudent;
    private SignUpTeacher signUpTeacher;
    private TeacherRecommendRequest teacherRecommendRequest;
    private EduWithdrawRequest eduWithdrawRequest;
    private EduMinorRequests eduMinorRequests;

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
        if (message.getRequest().equals("get degree lesson list")) {
            getUserDegreeLessonList(message.getContent());
        }
        if (message.getRequest().equals("get degree teacher list")) {
            getUserDegreeTeacherList(message.getContent());
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
        if (message.getRequest().equals("get faculty prop")) {
            showFacultyProp(message);
        }
        if (message.getRequest().equals("get faculty prop unit")) {
            showFacultyPropUnit(message);
        }
        if (message.getRequest().equals("get faculty prop stage")) {
            showFacultyPropStage(message);
        }
        if (message.getRequest().equals("get teacher list of faculty")) {
            showFacultyTeacherProp(message);
        }

    }

    private void showFacultyPropUnit(Message message) {
        lessonListDesk.getFacultyUnitData(message);
    }

    private void showFacultyPropStage(Message message) {
        lessonListDesk.getFacultyStageData(message);
    }

    private void showFacultyProp(Message message) {
        lessonListDesk.getFacultyData(message);
    }

    private void showFacultyTeacherProp(Message message) {
        teachersListDesk.getTeacherListData(message);
    }

    private void showStudentDegreeName(Message message) {
        studentProfile.setUserDegreeAndName(message.getContent());
    }

    private void showUserName(Message message) {
        teacherProfile.setUserName(message.getContent());
    }

    private void authToken(Message message) {
        Client.getClient().setAuthToken(message.getContent());
    }

    private void showTeacherData(Message message) {
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

    private void getUserDegreeLessonList(String content) {
        lessonListDesk.setDegree(content);
    }

    private void getUserDegreeTeacherList(String content) {
        teachersListDesk.setDegree(content);
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
        System.out.println("log out");
        this.stage = stage;
        this.logOutDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("log out is null");
    }

    public void setTeachersListDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("teacher list desk");
        this.stage = stage;
        this.teachersListDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("teacher list disk is  null");
    }

    public void setLessonListDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("lesson list desk");
        this.stage = stage;
        this.lessonListDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("lesson list desk is null");
    }

    public void setEditLessonsDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("edit lessons desk desk");
        this.stage = stage;
        this.editLessonsDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("edit lessons desk is null");
    }

    public void setChangeEduAssisOrEdit(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("ChangeEduAssisOrEdit desk");
        this.stage = stage;
        this.changeEduAssisOrEdit = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("lesson list desk is null");
    }
    public void setSignUpStudent(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("signup student desk");
        this.stage = stage;
        this.signUpStudent = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("signup student desk is null");
    }
    public void setSignUpTeacher(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("signup teacher desk");
        this.stage = stage;
        this.signUpTeacher = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("signup teacher desk is null");
    }
    public void setEduWithdrawRequest(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("edu withdraw request");
        this.stage = stage;
        this.signUpTeacher = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("edu withdraw request is null");
    }
    public void setEduMinorRequests(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("edu minor request desk");
        this.stage = stage;
        this.signUpTeacher = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("edu minor request desk is null");
    }
    public void setTeacherRecommendRequest(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("teacher recommendation request desk");
        this.stage = stage;
        this.signUpTeacher = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("teacher recommendation desk is null");
    }


}
