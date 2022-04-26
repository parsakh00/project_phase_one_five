package edu.system.gui;

import edu.system.HelloApplication;
import edu.system.logic.Controller;
import edu.system.logic.CurrentUser;
import edu.system.logic.MassageUserDesk;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
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






    public void initialize() throws IOException, ParseException {
        getUserLessonExam();
        getUserLessonName();
        turnLessonToArray();
        turnExamDayToArray();
        mapping();
        sortMapAndShow();
    }
    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
        else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setHeight(650);
            stage.setWidth(800);
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("educational system");
            stage.show();
        }
    }

    protected String getUserDegree() throws IOException, ParseException {
        MassageUserDesk massageUserDegree = new MassageUserDesk(CurrentUser.getInstance().getUser());
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    protected void getUserLesson() throws IOException, ParseException {
        MassageUserDesk massageGetUserLesson = new MassageUserDesk(CurrentUser.getInstance().getUser());
        lesson =  Controller.getInstance().userOfLessons(massageGetUserLesson);
    }
    protected void getUserLessonExam() throws IOException, ParseException {
        MassageUserDesk massageGetLessonExam = new MassageUserDesk(CurrentUser.getInstance().getUser());
        examDay =  Controller.getInstance().nameOfLessons(massageGetLessonExam);

    }
    protected void getUserLessonName() throws IOException, ParseException {
        MassageUserDesk massageUserLessonName = new MassageUserDesk(CurrentUser.getInstance().getUser());
        lessonName =  Controller.getInstance().examOfLessons(massageUserLessonName);

    }

    protected void turnLessonToArray(){
        for (String element : examDay){
            if (!Objects.equals((String) element, "null")) lessonsNames.add(element);
        }
    }
    protected void turnExamDayToArray(){
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
