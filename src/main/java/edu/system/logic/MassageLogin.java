package edu.system.logic;

public class MassageLogin {
    String name;
    String pass;
    String time;
    String teacher;
    String unity;
    String stage;
    String id;

    Boolean isPresent;

    public MassageLogin(String name){
        this.name = name;
    }

    public MassageLogin(String name, String pass){
        this.name = name;
        this.pass = pass;
    }
    public MassageLogin(String name, String pass, String id){
        this.name = name;
        this.pass = pass;
        this.id = id;
    }
    public MassageLogin(String name, String pass, String time, String teacher) {
        this.name = name;
        this.pass = pass;
        this.time = time;
        this.teacher = teacher;
    }
    public MassageLogin(String name, String pass, String time, String teacher, String id) {
        this.name = name;
        this.pass = pass;
        this.time = time;
        this.teacher = teacher;
        this.id = id;
    }

    public MassageLogin(String name, String pass, String time, String teacher, String unity, String stage, String id, Boolean isPresent) {
        this.name = name;
        this.pass = pass;
        this.time = time;
        this.teacher = teacher;
        this.unity = unity;
        this.stage = stage;
        this.id = id;
        this.isPresent = isPresent;
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

    public String getTime() {
        return time;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getUnity() {
        return unity;
    }

    public String getStage() {
        return stage;
    }

    public String getId() {
        return id;
    }

    public Boolean getPresent() {
        return isPresent;
    }
}
