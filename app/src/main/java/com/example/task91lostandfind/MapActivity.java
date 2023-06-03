package com.example.task91lostandfind;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_map);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_fragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        List<Item> ltlng = DatabaseHelper.getInstance(this).getAllItems();
        for (int i = 0; i < ltlng.size(); i++){
            // Add a marker and move the camera to a specific location
            String coordinates = ltlng.get(i).getLocation();
            String[] arraylatLong = coordinates.split(" ");
            Double lat = Double.parseDouble(arraylatLong[0]);
            Double lon = Double.parseDouble(arraylatLong[1]);
            LatLng location = new LatLng(lat, lon);

            mMap.addMarker(new MarkerOptions().position(location).title("Location"+1));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        }
    }
}
