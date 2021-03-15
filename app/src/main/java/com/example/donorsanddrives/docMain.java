package com.example.donorsanddrives;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class docMain extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
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