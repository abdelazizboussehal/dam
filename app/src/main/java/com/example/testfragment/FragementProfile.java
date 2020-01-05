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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class FragementProfile extends Fragment {
     static double x=0, y=0, z=0;
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void recupererPosition() {
        LocationManager lm = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
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
                x = location.getLongitude();
                y = location.getLatitude();
                z = location.getAltitude();
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
        if (getActivity().checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && getActivity().checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
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
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_drawer_profile, container, false);
        Button button=(Button)view.findViewById(R.id.btRecupererPosition);
        final TextView textView =(TextView) view.findViewById(R.id.tvPosition);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                recupererPosition();
                textView.setText("long : "+x+" lat :"+y);

            }
        });
        Button buttoncree=(Button) view.findViewById(R.id.btncréeChlng);
        buttoncree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CreeChallenges.class);
                startActivity(intent);
            }
        });
        getActivity().setTitle("First title");
        return view;
    }
}
