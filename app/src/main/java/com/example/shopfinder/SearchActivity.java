package com.example.shopfinder;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.graphics.Rect;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;
import java.util.HashMap;
import java.util.Map;

import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.example.shopfinder.CustomWebSocketListener;
import com.example.shopfinder.ProductData;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;


public class SearchActivity extends AppCompatActivity {
    private EditText productEntryField;
    private ConstraintLayout rootLayout;
    private ConstraintLayout hintsContainer;
    private NestedScrollView productsContainer;
    private boolean isSearchMode = false;
    private boolean isCheckboxChecked = false;
    private boolean isFormatting = false;

    //LogoText
    private TextView ozon_logo_text;
    private TextView wb_logo_text;
    private TextView yandex_logo_text;
    private TextView magnit_logo_text;
    private TextView dns_logo_text;
    private TextView citilink_logo_text;
    private TextView mvideo_logo_text;
    private TextView aliexpress_logo_text;
    private TextView joom_logo_text;
    private TextView mts_logo_text;
    private TextView technopark_logo_text;
    private TextView lamoda_logo_text;

    // private_FeedbackText
    private TextView ozonFeedbackText;
    private TextView wbFeedbackText;
    private TextView yandex_marketFeedbackText;
    private TextView magnitFeedbackText;
    private TextView dnsFeedbackText;
    private TextView citilinkFeedbackText;
    private TextView mvideoFeedbackText;
    private TextView aliexpressFeedbackText;
    private TextView joomFeedbackText;
    private TextView mtsFeedbackText;
    private TextView technoparkFeedbackText;
    private TextView lamodaFeedbackText;

    // private Title, Price, Rating, Image
    private TextView ozonTitleText, wbTitleText, YandexMarketTitleText, MagnitMarketTitleText, dnsTitleText, CitilinkTitleText, MVideoTitleText, AliexpressTitleText, JoomTitleText, Mts_Shop_TitleText, TechnoparkTitleText, LamodaTitleText;
    private TextView ozonPriceText, wbPriceText, YandexMarketPriceText, MagnitMarketPriceText, dnsPriceText, CitilinkPriceText, MVideoPriceText, AliexpressPriceText, JoomPriceText, Mts_Shop_PriceText, TechnoparkPriceText, LamodaPriceText;
    private TextView ozonRatingText, wbRatingText, YandexMarketRatingText, MagnitMarketRatingText, dnsRatingText, CitilinkRatingText, MVideoRatingText, AliexpressRatingText, JoomRatingText, Mts_Shop_RatingText, TechnoparkRatingText, LamodaRatingText;
    private ImageView ozonImage, wbImage, YandexMarketImage, MagnitMarketImage, dnsImage, CitilinkImage, MVideoImage, AliexpressImage, JoomImage, Mts_ShopImage, TechnoparkImage, LamodaImage;

    //navbar
    private RelativeLayout mainFullNavigationButton;
    private ImageButton mainNavigationButton;
    //ProductBlock
    private int currentImageIndex = 0;
    private List<String> productImages = new ArrayList<>();
    private ImageView mainImage;
    private ImageView rightIconInBut;
    private ImageView leftIconInBut;
    private ConstraintLayout productBlock;

    private Map<String, ProductData> marketplaceProducts = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        productEntryField = findViewById(R.id.product_entry_field);
        rootLayout = findViewById(R.id.root_layout);
        hintsContainer = findViewById(R.id.hints_container);
        productsContainer = findViewById(R.id.products_container);
        View searchButton = findViewById(R.id.search_icon_green_but);
        ozon_logo_text = findViewById(R.id.ozon_logo_text);
        wb_logo_text = findViewById(R.id.wb_logo_text);
        yandex_logo_text = findViewById(R.id.yandex_logo_text);
        magnit_logo_text = findViewById(R.id.magnit_logo_text);
        dns_logo_text = findViewById(R.id.dns_logo_text);
        citilink_logo_text = findViewById(R.id.citilink_logo_text);
        mvideo_logo_text = findViewById(R.id.mvideo_logo_text);
        aliexpress_logo_text = findViewById(R.id.aliexpress_logo_text);
        joom_logo_text = findViewById(R.id.joom_logo_text);
        mts_logo_text = findViewById(R.id.mts_logo_text);
        technopark_logo_text = findViewById(R.id.technopark_logo_text);
        lamoda_logo_text = findViewById(R.id.lamoda_logo_text);

        mainNavigationButton = findViewById(R.id.mainNavigationButton);
        mainFullNavigationButton = findViewById(R.id.mainFullNavigationButton);
        ImageView leftIcon = findViewById(R.id.left_icon);
        ImageView centerIcon = findViewById(R.id.center_icon);
        ImageView rightIcon = findViewById(R.id.right_icon);

        ozonFeedbackText = findViewById(R.id.ozon_feedback_text);
        wbFeedbackText = findViewById(R.id.wb_feedback_text);
        yandex_marketFeedbackText = findViewById(R.id.yandex_market_feedback_text);
        magnitFeedbackText = findViewById(R.id.magnit_feedback_text);
        dnsFeedbackText = findViewById(R.id.dns_feedback_text);
        citilinkFeedbackText = findViewById(R.id.citilink_feedback_text);
        mvideoFeedbackText = findViewById(R.id.mvideo_feedback_text);
        aliexpressFeedbackText = findViewById(R.id.aliexpress_feedback_text);
        joomFeedbackText = findViewById(R.id.joom_feedback_text);
        mtsFeedbackText = findViewById(R.id.mts_feedback_text);
        technoparkFeedbackText= findViewById(R.id.technopark_feedback_text);
        lamodaFeedbackText = findViewById(R.id.lamoda_feedback_text);

        ozonTitleText = findViewById(R.id.ozon_title_text);
        wbTitleText = findViewById(R.id.wb_title_text);
        YandexMarketTitleText = findViewById(R.id.yandex_market_title_text);
        MagnitMarketTitleText = findViewById(R.id.magnit_title_text);
        dnsTitleText = findViewById(R.id.dns_title_text);
        CitilinkTitleText = findViewById(R.id.citilink_title_text);
        MVideoTitleText = findViewById(R.id.mvideo_title_text);
        AliexpressTitleText = findViewById(R.id.aliexpress_title_text);
        JoomTitleText = findViewById(R.id.joom_title_text);
        Mts_Shop_TitleText = findViewById(R.id.mts_title_text);
        TechnoparkTitleText = findViewById(R.id.technopark_title_text);
        LamodaTitleText = findViewById(R.id.lamoda_title_text);

        ozonPriceText = findViewById(R.id.ozon_price_text);
        wbPriceText = findViewById(R.id.wb_price_text);
        YandexMarketPriceText = findViewById(R.id.yandex_market_price_text);
        MagnitMarketPriceText = findViewById(R.id.magnit_price_text);
        dnsPriceText = findViewById(R.id.dns_price_text);
        CitilinkPriceText = findViewById(R.id.citilink_price_text);
        MVideoPriceText = findViewById(R.id.mvideo_price_text);
        AliexpressPriceText = findViewById(R.id.aliexpress_price_text);
        JoomPriceText = findViewById(R.id.joom_price_text);
        Mts_Shop_PriceText = findViewById(R.id.mts_price_text);
        TechnoparkPriceText = findViewById(R.id.technopark_price_text);
        LamodaPriceText = findViewById(R.id.lamoda_price_text);

