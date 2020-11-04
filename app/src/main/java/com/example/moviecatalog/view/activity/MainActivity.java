package com.example.moviecatalog.view.activity;

import android.content.Intent;
import android.os.Bundle;

import com.example.moviecatalog.R;
import com.example.moviecatalog.view.fragment.FavouriteFragment;
import com.example.moviecatalog.view.fragment.MoviesFragment;
import com.example.moviecatalog.view.fragment.TvFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.navigation_movies:
                    Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.listmovie);
                    fragment = new MoviesFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_tvshow:
                    Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.listtv);
                    fragment = new TvFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
                case R.id.navigation_favorite:
                    Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.listfav);
                    fragment = new FavouriteFragment();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        Objects.requireNonNull(getSupportActionBar()).setElevation(0.5f);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.listmovie);
            Fragment fragment;
            fragment = new MoviesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName()).commit();
        }
        boolean fav = false;
        fav = getIntent().getBooleanExtra("favorite", false);
        if (fav) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.listfav);
            Fragment mfragment = new FavouriteFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, mfragment, mfragment.getClass().getSimpleName()).commit();
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.setting, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        else if (item.getItemId() == R.id.action_switch_menu){
            Intent intent = new Intent(MainActivity.this,ReminderActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

}
