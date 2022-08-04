package edu.system.logic;

import java.util.ArrayList;

public class MassageUserDesk {
    String name;

    ArrayList<String> objections;

    String score;

    public MassageUserDesk(String name) {
        this.name = name;
    }

    public MassageUserDesk(String name, String score) {
        this.name = name;
        this.score = score;
    }

    public MassageUserDesk(String name, ArrayList<String> objections) {
        this.name = name;
        this.objections = objections;
    }

    public void setObjections(ArrayList<String> objections) {
        this.objections = objections;
    }

    public void setName(String name) {
        this.name = name;
    }
    public ArrayList<String> getObjections() {
        return objections;
    }

    public String getName() {
        return name;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }
}
