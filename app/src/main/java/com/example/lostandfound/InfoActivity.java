package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {
    TextView xmltitle;
    TextView xmldesc;
    TextView xmlcontacts;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        xmltitle=findViewById(R.id.infotitle);
       // xmldesc=findViewById(R.id.infodescription);
        //xmlcontacts=findViewById(R.id.infocontacts);
        ref = FirebaseDatabase.getInstance().getReference().child("Items");
        String key = getIntent().getStringExtra("key");


            xmltitle.setText(key);


    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }}
