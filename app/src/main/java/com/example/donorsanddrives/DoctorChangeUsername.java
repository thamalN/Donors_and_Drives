package com.example.donorsanddrives;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

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

public class DoctorChangeUsername extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_change_username);

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));

        Button change_username = (Button) findViewById(R.id.change_username);

        TextView userID = (TextView) findViewById(R.id.user_id);
        final TextView name = (TextView) findViewById(R.id.name);

        final TextView new_username = (TextView) findViewById(R.id.username);
        final TextView password = (TextView) findViewById(R.id.password);

        userID.setText("ID - " + user_id);

        final RequestQueue queue = Volley.newRequestQueue(DoctorChangeUsername.this);

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewDoctorInfo/" + user_id;

        //request object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(Activity5.this, response.toString(), Toast.LENGTH_SHORT).show();
                JSONObject doctor = (JSONObject) response;
                try {

                    name.setText("Name - " +response.getString("name"));
                    new_username.setText(response.getString("username"));


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DoctorChangeUsername.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);


        change_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/users/changeUsername";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject data = null;
                        try {
                            data = new JSONObject(response);
                            Toast.makeText(DoctorChangeUsername.this, data.getString("message"), Toast.LENGTH_SHORT).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        Intent intent = new Intent(getApplicationContext(), EditProfileActivity.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(DoctorChangeUsername.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", user_id);
                        params.put("username", new_username.getText().toString());
                        params.put("password", password.getText().toString());
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
                queue.add(request);

                Intent intent = new Intent(getApplicationContext(), ViewProfileDoctor.class);
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
}