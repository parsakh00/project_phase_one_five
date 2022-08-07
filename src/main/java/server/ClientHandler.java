package server;

import message.Message;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientHandler implements Runnable{

    private final Socket socket;
    private final PrintWriter out;
    private final String authToken;
    private String username;

    public ClientHandler(Socket socket, String authToken) throws IOException {
        this.socket = socket;
        this.out = new PrintWriter(socket.getOutputStream());
        this.authToken = authToken;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAuthToken() {
        return authToken;
    }

    @Override
    public void run() {
        System.out.println("ClientHandler has been created!");
        this.sendMessage(new Message(this.authToken, this.authToken, "authToken"));
        try {
            Scanner in = new Scanner(socket.getInputStream());
            while (true) {
                // System.out.println("ClientHandler is waiting for a message.");
                String messageFromClient = in.nextLine();
                while (true) {
                    String nextLine = in.nextLine();
                    if (nextLine.equals("over")) break;
                    messageFromClient += nextLine;
                }
                System.out.println("Message from Client: " + messageFromClient);
                Message message = Message.jsonTOMessage(messageFromClient);
                //analyse the message:
                ServerLogic.getInstance().analyse(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public void sendMessage(Message message) {
        out.println(message.toJson());
        out.flush();
    }

    public void kill()  {
        try {
            Server.getServer().getClientHandlers().remove(this);
            socket.close();
            //ServerLogic.getInstance().sendUsernames();
            System.out.println("client killed");
            System.out.println(Server.getServer().getClientHandlers().size());
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
