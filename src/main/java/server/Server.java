package server;

import authToken.AuthenticationToken;
import constants.Constants;
import message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {

    private static Server server;


    private final ArrayList<ClientHandler> clientHandlers;

    public Server(){
        clientHandlers = new ArrayList<>();
        Server.server = this;
    }


    public static Server getServer() {
        return server;
    }

    public ArrayList<ClientHandler> getClientHandlers() {
        return clientHandlers;
    }

    public void init() {

        System.out.println("Server is running...");
        try {
            int port = Constants.CONFIG.getProperty(Integer.class, "serverPort");
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                System.out.println("waiting for a connection...");
                Socket socket = serverSocket.accept();
                addNewClientHandler(socket);
                System.out.println("====> There are " + clientHandlers.size() + " clients on the server!");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void addNewClientHandler(Socket socket) throws IOException {
        ClientHandler clientHandler = new ClientHandler(socket, AuthenticationToken.generateNewToken());
        clientHandlers.add(clientHandler);
        //ServerLogic.getInstance().sendUsernames();
        new Thread(clientHandler).start();
    }
}
