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

import com.android.volley.AuthFailureError;
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

public class editDocAcc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_doc_acc);

        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("details", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);

            final TextView editText1 = findViewById(R.id.textView15);
            editText1.append(jsonObject.getString("user_id"));

            final EditText editText2 = findViewById(R.id.name);
            editText2.setText(jsonObject.getString("name"));

            final EditText editText3 = findViewById(R.id.gender);
            editText3.setText(jsonObject.getString("gender"));

            final EditText editText4 = findViewById(R.id.street_no);
            editText4.setText(jsonObject.getString("street_no"));

            final EditText editText5 = findViewById(R.id.street);
            editText5.setText(jsonObject.getString("street"));

            final EditText editText6 = findViewById(R.id.city);
            editText6.setText(jsonObject.getString("city"));

            final EditText editText7 = findViewById(R.id.province);
            editText7.setText(jsonObject.getString("province"));

            final EditText editText8 = findViewById(R.id.contact);
            editText8.setText(jsonObject.getString("contact"));

            final EditText editText9 = findViewById(R.id.email);
            editText9.setText(jsonObject.getString("email"));

            final EditText editText10 = findViewById(R.id.hospital);
            editText10.setText(jsonObject.getString("hospital"));

            final String id = jsonObject.getString("user_id");
            Button button = findViewById(R.id.button7);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    func(id, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

        public void func(final String ID, EditText editText2, EditText editText3, EditText editText4, EditText editText5, EditText editText6,
                         EditText editText7, EditText editText8, EditText editText9, EditText editText10) {

            String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/editDoctor";
            RequestQueue queue = Volley.newRequestQueue(this);

            final String name, gender, street_no, street, city, province, contact, email, hospital;

            name = editText2.getText().toString();
            gender = editText3.getText().toString();
            street_no = editText4.getText().toString();
            street = editText5.getText().toString();
            city = editText6.getText().toString();
            province = editText7.getText().toString();
            contact = editText8.getText().toString();
            email = editText9.getText().toString();
            hospital = editText10.getText().toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonObject = new JSONObject(response);

                        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                        String s = sharedPreferences.getString("details", null);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("details", jsonObject.toString());
                        editor.apply();

                        Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), docMain.class);
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