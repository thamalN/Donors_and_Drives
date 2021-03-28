package com.example.donorsanddrives;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class cmanagerHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmanager_home);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        String id = sharedPreferences.getString("user_id", "0");

        TextView textView = findViewById(R.id.textView14);
        textView.append(id);

        Button campaigns = findViewById(R.id.button3);
        campaigns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), searchCampaigns.class);
                startActivity(intent);
            }
        });

        Button profile = findViewById(R.id.button11);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), managerProfile.class);
                startActivity(intent);
            }
        });

        Button logout = findViewById(R.id.button16);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
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

    @Override
    public void onBackPressed() {
        logOut();
    }
}