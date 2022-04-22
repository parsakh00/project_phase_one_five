package edu.system.logic;

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



    public boolean login(MassageLogin massage){
        String name = massage.getName();
        String pass = massage.getPass();
        LoginController login2 = new LoginController();
        return (login2.checkName(name) && login2.checkPass(pass, name));
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








}
