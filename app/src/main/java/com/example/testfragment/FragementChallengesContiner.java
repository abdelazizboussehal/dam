package com.example.testfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class FragementChallengesContiner extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Challenges");
        View view= inflater.inflate(R.layout.fragment_challenges_container, container, false);
        ViewPager pagesVP = (ViewPager) view.findViewById(R.id.view_pager);
        TabLayout slidingTL = (TabLayout) view.findViewById(R.id.table_layout);
        AdapterPager adapter =new AdapterPager(getContext(), getChildFragmentManager());
        pagesVP.setAdapter(adapter);
        slidingTL.setupWithViewPager(pagesVP);



        return view;
    }
}
