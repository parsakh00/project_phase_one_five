package message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Message {


    private String authToken;
    private String request;
    private String content;

    public Message(String authToken, String content, String request) {
        this.authToken = authToken;
        this.content = content;
        this.request = request;
    }

    public String toJson(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(this)+'\n'+"over";
    }

    public static Message jsonTOMessage(String json){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(json, Message.class);
    }

    public String getAuthToken() {
        return authToken;
    }

    public String getRequest() {
        return request;
    }

    public String getContent() {
        return content;
    }


}
