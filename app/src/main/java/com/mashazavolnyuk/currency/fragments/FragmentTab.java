package com.mashazavolnyuk.currency.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.LinearLayout;

import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.interfaces.ICorrectDesigner;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class FragmentTab extends android.support.v4.app.Fragment {
    FragmentCurrentCourse tab1;
    FragmentCourseGraph tab2;
    TabLayout tabLayout;
    private int[] tabIcons = {
            R.mipmap.ic_currency_eur_white_48dp,
            R.mipmap.ic_chart_line_white_48dp,
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tab, container, false);
        tabLayout = (TabLayout) v.findViewById(R.id.tabLayout);
        setHasOptionsMenu(true);
        adjustElementView(v);
        LinearLayout linearLayout=(LinearLayout) v.findViewById(R.id.parrent);
        linearLayout.removeView(tabLayout);
        (( ICorrectDesigner)getActivity()).addChild(tabLayout,0);
        return v;
    }

    private void adjustElementView(View view) {
        tabLayout.addTab(tabLayout.newTab().setText("Current situation"));
        tabLayout.addTab(tabLayout.newTab().setText("Last 30 days"));
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        final ViewPager viewPager = (ViewPager) view.findViewById(R.id.vpTab);
        viewPager.setAdapter(new PagerAdapter
                (getChildFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tab1 = new FragmentCurrentCourse();
        tab2 = new FragmentCourseGraph();
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return tab1;
                case 1:
                    return tab2;
                default:
                    return null;
            }
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public Object instantiateItem(View container, int position) {
            return super.instantiateItem(container, position);
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
