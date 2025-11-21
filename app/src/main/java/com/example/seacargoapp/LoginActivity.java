package com.example.seacargoapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seacargoapp.AuthApi;
import com.example.seacargoapp.models.Users;
import com.example.seacargoapp.SupabaseClient;
import com.google.android.material.textfield.TextInputEditText;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etLogin, etPassword;
    private View btnLogin, progressBar;
    private View tvError;
    private AuthApi authApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Поле теперь логин, а не email
        etLogin = findViewById(R.id.etEmail); // оставляем ID, но меняем hint в layout
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        progressBar = findViewById(R.id.progress);
        tvError = findViewById(R.id.tvError);

        findViewById(R.id.tvRegister).setOnClickListener(v ->
                startActivity(new Intent(this, RegisterActivity.class)));

        authApi = SupabaseClient.getClient().create(AuthApi.class);

        btnLogin.setOnClickListener(v -> attemptLogin());
    }

    private void attemptLogin() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();

        if (login.isEmpty() || password.isEmpty()) {
            showError("Введите логин и пароль");
            return;
        }

        showProgress(true);

        // Ищем пользователя по username=eq.ваш_логин
        authApi.getUserByUsername("eq." + login).enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                showProgress(false);

                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Users user = response.body().get(0);

                    if (password.equals(user.getPassword())) {
                        saveUserSession(user);
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        finish();
                    } else {
                        showError("Неверный пароль");
                    }
                } else {
                    showError("Пользователь не найден");
                }
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
                showProgress(false);
                showError("Нет интернета или ошибка сервера");
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void saveUserSession(Users user) {
        SharedPreferences prefs = getSharedPreferences("user_session", MODE_PRIVATE);
        prefs.edit()
                .putInt("user_id", user.getId())
                .putString("username", user.getUsername())
                .putString("email", user.getEmail())
                .putBoolean("is_logged_in", true)
                .apply();
    }

    private void showProgress(boolean show) {
        progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
        btnLogin.setEnabled(!show);
    }

    private void showError(String msg) {
        if (tvError instanceof android.widget.TextView) {
            ((android.widget.TextView) tvError).setText(msg);
        }
        tvError.setVisibility(View.VISIBLE);
    }
}