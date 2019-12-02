package com.example.testfragment;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class AdapterPager  extends FragmentPagerAdapter {
        private Context ctx;
        public AdapterPager(Context ctx, FragmentManager fm){super(fm); this.ctx=ctx;}
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0: return new FragemntMapsChallenges();
                case 1: return new FragementListChallenges();}
                // lazem return
            return new FragementListChallenges();
        }
        @Override
        public CharSequence getPageTitle(int position){
            switch (position) {
                case 0: return ctx.getString(R.string.maps);
                case 1: return ctx.getString(R.string.challenges);
            }
            // lazem return
             return ctx.getString(R.string.maps);
        }
        @Override
        public int getCount() { return 2; }
    }

