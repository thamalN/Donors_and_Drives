package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class managerMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

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

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("details", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);
            TextView textView = findViewById(R.id.textView14);
            textView.append(jsonObject.getString("user_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void func() {
        Intent intent = new Intent(this, viewManagerAcc.class);
        startActivity(intent);
    }

    public void func2() {
        Intent intent = new Intent(this, editManagerAcc.class);
        startActivity(intent);
    }

    public void func3() {
        Intent intent = new Intent(this, deleteManagerAcc.class);
        startActivity(intent);
    }
}