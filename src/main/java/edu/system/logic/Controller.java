package edu.system.logic;

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



    public boolean login(Massage massage){
        String name = massage.getName();
        String pass = massage.getPass();
        Login login2 = new Login();
        return (login2.checkName(name) && login2.checkPass(pass, name));


    }



}
