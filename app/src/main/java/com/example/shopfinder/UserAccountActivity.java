package com.example.shopfinder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserAccountActivity extends AppCompatActivity{

    private Button goBackButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_account_page);


        goBackButton = findViewById(R.id.goBackButton);


        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(UserAccountActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

    }
}







