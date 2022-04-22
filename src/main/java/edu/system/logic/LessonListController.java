package edu.system.logic;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class LessonListController {
    String[] facultyData;
    String eachLesson;

    String eachTeacher;

    String[] TeacherData;

    String Unt;
    Boolean isChoose;



    public Boolean isChosenBefore(String name){
        JSONParser parser = new JSONParser();
        try {
            System.out.println("toshe");
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            isChoose = (Boolean) user.get("isEducationalAssistantSet");
            System.out.println("ischosen : " + isChoose);

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return isChoose;
    }

    public void change(String name){
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            if (!((Boolean) user.get("isEducationalAssistantSet"))) {
                user.put("isEducationalAssistantSet", true);

            }
            else{
                System.out.println("true");
                user.put("isEducationalAssistantSet", false);
                user.toJSONString();

            }


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void relegation(String name){
        JSONParser parser = new JSONParser();
        try {
            System.out.println("relegation");
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            user.put("degree", "");

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void promotion(String name){
        JSONParser parser = new JSONParser();
        System.out.println("promotion");
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            user.put("degree", "education assistant");


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public String[] getFacultyData(String name) {
        JSONParser parser = new JSONParser();
        String tmp = "/";

        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;


            faculty.forEach(fac -> eachLessonProperties( (JSONObject) fac ) );

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        if (eachLesson != null) facultyData = eachLesson.split("/");
        else facultyData = tmp.split("/");

        return facultyData;
    }
    public String[] getFacultyUnitData(String name, String unit){
        JSONParser parser = new JSONParser();
        this.Unt = unit;
        String tmp = "/";

        try {

            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;


            faculty.forEach(fac -> eachLessonUnitProperties( (JSONObject) fac ) );

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        if (eachLesson != null) facultyData = eachLesson.split("/");
        else facultyData = tmp.split("/");

        return facultyData;
    }

    private void eachLessonProperties(JSONObject facultyData) {


        if ((Boolean) facultyData.get("isPresent")) {
            String lesson = (String) facultyData.get("lesson");
            String unity = (String) facultyData.get("unity");
            String stage = (String) facultyData.get("stage");
            String id = (String) facultyData.get("id");
            String teacher = (String) facultyData.get("teacher");

            eachLesson = eachLesson +"/" + stage + "/"+ teacher + "/"+ lesson + "/"+ unity + "/"+ id;
        }
        //else if (eachLesson == null) eachLesson = "/" ;
    }
    private void eachLessonUnitProperties(JSONObject facultyData) {

        if ((Boolean) facultyData.get("isPresent")) {
            if (Objects.equals(Unt, (String) facultyData.get("unity"))) {
                String lesson = (String) facultyData.get("lesson");
                String unity = (String) facultyData.get("unity");
                String stage = (String) facultyData.get("stage");
                String id = (String) facultyData.get("id");
                String teacher = (String) facultyData.get("teacher");
                eachLesson = eachLesson + "/" + stage + "/" + teacher + "/" + lesson + "/" + unity + "/" + id;
                System.out.println(eachLesson);
            }
            //else if (eachLesson == null) eachLesson = "/" ;
        }
    }

    public String[] getFacultyStageData(String name, String unit) {
        JSONParser parser = new JSONParser();
        this.Unt = unit;
        String tmp = "/";

        try {

            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;


            faculty.forEach(fac -> eachLessonStageProperties( (JSONObject) fac ) );

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        if (eachLesson != null) facultyData = eachLesson.split("/");
        else facultyData = tmp.split("/");

        return facultyData;
    }
    private void eachLessonStageProperties(JSONObject facultyData) {

        if ((Boolean) facultyData.get("isPresent")) {
            if (Objects.equals(Unt, (String) facultyData.get("stage"))) {
                String lesson = (String) facultyData.get("lesson");
                String unity = (String) facultyData.get("unity");
                String stage = (String) facultyData.get("stage");
                String id = (String) facultyData.get("id");
                String teacher = (String) facultyData.get("teacher");
                eachLesson = eachLesson + "/" + stage + "/" + teacher + "/" + lesson + "/" + unity + "/" + id;
            }
            //else if (eachLesson == null) eachLesson = "/" ;
        }
    }

    public String[] getFacultyTeachers(String name){
        //ToDo

        JSONParser parser = new JSONParser();
        String tmp = "/";

        try {

            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;


            faculty.forEach(fac -> eachFacultyTeacherProperties( (JSONObject) fac ) );

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        if (eachTeacher != null) TeacherData = eachTeacher.split("/");
        else TeacherData = tmp.split("/");

        return TeacherData;
    }
    private void eachFacultyTeacherProperties(JSONObject facultyData) {
        String teacherName = (String) facultyData.get("teacher");
        JSONParser parser = new JSONParser();
        try {

            File file =  new File(System.getProperty("user.dir"),"/" + "\\src\\main\\java\\edu\\system\\userdata\\" + teacherName + ".json");
            if (file.exists()) {
                //Read JSON file
                Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + teacherName + ".json"));
                JSONObject teacher = (JSONObject) obj;

                String username = (String) teacher.get("username");
                String email = (String) teacher.get("email");
                String roomNo = (String) teacher.get("room No.");
                String phone = (String) teacher.get("phone");
                eachTeacher = eachTeacher + "/" + roomNo + "/" + phone + "/" + email + "/" + username;
            }

        }catch (ParseException | IOException e) {
            e.printStackTrace();
        }



        }

}