        ozonRatingText = findViewById(R.id.ozon_rating_text);
        wbRatingText = findViewById(R.id.wb_rating_text);
        YandexMarketRatingText = findViewById(R.id.yandex_market_rating_text);
        MagnitMarketRatingText = findViewById(R.id.magnit_rating_text);
        dnsRatingText = findViewById(R.id.dns_rating_text);
        CitilinkRatingText = findViewById(R.id.citilink_rating_text);
        MVideoRatingText = findViewById(R.id.mvideo_rating_text);
        AliexpressRatingText = findViewById(R.id.aliexpress_rating_text);
        JoomRatingText = findViewById(R.id.joom_rating_text);
        Mts_Shop_RatingText = findViewById(R.id.shop_rating_text);
        TechnoparkRatingText = findViewById(R.id.technopark_rating_text);
        LamodaRatingText = findViewById(R.id.lamoda_rating_text);

        ozonImage = findViewById(R.id.ozon_image);
        wbImage = findViewById(R.id.wb_image);
        YandexMarketImage = findViewById(R.id.yandex_market_image);
        MagnitMarketImage = findViewById(R.id.magnit_image);
        dnsImage = findViewById(R.id.dns_image);
        CitilinkImage = findViewById(R.id.citilink_image);
        MVideoImage = findViewById(R.id.mvideo_image);
        AliexpressImage = findViewById(R.id.aliexpress_image);
        JoomImage = findViewById(R.id.joom_image);
        Mts_ShopImage = findViewById(R.id.mts_image);
        TechnoparkImage = findViewById(R.id.technopark_image);
        LamodaImage = findViewById(R.id.lamoda_image);

        mainImage = findViewById(R.id.mainImage);
        rightIconInBut = findViewById(R.id.right_icon_in_but);
        leftIconInBut = findViewById(R.id.left_icon_in_but);
        productBlock = findViewById(R.id.product_block);
        View closeButton2 = findViewById(R.id.close_button2);
        ImageView shareIcon = findViewById(R.id.share_icon);
        shareIcon.setOnClickListener(v -> shareProductLink());
        ImageView openLinkIcon = findViewById(R.id.open_link_icon);

        ConstraintLayout ozonBlock = findViewById(R.id.ozon_block);


        rightIconInBut.setOnClickListener(v -> showNextImage());
        leftIconInBut.setOnClickListener(v -> showPreviousImage());

        productBlock.setVisibility(View.GONE);

        setupMarketplaceClickListeners();

        closeButton2.setOnClickListener(v -> {
            productBlock.setVisibility(View.GONE);
            productBlock.setClickable(false);
        });

        productsContainer.setVisibility(View.GONE);
        hintsContainer.setVisibility(View.VISIBLE);


        mainFullNavigationButton.setVisibility(View.GONE);

        mainNavigationButton.setOnClickListener(v -> {
            mainNavigationButton.setVisibility(View.GONE);
            mainFullNavigationButton.setVisibility(View.VISIBLE);
        });


        leftIcon.setOnClickListener(v -> {
            Intent selectedProductsIntent = new Intent(SearchActivity.this, SelectedProductsActivity.class);
            startActivity(selectedProductsIntent);
        });

        centerIcon.setOnClickListener(v -> {
            mainFullNavigationButton.setVisibility(View.GONE);
            mainNavigationButton.setVisibility(View.VISIBLE);
        });

