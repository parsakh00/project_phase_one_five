package gui;

import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import currentUser.CurrentUser;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import message.Message;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;
import server.Controller;
import server.MassageInNetwork;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TeacherDesk {
    public MenuBar barTape;
    public Label criticism;
    public TextArea textArea;
    public Label studentFilterLabel;
    public TextField studentFilterTextField;
    public TextArea studentFilterTextArea;
    public Label mohseniAlert;
    public Label entYearLabel;
    public Label nameLabel;
    public Label degreeLabel;
    public TextField degreeTextField;
    public TextField enteringYearText;
    public TextField nameTextField;
    public Label mohseniMessageLabel;
    public TextField mohseniMessageTextField;
    public Button mohseniMessageBtn;
    public ComboBox studentProfileCombo;
    public TextArea studentProfileListView;
    public Label studentProfile;
    String lastLogIn;
    Stage stage;
    @FXML
    protected Label email;
    @FXML
    protected Label timeDate;
    @FXML
    protected Label username;
    @FXML
    protected Button logOut;
    @FXML
    protected Pane noidea;
    @FXML
    protected ImageView userImage;
    @FXML
    protected Label lastTimeLogIn;



    static Logger log = LogManager.getLogger(TeacherDesk.class);

    PauseTransition timer = new PauseTransition(Duration.seconds(10800));

    public void initialize() throws IOException, ParseException {
        log.info("Open teacher main, logged in");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                log.error("exception happened");
                throw new RuntimeException(e);
            }
        });

        getLoginTime();
        if (!Objects.equals(CurrentUser.getInstance().getUserName(), "admin") && !Objects.equals(CurrentUser.getInstance().getUserName(), "mohseni"))
            setUserImage();
        timeDisplay();
        lastTimeLogIn.setText("last log in : " + getLoginTime());
        username.setText("User : " + getUsername());
        if (!Objects.equals(CurrentUser.getInstance().getUserName(), "admin") && !Objects.equals(CurrentUser.getInstance().getUserName(), "mohseni")) {
            email.setText("Email : " + getEmail());
        }
        if (Objects.equals(CurrentUser.getInstance().getUserName(), "admin")) {
            barTape.setVisible(false);
            criticism.setVisible(true);
            textArea.setVisible(true);
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "message for admin previous"));
        }
        if (Objects.equals(CurrentUser.getInstance().getUserName(), "mohseni")) {
            studentFilterLabel.setText(null);
            barTape.setVisible(false);
            studentFilterTextArea.setVisible(true);
            studentFilterLabel.setVisible(true);
            studentFilterTextField.setVisible(true);
            mohseniAlert.setVisible(true);
            entYearLabel.setVisible(true);
            nameLabel.setVisible(true);
            degreeLabel.setVisible(true);
            enteringYearText.setText(null);
            enteringYearText.setVisible(true);
            degreeTextField.setText(null);
            degreeTextField.setVisible(true);
            nameTextField.setText(null);
            nameTextField.setVisible(true);
            mohseniMessageLabel.setVisible(true);
            mohseniMessageTextField.setVisible(true);
            mohseniMessageBtn.setVisible(true);
            studentProfileCombo.setVisible(true);
            studentProfileListView.setVisible(true);
            studentProfile.setVisible(true);
            studentProfileListView.setVisible(true);
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), studentFilterTextField.getText(), "get students base on filter"));
        }
        Thread ping = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (Objects.equals(CurrentUser.getInstance().getUserName(), "mohseni")) {
                        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), studentFilterTextField.getText(),
                                "get students base on filter"));
                    }
                    if (Objects.equals(CurrentUser.getInstance().getUserName(), "admin")) {
                        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), "", "show message for admin"));
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

    public void recommendationRequest() throws IOException {
        log.info("open recommendation requests");
        stage = ((Stage) (email).getScene().getWindow());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to teacherRecommendRequest fxml"));
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherRecommendRequest-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setTeacherRecommendRequest(loader, stage);
    }

    public void logOut() throws IOException {
        log.info("Logged out(out of time)");
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "logged out"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogOutDesk(loader, stage);
    }

    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        log.info("User logged out by himself");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/login-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setLogin(loader, stage);
    }

    public void timeDisplay() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeDate.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            }
        };
        timer.start();
    }

    public String getLoginTime() {

        try {
            FileInputStream fstream = new FileInputStream("./src/main/resources/logs/userActivity.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                if (strLine.contains("TeacherDesk")) {
                    Pattern pattern = Pattern.compile("\\d{2}:\\d{2}:\\d{2}");
                    Matcher matcher = pattern.matcher(strLine);
                    if (matcher.find()) {
                        lastLogIn = matcher.group();
                    }
                    break;
                }
            }
            fstream.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lastLogIn;
    }

    protected String getEmail() throws IOException, ParseException {
        log.info("Get current user Ebail");
        MassageInNetwork massageTeacherDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskEmail(massageTeacherDesk);
    }

    protected String getUsername() throws IOException, ParseException {
        log.info("Get current user username");
        MassageInNetwork massageTeacherDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskUserName(massageTeacherDesk);
    }

    private void setStageProp(Stage stage, Scene scene) {
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
    }

    public void setUserImage() throws IOException, ParseException {
        log.info("Set current user image");
        userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")));
        userImage.setFitHeight(160);
        userImage.setFitWidth(140);
        noidea.getChildren().add(userImage);
    }

    public void lessonListsClicked() throws IOException {
        log.info("Current user clicked lesson lists");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to lessonList-view fxml"));
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/lessonLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setLessonListDesk(loader, stage);
    }

    public void teachersListsClicked() throws IOException {
        log.info("Current user clicked teachers list");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to teacherLists-view fxml"));
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherLists-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        ClientLogic.getInstance().setTeachersListDesk(loader, stage);
    }

    protected String getUserDegree() throws IOException, ParseException {
        log.info("Current user degree");
        MassageInNetwork massageUserDegree = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDegree(massageUserDegree);
    }

    public void profileClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user profile clicked");
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                , "change fxml to profile fxml"));
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherProfile.fxml"));
        Scene scene = new Scene(loader.load());
        stage.setHeight(650);
        stage.setWidth(800);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        ClientLogic.getInstance().setTeacherDesk(loader, stage);
    }

    public void temporaryScoreClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Current user temporary score clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/temporaryScoreTeacher.fxml"));
