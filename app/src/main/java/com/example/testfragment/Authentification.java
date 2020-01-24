package com.example.testfragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dd.processbutton.iml.ActionProcessButton;
import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Client;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONObject;
import org.mapsforge.core.model.LatLong;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Authentification extends AppCompatActivity {
    MaterialEditText username , password;
    String stringuUername,stringpassword;
    ActionProcessButton btnSignIn;

   // ActionProcessButton btnSignIn;
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
         username=findViewById(R.id.etxt_email);
         password= findViewById(R.id.etxt_password);

         // btnSignIn = (ActionProcessButton) findViewById(R.id.btnSignIn);
        //Bundle extras = getIntent().getExtras();
        //btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);



        btnSignIn = (ActionProcessButton) findViewById(R.id.bt_connectez_vous);
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                stringuUername=username.getText().toString();
                stringpassword=password.getText().toString();
                if(TextUtils.isEmpty(stringuUername)||TextUtils.isEmpty(stringpassword)) {
                    if(TextUtils.isEmpty(stringuUername))
                        username.setError("le champ est vide");
                    if(TextUtils.isEmpty(stringpassword))
                        password.setError("le champ est vide");
                    return;
                }
                else
                {
                    btnSignIn.setMode(ActionProcessButton.Mode.PROGRESS);

// no progress
                    btnSignIn.setProgress(0);
// progressDrawable cover 50% of button width, progressText is shown
                    btnSignIn.setProgress(50);
// progressDrawable cover 75% of button width, progressText is shown
                    btnSignIn.setProgress(75);
// completeColor & completeText is shown
                    btnSignIn.setProgress(100);

// you can display endless google like progress indicator
                    btnSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
// set progress > 0 to start progress indicator animation
                    btnSignIn.setProgress(1);
                    authtification(stringuUername,stringpassword);
             //       Intent intent=new Intent(getApplicationContext(),DrawerNavigationView.class);
               //     startActivity(intent);
                 //   finish();
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
                username.setText(data.getStringExtra("email"));
                password.setText(data.getStringExtra("password"));
            }
        }
    }

    public void authtification(final String user, final String password){
        String url = "http://192.168.137.1:3000/client/auth";


        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        StringRequest sr = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<Client> clients=Client.getClientsFromJson(response);
                        if(clients.size()>0){
                            Client client=clients.get(0);
                            Intent intent=new Intent(getApplicationContext(),DrawerNavigationView.class);
                            startActivity(intent);
                            finish();
                            queue.getCache().clear();
                        }
                        else
                        {
                            queue.getCache().clear();
                            btnSignIn.setProgress(0);
                            Toast.makeText(getApplicationContext(),"incorrect mdp or password",Toast.LENGTH_LONG).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        btnSignIn.setProgress(0);
                        queue.getCache().clear();
                    }
                })
        {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("Content-Type","application/json");
                params.put("userName", user);
                params.put("password", password);
                return params;
            }
        };
        queue.add(sr);
    }
}
