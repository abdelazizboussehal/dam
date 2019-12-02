package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class TestFragement extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_fragement);
        FirstFragement fragment = new FirstFragement();
        SecondFragement fragment2=new SecondFragement();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.container_1, fragment);
        fragmentTransaction.add(R.id.container_2, fragment2);

        fragmentTransaction.commit();
    }
}
