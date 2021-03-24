package com.example.donorsanddrives;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class profile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editProfile();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewMedicalDetails();
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                donationHistory();
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.profile);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.map:
                        startActivity(new Intent(getApplicationContext()
                                , MapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , donorHome.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        return true;
                }
                return false;
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
            textView3.setText(jsonObject.getString("gender"));

            TextView textView4 = findViewById(R.id.textView7);
            textView4.setText(jsonObject.getString("blood_type"));

            TextView textView5 = findViewById(R.id.textView9);
            textView5.setText(jsonObject.getString("birthday"));

            TextView textView6 = findViewById(R.id.textView11);
            textView6.setText(jsonObject.getString("age"));

            TextView textView7 = findViewById(R.id.textView13);
            textView7.setSelected(true);
            String address = jsonObject.getString("street_no") + ", " + jsonObject.getString("street") +
                    ", " + jsonObject.getString("city") + ", " + jsonObject.getString("province");
            textView7.setText(address);

            TextView textView8 = findViewById(R.id.textView15);
            textView8.setText(jsonObject.getString("email"));

            TextView textView9 = findViewById(R.id.textView17);
            textView9.setText(jsonObject.getString("contact"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void editProfile() {
        Intent intent = new Intent(this, editDonAcc.class);
        startActivity(intent);
    }

    public void viewMedicalDetails() {
        Intent intent = new Intent(this, viewMedicalDetails.class);
        startActivity(intent);
    }

    public void donationHistory() {
        Intent intent = new Intent(this, viewDonHistory.class);
        startActivity(intent);
    }
}