package edu.system.logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class studentController {


    public String getEmail(String name) throws IOException, ParseException {
        //ToDo
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + name + ".json"));
        JSONObject jsonObject = (JSONObject) obj;
        return (String) jsonObject.get("email");


    }
}
