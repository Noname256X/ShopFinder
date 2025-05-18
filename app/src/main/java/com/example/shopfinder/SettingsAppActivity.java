package com.example.shopfinder;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.content.SharedPreferences;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.List;

public class SettingsAppActivity extends AppCompatActivity {

    //Switch
    private Switch ozonSwitch;
    private Switch wildberriesSwitch;
    private Switch yandexMarketSwitch;
    private Switch magnitMarketSwitch;
    private Switch dnsSwitch;
    private Switch citilinkSwitch;
    private Switch mVideoSwitch;
    private Switch aliexpressSwitch;
    private Switch joomSwitch;
    private Switch shopMtsSwitch;
    private Switch technoparkSwitch;
    private Switch lamodaSwitch;

    private static final String PREFS_NAME = "ShopFinderPrefs";
    private static final String PAGE_NUMBER_KEY = "pageNumber";

    private Button goBackButton;
    private FrameLayout leftArrowButton;
    private FrameLayout rightArrowButton;
    private EditText pageNumberInput;
    private ConstraintLayout quantityBlock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_app_page);


        goBackButton = findViewById(R.id.goBackButton);
        leftArrowButton = findViewById(R.id.leftArrowButton);
        rightArrowButton = findViewById(R.id.rightArrowButton);
        pageNumberInput = findViewById(R.id.pageNumberInput);
        quantityBlock = findViewById(R.id.quantityBlock);

        //Switch
        ozonSwitch = findViewById(R.id.ozonSwitch);
        wildberriesSwitch = findViewById(R.id.wildberriesSwitch);
        yandexMarketSwitch = findViewById(R.id.YandexMarketSwitch);
        magnitMarketSwitch = findViewById(R.id.MagnitMarketSwitch);
        dnsSwitch = findViewById(R.id.dnsSwitch);
        citilinkSwitch = findViewById(R.id.CitilinkSwitch);
        mVideoSwitch = findViewById(R.id.MVideoSwitch);
        aliexpressSwitch = findViewById(R.id.AliexpressSwitch);
        joomSwitch = findViewById(R.id.JoomSwitch);
        shopMtsSwitch = findViewById(R.id.Shop_mtsSwitch);
        technoparkSwitch = findViewById(R.id.TechnoparkSwitch);
        lamodaSwitch = findViewById(R.id.LamodaSwitch);


        //Switch
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        ozonSwitch.setChecked(prefs.getBoolean("ozonSwitch", true));
        wildberriesSwitch.setChecked(prefs.getBoolean("wildberriesSwitch", true));
        yandexMarketSwitch.setChecked(prefs.getBoolean("yandexMarketSwitch", true));
        magnitMarketSwitch.setChecked(prefs.getBoolean("magnitMarketSwitch", true));
        dnsSwitch.setChecked(prefs.getBoolean("dnsSwitch", true));
        citilinkSwitch.setChecked(prefs.getBoolean("citilinkSwitch", true));
        mVideoSwitch.setChecked(prefs.getBoolean("mVideoSwitch", true));
        aliexpressSwitch.setChecked(prefs.getBoolean("aliexpressSwitch", true));
        joomSwitch.setChecked(prefs.getBoolean("joomSwitch", true));
        shopMtsSwitch.setChecked(prefs.getBoolean("shopMtsSwitch", true));
        technoparkSwitch.setChecked(prefs.getBoolean("technoparkSwitch", true));
        lamodaSwitch.setChecked(prefs.getBoolean("lamodaSwitch", true));


//        int savedPageNumber = prefs.getInt(PAGE_NUMBER_KEY, 8);
//        pageNumberInput.setText(String.valueOf(savedPageNumber));
        quantityBlock.setVisibility(View.GONE);
        pageNumberInput.setText("2");

        goBackButton.setOnClickListener(v -> {
            Intent intent = new Intent(SettingsAppActivity.this, SettingsActivity.class);
            startActivity(intent);
            finish();
        });

