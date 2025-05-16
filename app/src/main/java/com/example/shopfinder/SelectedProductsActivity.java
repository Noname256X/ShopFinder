package com.example.shopfinder;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.text.NumberFormat;
import java.util.Locale;

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

        mainFullNavigationButton.setVisibility(View.GONE);
        filtersContainer.setVisibility(View.GONE);

        mainNavigationButton.setOnClickListener(v -> {
            mainNavigationButton.setVisibility(View.GONE);
            mainFullNavigationButton.setVisibility(View.VISIBLE);
        });

        leftIcon.setOnClickListener(v -> {
            Intent searchIntent = new Intent(SelectedProductsActivity.this, SearchActivity.class);
            startActivity(searchIntent);
        });

        centerIcon.setOnClickListener(v -> {
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
}