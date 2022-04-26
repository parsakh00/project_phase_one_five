package edu.system.logic;

public class MassageSignUp {

    String username;
    String password;
    String email;
    String phone;
    String roomNo;

    String faculty;
    String masterDegree;
    String id;
    String supervisor;
    String enteringYear;
    String condition;
    String degree;


    public MassageSignUp(String username, String password, String email, String phone, String roomNo, String faculty, String masterDegree, String id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.roomNo = roomNo;
        this.faculty = faculty;
        this.masterDegree = masterDegree;
        this.id = id;
    }
    public MassageSignUp(String username,String id, String phone,String supervisor,String faculty,
    String enteringYear, String condition,String password,String email,String degree
    ){
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

    public void setEnteringYear(String enteringYear) {
        this.enteringYear = enteringYear;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public void setMasterDegree(String masterDegree) {
        this.masterDegree = masterDegree;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMasterDegree() {
        return masterDegree;
    }

    public String getId() {
        return id;
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
