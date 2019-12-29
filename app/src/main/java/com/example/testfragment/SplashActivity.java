package com.example.testfragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable(){
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION}, 99);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"deja autorise",Toast.LENGTH_SHORT).show();
                    Intent mainIntent = new Intent(SplashActivity.this, Authentification.class);
                    recupererPosition();
                    SplashActivity.this.startActivity(mainIntent);
                    SplashActivity.this.finish();
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        recupererPosition();
        if(requestCode==99 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(getApplicationContext(),"permission autorise",Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(),"deja autorise",Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(SplashActivity.this, Authentification.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void recupererPosition() {
        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria cr = new Criteria();
        cr.setAccuracy(Criteria.ACCURACY_FINE); // précision
        cr.setAltitudeRequired(true); // altitude
        cr.setBearingRequired(true); // direction
        cr.setCostAllowed(false); // payant/gratuit
        cr.setPowerRequirement(Criteria.POWER_HIGH); // consommation
        cr.setSpeedRequired(true); // vitesse
        String provider = lm.getBestProvider(cr, false);
        int minTime = 5000; // en millisecondes
        float minDistance = 10; // en mètres


        LocationListener locListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                FragementProfile.x = location.getLongitude();
                FragementProfile.y = location.getLatitude();
                FragementProfile.z = location.getAltitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            // quand le status d’une source change.
// Il existe 3 statuts : OUT_OF_SERVICE, TEMPORARILY_UNAVAILABLE, AVAILABLE
            public void onProviderEnabled(String provider) {
            }

            // quand une source de localisation est activée (GPS, 3G..etc)
            public void onProviderDisabled(String provider) {
            }
// quand une source de localisation est désactivée (GPS, 3G, ...)
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        lm.requestLocationUpdates(provider, minTime, minDistance, locListener);
    }
}