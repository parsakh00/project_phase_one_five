package edu.system;

import ServerRunning.ServerMode;
import message.Message;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Client implements Runnable{
    private static Client client;
    private Socket socket;
    private String name;
    private PrintWriter printWriter;
    private Scanner scanner;
    private String authToken;

    public Client(Socket socket) throws IOException {
        this.socket = socket;
        this.printWriter = new PrintWriter(socket.getOutputStream());
        this.scanner = new Scanner(socket.getInputStream());
        Client.client = this;
    }

    private void init() throws IOException, ParseException, InterruptedException {
//        Message initialMessage = new Message(null,"I am connecting","DD");
//        printWriter.println(initialMessage.toJson());
//        printWriter.flush();
        ServerMode.getInstance().turnServerOn();
        while (true) {
            try {
                String input = scanner.nextLine();
                while (true) {
                    String nextLine = scanner.nextLine();
                    if (nextLine.equals("over")) break;
                    input += nextLine;
                }

                Message message = Message.jsonTOMessage(input);
                System.out.println("Message from server : " + input);
                //use client logic to analyse
                ClientLogic.getInstance().analyse(message);
            }catch (NoSuchElementException e){

                try {
                    Socket newSocket = new Socket("localhost", 8080);
                    this.socket=newSocket;
                    this.printWriter = new PrintWriter(socket.getOutputStream());
                    this.scanner = new Scanner(socket.getInputStream());
                    ServerMode.getInstance().turnServerOn();
                    System.out.println("connected");
                }catch (ConnectException e2){
                    ServerMode.getInstance().switchServerDown();
                    //if (!ServerMode.getInstance().isOnline()) System.out.println("server is offline");
                }
            }
        }

    }

    public static Client getClient() {
        return client;
    }

    public void sendMessage(Message message) {
        printWriter.println(message.toJson());
        printWriter.flush();
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public void run() {
        try {
            init();
        } catch (IOException | ParseException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
