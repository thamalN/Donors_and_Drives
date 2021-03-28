package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class DoctorMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;

    double lat;
    double lng;
    String drive_snippet;
    String user_id;
    String drive_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity_maps);


        final Intent recIntent = getIntent();
        user_id = String.valueOf(recIntent.getStringExtra("user_id"));

        try {

            JSONObject drive = new JSONObject(recIntent.getStringExtra("currentDrive"));
            lat = drive.getDouble("latitude");
            lng = drive.getDouble("longitude");
            drive_id = drive.getString("campaign_id");

//            Toast.makeText(this, lat+ ", " + lng, Toast.LENGTH_SHORT).show();

            drive_snippet = drive.getString("location");

        } catch (JSONException e) {
            e.printStackTrace();
        }



//        Toast.makeText(this, "The current drive is " + recIntent.getStringExtra("currentDrive"), Toast.LENGTH_SHORT).show();

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.appointments);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                Intent intent;
                switch(menuItem.getItemId()){

                    case R.id.appointments:
                        intent = new Intent(getApplicationContext(), Appointments.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.home:
                        intent = new Intent(getApplicationContext(), DoctorHome.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile:
                        intent = new Intent(getApplicationContext(), ViewProfileDoctor.class);
                        intent.putExtra("user_id", user_id);
                        startActivity(intent);
                        overridePendingTransition(0,0);
                        return true;

                }
                return false;
            }
        });

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Assign variable
        gMap = googleMap;

//        gMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                //creating marker
//                MarkerOptions markerOptions = new MarkerOptions();
//                //set marker position
//                markerOptions.position(latLng);
//                //set latitude and longitude on marker
//                markerOptions.title(latLng.latitude+ " : "+latLng.longitude);
//                //clear the previously clicked position
//                gMap.clear();
//                //zoom the marker
//                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,10));
//                //add marker on the map
//                gMap.addMarker(markerOptions);
//            }
//        });


        LatLng Drive = new LatLng(lat, lng);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(Drive)
                .title("Blood drive at")
                .snippet(drive_snippet);
        gMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Drive, 16);
        gMap.animateCamera(cameraUpdate);


        gMap.setOnMarkerClickListener(new OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
//                String venueID = mMarkerMap.get(marker.getId());
//                String venueName = marker.getTitle();
                Intent intent = new Intent(DoctorMapsActivity.this, Campaign.class);
                intent.putExtra("user_id", user_id);
                intent.putExtra("drive_id", drive_id);
//                Toast.makeText(DoctorMapsActivity.this, drive_id, Toast.LENGTH_SHORT).show();
                startActivity(intent);

                return false;
            }
        });


    }



}