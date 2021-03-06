package com.example.testfragment;


import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;


import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

import org.mapsforge.core.model.LatLong;

public class DrawerNavigationView extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ActionBarDrawerToggle toggle;
    FragmentManager fm;
    DrawerLayout drawer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_navigation_view);
        drawer = (DrawerLayout) findViewById(R.id.drawer);
        NavigationView nav = (NavigationView) findViewById(R.id.navigation);
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.app_name, R.string.app_name);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fm = getSupportFragmentManager();
        nav.setNavigationItemSelectedListener(this);
        nav.getMenu().performIdentifierAction(R.id.item_first, 0);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item))
            return true;
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_first:
                fm.beginTransaction().replace(R.id.frame_layout, new FragementProfile()).commit();
                drawer.closeDrawers();
                return true;
            case R.id.item_second:
                fm.beginTransaction().replace(R.id.frame_layout, new FragementChallengesContiner()).commit();
                drawer.closeDrawers();
                return true;
            case R.id.item_mes_challenge:
                fm.beginTransaction().replace(R.id.frame_layout, new FragementListMesChallenges()).commit();
                drawer.closeDrawers();
                return true;

        }
        return false;
    }
}
