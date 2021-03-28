package com.example.donorsanddrives;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.MenuItem;
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
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DoctorHome extends AppCompatActivity{
    
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


//    public void toSearchDoc(View v) { startActivity(new Intent(this, SearchDoc.class)); }
//
//    public void toSearchStation(View v) { startActivity(new Intent(this, SearchStation.class)); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_home);

        TextView textID = (TextView) findViewById(R.id.user_id);

//        Button btn = (Button) findViewById(R.id.btnShow);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                PopupMenu popup = new PopupMenu(DoctorHome.this, v);
//                popup.setOnMenuItemClickListener(DoctorHome.this);
//                popup.inflate(R.menu.popup_menu);
//                popup.show();
//            }
//        });

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));
        textID.setText("Doctor ID - " + user_id);

        final LinearLayout drives_layout = (LinearLayout) findViewById(R.id.drives);

        RequestQueue queue = Volley.newRequestQueue(DoctorHome.this);

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewCampaign/viewFutureCampaigns";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONArray returnedDrives = (JSONArray) response;

                for(int i=0; i<returnedDrives.length(); i++){

                    LinearLayout linearLayout = new LinearLayout(DoctorHome.this);

                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.setMargins(0, dpTOpx(10), 0, 0);
                    linearLayout.setLayoutParams(params);

                    /////
                    TextView driveID = new TextView(DoctorHome.this);

                    LinearLayout.LayoutParams paramsDrive = new LinearLayout.LayoutParams(
                            dpTOpx(20),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsDrive.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    driveID.setLayoutParams(paramsDrive);

                    driveID.setMovementMethod(LinkMovementMethod.getInstance());

                    //////
                    TextView location = new TextView(DoctorHome.this);

                    LinearLayout.LayoutParams paramsLocation = new LinearLayout.LayoutParams(
                            dpTOpx(80),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsLocation.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    location.setLayoutParams(paramsLocation);

                    //////
                    TextView date = new TextView(DoctorHome.this);

                    LinearLayout.LayoutParams paramsDate = new LinearLayout.LayoutParams(
                            dpTOpx(80),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsDate.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    date.setLayoutParams(paramsDate);


                    //////
                    TextView start_time = new TextView(DoctorHome.this);

                    LinearLayout.LayoutParams paramsStart_time = new LinearLayout.LayoutParams(
                            dpTOpx(60),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsStart_time.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    start_time.setLayoutParams(paramsStart_time);

                    //////
                    TextView end_time = new TextView(DoctorHome.this);

                    LinearLayout.LayoutParams paramsEnd_time = new LinearLayout.LayoutParams(
                            dpTOpx(60),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsEnd_time.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    end_time.setLayoutParams(paramsEnd_time);
                    //
                    try {
                        JSONObject Donation = returnedDrives.getJSONObject(i);
                        driveID.setText(Donation.getString("campaign_id"));
                        location.setText(Donation.getString("location"));
                        date.setText(Donation.getString("date"));
                        start_time.setText(Donation.getString("start_time"));
                        end_time.setText(Donation.getString("end_time"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    // add the textview to the linearlayout
                    linearLayout.addView(driveID);
                    linearLayout.addView(location);
                    linearLayout.addView(date);
                    linearLayout.addView(start_time);
                    linearLayout.addView(end_time);

                    drives_layout.addView(linearLayout, 3+i);

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctorHome.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);

















        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.home);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;
                switch(menuItem.getItemId()){

                    case R.id.appointments:
                        intent = new Intent(getApplicationContext(), Appointments.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        intent = new Intent(getApplicationContext(), DoctorHome.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        intent = new Intent(getApplicationContext(), ViewProfileDoctor.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });
    }

//    @Override
//    public boolean onMenuItemClick(MenuItem item) {
//        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
//        switch (item.getItemId()) {
//
//            case R.id.edit_profile:
//                Intent myintent1 = new Intent(DoctorHome.this, EditProfileActivity.class);
//                startActivity(myintent1);
//                return false;
//
//            case R.id.log_out:
//                Intent myintent2 = new Intent(DoctorHome.this, MainActivity.class);
//                startActivity(myintent2);
//                return false;
//
//            default:
//                return false;
//        }
//    }

}