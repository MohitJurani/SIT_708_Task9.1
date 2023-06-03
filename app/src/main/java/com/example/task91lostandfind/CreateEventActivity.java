package com.example.task91lostandfind;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import java.util.List;

public class CreateEventActivity extends AppCompatActivity implements LocationListener {
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 10001;
    private Button btnSave, btnCurrentLoc;

    private EditText etTitle, etLocation, etPhone, etDescription, etDate;

    private Item item;

    private RadioGroup radioGroup;

    private String type = "Found";

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    String lat;
    String provider;
    protected String latitude, longitude;
    protected boolean gps_enabled, network_enabled;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);
        btnSave = findViewById(R.id.btnSave);
        etTitle = findViewById(R.id.et_1);
        etPhone = findViewById(R.id.et_2);
        etDescription = findViewById(R.id.et_3);
        etLocation = findViewById(R.id.et_4);
        etDate = findViewById(R.id.et_d);
        radioGroup = findViewById(R.id.radioGroup);

        btnCurrentLoc = findViewById(R.id.btnCurrentLoc);

        item = new Item();
        btnSave.setOnClickListener(v -> {
            if (TextUtils.isEmpty(etTitle.getText().toString())) {
                Toast.makeText(this, "Please enter title", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etDate.getText().toString())) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etDate.getText().toString())) {
                Toast.makeText(this, "Please enter date", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etPhone.getText().toString())) {
                Toast.makeText(this, "Please enter mobile", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etDescription.getText().toString())) {
                Toast.makeText(this, "Please enter description", Toast.LENGTH_SHORT).show();
            } else if (TextUtils.isEmpty(etLocation.getText().toString())) {
                Toast.makeText(this, "Please enter location", Toast.LENGTH_SHORT).show();
            } else {
                item.setId(type);
                item.setDate(etDate.getText().toString());
                item.setTitle(etTitle.getText().toString());
                item.setDescription(etDescription.getText().toString());
                item.setContact(etPhone.getText().toString());
                item.setLocation(etLocation.getText().toString());
                long id = DatabaseHelper.getInstance(this).insertItem(item);
                if (id > 0) {
                    Toast.makeText(CreateEventActivity.this, "Incident logged success", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(CreateEventActivity.this, "Please try again.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = findViewById(checkedId);
                type = radioButton.getText().toString();
                Toast.makeText(CreateEventActivity.this, "Selected Radio Button is : " + radioButton.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        btnCurrentLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(CreateEventActivity.this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED &&
                        ActivityCompat.checkSelfPermission(CreateEventActivity.this,
                                Manifest.permission.ACCESS_COARSE_LOCATION)
                                != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateEventActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            LOCATION_PERMISSION_REQUEST_CODE);
                } else {
                    // Permission is already granted
                    getCurrentLocation();
                }
            }
        });
    }

    @SuppressLint("MissingPermission")
    private void getCurrentLocation() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, get the current location
                getCurrentLocation();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

    }

    @Override
    public void onLocationChanged(@NonNull List<Location> locations) {
        LocationListener.super.onLocationChanged(locations);
        Double lat = locations.get(0).getLatitude();
        Double lo = locations.get(0).getLongitude();
        etLocation.setText(lat +" "+lo);
    }

    @Override
    public void onFlushComplete(int requestCode) {
        LocationListener.super.onFlushComplete(requestCode);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        LocationListener.super.onStatusChanged(provider, status, extras);
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        LocationListener.super.onProviderEnabled(provider);
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        LocationListener.super.onProviderDisabled(provider);
    }
}