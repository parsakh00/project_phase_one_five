package edu.system.currentUser;


import java.util.ArrayList;

public class CurrentUser {

    String user;
    int timer;

    private static CurrentUser currentuser;
    public static CurrentUser getInstance(){
        if (currentuser == null){
            currentuser = new CurrentUser();

        }
        return currentuser;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getUser() {
        return user;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
    }
}
