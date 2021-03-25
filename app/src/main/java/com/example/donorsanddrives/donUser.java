package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class donUser extends AppCompatActivity {
    String username, password, password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_user_pass);

        Button button = findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func();
            }
        });
    }

    public void func() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/donorUser";
        RequestQueue queue = Volley.newRequestQueue(this);

        EditText editText1, editText2, editText3;

        editText1 = findViewById(R.id.username);
        editText2 = findViewById(R.id.password);
        editText3 = findViewById(R.id.password2);

        username = editText1.getText().toString();
        password = editText2.getText().toString();
        password2 = editText3.getText().toString();

        if (!validate(new EditText[] {editText1, editText2, editText3})) {
            Toast.makeText(this, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
        } else if (!password.equals(password2)) {
            Toast.makeText(this, "Passwords don't match", Toast.LENGTH_SHORT).show();
        } else {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String s) {
                    Intent intent = new Intent(getApplicationContext(), donQuiz.class);
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
                    params.put("username", username);
                    params.put("password", password);
                    params.put("password2", password2);
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

    private boolean validate(EditText[] editTexts) {
        for (EditText editText : editTexts) {
            if (editText.getText().toString().length() == 0)
                return false;
        }
        return true;
    }

}