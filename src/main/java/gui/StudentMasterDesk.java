package gui;

import ServerRunning.ServerMode;
import constants.Constants;
import edu.system.Client;
import edu.system.ClientLogic;
import edu.system.ClientMain;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import message.Message;
import server.Controller;
import currentUser.CurrentUser;
import server.MassageInNetwork;
import javafx.animation.AnimationTimer;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.json.simple.parser.ParseException;

import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StudentMasterDesk {
    static Logger log = LogManager.getLogger(StudentMasterDesk.class);
    public TextField messageForAdmin;
    public Label alertMessageForAdmin;
    public TextArea messageMohseni;
    public Label serverCondition;
    public Label toWhoLabel;
    public Label chatBoxLabel;
    public Button sendChatBtn;
    public TextArea chatBoxTextArea;
    public ComboBox toWhoCombo;
    public TextField messageChatField;
    public Button sendVoiceBtn;
    public Button sendImageBtn;
    public ImageView userPhoto;
    @FXML
    protected MenuItem teacherslist;
    @FXML
    protected MenuItem lessonLists;
    PauseTransition timer = new PauseTransition(Duration.seconds(CurrentUser.getInstance().getTimer()));
    String lastLogIn;
    Stage stage;
    String whichMember;
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
    @FXML
    protected Label educationalStatus;
    @FXML
    protected Label supervisor;
    @FXML
    protected MenuItem weeklyScheduleList;
    @FXML
    protected MenuItem studentExam;
    @FXML
    protected MenuItem Certificate;
    @FXML
    protected MenuItem Recomm;
    @FXML
    protected MenuItem WithdrawalEducation;
    @FXML
    protected MenuItem accommodation;

    public void initialize() throws IOException, ParseException {
        log.info("Open Master student main page");
        timer.playFromStart();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds());
        timer.setOnFinished(actionEvent -> {
            actionEvent.consume();
            try {
                logOut();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        log.info("logged in");
        getLoginTime();
        setUserImage();
        timeDisplay();
        educationalStatus.setText(getEducationalStatus());
        supervisor.setText(getSupervisor());
        lastTimeLogIn.setText("last log in : " + getLoginTime());
        username.setText("User : " + getUsername());
        email.setText("Email : " + getEmail());
        educationalStatus.setText(getEducationalStatus());
        supervisor.setText(getSupervisor());
        messageForAdmin.setText(null);
        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show chat box auto request"));


        Thread ping = new Thread(new Runnable() {
            public void run() {
                while (true) {
                    if (ServerMode.getInstance().isOnline()) {
                        Platform.runLater(() -> {
                            serverCondition.setText("Server is online");
                            messageChatField.setVisible(true);
                            sendChatBtn.setVisible(true);
                            toWhoCombo.setVisible(true);
                            sendChatBtn.setVisible(true);
                            sendImageBtn.setVisible(true);
                            sendVoiceBtn.setVisible(true);
                            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "show message of mohseni"));
                            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName(), "get all members for chat combo"));
                        });

                    } else {
                        Platform.runLater(() -> {
                            messageChatField.setVisible(false);
                            sendChatBtn.setVisible(false);
                            toWhoCombo.setVisible(false);
                            sendChatBtn.setVisible(true);
                            sendImageBtn.setVisible(true);
                            sendVoiceBtn.setVisible(true);
                            serverCondition.setText("server is offline");
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

    public void showMembersCombo(String member) {
        String[] data = member.split("-");
        toWhoCombo.getItems().addAll(data);
    }

    public void showMohseniMessageDesk(String content) {
        messageMohseni.setText(content + '\n');
        messageMohseni.setScrollTop(Double.MAX_VALUE);
    }

    @FXML
    protected void accommodationClicked() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Accommodation clicked");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/accommodation-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setStageProp(stage, scene);
                ClientLogic.getInstance().setAccommodationDesk(loader, stage);
            });
        }
    }

    @FXML
    protected void withdrawalClicked() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Withdrawal clicked");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/withdrawalFromEducation-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setStageProp(stage, scene);
                ClientLogic.getInstance().setWithdrawalFromEducationDesk(loader, stage);
            });
        }
    }

    @FXML
    protected void certificateClicked() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Certificate clicked");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/ApplyCertificateEmployment-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setStageProp(stage, scene);
                ClientLogic.getInstance().setApplyCertificateEmploymentDesk(loader, stage);
            });
        }
    }

    @FXML
    protected void recommendationClicked() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Recommendation request clicked");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/Recommendation-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setStageProp(stage, scene);
                ClientLogic.getInstance().setRecommendation(loader, stage);
            });
        }
    }

    @FXML
    protected void scheduleClicked() throws IOException {
        log.info("Current user weekly schedule clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/weeklySchedule-view.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        if (ServerMode.getInstance().isOnline()) {
            ClientLogic.getInstance().setWeeklySchedule(loader, stage);
        }
    }

    @FXML
    protected void examClicked() throws IOException {
        log.info("Current user exam clicked");
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/examsLists.fxml"));
        Scene scene = new Scene(loader.load());
        setStageProp(stage, scene);
        if (ServerMode.getInstance().isOnline()) {
            ClientLogic.getInstance().setExamsList(loader, stage);
        }
    }

    public void logOut() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Logged out out of time");
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/logOut.fxml"));
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

    public void logoutClicked(ActionEvent actionEvent) throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Current user logged out by him self");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                stage = ((Stage) ((Node) (actionEvent.getSource())).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/login-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                setStageProp(stage, scene);
                try {
                    ClientLogic.getInstance().setLogin(loader, stage);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    private void setStageProp(Stage stage, Scene scene) {
        int height = Constants.CONFIG.getProperty(Integer.class, "stageHeight");
        int width = Constants.CONFIG.getProperty(Integer.class, "stageWidth");
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
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
                if (strLine.contains("StudentMasterDesk")) {
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
        log.info("Current user email");
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskEmail(massageStudentMasterDesk);
    }

    protected String getUsername() throws IOException, ParseException {
        log.info("Current user name");
        MassageInNetwork massageStudentMasterDesk = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().userDeskUserName(massageStudentMasterDesk);
    }

    protected String getEducationalStatus() throws IOException, ParseException {
        log.info("Current user education status");
        MassageInNetwork EducationalStatus = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().educationalStatus(EducationalStatus);
    }

    protected String getSupervisor() throws IOException, ParseException {
        log.info("Current user supervisor");
        MassageInNetwork supervisor = new MassageInNetwork(CurrentUser.getInstance().getUserName(), null, null);
        return Controller.getInstance().supervisor(supervisor);
    }

    public void setUserImage() throws IOException, ParseException {
        if (String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")) == null) {
            log.info("Show image of user");
            int height = Constants.CONFIG.getProperty(Integer.class, "imageHeightUser");
            int width = Constants.CONFIG.getProperty(Integer.class, "imageWidthUser");
            ImageView userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/" + getUsername() + ".png")));
            userImage.setFitHeight(height);
            userImage.setFitWidth(width);
            noidea.getChildren().add(userImage);
        } else {
            log.info("Show default image");
            int height = Constants.CONFIG.getProperty(Integer.class, "imageHeightUser");
            int width = Constants.CONFIG.getProperty(Integer.class, "imageWidthUser");
            ImageView userImage = new ImageView(String.valueOf(ClientMain.class.getResource("images/default.png")));
            userImage.setFitHeight(height);
            userImage.setFitWidth(width);
            noidea.getChildren().add(userImage);
        }
    }

    public void lessonListsClicked() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Current user lessons list clicked");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                        , "change fxml to lessonList-view fxml"));
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/lessonLists-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int height = Constants.CONFIG.getProperty(Integer.class, "stageHeight");
                int width = Constants.CONFIG.getProperty(Integer.class, "stageWidth");
                stage.setHeight(height);
                stage.setWidth(width);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("educational system");
                stage.show();
                ClientLogic.getInstance().setLessonListDesk(loader, stage);
            });
        }
    }

    public void teachersListsClicked() throws IOException {
        if (ServerMode.getInstance().isOnline()) {
            Platform.runLater(() -> {
                log.info("Current user teachers lists clicked");
                timer.pause();
                CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                        , "change fxml to teacherList-view fxml"));
                stage = ((Stage) (email).getScene().getWindow());
                FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/teacherLists-view.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(loader.load());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                int height = Constants.CONFIG.getProperty(Integer.class, "stageHeight");
                int width = Constants.CONFIG.getProperty(Integer.class, "stageWidth");
                stage.setHeight(height);
                stage.setWidth(width);
                stage.setResizable(false);
                stage.setScene(scene);
                stage.setTitle("educational system");
                stage.show();
                ClientLogic.getInstance().setTeachersListDesk(loader, stage);
            });
        }
    }

    public void profileClicked(ActionEvent actionEvent) throws IOException {
        log.info("Current user profile clicked");
        if (ServerMode.getInstance().isOnline()) {
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName()
                    , "change fxml to profile fxml"));
        }
        timer.pause();
        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds() - (int) timer.getCurrentTime().toSeconds());
        stage = ((Stage) (email).getScene().getWindow());
        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/studentProfile.fxml"));
        Scene scene = new Scene(loader.load());
        int height = Constants.CONFIG.getProperty(Integer.class, "stageHeight");
        int width = Constants.CONFIG.getProperty(Integer.class, "stageWidth");
        stage.setHeight(height);
        stage.setWidth(width);
        stage.setResizable(false);
        stage.setScene(scene);
        stage.setTitle("educational system");
        stage.show();
        if (ServerMode.getInstance().isOnline()) {
            ClientLogic.getInstance().setStudentProfile(loader, stage);
        }
    }

    public void temporaryScoreClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Current user temporary score clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/temporaryScoreStudent-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        stage.setHeight(650);
