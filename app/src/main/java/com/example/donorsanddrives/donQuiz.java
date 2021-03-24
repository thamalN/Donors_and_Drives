package com.example.donorsanddrives;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class donQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_quiz);
        System.out.println("hello");

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });
    }

    public void func() {
        final String y = reasons();
        System.out.println(y);
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/donQuiz";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringrequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String s) {
                Intent intent = new Intent(getApplicationContext(), userAgree.class);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                volleyError.printStackTrace();
            }
        }) {
            @Override
            public byte[] getBody() {
                try {
                    return y.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", y, "utf-8");
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
        queue.add(stringrequest);
    }

    public String reasons() {

        RadioGroup radioGroup1 = (RadioGroup) findViewById(R.id.answers1);
        RadioGroup radioGroup2 = (RadioGroup) findViewById(R.id.answers2);
        RadioGroup radioGroup3 = (RadioGroup) findViewById(R.id.answers3);
        RadioGroup radioGroup4 = (RadioGroup) findViewById(R.id.answers4);
        RadioGroup radioGroup5 = (RadioGroup) findViewById(R.id.answers5);
        RadioGroup radioGroup6 = (RadioGroup) findViewById(R.id.answers6);
        RadioGroup radioGroup7 = (RadioGroup) findViewById(R.id.answers7);
        RadioGroup radioGroup8 = (RadioGroup) findViewById(R.id.answers8);
        RadioGroup radioGroup9 = (RadioGroup) findViewById(R.id.answers9);

        RadioGroup[] radioGroups = new RadioGroup[]{radioGroup1, radioGroup2, radioGroup3, radioGroup4, radioGroup5, radioGroup6, radioGroup7, radioGroup8, radioGroup9};


        String[] reason = {"not within the age group", "tattoo or piercing", "travelled overseas", "recent dental procedure", "pregnant or recent delivery", "Under medication", "Underweight", "Risk Behaviours", "Diabetes or Hypertension"};
        String x = "";

        String[] array2 = {"No", "Yes", "Yes", "Yes", "Yes", "Yes", "No", "No", "Yes",};
        for (int i = 0; i < radioGroups.length; i++) {
            RadioButton radioButton = (RadioButton) findViewById(radioGroups[i].getCheckedRadioButtonId());

            if (radioButton.getText().equals(array2[i])) {
                x += reason[i] + ", ";
                System.out.println(i + "teal");
            }
        }
            return x;
        }



}