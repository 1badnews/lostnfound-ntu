package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class PrimaryActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener,DialogClass.DialogInterfaceListener {

    private DrawerLayout draw;
    TextView nnumber;
    TextView emailaddy;
    public String email,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        draw=findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, draw,
                toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        draw.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState==null){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LostItemsFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_lostitems);}



        FirebaseDatabase database = FirebaseDatabase.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();

        database.getReference().child("Accounts").child(auth.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Account acc = snapshot.getValue(Account.class);
                View header = navigationView.getHeaderView(0);
                nnumber = header.findViewById(R.id.Nnumber);
                emailaddy= header.findViewById(R.id.emailaddy);
                nnumber.setText(acc.getStudentID());
                emailaddy.setText(acc.getEmail());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_lostitems:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new LostItemsFragment()).commit();

                break;
            case R.id.nav_myitems:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MyItemsFragment()).commit();

                break;
            case R.id.nav_postitem:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new PostItemFragment()).commit();

                break;
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(PrimaryActivity.this, MainActivity.class);
                PrimaryActivity.this.startActivity(intent);
                break;

            case R.id.nav_settings:
                openDialog();
                break;

        }
        draw.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openDialog() {
        DialogClass dialog = new DialogClass();
        dialog.show(getSupportFragmentManager(), "Dialog");
    }

    @Override
    public void onBackPressed() {
        if (draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }

    @Override
    public void reauth(String username, String password) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        email = username;
        pass = password;
        AuthCredential credential = EmailAuthProvider
                .getCredential(email, pass);
        user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SettingsFragment()).commit();
                } else {
                    Toast.makeText(PrimaryActivity.this,"User failed to reauthenticate!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}