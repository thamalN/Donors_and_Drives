package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class managerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        final String id = sharedPreferences.getString("user_id", "0");

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewCampaignManager";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    TextView textView1 = findViewById(R.id.textView14);
                    textView1.append(jsonObject.getString("campaignManager_id"));

                    TextView textView2 = findViewById(R.id.textView17);
                    textView2.setText(jsonObject.getString("name"));

                    TextView textView3 = findViewById(R.id.textView24);
                    textView3.setText(jsonObject.getString("regDate"));

                    TextView textView4 = findViewById(R.id.textView20);
                    textView4.setText(jsonObject.getString("email"));

                    TextView textView5 = findViewById(R.id.textView21);
                    textView5.setText(jsonObject.getString("contact"));

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("campaignManager", jsonObject.toString());
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

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

        requestQueue.add(stringRequest);

        Button button = findViewById(R.id.button7);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), editManagerProfile.class);
                startActivity(intent);
            }
        });
    }
}