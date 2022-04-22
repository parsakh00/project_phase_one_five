package edu.system.logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;

public class userController {


    public String getEmail(String name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("email");
    }
    public String getUserName(String name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("username");
    }

    public String getType(String name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("type");
    }
    public String getUserDegree(String name) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("degree");
    }
}
