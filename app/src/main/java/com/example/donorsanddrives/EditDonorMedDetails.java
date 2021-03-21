package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class EditDonorMedDetails extends AppCompatActivity {

    public void toActivity6(View v) {
        startActivity(new Intent(this, ViewDonor.class));
    }

    public void toActivity3(View v) {
        startActivity(new Intent(this, DoctorHome.class));
    }

    public void onCheckboxClicked(View view) {

        boolean checked = ((CheckBox) view).isChecked();

        switch(view.getId()) {

            case R.id.BP:
                if (checked);
            else
                break;

            case R.id.infectious:
                if (checked);
            else
                break;

            case R.id.lowhb:
                if (checked);
                else
                    break;

            case R.id.Medical:
                if (checked);
                else
                    break;

            case R.id.Medications:
                if (checked);
                else
                    break;

            case R.id.Other:
                if (checked);
                else
                    break;

            case R.id.sleep:
                if (checked);
                else
                    break;

            case R.id.Surgical:
                if (checked);
                else
                    break;

            case R.id.Travel:
                if (checked);
                else
                    break;

            case R.id.tti:
                if (checked);
                else
                    break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_donor_med_details);

        Spinner dropdown = findViewById(R.id.spinner1);
        String[] items = new String[]{"Eligible", "Temporarily Ineligible", "Permenantly Ineligible"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);

        dropdown.setAdapter(adapter);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.donors);

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
                        startActivity(new Intent(getApplicationContext()
                                , DoctorHome.class));
                        overridePendingTransition(0,0);
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
}