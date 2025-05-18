package com.example.shopfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

        nicknameField = findViewById(R.id.nicknameField);
        passwordField = findViewById(R.id.passwordField);
        logInButton = findViewById(R.id.log_in_button);
        goBackButton = findViewById(R.id.auth_go_back_but);

        logInButton.setOnClickListener(v -> {
            String nickname = nicknameField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (validateInputs(nickname, password)) {
                authenticateUser(nickname, password);
            }
        });

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
        JSONObject json = new JSONObject();
        try {
            json.put("nickname", nickname);
            json.put("password", password);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                json.toString(),
                MediaType.parse("application/json; charset=utf-8")
        );

        Request request = new Request.Builder()
                .url("http://192.168.1.4:3000/api/auth/login")
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

                        Log.d("AuthResponse", "Response: " + responseData);

                        if (response.isSuccessful()) {
                            // Проверяем наличие user_id в ответе
                            if (jsonResponse.has("userId") || jsonResponse.has("user_id")) {
                                int userId = jsonResponse.optInt("userId", jsonResponse.optInt("user_id", -1));

                                if (userId != -1) {
                                    showToast("Авторизация успешна!");

                                    SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putInt("user_id", userId);
                                    editor.apply();

                                    startActivity(new Intent(AuthorizationActivity.this, SearchActivity.class));
                                    finish();
                                } else {
                                    showToast("Ошибка: ID пользователя не получен");
                                }
                            } else {
                                showToast("Ошибка: Неверный формат ответа сервера");
                            }
                        } else {
                            String error = jsonResponse.optString("error", "Неизвестная ошибка");
                            showToast(error);
                        }
                    } catch (Exception e) {
                        Log.e("AuthError", "Ошибка парсинга: " + e.getMessage());
                        showToast("Ошибка обработки ответа: " + e.getMessage());
                    }
                });
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}