        rightIcon.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(SearchActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
        });

        productEntryField.setOnClickListener(v -> {
            activateSearchMode();
            hideFullNavigation();
        });

        searchButton.setOnClickListener(v -> {
            String searchText = productEntryField.getText().toString().trim();
            if (!searchText.isEmpty()) {
                if (SettingsAppActivity.areAllSwitchesDisabled(SearchActivity.this)) {
                    Intent intent = new Intent(SearchActivity.this, SettingsAppActivity.class);
                    startActivity(intent);
                    Toast.makeText(SearchActivity.this, "Включите хотя бы один маркетплейс для поиска", Toast.LENGTH_SHORT).show();
                    return;
                }

                setTextViewsColorGray();

                hintsContainer.setVisibility(View.GONE);
                productsContainer.setVisibility(View.VISIBLE);

                setCardStylesInactive();
                performSearch(searchText);
            }
            hideFullNavigation();
        });

        productBlock.setOnClickListener(v -> {
        });

        productBlock.setClickable(false);
        productBlock.setVisibility(View.GONE);

        openLinkIcon.setOnClickListener(v -> openProductLinkInBrowser());

        productEntryField.setFocusable(false);
        productEntryField.setFocusableInTouchMode(false);
        productEntryField.setOnClickListener(v -> activateSearchMode());

        rootLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    Rect editTextRect = new Rect();
                    productEntryField.getGlobalVisibleRect(editTextRect);

                    Rect navRect = new Rect();
                    mainFullNavigationButton.getGlobalVisibleRect(navRect);

                    if (!editTextRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                        deactivateSearchMode();
                    }

                    if (!navRect.contains((int)event.getRawX(), (int)event.getRawY())) {
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

    private void sendProductToApi(String marketplace) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductData productData = marketplaceProducts.get(marketplace);
        if (productData == null) {
            Toast.makeText(this, "Данные товара не найдены", Toast.LENGTH_SHORT).show();
            return;
        }

        String cleanPrice = productData.price.replaceAll("[^\\d]", "");

        try {
            JSONObject requestData = new JSONObject();
            requestData.put("user_id", userId);

            JSONObject productJson = new JSONObject();
            productJson.put("marketplace", marketplace);
            productJson.put("title", productData.title);
            productJson.put("price", cleanPrice);
            productJson.put("rating", productData.rating);
            productJson.put("reviews", productData.reviews);
            productJson.put("link", productData.link);
            productJson.put("article", productData.article);

            if (productData.images != null && !productData.images.isEmpty()) {
                JSONArray imagesArray = new JSONArray();
                for (String image : productData.images) {
                    imagesArray.put(image);
                }
                productJson.put("images", imagesArray);
            }

            requestData.put("product", productJson);

            RequestBody body = RequestBody.create(
                    requestData.toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url("http://192.168.1.4:3000/api/favorites")
                    .post(body)
                    .build();

            OkHttpClient client = new OkHttpClient();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    runOnUiThread(() ->
                            Toast.makeText(SearchActivity.this,
                                    "Ошибка отправки данных: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show()
                    );
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        String responseBody = response.body().string();
                        JSONObject jsonResponse = new JSONObject(responseBody);

                        if (response.isSuccessful()) {
                            String status = jsonResponse.getString("status");
                            String message = jsonResponse.getString("message");

                            runOnUiThread(() ->
                                    Toast.makeText(SearchActivity.this,
                                            message,
                                            Toast.LENGTH_SHORT).show()
                            );
                        } else {
                            String error = jsonResponse.optString("error", "Неизвестная ошибка");
                            runOnUiThread(() ->
                                    Toast.makeText(SearchActivity.this,
                                            "Ошибка: " + error,
                                            Toast.LENGTH_SHORT).show()
                            );
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() ->
                                Toast.makeText(SearchActivity.this,
                                        "Ошибка обработки ответа",
                                        Toast.LENGTH_SHORT).show()
                        );
                    } finally {
                        response.close();
                    }
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Ошибка формирования данных", Toast.LENGTH_SHORT).show();
        }
    }

    private void openProductLinkInBrowser() {
        if (productBlock.getVisibility() != View.VISIBLE) {
            Toast.makeText(this, "Нет товара для открытия", Toast.LENGTH_SHORT).show();
            return;
        }

        String marketplace = ((MaterialButton)findViewById(R.id.ozon_button)).getText().toString();
        ProductData productData = marketplaceProducts.get(marketplace);

        if (productData == null || productData.link == null || productData.link.isEmpty()) {
            Toast.makeText(this, "Ссылка на товар недоступна", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(productData.link));

        if (browserIntent.resolveActivity(getPackageManager()) != null) {

            Intent chooserIntent = Intent.createChooser(browserIntent, "Выберите браузер");
            startActivity(chooserIntent);
        } else {
            Toast.makeText(this, "Не найдено приложений для открытия ссылки", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareProductLink() {
        if (productBlock.getVisibility() != View.VISIBLE) {
            Toast.makeText(this, "Нет товара для отправки", Toast.LENGTH_SHORT).show();
            return;
        }

        String marketplace = ((MaterialButton)findViewById(R.id.ozon_button)).getText().toString();
        ProductData productData = marketplaceProducts.get(marketplace);

        if (productData == null || productData.link == null || productData.link.isEmpty()) {
            Toast.makeText(this, "Ссылка на товар недоступна", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Посмотрите этот товар: " + productData.title);
        shareIntent.putExtra(Intent.EXTRA_TEXT, productData.link);

        startActivity(Intent.createChooser(shareIntent, "Поделиться ссылкой"));
    }

    private void setCardStylesInactive() {
        ConstraintLayout ozonBlock = findViewById(R.id.ozon_block);
        ozonBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout ozonHeader = findViewById(R.id.ozon_header);
        ozonHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView ozonImage = findViewById(R.id.ozon_image);
        ozonImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout ozonBottom = findViewById(R.id.ozon_bottom);
        ozonBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout wbBlock = findViewById(R.id.wb_block);
        wbBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout wbHeader = findViewById(R.id.wb_header);
        wbHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView wbImage = findViewById(R.id.wb_image);
        wbImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout wbBottom = findViewById(R.id.wb_bottom);
        wbBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout yandexBlock = findViewById(R.id.yandex_block);
        yandexBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout yandexHeader = findViewById(R.id.yandex_header);
        yandexHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView yandexImage = findViewById(R.id.yandex_market_image);
        yandexImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout yandexBottom = findViewById(R.id.yandex_bottom);
        yandexBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout magnitBlock = findViewById(R.id.magnit_block);
        magnitBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout magnitHeader = findViewById(R.id.magnit_header);
        magnitHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView magnitImage = findViewById(R.id.magnit_image);
        magnitImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout magnitBottom = findViewById(R.id.magnit_bottom);
        magnitBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout dnsBlock = findViewById(R.id.dns_block);
        dnsBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout dnsHeader = findViewById(R.id.dns_header);
        dnsHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView dnsImage = findViewById(R.id.dns_image);
        dnsImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout dnsBottom = findViewById(R.id.dns_bottom);
        dnsBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout citilinkBlock = findViewById(R.id.citilink_block);
        citilinkBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout citilinkHeader = findViewById(R.id.citilink_header);
        citilinkHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView citilinkImage = findViewById(R.id.citilink_image);
        citilinkImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout citilinkBottom = findViewById(R.id.citilink_bottom);
        citilinkBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout mvideoBlock = findViewById(R.id.mvideo_block);
        mvideoBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout mvideoHeader = findViewById(R.id.mvideo_header);
        mvideoHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView mvideoImage = findViewById(R.id.mvideo_image);
        mvideoImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout mvideoBottom = findViewById(R.id.mvideo_bottom);
        mvideoBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout aliexpressBlock = findViewById(R.id.aliexpress_block);
        aliexpressBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout aliexpressHeader = findViewById(R.id.aliexpress_header);
        aliexpressHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView aliexpressImage = findViewById(R.id.aliexpress_image);
        aliexpressImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout aliexpressBottom = findViewById(R.id.aliexpress_bottom);
        aliexpressBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout joomBlock = findViewById(R.id.joom_block);
        joomBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout joomHeader = findViewById(R.id.joom_header);
        joomHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView joomImage = findViewById(R.id.joom_image);
        joomImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout joomBottom = findViewById(R.id.joom_bottom);
        joomBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout mtsBlock = findViewById(R.id.mts_block);
        mtsBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout mtsHeader = findViewById(R.id.mts_header);
        mtsHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView mtsImage = findViewById(R.id.mts_image);
        mtsImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout mtsBottom = findViewById(R.id.mts_bottom);
        mtsBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout technoparkBlock = findViewById(R.id.technopark_block);
        technoparkBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout technoparkHeader = findViewById(R.id.technopark_header);
        technoparkHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView technoparkImage = findViewById(R.id.technopark_image);
        technoparkImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout technoparkBottom = findViewById(R.id.technopark_bottom);
        technoparkBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);

        ConstraintLayout lamodaBlock = findViewById(R.id.lamoda_block);
        lamodaBlock.setBackgroundResource(R.drawable.search_page_inactive_block_background);

        RelativeLayout lamodaHeader = findViewById(R.id.lamoda_header);
        lamodaHeader.setBackgroundResource(R.drawable.search_page_inactive_header_block);

        ImageView lamodaImage = findViewById(R.id.lamoda_image);
        lamodaImage.setImageResource(R.drawable.search_page_no_pictures_gray);

        RelativeLayout lamodaBottom = findViewById(R.id.lamoda_bottom);
        lamodaBottom.setBackgroundResource(R.drawable.search_page_inactive_bottom_block);
    }

    private void hideProductElements() {
        findViewById(R.id.ozon_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.ozon_rating_block).setVisibility(View.GONE);
        findViewById(R.id.ozon_price_text).setVisibility(View.GONE);
        findViewById(R.id.ozon_title_text).setVisibility(View.GONE);

        findViewById(R.id.wb_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.wb_rating_block).setVisibility(View.GONE);
        findViewById(R.id.wb_price_text).setVisibility(View.GONE);
        findViewById(R.id.wb_title_text).setVisibility(View.GONE);

        findViewById(R.id.yandex_market_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.yandex_market_rating_block).setVisibility(View.GONE);
        findViewById(R.id.yandex_market_price_text).setVisibility(View.GONE);
        findViewById(R.id.yandex_market_title_text).setVisibility(View.GONE);

        findViewById(R.id.magnit_market_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.magnit_rating_block).setVisibility(View.GONE);
        findViewById(R.id.magnit_price_text).setVisibility(View.GONE);
        findViewById(R.id.magnit_title_text).setVisibility(View.GONE);

        findViewById(R.id.dns_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.dns_rating_block).setVisibility(View.GONE);
        findViewById(R.id.dns_price_text).setVisibility(View.GONE);
        findViewById(R.id.dns_title_text).setVisibility(View.GONE);

        findViewById(R.id.citilink_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.citilink_rating_block).setVisibility(View.GONE);
        findViewById(R.id.citilink_price_text).setVisibility(View.GONE);
        findViewById(R.id.citilink_title_text).setVisibility(View.GONE);

        findViewById(R.id.mvideo_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.mvideo_rating_block).setVisibility(View.GONE);
        findViewById(R.id.mvideo_price_text).setVisibility(View.GONE);
        findViewById(R.id.mvideo_title_text).setVisibility(View.GONE);

        findViewById(R.id.aliexpress_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.aliexpress_rating_block).setVisibility(View.GONE);
        findViewById(R.id.aliexpress_price_text).setVisibility(View.GONE);
        findViewById(R.id.aliexpress_title_text).setVisibility(View.GONE);

        findViewById(R.id.joom_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.joom_rating_block).setVisibility(View.GONE);
        findViewById(R.id.joom_price_text).setVisibility(View.GONE);
        findViewById(R.id.joom_title_text).setVisibility(View.GONE);

        findViewById(R.id.mts_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.mts_rating_block).setVisibility(View.GONE);
        findViewById(R.id.mts_price_text).setVisibility(View.GONE);
        findViewById(R.id.mts_title_text).setVisibility(View.GONE);

        findViewById(R.id.technopark_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.technopark_rating_block).setVisibility(View.GONE);
        findViewById(R.id.technopark_price_text).setVisibility(View.GONE);
        findViewById(R.id.technopark_title_text).setVisibility(View.GONE);

        findViewById(R.id.lamoda_rating_icon).setVisibility(View.GONE);
        findViewById(R.id.lamoda_rating_block).setVisibility(View.GONE);
        findViewById(R.id.lamoda_price_text).setVisibility(View.GONE);
        findViewById(R.id.lamoda_title_text).setVisibility(View.GONE);
    }

    private void showNextImage() {
        if (productImages.isEmpty()) return;

        currentImageIndex++;
        if (currentImageIndex >= productImages.size()) {
            currentImageIndex = 0;
        }
        loadImage(productImages.get(currentImageIndex));
    }

    private void showPreviousImage() {
        if (productImages.isEmpty()) return;

        currentImageIndex--;
        if (currentImageIndex < 0) {
            currentImageIndex = productImages.size() - 1;
        }
        loadImage(productImages.get(currentImageIndex));
    }

    private void loadImage(String imageUrl) {
        Glide.with(SearchActivity.this)
                .load(imageUrl)
                .override(convertDpToPx(195.05f), convertDpToPx(173.33f))
                .centerCrop()
                .placeholder(R.drawable.no_photo_pictures)
                .error(R.drawable.no_photo_pictures)
                .into(mainImage);
    }

    private void hideFullNavigation() {
        if (mainFullNavigationButton.getVisibility() == View.VISIBLE) {
            mainFullNavigationButton.setVisibility(View.GONE);
            mainNavigationButton.setVisibility(View.VISIBLE);
        }
    }

    private void setTextViewsColorGray() {
        int grayColor = getResources().getColor(R.color.text_field_gray);

        ozon_logo_text.setTextColor(grayColor);
        wb_logo_text.setTextColor(grayColor);
        yandex_logo_text.setTextColor(grayColor);
        magnit_logo_text.setTextColor(grayColor);
        dns_logo_text.setTextColor(grayColor);
        citilink_logo_text.setTextColor(grayColor);
        mvideo_logo_text.setTextColor(grayColor);
        aliexpress_logo_text.setTextColor(grayColor);
        joom_logo_text.setTextColor(grayColor);
        mts_logo_text.setTextColor(grayColor);
        technopark_logo_text.setTextColor(grayColor);
        lamoda_logo_text.setTextColor(grayColor);
    }

    private String formatNumber(long number) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        formatter.setGroupingUsed(true);
        return formatter.format(number);
    }

    private int convertDpToPx(float dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }

    private void activateSearchMode() {
        if (isSearchMode) return;

        isSearchMode = true;
        productEntryField.setFocusable(true);
        productEntryField.setFocusableInTouchMode(true);
        productEntryField.requestFocus();

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(productEntryField, InputMethodManager.SHOW_IMPLICIT);

        if (productEntryField.getText().toString().isEmpty()) {
            productEntryField.setHint("");
        }
    }

    private void deactivateSearchMode() {
        if (!isSearchMode) return;

        isSearchMode = false;
        productEntryField.setFocusable(false);
        productEntryField.setFocusableInTouchMode(false);

        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(productEntryField.getWindowToken(), 0);

        if (productEntryField.getText().toString().isEmpty()) {
            productEntryField.setHint("Поиск товара");
        }
    }

    private void showProductBlock(ProductData productData, String marketplace) {
        productBlock.setVisibility(View.VISIBLE);
        productBlock.setClickable(true);

        TextView productText = findViewById(R.id.product_text);
        TextView priceText = findViewById(R.id.priceText);
        TextView reviewsText = findViewById(R.id.reviewsText);
        TextView ratingText = findViewById(R.id.rating_text_view);

        productText.setText(productData.title);
        priceText.setText(productData.price);
        reviewsText.setText("отзывы: " + productData.reviews);
        ratingText.setText(productData.rating);

        MaterialButton marketplaceButton = findViewById(R.id.ozon_button);
        marketplaceButton.setText(marketplace);

        int colorResId = getMarketplaceColor(marketplace);
        marketplaceButton.setBackgroundTintList(ContextCompat.getColorStateList(this, colorResId));

        productImages.clear();
        if (productData.images != null && !productData.images.isEmpty()) {
            for (String imageName : productData.images) {
                String imageUrl = "http://192.168.1.4:3000/images/not%20in%20the%20database/" + imageName;
                productImages.add(imageUrl);
            }
            currentImageIndex = 0;
            loadImage(productImages.get(currentImageIndex));
        }
    }

    private int getMarketplaceColor(String marketplace) {
        switch (marketplace) {
            case "Ozon": return R.color.marketplace_ozon;
            case "Wildberries": return R.color.marketplace_wb;
            case "YandexMarket": return R.color.marketplace_yandex_market;
            case "MagnitMarket": return R.color.marketplace_magnit_market;
            case "DNS": return R.color.marketplace_dns;
            case "Citilink": return R.color.marketplace_citilink;
            case "M_Video": return R.color.marketplace_m_video;
            case "Aliexpress": return R.color.marketplace_aliexpress;
            case "Joom": return R.color.marketplace_joom;
            case "Shop_mts": return R.color.marketplace_mts_shop;
            case "Technopark": return R.color.marketplace_technopark;
            case "Lamoda": return R.color.white;
            default: return R.color.additional_color;
        }
    }

    private void setupMarketplaceClickListeners() {

        ImageView ozonRatingIcon = findViewById(R.id.ozon_rating_icon);
        ImageView wbRatingIcon = findViewById(R.id.wb_rating_icon);
        ImageView yandexRatingIcon = findViewById(R.id.yandex_market_rating_icon);
        ImageView magnitRatingIcon = findViewById(R.id.magnit_market_rating_icon);
        ImageView dnsRatingIcon = findViewById(R.id.dns_rating_icon);
        ImageView citilinkRatingIcon = findViewById(R.id.citilink_rating_icon);
        ImageView mvideoRatingIcon = findViewById(R.id.mvideo_rating_icon);
        ImageView aliexpressRatingIcon = findViewById(R.id.aliexpress_rating_icon);
        ImageView joomRatingIcon = findViewById(R.id.joom_rating_icon);
        ImageView mtsRatingIcon = findViewById(R.id.mts_rating_icon);
        ImageView technoparkRatingIcon = findViewById(R.id.technopark_rating_icon);
        ImageView lamodaRatingIcon = findViewById(R.id.lamoda_rating_icon);


        ozonRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Ozon")) {
                sendProductToApi("Ozon");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        wbRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Wildberries")) {
                sendProductToApi("Wildberries");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        yandexRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("YandexMarket")) {
                sendProductToApi("YandexMarket");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        magnitRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("MagnitMarket")) {
                sendProductToApi("MagnitMarket");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        dnsRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("DNS")) {
                sendProductToApi("DNS");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        citilinkRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Citilink")) {
                sendProductToApi("Citilink");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        mvideoRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("M_Video")) {
                sendProductToApi("M_Video");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        aliexpressRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Aliexpress")) {
                sendProductToApi("Aliexpress");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        joomRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Joom")) {
                sendProductToApi("Joom");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        mtsRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Shop_mts")) {
                sendProductToApi("Shop_mts");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        technoparkRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Technopark")) {
                sendProductToApi("Technopark");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });
        lamodaRatingIcon.setOnClickListener(v -> {
            if (marketplaceProducts.containsKey("Lamoda")) {
                sendProductToApi("Lamoda");
            } else {
                Toast.makeText(this, "Сначала найдите товар", Toast.LENGTH_SHORT).show();
            }
        });

        View.OnClickListener marketplaceClickListener = v -> {
            String marketplace = "";
            ProductData productData = null;

            int id = v.getId();

            if (id == R.id.ozon_block) {
                marketplace = "Ozon";
                productData = marketplaceProducts.get("Ozon");
            } else if (id == R.id.wb_block) {
                marketplace = "Wildberries";
                productData = marketplaceProducts.get("Wildberries");
            } else if (id == R.id.yandex_block) {
                marketplace = "YandexMarket";
                productData = marketplaceProducts.get("YandexMarket");
            } else if (id == R.id.magnit_block) {
                marketplace = "MagnitMarket";
                productData = marketplaceProducts.get("MagnitMarket");
            } else if (id == R.id.dns_block) {
                marketplace = "DNS";
                productData = marketplaceProducts.get("DNS");
            } else if (id == R.id.citilink_block) {
                marketplace = "Citilink";
                productData = marketplaceProducts.get("Citilink");
            } else if (id == R.id.mvideo_block) {
                marketplace = "M_Video";
                productData = marketplaceProducts.get("M_Video");
            } else if (id == R.id.aliexpress_block) {
                marketplace = "Aliexpress";
                productData = marketplaceProducts.get("Aliexpress");
            } else if (id == R.id.joom_block) {
                marketplace = "Joom";
                productData = marketplaceProducts.get("Joom");
            } else if (id == R.id.mts_block) {
                marketplace = "Shop_mts";
                productData = marketplaceProducts.get("Shop_mts");
            } else if (id == R.id.technopark_block) {
                marketplace = "Technopark";
                productData = marketplaceProducts.get("Technopark");
            } else if (id == R.id.lamoda_block) {
                marketplace = "Lamoda";
                productData = marketplaceProducts.get("Lamoda");
            }

            if (productData != null) {
                showProductBlock(productData, marketplace);
            }
        };

        findViewById(R.id.ozon_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.wb_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.yandex_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.magnit_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.dns_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.citilink_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.mvideo_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.aliexpress_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.joom_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.mts_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.technopark_block).setOnClickListener(marketplaceClickListener);
        findViewById(R.id.lamoda_block).setOnClickListener(marketplaceClickListener);
    }

    public class WebSocketClient {
        private OkHttpClient client;
        private WebSocket webSocket;
        private String ipAddress;
        private CustomWebSocketListener listener;

        public WebSocketClient(String ipAddress, CustomWebSocketListener listener) {
            this.ipAddress = ipAddress;
            this.listener = listener;
            this.client = new OkHttpClient();
        }

        public void connect() {
            Request request = new Request.Builder()
                    .url("ws://192.168.1.4:3000/ws")
                    .addHeader("X-Forwarded-For", ipAddress)
                    .build();

            webSocket = client.newWebSocket(request, new okhttp3.WebSocketListener() {
                @Override
                public void onOpen(okhttp3.WebSocket webSocket, Response response) {
                    super.onOpen(webSocket, response);
                }

                @Override
                public void onMessage(okhttp3.WebSocket webSocket, String text) {
                    try {
                        Log.d("WebSocket", "Received message: " + text);
                        JSONObject message = new JSONObject(text);
                        String type = message.getString("type");

                        if (type.equals("status")) {
                            String status = message.getString("message");
                            listener.onMessage(status);
                        } else if (type.equals("data")) {
                            JSONArray dataArray = message.getJSONArray("data");
                            String marketplace = message.getString("marketplace");

                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject data = dataArray.getJSONObject(i);
                                ProductData productData = parseProductData(data);
                                listener.onDataReceived(marketplace, productData);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(okhttp3.WebSocket webSocket, Throwable t, Response response) {
                    Log.e("WebSocket", "Connection failed: " + t.getMessage());
                    listener.onFailure(t.getMessage());
                }

                @Override
                public void onClosing(okhttp3.WebSocket webSocket, int code, String reason) {
                    Log.d("WebSocket", "Closing: " + code + " " + reason);
                    webSocket.close(1000, null);
                }
            });
        }

        private ProductData parseProductData(JSONObject data) throws JSONException {
            ProductData product = new ProductData();
            product.link = data.getString("link_products");
            product.article = data.optString("article", "");
            product.title = data.getString("title");
            product.price = data.getString("price");
            product.rating = data.optString("rating", "0");
            product.reviews = data.optString("reviews", "0");

            JSONArray images = data.optJSONArray("images");
            if (images != null && images.length() > 0) {
                product.images = new ArrayList<>();
                for (int i = 0; i < images.length(); i++) {
                    product.images.add(images.getString(i));
                }
            }

            return product;
        }

        public void disconnect() {
            if (webSocket != null) {
                webSocket.close(1000, "Closing");
            }
        }
    }

    public interface WebSocketListener {
        void onMessage(String message);
        void onDataReceived(String marketplace, ProductData data);
        void onFailure(String error);
    }

    private String truncateText(String text) {
        if (text.length() > 65) {
            return text.substring(0, 65) + "...";
        }
        return text;
    }

    public class InnerStrokeTransformation extends BitmapTransformation {
        private final int strokeWidth;
        private final int strokeColor;

        public InnerStrokeTransformation(int strokeWidth, int strokeColor) {
            this.strokeWidth = strokeWidth;
            this.strokeColor = strokeColor;
        }

        @Override
        protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
            Bitmap bitmap = Bitmap.createBitmap(outWidth, outHeight, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);
            RectF rectF = new RectF(0, 0, outWidth, outHeight);
            canvas.drawBitmap(toTransform, null, rectF, paint);

            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(strokeWidth);
            paint.setColor(strokeColor);
            rectF.inset(strokeWidth / 2f, strokeWidth / 2f);
            canvas.drawRoundRect(rectF, 0, 0, paint);

            return bitmap;
        }

        @Override
        public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
            messageDigest.update(("InnerStrokeTransformation" + strokeWidth + strokeColor).getBytes());
        }
    }

    private void setupWebSocketConnection(String ipAddress) {
        WebSocketClient webSocketClient = new WebSocketClient(ipAddress, new CustomWebSocketListener() {
            @Override
            public void onMessage(String message) {
                runOnUiThread(() -> {
                    Log.d("WebSocket", "Received message: " + message);
                    if (message.contains("Ozon")) {
                        ozonFeedbackText.setText(message);
                    } else if (message.contains("Wildberries")) {
                        wbFeedbackText.setText(message);
                    } else if (message.contains("YandexMarket")) {
                        yandex_marketFeedbackText.setText(message);
                    } else if (message.contains("MagnitMarket")) {
                        magnitFeedbackText.setText(message);
                    } else if (message.contains("DNS")) {
                        dnsFeedbackText.setText(message);
                    } else if (message.contains("Citilink")) {
                        citilinkFeedbackText.setText(message);
                    } else if (message.contains("M_Video")) {
                        mvideoFeedbackText.setText(message);
                    } else if (message.contains("Aliexpress")) {
                        aliexpressFeedbackText.setText(message);
                    } else if (message.contains("Joom")) {
                        joomFeedbackText.setText(message);
                    } else if (message.contains("Shop_mts")) {
                        mtsFeedbackText.setText(message);
                    } else if (message.contains("Technopark")) {
                        technoparkFeedbackText.setText(message);
                    } else if (message.contains("Lamoda")) {
                        lamodaFeedbackText.setText(message);
                    }
                });
            }

            private int convertDpToPx(float dp) {
                return (int) (dp * getResources().getDisplayMetrics().density);
            }

            @Override
            public void onDataReceived(String marketplace, ProductData data) {
                runOnUiThread(() -> {

                    String firstImageUrl = "";
                    if (data.images != null && !data.images.isEmpty()) {
                        firstImageUrl = "http://192.168.1.4:3000/images/not%20in%20the%20database/" + data.images.get(0);
                    }

                    marketplaceProducts.put(marketplace, data);

                    switch (marketplace) {
                        case "Ozon":
                            ConstraintLayout ozonBlock = findViewById(R.id.ozon_block);
                            ozonBlock.setBackgroundResource(R.drawable.search_page_ozon_block_background);

                            RelativeLayout ozonHeader = findViewById(R.id.ozon_header);
                            ozonHeader.setBackgroundResource(R.drawable.search_page_ozon_header_background);

                            ImageView ozonImage = findViewById(R.id.ozon_image);
                            ozonImage.setBackgroundResource(R.drawable.search_page_ozon_image_stroke);

                            RelativeLayout ozonBottom = findViewById(R.id.ozon_bottom);
                            ozonBottom.setBackgroundResource(R.drawable.search_page_ozon_bottom_background);

                            TextView ozonRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.ozon_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.ozon_rating_block).setVisibility(View.VISIBLE);

                            ozon_logo_text.setTextColor(getResources().getColor(R.color.white));


                            productImages.clear();
                            if (data.images != null && !data.images.isEmpty()) {
                                for (String imageName : data.images) {
                                    String imageUrl = "http://192.168.1.4:3000/images/not%20in%20the%20database/" + imageName;
                                    productImages.add(imageUrl);
                                }
                            }
                            ozonTitleText.setText(truncateText(data.title));
                            ozonPriceText.setText(data.price);
                            ozonRatingText.setText(data.rating);
                            ozonRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_ozon)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_ozon_image_stroke)
                                    .error(R.drawable.search_page_ozon_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(ozonImage);
                            ozonTitleText.setVisibility(View.VISIBLE);
                            ozonPriceText.setVisibility(View.VISIBLE);
                            ozonRatingText.setVisibility(View.VISIBLE);
                            ozonImage.setVisibility(View.VISIBLE);
                            ozonFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Wildberries":
                            ConstraintLayout wbBlock = findViewById(R.id.wb_block);
                            wbBlock.setBackgroundResource(R.drawable.search_page_wb_block_background);

                            RelativeLayout wbHeader = findViewById(R.id.wb_header);
                            wbHeader.setBackgroundResource(R.drawable.search_page_wb_header_background);

                            ImageView wbImage = findViewById(R.id.wb_image);
                            wbImage.setBackgroundResource(R.drawable.search_page_wb_image_stroke);

                            RelativeLayout wbBottom = findViewById(R.id.wb_bottom);
                            wbBottom.setBackgroundResource(R.drawable.search_page_wb_bottom_background);

                            TextView wbRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.wb_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.wb_rating_block).setVisibility(View.VISIBLE);

                            wb_logo_text.setTextColor(getResources().getColor(R.color.white));
                            wbTitleText.setText(truncateText(data.title));
                            wbPriceText.setText(data.price);
                            wbRatingText.setText(data.rating);
                            wbRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_wb)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_wb_image_stroke)
                                    .error(R.drawable.search_page_wb_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(wbImage);
                            wbTitleText.setVisibility(View.VISIBLE);
                            wbPriceText.setVisibility(View.VISIBLE);
                            wbRatingText.setVisibility(View.VISIBLE);
                            wbImage.setVisibility(View.VISIBLE);
                            wbFeedbackText.setVisibility(View.GONE);
                            break;
                        case "YandexMarket":
                            ConstraintLayout yandexBlock = findViewById(R.id.yandex_block);
                            yandexBlock.setBackgroundResource(R.drawable.search_page_yandex_market_background);

                            RelativeLayout yandexHeader = findViewById(R.id.yandex_header);
                            yandexHeader.setBackgroundResource(R.drawable.search_page_yandex_market_header_background);

                            ImageView yandexImage = findViewById(R.id.yandex_market_image);
                            yandexImage.setBackgroundResource(R.drawable.search_page_yandex_market_image_stroke);

                            RelativeLayout yandexBottom = findViewById(R.id.yandex_bottom);
                            yandexBottom.setBackgroundResource(R.drawable.search_page_yandex_market_bottom_background);

                            TextView yandexRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.yandex_market_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.yandex_market_rating_block).setVisibility(View.VISIBLE);

                            yandex_logo_text.setTextColor(getResources().getColor(R.color.black));

                            YandexMarketTitleText.setText(truncateText(data.title));
                            YandexMarketPriceText.setText(data.price);
                            YandexMarketRatingText.setText(data.rating);
                            yandexRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_yandex_market)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_yandex_market_image_stroke)
                                    .error(R.drawable.search_page_yandex_market_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(YandexMarketImage);
                            YandexMarketTitleText.setVisibility(View.VISIBLE);
                            YandexMarketPriceText.setVisibility(View.VISIBLE);
                            YandexMarketRatingText.setVisibility(View.VISIBLE);
                            YandexMarketImage.setVisibility(View.VISIBLE);
                            yandex_marketFeedbackText.setVisibility(View.GONE);
                            break;
                        case "MagnitMarket":
                            ConstraintLayout magnitBlock = findViewById(R.id.magnit_block);
                            magnitBlock.setBackgroundResource(R.drawable.search_page_magnit_market_block_background);

                            RelativeLayout magnitHeader = findViewById(R.id.magnit_header);
                            magnitHeader.setBackgroundResource(R.drawable.search_page_magnit_market_header_background);

                            ImageView magnitImage = findViewById(R.id.magnit_image);
                            magnitImage.setBackgroundResource(R.drawable.search_page_magnit_market_image_stroke);

                            RelativeLayout magnitBottom = findViewById(R.id.magnit_bottom);
                            magnitBottom.setBackgroundResource(R.drawable.search_page_magnit_market_bottom_background);

                            TextView magnitRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.magnit_market_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.magnit_rating_block).setVisibility(View.VISIBLE);

                            magnit_logo_text.setTextColor(getResources().getColor(R.color.marketplace_magnit_market));

                            MagnitMarketTitleText.setText(truncateText(data.title));
                            MagnitMarketPriceText.setText(data.price);
                            MagnitMarketRatingText.setText(data.rating);
                            magnitRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_magnit_market)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_magnit_market_image_stroke)
                                    .error(R.drawable.search_page_magnit_market_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(MagnitMarketImage);
                            MagnitMarketTitleText.setVisibility(View.VISIBLE);
                            MagnitMarketPriceText.setVisibility(View.VISIBLE);
                            MagnitMarketRatingText.setVisibility(View.VISIBLE);
                            MagnitMarketImage.setVisibility(View.VISIBLE);
                            magnitFeedbackText.setVisibility(View.GONE);
                            break;
                        case "DNS":
                            ConstraintLayout dnsBlock = findViewById(R.id.dns_block);
                            dnsBlock.setBackgroundResource(R.drawable.search_page_dns_block_background);

                            RelativeLayout dnsHeader = findViewById(R.id.dns_header);
                            dnsHeader.setBackgroundResource(R.drawable.search_page_dns_header_background);

                            ImageView dnsImage = findViewById(R.id.dns_image);
                            dnsImage.setBackgroundResource(R.drawable.search_page_dns_image_stroke);

                            RelativeLayout dnsBottom = findViewById(R.id.dns_bottom);
                            dnsBottom.setBackgroundResource(R.drawable.search_page_dns_bottom_background);

                            TextView dnsRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.dns_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.dns_rating_block).setVisibility(View.VISIBLE);

                            dns_logo_text.setTextColor(getResources().getColor(R.color.white));

                            dnsTitleText.setText(truncateText(data.title));
                            dnsPriceText.setText(data.price);
                            dnsRatingText.setText(data.rating);
                            dnsRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_dns)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_dns_image_stroke)
                                    .error(R.drawable.search_page_dns_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(dnsImage);
                            dnsTitleText.setVisibility(View.VISIBLE);
                            dnsPriceText.setVisibility(View.VISIBLE);
                            dnsRatingText.setVisibility(View.VISIBLE);
                            dnsImage.setVisibility(View.VISIBLE);
                            dnsFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Citilink":
                            ConstraintLayout citilinkBlock = findViewById(R.id.citilink_block);
                            citilinkBlock.setBackgroundResource(R.drawable.search_page_citilink_block_background);

                            RelativeLayout citilinkHeader = findViewById(R.id.citilink_header);
                            citilinkHeader.setBackgroundResource(R.drawable.search_page_citilink_header_background);

                            ImageView citilinkImage = findViewById(R.id.citilink_image);
                            citilinkImage.setBackgroundResource(R.drawable.search_page_citilink_image_stroke);

                            RelativeLayout citilinkBottom = findViewById(R.id.citilink_bottom);
                            citilinkBottom.setBackgroundResource(R.drawable.search_page_citilink_bottom_background);

                            TextView citilinkRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.citilink_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.citilink_rating_block).setVisibility(View.VISIBLE);

                            citilink_logo_text.setTextColor(getResources().getColor(R.color.white));

                            CitilinkTitleText.setText(truncateText(data.title));
                            CitilinkPriceText.setText(data.price);
                            CitilinkRatingText.setText(data.rating);
                            citilinkRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_citilink)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_citilink_image_stroke)
                                    .error(R.drawable.search_page_citilink_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(CitilinkImage);
                            CitilinkTitleText.setVisibility(View.VISIBLE);
                            CitilinkPriceText.setVisibility(View.VISIBLE);
                            CitilinkRatingText.setVisibility(View.VISIBLE);
                            CitilinkImage.setVisibility(View.VISIBLE);
                            citilinkFeedbackText.setVisibility(View.GONE);
                            break;
                        case "M_Video":
                            ConstraintLayout mvideoBlock = findViewById(R.id.mvideo_block);
                            mvideoBlock.setBackgroundResource(R.drawable.search_page_m_video_block_background);

                            RelativeLayout mvideoHeader = findViewById(R.id.mvideo_header);
                            mvideoHeader.setBackgroundResource(R.drawable.search_page_m_video_header_background);

                            ImageView mvideoImage = findViewById(R.id.mvideo_image);
                            mvideoImage.setBackgroundResource(R.drawable.search_page_m_video_image_stroke);

                            RelativeLayout mvideoBottom = findViewById(R.id.mvideo_bottom);
                            mvideoBottom.setBackgroundResource(R.drawable.search_page_m_video_bottom_background);

                            TextView mvideoRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.mvideo_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.mvideo_rating_block).setVisibility(View.VISIBLE);

                            mvideo_logo_text.setTextColor(getResources().getColor(R.color.white));

                            MVideoTitleText.setText(truncateText(data.title));
                            MVideoPriceText.setText(data.price);
                            MVideoRatingText.setText(data.rating);
                            mvideoRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(150f), convertDpToPx(140f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_m_video)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_m_video_image_stroke)
                                    .error(R.drawable.search_page_m_video_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(MVideoImage);
                            MVideoTitleText.setVisibility(View.VISIBLE);
                            MVideoPriceText.setVisibility(View.VISIBLE);
                            MVideoPriceText.setVisibility(View.VISIBLE);
                            MVideoImage.setVisibility(View.VISIBLE);
                            mvideoFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Aliexpress":
                            ConstraintLayout aliexpressBlock = findViewById(R.id.aliexpress_block);
                            aliexpressBlock.setBackgroundResource(R.drawable.search_page_aliexpress_block_background);

                            RelativeLayout aliexpressHeader = findViewById(R.id.aliexpress_header);
                            aliexpressHeader.setBackgroundResource(R.drawable.search_page_aliexpress_header_background);

                            ImageView aliexpressImage = findViewById(R.id.aliexpress_image);
                            aliexpressImage.setBackgroundResource(R.drawable.search_page_aliexpress_image_stroke);

                            RelativeLayout aliexpressBottom = findViewById(R.id.aliexpress_bottom);
                            aliexpressBottom.setBackgroundResource(R.drawable.search_page_aliexpress_bottom_background);

                            TextView aliexpressRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.aliexpress_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.aliexpress_rating_block).setVisibility(View.VISIBLE);

                            aliexpress_logo_text.setTextColor(getResources().getColor(R.color.white));

                            AliexpressTitleText.setText(truncateText(data.title));
                            AliexpressPriceText.setText(data.price);
                            AliexpressRatingText.setText(data.rating);
                            aliexpressRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_aliexpress)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_aliexpress_image_stroke)
                                    .error(R.drawable.search_page_aliexpress_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(AliexpressImage);
                            AliexpressTitleText.setVisibility(View.VISIBLE);
                            AliexpressPriceText.setVisibility(View.VISIBLE);
                            AliexpressRatingText.setVisibility(View.VISIBLE);
                            AliexpressImage.setVisibility(View.VISIBLE);
                            aliexpressFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Joom":
                            ConstraintLayout joomBlock = findViewById(R.id.joom_block);
                            joomBlock.setBackgroundResource(R.drawable.search_page_joom_block_background);

                            RelativeLayout joomHeader = findViewById(R.id.joom_header);
                            joomHeader.setBackgroundResource(R.drawable.search_page_joom_header_background);

                            ImageView joomImage = findViewById(R.id.joom_image);
                            joomImage.setBackgroundResource(R.drawable.search_page_joom_image_stroke);

                            RelativeLayout joomBottom = findViewById(R.id.joom_bottom);
                            joomBottom.setBackgroundResource(R.drawable.search_page_joom_bottom_background);

                            TextView joomRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.joom_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.joom_rating_block).setVisibility(View.VISIBLE);

                            joom_logo_text.setTextColor(getResources().getColor(R.color.white));

                            JoomTitleText.setText(truncateText(data.title));
                            JoomPriceText.setText(data.price);
                            JoomRatingText.setText(data.rating);
                            joomRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_joom)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_joom_image_stroke)
                                    .error(R.drawable.search_page_joom_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(JoomImage);
                            JoomTitleText.setVisibility(View.VISIBLE);
                            JoomPriceText.setVisibility(View.VISIBLE);
                            JoomRatingText.setVisibility(View.VISIBLE);
                            JoomImage.setVisibility(View.VISIBLE);
                            joomFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Shop_mts":
                            ConstraintLayout mtsBlock = findViewById(R.id.mts_block);
                            mtsBlock.setBackgroundResource(R.drawable.search_page_mts_shop_block_background);

                            RelativeLayout mtsHeader = findViewById(R.id.mts_header);
                            mtsHeader.setBackgroundResource(R.drawable.search_page_mts_shop_header_background);

                            ImageView mtsImage = findViewById(R.id.mts_image);
                            mtsImage.setBackgroundResource(R.drawable.search_page_mts_shop_image_stroke);

                            RelativeLayout mtsBottom = findViewById(R.id.mts_bottom);
                            mtsBottom.setBackgroundResource(R.drawable.search_page_mts_shop_bottom_background);

                            TextView mtsRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.mts_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.mts_rating_block).setVisibility(View.VISIBLE);

                            mts_logo_text.setTextColor(getResources().getColor(R.color.white));

                            Mts_Shop_TitleText.setText(truncateText(data.title));
                            Mts_Shop_PriceText.setText(data.price);
                            Mts_Shop_RatingText.setText(data.rating);
                            mtsRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_mts_shop)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_mts_shop_image_stroke)
                                    .error(R.drawable.search_page_mts_shop_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(Mts_ShopImage);
                            Mts_Shop_TitleText.setVisibility(View.VISIBLE);
                            Mts_Shop_PriceText.setVisibility(View.VISIBLE);
                            Mts_Shop_RatingText.setVisibility(View.VISIBLE);
                            Mts_ShopImage.setVisibility(View.VISIBLE);
                            mtsFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Technopark":
                            ConstraintLayout technoparkBlock = findViewById(R.id.technopark_block);
                            technoparkBlock.setBackgroundResource(R.drawable.search_page_technopark_block_background);

                            RelativeLayout technoparkHeader = findViewById(R.id.technopark_header);
                            technoparkHeader.setBackgroundResource(R.drawable.search_page_technopark_header_background);

                            ImageView technoparkImage = findViewById(R.id.technopark_image);
                            technoparkImage.setBackgroundResource(R.drawable.search_page_technopark_image_stroke);

                            RelativeLayout technoparkBottom = findViewById(R.id.technopark_bottom);
                            technoparkBottom.setBackgroundResource(R.drawable.search_page_technopark_bottom_background);

                            TextView technoparkRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.technopark_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.technopark_rating_block).setVisibility(View.VISIBLE);

                            technopark_logo_text.setTextColor(getResources().getColor(R.color.white));

                            TechnoparkTitleText.setText(truncateText(data.title));
                            TechnoparkPriceText.setText(data.price);
                            TechnoparkRatingText.setText(data.rating);
                            technoparkRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_technopark)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_technopark_image_stroke)
                                    .error(R.drawable.search_page_technopark_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(TechnoparkImage);
                            TechnoparkTitleText.setVisibility(View.VISIBLE);
                            TechnoparkPriceText.setVisibility(View.VISIBLE);
                            TechnoparkRatingText.setVisibility(View.VISIBLE);
                            TechnoparkImage.setVisibility(View.VISIBLE);
                            technoparkFeedbackText.setVisibility(View.GONE);
                            break;
                        case "Lamoda":
                            ConstraintLayout lamodaBlock = findViewById(R.id.lamoda_block);
                            lamodaBlock.setBackgroundResource(R.drawable.search_page_lamoda_block_background);

                            RelativeLayout lamodaHeader = findViewById(R.id.lamoda_header);
                            lamodaHeader.setBackgroundResource(R.drawable.search_page_lamoda_header_background);

                            ImageView lamodaImage = findViewById(R.id.lamoda_image);
                            lamodaImage.setBackgroundResource(R.drawable.search_page_lamoda_image_stroke);

                            RelativeLayout lamodaBottom = findViewById(R.id.lamoda_bottom);
                            lamodaBottom.setBackgroundResource(R.drawable.search_page_lamoda_bottom_background);

                            TextView lamodaRatingTextBlock = findViewById(R.id.reviewsText);

                            findViewById(R.id.lamoda_rating_icon).setVisibility(View.VISIBLE);
                            findViewById(R.id.lamoda_rating_block).setVisibility(View.VISIBLE);

                            lamoda_logo_text.setTextColor(getResources().getColor(R.color.black));

                            LamodaTitleText.setText(truncateText(data.title));
                            LamodaPriceText.setText(data.price);
                            LamodaRatingText.setText(data.rating);
                            lamodaRatingTextBlock.setText("отзывы:" + data.reviews);
                            Log.d("ImageLoad", "Loading image from URL: " + data.images.get(0));
                            Glide.with(SearchActivity.this)
                                    .load(firstImageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .centerCrop()
                                    .transform(new MultiTransformation<>(
                                            new CenterCrop(),
                                            new InnerStrokeTransformation(
                                                    convertDpToPx(2f),
                                                    ContextCompat.getColor(SearchActivity.this, R.color.marketplace_lamoda)
                                            )
                                    ))
                                    .placeholder(R.drawable.search_page_lamoda_image_stroke)
                                    .error(R.drawable.search_page_lamoda_image_stroke)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .into(LamodaImage);
                            LamodaTitleText.setVisibility(View.VISIBLE);
                            LamodaPriceText.setVisibility(View.VISIBLE);
                            LamodaRatingText.setVisibility(View.VISIBLE);
                            LamodaImage.setVisibility(View.VISIBLE);
                            lamodaFeedbackText.setVisibility(View.GONE);
                            break;
                    }
                });
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(SearchActivity.this, "Ошибка соединения: " + error, Toast.LENGTH_SHORT).show();
                });
            }

        });

        webSocketClient.connect();
    }

    private void performSearch(String searchText) {
        String ipAddress = getIPAddress();
        int pageNumber = SettingsAppActivity.getSavedPageNumber(this);
        List<String> activeMarketplaces = SettingsAppActivity.getActiveMarketplaces(this);

        hideProductElements();

        ApiClient apiClient = new ApiClient();

        apiClient.searchProducts(ipAddress, searchText, pageNumber, activeMarketplaces, new ApiClient.ApiCallback<ApiClient.SearchResponse>() {
            @Override
            public void onSuccess(ApiClient.SearchResponse response) {
                setupWebSocketConnection(ipAddress);
            }

            @Override
            public void onFailure(String error) {
                runOnUiThread(() -> {
                    Toast.makeText(SearchActivity.this, "Ошибка поиска: " + error, Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private String getIPAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {
            ex.printStackTrace();
        }
        return "unknown";
    }
}