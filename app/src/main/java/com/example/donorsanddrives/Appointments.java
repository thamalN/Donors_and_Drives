package com.example.donorsanddrives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

public class Appointments extends AppCompatActivity {

    ImageButton search;
    EditText dataInput;
    TextView textID;

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
        setContentView(R.layout.activity_appointments);

        textID = (TextView) findViewById(R.id.user_id);
        search = findViewById(R.id.search);
        dataInput = findViewById(R.id.dataInput);

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));

        final LinearLayout drives_layout = (LinearLayout) findViewById(R.id.drives);

        RequestQueue queue = Volley.newRequestQueue(Appointments.this);

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RequestQueue queue = Volley.newRequestQueue(Appointments.this);
                final String drive_id = dataInput.getText().toString();
                String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewCampaign/viewCampaignByID/" + drive_id;


                //request object
                JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        //Toast.makeText(Activity5.this, response.toString(), Toast.LENGTH_SHORT).show();
                        JSONObject returnedDrive = (JSONObject) response;
                        Intent intent = new Intent(getApplicationContext(), DoctorMapsActivity.class);
                        intent.putExtra("user_id", user_id);

                        intent.putExtra("currentDrive", String.valueOf(returnedDrive));
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Appointments.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

                // Add the request to the RequestQueue.
                queue.add(request);

            }
        });

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/doctorAttendsBloodDrive/doctorAppointments/";
        url = url + user_id;

//        Toast.makeText(this, url, Toast.LENGTH_SHORT).show();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                JSONArray returnedDrives = (JSONArray) response;

                for(int i=0; i<returnedDrives.length(); i++){

                    LinearLayout linearLayout = new LinearLayout(Appointments.this);

                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    params.setMargins(0, dpTOpx(10), 0, 0);
                    linearLayout.setLayoutParams(params);

                    /////
                    TextView driveID = new TextView(Appointments.this);

                    LinearLayout.LayoutParams paramsDrive = new LinearLayout.LayoutParams(
                            dpTOpx(20),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsDrive.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    driveID.setLayoutParams(paramsDrive);

                    driveID.setMovementMethod(LinkMovementMethod.getInstance());

                    //////
                    TextView location = new TextView(Appointments.this);

                    LinearLayout.LayoutParams paramsLocation = new LinearLayout.LayoutParams(
                            dpTOpx(80),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsLocation.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    location.setLayoutParams(paramsLocation);

                    //////
                    TextView date = new TextView(Appointments.this);

                    LinearLayout.LayoutParams paramsDate = new LinearLayout.LayoutParams(
                            dpTOpx(80),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsDate.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    date.setLayoutParams(paramsDate);


                    //////
                    TextView start_time = new TextView(Appointments.this);

                    LinearLayout.LayoutParams paramsStart_time = new LinearLayout.LayoutParams(
                            dpTOpx(60),
                            LinearLayout.LayoutParams.WRAP_CONTENT
                    );

                    paramsStart_time.setMargins(0, dpTOpx(5), dpTOpx(16), 0);
                    start_time.setLayoutParams(paramsStart_time);

                    //////
                    TextView end_time = new TextView(Appointments.this);

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

                    drives_layout.addView(linearLayout, 2+i);

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Appointments.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);
        
        
        
        
        
        

        
        
        
        
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.appointments);

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


}