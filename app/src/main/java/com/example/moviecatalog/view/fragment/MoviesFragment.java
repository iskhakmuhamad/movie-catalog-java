package com.example.moviecatalog.view.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.example.moviecatalog.R;
import com.example.moviecatalog.adapter.MoviesAdapter;
import com.example.moviecatalog.datamodel.MoviesItem;
import com.example.moviecatalog.view.activity.DetailActivity;
import com.example.moviecatalog.viewmodel.MoviesViewModel;
import com.example.moviecatalog.viewmodel.SearchMoviesViewModel;
import java.util.ArrayList;

public class MoviesFragment extends Fragment implements SearchView.OnQueryTextListener {

    private MoviesAdapter moviesAdapter = new MoviesAdapter();
    private ProgressBar progressBar;

    private static final String STATE_QUERY = "state_query";
    private static final String STATE_SEARCH = "is_search";
    private static String query;
    private Boolean isSearch;


    public MoviesFragment() {
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressBar = view.findViewById(R.id.progres_mbar);
        String lang = getResources().getString(R.string.language);
        isSearch = false;
        if (savedInstanceState != null) {
            isSearch = savedInstanceState.getBoolean(STATE_SEARCH);
            query = savedInstanceState.getString(STATE_QUERY);
        }

        if (isSearch) {
            SearchMoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(SearchMoviesViewModel.class);
            moviesViewModel.getSearchMovies().observe(this, getMovie);
            moviesViewModel.setMovieSearchData(lang, query);
        } else {
            MoviesViewModel moviesViewModel = ViewModelProviders.of(this).get(MoviesViewModel.class);
            moviesViewModel.getMovies().observe(this, getMovie);
            moviesViewModel.setMovies(lang);
        }

        RecyclerView recyclerView = view.findViewById(R.id.rv_movies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setOnItemClickCallback(new MoviesAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(MoviesItem data) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);

                intent.putExtra(DetailActivity.EXTRA_DATA, data);
                intent.putExtra(DetailActivity.EXTRA_TIPE, String.valueOf(R.string.detailmovie));
                startActivity(intent);
            }
        });

    }

    private Observer<ArrayList<MoviesItem>> getMovie = new Observer<ArrayList<MoviesItem>>() {
        @Override
        public void onChanged(ArrayList<MoviesItem> datas) {
            if (datas != null) {
                moviesAdapter.setMoviesData(datas);
                showLoading(false);
            } else {
                showLoading(true);
            }
        }
    };

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW | MenuItem.SHOW_AS_ACTION_IF_ROOM);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.query_hint));
        searchView.onActionViewExpanded();
        searchView.setOnQueryTextListener(this);

    }

    @Override
    public boolean onQueryTextChange(String query) {
        isSearch = true;
        MoviesFragment.query = query;
        String lang = getResources().getString(R.string.language);
        SearchMoviesViewModel tvsViewModel = ViewModelProviders.of(this).get(SearchMoviesViewModel.class);
        tvsViewModel.getSearchMovies().observe(this, getMovie);
        tvsViewModel.setMovieSearchData(lang, query);
        return false;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        isSearch = true;
        MoviesFragment.query = query;
        String lang = getResources().getString(R.string.language);
        SearchMoviesViewModel tvsViewModel = ViewModelProviders.of(this).get(SearchMoviesViewModel.class);
        tvsViewModel.getSearchMovies().observe(this, getMovie);
        tvsViewModel.setMovieSearchData(lang, query);
        return true;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SEARCH, isSearch);
        outState.putString(STATE_QUERY, query);
    }

}
