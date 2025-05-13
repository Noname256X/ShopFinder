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

public class AuthorizationActivity extends AppCompatActivity {
    private EditText nicknameField, passwordField;
    private Button logInButton, goBackButton;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorization_page);

        // Инициализация полей
        nicknameField = findViewById(R.id.nicknameField);
        passwordField = findViewById(R.id.passwordField);
        logInButton = findViewById(R.id.log_in_button);
        goBackButton = findViewById(R.id.auth_go_back_but);

        // Обработчик кнопки авторизации
        logInButton.setOnClickListener(v -> {
            String nickname = nicknameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (validateInputs(nickname, password)) {
                authenticateUser(nickname, password);
            }
        });

        // Обработчик кнопки "Назад"
        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(AuthorizationActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private boolean validateInputs(String nickname, String password) {
        if (nickname.isEmpty()) {
            showToast("Введите никнейм");
            return false;
        }

        if (password.isEmpty()) {
            showToast("Введите пароль");
            return false;
        }

        return true;
    }

    private void authenticateUser(String nickname, String password) {
        // Создаем JSON объект с данными пользователя
        JSONObject json = new JSONObject();
        try {
            json.put("nickname", nickname);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Создаем запрос
        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("http://192.168.1.4:3000/api/auth/login") // Убедитесь, что URL правильный
                .post(body)
                .build();

        // Отправляем запрос асинхронно
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
                            showToast("Авторизация успешна!");
                            startActivity(new Intent(AuthorizationActivity.this, SearchActivity.class));
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