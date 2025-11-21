// api/AuthApi.java
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

    // Вход по username
    @Headers({
            "apikey: " + com.example.seacargoapp.SupabaseClient.API_KEY,
            "Authorization: " + com.example.seacargoapp.SupabaseClient.AUTH_HEADER
    })
    @GET("users?select=*")
    Call<List<Users>> getUserByUsername(@Query("username") String usernameEq);

    // Регистрация (остаётся как есть)
    @Headers({
            "apikey: " + com.example.seacargoapp.SupabaseClient.API_KEY,
            "Authorization: " + com.example.seacargoapp.SupabaseClient.AUTH_HEADER,
            "Content-Type: application/json",
            "Prefer: return=representation"
    })
    @POST("users")
    Call<List<Users>> registerUser(@Body Users user);
}