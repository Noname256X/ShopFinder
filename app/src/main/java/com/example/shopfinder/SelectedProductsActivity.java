package com.example.shopfinder;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SelectedProductsActivity extends AppCompatActivity {

    private ConstraintLayout rootLayout;
    private boolean isSearchMode = false;

    //filter
    private ConstraintLayout filtersContainer;
    private boolean isCheckboxChecked = false;
    private boolean isFormatting = false;

    //navbar
    private RelativeLayout mainFullNavigationButton;
    private ImageButton mainNavigationButton;
    private EditText productEntryField;

    private ConstraintLayout productBlock;
    private int currentProductIndex = 0;
    private int currentImageIndex = 0;
    private List<ProductData> userProducts = new ArrayList<>();
    private ImageView mainImage;
    private ImageView rightIconInBut;
    private ImageView leftIconInBut;
    private FrameLayout rightButtonProductBlock;
    private FrameLayout leftButtonProductBlock;
    private TextView nothingFavorites;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selected_products_page);

        rootLayout = findViewById(R.id.root_layout);
        filtersContainer = findViewById(R.id.filters_container);
        View closeButton = findViewById(R.id.close_button);
        ImageView filterIconBut = findViewById(R.id.filter_icon_but);
        ImageView checkboxNone = findViewById(R.id.checkbox_none);
        EditText priceFrom = findViewById(R.id.price_from);
        EditText priceTo = findViewById(R.id.price_to);
        productEntryField = findViewById(R.id.product_entry_field);

        //navbar
        mainNavigationButton = findViewById(R.id.mainNavigationButton);
        mainFullNavigationButton = findViewById(R.id.mainFullNavigationButton);
        ImageView leftIcon = findViewById(R.id.left_icon);
        ImageView centerIcon = findViewById(R.id.center_icon);
        ImageView rightIcon = findViewById(R.id.right_icon);

        //Product Block
        View closeButton2 = findViewById(R.id.close_button2);
        productBlock = findViewById(R.id.product_block);
        mainImage = findViewById(R.id.mainImage);
        rightIconInBut = findViewById(R.id.right_icon_in_but);
        leftIconInBut = findViewById(R.id.left_icon_in_but);
        rightButtonProductBlock = findViewById(R.id.rightButtonProductBlock);
        leftButtonProductBlock = findViewById(R.id.leftButtonProductBlock);
        nothingFavorites = findViewById(R.id.nothing_favorites);
        ImageView starEnableIcon = findViewById(R.id.star_enable_icon);
        ImageView shareIcon = findViewById(R.id.share_icon);
        ImageView openLinkIcon = findViewById(R.id.open_link_icon);

        openLinkIcon.setOnClickListener(v -> openProductLink());

        productBlock.setVisibility(View.GONE);
        mainFullNavigationButton.setVisibility(View.GONE);
        filtersContainer.setVisibility(View.GONE);
        nothingFavorites.setVisibility(View.GONE);

        mainNavigationButton.setOnClickListener(v -> {
            mainNavigationButton.setVisibility(View.GONE);
            mainFullNavigationButton.setVisibility(View.VISIBLE);
        });

        rightIconInBut.setOnClickListener(v -> showNextImage());
        leftIconInBut.setOnClickListener(v -> showPreviousImage());

        rightButtonProductBlock.setOnClickListener(v -> showNextProduct());
        leftButtonProductBlock.setOnClickListener(v -> showPreviousProduct());

        starEnableIcon.setOnClickListener(v -> removeProductFromFavorites());
        shareIcon.setOnClickListener(v -> shareProduct());

        leftIcon.setOnClickListener(v -> {
            Intent searchIntent = new Intent(SelectedProductsActivity.this, SearchActivity.class);
            startActivity(searchIntent);
        });

        centerIcon.setOnClickListener(v -> {
            mainFullNavigationButton.setVisibility(View.GONE);
            mainNavigationButton.setVisibility(View.VISIBLE);
        });

        rightIcon.setOnClickListener(v -> {
            Intent settingsIntent = new Intent(SelectedProductsActivity.this, SettingsActivity.class);
            startActivity(settingsIntent);
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

        closeButton2.setOnClickListener(v -> {
            productBlock.setVisibility(View.GONE);
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

        checkboxNone.setOnClickListener(v -> {
            isCheckboxChecked = !isCheckboxChecked;
            checkboxNone.setImageResource(isCheckboxChecked ?
                    R.drawable.checkbox_icon : R.drawable.empty_check_icon);
        });

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
        loadUserProducts();
    }

    private void openProductLink() {
        if (userProducts.isEmpty() || currentProductIndex < 0 || currentProductIndex >= userProducts.size()) {
            Toast.makeText(this, "Товар не доступен", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductData product = userProducts.get(currentProductIndex);
        if (TextUtils.isEmpty(product.link)) {
            Toast.makeText(this, "Ссылка на товар отсутствует", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(product.link));
            startActivity(browserIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "Не удалось открыть браузер", Toast.LENGTH_SHORT).show();
        }
    }

    private void shareProduct() {
        if (userProducts.isEmpty() || currentProductIndex < 0 || currentProductIndex >= userProducts.size()) {
            Toast.makeText(this, "Нет товара для отправки", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductData currentProduct = userProducts.get(currentProductIndex);

        String shareText = "Посмотрите этот товар:\n\n" +
                currentProduct.title + "\n" +
                "Цена: " + currentProduct.price + "\n" +
                "Рейтинг: " + currentProduct.rating + " (" + currentProduct.reviews + " отзывов)\n" +
                "Ссылка: " + currentProduct.link;

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);

        startActivity(Intent.createChooser(shareIntent, "Поделиться через"));
    }

    private void removeProductFromFavorites() {
        if (userProducts.isEmpty() || currentProductIndex < 0 || currentProductIndex >= userProducts.size()) {
            return;
        }

        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            return;
        }

        ProductData currentProduct = userProducts.get(currentProductIndex);
        OkHttpClient client = new OkHttpClient();

        JSONObject json = new JSONObject();
        try {
            json.put("user_id", userId);
            json.put("product_link", currentProduct.link);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json"),
                json.toString()
        );

        Request request = new Request.Builder()
                .url("http://192.168.1.4:3000/api/favorites/remove")
                .post(body)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() ->
                        Toast.makeText(SelectedProductsActivity.this,
                                "Ошибка удаления товара: " + e.getMessage(),
                                Toast.LENGTH_SHORT).show()
                );
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    runOnUiThread(() ->
                            Toast.makeText(SelectedProductsActivity.this,
                                    "Ошибка сервера: " + response.code(),
                                    Toast.LENGTH_SHORT).show()
                    );
                    return;
                }

                runOnUiThread(() -> {
                    // Удаляем товар из локального списка
                    userProducts.remove(currentProductIndex);

                    // Обновляем отображение
                    if (userProducts.isEmpty()) {
                        showNoFavorites();
                    } else {
                        // Корректируем индекс, если он вышел за границы
                        if (currentProductIndex >= userProducts.size()) {
                            currentProductIndex = userProducts.size() - 1;
                        }
                        if (currentProductIndex >= 0) {
                            displayCurrentProduct();
                        } else {
                            showNoFavorites();
                        }
                    }

                    Toast.makeText(SelectedProductsActivity.this,
                            "Товар удалён из избранного",
                            Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    private void loadUserProducts() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(this, "Пользователь не авторизован", Toast.LENGTH_SHORT).show();
            showNoFavorites();
            return;
        }

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("http://192.168.1.4:3000/api/favorites?user_id=" + userId)
                .header("Content-Type", "application/json")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(() -> {
                    Toast.makeText(SelectedProductsActivity.this,
                            "Ошибка загрузки данных: " + e.getMessage(),
                            Toast.LENGTH_SHORT).show();
                    showNoFavorites();
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (!response.isSuccessful()) {
                        runOnUiThread(() -> {
                            Toast.makeText(SelectedProductsActivity.this,
                                    "Ошибка сервера: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                            showNoFavorites();
                        });
                        return;
                    }

                    String responseData = response.body() != null ? response.body().string() : "";
                    Log.d("API_RESPONSE", responseData);

                    try {
                        JSONObject json = new JSONObject(responseData);

                        if (json.has("error")) {
                            String error = json.optString("error", "Неизвестная ошибка");
                            runOnUiThread(() -> {
                                Toast.makeText(SelectedProductsActivity.this, error, Toast.LENGTH_SHORT).show();
                                showNoFavorites();
                            });
                            return;
                        }

                        JSONArray productsArray = json.getJSONArray("products");
                        userProducts.clear();

                        for (int i = 0; i < productsArray.length(); i++) {
                            JSONObject productJson = productsArray.getJSONObject(i);
                            ProductData productData = parseProductData(productJson);

                            if (productJson.has("photos")) {
                                JSONArray photos = productJson.optJSONArray("photos");
                                if (photos != null && photos.length() > 0) {
                                    for (int j = 0; j < photos.length(); j++) {
                                        productData.images.add(photos.getString(j));
                                    }
                                }
                            }

                            userProducts.add(productData);
                        }

                        runOnUiThread(() -> {
                            if (userProducts.isEmpty()) {
                                showNoFavorites();
                            } else {
                                showProducts();
                                currentProductIndex = 0;
                                currentImageIndex = 0;
                                displayCurrentProduct();
                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
                        runOnUiThread(() -> {
                            Toast.makeText(SelectedProductsActivity.this,
                                    "Ошибка формата данных: " + e.getMessage(),
                                    Toast.LENGTH_SHORT).show();
                            showNoFavorites();
                        });
                    }
                } finally {
                    if (response != null) {
                        response.close();
                    }
                }
            }
        });
    }

    private void showNoFavorites() {
        runOnUiThread(() -> {
            productBlock.setVisibility(View.GONE);
            nothingFavorites.setVisibility(View.VISIBLE);
        });
    }

    private void showProducts() {
        runOnUiThread(() -> {
            nothingFavorites.setVisibility(View.GONE);
            productBlock.setVisibility(View.VISIBLE);
        });
    }

    private void displayCurrentProduct() {
        if (userProducts.isEmpty() || currentProductIndex < 0 || currentProductIndex >= userProducts.size()) {
            return;
        }

        ProductData currentProduct = userProducts.get(currentProductIndex);

        TextView productText = findViewById(R.id.product_text);
        TextView priceText = findViewById(R.id.priceText);
        TextView reviewsText = findViewById(R.id.reviewsText);
        TextView ratingText = findViewById(R.id.rating_text_view);
        MaterialButton marketplaceButton = findViewById(R.id.ozon_button);

        productText.setText(currentProduct.title);
        priceText.setText(currentProduct.price);
        ratingText.setText(currentProduct.rating);
        reviewsText.setText("отзывы: " + currentProduct.reviews);

        marketplaceButton.setText(currentProduct.marketplace);
        int colorResId = getMarketplaceColor(currentProduct.marketplace);
        marketplaceButton.setBackgroundTintList(ContextCompat.getColorStateList(this, colorResId));

        if (!currentProduct.images.isEmpty()) {
            Glide.with(this)
                    .load(currentProduct.images.get(0))
                    .override(convertDpToPx(195.05f), convertDpToPx(173.33f))
                    .centerCrop()
                    .placeholder(R.drawable.no_photo_pictures)
                    .error(R.drawable.no_photo_pictures)
                    .into(mainImage);
        } else {
            mainImage.setImageResource(R.drawable.no_photo_pictures);
        }

        updateNavigationButtons();
    }

    private void showNextProduct() {
        if (userProducts.size() <= 1) {
            return;
        }

        currentProductIndex = (currentProductIndex + 1) % userProducts.size();
        displayCurrentProduct();
    }

    private void showPreviousProduct() {
        if (userProducts.size() <= 1) {
            return;
        }

        currentProductIndex = (currentProductIndex - 1 + userProducts.size()) % userProducts.size();
        displayCurrentProduct();
    }

    private void showNextImage() {
        ProductData currentProduct = userProducts.get(currentProductIndex);
        if (currentProduct.images.size() <= 1) {
            return;
        }

        currentImageIndex = (currentImageIndex + 1) % currentProduct.images.size();
        loadImage(currentProduct.images.get(currentImageIndex));
    }

    private void showPreviousImage() {
        ProductData currentProduct = userProducts.get(currentProductIndex);
        if (currentProduct.images.size() <= 1) {
            return;
        }

        currentImageIndex = (currentImageIndex - 1 + currentProduct.images.size()) % currentProduct.images.size();
        loadImage(currentProduct.images.get(currentImageIndex));
    }

    private void updateNavigationButtons() {
        int productsVisibility = userProducts.size() > 1 ? View.VISIBLE : View.INVISIBLE;
        rightButtonProductBlock.setVisibility(productsVisibility);
        leftButtonProductBlock.setVisibility(productsVisibility);

        if (!userProducts.isEmpty() && currentProductIndex >= 0 && currentProductIndex < userProducts.size()) {
            ProductData currentProduct = userProducts.get(currentProductIndex);
            int imagesVisibility = currentProduct.images.size() > 1 ? View.VISIBLE : View.INVISIBLE;
            rightIconInBut.setVisibility(imagesVisibility);
            leftIconInBut.setVisibility(imagesVisibility);
        }
    }

    private void loadImage(String imageUrl) {
        Glide.with(SelectedProductsActivity.this)
                .load(imageUrl)
                .override(convertDpToPx(195.05f), convertDpToPx(173.33f))
                .centerCrop()
                .placeholder(R.drawable.no_photo_pictures)
                .error(R.drawable.no_photo_pictures)
                .into(mainImage);
    }

    private ProductData parseProductData(JSONObject productJson) {
        ProductData product = new ProductData();
        try {
            product.title = productJson.optString("product_name", "");
            product.price = formatPrice(productJson.optInt("price", 0));


            double ratingValue = productJson.optDouble("rating", 0.0);
            DecimalFormat df = new DecimalFormat("#.#", new DecimalFormatSymbols(Locale.US));
            product.rating = df.format(ratingValue);



            product.reviews = String.valueOf(productJson.optInt("reviews_count", 0));
            product.link = productJson.optString("link_url", "");
            product.marketplace = getMarketplaceFromUrl(product.link);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    private String getMarketplaceFromUrl(String url) {
        if (url.contains("ozon.ru")) return "Ozon";
        if (url.contains("wildberries.ru")) return "Wildberries";
        if (url.contains("market.yandex.ru")) return "YandexMarket";
        if (url.contains("mm.ru")) return "MagnitMarket";
        if (url.contains("dns-shop.ru")) return "DNS";
        if (url.contains("citilink.ru")) return "Citilink";
        if (url.contains("mvideo.ru")) return "M_Video";
        if (url.contains("aliexpress.ru")) return "Aliexpress";
        if (url.contains("joom.ru")) return "Joom";
        if (url.contains("shop.mts.ru")) return "Shop_mts";
        if (url.contains("technopark.ru")) return "Technopark";
        if (url.contains("lamoda.ru")) return "Lamoda";
        return "Unknown";
    }

    private String formatPrice(int price) {
        NumberFormat formatter = NumberFormat.getInstance(Locale.getDefault());
        formatter.setGroupingUsed(true);
        return formatter.format(price) + " ₽";
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
            productEntryField.setHint(R.string.selected_search_field);
        }
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

    private static class ProductData {
        String title;
        String price;
        String rating;
        String reviews;
        String link;
        String marketplace;
        List<String> images = new ArrayList<>();
    }
}