package edu.system.logic;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Modifier;
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

    String UserPut;
    String userFaculty;
    String selectedUserFaculty;
    String courseTeacher;
    String lessonTarget;
    String lessonTargetEdit;
    String lessonTimeEdit;
    String lessonTeacherEdit;
    public void removeLesson(String lesson, String faculty){
        this.lessonTarget = lesson;
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json"));
            JSONArray courses = (JSONArray) obj;


            courses.forEach(lessonSelect -> findingLesson( (JSONObject) lessonSelect ) );
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json";

            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(courses);
                out.write(json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(String lesson, String faculty, String time, String teacher){
        this.lessonTargetEdit = lesson;
        this.lessonTimeEdit = time;
        this.lessonTeacherEdit = teacher;
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json"));
            JSONArray coursesEdit = (JSONArray) obj;


            coursesEdit.forEach(lessonSelect -> findingLessonEdit( (JSONObject) lessonSelect ) );
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json";

            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(coursesEdit);
                out.write(json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    private void findingLessonEdit(JSONObject lessons) {
        if (Objects.equals((String) lessons.get("lesson"), lessonTargetEdit)) {
            if(lessonTeacherEdit != null ){
                lessons.put("teacher", lessonTeacherEdit);
            }
            if(lessonTimeEdit != null){
                lessons.put("time", lessonTimeEdit);
            }
        }
    }
    public void add(String lesson, String faculty, String time, String teacher, String unity,String stage,String id,Boolean isPresent){


        JSONParser parser = new JSONParser();
        try {
            JSONObject eachLesson = new JSONObject();
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json"));
            JSONArray coursesAdd = (JSONArray) obj;
            //ToDo add list to array
            eachLesson.put("lesson",lesson);
            eachLesson.put("unity",unity);
            eachLesson.put("stage",stage);
            eachLesson.put("id",id);
            eachLesson.put("teacher",teacher);
            eachLesson.put("isPresent",isPresent);
            eachLesson.put("faculty",faculty);
            eachLesson.put("time",time);
            coursesAdd.add(eachLesson);

            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json";
            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(coursesAdd);
                out.write(json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
    private void findingLesson(JSONObject lessons) {
        if (Objects.equals((String) lessons.get("lesson"), lessonTarget)) {
            lessons.clear();
        }
    }
    public void TeacherDeleting(String name){
        File file =  new File(System.getProperty("user.dir"),"/" + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json");
        file.delete();

    }
    public String getFaculty(String name){
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            userFaculty =  (String) user.get("faculty");


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return userFaculty;
    }
    public String  getSelectedFaculty(String name){
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            userFaculty =  (String) user.get("faculty");


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return userFaculty;
    }
    public Boolean isChosenBefore(String name){
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            isChoose = (Boolean) user.get("isEducationalAssistantSet");


        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return isChoose;
    }
    public void change(String name) {
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            if (!((Boolean) user.get("isEducationalAssistantSet"))) {
                user.put("isEducationalAssistantSet", true);

            }
            else{

                user.put("isEducationalAssistantSet", false);

            }
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json";

            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(user);
                out.write(json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void relegation(String name){
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            user.put("degree", "");
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json";

            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(user);
                out.write(json);

            }
            catch (Exception e){
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void promotion(String name){
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            user.put("degree", "education assistant");
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json";

            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(user);
                out.write(json);

            }
            catch (Exception e){
                e.printStackTrace();
            }


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
