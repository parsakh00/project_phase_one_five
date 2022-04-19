package edu.system.logic;

//for generate primitive user data

public class User {

    public String username;
    public String password;
    public String type;
    public String email;

    public String degree;
    public User(String username, String password, String type, String degree, String email) {
        this.username = username;
        this.password = password;
        this.type = type;
        this.email = email;
        this.degree = degree;
    }
}