//        leftArrowButton.setOnClickListener(v -> {
//            int currentValue = Integer.parseInt(pageNumberInput.getText().toString());
//            if (currentValue > 1) {
//                currentValue--;
//                pageNumberInput.setText(String.valueOf(currentValue));
//                savePageNumber();
//            }
//        });
//
//        rightArrowButton.setOnClickListener(v -> {
//            int currentValue = Integer.parseInt(pageNumberInput.getText().toString());
//            if (currentValue < 8) {
//                currentValue++;
//                pageNumberInput.setText(String.valueOf(currentValue));
//                savePageNumber();
//            }
//        });
    }

    public static boolean areAllSwitchesDisabled(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);

        return !prefs.getBoolean("ozonSwitch", true) &&
                !prefs.getBoolean("wildberriesSwitch", true) &&
                !prefs.getBoolean("yandexMarketSwitch", true) &&
                !prefs.getBoolean("magnitMarketSwitch", true) &&
                !prefs.getBoolean("dnsSwitch", true) &&
                !prefs.getBoolean("citilinkSwitch", true) &&
                !prefs.getBoolean("mVideoSwitch", true) &&
                !prefs.getBoolean("aliexpressSwitch", true) &&
                !prefs.getBoolean("joomSwitch", true) &&
                !prefs.getBoolean("shopMtsSwitch", true) &&
                !prefs.getBoolean("technoparkSwitch", true) &&
                !prefs.getBoolean("lamodaSwitch", true);
    }

    public static List<String> getActiveMarketplaces(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        List<String> activeMarketplaces = new ArrayList<>();

        if (prefs.getBoolean("ozonSwitch", true)) activeMarketplaces.add("Ozon");
        if (prefs.getBoolean("wildberriesSwitch", true)) activeMarketplaces.add("Wildberries");
        if (prefs.getBoolean("yandexMarketSwitch", true)) activeMarketplaces.add("YandexMarket");
        if (prefs.getBoolean("magnitMarketSwitch", true)) activeMarketplaces.add("MagnitMarket");
        if (prefs.getBoolean("dnsSwitch", true)) activeMarketplaces.add("DNS");
        if (prefs.getBoolean("citilinkSwitch", true)) activeMarketplaces.add("Citilink");
        if (prefs.getBoolean("mVideoSwitch", true)) activeMarketplaces.add("M_Video");
        if (prefs.getBoolean("aliexpressSwitch", true)) activeMarketplaces.add("Aliexpress");
        if (prefs.getBoolean("joomSwitch", true)) activeMarketplaces.add("Joom");
        if (prefs.getBoolean("shopMtsSwitch", true)) activeMarketplaces.add("Shop_mts");
        if (prefs.getBoolean("technoparkSwitch", true)) activeMarketplaces.add("Technopark");
        if (prefs.getBoolean("lamodaSwitch", true)) activeMarketplaces.add("Lamoda");

        return activeMarketplaces;
    }

    @Override
    protected void onPause() {
        super.onPause();
//        savePageNumber();
        saveSwitchStates();
    }

//    private int getCurrentPageNumber() {
//        try {
//            return Integer.parseInt(pageNumberInput.getText().toString());
//        } catch (NumberFormatException e) {
//            return 8;
//        }
//    }

    private void saveSwitchStates() {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();

        editor.putBoolean("ozonSwitch", ozonSwitch.isChecked());
        editor.putBoolean("wildberriesSwitch", wildberriesSwitch.isChecked());
        editor.putBoolean("yandexMarketSwitch", yandexMarketSwitch.isChecked());
        editor.putBoolean("magnitMarketSwitch", magnitMarketSwitch.isChecked());
        editor.putBoolean("dnsSwitch", dnsSwitch.isChecked());
        editor.putBoolean("citilinkSwitch", citilinkSwitch.isChecked());
        editor.putBoolean("mVideoSwitch", mVideoSwitch.isChecked());
        editor.putBoolean("aliexpressSwitch", aliexpressSwitch.isChecked());
        editor.putBoolean("joomSwitch", joomSwitch.isChecked());
        editor.putBoolean("shopMtsSwitch", shopMtsSwitch.isChecked());
        editor.putBoolean("technoparkSwitch", technoparkSwitch.isChecked());
        editor.putBoolean("lamodaSwitch", lamodaSwitch.isChecked());

        editor.apply();
    }

//    private void savePageNumber() {
//        int pageNumber = getCurrentPageNumber();
//        if (pageNumber < 1) pageNumber = 1;
//        if (pageNumber > 8) pageNumber = 8;
//
//        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
//        editor.putInt(PAGE_NUMBER_KEY, pageNumber);
//        editor.apply();
//    }

//    public static int getSavedPageNumber(android.content.Context context) {
//        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//        int pageNumber = prefs.getInt(PAGE_NUMBER_KEY, 8);
//        if (pageNumber < 1) return 1;
//        if (pageNumber > 8) return 8;
//        return pageNumber;
//    }

    public static int getSavedPageNumber(android.content.Context context) {
        return 2;
    }
}