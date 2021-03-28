package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { loggedIn(); }
        });
    }

    public void loggedIn() {

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/authentication/login";
        RequestQueue queue = Volley.newRequestQueue(this);

        EditText user, pass;
        user = findViewById(R.id.username);
        pass = findViewById(R.id.password);

        username = user.getText().toString();
        password = pass.getText().toString();

        if(!validate(new EditText[] {user, pass})) {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject object = new JSONObject(response);
                        int flag = object.getInt("user_flag");
                        int id = object.getInt("user_id");
                        int online = object.getInt("online");

                        SharedPreferences sharedPreferences1 = getSharedPreferences("sharedId", MODE_PRIVATE);
                        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
                        editor1.putString("user_id", String.valueOf(id));
                        editor1.apply();

                        if(online == 1) {
                            Toast.makeText(getApplicationContext(), "You are already logged in from another device", Toast.LENGTH_SHORT).show();
                        } else if (flag == 1) {
                            Intent intent = new Intent(getApplicationContext(), admin_home.class);
                            startActivity(intent);
                        } else if (flag == 2) {
                          Intent intent = new Intent(getApplicationContext(), DoctorHome.class);
                          startActivity(intent);
                        } else if (flag == 3) {
                            Intent intent = new Intent(getApplicationContext(), donorHome.class);
                            startActivity(intent);
                        } else if (flag == 4) {
                            Intent intent = new Intent(getApplicationContext(), cmanagerHome.class);
                            startActivity(intent);
                        }else {
                           Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
                        }
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
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username", username);
                    params.put("password", password);
                    return params;
                }

                @Override
                public Map<String, String> getHeaders() {
                    HashMap<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "application/x-www-form-urlencoded");
                    return headers;
                }
            };
            queue.add(stringRequest);
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        EditText user = findViewById(R.id.username);
        EditText pass = findViewById(R.id.password);
        user.setText(null);
        pass.setText(null);
    }

    private boolean validate(EditText[] editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() == 0)
                return false;
        }
        return true;
    }
}