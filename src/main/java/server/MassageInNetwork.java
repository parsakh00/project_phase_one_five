package server;

import java.util.ArrayList;

public class MassageInNetwork {
    String name;
    String pass;
    String time;
    String teacher;
    String unity;
    String stage;
    String id;
    Boolean isPresent;
    String unit;
    String faculty;
    String username;
    String password;
    String email;
    String phone;
    String roomNo;
    String masterDegree;
    String supervisor;
    String enteringYear;
    String condition;
    String degree;
    ArrayList<String> objections;
    String score;

    public MassageInNetwork(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public MassageInNetwork(String name, ArrayList<String> objections) {
        this.name = name;
        this.objections = objections;
    }

    public MassageInNetwork(String name, String unit, String faculty) {
        this.name = name;
        this.unit = unit;
        this.faculty = faculty;
    }

    public MassageInNetwork(String name, String score, String unit, String time) {
        this.name = name;
        this.score = score;
        this.unit = unit;
        this.time = time;
    }

    public MassageInNetwork(String username, String password, String email, String phone, String roomNo, String faculty, String masterDegree, String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roomNo = roomNo;
        this.faculty = faculty;
        this.masterDegree = masterDegree;
        this.id = id;
    }

    public MassageInNetwork(String name, String pass, String time, String teacher, String unity, String stage, String id, Boolean isPresent, String unit, String faculty) {
        this.name = name;
        this.pass = pass;
        this.time = time;
        this.teacher = teacher;
        this.unity = unity;
        this.stage = stage;
        this.id = id;
        this.isPresent = isPresent;
        this.unit = unit;
        this.faculty = faculty;
    }

    public MassageInNetwork(String username, String id, String phone, String supervisor, String faculty,
                            String enteringYear, String condition, String password, String email, String degree, String time) {
        this.username = username;
        this.id = id;
        this.phone = phone;
        this.supervisor = supervisor;
        this.faculty = faculty;
        this.enteringYear = enteringYear;
        this.condition = condition;
        this.password = password;
        this.degree = degree;
        this.email = email;
        this.time = time;
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

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getUnit() {
        return unit;
    }

    public void setSupervisor(String supervisor) {
        this.supervisor = supervisor;
    }

    public String getSupervisor() {
        return supervisor;
    }

    public String getEnteringYear() {
        return enteringYear;
    }

    public String getCondition() {
        return condition;
    }

    public String getDegree() {
        return degree;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterDegree() {
        return masterDegree;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public ArrayList<String> getObjections() {
        return objections;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getScore() {
        return score;
    }
}
