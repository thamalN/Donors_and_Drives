package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class doctor_acc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_acc);

        Button button = findViewById(R.id.button5);
        Button button2 = findViewById(R.id.button6);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func2();
            }
        });
    }

    public void func() {
        Intent intent = new Intent(this, createDocAcc.class);
        startActivity(intent);

    }

    public void func2() {
        Intent intent = new Intent(this, searchDocId.class);
        startActivity(intent);

    }
}