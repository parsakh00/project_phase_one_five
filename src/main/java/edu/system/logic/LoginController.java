package edu.system.logic;

import com.google.gson.Gson;
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
    String name;

    public void editingPassWord(String username, String password){
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
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void addingTeacherUser(String user,String pass ,String email,String room,String phone, String faculty){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("password", pass);
        jsonObject.put("email", email);
        jsonObject.put("room No.", room);
        jsonObject.put("phone", phone);
        jsonObject.put("faculty", faculty);
        jsonObject.put("type", "teacher");
        jsonObject.put("degree", "");
        jsonObject.put("lesson", "");
        try {
            FileWriter file = new FileWriter("E:/output.json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void editingEmail(String username, String email){
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
                e.printStackTrace();
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public void deleteCourseTeacher(String name,String faculty){
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
                e.printStackTrace();
            }

        } catch (ParseException | IOException e) {
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
        File file =  new File(System.getProperty("user.dir"),"/" + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json");
        isNameValid = file.exists();
        return isNameValid;
    }
    public boolean checkPass(String pass,String name){
        if (isNameValid){
            JSONParser parser = new JSONParser();
            try {
                Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
                JSONObject jsonObject = (JSONObject)obj;
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
