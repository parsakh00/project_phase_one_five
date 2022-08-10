package ServerRunning;

public class ServerMode {

    Boolean isOnline;

    private static ServerMode serverMode;

    private ServerMode(){
        this.isOnline = true;
    }
    public static ServerMode getInstance() {
        if (serverMode == null) {
            serverMode = new ServerMode();
        }
        return serverMode;
    }

    public void switchServerDown(){
        this.isOnline = false;
    }
    public void turnServerOn(){
        this.isOnline = true;
    }

    public Boolean isOnline() {
        return isOnline;
    }
}
