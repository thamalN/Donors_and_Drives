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
        Button button2 = findViewById(R.id.radioButton3);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageDoctor();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageManager();
            }
        });
    }

    public void manageDoctor() {
        Intent intent = new Intent(this, doctor_acc.class);
        startActivity(intent);
    }

    public void manageManager() {
        Intent intent = new Intent(this, managerAcc.class);
        startActivity(intent);
    }

}