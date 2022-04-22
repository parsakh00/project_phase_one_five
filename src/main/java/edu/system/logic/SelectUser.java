package edu.system.logic;

public class SelectUser {

    String user;
    private static SelectUser selectUser;
    public static SelectUser getInstance(){
        if (selectUser == null){
            selectUser = new SelectUser();

        }
        return selectUser;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

}
