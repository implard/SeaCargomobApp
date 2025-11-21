// CargoApi.java
package com.example.seacargoapp;

import com.example.seacargoapp.models.Cargo;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface CargoApi {

    @Headers({
            "apikey: " + com.example.seacargoapp.SupabaseClient.API_KEY,
            "Authorization: " + com.example.seacargoapp.SupabaseClient.AUTH_HEADER
    })
    @GET("cargo?select=*")
    Call<List<Cargo>> getAllCargo();

}