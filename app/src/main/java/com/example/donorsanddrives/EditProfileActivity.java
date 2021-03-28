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

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        final Intent recIntent = getIntent();
        final String user_id = String.valueOf(recIntent.getStringExtra("user_id"));
        final String[] Name = new String[1];

//        Toast.makeText(this, user_id, Toast.LENGTH_SHORT).show();

        Button save = (Button) findViewById(R.id.save);
        Button change_username = (Button) findViewById(R.id.change_username);
        Button change_password = (Button) findViewById(R.id.change_password);

        TextView userID = (TextView) findViewById(R.id.user_id);
        final TextView name = (TextView) findViewById(R.id.name);

        final TextView street_no = (TextView) findViewById(R.id.street_not);
        final TextView street = (TextView) findViewById(R.id.streett);
        final TextView city = (TextView) findViewById(R.id.cityt);
        final TextView province = (TextView) findViewById(R.id.provincet);
        final TextView hospital = (TextView) findViewById(R.id.hospitalt);
        final TextView email = (TextView) findViewById(R.id.emailt);
        final TextView contact = (TextView) findViewById(R.id.contactt);

        userID.setText("ID - " + user_id);

        final RequestQueue queue = Volley.newRequestQueue(EditProfileActivity.this);

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewDoctorInfo/" + user_id;

        //request object
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //Toast.makeText(Activity5.this, response.toString(), Toast.LENGTH_SHORT).show();
                JSONObject doctor = (JSONObject) response;
                try {

                    Name[0] = response.getString("name");
                    name.setText("Name - " + Name[0]);
                    street_no.setText(response.getString("street_no"));
                    street.setText(response.getString("street"));
                    city.setText(response.getString("city"));
                    province.setText(response.getString("province"));
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
                Toast.makeText(EditProfileActivity.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
            }
        });

        // Add the request to the RequestQueue.
        queue.add(request);


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/editDoctorProfInfo/info";

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Intent intent = new Intent(getApplicationContext(), ViewProfileDoctor.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(EditProfileActivity.this, "Error - " + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){

                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id", user_id);
                        params.put("email", email.getText().toString());
                        params.put("contact", contact.getText().toString());
                        params.put("street_no", street_no.getText().toString());
                        params.put("street", street.getText().toString());
                        params.put("city", city.getText().toString());
                        params.put("province", province.getText().toString());
                        params.put("hospital", hospital.getText().toString());

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
        });


        change_username.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DoctorChangeUsername.class);
                intent.putExtra("user_id", user_id);
                startActivity(intent);
            }
        });

        change_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), DoctorChangePassword.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("name", Name[0]);
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