package com.example.testfragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class SecondDrawerFragement extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Second title");
        View view= inflater.inflate(R.layout.fragment_drawer_second, container, false);
        ViewPager pagesVP = (ViewPager) view.findViewById(R.id.view_pager);
        TabLayout slidingTL = (TabLayout) view.findViewById(R.id.table_layout);
        AdapterPager adapter =new AdapterPager(getContext(), getChildFragmentManager());
        pagesVP.setAdapter(adapter);
        slidingTL.setupWithViewPager(pagesVP);



        return view;
    }
}
