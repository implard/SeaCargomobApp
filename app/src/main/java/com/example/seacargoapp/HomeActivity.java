package com.example.seacargoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome, tvLogout;
    private RecyclerView recyclerCargo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvWelcome = findViewById(R.id.tvWelcome);
        tvLogout = findViewById(R.id.tvLogout);
        recyclerCargo = findViewById(R.id.recyclerCargo);

        SharedPreferences sp = getSharedPreferences("user_session", MODE_PRIVATE);
        String username = sp.getString("username", "Пользователь");
        tvWelcome.setText("Привет, " + username + "!");

        tvLogout.setOnClickListener(v -> {
            sp.edit().clear().apply();
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });

        recyclerCargo.setLayoutManager(new LinearLayoutManager(this));

        Toast.makeText(this, "Добро пожаловать!", Toast.LENGTH_SHORT).show();
    }
}