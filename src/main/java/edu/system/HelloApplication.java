package edu.system;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class HelloApplication extends Application {
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

    public static void main(String[] args) {
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
    public static void deserializeUser(){
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("C:\\\\Users\\\\asus\\\\IdeaProjects\\\\project_phase_one_five\\\\src\\\\main\\\\java\\\\edu\\\\system\\\\userdata\\\\user100.json"));
            JSONObject jsonObject = (JSONObject)obj;
            String username = (String)jsonObject.get("username");
            long password = passHash((String)jsonObject.get("password"));
            String type = (String)jsonObject.get("type");
            System.out.println("Name: " + username);
            System.out.println("Course: " + password);
            System.out.println("type:" + type);
        } catch(Exception e) {
            e.printStackTrace();
        }

    }

    public static void currentDirectory(){
        File file =  new File(System.getProperty("user.dir"),"/" + "\\src\\main\\java\\edu\\system\\userdata\\user100.json");
        System.out.println(file.exists());
    }
    private static long passHash(String str){
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
}