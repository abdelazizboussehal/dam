package com.example.testfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SecondFragement extends Fragment {
    public SecondFragement(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view= inflater.inflate(R.layout.fragement_second, container, false);
        List<String> noms=new ArrayList<>();
        noms.add("aziz");
        noms.add("aziz");
        noms.add("aziz");
        ListView listView=(ListView)view.findViewById(R.id.ListvewText);
        myAdapter myada=new myAdapter(getActivity(),R.layout.item_list_view,noms);
        listView.setAdapter(myada);
        return view;
    }
}
