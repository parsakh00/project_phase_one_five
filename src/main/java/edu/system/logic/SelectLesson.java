package edu.system.logic;

public class SelectLesson {

    String lesson;
    String faculty;
    String time;
    String teacher;
    String stage;
    String id;
    Boolean isPresent;
    String unity;

    private static SelectLesson selectlesson;
    public static SelectLesson getInstance(){
        if (selectlesson == null){
            selectlesson = new SelectLesson();

        }
        return selectlesson;
    }

    public void setLesson(String user) {
        this.lesson = user;
    }

    public String getLesson() {
        return lesson;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
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

    public String getUnity() {
        return unity;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPresent(Boolean present) {
        isPresent = present;
    }

    public void setUnity(String unity) {
        this.unity = unity;
    }
}

