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

public class viewCampaign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_campaign);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String details = sharedPreferences.getString("campaignDetails", null);

        try {
            assert details != null;
            JSONObject jsonObject = new JSONObject(details);

            TextView textView1 = findViewById(R.id.textView1);
            textView1.append(jsonObject.getString("campaign_id"));

            TextView textView2 = findViewById(R.id.textView3);
            textView2.setText(jsonObject.getString("campaignManager_id"));

            TextView textView3 = findViewById(R.id.textView5);
            textView3.setText(jsonObject.getString("date"));

            TextView textView4 = findViewById(R.id.textView7);
            textView4.setText(jsonObject.getString("start_time"));

            TextView textView5 = findViewById(R.id.textView9);
            textView5.setText(jsonObject.getString("end_time"));

            TextView textView6 = findViewById(R.id.textView11);
            textView6.setText(jsonObject.getString("location"));

            TextView textView7 = findViewById(R.id.textView13);
            String lat = jsonObject.getString("latitude") + ", " + jsonObject.getString("longitude");
            textView7.setText(lat);

            TextView textView8 = findViewById(R.id.textView15);
            textView8.setSelected(true);
            String address = jsonObject.getString("street_no") + ", " + jsonObject.getString("street") +
                    ", " + jsonObject.getString("city") + ", " + jsonObject.getString("province");
            textView8.setText(address);

            Button button = findViewById(R.id.button7);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), editCampaign.class);
                    startActivity(intent);
                }
            });



        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}