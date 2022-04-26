package edu.system.logic;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.*;

public class LessonListController {
    String[] facultyData;
    String eachLesson;

    String eachTeacher;

    String[] TeacherData;

    String Unt;
    Boolean isChoose;

    String UserPut;
    String userFaculty;
    String[] userLessonData;
    String lessonTarget;
    String lessonTargetEdit;
    String lessonTimeEdit;
    String lessonTeacherEdit;
    String idOfLesson;
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
    public String[] nameLists(String name) throws IOException, ParseException {
        String tmp = "/";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                tmp += element + "/";
            }

        }
        return tmp.split("/");
    }
    public void rejectMinor(String name, String faculty) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;
        JSONObject user2 = (JSONObject)user.get(name);
        user2.put(faculty,"0");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(user2);
            out.write(json);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public void acceptMinor(String name, String faculty) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;
        JSONObject user2 = (JSONObject)user.get(name);
        user2.put(faculty,"1");
        System.out.println(user2.get(faculty));
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(user2);
            out.write(json);

        }
        catch (Exception e){
            e.printStackTrace();
        }


    }

    public String[] nameListsMinor(String name, String faculty) throws IOException, ParseException {
        String tmp = "/";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;

        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());

            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);
                List<String> namess = new ArrayList<String>(user2.keySet());
                for (String elements : namess){
                    System.out.println(elements);
                    if (Objects.equals((String) elements, (String) faculty)){
                        tmp += element;
                    }
                }
            }
        }
        System.out.println(tmp);
        return tmp.split("/");
    }
    public String[] nameListsRecommend(String name) throws IOException, ParseException {
        String tmp = "/";

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;

        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());

            for (String element : names) {
                if (Objects.equals((String) element, (String) name)) {
                    JSONObject user2 = (JSONObject)user.get(name);

                    tmp += ((String) user2.get("username")) + "/";
                    System.out.println(tmp);

                }
            }

        }
        return tmp.split("/");
    }

    public String[] nameOfLesson(String name) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject user = (JSONObject) obj;
        String facultyOfLesson;
        JSONObject getId = (JSONObject) user.get("lessons");
        for(int i = 0 ; i < 5; i++) {
            if (i == 0) facultyOfLesson = "Chemistry";
            else if (i == 1) facultyOfLesson = "MechanicEng";
            else if (i == 2) facultyOfLesson = "ElectricalEng";
            else if (i == 3) facultyOfLesson = "MathSci";
            else facultyOfLesson = "Physics";
            idOfLesson = (String) getId.get(facultyOfLesson);
            if (!Objects.equals((String)idOfLesson, null)) {

                JSONParser parser2 = new JSONParser();

                try {
                    //Read JSON file
                    Object obj2 = parser2.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + facultyOfLesson + ".json"));
                    JSONArray faculty = (JSONArray) obj2;


                    faculty.forEach(fac -> eachLessonName((JSONObject) fac));

                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (eachLesson != null) userLessonData = eachLesson.split("/");
        else userLessonData = tmp.split("/");
        return userLessonData;

    }
    private void eachLessonName(JSONObject facultyData) {

        if (Objects.equals((String) idOfLesson, (String) facultyData.get("id"))) {
            String lesson = (String) facultyData.get("lesson");
            String time = (String) facultyData.get("time");
            String day = (String) facultyData.get("day");
            String exam = (String) facultyData.get("exam");
            eachLesson = (String) eachLesson +"/" + lesson;
        }
        //else if (eachLesson == null) eachLesson = "/" ;
    }
    public String[] examOfLesson(String name) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject user = (JSONObject) obj;
        String facultyOfLesson;
        JSONObject getId = (JSONObject) user.get("lessons");
        for(int i = 0 ; i < 5; i++) {
            if (i == 0) facultyOfLesson = "Chemistry";
            else if (i == 1) facultyOfLesson = "MechanicEng";
            else if (i == 2) facultyOfLesson = "ElectricalEng";
            else if (i == 3) facultyOfLesson = "MathSci";
            else facultyOfLesson = "Physics";
            idOfLesson = (String) getId.get(facultyOfLesson);
            if (!Objects.equals((String)idOfLesson, null)) {

                JSONParser parser2 = new JSONParser();

                try {
                    //Read JSON file
                    Object obj2 = parser2.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + facultyOfLesson + ".json"));
                    JSONArray faculty = (JSONArray) obj2;


                    faculty.forEach(fac -> eachLessonExam((JSONObject) fac));

                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (eachLesson != null) userLessonData = eachLesson.split("/");
        else userLessonData = tmp.split("/");
        return userLessonData;

    }
    private void eachLessonExam(JSONObject facultyData) {

        if (Objects.equals((String) idOfLesson, (String) facultyData.get("id"))) {
            String lesson = (String) facultyData.get("lesson");
            String time = (String) facultyData.get("time");
            String day = (String) facultyData.get("day");
            String exam = (String) facultyData.get("exam");
            eachLesson = (String) eachLesson + "/" + exam;
        }
        //else if (eachLesson == null) eachLesson = "/" ;
    }
    public String[] lessonsOfUser(String name) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject user = (JSONObject) obj;
        String facultyOfLesson;
        JSONObject getId = (JSONObject) user.get("lessons");
        for(int i = 0 ; i < 5; i++) {
            if (i == 0) facultyOfLesson = "Chemistry";
            else if (i == 1) facultyOfLesson = "MechanicEng";
            else if (i == 2) facultyOfLesson = "ElectricalEng";
            else if (i == 3) facultyOfLesson = "MathSci";
            else facultyOfLesson = "Physics";
            idOfLesson = (String) getId.get(facultyOfLesson);
            if (!Objects.equals((String)idOfLesson, null)) {

                JSONParser parser2 = new JSONParser();

                try {
                    //Read JSON file
                    Object obj2 = parser2.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + facultyOfLesson + ".json"));
                    JSONArray faculty = (JSONArray) obj2;


                    faculty.forEach(fac -> eachLessonOfUser((JSONObject) fac));

                } catch (ParseException | IOException e) {
                    e.printStackTrace();
                }
            }
        }
        if (eachLesson != null) userLessonData = eachLesson.split("/");
        else userLessonData = tmp.split("/");
        return userLessonData;
    }
    private void eachLessonOfUser(JSONObject facultyData) {

        if (Objects.equals((String) idOfLesson, (String) facultyData.get("id"))) {
            String lesson = (String) facultyData.get("lesson");
            String time = (String) facultyData.get("time");
            String day = (String) facultyData.get("day");
            String exam = (String) facultyData.get("exam");
            eachLesson = (String) eachLesson +"/" + lesson + "/"+ time + "/"+ day + "/"+ exam;
        }
        //else if (eachLesson == null) eachLesson = "/" ;
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
