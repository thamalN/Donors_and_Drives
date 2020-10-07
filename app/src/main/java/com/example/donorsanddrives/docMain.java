package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class docMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doc_main);

        Button button = findViewById(R.id.button10);
        Button button2 = findViewById(R.id.button12);
        Button button3 = findViewById(R.id.button13);

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
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func3();
            }
        });
    }

    public void func() {
        Intent intent = new Intent(this, viewDocAcc.class);
        startActivity(intent);

    }

    public void func2() {
        Intent intent = new Intent(this, editDocAcc.class);
        startActivity(intent);

    }

    public void func3() {
        Intent intent = new Intent(this, deleteDocAcc.class);
        startActivity(intent);

    }
}