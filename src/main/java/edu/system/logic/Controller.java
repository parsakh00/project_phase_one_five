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
        Login login2 = new Login();
        return (login2.checkName(name) && login2.checkPass(pass, name));
    }

    public String studentDeskEmail(MassageStudentDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        studentController studentController = new studentController();
        return studentController.getEmail(name);

    }

    public String studentDeskUserName(MassageStudentDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        studentController studentController = new studentController();
        return studentController.getUserName(name);
    }

    public String studentDeskType(MassageStudentDesk massageStudentDesk) throws IOException, ParseException {
        String name = massageStudentDesk.getName();
        studentController studentController = new studentController();
        return studentController.getType(name);

    }



}
