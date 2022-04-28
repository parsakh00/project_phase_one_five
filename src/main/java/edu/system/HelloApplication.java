package edu.system;

import com.google.gson.Gson;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;

public class HelloApplication extends Application {
    static Logger log = LogManager.getLogger(HelloApplication.class);

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        Image icon = new Image(String.valueOf(HelloApplication.class.getResource("images/university logo.png")));
        stage.setOnCloseRequest(event ->{
            event.consume();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("close window");
            alert.setHeaderText("confirmation");
            alert.setContentText("Are you sure you want to close a window?");
            Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
            alertStage.getIcons().add(new Image(String.valueOf(HelloApplication.class.getResource("images/university logo.png"))));
            if (alert.showAndWait().get() == ButtonType.OK){
                stage.close();
            }
        });

        stage.getIcons().add(icon);
        stage.setTitle("Education System");
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws IOException {


//        JSONParser parser = new JSONParser();
//        try {
//            //Read JSON file
//            Object obj = parser.parse(new FileReader(System.getProperty("user.dir") + "\\src\\main\\java\\edu\\system\\userdata\\" + "bahram ghanbari" + ".json"));
//            JSONObject user = (JSONObject) obj;
//            System.out.println(user.toString());
//            if (!((Boolean) user.get("isEducationalAssistantSet"))) {
//                user.put("isEducationalAssistantSet", true);
//
//
//            }
//            else{
//                System.out.println("true");
//                user.put("isEducationalAssistantSet", false);
//                user.toString();
//                System.out.println(user.toJSONString());
//
//            }
//
//        } catch (ParseException | IOException e) {
//            e.printStackTrace();
//        }
        //System.out.println(passHash("96102132"));
        //System.out.println(passHash("97231023"));
        log.info("launch program");
//        try{
//            FileInputStream fstream = new FileInputStream("./src/main/resources/logs/userActivity.log");
//            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
//            String strLine;
//            /* read log line by line */
//            while ((strLine = br.readLine()) != null)   {
//                /* parse strLine to obtain what you want */
//                System.out.println (strLine);
//            }
//            fstream.close();
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//        }
        //System.out.println(log.getParent());

        //deserializeUser();
        //serializeUser();
        //currentDirectory();
        //System.out.println(passHash("98114022"));
        launch();

    }
    //for generate primitive user data
//    public static void serializeUser(){
//        String path = "C:\\Users\\asus\\IdeaProjects\\project_phase_one_five\\src\\main\\java\\edu\\system\\userdata\\user100.json";
//        User users = new User("student10", "23314135","teacher", "","student10@gmail.com");
//        try(PrintWriter out = new PrintWriter(new FileWriter(path))){
//            Gson gson = new Gson();
//            String json = gson.toJson(users);
//
//            out.write(json);
//
//        }
//        catch (Exception e){
//            e.printStackTrace();
//        }
//
//
//    }
//    public static void deserializeUser(){
//        JSONParser parser = new JSONParser();
//        try {
//            Object obj = parser.parse(new FileReader("C:\\\\Users\\\\asus\\\\IdeaProjects\\\\project_phase_one_five\\\\src\\\\main\\\\java\\\\edu\\\\system\\\\userdata\\\\user100.json"));
//            JSONObject jsonObject = (JSONObject)obj;
//            String username = (String)jsonObject.get("username");
//            long password = passHash((String)jsonObject.get("password"));
//            String type = (String)jsonObject.get("type");
//            System.out.println("Name: " + username);
//            System.out.println("Course: " + password);
//            System.out.println("type:" + type);
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    public static void currentDirectory(){
//        File file =  new File(System.getProperty("user.dir"),"/" + "\\src\\main\\java\\edu\\system\\userdata\\user100.json");
//        System.out.println(file.exists());
//    }
//    private static long passHash(String str){
//        long res=0;
//        long tavan=1;
//        long mod= 1000000007;
//        long prime=373;
//        for (int i=0;i<str.length();i++){
//            int ascii=str.charAt(i);
//            tavan=(tavan*prime)%mod;
//            res=(res+(tavan*ascii)%mod)%mod;
//        }
//        return res;
//    }
}