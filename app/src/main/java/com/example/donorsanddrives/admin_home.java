package com.example.donorsanddrives;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TableRow;
import android.widget.TextView;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

public class admin_home extends AppCompatActivity {

    static JSONArray jsonArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        usersShow();
        campsShow();

        Button button = findViewById(R.id.button4);
        Button button2 = findViewById(R.id.button11);
        Button button3 = findViewById(R.id.button3);
        CardView camp = findViewById(R.id.camp);
        CardView user = findViewById(R.id.user);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manageUsers();
            }
        });
        camp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                campsClick();
            }
        });
        user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usersClick();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Users();
            }
        });

    }

    public void manageUsers() {
        Intent intent = new Intent(this, manage_accs.class);
        startActivity(intent);
    }

    public void Users() {
        Intent intent = new Intent(this, searchUsers.class);
        startActivity(intent);
    }

    public void campsShow() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/adminDashboard/campaigns";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    TextView textView = findViewById(R.id.textView4);
                    textView.append(String.valueOf(length));

                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("campaigns", jsonArray.toString());
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void campsClick() {
        String name, location, start, end, date, address;
        JSONObject jsonObject;

        try {
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                name = "Campaign ID - " + jsonObject.getString("campaign_id");
                location = jsonObject.getString("location");
                start = jsonObject.getString("start_time");
                end = jsonObject.getString("end_time");
                date = jsonObject.getString("date");
                address = jsonObject.getString("street_no") + ", " + jsonObject.getString("street") + ", " +
                        jsonObject.getString("city") + ", " + jsonObject.getString("province");

                @SuppressLint("SimpleDateFormat") SimpleDateFormat datetime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.getDefault());

                Calendar beginTime = Calendar.getInstance();
                Calendar endTime = Calendar.getInstance();

                String BeginDateTime = date + " " + start;
                String EndDateTime = date + " " + end;

                beginTime.setTime(datetime.parse(BeginDateTime));
                endTime.setTime(datetime.parse(EndDateTime));

                long calID = 3;
                long startMillis = 0;
                long endMillis = 0;

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_CALENDAR) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CALENDAR}, 40);
                }

                startMillis = beginTime.getTimeInMillis();
                endMillis = endTime.getTimeInMillis();

                ContentResolver cr = getApplicationContext().getContentResolver();
                ContentValues values = new ContentValues();
                values.put(CalendarContract.Events.DTSTART, startMillis);
                values.put(CalendarContract.Events.DTEND, endMillis);
                values.put(CalendarContract.Events.TITLE, name);
                values.put(CalendarContract.Events.EVENT_LOCATION, location);
                values.put(CalendarContract.Events.DESCRIPTION, address);
                values.put(CalendarContract.Events.CALENDAR_ID, calID);
                values.put(CalendarContract.Events.EVENT_TIMEZONE, TimeZone.getDefault().toString());
                cr.insert(CalendarContract.Events.CONTENT_URI, values);
            }

            long time = Calendar.getInstance().getTimeInMillis();
            Uri.Builder builder = CalendarContract.CONTENT_URI.buildUpon();
            builder.appendPath("time");
            ContentUris.appendId(builder, time);
            Intent intent = new Intent(Intent.ACTION_VIEW)
                    .setData(builder.build());
            startActivity(intent);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void usersShow() {
        String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/adminDashboard/users";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    jsonArray = new JSONArray(response);
                    int length = jsonArray.length();
                    TextView textView = findViewById(R.id.textView3);
                    if(textView.getEditableText() != null)
                        textView.getEditableText().delete(13, textView.getEditableText().length());
                    textView.append(String.valueOf(length));
                    SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("users", jsonArray.toString());
                    editor.apply();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        queue.add(stringRequest);
    }

    public void usersClick() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedPref", MODE_PRIVATE);
        String users = sharedPreferences.getString("users", null);

        ArrayList<String> onlineUsers = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(users);
            JSONObject jsonObject;

            for (int i=0;i<jsonArray.length();i++) {
                jsonObject = (JSONObject) jsonArray.get(i);
                String user =  i+1 + ") " + jsonObject.getString("username");
                onlineUsers.add(user);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String[] online = onlineUsers.toArray(new String[0]);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Online Users");
        builder.setItems(online, null);
        builder.setCancelable(true);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void logOut() {
        SharedPreferences sharedPreferences = getSharedPreferences("sharedId", MODE_PRIVATE);
        final String id = sharedPreferences.getString("user_id", null);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm Logout");
        builder.setMessage("Are you sure you want to log out?");
        builder.setCancelable(true);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String url = "http://10.0.2.2:8080/DonorsAndDrives_war_exploded/authentication/logout";
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity2.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
                    @Override
                    public byte[] getBody() {
                        try {
                            return id == null ? null : id.getBytes("utf-8");
                        } catch (UnsupportedEncodingException uee) {
                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", id, "utf-8");
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
                queue.add(stringRequest);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    @Override
    public void onBackPressed() {
        logOut();
    }

    @Override
    public void onStart() {
        super.onStart();
        usersShow();
    }
}