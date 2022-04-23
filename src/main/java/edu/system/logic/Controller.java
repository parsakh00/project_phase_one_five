package edu.system.logic;

import org.json.simple.parser.ParseException;

import java.io.IOException;

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



    public boolean login(MassageLogin massage){
        String name = massage.getName();
        String pass = massage.getPass();
        LoginController login2 = new LoginController();
        return (login2.checkName(name) && login2.checkPass(pass, name));
    }
    public void deletingCourse(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String faculty = massageLogin.getPass();
        LoginController deleting = new LoginController();
        deleting.deleteCourseTeacher(username, faculty);

    }
    public void editPass(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String password = massageLogin.getPass();
        LoginController editingPass = new LoginController();
        editingPass.editingPassWord(username, password);

    }

    public void addUser(MassageSignUp massageSignUp){
        String user = massageSignUp.getUsername();
        String pass = massageSignUp.getPassword();
        String email = massageSignUp.getEmail();
        String room = massageSignUp.getRoomNo();
        String phone = massageSignUp.getPhone();
        String faculty = massageSignUp.getFaculty();
        LoginController addingTeacher = new LoginController();
        addingTeacher.addingTeacherUser(user, pass, email, room, phone, faculty);

    }



    public void editEmail(MassageLogin massageLogin){
        String username = massageLogin.getName();
        String email = massageLogin.getPass();
        LoginController editingEmail = new LoginController();
        editingEmail.editingEmail(username, email);

    }

    public String userDeskEmail(MassageUserDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getEmail(name);

    }

    public String userDeskUserName(MassageUserDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getUserName(name);
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


    public String userDeskType(MassageUserDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getType(name);
    }
    public String userDeskDegreee(MassageUserDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        userController studentController = new userController();
        return studentController.getUserDegree(name);
    }

    public String[] facultyLessons(MassageUserDesk massageFacultyName) {
        String name = massageFacultyName.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyData(name);
    }
    public String[] facultyTeachers(MassageUserDesk massageFacultyName) {
        String name = massageFacultyName.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyTeachers(name);
    }
    public String[] facultyUnitLessons(MassageFacultyUnit massageFacultyUnit) {
        String name = massageFacultyUnit.getName();
        String unit = massageFacultyUnit.getUnit();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyUnitData(name, unit);
    }
    public String[] facultyStageLessons(MassageFacultyUnit massageFacultyUnit) {
        String name = massageFacultyUnit.getName();
        String unit = massageFacultyUnit.getUnit();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFacultyStageData(name, unit);
    }
    public Boolean isChosen (MassageUserDesk massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.isChosenBefore(name);
    }
    public void valueChanger (MassageUserDesk massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.change(name);
    }

    public void promotion (MassageUserDesk massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.promotion(name);
    }
    public void relegation (MassageUserDesk massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.relegation(name);
    }
    public void deletingTeacher(MassageUserDesk massageUserDesk){
        String name = massageUserDesk.getName();
        LessonListController lessonListController = new LessonListController();
        lessonListController.TeacherDeleting(name);
    }
    public String userFaculty(MassageUserDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getFaculty(name);
    }

    public String selectedUserFaculty(MassageUserDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        LessonListController lessonListController = new LessonListController();
        return lessonListController.getSelectedFaculty(name);
    }










}
