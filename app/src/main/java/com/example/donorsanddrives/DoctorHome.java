package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class DoctorHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {


    public void toActivity4(View v) {
        startActivity(new Intent(this, BloodStocks.class));
    }


    public void toSearchDoc(View v) { startActivity(new Intent(this, SearchDoc.class)); }

    public void toSearchCM(View v) {
        startActivity(new Intent(this, SearchCM.class));
    }

    public void toSearchCamp(View v) {
        startActivity(new Intent(this, SearchCamp.class));
    }

    public void toSearchStation(View v) { startActivity(new Intent(this, SearchStation.class)); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);
        Button btn = (Button) findViewById(R.id.btnShow);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(DoctorHome.this, v);
                popup.setOnMenuItemClickListener(DoctorHome.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.map:
                        startActivity(new Intent(getApplicationContext()
                                , DoctorMapsActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.donors:
                        startActivity(new Intent(getApplicationContext()
                                , FindDonor.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {

            case R.id.edit_profile:
                Intent myintent1 = new Intent(DoctorHome.this, EditProfileActivity.class);
                startActivity(myintent1);
                return false;

            case R.id.notifications:
                // do your code
                return true;

            case R.id.log_out:
                Intent myintent2 = new Intent(DoctorHome.this, MainActivity.class);
                startActivity(myintent2);
                return false;

            default:
                return false;
        }
    }

}