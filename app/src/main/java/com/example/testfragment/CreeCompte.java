package com.example.testfragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;

import com.android.volley.toolbox.Volley;
import com.dd.processbutton.iml.ActionProcessButton;

import com.example.testfragment.model.Client;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;

public class CreeCompte extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String version;
    ActionProcessButton creecompte;
    MaterialEditText editnom, editprenom, editemail, editpassword, editdate;
    EditText editphonrnumber;
    DatePickerDialog dpd;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        String date = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
        if(view ==dpd)
        editdate.setText(date);
    }

    @SuppressLint({"WrongViewCast", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cree_compte);
        creecompte = findViewById(R.id.btCreeCompte);
        String numInterNational[] = {"+213", "+22", "+51"};
        Spinner spinner = (Spinner) findViewById(R.id.spinnerNumNatonal);
        ArrayAdapter spinnerArrayAdapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                numInterNational);
        editnom = findViewById(R.id.etxt_nom);
        editprenom = findViewById(R.id.etxt_prénom);
        editemail = findViewById(R.id.etxt_email);
        editpassword = findViewById(R.id.etxt_password);
        editphonrnumber = findViewById(R.id.etxt_num);
        editdate = findViewById(R.id.etxt_date);
        spinner.setAdapter(spinnerArrayAdapter);
        creecompte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "cree compte", Toast.LENGTH_SHORT).show();
                String prenom, nom, username, password, phone, date;
                prenom = editprenom.getText().toString();
                nom = editprenom.getText().toString();
                username = editemail.getText().toString();
                password = editpassword.getText().toString();
                phone = editphonrnumber.getText().toString();
                date =editdate.getText().toString();
                if (TextUtils.isEmpty(nom)||TextUtils.isEmpty(prenom)||TextUtils.isEmpty(password)||TextUtils.isEmpty(username)
                ||TextUtils.isEmpty(date)||TextUtils.isEmpty(phone))
                {
                    if(TextUtils.isEmpty(nom))
                        editnom.setError("le champ est vide");
                    if(TextUtils.isEmpty(prenom))
                        editprenom.setError("le champ est vide");
                    if(TextUtils.isEmpty(username))
                        editemail.setError("le champ est vide");
                    if(TextUtils.isEmpty(password))
                        editpassword.setError("le champ est vide");
                    if(TextUtils.isEmpty(date))
                        editdate.setError("le champ est vide");
                    if(TextUtils.isEmpty(phone))
                        editphonrnumber.setError("le champ est vide");

                }
                    else
                {
                    creecompte.setMode(ActionProcessButton.Mode.PROGRESS);
                    creecompte.setProgress(100);
                    try {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        version = pInfo.versionName;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
                    Client client = new Client(1, nom, prenom, username
                            , password, editdate.getText().toString(), phone, version);


                    creeCompteRequtte(client);
                }
            }
        });
        Calendar now = Calendar.getInstance();
        dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR), // Initial year selection
                now.get(Calendar.MONTH), // Initial month selection
                now.get(Calendar.DAY_OF_MONTH) // Inital day selection
        );
        dpd.setVersion(DatePickerDialog.Version.VERSION_2);
        dpd.setAccentColor(R.color.colorPrimary);
// If you're calling this from a support Fragment
        //
        editdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dpd.show(getFragmentManager(), "Datepickerdialog");
            }
        });

    }

    @Override
    public void finish() {
        Intent aziz = new Intent();
        aziz.putExtra("email", editemail.getText().toString());
        aziz.putExtra("password", editpassword.getText().toString());
        setResult(RESULT_OK, aziz);
        super.finish();
    }

    public void creeCompteRequtte(Client client) {

        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, "http://192.168.137.1:3000/client/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        finish();
                        Log.d("Response", response);
                        creecompte.setProgress(0);
                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                creecompte.setProgress(0);
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }

        });

        smr.addStringParam("client", client.tojson());
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        mRequestQueue.add(smr);

    }

}
