package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testfragment.model.Address;
import com.example.testfragment.model.Challenge;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class FormulaireAdress extends AppCompatActivity {
    Bundle bundle;
    String ville,rue,codezip,paye;
    EditText edville,edrue,edcodezip,edpaye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulaire_adress);
        Intent intent=getIntent();
        bundle=intent.getExtras();
        edville=findViewById(R.id.edt_ville);
        edrue=findViewById(R.id.edt_rue);
        edcodezip=findViewById(R.id.edt_postal);
        edpaye=findViewById(R.id.edt_paye);
        if(bundle.size()>2){
        ville=bundle.getString("ville");
        rue=bundle.getString("street");
        codezip=bundle.getString("zipeCode");
        paye=bundle.getString("city");

        edrue.setText(rue);
        edpaye.setText(paye);
        edcodezip.setText(codezip);
        edville.setText(ville);
        }

        Button button=findViewById(R.id.btPublier);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Double lat=(double)bundle.getDouble("lat");
                Double lon=(double)bundle.getDouble("lon");
                creeChallengePost(lat,lon);
            }
        });
    }

    public  void creeChallengePost(double lat,double lon){
        String url = "http://192.168.137.1:3000/challenge/";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Calendar calobj = Calendar.getInstance();
        System.out.println(df.format(calobj.getTime()));

        Challenge challenge =new Challenge(123,df.format(calobj.getTime()),null,null
                ,new Address(1,lon,lat,edrue.getText().toString(),edville.getText().toString()
                ,edcodezip.getText().toString(),
                edpaye.getText().toString()));
        JSONParser parser = new JSONParser();
        JSONObject jsonBody=null;
        String a=challenge.tojson();
        a=challenge.tojson();
        try {
            jsonBody = new JSONObject(a);

        }  catch (JSONException e) {
            e.printStackTrace();
        }
        final String mRequestBody = jsonBody.toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                finish();
                CreeChallenges.activity.finish();
                Log.i("LOG_RESPONSE", response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null) {
                    responseString = String.valueOf(response.statusCode);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };

        requestQueue.add(stringRequest);
    }

}
