package com.example.moviecatalog.adapter;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.moviecatalog.view.favfragmentviewpager.FavMoviesFragment;
import com.example.moviecatalog.view.favfragmentviewpager.FavTvFragment;

import java.util.ArrayList;
import java.util.List;


public class FavTabAdapter extends FragmentPagerAdapter {

    private static List<String> tabTitle = new ArrayList<>();

    public void addTitle(String title) {
        tabTitle.add(title);
    }

    public FavTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new FavMoviesFragment();
        switch (position) {
            case 0:
                fragment = new FavMoviesFragment();
                break;
            case 1:
                fragment = new FavTvFragment();
                break;
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitle.get(position);
    }

    @Override
    public int getCount() {
        return 2;
    }


}
