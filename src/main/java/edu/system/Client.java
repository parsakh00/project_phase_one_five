package edu.system;

import message.Message;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client implements Runnable{
    private static Client client;
    private final Socket socket;
    private String name;
    private final PrintWriter printWriter;
    private final Scanner scanner;
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

        while (true) {
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
