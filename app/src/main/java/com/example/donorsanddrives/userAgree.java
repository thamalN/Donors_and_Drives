package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class userAgree extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_agree);

        Button button = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func1();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func2();
            }
        });
    }

    public void func1() {
        Intent intent = new Intent(this, donQuiz.class);
        startActivity(intent);
    }

    public void func2() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

}