package server;

import com.google.gson.Gson;
import edu.system.ClientMain;
import passHash.PassHash;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Logic {
    String courseTeacher;
    boolean isPassValid;
    String name;
    ArrayList<String> objections;
    String[] facultyData;
    ArrayList<String> scores = new ArrayList<String>();
    ArrayList<String> teacher = new ArrayList<String>();
    ArrayList<String> lessonsss = new ArrayList<String>();
    ArrayList<String> units = new ArrayList<>();
    ArrayList<String> respond = new ArrayList<String>();
    String eachLesson;
    String eachTeacher;
    String[] TeacherData;
    String Unt;
    Boolean isChoose;
    static String userFaculty;
    String[] userLessonData;
    String lessonTarget;
    String lessonTargetEdit;
    String lessonTimeEdit;
    String lessonTeacherEdit;
    String idOfLesson;
    int j = 0;
    static Logger log = LogManager.getLogger(ClientMain.class);

    public void editingPassWord(String username, String password) {
        log.info(" open and rewrite password");
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = getUserJsonObject(parser, username);
            jsonObject.put("password", PassHash.passHash(password));
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + username + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public Boolean editPassLogOut(String username, String newPassword, String oldPassword) {
        log.info("Read and rewrite file to edit password");
        JSONParser parser = new JSONParser();
        boolean isAble = false;
        try {
            JSONObject jsonObject = getUserJsonObject(parser, username);
            if (Objects.equals((Long) jsonObject.get("password"), PassHash.passHash(oldPassword))) {
                jsonObject.put("password", PassHash.passHash(newPassword));
                String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + username + ".json";
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
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return isAble;
    }

    public void signUpUser(String user, String id, String phone, String supervisor, String faculty, String enteringYear,
                           String condition, String pass, String email, String degree) {
        log.info("Sign Up new student");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("id", id);
        jsonObject.put("phone", phone);
        jsonObject.put("total score", "");
        jsonObject.put("faculty", faculty);
        jsonObject.put("supervisor", supervisor);
        jsonObject.put("entering year", enteringYear);
        jsonObject.put("condition", condition);
        jsonObject.put("password", PassHash.passHash(pass));
        jsonObject.put("type", "student");
        jsonObject.put("email", email);
        jsonObject.put("degree", degree);
        jsonObject.put("student number", pass);
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + user + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUpTeachers(String user, String id, String phone, String supervisor, String faculty, String enteringYear,
                               String condition, String pass, String email, String degree) {
        log.info("Sign Up nre teacher");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("id", id);
        jsonObject.put("phone number", phone);
        jsonObject.put("faculty", faculty);
        jsonObject.put("room No.", supervisor);
        jsonObject.put("phone", enteringYear);
        jsonObject.put("master degree", condition);
        jsonObject.put("password", PassHash.passHash(pass));
        jsonObject.put("type", "teacher");
        jsonObject.put("email", email);
        jsonObject.put("degree", degree);
        jsonObject.put("teacher number", pass);
        try {
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + user + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void getRecommend(String teacherName, String lesson, String score, String ta, String userName) throws IOException, ParseException {
        log.info("write new recommendation request file for user");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject2.put("condition", "2");
        jsonObject2.put("username", userName);
        jsonObject2.put("lessons", lesson);
        jsonObject2.put("scores", score);
        jsonObject2.put("TA", ta);
        jsonObject2.put("teacherName", teacherName);
        jsonObject.put(teacherName, jsonObject2);
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }
    public static void addMessageForCriticism(String username,String message){
        log.info("write message for admin");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\messageForAdmin.json"));
            JSONObject jsonObject = (JSONObject) obj;
            jsonObject.put(username, message);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\messageForAdmin.json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void userMinorRequest(String name, String faculty1, String faculty2) throws IOException, ParseException {
        log.info("Open and rewrite minor file acceptation or rejection condition");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json"));
        JSONObject jsonObject2 = new JSONObject();
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject2.put(faculty1, "2");
        jsonObject2.put(faculty2, "2");
        jsonObject.put(name, jsonObject2);
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void addingTeacherUser(String user, String pass, String email, String room, String phone, String faculty, String masterDegree, String id) {
        log.info("write new user file");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user);
        jsonObject.put("password", PassHash.passHash(pass));
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
            FileWriter file = new FileWriter(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + user + ".json");
            file.write(jsonObject.toJSONString());
            file.close();
        } catch (IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }

    }

    public void emailEditProfile(String username, String email) {
        log.info("Read and rewrite userdata file");
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = getUserJsonObject(parser, username);
            jsonObject.put("email", (String) email);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + username + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void passEditProfile(String username, String phone) {
        log.info("Read and rewrite userdata file to edit password of user");
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = getUserJsonObject(parser, username);
            jsonObject.put("phone number", (String) phone);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + username + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void editingEmail(String username, String email) {
        log.info("Open and rewrite email");
        JSONParser parser = new JSONParser();
        try {
            JSONObject jsonObject = getUserJsonObject(parser, username);
            jsonObject.put("email", (String) email);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + username + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(jsonObject);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void deleteCourseTeacher(String name, String faculty) {
        log.info("Open and rewrite for delete course teacher");
        this.courseTeacher = name;
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json"));
            JSONArray facultyy = (JSONArray) obj;
            facultyy.forEach(fac -> removeCourseProperty((JSONObject) fac));
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(facultyy);
                out.write(json);
            } catch (Exception e) {
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
    }

    public JSONObject getUserJsonObject(JSONParser parser, String name) throws IOException, ParseException {
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        return (JSONObject) obj;
    }

    public boolean checkUserPass(String pass, String name) {
        log.info("Read username and password of user from userdata file and check them");
        File file = new File(System.getProperty("user.dir"), "/" + "\\src\\main\\java\\data\\userdata\\" + name + ".json");
        if (file.exists()) {
            JSONParser parser = new JSONParser();
            try {
                JSONObject jsonObject = getUserJsonObject(parser, name);
                log.info("check equality of hash of pass that entered by pass that user field in password field");
                if (PassHash.passHash(pass) == (long) jsonObject.get("password")) {

                    isPassValid = true;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (isPassValid) this.name = name;
        return isPassValid;
    }

    public static String getEmail(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("email");
    }

    public static String getUserName(String name) throws IOException, ParseException {
        log.info("Read name of user from file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("username");
    }

    public static String getUserScore(String name) throws IOException, ParseException {
        log.info("Read user total score from user data file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("total score");
    }

    public static void setUserNewScore(String name, String totalScore) throws IOException, ParseException {
        log.info("Read and rewrite withdrawal file to set new total score of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put("total score", totalScore);
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public static String getUserYear(String name) throws IOException, ParseException {
        log.info("Read user enterance year from user data file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("entering year");
    }

    public static String getId(String name) throws IOException, ParseException {
        log.info("Open user data file to read student id of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("id");
    }

    public static String getCondition(String name) throws IOException, ParseException {
        log.info("Open user data file to read student id of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("condition");
    }

    public static String getPhone(String name) throws IOException, ParseException {
        log.info("Open user data file to read student id of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("phone");
    }

    public static String getEnteringYear(String name) throws IOException, ParseException {
        log.info("Open user data file to read student id of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("entering year");
    }

    public static String getRoomNo(String name) throws IOException, ParseException {
        log.info("Open user data file to read student id of student");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("room No.");
    }

    public static String getFaculty(String name) throws IOException, ParseException {
        log.info("Read user type from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("faculty");
    }

    public static String getUserPhoneNumber(String name) throws IOException, ParseException {
        log.info("Read and rewrite phone number from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("phone number");
    }

    public static Boolean isStudent(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (Boolean) jsonObject.get("is student");
    }

    public static void userWithdraw(String name) throws IOException, ParseException {
        log.info("Read and rewrite withdrawal file to set withdrawal request");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put(name, "2");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public static void rejectUser(String name) throws IOException, ParseException {
        log.info("Open and rewrite withdrawal requests file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put(name, "0");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened");
            e.printStackTrace();
        }
    }

    public static void acceptRecommend(String name, String teacher) throws IOException, ParseException {
        log.info("Read and rewrite result of recommendation from recommendation file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        user.put(name, "1");
        JSONObject user2 = (JSONObject) user.get(teacher);
        user2.put("condition", "1");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public static void rejectRecommend(String name, String teacher) throws IOException, ParseException {
        log.info("Read and rewrite result of recommendation from recommendation file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        user.put(name, "0");
        JSONObject user2 = (JSONObject) user.get(teacher);
        user2.put("condition", "0");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(user);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }

    }


    public static void acceptUser(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put(name, "1");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void condition(String name) throws IOException, ParseException {
        log.info("Open and write new condition of user");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        jsonObject.put("condition", "withdrawal from education");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(jsonObject);
            out.write(json);
        } catch (Exception e) {
            log.error("exception", e);
            e.printStackTrace();
        }
    }

    public static String teacherGetName(String name) throws IOException, ParseException {
        log.info("Open and read recommendation file to get teacher name");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))) {
                    return (String) user2.get("teacherName");
                }
            }
        }
        return null;
    }

    public static String teacherGetScore(String name) throws IOException, ParseException {
        log.info("Open and read recommendation file to get scores");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))) {
                    return (String) user2.get("scores");
                }
            }
        }
        return null;
    }

    public static String teacherGetLesson(String name) throws IOException, ParseException {
        log.info("Open and read recommendation file to get lessons");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))) {
                    return (String) user2.get("lessons");
                }
            }
        }
        return null;
    }

    public static String teacherGetTa(String name) throws IOException, ParseException {
        log.info("Open and read recommendation to figure it if user has been ta or not");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))) {
                    return (String) user2.get("TA");
                }
            }
        }
        return null;
    }

    public static String minor(String name) throws IOException, ParseException {
        log.info("Open and read minor requests");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                Collection ki = user2.values();
                String tmp = "0";
                for (Object elements : ki) {
                    tmp += (String) elements;
                }
                return tmp;
            }
        }
        return null;
    }

    public static String userResult(String name) throws IOException, ParseException {
        log.info("open recommendation file to read result");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                if (Objects.equals(((String) user2.get("username")), ((String) name))) {
                    return (String) user2.get("condition");
                }
            }
        }
        return null;
    }

    public static String userWithdrawResult(String name) throws IOException, ParseException {
        log.info("Read withdrawal file to show user withdrawal result");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get(name);
    }

    public static String getUserCondition(String name) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("condition");
    }

    public static String getUserFaculty(String name) throws IOException, ParseException {
        log.info("Read user faculty from file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("faculty");
    }

    public static String getUserStudentNumber(String name) throws IOException, ParseException {
        log.info("Read user student number from file ");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("student number");
    }

    public static String getEducationalStatus(String name) throws IOException, ParseException {
        log.info("Read user education status from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("condition");
    }

    public static String getSupervisor(String name) throws IOException, ParseException {
        log.info("Read user supervisor from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("supervisor");
    }

    public static String getType(String name) throws IOException, ParseException {
        log.info("Read user type from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("type");
    }

    public static String getDegree(String name) throws IOException, ParseException {
        log.info("Read user type from userdata file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("degree");
    }

    public static String getUserDegree(String name) throws IOException, ParseException {
        log.info("Read degree of user from file");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("degree");
    }

    public static String getUserMastery(String name) throws IOException, ParseException {
        log.info("Read userdata file to find teacher's master degree");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("master degree");
    }

    public void removeLesson(String lesson, String faculty) {
        this.lessonTarget = lesson;
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json"));
            JSONArray courses = (JSONArray) obj;
            courses.forEach(lessonSelect -> findingLesson((JSONObject) lessonSelect));
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(courses);
                out.write(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void edit(String lesson, String faculty, String time, String teacher) {
        this.lessonTargetEdit = lesson;
        this.lessonTimeEdit = time;
        this.lessonTeacherEdit = teacher;
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json"));
            JSONArray coursesEdit = (JSONArray) obj;
            coursesEdit.forEach(lessonSelect -> findingLessonEdit((JSONObject) lessonSelect));
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(coursesEdit);
                out.write(json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    private void findingLessonEdit(JSONObject lessons) {
        if (Objects.equals((String) lessons.get("lesson"), lessonTargetEdit)) {
            if (lessonTeacherEdit != null) {
                lessons.put("teacher", lessonTeacherEdit);
            }
            if (lessonTimeEdit != null) {
                lessons.put("time", lessonTimeEdit);
            }
        }
    }

    public void add(String lesson, String faculty, String time, String teacher, String unity, String stage, String id, Boolean isPresent) {
        JSONParser parser = new JSONParser();
        try {
            JSONObject eachLesson = new JSONObject();
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json"));
            JSONArray coursesAdd = (JSONArray) obj;
            //ToDo add list to array
            eachLesson.put("lesson", lesson);
            eachLesson.put("unity", unity);
            eachLesson.put("stage", stage);
            eachLesson.put("id", id);
            eachLesson.put("teacher", teacher);
            eachLesson.put("isPresent", isPresent);
            eachLesson.put("faculty", faculty);
            eachLesson.put("time", time);
            coursesAdd.add(eachLesson);
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + faculty + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(coursesAdd);
                out.write(json);
            } catch (Exception e) {
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

    public void TeacherDeleting(String name) {
        log.info("Delete file of teacher");
        File file = new File(System.getProperty("user.dir"), "/" + "\\src\\main\\java\\data\\userdata\\" + name + ".json");
        file.delete();
    }

    public static String getNationalId(String name) {
        log.info("Get user national id from userdata file");
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            userFaculty = (String) user.get("id");
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return userFaculty;
    }

    public String getSelectedFaculty(String name) {
        log.info("Read file to get selected faculty");
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            userFaculty = (String) user.get("faculty");
        } catch (ParseException | IOException e) {
            log.error("exception happened!");
            e.printStackTrace();
        }
        return userFaculty;
    }

    public Boolean isChosenBefore(String name) {
        log.info("Read manager info if educational assistant chosen or not");
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            isChoose = (Boolean) user.get("isEducationalAssistantSet");
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return isChoose;
    }

    public void change(String name) {
        log.info("Open and rewrite file to change is chosen boolean");
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            if (!((Boolean) user.get("isEducationalAssistantSet"))) {
                user.put("isEducationalAssistantSet", true);
            } else {
                user.put("isEducationalAssistantSet", false);
            }
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(user);
                out.write(json);

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    public void relegation(String name) {
        log.info("Read and rewrite for to relegate user");
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            user.put("degree", "-");
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(user);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void promotion(String name) {
        log.info("Read and rewrite file for promote user");
        JSONParser parser = new JSONParser();
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
            JSONObject user = (JSONObject) obj;
            user.put("degree", "education assistant");
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(user);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened", e);
                e.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public String[] nameLists(String name) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\withdrawal.json"));
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
        log.info("Open filr to reject minor request");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;
        JSONObject user2 = (JSONObject) user.get(name);
        user2.put(faculty, "0");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(user2);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    public void acceptMinor(String name, String faculty) throws IOException, ParseException {
        log.info("Open and rewrite file for accepting minor");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;
        JSONObject user2 = (JSONObject) user.get(name);
        user2.put(faculty, "1");
        String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json";
        try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
            Gson gson = new Gson();
            String json = gson.toJson(user2);
            out.write(json);
        } catch (Exception e) {
            log.error("exception happened!", e);
            e.printStackTrace();
        }
    }

    public String[] nameListsMinor(String name, String faculty) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\minor.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                JSONObject user2 = (JSONObject) user.get(element);
                List<String> namess = new ArrayList<String>(user2.keySet());
                for (String elements : namess) {
                    System.out.println(elements);
                    if (Objects.equals((String) elements, (String) faculty)) {
                        tmp += element;
                    }
                }
            }
        }
        System.out.println(tmp);
        return tmp.split("/");
    }

    public String[] nameListsRecommend(String name) throws IOException, ParseException {
        log.info("Open and read list of users from recommendation file");
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\recommendation.json"));
        JSONObject user = (JSONObject) obj;
        if (user.size() != 0) {
            List<String> names = new ArrayList<String>(user.keySet());
            for (String element : names) {
                if (Objects.equals((String) element, (String) name)) {
                    JSONObject user2 = (JSONObject) user.get(name);
                    tmp += ((String) user2.get("username")) + "/";
                }
            }
        }
        return tmp.split("/");
    }

    public String[] nameOfLesson(String name) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject user = (JSONObject) obj;
        String facultyOfLesson;
        JSONObject getId = (JSONObject) user.get("lessons");
        for (int i = 0; i < 5; i++) {
            if (i == 0) facultyOfLesson = "Chemistry";
            else if (i == 1) facultyOfLesson = "MechanicEng";
            else if (i == 2) facultyOfLesson = "ElectricalEng";
            else if (i == 3) facultyOfLesson = "MathSci";
            else facultyOfLesson = "Physics";
            idOfLesson = (String) getId.get(facultyOfLesson);
            if (!Objects.equals((String) idOfLesson, null)) {
                JSONParser parser2 = new JSONParser();
                try {
                    //Read JSON file
                    Object obj2 = parser2.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + facultyOfLesson + ".json"));
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
            eachLesson = (String) eachLesson + "/" + lesson;
        }
        //else if (eachLesson == null) eachLesson = "/" ;
    }

    public ArrayList<String> userRespond(String name) {
        log.info("Read temporaryScores file to get teacher respond");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            faculty.forEach(fac -> eachRespondShow((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return respond;
    }

    private void eachRespondShow(JSONObject facultyData) {
        String score = (String) facultyData.get("response");
        respond.add(score);
    }

    public ArrayList<String> userScore(String name) {
        log.info("Read temporaryScores file to get user scores");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            faculty.forEach(fac -> eachScoreShow((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return scores;
    }

    private void eachScoreShow(JSONObject facultyData) {
        if (!((Boolean) facultyData.get("final"))) {
            String score = "N/A";
            scores.add(score);
        }
    }

    public ArrayList<String> userScoreNew(String name) {
        log.info("Read permanent Scores file to get user scores");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\permanent Scores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            if (faculty != null) {
                faculty.forEach(fac -> eachScoreShowNew((JSONObject) fac));
            } else return scores;
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return scores;
    }

    private void eachScoreShowNew(JSONObject facultyData) {
        String score = (String) facultyData.get("score");
        scores.add(score);
    }


    public ArrayList<String> lessonUnit(String name) {
        log.info("Read temporaryScores file to get lessons unit");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            faculty.forEach(fac -> eachLessonUnit((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return units;
    }

    private void eachLessonUnit(JSONObject facultyData) {
        String unit = (String) facultyData.get("unit");
        units.add(unit);
    }

    public ArrayList<String> lessonUnitNew(String name) {
        log.info("Read permanent Scores file to get lessons unit");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\permanent Scores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            if (faculty != null) {
                faculty.forEach(fac -> eachLessonUnitNew((JSONObject) fac));
            } else return units;
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return units;
    }

    private void eachLessonUnitNew(JSONObject facultyData) {
        String unit = (String) facultyData.get("unit");
        units.add(unit);
    }

    public ArrayList<String> userTeacher(String name) {
        log.info("Read temporaryScores file to get user teachers");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            faculty.forEach(fac -> eachTeacherShow((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return teacher;
    }

    private void eachTeacherShow(JSONObject facultyData) {
        String teachers = (String) facultyData.get("teacherName");
        teacher.add(teachers);
    }

    public ArrayList<String> userTeacherNew(String name) {
        log.info("Read permanent Scores file to get user teachers");
        JSONParser parser = new JSONParser();
        try {

            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\permanent scores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            if (faculty != null) {
                faculty.forEach(fac -> eachTeacherShowNew((JSONObject) fac));
            } else return teacher;
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return teacher;
    }

    private void eachTeacherShowNew(JSONObject facultyData) {
        String teachers = (String) facultyData.get("teacherName");
        teacher.add(teachers);
    }

    public void userObjections(String name, ArrayList<String> objection) {
        objections = objection;
        log.info("open and write objections in temporary scores");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            faculty.forEach(fac -> eachObjection((JSONObject) fac));
            String path = System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json";
            try (PrintWriter out = new PrintWriter(new FileWriter(path))) {
                Gson gson = new Gson();
                String json = gson.toJson(faculty);
                out.write(json);
            } catch (Exception e) {
                log.error("exception happened!", e);
                e.printStackTrace();
            }
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
    }

    private void eachObjection(JSONObject facultyData) {
        facultyData.put("objection", objections.get(j));
        j += 1;
    }

    public ArrayList<String> userLessonss(String name) {
        log.info("Read temporaryScores file to get user lessons");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\temporaryScores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            faculty.forEach(fac -> eachLessonShow((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return lessonsss;
    }

    private void eachLessonShow(JSONObject facultyData) {
        String lesson = (String) facultyData.get("lesson");
        lessonsss.add(lesson);
    }

    public ArrayList<String> userLessonssNew(String name) {
        log.info("Read permanent Scores file to get user lessons");
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\requests\\permanent Scores.json"));
            JSONObject user = (JSONObject) obj;
            JSONArray faculty = (JSONArray) user.get(name);
            if (faculty != null) {
                faculty.forEach(fac -> eachLessonShowNew((JSONObject) fac));
            } else return lessonsss;
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
            e.printStackTrace();
        }
        return lessonsss;
    }

    private void eachLessonShowNew(JSONObject facultyData) {
        String lesson = (String) facultyData.get("lesson");
        lessonsss.add(lesson);
    }

    public String[] examOfLesson(String name) throws IOException, ParseException {
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject user = (JSONObject) obj;
        String facultyOfLesson;
        JSONObject getId = (JSONObject) user.get("lessons");
        for (int i = 0; i < 5; i++) {
            if (i == 0) facultyOfLesson = "Chemistry";
            else if (i == 1) facultyOfLesson = "MechanicEng";
            else if (i == 2) facultyOfLesson = "ElectricalEng";
            else if (i == 3) facultyOfLesson = "MathSci";
            else facultyOfLesson = "Physics";
            idOfLesson = (String) getId.get(facultyOfLesson);
            if (!Objects.equals((String) idOfLesson, null)) {
                JSONParser parser2 = new JSONParser();
                try {
                    //Read JSON file
                    Object obj2 = parser2.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + facultyOfLesson + ".json"));
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
        log.info("Read userdata file to get lesson of user");
        String tmp = "/";
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + name + ".json"));
        JSONObject user = (JSONObject) obj;
        String facultyOfLesson;
        JSONObject getId = (JSONObject) user.get("lessons");
        for (int i = 0; i < 5; i++) {
            if (i == 0) facultyOfLesson = "Chemistry";
            else if (i == 1) facultyOfLesson = "MechanicEng";
            else if (i == 2) facultyOfLesson = "ElectricalEng";
            else if (i == 3) facultyOfLesson = "MathSci";
            else facultyOfLesson = "Physics";
            idOfLesson = (String) getId.get(facultyOfLesson);
            if (!Objects.equals((String) idOfLesson, null)) {
                JSONParser parser2 = new JSONParser();
                try {
                    //Read JSON file
                    Object obj2 = parser2.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + facultyOfLesson + ".json"));
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
            eachLesson = (String) eachLesson + "/" + lesson + "/" + time + "/" + day + "/" + exam;
        }
        //else if (eachLesson == null) eachLesson = "/" ;
    }

    public String[] getFacultyData(String name) {
        log.info("Open and read faculty data");
        JSONParser parser = new JSONParser();
        String tmp = "/";
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;
            faculty.forEach(fac -> eachLessonProperties((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception error", e);
            e.printStackTrace();
        }
        if (eachLesson != null) facultyData = eachLesson.split("/");
        else facultyData = tmp.split("/");
        return facultyData;
    }

    public String[] getFacultyUnitData(String name, String unit) {
        log.info("Open and read faculty unit data");
        JSONParser parser = new JSONParser();
        this.Unt = unit;
        String tmp = "/";
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;
            faculty.forEach(fac -> eachLessonUnitProperties((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception error", e);
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
            eachLesson = eachLesson + "/" + stage + "/" + teacher + "/" + lesson + "/" + unity + "/" + id;
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
        log.info("Open and read faculty stage data from file");
        JSONParser parser = new JSONParser();
        this.Unt = unit;
        String tmp = "/";
        try {
            //Read JSON file
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;
            faculty.forEach(fac -> eachLessonStageProperties((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception error", e);
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

    public String[] getFacultyTeachers(String name) {
        log.info("Read unidatas file to get teachers data");
        JSONParser parser = new JSONParser();
        String tmp = "/";
        try {
            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\unidatas\\" + name + ".json"));
            JSONArray faculty = (JSONArray) obj;
            faculty.forEach(fac -> eachFacultyTeacherProperties((JSONObject) fac));
        } catch (ParseException | IOException e) {
            log.error("exception happened", e);
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
            File file = new File(System.getProperty("user.dir"), "/" + "\\src\\main\\java\\data\\userdata\\" + teacherName + ".json");
            if (file.exists()) {
                //Read JSON file
                Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\data\\userdata\\" + teacherName + ".json"));
                JSONObject teacher = (JSONObject) obj;
                String username = (String) teacher.get("username");
                String email = (String) teacher.get("email");
                String roomNo = (String) teacher.get("room No.");
                String phone = (String) teacher.get("phone");
                eachTeacher = eachTeacher + "/" + roomNo + "/" + phone + "/" + email + "/" + username;
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }
}