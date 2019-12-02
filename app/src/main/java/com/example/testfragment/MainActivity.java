package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        List<String> noms=new ArrayList<>();
        noms.add("aziz");
        noms.add("aziz");
        noms.add("aziz");
        ListView listView=(ListView)findViewById(R.id.ListvewText);
        myAdapter myada=new myAdapter(MainActivity.this,R.layout.item_list_view,noms);
        listView.setAdapter(myada);
    }
}
