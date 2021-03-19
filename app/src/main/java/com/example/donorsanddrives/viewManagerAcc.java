package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class viewManagerAcc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_manager_acc);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("details", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);

            TextView textView1 = findViewById(R.id.textView14);
            textView1.append(jsonObject.getString("user_id"));

            TextView textView2 = findViewById(R.id.textView17);
            textView2.setText(jsonObject.getString("name"));

            TextView textView3 = findViewById(R.id.textView24);
            textView3.setText(jsonObject.getString("regDate"));

            TextView textView4 = findViewById(R.id.textView21);
            textView4.setText(jsonObject.getString("email"));

            TextView textView5 = findViewById(R.id.textView20);
            textView5.setText(jsonObject.getString("contact"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}