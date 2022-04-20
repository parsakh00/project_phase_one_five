package edu.system.logic;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;

public class Login {
    boolean isNameValid;
    boolean isPassValid;
    String name;
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
