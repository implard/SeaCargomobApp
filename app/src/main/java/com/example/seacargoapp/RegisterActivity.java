package com.example.seacargoapp;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.seacargoapp.AuthApi;
import com.example.seacargoapp.models.Users;
import com.google.android.material.textfield.TextInputEditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private TextInputEditText etUsername, etEmail, etPassword;
    private AuthApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername = findViewById(R.id.loginField);
        etEmail = findViewById(R.id.emailField);
        etPassword = findViewById(R.id.passField);
        findViewById(R.id.registerButton).setOnClickListener(v -> register());

        api = SupabaseClient.getClient().create(AuthApi.class);
        findViewById(R.id.tvHaveAccount).setOnClickListener(v -> finish());
    }

    private void register() {
        String username = etUsername.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String pass = etPassword.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || pass.length() < 6) {
            Toast.makeText(this, "Проверьте данные (пароль ≥6)", Toast.LENGTH_LONG).show();
            return;
        }

        Users newUser = new Users(username, email, pass);

        api.registerUser(newUser).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // 200–299 = всё ок, пользователь создан
                    Toast.makeText(RegisterActivity.this, "Регистрация успешна!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    // Здесь уже точно ошибка (409, 400 и т.д.)
                    if (response.code() == 409) {
                        Toast.makeText(RegisterActivity.this, "Пользователь с таким логином/email уже существует", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(RegisterActivity.this, "Ошибка: " + response.code() + " " + response.message(), Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Нет сети: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}