package gui;

import ServerRunning.ServerMode;
import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import javafx.application.Platform;
import message.Message;
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
    public Label serverCondition;
    Stage stage;
    String[] lesson;
    String[] lessonName;
    String[] examDay;
    ArrayList<String> lessonsNames = new ArrayList<>();
    ArrayList<Integer> examsDays = new ArrayList<>();
    Map<Integer, String> map = new HashMap<Integer, String>();

    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));

    static Logger log = LogManager.getLogger(ClientMain.class);

    public void initialize() throws IOException, ParseException, InterruptedException {
        log.info("Open exams list page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened", e);
                throw new RuntimeException(e);
            }
        });
        Thread ping = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (ServerMode.getInstance().isOnline()) {
                        Platform.runLater(() -> {
                            serverCondition.setText("Server is online");
                            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "get user lesson name and exam"));
                        });

                    } else {
                        Platform.runLater(() -> {
                            serverCondition.setText("server is offline");
                            try {
                                showExamListOffline();
                            } catch (IOException | ParseException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    }

                    // delay 5 seconds
                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        break;
                    }
                }
            }
        });
        ping.start();
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
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Logged out out of time");
                stage = ((Stage) (one).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setStageProp(stage, scene);
                ClientLogic.getInstance().setLogOutDesk(loader, stage);
            });
        }
    }

    public void backBtnClicked(ActionEvent actionEvent) throws IOException, ParseException {
        log.info("Back button clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "back to main page"));
            });
        }
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        if (Objects.equals(getUserDegree(), "undergraduate")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentUndergraduateDesk-view.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentUndergraduateDesk(loader, stage);
        } else if (Objects.equals(getUserDegree(), "master")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentMasterDesk.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentMasterDesk(loader, stage);
        } else if (Objects.equals(getUserDegree(), "phd")) {
            FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentPhd.fxml"));
            Scene scene = new Scene(loader.load());
            setStageProp(stage, scene);
            ClientLogic.getInstance().setStudentPhdDesk(loader, stage);
        }
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Get user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    private void showExamListOffline() throws IOException, ParseException {
        MassageInNetwork massageGetLessonExam = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        String[] examDay = Controller.getInstance().nameOfLessons(massageGetLessonExam);
        MassageInNetwork massageUserLessonName = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        String[] lessonName2 = Controller.getInstance().examOfLessons(massageUserLessonName);
        log.info("Turn lesson to array");
        for (String element : examDay) {
            if (!Objects.equals((String) element, "null")) examsDays.add(Integer.valueOf(element));
        }
        log.info("Turn exam day to array");
        for (String element : lessonName2) {
            if (!Objects.equals((String) element, "null")) lessonsNames.add(element);
        }
        for (int i = 0; i < examsDays.size(); i++) {
            map.put(examsDays.get(i), lessonsNames.get(i));
        }
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
            int month = entry.getKey() / 100;
            int day = entry.getKey() % 100;
            String days = "/" + Integer.toString(month) + "/" + Integer.toString(day) + "   " + lessonName;
            label.setText(days);
            label.setAlignment(Pos.CENTER);
            exams.add(label, 0, i);
            i += 1;
        }
    }

    public void getUserLessonExamNew(String content) {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Get user lesson name");
                log.info("Get user lesson exam");
                String[] data = content.split("-");
                String[] lessons = new String[(data.length) / 2];
                String[] examList = new String[(data.length) / 2];
                for (int i = 0; i < data.length; i++) {
                    if (i < (data.length) / 2) {
                        lessons[i] = data[i];
                    } else {
                        examList[i - (data.length) / 2] = data[i];
                    }
                }
                log.info("Turn lesson to array");
                for (String element : examList) {
                    if (!Objects.equals((String) element, "null")) examsDays.add(Integer.valueOf(element));
                }
                log.info("Turn exam day to array");
                for (String element : lessons) {
                    if (!Objects.equals((String) element, "null")) lessonsNames.add(element);
                }
                for (int i = 0; i < examsDays.size(); i++) {
                    map.put(examsDays.get(i), lessonsNames.get(i));
                }
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
                    int month = entry.getKey() / 100;
                    int day = entry.getKey() % 100;
                    String days = "/" + Integer.toString(month) + "/" + Integer.toString(day) + "   " + lessonName;
                    label.setText(days);
                    label.setAlignment(Pos.CENTER);
                    exams.add(label, 0, i);
                    i += 1;
                }
            });
        }
    }
}