//        stage.setWidth(800);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.setTitle("educational system");
//        stage.show();
    }

    public void studentsStatusClicked(ActionEvent actionEvent) throws IOException {
        //ToDo incomplete from previous phase
//        log.info("Current user students status clicked");
//        timer.pause();
//        CurrentUser.getInstance().setTimer((int) timer.getDuration().toSeconds()-(int) timer.getCurrentTime().toSeconds());
//        stage = ((Stage) (email).getScene().getWindow());
//        FXMLLoader loader = new FXMLLoader(ClientMain.class.getResource("fxml/StudentsEducationalStatus-view.fxml"));
//        Scene scene = new Scene(loader.load());
//        stage.setHeight(650);
//        stage.setWidth(800);
//        stage.setResizable(false);
//        stage.setScene(scene);
//        stage.setTitle("educational system");
//        stage.show();
    }

    public void submitForAdmin(ActionEvent actionEvent) {
        if (messageForAdmin.getText() != null) {
            alertMessageForAdmin.setText(null);
            Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), CurrentUser.getInstance().getUserName() + "-" +
                    messageForAdmin.getText(), "message for admin"));
            alertMessageForAdmin.setText("Done!");
            messageForAdmin.clear();
        } else {
            alertMessageForAdmin.setText("First write your message!");
        }
    }

    public void sendChatBtnClicked(ActionEvent actionEvent) {
        Platform.runLater(()->{
            LocalTime time = LocalTime.now();
            String[] time2 = String.valueOf(time).split("\\.");
            if (messageChatField.getText()!= null || !Objects.equals(messageChatField.getText(), "")){
                chatBoxTextArea.setText(chatBoxTextArea.getText() + CurrentUser.getInstance().getUserName() +" : "+
                        messageChatField.getText()+"   " + time2[0] + '\n');
                chatBoxTextArea.setScrollTop(Double.MAX_VALUE);
                Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), whichMember +"-"+ CurrentUser.getInstance().getUserName()
                        +"-"+messageChatField.getText()+"-"+time2[0], "write message of chatBox"));
            }
            messageChatField.setText(null);
        });
    }
    public void readChatSend(String content){
        Platform.runLater(()->{
            String[] data = content.split("-");
            if (data.length == 4) {
                String toWho = data[0];
                String whoSend = data[1];
                String whatMessage = data[2];
                String time = data[3];
                chatBoxTextArea.setText(chatBoxTextArea.getText() + whoSend + ":" + whatMessage + "    " + time + '\n');
                chatBoxTextArea.setScrollTop(Double.MAX_VALUE);
            }else{
                String toWho = data[0];
                String whoSend = data[1];
                String kind = data[3];
                String whatMessage = data[2];
                String time = data[4];
                if (Objects.equals(kind, "image")){
                    Image image = ImageMessage.Image.decode(whatMessage);
                    userPhoto.setImage(image);
                }
                if (Objects.equals(kind, "voice")){

                }
            }
        });
    }

    public void toWhoComboClicked(ActionEvent actionEvent) {
        Platform.runLater(() -> {
            whichMember = (String) toWhoCombo.getValue();
        });
    }
    public void readChatSendInit(String content) {
        Platform.runLater(() -> {
            String[] parts = content.split("::");
            for (String message : parts) {
                String[] eachMessage = message.split("-");
                System.out.println(eachMessage.length);
                if (eachMessage.length == 4) {
                    chatBoxTextArea.setText(chatBoxTextArea.getText() + eachMessage[1] + " : " + eachMessage[2] + "   " + eachMessage[3] + '\n');
                    chatBoxTextArea.setScrollTop(Double.MAX_VALUE);
                } else {
                    if (Objects.equals(eachMessage[3], "image")) {
                        Image image = ImageMessage.Image.decode(eachMessage[2]);
                        userPhoto.setImage(image);
                    }
                    if (Objects.equals(eachMessage[3], "voice")) {

                    }
                }

            }
        });
    }
    public void sendVoiceClicked(ActionEvent actionEvent) {
        Platform.runLater(()->{
            LocalTime time = LocalTime.now();
            String[] time2 = String.valueOf(time).split("\\.");
            if (messageChatField.getText()!= null || !Objects.equals(messageChatField.getText(), "")){
                //ToDO voice message

            }
            messageChatField.setText(null);
        });
    }

    public void sendImageClicked(ActionEvent actionEvent) {
        Platform.runLater(()->{
            LocalTime time = LocalTime.now();
            String[] time2 = String.valueOf(time).split("\\.");
            String random = String.valueOf((int) (Math.random() * 1000));
            if (chatBoxTextArea.getText() != null || !Objects.equals(chatBoxTextArea.getText(), "")) {
                //ToDo image message
                stage = new Stage();
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("choose an image");
                fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.jpg"));
                File selectedFile = fileChooser.showOpenDialog(stage);
                if (chatBoxTextArea.getText() != null || !Objects.equals(chatBoxTextArea.getText(), "")) {
                    if (selectedFile != null) {

                        String image = ImageMessage.Image.encode(selectedFile.toURI().toString());
                        javafx.scene.image.Image originalPhoto = new javafx.scene.image.Image(selectedFile.toURI().toString());
                        Client.getClient().sendMessage(new Message(Client.getClient().getAuthToken(), whichMember + "-" + CurrentUser.getInstance().getUserName()
                                + "-" + image + "-image" + "-" + time2[0], "write message image of chatBox"));
                        userPhoto.setImage(originalPhoto);
                    }
                    Image imageToBeSaved = userPhoto.getImage();
                    File file = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\edu\\system\\images\\" + random + ".jpg");
                    try {
                        ImageIO.write(SwingFXUtils.fromFXImage(imageToBeSaved, null), "jpg", file);
                    } catch (IOException e) {
                        log.error("exception happened", e);
                        e.printStackTrace();
                    }
                }
            }
            chatBoxTextArea.setText(null);
        });
    }
}
