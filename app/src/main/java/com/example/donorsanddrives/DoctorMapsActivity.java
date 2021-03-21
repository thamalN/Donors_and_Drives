package com.example.donorsanddrives;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DoctorMapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap gMap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doctor_activity_maps);

        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);

        //Initialize and assign variable
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        //set home selected
        bottomNavigationView.setSelectedItemId(R.id.map);

        //perform ItemSelectedListener
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch(menuItem.getItemId()){
                    case R.id.map:
                        return true;

                    case R.id.home:
                        startActivity(new Intent(getApplicationContext()
                                , DoctorHome.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.donors:
                        startActivity(new Intent(getApplicationContext()
                                , FindDonor.class));
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

        LatLng Drive = new LatLng(6.927079, 79.861244);
        MarkerOptions markerOptions = new MarkerOptions()
                .position(Drive)
                .title("Rajamaha Viharaya")
                .snippet("Blood Drive");
        gMap.addMarker(markerOptions);
        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(Drive, 16);
        gMap.animateCamera(cameraUpdate);
    }



}