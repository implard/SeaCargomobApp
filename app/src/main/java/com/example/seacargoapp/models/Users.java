package com.example.seacargoapp.models;

import com.google.gson.annotations.SerializedName;

public class Users {

    @SerializedName("id")           private Integer id;
    @SerializedName("username")     private String username;
    @SerializedName("email")        private String email;
    @SerializedName("password")     private String password;
    @SerializedName("role")         private String role;
    @SerializedName("created_at")   private String created_at;

    public Users() {}

    public Users(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.role = "user";
    }

    public Integer getId() { return id != null ? id : 0; }
    public String getUsername() { return username; }
    public String getEmail() { { return email; } }
    public String getPassword() { return password; }
    public String getRole() { return role; }
}