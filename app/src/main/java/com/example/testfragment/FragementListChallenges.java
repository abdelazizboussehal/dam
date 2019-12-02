package com.example.testfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragementListChallenges extends Fragment {
    public FragementListChallenges(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragement_list_challenges, container, false);
        List<String> noms=new ArrayList<>();
        noms.add("aziz");
        noms.add("aziz");
        noms.add("aziz");
        ListView listView=(ListView)view.findViewById(R.id.ListvewText);
        AdapterListChallenges myada=new AdapterListChallenges(getActivity(),R.layout.item_challenge,noms);
        listView.setAdapter(myada);
        return view;
    }
}
