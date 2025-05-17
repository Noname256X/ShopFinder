package com.example.shopfinder;

import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class SettingsActivity extends  AppCompatActivity {

    private ConstraintLayout rootLayout;

    //navbar
    private RelativeLayout mainFullNavigationButton;
    private ImageButton mainNavigationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_page);

        rootLayout = findViewById(R.id.root_layout);

        //buttons
        RelativeLayout userButtonBlock = findViewById(R.id.user_button_block);
        RelativeLayout settingsButtonBlock = findViewById(R.id.settings_button_block);

        //navbar
        mainNavigationButton = findViewById(R.id.mainNavigationButton);
        mainFullNavigationButton = findViewById(R.id.mainFullNavigationButton);
        ImageView leftIcon = findViewById(R.id.left_icon);
        ImageView centerIcon = findViewById(R.id.center_icon);
        ImageView rightIcon = findViewById(R.id.right_icon);



        userButtonBlock.setOnClickListener(v -> {
            Intent userAccountIntent = new Intent(SettingsActivity.this, UserAccountActivity.class);
            startActivity(userAccountIntent);
        });

        Button accountButton = findViewById(R.id.accountButton);
        accountButton.setClickable(false);
        accountButton.setFocusable(false);

        settingsButtonBlock.setOnClickListener(v -> {
            Intent settingsAppIntent = new Intent(SettingsActivity.this, SettingsAppActivity.class);
            startActivity(settingsAppIntent);
        });

        Button appSettingsButton = findViewById(R.id.appSettingsButton);
        appSettingsButton.setClickable(false);
        appSettingsButton.setFocusable(false);

        mainFullNavigationButton.setVisibility(View.GONE);

        mainNavigationButton.setOnClickListener(v -> {
            mainNavigationButton.setVisibility(View.GONE);
            mainFullNavigationButton.setVisibility(View.VISIBLE);
        });

        leftIcon.setOnClickListener(v -> {
            Intent searchIntent = new Intent(SettingsActivity.this, SelectedProductsActivity.class);
            startActivity(searchIntent);
        });

        centerIcon.setOnClickListener(v -> {
            mainFullNavigationButton.setVisibility(View.GONE);
            mainNavigationButton.setVisibility(View.VISIBLE);
        });

        rightIcon.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(SettingsActivity.this, SearchActivity.class);
            startActivity(settingsIntent);
        });

        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Rect outRect = new Rect();
                    mainFullNavigationButton.getGlobalVisibleRect(outRect);

                    if (!outRect.contains((int) event.getRawX(), (int) event.getRawY())) {
                        if (mainFullNavigationButton.getVisibility() == View.VISIBLE) {
                            mainFullNavigationButton.setVisibility(View.GONE);
                            mainNavigationButton.setVisibility(View.VISIBLE);
                        }
                    }
                }
                return false;
            }
        });
    }

    private void hideFullNavigation() {
        if (mainFullNavigationButton.getVisibility() == View.VISIBLE) {
            mainFullNavigationButton.setVisibility(View.GONE);
            mainNavigationButton.setVisibility(View.VISIBLE);
        }
    }
}





