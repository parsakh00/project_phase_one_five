package edu.system.logic;

import com.google.gson.Gson;
import edu.system.HelloApplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Objects;

public class LoginController {
    String courseTeacher;
    boolean isNameValid;
    boolean isPassValid;
    boolean isPassLogOutChange;
    String name;

    static Logger log = LogManager.getLogger(HelloApplication.class);


    public void editingPassWord(String username, String password){
        log.info(" open and rewrite password");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json"));
            JSONObject jsonObject = (JSONObject)obj;
            jsonObject.put("password", (Long)passHash(password));
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json";
            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            }
            catch (Exception e){
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch(Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }
    public Boolean editPassLogOut(String username, String newPassword, String oldPassword){
        log.info("Read and rewrite file to edit password");
        JSONParser parser = new JSONParser();
        boolean isAble = false;
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json"));
            JSONObject jsonObject = (JSONObject)obj;
            if (Objects.equals((Long) jsonObject.get("password"), (Long) passHash(oldPassword))) {
                jsonObject.put("password", (Long) passHash(newPassword));
                String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json";
                try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                    Gson gson = new Gson();
                    String json = gson.toJson(jsonObject);
                    out.write(json);
                } catch (Exception e) {
                    log.error("exception happened", e);
                    e.printStackTrace();
                }
                isAble = true;
            }
        } catch(Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }

        return isAble;

    }
    public void signUpUser(String user,String id, String phone,String supervisor,String faculty, String enteringYear,
                           String condition,String pass, String email,String degree){
        log.info("Sign Up new student");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("id", id);
        jsonObject.put("phone", phone);
        jsonObject.put("total score","");
        jsonObject.put("faculty", faculty);
        jsonObject.put("supervisor", supervisor);
        jsonObject.put("entering year", enteringYear);
        jsonObject.put("condition", condition);
        jsonObject.put("password", passHash(pass));
        jsonObject.put("type","student");
        jsonObject.put("email", email);
        jsonObject.put("degree",degree);
        jsonObject.put("student number", pass);
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + user + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void signUpTeachers(String user,String id, String phone,String supervisor,String faculty, String enteringYear,
                           String condition,String pass, String email,String degree){
        log.info("Sign Up nre teacher");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("id", id);
        jsonObject.put("phone number", phone);
        jsonObject.put("faculty", faculty);
        jsonObject.put("room No.", supervisor);
        jsonObject.put("phone", enteringYear);
        jsonObject.put("master degree", condition);
        jsonObject.put("password", passHash(pass));
        jsonObject.put("type","teacher");
        jsonObject.put("email", email);
        jsonObject.put("degree",degree);
        jsonObject.put("teacher number", pass);
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + user + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void getRecommend(String teacherName, String lesson, String score, String ta,String userName) throws IOException, ParseException {
        log.info("write new recommendation request file for user");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject2.put("condition", "2");
        jsonObject2.put("username", userName);
        jsonObject2.put("lessons", lesson);
        jsonObject2.put("scores", score);
        jsonObject2.put("TA",ta);
        jsonObject2.put("teacherName",teacherName);
        jsonObject.put(teacherName, jsonObject2);


        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        }
        catch (Exception e){
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }
    public void userMinorRequest(String name, String faculty1,String faculty2) throws IOException, ParseException {
        log.info("Open and rewrite minor file acceptation or rejection condition");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json"));
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject2.put(faculty1, "2");
        jsonObject2.put(faculty2, "2");
        jsonObject.put(name, jsonObject2);


        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        }
        catch (Exception e){
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void addingTeacherUser(String user,String pass ,String email,String room,String phone, String faculty, String masterDegree, String id){
        log.info("write new user file");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("password", passHash(pass));
        jsonObject.put("email", email);
        jsonObject.put("room No.", room);
        jsonObject.put("phone", phone);
        jsonObject.put("faculty", faculty);
        jsonObject.put("type", "teacher");
        jsonObject.put("degree", "");
        jsonObject.put("lesson", "");
        jsonObject.put("master degree", masterDegree);
        jsonObject.put("id", id);
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + user + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }

    }

    public void emailEditProfile(String username, String email){
        log.info("Read and rewrite userdata file");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json"));
            JSONObject jsonObject = (JSONObject)obj;
            jsonObject.put("email", (String)email);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json";
            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            }
            catch (Exception e){
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch(Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void passEditProfile(String username, String phone){
        log.info("Read and rewrite userdata file to edit password of user");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json"));
            JSONObject jsonObject = (JSONObject)obj;
            jsonObject.put("phone number", (String)phone);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json";
            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            }
            catch (Exception e){
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void editingEmail(String username, String email){
        log.info("Open and rewrite email");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json"));
            JSONObject jsonObject = (JSONObject)obj;
            jsonObject.put("email", (String)email);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + username + ".json";
            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            }
            catch (Exception e){
                log.error("exception happened",e);
                e.printStackTrace();
            }
        } catch(Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }
    public void deleteCourseTeacher(String name,String faculty){
        log.info("Open and rewrite for delete course teacher");
        this.courseTeacher = name;
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json"));
            JSONArray facultyy = (JSONArray) obj;


            facultyy.forEach(fac -> removeCourseProperty( (JSONObject) fac ) );

            String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\unidatas\\" + faculty + ".json";
            try(PrintWriter out = new PrintWriter(new FileWriter(path))){
                Gson gson = new Gson();
                String json = gson.toJson(facultyy);
                out.write(json);

            }
            catch (Exception e){
                log.error("exception happened", e);
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }
    private void removeCourseProperty(JSONObject facultyData) {


        if (Objects.equals((String) facultyData.get("teacher"), courseTeacher)) {
            facultyData.put("teacher", null);

        }
        //else if (eachLesson == null) eachLesson = "/" ;
    }

    public boolean checkName(String name){
        log.info("Read name of user from userdata file");
        File file =  new File(System.getProperty("user.dir"),"/" + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json");
        isNameValid = file.exists();
        return isNameValid;
    }
    public boolean checkPass(String pass,String name){
        log.info("Read password of user from userdata file");
        if (isNameValid){
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
                JSONObject jsonObject = (JSONObject)obj;
                log.info("check equality of hash of pass that entered by pass that user field in password field");
                if(passHash(pass) == (long)jsonObject.get("password")){

                    isPassValid = true;
                }
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
        if (isPassValid) this.name = name;
        return isPassValid;
    }
    private long passHash(String str){
        long res=0;
        long tavan=1;
        long mod= 1000000007;
        long prime=373;
        for (int i=0;i<str.length();i++){
            int ascii=str.charAt(i);
            tavan=(tavan*prime)%mod;
            res=(res+(tavan*ascii)%mod)%mod;
        }
        return res;
    }



//    public void deserializeUser(){
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("C:\\Users\\asus\\IdeaProjects\\project_phase_one_five\\src\\main\\java\\edu\\system\\userdata\\user10.json"));
//            JSONObject jsonObject = (JSONObject)obj;
//            this.username = (String)jsonObject.get("username");
//            this.password = (long)jsonObject.get("password");
//            String type = (String)jsonObject.get("type");
//            System.out.println("Name: " + username);
//            System.out.println("Course: " + password);
//            System.out.println("type:" + type);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }
}
