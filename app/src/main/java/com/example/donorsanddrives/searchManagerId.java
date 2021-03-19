package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.*;
import com.android.volley.toolbox.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class searchManagerId extends AppCompatActivity {
    TableLayout tableLayout;
    String record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_manager_id);

        ImageButton button = findViewById(R.id.imageButton);

        tableLayout = findViewById(R.id.table);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);
                loggedIn();
            }
        });
    }

    public void loggedIn() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/searchManager";
        RequestQueue queue = Volley.newRequestQueue(this);

        EditText id;
        id = findViewById(R.id.editTextTextPersonName6);

        final String Id;
        Id = id.getText().toString();

        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);

                    if(jsonArray.length() == 0) {
                        Toast.makeText(searchManagerId.this, "No records found!", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            final JSONObject jsonObject = jsonArray.getJSONObject(i);

                            String user_id = jsonObject.getString("user_id");
                            String name = jsonObject.getString("name");
                            String regDate = jsonObject.getString("regDate");
                            String email = jsonObject.getString("email");
                            String contact = jsonObject.getString("contact");

                            record = i + 1 + ") " +
                                    "User Id - " + user_id + "\n" +
                                    "     Name - " + name + "\n" +
                                    "     Registered Date - " + regDate + "\n" +
                                    "     Email - " + email + "\n" +
                                    "     Contact - " + contact + "\n\n";

                            final TableRow tableRow = new TableRow(getApplicationContext());
                            final TextView textView = new TextView(getApplicationContext());

                            textView.setText(record);
                            tableRow.addView(textView);
                            tableLayout.addView(tableRow);

                            tableRow.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    try {
                                        JSONObject jsonObject1 = new JSONObject(jsonObject.toString());

                                        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                                        SharedPreferences.Editor editor = sharedPreferences.edit();
                                        editor.putString("details", jsonObject1.toString());
                                        editor.apply();

                                        Intent intent = new Intent(getApplicationContext(), managerMain.class);
                                        startActivity(intent);

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
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
            public byte[] getBody() {
                try {
                    return Id == null ? null : Id.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", Id, "utf-8");
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

        queue.add(jsonObjectRequest);

    }
}