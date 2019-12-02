package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class TableLayoutPager extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_table_layout_pager);
        ViewPager pagesVP = (ViewPager) findViewById(R.id.view_pager);
        TabLayout slidingTL = (TabLayout) findViewById(R.id.table_layout);
        AdapterPager adapter =new AdapterPager(this, getSupportFragmentManager());
        pagesVP.setAdapter(adapter);
        slidingTL.setupWithViewPager(pagesVP);

    }

}

