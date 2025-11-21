package com.example.seacargoapp;

import com.example.seacargoapp.models.Users;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApi {

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtvb2xndWd5YWdzc2d5ampkdGp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2ODU1NTcsImV4cCI6MjA3OTI2MTU1N30.mhERHLRVpHpUk_CAhXHkggA66P12Z5vkFZd9wzTnAB8",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtvb2xndWd5YWdzc2d5ampkdGp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2ODU1NTcsImV4cCI6MjA3OTI2MTU1N30.mhERHLRVpHpUk_CAhXHkggA66P12Z5vkFZd9wzTnAB8",
            "Content-Type: application/json"
    })
    @GET("users")
    Call<List<Users>> getUserByEmail(@Query("email") String email);

    @Headers({
            "apikey: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtvb2xndWd5YWdzc2d5ampkdGp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2ODU1NTcsImV4cCI6MjA3OTI2MTU1N30.mhERHLRVpHpUk_CAhXHkggA66P12Z5vkFZd9wzTnAB8",
            "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imtvb2xndWd5YWdzc2d5ampkdGp1Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2ODU1NTcsImV4cCI6MjA3OTI2MTU1N30.mhERHLRVpHpUk_CAhXHkggA66P12Z5vkFZd9wzTnAB8",
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("users")
    Call<List<Users>> registerUser(@Body Users user);
}