package edu.system.logic;

public class MassageSignUp {

    String username;
    String password;
    String email;
    String phone;
    String roomNo;

    String faculty;


    public MassageSignUp(String username, String password, String email, String phone, String roomNo, String faculty) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roomNo = roomNo;
        this.faculty = faculty;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
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

    public void setRoomNo(String roomNo) {
        this.roomNo = roomNo;
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
}