//        Scene scene = new Scene(loader.load());
//        setStageProp(stage, scene);
    }

    public void studentsStatusClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Students status clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/StudentsEducationalStatus-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        setStageProp(stage, scene);
    }

    public void showMessageForAdmin(String content) {
        String[] data = content.split("-");
        textArea.clear();
        for (String str : data) {
            textArea.setText(textArea.getText() + str + '\n');
            textArea.setScrollTop(Double.MAX_VALUE);
        }
    }

    public void showMessageAdmin(String str) {
        textArea.setText(textArea.getText() + str + '\n');
        textArea.setScrollTop(Double.MAX_VALUE);
    }

    public void getStudentFilter(String content) {
        Platform.runLater(()->{
            String[] data = content.split("-");
            studentProfileCombo.getItems().clear();
            for (String str:data){
                studentProfileCombo.getItems().add(str);
            }
            studentFilterTextArea.clear();
            for (String str : data) {
                studentFilterTextArea.setText(studentFilterTextArea.getText() + str + '\n');
                studentFilterTextArea.setScrollTop(Double.MAX_VALUE);
            }
        });
    }


    public void executeEvent(KeyEvent event) {
        //int cursorLine = txtInput.getCaretPosition();
        if (event.isControlDown() && event.getCode() == KeyCode.ENTER) {
            //select line of text where integer equals cursorLine
            int caretPos = studentFilterTextArea.getCaretPosition();
            int previousNewLine = studentFilterTextArea.getText().lastIndexOf('\n', caretPos);
            int nextNewLine = studentFilterTextArea.getText().indexOf('\n', caretPos);
            if (nextNewLine == -1) nextNewLine = studentFilterTextArea.getText().length();
            studentFilterTextArea.selectRange(previousNewLine + 1, nextNewLine);
            //txtInput.selectBackward();
            //txtInput.selectForward();

        }
    }

    public void mohseniMessageBtnClicked(ActionEvent actionEvent) {
        if ((nameTextField.getText() == null || Objects.equals(nameTextField.getText(), "")) && (degreeTextField.getText() == null || Objects.equals(degreeTextField.getText(), "")) && (enteringYearText.getText() == null || Objects.equals(enteringYearText.getText(), ""))) {
            mohseniAlert.setText("specify at least one filter !");
        }
        else {
            if (mohseniMessageTextField.getText() != null && mohseniMessageTextField.getText() != "") {
                String data = "";
                if (!(nameTextField.getText() == null || Objects.equals(nameTextField.getText(), "")))
                    data = nameTextField.getText();
                if (!(degreeTextField.getText() == null || Objects.equals(degreeTextField.getText(), "")))
                    data = degreeTextField.getText();
                if (!(enteringYearText.getText() == null || Objects.equals(enteringYearText.getText(), "")))
                    data = enteringYearText.getText();
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), data + "-" + mohseniMessageTextField.getText(), "message from mohseni write file"));
                mohseniAlert.setText("sent !");
            }
            else {
                mohseniAlert.setText("what is your message ?!");
            }
        }
    }

    public void ComboBoxClicked(ActionEvent actionEvent) {
        if (studentProfileCombo.getValue() != null) {
            System.out.println(studentProfileCombo.getValue());
            String name = ((String) (studentProfileCombo.getValue())).split(":")[1];
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), name, "show specific user for mohseni"));
        }
    }
    public void showStudentProfile(String name){
        String[] data = name.split("-");
        studentProfileListView.clear();
        for (String str : data) {
            studentProfileListView.setText(studentProfileListView.getText() + str + '\n');
            studentProfileListView.setScrollTop(Double.MAX_VALUE);
        }
    }
}
