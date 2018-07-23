package com.example.dorafundak.multitask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class RedActivity extends AppCompatActivity {

    Button calculatorButton;
    Button fuelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red);

        calculatorButton = (findViewById(R.id.calculatorButton));
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RedActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        fuelButton = (findViewById(R.id.fuelButton));
        fuelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RedActivity.this, BlueActivity.class);
                startActivity(intent);
            }
        });
    }
}
