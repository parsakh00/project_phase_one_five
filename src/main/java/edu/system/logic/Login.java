package edu.system.logic;

public class Login {
    boolean isNameValid;
    boolean isPassValid;
    String name_type;
    String userType;

    public boolean checkName(String name){
        name_type = name;
        //ToDo
        return isNameValid;
    }
    public boolean checkPass(String pass,String name){
        //ToDo
        return isPassValid;
    }
    public String userType(){
        if (isNameValid && isPassValid){
            //ToDo
            return userType;
        }
        else return null;
    }


}
