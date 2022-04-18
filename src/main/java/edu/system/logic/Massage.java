package edu.system.logic;

public class Massage {
    String name;
    String pass;


    public enum Login{
        LOGIN
    }

    public Massage(String name,String pass){
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String getPass() {
        return pass;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPass(String pass) {

        this.pass = pass;
    }
}
