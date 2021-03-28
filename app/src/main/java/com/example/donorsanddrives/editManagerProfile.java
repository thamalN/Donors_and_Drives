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

public class editManagerProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_manager_profile);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("campaignManager", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);

            final TextView editText1 = findViewById(R.id.textView15);
            editText1.append(jsonObject.getString("campaignManager_id"));

            final EditText editText2 = findViewById(R.id.name);
            editText2.setText(jsonObject.getString("name"));

            final EditText editText3 = findViewById(R.id.contact);
            editText3.setText(jsonObject.getString("contact"));

            final EditText editText4 = findViewById(R.id.email);
            editText4.setText(jsonObject.getString("email"));

            final String id = jsonObject.getString("campaignManager_id");

            Button button = findViewById(R.id.button7);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    func(id, editText2, editText3, editText4);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void func(final String ID, EditText editText2, EditText editText3, EditText editText4) {

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/editManager";
        RequestQueue queue = Volley.newRequestQueue(this);

        final String name, contact, email;

        name = editText2.getText().toString();
        contact = editText3.getText().toString();
        email = editText4.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), managerProfile.class);
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
                params.put("user_id", ID);
                params.put("name", name);
                params.put("contact", contact);
                params.put("email", email);

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