package com.example.donorsanddrives;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class viewDonHistory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_history);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", null);

        TextView textView1 = findViewById(R.id.textView1);
        textView1.append(id);

        try {
            String s = sharedPreferences.getString("donHistory", null);
            assert s != null;
            JSONArray jsonArray = new JSONArray(s);

            if (jsonArray.length() == 0) {
                Toast.makeText(viewDonHistory.this, "No records found!", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);


                    TextView textView2 = findViewById(R.id.textView9);
                    textView2.setText(jsonObject.getString("donation_id"));

                    TextView textView3 = findViewById(R.id.textView10);
                    textView3.setText(jsonObject.getString("campaign_id"));

                    TextView textView4 = findViewById(R.id.textView11);
                    textView4.setText(jsonObject.getString("station_id"));

                    TextView textView5 = findViewById(R.id.textView12);
                    textView4.setText(jsonObject.getString("date"));

                    TextView textView6 = findViewById(R.id.textView13);
                    textView4.setText(jsonObject.getString("time"));

                    TextView textView = findViewById(R.id.textView14);
                    textView4.setText(jsonObject.getString("amount"));

                    TextView textView8 = findViewById(R.id.textView15);
                    textView4.setText(jsonObject.getString("comments"));
                }


            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}