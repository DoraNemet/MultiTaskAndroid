package com.example.dorafundak.multitask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button fuelButton;
    private Button notesButton;

    private TextView currentScreen;
    private TextView lastScreen;
    private String display = "";
    private String currentOperator = "";
    private Double va, vb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fuelButton = findViewById(R.id.fuelButton);
        fuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BlueActivity.class);
                startActivity(intent);
            }
        });

        notesButton = (findViewById(R.id.notesButton));
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RedActivity.class);
                startActivity(intent);
            }
        });
        currentScreen = findViewById(R.id.currentNumber);
        lastScreen = findViewById(R.id.lastNumber);
    }

    private void updateScreen() {
        currentScreen.setText(display);
    }

    public void onClickNumber(View view) {
        Button button = (Button) view;
        if (currentOperator == "=") {
            currentOperator = "";
            va = 0.0;
            vb = 0.0;
            lastScreen.setText("");
        }
        display += button.getText();
        updateScreen();
    }

    public void onClickOperator(View view) {
        Button b = (Button) view;
        display = "";

        if (currentOperator != "" && currentOperator != "=") {
            vb = Double.parseDouble(currentScreen.getText().toString());

            if (currentOperator.equals("+")) {
                va = va + vb;
            } else if (currentOperator.equals("-")) {
                va = va - vb;
            } else if (currentOperator.equals("x")) {
                va = va * vb;
            } else {
                va = va / vb;
            }
            lastScreen.setText(Double.toString(va));
            currentOperator = b.getText().toString();
            currentScreen.setText(currentOperator);

        } else {
            currentOperator = b.getText().toString();
            if (!lastScreen.getText().toString().equals("")) {
                va = Double.parseDouble(lastScreen.getText().toString());
            } else {
                va = Double.parseDouble(currentScreen.getText().toString());
            }
            currentScreen.setText(currentOperator);
            lastScreen.setText(va.toString());
        }
    }

    public void onClickAC(View view) {
        display = "";
        currentOperator = "";
        va = 0.0;
        vb = 0.0;
        currentScreen.setText("");
        lastScreen.setText("");
        updateScreen();
    }

    public void onClickC(View view) {
        String text = currentScreen.getText().toString();
        if (text != "") {
            text = text.substring(0, text.length() - 1);
            display = text;
            currentScreen.setText(text);
        }
    }

    public void onClickEquals(View view) {
        if (currentOperator != "") {
            vb = Double.parseDouble(currentScreen.getText().toString());

            if (currentOperator.equals("+")) {
                va = va + vb;
            } else if (currentOperator.equals("-")) {
                va = va - vb;
            } else if (currentOperator.equals("x")) {
                va = va * vb;
            } else {
                va = va / vb;
            }
            lastScreen.setText(Double.toString(va));
            currentOperator = "";
            currentScreen.setText("");
        }
    }
}
