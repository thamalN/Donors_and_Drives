package com.example.donorsanddrives;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class editDonAcc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_don_acc);
        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);

        try {
            String s = sharedPreferences.getString("donorDetails", null);
            assert s != null;
            JSONObject jsonObject = new JSONObject(s);

            final TextView textView1 = findViewById(R.id.donorid);
            textView1.append(jsonObject.getString("user_id"));

            final EditText editText2 = findViewById(R.id.namet);
            editText2.setText(jsonObject.getString("name"));

            final EditText editText3 = findViewById(R.id.gendert);
            editText3.setText(jsonObject.getString("gender"));

            final EditText editText4 = findViewById(R.id.birthdayt);
            editText4.setText(jsonObject.getString("birthday"));

            final EditText editText5 = findViewById(R.id.aget);
            editText5.setText(jsonObject.getString("age"));

            final EditText editText6 = findViewById(R.id.streetnot);
            editText6.setText(jsonObject.getString("street_no"));

            final EditText editText7 = findViewById(R.id.streett);
            editText7.setText(jsonObject.getString("street"));

            final EditText editText8 = findViewById(R.id.cityt);
            editText8.setText(jsonObject.getString("city"));

            final EditText editText9 = findViewById(R.id.provincet);
            editText9.setText(jsonObject.getString("province"));

            final EditText editText10 = findViewById(R.id.emailt);
            editText10.setText(jsonObject.getString("email"));

            final EditText editText11 = findViewById(R.id.contactt);
            editText11.setText(jsonObject.getString("contact"));

            final String id = jsonObject.getString("user_id");
            Button button = findViewById(R.id.button);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    func(id, editText2, editText3, editText4, editText5, editText6, editText7, editText8, editText9, editText10, editText11);
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void func(final String id, EditText editText2, EditText editText3, EditText editText4, EditText editText5, EditText editText6,
                     EditText editText7, EditText editText8, EditText editText9, EditText editText10, EditText editText11) {

        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/editDonProfile";
        RequestQueue queue = Volley.newRequestQueue(this);

        final String name, gender, birthday, age, street_no, street, city, province, email, contact;

        name = editText2.getText().toString();
        gender = editText3.getText().toString();
        birthday = editText4.getText().toString();
        age = editText5.getText().toString();
        street_no = editText6.getText().toString();
        street = editText7.getText().toString();
        city = editText8.getText().toString();
        province = editText9.getText().toString();
        email = editText10.getText().toString();
        contact = editText11.getText().toString();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
                    String s = sharedPreferences.getString("donorDetails", null);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("donorDetails", jsonObject.toString());
                    editor.apply();

                    Toast.makeText(getApplicationContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), donorHome.class);
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
                params.put("user_id", id);
                params.put("name", name);
                params.put("gender", gender);
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