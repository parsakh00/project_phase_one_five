package edu.system.logic;

public class MassageFacultyUnit {
    String name;
    String unit;

    String faculty;

    public MassageFacultyUnit(String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    public MassageFacultyUnit(String name, String unit, String faculty) {
        this.name = name;
        this.unit = unit;
        this.faculty = faculty;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFaculty() {
        return faculty;
    }

    public String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }
}
