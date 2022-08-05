package edu.system.logic;

import edu.system.HelloApplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {

    public static Controller controller;

    private Controller() {

    }

    public static Controller getInstance() {
        if (controller == null) {
            controller = new Controller();
        }
        return controller;
    }

    static Logger log = LogManager.getLogger(HelloApplication.class);

    public boolean login(MassageInNetwork massage) {
        String name = massage.getName();
        String pass = massage.getPass();
        Logic login2 = new Logic();
        return (login2.checkUserPass(pass, name));
    }

    public void deletingCourse(MassageInNetwork massageInNetwork) {
        String username = massageInNetwork.getName();
        String faculty = massageInNetwork.getPass();
        Logic deleting = new Logic();
        deleting.deleteCourseTeacher(username, faculty);

    }

    public void editPass(MassageInNetwork massageInNetwork) {
        String username = massageInNetwork.getName();
        String password = massageInNetwork.getPass();
        Logic editingPass = new Logic();
        editingPass.editingPassWord(username, password);
    }

    public Boolean editPassLogOut(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String username = massageInNetwork.getName();
        String newPassword = massageInNetwork.getPass();
        String oldPassword = massageInNetwork.getId();
        Logic editingPass = new Logic();
        return editingPass.editPassLogOut(username, newPassword, oldPassword);
    }

    public void addUser(MassageInNetwork massageSignUp) {
        String user = massageSignUp.getUsername();
        String pass = massageSignUp.getPassword();
        String email = massageSignUp.getEmail();
        String room = massageSignUp.getRoomNo();
        String phone = massageSignUp.getPhone();
        String faculty = massageSignUp.getFaculty();
        String masterDegree = massageSignUp.getMasterDegree();
        String id = massageSignUp.getId();
        Logic addingTeacher = new Logic();
        addingTeacher.addingTeacherUser(user, pass, email, room, phone, faculty, masterDegree, id);

    }

    public void signUpUser(MassageInNetwork massageSignUp) {
        log.info("write new file for new user");
        String user = massageSignUp.getUsername();
        String id = massageSignUp.getId();
        String supervisor = massageSignUp.getSupervisor();
        String enteringYear = massageSignUp.getEnteringYear();
        String condition = massageSignUp.getCondition();
        String pass = massageSignUp.getPassword();
        String email = massageSignUp.getEmail();
        String room = massageSignUp.getRoomNo();
        String phone = massageSignUp.getPhone();
        String faculty = massageSignUp.getFaculty();
        String degree = massageSignUp.getDegree();
        String masterDegree = massageSignUp.getMasterDegree();
        Logic addingTeacher = new Logic();
        addingTeacher.signUpUser(user, id, phone, supervisor, faculty, enteringYear, condition, pass, email, degree);
    }

    public void signUpTeacher(MassageInNetwork massageSignUp) {
        log.info("write new file for new user");
        String user = massageSignUp.getUsername();
        String id = massageSignUp.getId();
        String supervisor = massageSignUp.getSupervisor();
        String enteringYear = massageSignUp.getEnteringYear();
        String condition = massageSignUp.getCondition();
        String pass = massageSignUp.getPassword();
        String email = massageSignUp.getEmail();
        String room = massageSignUp.getRoomNo();
        String phone = massageSignUp.getPhone();
        String faculty = massageSignUp.getFaculty();
        String degree = massageSignUp.getDegree();
        String masterDegree = massageSignUp.getMasterDegree();
        Logic addingTeacher = new Logic();
        addingTeacher.signUpTeachers(user, id, phone, supervisor, faculty, enteringYear, condition, pass, email, degree);
    }

    public void addRecommendRequest(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String teacherName = massageInNetwork.getName();
        String lessonName = massageInNetwork.getPass();
        String score = massageInNetwork.getTime();
        String ta = massageInNetwork.getTeacher();
        String userName = massageInNetwork.getId();
        Logic addRecommendRequest = new Logic();
        addRecommendRequest.getRecommend(teacherName, lessonName, score, ta, userName);
    }

    public void addMinorRequest(MassageInNetwork massageFacultyUnit) throws IOException, ParseException {
        String userName = massageFacultyUnit.getName();
        String faculty1 = massageFacultyUnit.getUnit();
        String faculty2 = massageFacultyUnit.getFaculty();
        Logic addMinority = new Logic();
        addMinority.userMinorRequest(userName, faculty1, faculty2);
    }

    public void editEmail(MassageInNetwork massageInNetwork) {
        String username = massageInNetwork.getName();
        String email = massageInNetwork.getPass();
        Logic editingEmail = new Logic();
        editingEmail.editingEmail(username, email);
    }

    public void editEmailProfile(MassageInNetwork massageInNetwork) {
        log.info("Read and rewrite user email from userdata file");
        String username = massageInNetwork.getName();
        String email = massageInNetwork.getPass();
        Logic editingEmail = new Logic();
        editingEmail.emailEditProfile(username, email);
    }

    public void editPassProfile(MassageInNetwork massageInNetwork) {
        String username = massageInNetwork.getName();
        String phone = massageInNetwork.getPass();
        Logic editingEmail = new Logic();
        editingEmail.passEditProfile(username, phone);
    }

    public String userDeskEmail(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getEmail(name);
    }

    public String userDeskUserName(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getUserName(name);
    }

    public String userScore(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.getUserScore(name);
    }

    public void userNewScore(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        String score = massageUserDesk.getScore();
        Logic.setUserNewScore(name, score);
    }

    public String userYear(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.getUserYear(name);
    }

    public String userId(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getId(name);
    }

    public String userphoneNumber(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.getUserPhoneNumber(name);
    }

    public void withdrawRequest(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic.userWithdraw(name);
    }

    public void rejection(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic.rejectUser(name);
    }

    public void rejectionRequest(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String nameStudent = massageInNetwork.getName();
        String nameTeacher = massageInNetwork.getPass();
        Logic.rejectRecommend(nameStudent, nameTeacher);
    }

    public void acceptRequest(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String nameStudent = massageInNetwork.getName();
        String nameTeacher = massageInNetwork.getPass();
        Logic.acceptRecommend(nameStudent, nameTeacher);
    }

    public void accept(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic.acceptUser(name);
    }

    public void changeCondition(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic.condition(name);
    }

    public String userCondition(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getUserCondition(name);
    }

    public String getUserFaculty(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getUserFaculty(name);
    }

    public String getUserStudentNumber(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getUserStudentNumber(name);
    }

    public String userDegree(MassageInNetwork massageUserDegree) throws IOException, ParseException {
        String name = massageUserDegree.getName();
        return Logic.getUserDegree(name);
    }

    public String userMastery(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.getUserMastery(name);
    }

    public String withdrawResult(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.userWithdrawResult(name);
    }

    public String recommendResult(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.userResult(name);
    }

    public String facultyForMinors(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.minor(name);
    }

    public String recommendGetTeacher(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.teacherGetName(name);
    }

    public String recommendGetLessons(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.teacherGetLesson(name);
    }

    public String recommendGetScores(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.teacherGetScore(name);
    }

    public String recommendGetTa(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.teacherGetTa(name);
    }

    public String educationalStatus(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getEducationalStatus(name);
    }

    public String supervisor(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getSupervisor(name);
    }

    public void removal(MassageInNetwork removalLesson) {
        String lesson = removalLesson.getName();
        String faculty = removalLesson.getPass();
        Logic removeLessons = new Logic();
        removeLessons.removeLesson(lesson, faculty);
    }

    public void editing(MassageInNetwork editingLesson) {
        String lesson = editingLesson.getName();
        String faculty = editingLesson.getPass();
        String time = editingLesson.getTime();
        String teacher = editingLesson.getTeacher();
        Logic editingLessons = new Logic();
        editingLessons.edit(lesson, faculty, time, teacher);
    }

    public void adding(MassageInNetwork editingLesson) {
        String lesson = editingLesson.getName();
        String faculty = editingLesson.getPass();
        String time = editingLesson.getTime();
        String teacher = editingLesson.getTeacher();
        String unity = editingLesson.getUnity();
        String stage = editingLesson.getStage();
        String id = editingLesson.getId();
        Boolean isPresent = editingLesson.getPresent();
        Logic editingLessons = new Logic();
        editingLessons.add(lesson, faculty, time, teacher, unity, stage, id, isPresent);
    }

    public String userDeskType(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getType(name);
    }

    public String userDeskDegreee(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getUserDegree(name);
    }

    public String[] facultyLessons(MassageInNetwork massageFacultyName) {
        String name = massageFacultyName.getName();
        Logic lessonListController = new Logic();
        return lessonListController.getFacultyData(name);
    }

    public String[] userOfLessons(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.lessonsOfUser(name);
    }

    public String[] nameOfLessons(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.nameOfLesson(name);
    }

    public String[] listOfUser(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.nameLists(name);
    }

    public String[] listOfUserRecommend(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.nameListsRecommend(name);
    }

    public String[] listOfUserMinor(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String name = massageInNetwork.getName();
        String faculty = massageInNetwork.getPass();
        Logic lessonListController = new Logic();
        return lessonListController.nameListsMinor(name, faculty);
    }

    public void rejectMinorRequest(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String name = massageInNetwork.getName();
        String faculty = massageInNetwork.getPass();
        Logic lessonListController = new Logic();
        lessonListController.rejectMinor(name, faculty);
    }

    public void acceptMinorRequest(MassageInNetwork massageInNetwork) throws IOException, ParseException {
        String name = massageInNetwork.getName();
        String faculty = massageInNetwork.getPass();
        Logic lessonListController = new Logic();
        lessonListController.acceptMinor(name, faculty);
    }

    public String[] examOfLessons(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.examOfLesson(name);
    }

    public ArrayList<String> getLessons(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userLessonss(name);
    }

    public ArrayList<String> getLessonsNew(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userLessonssNew(name);
    }

    public void setObjection(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        ArrayList<String> objection = massageUserDesk.getObjections();
        Logic lessonListController = new Logic();
        lessonListController.userObjections(name, objection);
    }

    public ArrayList<String> getTeachers(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userTeacher(name);
    }

    public ArrayList<String> getTeachersNew(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userTeacherNew(name);
    }

    public ArrayList<String> getScores(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userScore(name);
    }

    public ArrayList<String> getScoresNew(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userScoreNew(name);
    }

    public ArrayList<String> getUnits(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.lessonUnit(name);
    }

    public ArrayList<String> getUnitsNew(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.lessonUnitNew(name);
    }

    public ArrayList<String> getRespond(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.userRespond(name);
    }

    public String[] facultyTeachers(MassageInNetwork massageFacultyName) {
        String name = massageFacultyName.getName();
        Logic lessonListController = new Logic();
        return lessonListController.getFacultyTeachers(name);
    }

    public String[] facultyUnitLessons(MassageInNetwork massageFacultyUnit) {
        String name = massageFacultyUnit.getName();
        String unit = massageFacultyUnit.getUnit();
        Logic lessonListController = new Logic();
        return lessonListController.getFacultyUnitData(name, unit);
    }

    public String[] facultyStageLessons(MassageInNetwork massageFacultyUnit) {
        String name = massageFacultyUnit.getName();
        String unit = massageFacultyUnit.getUnit();
        Logic lessonListController = new Logic();
        return lessonListController.getFacultyStageData(name, unit);
    }

    public Boolean isChosen(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.isChosenBefore(name);
    }

    public void valueChanger(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        lessonListController.change(name);
    }

    public void promotion(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        lessonListController.promotion(name);
    }

    public void relegation(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        lessonListController.relegation(name);
    }

    public void deletingTeacher(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        lessonListController.TeacherDeleting(name);
    }

    public String userFaculty(MassageInNetwork massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return Logic.getFaculty(name);
    }

    public String userNationalId(MassageInNetwork massageUserDesk) {
        String name = massageUserDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.getNationalId(name);
    }

    public String userRoomNo(MassageInNetwork massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return Logic.getRoomNo(name);
    }

    public String selectedUserFaculty(MassageInNetwork massageStudentDesk) {
        String name = massageStudentDesk.getName();
        Logic lessonListController = new Logic();
        return lessonListController.getSelectedFaculty(name);
    }
}