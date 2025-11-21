// HomeActivity.java — ВСЕ ГРУЗЫ ДЛЯ ВСЕХ
package com.example.seacargoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.seacargoapp.CargoAdapter;
import com.example.seacargoapp.CargoApi;
import com.example.seacargoapp.models.Cargo;
import com.example.seacargoapp.SupabaseClient;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerCargo;
    private ProgressBar progressHome;
    private TextView tvEmpty;
    private CargoAdapter adapter;
    private CargoApi cargoApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom);

        // Toolbar + выход
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        findViewById(R.id.tvLogout).setOnClickListener(v -> logout());

        // Views
        recyclerCargo = findViewById(R.id.recyclerCargo);
        progressHome = findViewById(R.id.progressHome);
        tvEmpty = findViewById(R.id.tvEmpty);
        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);

        // RecyclerView
        adapter = new CargoAdapter();
        recyclerCargo.setLayoutManager(new LinearLayoutManager(this));
        recyclerCargo.setAdapter(adapter);

        // API
        cargoApi = SupabaseClient.getClient().create(CargoApi.class);

        // Кнопка добавления (пока тост)
        fabAdd.setOnClickListener(v ->
                Toast.makeText(this, "Добавление груза — скоро!", Toast.LENGTH_SHORT).show());

        // Загружаем ВСЕ грузы
        loadAllCargo();
    }

    private void loadAllCargo() {
        progressHome.setVisibility(android.view.View.VISIBLE);
        tvEmpty.setVisibility(android.view.View.GONE);

        // ←←← ВОТ ЭТА СТРОКА — ГЛАВНОЕ ИЗМЕНЕНИЕ ←←←
        cargoApi.getAllCargo().enqueue(new Callback<List<Cargo>>() {
            @Override
            public void onResponse(Call<List<Cargo>> call, Response<List<Cargo>> response) {
                progressHome.setVisibility(android.view.View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    List<Cargo> list = response.body();

                    adapter.updateData(list);

                    if (list.isEmpty()) {
                        tvEmpty.setText("В базе пока нет грузов");
                        tvEmpty.setVisibility(android.view.View.VISIBLE);
                    } else {
                        tvEmpty.setVisibility(android.view.View.GONE);
                    }

                    Toast.makeText(HomeActivity.this,
                            "Загружено грузов: " + list.size(), Toast.LENGTH_SHORT).show();
                } else {
                    tvEmpty.setVisibility(android.view.View.VISIBLE);
                    Toast.makeText(HomeActivity.this,
                            "Ошибка: " + response.code(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cargo>> call, Throwable t) {
                progressHome.setVisibility(android.view.View.GONE);
                tvEmpty.setVisibility(android.view.View.VISIBLE);
                Toast.makeText(HomeActivity.this,
                        "Нет интернета: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void logout() {
        getSharedPreferences("user_session", MODE_PRIVATE).edit().clear().apply();
        Intent intent = new Intent(this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadAllCargo();
    }
}