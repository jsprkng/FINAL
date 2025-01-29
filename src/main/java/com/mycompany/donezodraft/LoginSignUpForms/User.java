package com.mycompany.donezodraft.LoginSignUpForms;
public class User {
    private String name;
    private String username;
    private String password;

    // Constructor
    public User(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    // Getters and Setters (optional but recommended)
    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}