package com.example.donorsanddrives;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DonationsActivity extends AppCompatActivity {

    public void toActivityViewDonor(View v) {
        startActivity(new Intent(this, ViewDonor.class));
    }

    public int dpTOpx(float dp){
        //float dp = 10f;
        Resources r = getResources();
        float px = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp,
                r.getDisplayMetrics()
        );
        return (int) px;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donations);

        TextView donor_name = (TextView) findViewById(R.id.donorName);
        TextView donor_id = (TextView) findViewById(R.id.donorid);
        final LinearLayout donations_layout = (LinearLayout) findViewById(R.id.donations);

        RequestQueue queue = Volley.newRequestQueue(DonationsActivity.this);

        Intent intent = getIntent();
        String donorID = intent.getStringExtra("curDonorID");
        String donorName = intent.getStringExtra("curDonorName");
        donor_name.setText("Donor Name - " + donorID);
        donor_id.setText("Donor ID - " + donorName);

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewDonationsOfDonor/" + donorID;


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONArray returnedDonations = (JSONArray) response;

                for(int i=0; i<returnedDonations.length(); i++){

                    LinearLayout linearLayout = new LinearLayout(DonationsActivity.this);

                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.setMargins(0, dpTOpx(10), 0, 0);
                    linearLayout.setLayoutParams(params);

                    /////
                    TextView driveID = new TextView(DonationsActivity.this);

                    LinearLayout.LayoutParams paramsDrive = new LinearLayout.LayoutParams(
                            dpTOpx(50),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsDrive.setMargins(0, dpTOpx(5), dpTOpx(40), 0);
                    driveID.setLayoutParams(paramsDrive);

                    //////
                    TextView stationID = new TextView(DonationsActivity.this);

                    LinearLayout.LayoutParams paramsStation = new LinearLayout.LayoutParams(
                            dpTOpx(50),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsStation.setMargins(0, dpTOpx(5), dpTOpx(40), 0);
                    stationID.setLayoutParams(paramsStation);

                    //////
                    TextView date = new TextView(DonationsActivity.this);

                    LinearLayout.LayoutParams paramsDate = new LinearLayout.LayoutParams(
                            dpTOpx(80),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsDate.setMargins(0, dpTOpx(5), dpTOpx(40), 0);
                    date.setLayoutParams(paramsDate);

                    //////
//                    TextView time = new TextView(DonationsActivity.this);
//
//                    LinearLayout.LayoutParams paramsTime = new LinearLayout.LayoutParams(
//                            dpTOpx(50),
//                            LinearLayout.LayoutParams.WRAP_CONTENT
//                    );
//
//                    paramsTime.setMargins(0, dpTOpx(5), dpTOpx(40), 0);
//                    time.setLayoutParams(paramsTime);


                    //////
                    TextView amount = new TextView(DonationsActivity.this);

                    LinearLayout.LayoutParams paramsAmt = new LinearLayout.LayoutParams(
                            dpTOpx(50),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsAmt.setMargins(0, dpTOpx(5), dpTOpx(40), 0);
                    amount.setLayoutParams(paramsAmt);


                    //
                    try {
                        JSONObject Donation = returnedDonations.getJSONObject(i);
                        driveID.setText(Donation.getString("campaign_id"));
                        stationID.setText(Donation.getString("station_id"));
                        date.setText(Donation.getString("date"));
//                        time.setText(Donation.getString("time"));
                        amount.setText(Donation.getString("amount"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // add the textview to the linearlayout
                    linearLayout.addView(driveID);
                    linearLayout.addView(stationID);
                    linearLayout.addView(date);
//                    linearLayout.addView(time);
                    linearLayout.addView(amount);

                    donations_layout.addView(linearLayout, 4 + i);

//                    ScrollView sv = new ScrollView(this);
//                    LinearLayout ll = new LinearLayout(this);
//                    ll.setOrientation(LinearLayout.VERTICAL);
//                    sv.addView(ll);

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DonationsActivity.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);



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