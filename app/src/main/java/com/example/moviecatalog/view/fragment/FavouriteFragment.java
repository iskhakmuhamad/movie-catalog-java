package com.example.moviecatalog.view.fragment;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.moviecatalog.R;
import com.example.moviecatalog.adapter.FavTabAdapter;
import com.google.android.material.tabs.TabLayout;


public class FavouriteFragment extends Fragment {

    public FavouriteFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_favourite, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ViewPager viewPager = view.findViewById(R.id.view_pager);
        TabLayout tabLayout = view.findViewById(R.id.tabs);

        FavTabAdapter favTabAdapter = new FavTabAdapter(getChildFragmentManager());
        favTabAdapter.addTitle(getResources().getString(R.string.title_tab_movies));
        favTabAdapter.addTitle(getResources().getString(R.string.title_tab_tv));

        viewPager.setAdapter(favTabAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
