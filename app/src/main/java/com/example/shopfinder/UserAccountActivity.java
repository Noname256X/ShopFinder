package com.example.shopfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.button.MaterialButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;

public class UserAccountActivity extends AppCompatActivity{
    private Button goBackButton;
    private Button authSettingsButton;
    private Button accDelSettingsButton;
    private Button cancelDeleteButton;
    private ConstraintLayout deleteConfirmationBlock;
    private MaterialButton confirmDeleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account_page);

        goBackButton = findViewById(R.id.goBackButton);
        authSettingsButton = findViewById(R.id.authSettingsButton);
        accDelSettingsButton = findViewById(R.id.accDelSettingsButton);
        cancelDeleteButton = findViewById(R.id.cancelDeleteButton);
        deleteConfirmationBlock = findViewById(R.id.deleteAccountConfirmationBlock);
        confirmDeleteButton = findViewById(R.id.confirmDeleteButton);


        confirmDeleteButton.setOnClickListener(v -> deleteUserAccount());

        deleteConfirmationBlock.setVisibility(View.GONE);

        accDelSettingsButton.setOnClickListener(v ->
                deleteConfirmationBlock.setVisibility(View.VISIBLE));

        cancelDeleteButton.setOnClickListener(v ->
                deleteConfirmationBlock.setVisibility(View.GONE));

        authSettingsButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserAccountActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        });

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserAccountActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

    }
    private void deleteUserAccount() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("user_id", userId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                json.toString()
        );

        Request request = new Request.Builder()
                .url("http://192.168.1.4:3000/api/users/soft-delete")
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(UserAccountActivity.this,
                                "Ошибка удаления: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.clear().apply();

                    runOnUiThread(() -> {
                        Toast.makeText(UserAccountActivity.this,
                                "Аккаунт успешно удалён",
                                Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(UserAccountActivity.this, SearchActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    });
                } else {
                    runOnUiThread(() ->
                            Toast.makeText(UserAccountActivity.this,
                                    "Ошибка сервера: " + response.code(),
                                    Toast.LENGTH_SHORT).show()
                    );
                }
            }
        });
    }
}







