package com.ali.pf_monthly_task.models;

public class Login {

    private float id;
    private String email;
    private String username;
    private String password;



    // Getter Methods



    public float getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    // Setter Methods



    public void setId(float id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

