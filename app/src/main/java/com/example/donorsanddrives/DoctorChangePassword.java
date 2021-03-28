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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DoctorChangePassword extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_change_password);

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));

        Button change_password = (Button) findViewById(R.id.change_password);

        TextView userID = (TextView) findViewById(R.id.user_id);
        final TextView name = (TextView) findViewById(R.id.name);

        final TextView new_password = (TextView) findViewById(R.id.new_passwordt);
        final TextView conf_password = (TextView) findViewById(R.id.conf_passwordt);
        final TextView cur_password = (TextView) findViewById(R.id.cur_passwordt);

        userID.setText("ID - " + user_id);
        name.setText("Name - " + recIntent.getStringExtra("name"));

        final RequestQueue queue = Volley.newRequestQueue(DoctorChangePassword.this);

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(new_password.getText().toString().equals(conf_password.getText().toString())){
                    String regx = "^(?=.*[0-9])(?=.*[!@#$%^&*])[a-zA-Z0-9!@#$%^&*]{6,16}$";
                    Pattern pattern = Pattern.compile(regx);
                    Matcher matcher = pattern.matcher(new_password.getText().toString());
                    
                    if(!matcher.matches()){
                        Toast.makeText(DoctorChangePassword.this, "The password should contain atleast one number and one special character while the length could vary from 6 to 16 characters", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/users/changePassword";

                        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                JSONObject data = null;
                                try {
                                    data = new JSONObject(response);
                                    Toast.makeText(DoctorChangePassword.this, data.getString("message"), Toast.LENGTH_SHORT).show();

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
                                Toast.makeText(DoctorChangePassword.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }){

                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<>();
                                params.put("user_id", user_id);
                                params.put("new_password", new_password.getText().toString());
                                params.put("current_password", cur_password.getText().toString());
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

                    }

                }else {
                    Toast.makeText(DoctorChangePassword.this, "New password not confirmed!", Toast.LENGTH_SHORT).show();
                }




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