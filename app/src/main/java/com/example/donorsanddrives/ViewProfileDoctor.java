package com.example.donorsanddrives;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ViewProfileDoctor extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_doctor);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        final String user_id = sharedPreferences.getString("user_id", null);

//        Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();

        Button editProf = (Button) findViewById(R.id.btn);

        TextView userID = (TextView) findViewById(R.id.user_id);
        final TextView username = (TextView) findViewById(R.id.usernamet);
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView address = (TextView) findViewById(R.id.addresst);
        final TextView hospital = (TextView) findViewById(R.id.hospitalt);
        final TextView email = (TextView) findViewById(R.id.emailt);
        final TextView contact = (TextView) findViewById(R.id.contactt);
        Button logoutButton = findViewById(R.id.logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logOut();
            }
        });

        userID.setText("ID - " + user_id);

        final RequestQueue queue = Volley.newRequestQueue(ViewProfileDoctor.this);

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewDoctorInfo/" + user_id;

        //request object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(Activity5.this, response.toString(), Toast.LENGTH_SHORT).show();
                JSONObject doctor = (JSONObject) response;
                try {
                    username.setText(response.getString("username"));
                    name.setText("Name - " + response.getString("name"));
                    address.setText(response.getString("street_no") + ", " + response.getString("street") + ", " + response.getString("city") + ", " + response.getString("province"));
                    hospital.setText(response.getString("hospital"));
                    email.setText(response.getString("email"));
                    contact.setText(response.getString("contact"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewProfileDoctor.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);


        editProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });












        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.profile);

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

    public void logOut() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        final String id = sharedPreferences.getString("user_id", null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/authentication/logout";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    public byte[] getBody() {
                        try {
                            return id == null ? null : id.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", id, "utf-8");
                            return null;
                        }
                    }

                    @Override
                    public Map<String, String> getHeaders() {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "text/plain");
                        return headers;
                    }
                };
                queue.add(stringRequest);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}