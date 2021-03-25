package com.example.donorsanddrives;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class viewMedicalDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_medical_details);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("donorDetails", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);

            TextView textView1 = findViewById(R.id.textView1);
            textView1.append(jsonObject.getString("user_id"));

            TextView textView2 = findViewById(R.id.textView3);
            textView2.setText(jsonObject.getString("name"));

            TextView textView3 = findViewById(R.id.textView5);
            textView3.setText(jsonObject.getString("blood_type"));

            TextView textView4 = findViewById(R.id.textView7);
            textView4.setText(jsonObject.getString("weight"));

            TextView textView5 = findViewById(R.id.textView9);
            textView5.setText(jsonObject.getString("height"));

            TextView textView6 = findViewById(R.id.textView11);
            textView6.setText(jsonObject.getString("bmi"));

            TextView textView7 = findViewById(R.id.textView13);
            textView7.setText(jsonObject.getString("status"));

            TextView textView8 = findViewById(R.id.textView15);
            textView8.setText(jsonObject.getString("reasons_if_ineligible"));

            TextView textView9 = findViewById(R.id.textView17);
            textView9.setText(jsonObject.getString("ineligible_since"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void func() {
        Intent intent = new Intent(this, profile.class);
        startActivity(intent);

    }
}