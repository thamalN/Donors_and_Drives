package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class ViewDonor extends AppCompatActivity {

    public void toActivity8(View v) {
        startActivity(new Intent(this, EditDonorMedDetails.class));
    }


    public void toActivity5(View v) {
        startActivity(new Intent(this, FindDonor.class));
    }

    public void toDonationsActivity(View v) {
        startActivity(new Intent(this, DonationsActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_donor);

        TextView donorName = (TextView) findViewById(R.id.donorName);
        TextView donorID = (TextView) findViewById(R.id.donorid);
        TextView gender = (TextView) findViewById(R.id.gendert);
        TextView bloodType = (TextView) findViewById(R.id.bloodtypet);
        TextView birthDay = (TextView) findViewById(R.id.birthdayt);
        TextView age = (TextView) findViewById(R.id.aget);
        TextView weight = (TextView) findViewById(R.id.weightt);
        TextView height = (TextView) findViewById(R.id.heightt);
        TextView bmi = (TextView) findViewById(R.id.bmit);
        TextView status = (TextView) findViewById(R.id.statust);
        TextView reasons = (TextView) findViewById(R.id.reasonst);
        TextView ineligibleSince = (TextView) findViewById(R.id.ineligiblesincet);
        TextView address = (TextView) findViewById(R.id.addresst);
        TextView contact = (TextView) findViewById(R.id.contactt);
        TextView email = (TextView) findViewById(R.id.emailt);

        Intent intent = getIntent();
        try {
            JSONObject donorObj = new JSONObject(intent.getStringExtra("currentDonor"));
            donorName.setText("Donor Name - " + donorObj.getString("name"));
            donorID.setText("Donor ID - " + donorObj.getString("user_id"));
            gender.setText(donorObj.getString("gender"));
            bloodType.setText(donorObj.getString("blood_type"));
            birthDay.setText(donorObj.getString("birthday"));
            age.setText(donorObj.getString("age"));
            weight.setText(donorObj.getString("weight"));
            height.setText(donorObj.getString("height"));
            bmi.setText(donorObj.getString("bmi"));
            status.setText(donorObj.getString("status"));
            reasons.setText(donorObj.getString("reasons_if_ineligible"));
            ineligibleSince.setText(donorObj.getString("ineligible_since"));
            address.setText(donorObj.getString("street_no") + ", " + donorObj.getString("street") + ", " + donorObj.getString("city") + ", " + donorObj.getString("province"));
            contact.setText(donorObj.getString("contact"));
            email.setText(donorObj.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }


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