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

public class createDocAcc extends AppCompatActivity {

    String name, gender, street_no, street, city, province, contact, email, hospital;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_doc_acc);

        Button button = findViewById(R.id.button7);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });
    }

    public void func() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/addDoctor";
        RequestQueue queue = Volley.newRequestQueue(this);

        TextView textView1 = findViewById(R.id.name);
        TextView textView2 = findViewById(R.id.gender);
        TextView textView3 = findViewById(R.id.street_no);
        TextView textView4 = findViewById(R.id.street);
        TextView textView5 = findViewById(R.id.city);
        TextView textView6 = findViewById(R.id.province);
        TextView textView7 = findViewById(R.id.contact);
        TextView textView8 = findViewById(R.id.email);
        TextView textView9 = findViewById(R.id.hospital);

        name = textView1.getText().toString();
        gender = textView2.getText().toString();
        street_no = textView3.getText().toString();
        street = textView4.getText().toString();
        city = textView5.getText().toString();
        province = textView6.getText().toString();
        contact = textView7.getText().toString();
        email = textView8.getText().toString();
        hospital = textView9.getText().toString();

        if (!validate(new TextView[]{textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9})) {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        } else {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Intent intent = new Intent(getApplicationContext(), docUser.class);
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
                    params.put("gender", gender);
                    params.put("street_no", street_no);
                    params.put("street", street);
                    params.put("city", city);
                    params.put("province", province);
                    params.put("contact", contact);
                    params.put("email", email);
                    params.put("hospital", hospital);

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