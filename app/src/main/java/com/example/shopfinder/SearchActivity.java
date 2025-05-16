package com.example.shopfinder;

import android.content.Context;
import android.content.Intent;
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

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import com.bumptech.glide.Glide;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Locale;

import okhttp3.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.shopfinder.CustomWebSocketListener;
import com.example.shopfinder.ProductData;



public class SearchActivity extends AppCompatActivity {
    private EditText productEntryField;
    private ConstraintLayout rootLayout;
    private ConstraintLayout filtersContainer;
    private ConstraintLayout hintsContainer;
    private NestedScrollView productsContainer;
    private boolean isSearchMode = false;
    private boolean isCheckboxChecked = false;
    private boolean isFormatting = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_page);

        productEntryField = findViewById(R.id.product_entry_field);
        rootLayout = findViewById(R.id.root_layout);
        EditText priceFrom = findViewById(R.id.price_from);
        EditText priceTo = findViewById(R.id.price_to);
        ImageView checkboxNone = findViewById(R.id.checkbox_none);
        ImageView filterIconBut = findViewById(R.id.filter_icon_but);
        filtersContainer = findViewById(R.id.filters_container);
        hintsContainer = findViewById(R.id.hints_container);
        productsContainer = findViewById(R.id.products_container);
        View closeButton = findViewById(R.id.close_button);
        View searchButton = findViewById(R.id.search_icon_green_but);

        //navbar
        mainNavigationButton = findViewById(R.id.mainNavigationButton);
        mainFullNavigationButton = findViewById(R.id.mainFullNavigationButton);
        ImageView leftIcon = findViewById(R.id.left_icon);
        ImageView centerIcon = findViewById(R.id.center_icon);
        ImageView rightIcon = findViewById(R.id.right_icon);


        //FeedbackText
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

        //TitleText
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

        //PriceText
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

        //RatingText
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

        //Image
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

        productsContainer.setVisibility(View.GONE);
        hintsContainer.setVisibility(View.VISIBLE);
        filtersContainer.setVisibility(View.GONE);


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
            // Обработка клика по поиску
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
                hintsContainer.setVisibility(View.GONE);
                productsContainer.setVisibility(View.VISIBLE);
                performSearch(searchText);
            }
            hideFullNavigation();
        });

        filterIconBut.setOnClickListener(v -> {
            if (filtersContainer.getVisibility() == View.VISIBLE) {
                filtersContainer.setVisibility(View.GONE);
            } else {
                filtersContainer.setVisibility(View.VISIBLE);
            }
            hideFullNavigation();
            deactivateSearchMode();
        });

        closeButton.setOnClickListener(v -> {
            filtersContainer.setVisibility(View.GONE);
        });

        setupPriceField(priceFrom, "от ");
        setupPriceField(priceTo, "до ");

        priceFrom.setPadding(convertDpToPx(7.62f), priceFrom.getPaddingTop(),
                priceFrom.getPaddingRight(), priceFrom.getPaddingBottom());
        priceTo.setPadding(convertDpToPx(7.62f), priceTo.getPaddingTop(),
                priceTo.getPaddingRight(), priceTo.getPaddingBottom());

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

        checkboxNone.setOnClickListener(v -> {
            isCheckboxChecked = !isCheckboxChecked;
            checkboxNone.setImageResource(isCheckboxChecked ?
                    R.drawable.checkbox_icon : R.drawable.empty_check_icon);
        });

    }


    private void hideFullNavigation() {
        if (mainFullNavigationButton.getVisibility() == View.VISIBLE) {
            mainFullNavigationButton.setVisibility(View.GONE);
            mainNavigationButton.setVisibility(View.VISIBLE);
        }
    }

    private void setupPriceField(EditText field, String prefix) {
        field.setInputType(InputType.TYPE_CLASS_NUMBER);

        field.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (isFormatting) return;

                isFormatting = true;
                try {
                    String originalString = s.toString();
                    String cleanString = originalString.replaceAll("[^\\d]", "");

                    if (!cleanString.isEmpty()) {
                        long parsed = Long.parseLong(cleanString);
                        String formatted = formatNumber(parsed);
                        String result = prefix + formatted + " ₽";

                        if (!originalString.equals(result)) {
                            field.setText(result);
                            field.setSelection(result.length() - 2);
                        }
                    } else {
                        field.setText("");
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } finally {
                    isFormatting = false;
                }
            }
        });

        field.setOnKeyListener((v, keyCode, event) -> {
            if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_DOWN) {
                EditText editText = (EditText) v;
                int selectionStart = editText.getSelectionStart();
                String text = editText.getText().toString();

                if (selectionStart == text.length() - 1 && text.endsWith("₽")) {
                    editText.setSelection(text.length() - 2);
                    return true;
                }
            }
            return false;
        });
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

    // WebSocket клиент
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
                product.imageUrl = "http://192.168.1.4:3000/images/not%20in%20the%20database/" + images.getString(0);
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

    private void performSearch(String searchText) {
        String ipAddress = getIPAddress();

        ApiClient apiClient = new ApiClient();

        apiClient.searchProducts(ipAddress, searchText, new ApiClient.ApiCallback<ApiClient.SearchResponse>() {
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
                    switch (marketplace) {
                        case "Ozon":
                            ozonTitleText.setText(data.title);
                            ozonPriceText.setText(data.price);
                            ozonRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            wbTitleText.setText(data.title);
                            wbPriceText.setText(data.price);
                            wbRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            YandexMarketTitleText.setText(data.title);
                            YandexMarketPriceText.setText(data.price);
                            YandexMarketRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            MagnitMarketTitleText.setText(data.title);
                            MagnitMarketPriceText.setText(data.price);
                            MagnitMarketRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            dnsTitleText.setText(data.title);
                            dnsPriceText.setText(data.price);
                            dnsRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            CitilinkTitleText.setText(data.title);
                            CitilinkPriceText.setText(data.price);
                            CitilinkRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            MVideoTitleText.setText(data.title);
                            MVideoPriceText.setText(data.price);
                            MVideoRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            AliexpressTitleText.setText(data.title);
                            AliexpressPriceText.setText(data.price);
                            AliexpressRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            JoomTitleText.setText(data.title);
                            JoomPriceText.setText(data.price);
                            JoomRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            Mts_Shop_TitleText.setText(data.title);
                            Mts_Shop_PriceText.setText(data.price);
                            Mts_Shop_RatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            TechnoparkTitleText.setText(data.title);
                            TechnoparkPriceText.setText(data.price);
                            TechnoparkRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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
                            LamodaTitleText.setText(data.title);
                            LamodaPriceText.setText(data.price);
                            LamodaRatingText.setText(data.rating);
                            Log.d("ImageLoad", "Loading image from URL: " + data.imageUrl);
                            Glide.with(SearchActivity.this)
                                    .load(data.imageUrl)
                                    .override(convertDpToPx(171.43f), convertDpToPx(152.38f))
                                    .fitCenter()
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