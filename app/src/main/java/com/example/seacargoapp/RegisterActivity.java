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
        String pass = etPassword.getText().toString();

        if (username.isEmpty() || email.isEmpty() || pass.length() < 6) {
            Toast.makeText(this, "Проверьте данные (пароль ≥6)", Toast.LENGTH_LONG).show();
            return;
        }

        Users newUser = new Users(username, email, pass);

        api.registerUser(newUser).enqueue(new Callback<java.util.List<Users>>() {
            @Override
            public void onResponse(Call<java.util.List<Users>> call, Response<java.util.List<Users>> response) {
                if (response.isSuccessful() && response.body() != null && !response.body().isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Регистрация успешна!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "Ошибка (возможно email уже занят)", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<java.util.List<Users>> call, Throwable t) {
                Toast.makeText(RegisterActivity.this, "Нет сети: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}