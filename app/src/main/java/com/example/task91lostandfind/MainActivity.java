package com.example.task91lostandfind;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private Button btnCreate, btnView, btnViewMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);
        btnCreate = findViewById(R.id.btnCreateEvent);
        btnView = findViewById(R.id.btnViewEvent);
        btnViewMap = findViewById(R.id.btnViewMap);

        btnCreate.setOnClickListener(v -> {
          startActivity(new Intent(MainActivity.this, CreateEventActivity.class));
        });
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ViewEventActivity.class));
            }
        });
        btnViewMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });
    }
}