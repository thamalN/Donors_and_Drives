package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class ViewProfileDoctor extends AppCompatActivity {

    public void toMainActivity(View v) {
        startActivity(new Intent(this, MainActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_profile_doctor);

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));

//        Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();

        Button editProf = (Button) findViewById(R.id.btn);

        TextView userID = (TextView) findViewById(R.id.user_id);
        final TextView username = (TextView) findViewById(R.id.usernamet);
        final TextView name = (TextView) findViewById(R.id.name);
        final TextView address = (TextView) findViewById(R.id.addresst);
        final TextView hospital = (TextView) findViewById(R.id.hospitalt);
        final TextView email = (TextView) findViewById(R.id.emailt);
        final TextView contact = (TextView) findViewById(R.id.contactt);

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
}