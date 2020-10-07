package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class manage_accs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_accs);

        Button button = findViewById(R.id.radioButton2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageDoctor();
            }
        });
    }

    public void manageDoctor() {
        Intent intent = new Intent(this, doctor_acc.class);
        startActivity(intent);
    }

}