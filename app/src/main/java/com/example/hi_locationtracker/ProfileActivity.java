package com.example.hi_locationtracker;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        String fullname = getIntent().getStringExtra("FULLNAME");
        final TextView nameText = (TextView) findViewById(R.id.textView);
        nameText.setText(fullname);
    }
}
