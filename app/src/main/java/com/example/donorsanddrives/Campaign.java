package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Campaign extends AppCompatActivity {



    public void toActivity3(View v) {
        startActivity(new Intent(this, DoctorHome.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.campaign);

        Button btn = (Button) findViewById(R.id.btn);

        TextView campaign_id = (TextView) findViewById(R.id.campaign_id);
        final TextView cm = (TextView) findViewById(R.id.cmt);
        final TextView date = (TextView) findViewById(R.id.datet);
        final TextView start_time = (TextView) findViewById(R.id.start_timet);
        final TextView end_time = (TextView) findViewById(R.id.end_timet);
        final TextView location = (TextView) findViewById(R.id.locationt);
        final TextView address = (TextView) findViewById(R.id.addresst);
        final CheckBox chkAttendance = (CheckBox) findViewById(R.id.attending);

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));

        final String drive_id = recIntent.getStringExtra("drive_id");
        campaign_id.setText("Campaign ID - " + drive_id);

        final RequestQueue queue = Volley.newRequestQueue(Campaign.this);

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewCampaign/viewCampaignByID/" + drive_id;


        //request object
        JsonObjectRequest requestGetData = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(Activity5.this, response.toString(), Toast.LENGTH_SHORT).show();
                JSONObject returnedDrive = (JSONObject) response;
                try {
                    cm.setText(returnedDrive.getString("campaignManager_id"));
                    date.setText(returnedDrive.getString("date"));
                    start_time.setText(returnedDrive.getString("start_time"));
                    end_time.setText(returnedDrive.getString("end_time"));
                    location.setText(returnedDrive.getString("location"));
                    address.setText(returnedDrive.getString("street_no") + ", " + returnedDrive.getString("street") + ", " + returnedDrive.getString("city") + ", " + returnedDrive.getString("province"));

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(getApplicationContext(), DoctorMapsActivity.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("drive_id", drive_id);
//                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Campaign.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(requestGetData);


        // loading attendance
        url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/doctorAttendsBloodDrive/ifAppointmentExists";


        StringRequest requestGetAttendance = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject attendance = new JSONObject(response);
                    if (attendance.getBoolean("state")) {
                        chkAttendance.setChecked(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Campaign.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("user_id", user_id);
                params.put("campaign_id", drive_id);

                return params;
            }
            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/x-www-form-urlencoded");
                return headers;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(requestGetAttendance);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(chkAttendance.isChecked()){
                    String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/doctorAttendsBloodDrive";

                    StringRequest requestGetAttendance = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Campaign.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", user_id);
                            params.put("campaign_id", drive_id);

                            return params;
                        }
                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            return headers;
                        }
                    };

                    // Add the request to the RequestQueue.
                    queue.add(requestGetAttendance);

                }else{

                    String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/doctorAttendsBloodDrive/cancelling";

                    StringRequest requestGetAttendance = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Campaign.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){

                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("user_id", user_id);
                            params.put("campaign_id", drive_id);

                            return params;
                        }
                        @Override
                        public Map<String, String> getHeaders() {
                            HashMap<String, String> headers = new HashMap<>();
                            headers.put("Content-Type", "application/x-www-form-urlencoded");
                            return headers;
                        }
                    };

                    // Add the request to the RequestQueue.
                    queue.add(requestGetAttendance);



                }
            }
        });















        //Initialize and assign variable
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