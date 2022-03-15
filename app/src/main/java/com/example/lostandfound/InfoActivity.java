package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    private ImageView xmlimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        xmltitle=findViewById(R.id.infotitle);
        xmldesc=findViewById(R.id.infodescription);
        xmlcontacts=findViewById(R.id.infocontacts);
        xmlimage=findViewById(R.id.infoimage);
        ref = FirebaseDatabase.getInstance().getReference().child("Items");
        String key = getIntent().getStringExtra("key");

            ref.child(key).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    String imageurl = snapshot.child("image").getValue().toString();
                    Glide.with(getApplicationContext())
                            .load(imageurl)
                            .into(xmlimage);
                    String title= snapshot.child("title").getValue().toString();
                    String desc= snapshot.child("description").getValue().toString();
                    String contacts= snapshot.child("contacts").getValue().toString();
                    xmltitle.setText(title);
                    xmldesc.setText(desc);
                    xmlcontacts.setText(contacts);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });




    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }}
