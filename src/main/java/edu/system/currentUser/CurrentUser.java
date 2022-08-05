package edu.system.currentUser;


import edu.system.logic.userController;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class CurrentUser {

    String userName;
    String id;
    String phoneNumber;
    String totalScore;
    String faculty;
    String supervisor;
    String enteringYear;
    String condition;
    String type;
    String email;
    String degree;
    String studentNumber;
    String masterDegree;
    String lesson;
    String teacherNumber;
    String phone;
    String roomNo;

    int timer;
    private static CurrentUser currentuser;
    public static CurrentUser getInstance(){
        if (currentuser == null){
            currentuser = new CurrentUser();

        }
        return currentuser;
    }

    public void setUser(String userName) throws IOException, ParseException {
        this.userName = userName;
        setData();
    }

    public void setData() throws IOException, ParseException {
        //ToDo set data from json files for user
        this.type = userController.getType(userName);
        this.degree = userController.getDegree(userName);
        this.faculty = userController.getFaculty(userName);
        this.phoneNumber = userController.getUserPhoneNumber(userName);
        this.email = userController.getEmail(userName);
        this.id = userController.getId(userName);

        if(userController.isStudent(getUserName())){
            this.supervisor = userController.getSupervisor(userName);
            this.enteringYear = userController.getEnteringYear(userName);
            this.condition = userController.getCondition(userName);
        }
        else{
            this.phone = userController.getPhone(userName);
            this.roomNo = userController.getRoomNo(userName);
        }
    }
    public String getUserName() {
        return userName;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public int getTimer() {
        return timer;
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

    public String getMasterDegree() {
        return masterDegree;
    }

    public String getLesson() {
        return lesson;
    }

    public String getTeacherNumber() {
        return teacherNumber;
    }

    public String getPhone() {
        return phone;
    }

    public String getRoomNo() {
        return roomNo;
    }
}
