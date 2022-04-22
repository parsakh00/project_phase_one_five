package edu.system.logic;

public class CurrentFacultyUnit {
    String faculty;

    String unit;

    private static CurrentFacultyUnit currentFacultyunit;
    public static CurrentFacultyUnit getInstance(){
        if (currentFacultyunit == null){
            currentFacultyunit = new CurrentFacultyUnit();

        }
        return currentFacultyunit;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }


    public String getFaculty() {
        return faculty;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getUnit() {
        return unit;
    }
}
