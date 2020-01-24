package com.example.testfragment;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testfragment.model.Address;
import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Client;

import org.mapsforge.core.model.LatLong;

import java.util.ArrayList;
import java.util.List;

public class FragementProfile extends Fragment {
     static double x=0, y=0, z=0;
    ListView listView;
    List<Challenge> noms;
    int position=-1;
    AdapterListChallenges myada;
    SharedPreferences sharedPref ;
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

        Button buttoncree=(Button) view.findViewById(R.id.btncréeChlng);
        buttoncree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),CreeChallenges.class);
                startActivity(intent);
            }
        });
        String usernamee=getActivity().getSharedPreferences("aziz",Context.MODE_PRIVATE).getString("username","none");
        int id=getActivity().getSharedPreferences("aziz",Context.MODE_PRIVATE).getInt("id",-1);
        if(id>1)
            getMychallenge(id);
        noms=new ArrayList<>();
        Client client=new Client(1,"","",usernamee,"","","","");
        Challenge challenge=new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria"));
        challenge.setrClient(client);
        noms.add(challenge);
        noms.add(challenge);
        noms.add(challenge);


        listView=(ListView)view.findViewById(R.id.listviewmychallenge);
        myada=new AdapterListChallenges(getActivity(),R.layout.item_challenge,noms);
        listView.setAdapter(myada);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"suprimer",Toast.LENGTH_LONG).show();
                position=i;
                AlertDialog alertDialog=creedialog();
                alertDialog.show();
                return false;
            }
        });
        getActivity().setTitle("First title");
        return view;
    }
    public AlertDialog creedialog(){
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                // set message, title, and icon

                .setTitle("Delete")
                .setMessage("Do you want to Delete "+(position++)+" item")
                .setIcon(R.drawable.challenges_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        position--;
                        deletechallenge(noms.get(position).getId());
                        noms.remove(position);
                        myada.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    public  void getMychallenge(int id){
        String url = "http://192.168.137.1:3000/challenge/mychallenge/"+id;

        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        noms=new ArrayList<>();
                        noms=Challenge.getChallengesFromJson(response);
                        myada=new AdapterListChallenges(getActivity(),R.layout.item_challenge,noms);
                        listView.setAdapter(myada);
                        myada.notifyDataSetChanged();
                        queue.getCache().clear();
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.getCache().clear();
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);
    }

    public  void deletechallenge(int id){
        String url = "http://192.168.137.1:3000/challenge/"+id;

        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        myada.notifyDataSetChanged();
                    }
                },  new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                queue.getCache().clear();
                Toast.makeText(getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });

// Add the request to the RequestQueue.

        queue.add(stringRequest);
    }
}
