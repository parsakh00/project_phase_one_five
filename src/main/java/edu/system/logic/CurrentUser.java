package edu.system.logic;


public class CurrentUser {

    String user;
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


}
