package com.example.testfragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class SplashActivity extends AppCompatActivity {

    private static final long SPLASH_DISPLAY_LENGTH = 1000;
    int PERMISSION_ALL = 1;
    String[] PERMISSIONS = {
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE


    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);



        if (!hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }
        else
        {
            spalsh();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[]
            permissions, int[] grantResults) {
        recupererPosition();
        if (requestCode == 99 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getApplicationContext(), "permission autorise", Toast.LENGTH_SHORT).show();
            Toast.makeText(getApplicationContext(), "deja autorise", Toast.LENGTH_SHORT).show();
            Intent mainIntent = new Intent(SplashActivity.this, Authentification.class);
            SplashActivity.this.startActivity(mainIntent);
            SplashActivity.this.finish();
        }
        if(requestCode == 1 ){
            if (hasPermissions(this, PERMISSIONS)) {
                spalsh();
            }
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

    public void spalsh() {
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void run() {

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
                        && checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{
                            Manifest.permission.ACCESS_FINE_LOCATION}, 99);
                } else {
                    recupererPosition();
                    SharedPreferences sharedPref = getSharedPreferences("aziz",Context.MODE_PRIVATE);
                    int id = sharedPref.getInt("id", -1);
                    if (id < 0) {
                        Toast.makeText(getApplicationContext(), "deja autorise", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(SplashActivity.this, Authentification.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();

                    } else {
                        Intent mainIntent = new Intent(SplashActivity.this, DrawerNavigationView.class);
                        SplashActivity.this.startActivity(mainIntent);
                        SplashActivity.this.finish();

                    }
                }

            }
        }, SPLASH_DISPLAY_LENGTH);
    }

    public static boolean checkConnection(Context ctx) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || !networkInfo.isConnected() ||
                (networkInfo.getType() != ConnectivityManager.TYPE_WIFI &&
                        networkInfo.getType() != ConnectivityManager.TYPE_MOBILE)) {
// No internet connection
            return false;
        } else
            return true;
    }
    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}