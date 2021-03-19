package com.example.donorsanddrives;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class createManagerAcc extends AppCompatActivity {

    String name, contact, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_manager_acc);

        Button button = findViewById(R.id.button7);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });
    }

    public void func() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/addManager";
        RequestQueue queue = Volley.newRequestQueue(this);

        TextView textView1 = findViewById(R.id.name);
        TextView textView2 = findViewById(R.id.email);
        TextView textView3 = findViewById(R.id.contact);

        name = textView1.getText().toString();
        email = textView2.getText().toString();
        contact = textView3.getText().toString();

        if (!validate(new TextView[]{textView1, textView2, textView3})) {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Intent intent = new Intent(getApplicationContext(), managerUser.class);
                    startActivity(intent);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    volleyError.printStackTrace();
                }
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("name", name);
                    params.put("email", email);
                    params.put("contact", contact);

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
    private boolean validate(TextView[] textViews) {
        for (TextView textView : textViews) {
            if (textView.getText().toString().length() == 0)
                return false;
        }
        return true;
    }

}