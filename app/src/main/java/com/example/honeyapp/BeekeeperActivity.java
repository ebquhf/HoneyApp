package com.example.honeyapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.widget.TextView;

public class BeekeeperActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beekeeper);

        Intent intent = getIntent();
        String name = intent.getStringExtra(MapsActivity.EXTRA_MESSAGE);

        TextView text = (TextView) findViewById(R.id.beekeeperName);
        text.setText(name);
    }
}