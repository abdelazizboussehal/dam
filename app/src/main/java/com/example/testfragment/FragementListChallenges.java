package com.example.testfragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.testfragment.model.Address;
import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Challengeee;

import org.json.JSONObject;
import org.mapsforge.core.model.LatLong;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class FragementListChallenges extends Fragment {
    List<Challenge> noms;
    AdapterListChallenges myada;

    public FragementListChallenges() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("List challenegs");
        View view = inflater.inflate(R.layout.fragement_list_challenges, container, false);
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        noms = new ArrayList<>();
        /*noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));
        noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));
        noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));
        noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));*/
        ListView listView = (ListView) view.findViewById(R.id.ListvewText);
        myada = new AdapterListChallenges(getActivity(), R.layout.item_challenge, noms);
        listView.setAdapter(myada);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ChallengeDetailler.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("challenge", (Serializable) noms.get(i));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recupererAllChallenge();
        return view;
    }

    public void recupererAllChallenge() {
        String url = "http://192.168.137.1:3000/challenge/";

        RequestQueue queue = Volley.newRequestQueue(getContext());

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<LatLong> path = new ArrayList<>();
                        JSONObject jsonObj = null;
                        List<Challenge> challengeSet = new ArrayList<>();
                        challengeSet = Challenge.getChallengesFromJson(response);
                        noms.addAll(challengeSet);
                        queue.getCache().clear();
                        myada.notifyDataSetChanged();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                queue.getCache().clear();
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
