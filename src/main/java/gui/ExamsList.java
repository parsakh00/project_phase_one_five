package gui;

import edu.system.ClientMain;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.*;

public class ExamsList {
    public Pane one;
    public Pane two;
    public Pane three;
    public Pane four;
    public Pane five;
    public Pane seven;
    public Pane six;
    public Pane eight;
    public Pane nine;
    public Pane ten;
    public GridPane exams;
    Stage stage;
    String[] lesson;
    String[] lessonName;
    String[] examDay;
    ArrayList<String> lessonsNames = new ArrayList<>();
    ArrayList<Integer> examsDays = new ArrayList<>();
    Map<Integer, String> map = new HashMap<Integer, String>();

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() throws IOException, ParseException {
        log.info("Open exams list page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent ->{
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened", e);
                throw new RuntimeException(e);
            }
        } );
        getUserLessonExam();
        getUserLessonName();
        turnLessonToArray();
        turnExamDayToArray();
        mapping();
        sortMapAndShow();
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }
    public void logOut() throws IOException {
        log.info("Logged out out of time");
        stage = ((Stage) (one).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
    }
    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
        }
        else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
        }
        else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
        }
    }
    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }
    protected void getUserLessonExam() throws IOException, ParseException {
        log.info("Get user lesson exam");
        MassageInNetwork massageGetLessonExam = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        examDay =  Controller.getInstance().nameOfLessons(massageGetLessonExam);

    }
    protected void getUserLessonName() throws IOException, ParseException {
        log.info("Get user lesson name");
        MassageInNetwork massageUserLessonName = new MassageInNetwork(CurrentUser.getInstance().getUserName(),null,null);
        lessonName =  Controller.getInstance().examOfLessons(massageUserLessonName);

    }
    protected void turnLessonToArray(){
        log.info("Turn lesson to array");
        for (String element : examDay){
            if (!Objects.equals((String) element, "null")) lessonsNames.add(element);
        }
    }
    protected void turnExamDayToArray(){
        log.info("Turn exam day to array");
        for (String element : lessonName){
            if (!Objects.equals((String) element, "null")) examsDays.add(Integer.parseInt(element));
        }
    }
    protected void mapping(){
        for (int i = 0; i < examsDays.size() ; i++){
            map.put(examsDays.get(i), lessonsNames.get(i));

        }
    }
    protected void sortMapAndShow(){
        log.info("Sorting arrays");
        Map<Integer, String> treeMap = new TreeMap<Integer, String>(
                new Comparator<Integer>() {

                    @Override
                    public int compare(Integer o1, Integer o2) {
                        return o2.compareTo(o1);
                    }

                });
        treeMap.putAll(map);
        int i = 0;
        for (Map.Entry<Integer, String> entry : treeMap.entrySet()) {
            Label label = new Label();
            String lessonName = entry.getValue();
            int month = entry.getKey()/100;
            int day = entry.getKey() % 100;
            String days = "/"+Integer.toString(month) + "/" + Integer.toString(day) + "   " + lessonName;
            label.setText(days);
            label.setAlignment(Pos.CENTER);
            exams.add(label, 0, i);
            i += 1;
        }
    }
}
