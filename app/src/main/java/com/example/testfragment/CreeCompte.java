package com.example.testfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreeCompte extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_compte);
        Button creecompte= (Button)findViewById(R.id.btCreeCompte);
        String numInterNational []={"+213","+22","+51"};
        Spinner spinner=(Spinner)findViewById(R.id.spinnerNumNatonal);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                numInterNational);
        spinner.setAdapter(spinnerArrayAdapter);
        creecompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"cree compte",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    public void finish() {
        Intent aziz=new Intent();
        aziz.putExtra("email","aziz@gmail.com");
        setResult(RESULT_OK,aziz);
        super.finish();
    }
}
