package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class createDonAcc extends AppCompatActivity {
    String name, gender, blood_type, birthday, age, street_no, street, city, province, email, contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_don_acc);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });
    }

    public void func() {

            String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/addDonor";
            RequestQueue queue = Volley.newRequestQueue(this);

            TextView textView1 = findViewById(R.id.namet);
            TextView textView2 = findViewById(R.id.gendert);
            TextView textView3 = findViewById(R.id.bloodtypet);
            TextView textView4 = findViewById(R.id.birthdayt);
            TextView textView5 = findViewById(R.id.aget);
            TextView textView6 = findViewById(R.id.street_not);
            TextView textView7 = findViewById(R.id.streett);
            TextView textView8 = findViewById(R.id.cityt);
            TextView textView9 = findViewById(R.id.provincet);
            TextView textView10 = findViewById(R.id.emailt);
            TextView textView11 = findViewById(R.id.contactt);

            name = textView1.getText().toString();
            gender = textView2.getText().toString();
            blood_type = textView3.getText().toString();
            birthday = textView4.getText().toString();
            age = textView5.getText().toString();
            street_no = textView6.getText().toString();
            street = textView7.getText().toString();
            city = textView8.getText().toString();
            province = textView9.getText().toString();
            email = textView10.getText().toString();
            contact = textView11.getText().toString();

            if (!validate(new TextView[]{textView1, textView2, textView3, textView4, textView5, textView6, textView7, textView8, textView9, textView10, textView11})) {
                Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
            } else {
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        Intent intent = new Intent(getApplicationContext(), donUser.class);
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
                        params.put("blood_type", blood_type);
                        params.put("birthday", birthday);
                        params.put("age", age);
                        params.put("street_no", street_no);
                        params.put("street", street);
                        params.put("city", city);
                        params.put("province", province);
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