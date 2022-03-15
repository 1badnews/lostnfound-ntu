package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class EditInfoActivity extends AppCompatActivity {

    private EditText edit_title,edit_description,edit_contacts;
    private Button edit_button, delete_button;
    private DatabaseReference dbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        edit_title=findViewById(R.id.edit_title);
        edit_description=findViewById(R.id.edit_description);
        edit_contacts=findViewById(R.id.edit_contacts);
        edit_button=findViewById(R.id.edit_button);
        String key = getIntent().getStringExtra("key");
        delete_button = findViewById(R.id.delete_button);

        dbref = FirebaseDatabase.getInstance().getReference().child("Items");



        edit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = edit_title.getText().toString();
                String description = edit_description.getText().toString();
                String contacts = edit_contacts.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("contacts",contacts);
                hashMap.put("description",description);
                hashMap.put("title",title);



                dbref.child(key).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        onBackPressed();
                    }
                });
            }
        });

        dbref.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String title= snapshot.child("title").getValue().toString();
                String desc= snapshot.child("description").getValue().toString();
                String contacts= snapshot.child("contacts").getValue().toString();
                edit_title.setText(title);
                edit_description.setText(desc);
                edit_contacts.setText(contacts);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
    });


        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbref.child(key).removeValue();

            }
        });




    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }}