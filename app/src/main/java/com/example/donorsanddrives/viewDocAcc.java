package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class viewDocAcc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_doc_acc);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("details", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);

            TextView textView1 = findViewById(R.id.textView14);
            textView1.append(jsonObject.getString("user_id"));

            TextView textView2 = findViewById(R.id.textView17);
            textView2.setText(jsonObject.getString("name"));

            TextView textView3 = findViewById(R.id.textView18);
            textView3.setText(jsonObject.getString("gender"));

            TextView textView4 = findViewById(R.id.textView24);
            textView4.setText(jsonObject.getString("regDate"));

            TextView textView5 = findViewById(R.id.textView19);
            textView5.setSelected(true);
            String address = jsonObject.getString("street_no") + ", " + jsonObject.getString("street") +
                    ", " + jsonObject.getString("city") + ", " + jsonObject.getString("province");
            textView5.setText(address);

            TextView textView6 = findViewById(R.id.textView20);
            textView6.setText(jsonObject.getString("contact"));

            TextView textView7 = findViewById(R.id.textView21);
            textView7.setText(jsonObject.getString("email"));

            TextView textView8 = findViewById(R.id.textView22);
            textView8.setText(jsonObject.getString("hospital"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}