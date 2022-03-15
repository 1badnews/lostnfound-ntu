package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class EditInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }}