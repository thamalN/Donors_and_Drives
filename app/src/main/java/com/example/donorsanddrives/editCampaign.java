package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class editCampaign extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_campaign);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String details = sharedPreferences.getString("campaignDetails", null);

        try {
            assert details != null;
            JSONObject jsonObject = new JSONObject(details);

            final TextView editText1 = findViewById(R.id.textView1);
            editText1.append(jsonObject.getString("campaign_id"));

            final EditText editText2 = findViewById(R.id.id);
            editText2.setText(jsonObject.getString("campaignManager_id"));

            final EditText editText3 = findViewById(R.id.date);
            editText3.setText(jsonObject.getString("date"));

            final EditText editText4 = findViewById(R.id.start);
            editText4.setText(jsonObject.getString("start_time"));

            final EditText editText5 = findViewById(R.id.end);
            editText5.setText(jsonObject.getString("end_time"));

            final EditText editText6 = findViewById(R.id.location);
            editText6.setText(jsonObject.getString("location"));

            final EditText editText7 = findViewById(R.id.lat);
            editText7.setText(jsonObject.getString("latitude"));

            final EditText editText8 = findViewById(R.id.lng);
            editText8.setText(jsonObject.getString("longitude"));

            final EditText editText9 = findViewById(R.id.street_no);
            editText9.setText(jsonObject.getString("street_no"));

            final EditText editText10 = findViewById(R.id.street);
            editText10.setText(jsonObject.getString("street"));

            final EditText editText11 = findViewById(R.id.city);
            editText11.setText(jsonObject.getString("city"));

            final EditText editText12 = findViewById(R.id.province);
            editText12.setText(jsonObject.getString("province"));

            final String id = jsonObject.getString("campaign_id");

            Button button = findViewById(R.id.button7);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    func(id, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11, editText12);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void func(final String ID, EditText editText2, EditText editText3, EditText editText4, EditText editText5, EditText editText6, EditText editText7,
                     EditText editText8, EditText editText9, EditText editText10, EditText editText11, EditText editText12) {

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/editCampaign";
        RequestQueue queue = Volley.newRequestQueue(this);

        final String managerId, date, start, end, location, lat, lng, street_no, street, city, province;

        managerId = editText2.getText().toString();
        date = editText3.getText().toString();
        start = editText4.getText().toString();
        end = editText5.getText().toString();
        location = editText6.getText().toString();
        lat = editText7.getText().toString();
        lng = editText8.getText().toString();
        street_no = editText9.getText().toString();
        street = editText10.getText().toString();
        city = editText11.getText().toString();
        province = editText12.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("campaignDetails", jsonObject.toString());
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), viewCampaign.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("campaign_id", ID);
                params.put("managerid", managerId);
                params.put("date", date);
                params.put("start", start);
                params.put("end", end);
                params.put("location", location);
                params.put("lat", lat);
                params.put("lng", lng);
                params.put("street_no", street_no);
                params.put("street", street);
                params.put("city", city);
                params.put("province", province);

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