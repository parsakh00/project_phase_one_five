package edu.system.logic;

public class UserEdit {

    String username;
    Long password;
    String type;
    String email;
    String degree;
    String roomNo;
    String phone;
    Boolean isEducationalAssistantSet;
    String faculty;

    public UserEdit(String username, Long password, String type, String email, String degree, String roomNo, String phone, Boolean isEducationalAssistantSet, String faculty) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
        this.degree = degree;
        this.roomNo = roomNo;
        this.phone = phone;
        this.isEducationalAssistantSet = isEducationalAssistantSet;
        this.faculty = faculty;
    }
    public UserEdit(String username){
        this.username = username;
    }
}
