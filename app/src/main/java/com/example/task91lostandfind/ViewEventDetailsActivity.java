package com.example.task91lostandfind;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ViewEventDetailsActivity extends AppCompatActivity {
    private Button btnRemove;
    private TextView tv1, tv2, tv3, tv4, tv5, tv6;

    private Item item;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__view_details);
        btnRemove = findViewById(R.id.btnRemove);

        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);
        tv4= findViewById(R.id.tv4);
        tv5 = findViewById(R.id.tv5);
        tv6 = findViewById(R.id.tv6);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        String phone = getIntent().getStringExtra("phone");
        String location = getIntent().getStringExtra("location");
        String date = getIntent().getStringExtra("date");
        String id = getIntent().getStringExtra("id");

        item = new Item();
        item.setId(id);
        item.setContact(phone);
        item.setLocation(location);
        item.setDate(date);
        item.setDescription(description);
        item.setTitle(title);

        tv1.setText("Type:" + item.getId());
        tv2.setText("Title:" + item.getTitle());
        tv3.setText("Description:" + item.getDescription());
        tv4.setText("Phone:" + item.getContact());
        tv5.setText("Location:" + item.getLocation());
        tv6.setText("Date:" + item.getDate());

        btnRemove.setOnClickListener(v -> {

            long isSuccess = DatabaseHelper.getInstance(this).removeRecord(item);
            if (isSuccess > 0) {
                Toast.makeText(ViewEventDetailsActivity.this, "Incident removed success",
                        Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(ViewEventDetailsActivity.this, "Please try again.",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}