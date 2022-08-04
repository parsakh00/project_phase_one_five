package edu.system.individual;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class notStudentIndividual {

    private String userName;
    private int password;
    private String type;
    private String email;
    private String roomNo;
    private String degree;
    private String phone;
    private String lesson;
    private String faculty;
    private String phoneNumber;
    private String masterDegree;
    private String teacherNumber;
    private String id;

    public notStudentIndividual(String userName, int password, String type, String email, String roomNo, String degree, String phone, String lesson, String faculty, String phoneNumber, String masterDegree, String teacherNumber, String id) {
        this.userName = userName;
        this.password = password;
        this.type = type;
        this.email = email;
        this.roomNo = roomNo;
        this.degree = degree;
        this.phone = phone;
        this.lesson = lesson;
        this.faculty = faculty;
        this.phoneNumber = phoneNumber;
        this.masterDegree = masterDegree;
        this.teacherNumber = teacherNumber;
        this.id = id;
    }

    public String getUserName() {
        return userName;
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

    public String getRoomNo() {
        return roomNo;
    }

    public String getDegree() {
        return degree;
    }

    public String getPhone() {
        return phone;
    }

    public String getLesson() {
        return lesson;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMasterDegree() {
        return masterDegree;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public String getId() {
        return id;
    }

    public static studentIndividual jsonToMessage(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, studentIndividual.class);
    }

    public String toJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this);
    }
}
