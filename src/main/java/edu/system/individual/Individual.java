package edu.system.individual;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Individual {

    private String username;
    private String id;
    private String phoneNumber;
    private String totalScore;
    private String faculty;
    private String supervisor;
    private String enteringYear;
    private String condition;
    private int password;
    private String type;
    private String email;
    private String degree;
    private String studentNumber;
    private String chemistry;
    private String mechanicEng;
    private String electricalEng;
    private String mathSci;
    private String physics;
    private String masterDegree;
    private String lesson;
    private String teacherNumber;
    private String roomNo;


    public Individual(String username, int password){
        this.username = username;
        this.password = password;
    }

    public Individual(String username, String id, String phoneNumber, String totalScore, String faculty, String supervisor, String enteringYear, String condition, int password, String type, String email, String degree, String studentNumber, String chemistry, String mechanicEng, String electricalEng, String mathSci, String physics, String masterDegree, String lesson, String teacherNumber, String roomNo) {
        this.username = username;
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.totalScore = totalScore;
        this.faculty = faculty;
        this.supervisor = supervisor;
        this.enteringYear = enteringYear;
        this.condition = condition;
        this.password = password;
        this.type = type;
        this.email = email;
        this.degree = degree;
        this.studentNumber = studentNumber;
        this.chemistry = chemistry;
        this.mechanicEng = mechanicEng;
        this.electricalEng = electricalEng;
        this.mathSci = mathSci;
        this.physics = physics;
        this.masterDegree = masterDegree;
        this.lesson = lesson;
        this.teacherNumber = teacherNumber;
        this.roomNo = roomNo;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getTotalScore() {
        return totalScore;
    }

    public String getFaculty() {
        return faculty;
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

    public int getPassword() {
        return password;
    }

    public String getType() {
        return type;
    }

    public String getEmail() {
        return email;
    }

    public String getDegree() {
        return degree;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public String getChemistry() {
        return chemistry;
    }

    public String getMechanicEng() {
        return mechanicEng;
    }

    public String getElectricalEng() {
        return electricalEng;
    }

    public String getMathSci() {
        return mathSci;
    }

    public String getPhysics() {
        return physics;
    }

    public String getMasterDegree() {
        return masterDegree;
    }

    public String getLesson() {
        return lesson;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public String getRoomNo() {
        return roomNo;
    }

    public static Individual jsonToMessage(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Individual.class);
    }

    public String toJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
