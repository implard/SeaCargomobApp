
package com.example.seacargoapp.models;

import com.google.gson.annotations.SerializedName;

public class Cargo {

    @SerializedName("id") private int id;
    @SerializedName("name") private String name;
    @SerializedName("weight") private String weight;
    @SerializedName("sender") private String sender;
    @SerializedName("receiver") private String receiver;
    @SerializedName("user_id") private int userId;
    @SerializedName("status") private String status;

    public Cargo() {}

    public int getId() { return id; }
    public String getName() { return name != null ? name : "Без названия"; }
    public String getWeight() { return weight != null ? weight : "—"; }
    public String getSender() { return sender != null ? sender : "Не указано"; }
    public String getReceiver() { return receiver != null ? receiver : "Не указано"; }
    public String getStatus() { return status != null ? status : "В пути"; }
}