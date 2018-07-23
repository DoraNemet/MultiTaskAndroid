package com.example.dorafundak.multitask;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class BlueActivity extends AppCompatActivity {

    Button calculatorButton;
    Button notesButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue);

        calculatorButton = (findViewById(R.id.calculatorButton));
        calculatorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlueActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        notesButton = (findViewById(R.id.notesButton));
        notesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BlueActivity.this, RedActivity.class);
                startActivity(intent);
            }
        });
    }
}
