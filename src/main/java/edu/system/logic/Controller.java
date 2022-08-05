package edu.system.logic;

import edu.system.HelloApplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;

public class Controller {
    public static  Controller controller;

    private Controller(){

    }

    public static Controller getInstance(){
        if (controller == null){
            controller = new Controller();
        }
        return controller;
    }
    static Logger log = LogManager.getLogger(HelloApplication.class);
    public boolean login(MassageLogin massage){
        String name = massage.getName();
        String pass = massage.getPass();
        Logic login2 = new Logic();
        return (login2.checkUserPass(pass, name));
    }
    public void deletingCourse(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String faculty = massageLogin.getPass();
        Logic deleting = new Logic();
        deleting.deleteCourseTeacher(username, faculty);

    }
    public void editPass(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String password = massageLogin.getPass();
        Logic editingPass = new Logic();
        editingPass.editingPassWord(username, password);
    }
    public Boolean editPassLogOut(MassageLogin massageLogin) throws IOException, ParseException {
        String username = massageLogin.getName();
        String newPassword = massageLogin.getPass();
        String oldPassword = massageLogin.getId();
        Logic editingPass = new Logic();
        return editingPass.editPassLogOut(username, newPassword, oldPassword);
    }
    public void addUser(MassageLogin massageSignUp){
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
    public void signUpUser(MassageLogin massageSignUp){
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
        addingTeacher.signUpUser(user, id, phone, supervisor, faculty,enteringYear, condition, pass, email,degree);
    }
    public void signUpTeacher(MassageLogin massageSignUp){
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
        addingTeacher.signUpTeachers(user, id, phone, supervisor, faculty,enteringYear, condition, pass, email,degree);
    }
    public void addRecommendRequest(MassageLogin massageLogin) throws IOException, ParseException {
        String teacherName = massageLogin.getName();
        String lessonName = massageLogin.getPass();
        String score = massageLogin.getTime();
        String ta = massageLogin.getTeacher();
        String userName = massageLogin.getId();
        Logic addRecommendRequest = new Logic();
        addRecommendRequest.getRecommend(teacherName, lessonName, score, ta, userName);
    }
    public void addMinorRequest(MassageLogin massageFacultyUnit) throws IOException, ParseException {
        String userName = massageFacultyUnit.getName();
        String faculty1 = massageFacultyUnit.getUnit();
        String faculty2 = massageFacultyUnit.getFaculty();
        Logic addMinority = new Logic();
        addMinority.userMinorRequest(userName, faculty1, faculty2);
    }
    public void editEmail(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String email = massageLogin.getPass();
        Logic editingEmail = new Logic();
        editingEmail.editingEmail(username, email);
    }
    public void editEmailProfile(MassageLogin massageLogin){
        log.info("Read and rewrite user email from userdata file");
        String username = massageLogin.getName();
        String email = massageLogin.getPass();
        Logic editingEmail = new Logic();
        editingEmail.emailEditProfile(username, email);
    }
    public void editPassProfile(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String phone = massageLogin.getPass();
        Logic editingEmail = new Logic();
        editingEmail.passEditProfile(username, phone);
    }
    public String userDeskEmail(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return userController.getEmail(name);
    }
    public String userDeskUserName(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return userController.getUserName(name);
    }
    public String userScore(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.getUserScore(name);
    }
    public void userNewScore(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        String score = massageUserDesk.getScore();
        userController studentController = new userController();
        studentController.setUserNewScore(name, score);
    }
    public String userYear(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return userController.getUserYear(name);
    }
    public String userId(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return userController.getId(name);
    }
    public String userphoneNumber(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        return userController.getUserPhoneNumber(name);
    }
    public void withdrawRequest(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController userWithdrawRequest = new userController();
        userWithdrawRequest.userWithdraw(name);
    }
    public void rejection(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController userWithdrawRequest = new userController();
        userWithdrawRequest.rejectUSer(name);
    }
    public void rejectionRequest(MassageLogin massageLogin) throws IOException, ParseException {
        String nameStudent = massageLogin.getName();
        String nameTeacher = massageLogin.getPass();
        userController userWithdrawRequest = new userController();
        userWithdrawRequest.rejectRecommend(nameStudent, nameTeacher);
    }
    public void acceptRequest(MassageLogin massageLogin) throws IOException, ParseException {
        String nameStudent = massageLogin.getName();
        String nameTeacher = massageLogin.getPass();
        userController userWithdrawRequest = new userController();
        userWithdrawRequest.acceptRecommend(nameStudent, nameTeacher);
    }
    public void accept(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController userWithdrawRequest = new userController();
        userWithdrawRequest.acceptUser(name);
    }
    public void changeCondition(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController userWithdrawRequest = new userController();
        userWithdrawRequest.condition(name);
    }
    public String userCondition(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getUserCondition(name);
    }
    public String getUserFaculty(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getUserFaculty(name);
    }
    public String getUserStudentNumber(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getUserStudentNumber(name);
    }
    public String userDegree(MassageLogin massageUserDegree) throws IOException, ParseException {
        String name = massageUserDegree.getName();
        userController studentController = new userController();
        return studentController.getUserDegree(name);
    }
    public String userMastery(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.getUserMastery(name);
    }
    public String withdrawResult(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.userWithdrawResult(name);
    }
    public String recommendResult(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.userResult(name);
    }
    public String facultyForMinors(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentMinor = new userController();
        return studentMinor.minor(name);
    }
    public String recommendGetTeacher(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.teacherGetName(name);
    }
    public String recommendGetLessons(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.teacherGetLesson(name);
    }
    public String recommendGetScores(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.teacherGetScore(name);
    }
    public String recommendGetTa(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        userController studentController = new userController();
        return studentController.teacherGetTa(name);
    }
    public String educationalStatus(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getEducationalStatus(name);
    }
    public String supervisor(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return userController.getSupervisor(name);
    }
    public void removal(MassageLogin removalLesson){
        String lesson = removalLesson.getName();
        String faculty = removalLesson.getPass();
        LessonListController removeLessons = new LessonListController();
        removeLessons.removeLesson(lesson, faculty);
    }
    public void editing(MassageLogin editingLesson){
        String lesson = editingLesson.getName();
        String faculty = editingLesson.getPass();
        String time = editingLesson.getTime();
        String teacher = editingLesson.getTeacher();
        LessonListController editingLessons = new LessonListController();
        editingLessons.edit(lesson, faculty, time, teacher);
    }
    public void adding(MassageLogin editingLesson){
        String lesson = editingLesson.getName();
        String faculty = editingLesson.getPass();
        String time = editingLesson.getTime();
        String teacher = editingLesson.getTeacher();
        String unity = editingLesson.getUnity();
        String stage = editingLesson.getStage();
        String id = editingLesson.getId();
        Boolean isPresent = editingLesson.getPresent();
        LessonListController editingLessons = new LessonListController();
        editingLessons.add(lesson, faculty, time, teacher, unity, stage, id, isPresent);
    }
    public String userDeskType(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        return userController.getType(name);
    }
    public String userDeskDegreee(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getUserDegree(name);
    }
    public String[] facultyLessons(MassageLogin massageFacultyName) {
        String name = massageFacultyName.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyData(name);
    }

    public String[] userOfLessons(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.lessonsOfUser(name);
    }
    public String[] nameOfLessons(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.nameOfLesson(name);
    }
    public String[] listOfUser(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.nameLists(name);
    }
    public String[] listOfUserRecommend(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.nameListsRecommend(name);
    }
    public String[] listOfUserMinor(MassageLogin massageLogin) throws IOException, ParseException {
        String name = massageLogin.getName();
        String faculty = massageLogin.getPass();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.nameListsMinor(name, faculty);
    }
    public void rejectMinorRequest(MassageLogin massageLogin) throws IOException, ParseException {
        String name = massageLogin.getName();
        String faculty = massageLogin.getPass();
        LessonListController lessonListController = new LessonListController();
        lessonListController.rejectMinor(name, faculty);
    }
    public void acceptMinorRequest(MassageLogin massageLogin) throws IOException, ParseException {
        String name = massageLogin.getName();
        String faculty = massageLogin.getPass();
        LessonListController lessonListController = new LessonListController();
        lessonListController.acceptMinor(name, faculty);
    }
    public String[] examOfLessons(MassageLogin massageUserDesk) throws IOException, ParseException {
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.examOfLesson(name);
    }
    public ArrayList<String> getLessons(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userLessonss(name);
    }
    public ArrayList<String> getLessonsNew(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userLessonssNew(name);
    }
    public void setObjection(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        ArrayList<String> objection = massageUserDesk.getObjections();
        LessonListController lessonListController = new LessonListController();
        lessonListController.userObjections(name, objection);
    }
    public ArrayList<String> getTeachers(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userTeacher(name);
    }
    public ArrayList<String> getTeachersNew(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userTeacherNew(name);
    }
    public ArrayList<String> getScores(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userScore(name);
    }
    public ArrayList<String> getScoresNew(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userScoreNew(name);
    }
    public ArrayList<String> getUnits(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.lessonUnit(name);
    }
    public ArrayList<String> getUnitsNew(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.lessonUnitNew(name);
    }
    public ArrayList<String> getRespond(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.userRespond(name);
    }

//    public ArrayList<String> setObject(MassageUserDesk massageUserDesk){
//        String name = massageUserDesk.getName();
//        LessonListController lessonListController = new LessonListController();
//        return lessonListController.setUserObject(name);
//    }

    public String[] facultyTeachers(MassageLogin massageFacultyName) {
        String name = massageFacultyName.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyTeachers(name);
    }
    public String[] facultyUnitLessons(MassageLogin massageFacultyUnit) {
        String name = massageFacultyUnit.getName();
        String unit = massageFacultyUnit.getUnit();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyUnitData(name, unit);
    }
    public String[] facultyStageLessons(MassageLogin massageFacultyUnit) {
        String name = massageFacultyUnit.getName();
        String unit = massageFacultyUnit.getUnit();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyStageData(name, unit);
    }
    public Boolean isChosen (MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.isChosenBefore(name);
    }
    public void valueChanger (MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.change(name);
    }
    public void promotion (MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.promotion(name);
    }
    public void relegation (MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.relegation(name);
    }
    public void deletingTeacher(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.TeacherDeleting(name);
    }
    public String userFaculty(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFaculty(name);
    }
    public String userNationalId(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getNationalId(name);
    }
    public String userRoomNo(MassageLogin massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getRoomNo(name);
    }
    public String selectedUserFaculty(MassageLogin massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getSelectedFaculty(name);
    }
}