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



}
