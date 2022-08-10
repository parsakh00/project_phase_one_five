package edu.system;

import gui.*;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import message.Message;
import org.json.simple.parser.ParseException;
import server.Logic;

import java.io.IOException;
import java.util.ArrayList;

public class ClientLogic {

    private static ClientLogic clientLogic;

    private Stage stage;
    private Login login;
    private StudentProfile studentProfile;
    private TeacherProfile teacherProfile;
    private TeachersListDesk teachersListDesk;
    private LessonListDesk lessonListDesk;
    private StudentUndergraduateDesk studentUndergraduateDesk;
    private StudentMasterDesk studentMasterDesk;
    private StudentPhdDesk studentPhdDesk;
    private TeacherDesk teacherDesk;
    private EducationalAssistantDesk educationalAssistantDesk;
    private ChangeEduAssisOrEdit changeEduAssisOrEdit;
    private EditLessonsDesk editLessonsDesk;
    private LogOutDesk logOutDesk;
    private SignUpStudent signUpStudent;
    private SignUpTeacher signUpTeacher;
    private TeacherRecommendRequest teacherRecommendRequest;
    private EduWithdrawRequest eduWithdrawRequest;
    private EduMinorRequests eduMinorRequests;

    private MinorDesk minorDesk;
    private withdrawalFromEducationDesk withdrawalFromEducationDesk;
    private Recommendation recommendation;
    private WeeklySchedule weeklySchedule;
    private ExamsList examsList;
    private ApplyCertificateEmploymentDesk applyCertificateEmploymentDesk;
    private RequestDefendDissertation requestDefendDissertation;
    private AccommodationDesk accommodationDesk;


    private ClientLogic() {

    }

    public static ClientLogic getInstance() {
        if (ClientLogic.clientLogic == null)
            ClientLogic.clientLogic = new ClientLogic();
        return ClientLogic.clientLogic;
    }

    public void analyse(Message message) throws IOException, ParseException, InterruptedException {
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
        if (message.getRequest().equals("show weekly schedule")) {
            showWeeklySchedule(message);
        }
        if (message.getRequest().equals("showWithdrawalResult")) {
            showWithdrawalResult(message);
        }
        if (message.getRequest().equals("get user lesson exam")) {
            getUserLessonExam(message);
        }
        if (message.getRequest().equals("get user lesson name")) {
            getUserLessonName(message);
        }
        if (message.getRequest().equals("show minor result")) {
            showMinorResult(message);
        }
        if (message.getRequest().equals("get user lesson exam")) {
            userLessonExam(message);
        }
        if (message.getRequest().equals("show message for admin")){
            showMessageForAdmin(message);
        }
        if (message.getRequest().equals("message for admin previous")){
            showMessageForAdminPr();
        }
        if (message.getRequest().equals("get students base on filter")) {
            getStudentFilter(message);
        }
        if (message.getRequest().equals("show message of mohseni")) {
            showMohseniMessage(message);
        }
        if (message.getRequest().equals("show specific user for mohseni")){
            studentProfileForMohseni(message);
        }
    }
    private void studentProfileForMohseni(Message message){
        teacherDesk.showStudentProfile(message.getContent());
    }
    private void showMohseniMessage(Message message){
        String data = message.getContent();
        if (this.studentPhdDesk != null) studentPhdDesk.showMohseniMessageDesk(data);
        if (this.studentMasterDesk != null) studentMasterDesk.showMohseniMessageDesk(data);
        if (this.studentUndergraduateDesk != null) studentUndergraduateDesk.showMohseniMessageDesk(data);
    }


    private void getStudentFilter(Message message) throws IOException, ParseException {
        String data = Logic.getStudentFilter(message.getContent());
        System.out.println(data);
        teacherDesk.getStudentFilter(data);
    }

    private void showMessageForAdmin(Message message) throws IOException, ParseException {
        String data = Logic.getMessageForAdmin();
        if (this.teacherDesk != null) teacherDesk.showMessageForAdmin(data);
        else System.out.println("is null");
        teacherDesk.showMessageAdmin(message.getContent());
    }
    private void showMessageForAdminPr() throws IOException, ParseException, InterruptedException {
        Thread.sleep(100);
        String data = Logic.getMessageForAdmin();
        if (this.teacherDesk != null) teacherDesk.showMessageForAdmin(data);
        else System.out.println("is null");
    }
    private void showMinorResult(Message message){
        minorDesk.showResult(message.getContent());
    }
    private void getUserLessonExam(Message message){
        //examsList.userLessonExam(message.getContent());
    }
    private void userLessonExam(Message message){
        examsList.getUserLessonExamNew(message.getContent());
    }
    private void getUserLessonName(Message message){
        //examsList.userLessonName(message.getContent());
    }

    private void showWithdrawalResult(Message message) {
        withdrawalFromEducationDesk.withdrawalResult(message.getContent());
    }

    private void showWeeklySchedule(Message message) {
        weeklySchedule.showStudentSchedule(message.getContent());
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
        this.eduWithdrawRequest = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("edu withdraw request is null");
    }

    public void setEduMinorRequests(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("edu minor request desk");
        this.stage = stage;
        this.eduMinorRequests = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("edu minor request desk is null");
    }

    public void setTeacherRecommendRequest(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("teacher recommendation request desk");
        this.stage = stage;
        this.teacherRecommendRequest = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("teacher recommendation desk is null");
    }

    public void setMinorDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Minor Desk");
        this.stage = stage;
        this.minorDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Minor Desk is null");
    }

    public void setWithdrawalFromEducationDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Withdrawal From Education desk");
        this.stage = stage;
        this.withdrawalFromEducationDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Withdrawal From Education desk is null");
    }

    public void setRecommendation(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Recommendation desk");
        this.stage = stage;
        this.recommendation = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Recommendation desk is null");
    }

    public void setWeeklySchedule(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Weekly Schedule desk");
        this.stage = stage;
        this.weeklySchedule = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Weekly Schedule desk is null");
    }

    public void setExamsList(FXMLLoader fxmlLoader, Stage stage) {
        if (fxmlLoader.getController() == null) System.out.println("Exams List desk is null");
        System.out.println("Exams List desk");
        this.stage = stage;
        this.examsList = fxmlLoader.getController();
    }

    public void setApplyCertificateEmploymentDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Apply Certificate Employment Desk desk");
        this.stage = stage;
        this.applyCertificateEmploymentDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Apply Certificate Employment Desk desk is null");
    }

    public void setRequestDefendDissertation(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Request Defend Dissertation desk");
        this.stage = stage;
        this.requestDefendDissertation = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Request Defend Dissertation desk is null");
    }

    public void setAccommodationDesk(FXMLLoader fxmlLoader, Stage stage) {
        System.out.println("Accommodation desk request desk");
        this.stage = stage;
        this.accommodationDesk = fxmlLoader.getController();
        if (fxmlLoader.getController() == null) System.out.println("Accommodation desk is null");
    }
}
