package com.example.testfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Authentification extends AppCompatActivity {
    Button btConnecteVous;
    EditText username , password;
    String stringuUername,stringpassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authentification);
        TextView tv=(TextView) findViewById(R.id.tvCreecompte);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentCreecompte =new Intent(Authentification.this,CreeCompte.class);
                startActivityForResult(intentCreecompte,99);
            }
        });
         username=(EditText) findViewById(R.id.etxt_email);
         password=(EditText) findViewById(R.id.etxt_password);
        btConnecteVous=(Button)findViewById(R.id.bt_connectez_vous);
        btConnecteVous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stringuUername=username.getText().toString();
                stringpassword=password.getText().toString();
                if(stringpassword.equals("aziz") && stringuUername.equals("aziz")){
                    Intent intent=new Intent(getApplicationContext(),DrawerNavigationView.class);
                    startActivity(intent);

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"false",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                Toast.makeText(getApplicationContext(), "aziz", Toast.LENGTH_LONG).show();
            }
        }
    }
}
