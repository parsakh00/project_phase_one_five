package edu.system.currentUser;


import edu.system.logic.Logic;
import edu.system.logic.Logic;
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

    public static CurrentUser getInstance() {
        if (currentuser == null) {
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
        this.type = Logic.getType(userName);
        this.degree = Logic.getDegree(userName);
        this.faculty = Logic.getFaculty(userName);
        this.phoneNumber = Logic.getUserPhoneNumber(userName);
        this.email = Logic.getEmail(userName);
        this.id = Logic.getId(userName);

        if (Logic.isStudent(getUserName())) {
            this.supervisor = Logic.getSupervisor(userName);
            this.enteringYear = Logic.getEnteringYear(userName);
            this.condition = Logic.getCondition(userName);
        } else {
            this.phone = Logic.getPhone(userName);
            this.roomNo = Logic.getRoomNo(userName);
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
