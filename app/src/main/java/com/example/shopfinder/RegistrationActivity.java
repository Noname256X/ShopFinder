package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import okhttp3.*;
import org.json.JSONObject;
import java.io.IOException;

public class RegistrationActivity extends AppCompatActivity {
    private EditText nicknameField, emailField, passwordField;
    private Button signUpButton, goBackButton;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_page);

        nicknameField = findViewById(R.id.nicknameField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signUpButton = findViewById(R.id.sing_in_button);
        goBackButton = findViewById(R.id.reg_go_back_but);

        signUpButton.setOnClickListener(v -> {
            String nickname = nicknameField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (validateInputs(nickname, email, password)) {
                registerUser(nickname, email, password);
            }
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs(String nickname, String email, String password) {
        if (nickname.isEmpty()) {
            showToast("Введите никнейм");
            return false;
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            showToast("Введите корректный email");
            return false;
        }

        if (password.isEmpty() || password.length() < 6) {
            showToast("Пароль должен содержать минимум 6 символов");
            return false;
        }

        return true;
    }

    private void registerUser(String nickname, String email, String password) {
        JSONObject json = new JSONObject();
        try {
            json.put("nickname", nickname);
            json.put("email", email);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("http://192.168.1.4:3000/api/auth/register")
                .post(body)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        showToast("Ошибка соединения: " + e.getMessage())
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseData = response.body().string();
                runOnUiThread(() -> {
                    try {
                        JSONObject jsonResponse = new JSONObject(responseData);
                        if (response.isSuccessful()) {
                            showToast("Регистрация успешна!");
                            startActivity(new Intent(RegistrationActivity.this, SearchActivity.class));
                            finish();
                        } else {
                            showToast(jsonResponse.getString("error"));
                        }
                    } catch (Exception e) {
                        showToast("Ошибка обработки ответа");
                    }
                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}