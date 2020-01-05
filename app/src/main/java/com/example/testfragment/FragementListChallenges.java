package com.example.testfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.testfragment.model.Challengeee;

import java.util.ArrayList;
import java.util.List;



public class FragementListChallenges extends Fragment {
    public FragementListChallenges(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("List challenegs");
        View view= inflater.inflate(R.layout.fragement_list_challenges, container, false);
        List<Challengeee> noms=new ArrayList<>();
        noms.add(new Challengeee(R.drawable.lieu1,1,50,"12/05/2020",15,"challenge nouvelle ville uv 3"));
        noms.add(new Challengeee(R.drawable.lieu2,1,50,"15/02/2020",15,"challenge nouvelle ville uv 17"));
        noms.add(new Challengeee(R.drawable.lieu3,1,50,"",15,"challenge elkhroub 1013"));
        noms.add(new Challengeee(R.drawable.lieu1,1,50,"12/05/2020",15,"challenge nouvelle ville uv 3"));
        noms.add(new Challengeee(R.drawable.lieu2,1,50,"15/02/2020",15,"challenge nouvelle ville uv 17"));
        noms.add(new Challengeee(R.drawable.lieu3,1,50,"",15,"challenge elkhroub 1013"));
        noms.add(new Challengeee(R.drawable.lieu1,1,50,"12/05/2020",15,"challenge nouvelle ville uv 3"));
        noms.add(new Challengeee(R.drawable.lieu2,1,50,"15/02/2020",15,"challenge nouvelle ville uv 17"));
        noms.add(new Challengeee(R.drawable.lieu3,1,50,"",15,"challenge elkhroub 1013"));
        noms.add(new Challengeee(R.drawable.lieu1,1,50,"12/05/2020",15,"challenge nouvelle ville uv 3"));
        noms.add(new Challengeee(R.drawable.lieu2,1,50,"15/02/2020",15,"challenge nouvelle ville uv 17"));
        noms.add(new Challengeee(R.drawable.lieu3,1,50,"",15,"challenge elkhroub 1013"));
        ListView listView=(ListView)view.findViewById(R.id.ListvewText);
        AdapterListChallenges myada=new AdapterListChallenges(getActivity(),R.layout.item_challenge,noms);
        listView.setAdapter(myada);
        return view;
    }
}
