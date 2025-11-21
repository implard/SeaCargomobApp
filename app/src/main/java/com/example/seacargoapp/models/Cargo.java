package com.example.seacargoapp.models;

import com.google.gson.annotations.SerializedName;

public class Cargo {
    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("weight") private String weight;
    @SerializedName("sender") private String sender;
    @SerializedName("receiver") private String receiver;
    @SerializedName("user_id") private int userId;  // ВАЖНО: в Supabase скорее всего user_id, а не userId!
    @SerializedName("status") private String status;

    public Cargo() {}

    public Cargo(String name, String weight, String sender, String receiver, int userId, String status) {
        this.name = name;
        this.weight = weight;
        this.sender = sender;
        this.receiver = receiver;
        this.userId = userId;
        this.status = status;
    }

    // геттеры...
    public int getId() { return id; }
    public String getName() { return name; }
    public String getWeight() { return weight; }
    public String getSender() { return sender; }
    public String getReceiver() { return receiver; }
    public int getUserId() { return userId; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return name + " (" + weight + ")";
    }
}