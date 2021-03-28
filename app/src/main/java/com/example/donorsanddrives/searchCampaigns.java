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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class searchCampaigns extends AppCompatActivity {

    TableLayout tableLayout;
    String record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_campaigns);

        loadTable();

        ImageButton button = findViewById(R.id.imageButton);
        tableLayout = findViewById(R.id.table);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tableLayout.removeAllViews();
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

                EditText phrase;
                phrase = findViewById(R.id.editTextTextPersonName6);

                final String Phrase;
                Phrase = phrase.getText().toString();

                String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/filterCampaigns";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        TableData(response);
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
                        params.put("phrase", Phrase);
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
        });
    }

    public void loadTable() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/viewCampaign";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                TableData(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);

    }

    public void TableData(String response) {
        try {
            JSONArray jsonArray = new JSONArray(response);

            if (jsonArray.length() == 0) {
                Toast.makeText(searchCampaigns.this, "No records found!", Toast.LENGTH_SHORT).show();
            } else {
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject jsonObject = jsonArray.getJSONObject(i);

                    String campaignId = jsonObject.getString("campaign_id");
                    String campaignManagerId = jsonObject.getString("campaignManager_id");
                    String date = jsonObject.getString("date");
                    String startTime = jsonObject.getString("start_time");
                    String endTime = jsonObject.getString("end_time");
                    String latitude = jsonObject.getString("latitude");
                    String longitude = jsonObject.getString("longitude");
                    String location = jsonObject.getString("location");
                    String street_no = jsonObject.getString("street_no");
                    String street = jsonObject.getString("street");
                    String city = jsonObject.getString("city");
                    String province = jsonObject.getString("province");

                    record = i + 1 + ") " +
                            "Campaign Id - " + campaignId + "\n" +
                            "     Campaign Manager Id - " + campaignManagerId + "\n" +
                            "     Date - " + date + "\n" +
                            "     Start Time - " + startTime + "\n" +
                            "     End Time - " + endTime + "\n" +
                            "     Latitude - " + latitude + "\n" +
                            "     Longitude - " + longitude + "\n" +
                            "     Location - " + location + "\n" +
                            "     Address - " + street_no + ", " + street + ", " + city + ", " + province + "\n\n";

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
                                editor.putString("campaignDetails", jsonObject1.toString());
                                editor.apply();

                                Intent intent = new Intent(getApplicationContext(), viewCampaign.class);
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

}


