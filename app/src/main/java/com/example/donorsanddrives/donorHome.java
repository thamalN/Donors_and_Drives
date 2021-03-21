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

public class donorHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDonationHistory();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDonorLeaderboard();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewdBloodDrives();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(donorHome.this, v);
                popup.setOnMenuItemClickListener(donorHome.this);
                popup.inflate(R.menu.popup_menu_2);
                popup.show();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.map:
                        startActivity(new Intent(getApplicationContext()
                                , MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext()
                                , profile.class));
                        overridePendingTransition(0, 0);
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

                case R.id.notifications:
                    return true;
                case R.id.about:
                    return true;

                case R.id.log_out:
                    Intent intent = new Intent(donorHome.this, MainActivity.class);
                    startActivity(intent);
                    return false;

                default:
                    return false;

        }
    }

        public void viewDonationHistory() {
            Intent intent = new Intent(this, viewDonHistory.class);
            startActivity(intent);
        }

    public void viewDonorLeaderboard() {
        Intent intent = new Intent(this, viewDonLead.class);
        startActivity(intent);
    }

    public void viewdBloodDrives() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }


}