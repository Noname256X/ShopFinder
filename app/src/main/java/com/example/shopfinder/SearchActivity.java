package com.example.shopfinder;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.graphics.Rect;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;

import java.text.NumberFormat;
import java.util.Locale;

public class SearchActivity extends AppCompatActivity {
    private EditText productEntryField;
    private ConstraintLayout rootLayout;
    private ConstraintLayout filtersContainer;
    private ConstraintLayout hintsContainer;
    private NestedScrollView productsContainer;
    private boolean isSearchMode = false;
    private boolean isCheckboxChecked = false;
    private boolean isFormatting = false;

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

        // Изначально скрываем контейнер с товарами и показываем подсказки
        productsContainer.setVisibility(View.GONE);
        hintsContainer.setVisibility(View.VISIBLE);
        filtersContainer.setVisibility(View.GONE);

        // Обработчик нажатия на кнопку поиска
        searchButton.setOnClickListener(v -> {
            String searchText = productEntryField.getText().toString().trim();
            if (!searchText.isEmpty()) {
                // Скрываем подсказки и показываем товары
                hintsContainer.setVisibility(View.GONE);
                productsContainer.setVisibility(View.VISIBLE);

                // Здесь можно добавить логику для выполнения поиска
                // performSearch(searchText);
            }
        });

        filterIconBut.setOnClickListener(v -> {
            if (filtersContainer.getVisibility() == View.VISIBLE) {
                filtersContainer.setVisibility(View.GONE);
            } else {
                filtersContainer.setVisibility(View.VISIBLE);
            }
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

        rootLayout.setOnTouchListener((v, event) -> {
            if (isSearchMode && event.getAction() == MotionEvent.ACTION_DOWN) {
                Rect outRect = new Rect();
                productEntryField.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    deactivateSearchMode();
                    return true;
                }
            }
            return false;
        });

        checkboxNone.setOnClickListener(v -> {
            isCheckboxChecked = !isCheckboxChecked;
            checkboxNone.setImageResource(isCheckboxChecked ?
                    R.drawable.checkbox_icon : R.drawable.empty_check_icon);
        });
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
}