package com.example.dorafundak.multitask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class BlueActivity extends AppCompatActivity {

    Button calculatorButton;
    Button notesButton;

    EditText fuelPriceET, usageET, distanceET;
    TextView tripPriceTV, fuelUsedTV;

    Double fuelPriced = 0.0, usaged = 0.0, distanced = 0.0;
    Double tripPriceD = 0.0, fuelUsedd = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        calculatorButton = findViewById(R.id.calculatorButton);
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlueActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        notesButton = findViewById(R.id.notesButton);
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlueActivity.this, RedActivity.class);
                startActivity(intent);
            }
        });

        fuelPriceET = findViewById(R.id.fuelPrice);
        usageET = findViewById(R.id.usage);
        distanceET = findViewById(R.id.distance);
        tripPriceTV = findViewById(R.id.tripPrice);
        fuelUsedTV = findViewById(R.id.fuelVolume);

        fuelPriceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!fuelPriceET.getText().toString().isEmpty()) {
                    fuelPriced = Double.parseDouble(fuelPriceET.getText().toString());
                } else {
                    fuelPriced = 0.0;
                }
                calculate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        usageET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!usageET.getText().toString().isEmpty()) {
                    usaged = Double.parseDouble(usageET.getText().toString());
                } else {
                    usaged = 0.0;
                }
                calculate();

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        distanceET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!distanceET.getText().toString().isEmpty()) {
                    distanced = Double.parseDouble(distanceET.getText().toString());
                } else {
                    distanced = 0.0;
                }
                calculate();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View v = getCurrentFocus();

        if (v != null &&
                (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) &&
                v instanceof EditText &&
                !v.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            v.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + v.getLeft() - scrcoords[0];
            float y = ev.getRawY() + v.getTop() - scrcoords[1];

            if (x < v.getLeft() || x > v.getRight() || y < v.getTop() || y > v.getBottom())
                hideKeyboard(this);
        }
        return super.dispatchTouchEvent(ev);
    }

    public static void hideKeyboard(Activity activity) {
        if (activity != null && activity.getWindow() != null && activity.getWindow().getDecorView() != null) {
            InputMethodManager imm = (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private void calculate() {
        if (fuelPriced != 0 && usaged != 0 && distanced != 0) {
            tripPriceD = distanced * (usaged / 100) * fuelPriced;
            tripPriceTV.setText(String.format("%.2f", tripPriceD));
        }

        if (usaged != 0 && distanced != 0) {
            fuelUsedd = distanced * (usaged / 100);
            fuelUsedTV.setText(String.format("%.2f", fuelUsedd));
        }

        if (fuelPriced == 0) {
            tripPriceTV.setText("");
            tripPriceD = 0.0;
        }
        if (distanced == 0 || usaged == 0) {
            tripPriceTV.setText("");
            tripPriceD = 0.0;
            fuelUsedTV.setText("");
            fuelUsedd = 0.0;
        }
    }
}
