package com.example.donorsanddrives;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class donorHome extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_home);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        String s = sharedPreferences.getString("user_id", null);

        TextView textView1 = findViewById(R.id.donorid);
        textView1.append(s);
        final int id = Integer.parseInt(s);

        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDonationHistory(id);
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewDonorLeaderboard();
            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewdBloodDrives();
            }
        });

        button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(donorHome.this, v);
                popup.setOnMenuItemClickListener(donorHome.this);
                popup.inflate(R.menu.popup_menu);
                popup.show();
            }
        });


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        bottomNavigationView.setSelectedItemId(R.id.home);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.map:
                        startActivity(new Intent(getApplicationContext()
                                , MapsActivity.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.home:
                        return true;

                    case R.id.profile:
                        viewProfile(id);
                        overridePendingTransition(0, 0);
                        return true;
                }
                return false;
            }
        });

       /* SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("details", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);
            TextView textView = findViewById(R.id.textView14);
            textView.append(jsonObject.getString("user_id"));
        } catch (JSONException e) {
            e.printStackTrace();
        }*/
    }
        @Override
        public boolean onMenuItemClick(MenuItem item) {
            Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
            switch (item.getItemId()) {

                case R.id.notifications:
                    return true;
                case R.id.about:
                    return true;

                case R.id.log_out:
                    Intent intent = new Intent(donorHome.this, MainActivity.class);
                    startActivity(intent);
                    return false;

                default:
                    return false;

        }
    }

        public void viewDonationHistory(int id) {
            RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
            String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewDonHistory/" + id;


            StringRequest stringrequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                            for (int i = 0; i < jsonArray.length(); i++) {
                                final JSONObject jsonObject = jsonArray.getJSONObject(i);

                                JSONObject jsonObject1 = new JSONObject(jsonObject.toString());
                                SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("donHistory", jsonObject1.toString());
                                editor.apply();


                            }
                            Intent intent = new Intent(getApplicationContext(), viewDonHistory.class);
                            startActivity(intent);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });
            queue.add(stringrequest);
            }


    public void viewDonorLeaderboard() {
        Intent intent = new Intent(this, viewDonLead.class);
        startActivity(intent);
    }

    public void viewdBloodDrives() {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);
    }

    public void viewProfile(int id) {
        RequestQueue queue1 = Volley.newRequestQueue(getApplicationContext());

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewDonorProfile/" + id;

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("donorDetails", response);
                editor.apply();
//                            final JSONObject jsonObject = new JSONObject(response);
//
//                            String user_id = jsonObject.getString("user_id");
//                            String name = jsonObject.getString("name");
//                            String gender = jsonObject.getString("gender");
//                            String blood_type = jsonObject.getString("blood_type");
//                            String birthday = jsonObject.getString("birthday");
//                            String age = jsonObject.getString("age");
//                            String street_no = jsonObject.getString("street_no");
//                            String street = jsonObject.getString("street");
//                            String city = jsonObject.getString("city");
//                            String province = jsonObject.getString("province");
//                            String contact = jsonObject.getString("contact");
//                            String email = jsonObject.getString("email");
//
//                            System.out.println(user_id);
                    System.out.println(response);

                Intent intent = new Intent(getApplicationContext(), profile.class);
                //final int id = intent.getIntExtra("id", 0);
                startActivity(intent);

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        queue1.add(jsonObjectRequest);


    }


}
