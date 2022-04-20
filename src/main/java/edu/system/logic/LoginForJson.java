package edu.system.logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class LoginForJson {
    String name;
    String password;

    public LoginForJson(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public String getUserType() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject)obj;
        return (String) jsonObject.get("type");
    }

    public String getUserName() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject)obj;
        return (String) jsonObject.get("username");
    }

    public String getUserEmail() throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject)obj;
        return (String) jsonObject.get("email");
    }

}
