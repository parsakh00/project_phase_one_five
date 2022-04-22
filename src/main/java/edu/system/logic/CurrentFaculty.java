package edu.system.logic;

public class CurrentFaculty {

    String faculty;

    private static CurrentFaculty currentFaculty;
    public static CurrentFaculty getInstance(){
        if (currentFaculty == null){
            currentFaculty = new CurrentFaculty();

        }
        return currentFaculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }


    public String getFaculty() {
        return faculty;
    }
}
