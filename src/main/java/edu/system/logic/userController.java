package edu.system.logic;

import com.google.gson.Gson;
import edu.system.HelloApplication;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class userController {
    static Logger log = LogManager.getLogger(HelloApplication.class);



    public String getEmail(String name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("email");
    }
    public String getUserName(String name) throws IOException, ParseException {
        log.info("Read name of user from file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("username");
    }
    public String getUserScore(String name) throws IOException, ParseException {
        log.info("Read user total score from user data file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("total score");
    }
    public String getUserYear(String name) throws IOException, ParseException {
        log.info("Read user enterance year from user data file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("entering year");
    }
    public String getId(String name) throws IOException, ParseException {
        log.info("Open user data file to read student id of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("student number");
    }
    public String getUserPhoneNumber(String name) throws IOException, ParseException {
        log.info("Read and rewrite phone number from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("phone number");
    }
    public String getIdTeacher(String name) throws IOException, ParseException {
        log.info("Read userdata file to get teacher number");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("teacher number");
    }

    public void userWithdraw(String name) throws IOException, ParseException {
        log.info("Read and rewrite withdrawal file to set withdrawal request");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put(name, "2");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json";
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
    public void rejectUSer(String name) throws IOException, ParseException {
        log.info("Open and rewrite withdrawal requests file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put(name, "0");

        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        }
        catch (Exception e){
            log.error("exception happened");
            e.printStackTrace();
        }
    }

    public void acceptRecommend(String name,String teacher) throws IOException, ParseException {
        log.info("Read and rewrite result of recommendation from recommendation file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        user.put(name,"1");
        JSONObject user2 = (JSONObject)user.get(teacher);
        user2.put("condition", "1");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(user);
            out.write(json);
        }
        catch (Exception e){
            log.error("exception happened", e);
            e.printStackTrace();
        }

    }
    public void rejectRecommend(String name, String teacher) throws IOException, ParseException {
        log.info("Read and rewrite result of recommendation from recommendation file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        user.put(name,"0");
        JSONObject user2 = (JSONObject)user.get(teacher);
        user2.put("condition", "0");

        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(user);
            out.write(json);
        }
        catch (Exception e){
            log.error("exception happened", e);
            e.printStackTrace();
        }

    }


    public void acceptUser(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put(name, "1");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
    public void condition(String name) throws IOException, ParseException {
        log.info("Open and write new condition of user");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\"+name+".json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put("condition", "withdrawal from education");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\"+name+".json";
        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        }
        catch (Exception e){
            log.error("exception", e);
            e.printStackTrace();
        }
    }
    public String teacherGetName(String name) throws IOException, ParseException {
        log.info("Open and read recommendation file to get teacher name");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;

        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());


            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))){
                    return (String) user2.get("teacherName");
                }
            }
        }
        return null;
    }
    public String teacherGetScore(String name) throws IOException, ParseException {
        log.info("Open and read recommendation file to get scores");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))){
                    return (String) user2.get("scores");
                }
            }
        }
        return null;
    }
    public String teacherGetLesson(String name) throws IOException, ParseException {
        log.info("Open and read recommendation file to get lessons");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;

        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());


            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))){
                    return (String) user2.get("lessons");
                }
            }
        }
        return null;
    }
    public String teacherGetTa(String name) throws IOException, ParseException {
        log.info("Open and read recommendation to figure it if user has been ta or not");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);

                if (Objects.equals(((String) user2.get("username")), ((String) name))){
                    return (String) user2.get("TA");
                }
            }
        }
        return null;
    }
    public String minor(String name) throws IOException, ParseException {
        log.info("Open and read minor requests");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;

        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);

                Collection ki = user2.values();
                String tmp = "0";
                for (Object elements:ki){
                    tmp += (String) elements;
                }
                return tmp;
            }
        }
        return null;
    }



    public String userResult(String name) throws IOException, ParseException {
        log.info("open recommendation file to read result");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;

        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());


            for (String element : names) {
                JSONObject user2 = (JSONObject)user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))){
                    return (String) user2.get("condition");
                }
            }
        }
        return null;
    }





    public String userWithdrawResult(String name) throws IOException, ParseException {
        log.info("Read withdrawal file to show user withdrawal result");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get(name);
    }
    public String getUserCondition(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("condition");
    }
    public String getUserFaculty(String name) throws IOException, ParseException {
        log.info("Read user faculty from file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("faculty");
    }
    public String getUserStudentNumber(String name) throws IOException, ParseException {
        log.info("Read user student number from file ");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("student number");
    }



    public String getEducationalStatus(String name) throws IOException, ParseException {
        log.info("Read user education status from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("condition");
    }
    public String getSupervisor(String name) throws IOException, ParseException {
        log.info("Read user supervisor from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("supervisor");
    }

    public String getType(String name) throws IOException, ParseException {
        log.info("Read user type from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("type");
    }
    public String getUserDegree(String name) throws IOException, ParseException {
        log.info("Read degree of user from file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("degree");
    }
    public String getUserMastery(String name) throws IOException, ParseException {
        log.info("Read userdata file to find teacher's master degree");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("master degree");
    }


}

