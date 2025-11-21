// SupabaseClient.java
package com.example.seacargoapp;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SupabaseClient {
    private static final String BASE_URL = "https://zanmveyjbwivffbcokqm.supabase.co/rest/v1/";

    private static Retrofit retrofit;

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static final String API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inphbm12ZXlqYndpdmZmYmNva3FtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2ODc4NDMsImV4cCI6MjA3OTI2Mzg0M30.jfglm2PyqSGtkcw7y1VL34qAuKRus4y7WnXMqKWrFlU";

    public static final String AUTH_HEADER = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Inphbm12ZXlqYndpdmZmYmNva3FtIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM2ODc4NDMsImV4cCI6MjA3OTI2Mzg0M30.jfglm2PyqSGtkcw7y1VL34qAuKRus4y7WnXMqKWrFlU";
}