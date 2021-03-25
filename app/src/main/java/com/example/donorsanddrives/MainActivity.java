package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = findViewById(R.id.logIn);
        Button button2 = findViewById(R.id.signUp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLogIn();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickSignUp();
            }
        });
    }

    public void clickLogIn() {
        Intent intent = new Intent(this, MainActivity2.class);
        startActivity(intent);
    }

    public void clickSignUp() {
        Intent intent = new Intent(this, createDonAcc.class);
        startActivity(intent);
    }
}