package com.example.seacargoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seacargoapp.models.Cargo;
import com.example.seacargoapp.CargoApi;
import com.example.seacargoapp.SupabaseClient;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerCargo;
    private ProgressBar progressHome;
    private View emptyView;
    private TextView tvWelcome;
    private CargoAdapter adapter;
    private CargoApi cargoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.tvLogout).setOnClickListener(v -> logout());

        // Views
        recyclerCargo = findViewById(R.id.recyclerCargo);
        progressHome = findViewById(R.id.progressHome);
        emptyView = findViewById(R.id.emptyView);
        tvWelcome = findViewById(R.id.tvWelcome);
        ExtendedFloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        String username = prefs.getString("username", "Друг");
        tvWelcome.setText("Привет, " + username + "!");

        adapter = new CargoAdapter();
        recyclerCargo.setLayoutManager(new LinearLayoutManager(this));
        recyclerCargo.setAdapter(adapter);

        cargoApi = SupabaseClient.getClient().create(CargoApi.class);

        fabAdd.setOnClickListener(v ->
                Toast.makeText(this, "Добавление груза — скоро будет!", Toast.LENGTH_SHORT).show());

        loadAllCargo();
    }

    private void loadAllCargo() {
        progressHome.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        recyclerCargo.setVisibility(View.VISIBLE);

        cargoApi.getAllCargo().enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                progressHome.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Cargo> cargos = response.body();
                    adapter.updateData(cargos);

                    if (cargos.isEmpty()) {
                        emptyView.setVisibility(View.VISIBLE);
                        recyclerCargo.setVisibility(View.GONE);
                    }
                } else {
                    emptyView.setVisibility(View.VISIBLE);
                    recyclerCargo.setVisibility(View.GONE);
                    Toast.makeText(HomeActivity.this, "Ошибка загрузки", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                progressHome.setVisibility(View.GONE);
                emptyView.setVisibility(View.VISIBLE);
                recyclerCargo.setVisibility(View.GONE);
                Toast.makeText(HomeActivity.this, "Нет интернета", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logout() {
        getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();
        startActivity(new Intent(this, LoginActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllCargo();
    }
}