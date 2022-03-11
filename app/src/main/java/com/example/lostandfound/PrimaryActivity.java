package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class PrimaryActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout draw;

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
        }
        draw.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (draw.isDrawerOpen(GravityCompat.START)){
            draw.closeDrawer(GravityCompat.START);
        } else{
            super.onBackPressed();
        }
    }
}