package com.example.moviecatalog.view.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.moviecatalog.R;
import com.example.moviecatalog.datamodel.MoviesItem;
import com.example.moviecatalog.datamodel.TvsItem;
import com.example.moviecatalog.db.AppDatabase;
import com.example.moviecatalog.db.DbDataModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_DATA = "extra_data";
    public static final String EXTRA_TIPE = "extra_tipe";

    TextView tvDescription, tvTitle, tvOTitle, tvRate, tvRelease, tvOr;
    ImageView imgDetail;
    MoviesItem moviesItem;
    TvsItem tvsItem;
    Boolean favIcon = false;
    private Menu menuIconfav;
    private AppDatabase appDatabase;
    private String Tipe;
    private boolean isChange = false;
    boolean fav;
    List<DbDataModel> dbData = new ArrayList<>();
    DbDataModel dbFavData = new DbDataModel();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setFindId();
        fav = getIntent().getBooleanExtra("fav", false);
        Tipe = getIntent().getStringExtra(EXTRA_TIPE);

        if (String.valueOf(R.string.detailmovie).equals(Tipe)) {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.detailmovie);
            moviesItem = getIntent().getParcelableExtra(EXTRA_DATA);
            setInitMovies();
        } else {
            Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.detailtv);
            tvsItem = getIntent().getParcelableExtra(EXTRA_DATA);
            setInitTvs();
        }

        appDatabase = AppDatabase.getAppDatabase(getApplicationContext());
    }

    void setFindId() {
        tvDescription = findViewById(R.id.tv_description_detail);
        tvOTitle = findViewById(R.id.tv_original_title);
        tvOr = findViewById(R.id.tv_real_or_air);
        tvRate = findViewById(R.id.tv_rate_detail);
        tvRelease = findViewById(R.id.tv_realese_detail);
        tvTitle = findViewById(R.id.title_detail);
        imgDetail = findViewById(R.id.img_detail);
    }

    void setInitMovies() {
        Glide.with(findViewById(R.id.img_detail).getContext())
                .load("https://image.tmdb.org/t/p/w500/" + moviesItem.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(imgDetail);
        tvTitle.setText(moviesItem.getTitle());
        tvRelease.setText(moviesItem.getReleaseDate());
        tvRate.setText(String.valueOf(moviesItem.getVoteAverage()));
        tvOTitle.setText(moviesItem.getOriginalTitle());
        tvDescription.setText(moviesItem.getOverview());
    }

    void setInitTvs() {
        Glide.with(findViewById(R.id.img_detail).getContext())
                .load("https://image.tmdb.org/t/p/w500/" + tvsItem.getPosterPath())
                .apply(new RequestOptions().override(350, 550))
                .into(imgDetail);
        tvTitle.setText(tvsItem.getName());
        tvRelease.setText(tvsItem.getFirstAirDate());
        tvRate.setText(String.valueOf(tvsItem.getVoteAverage()));
        tvOTitle.setText(tvsItem.getOriginalName());
        tvDescription.setText(tvsItem.getOverview());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fav_icon_menu, menu);
        menuIconfav = menu;

        if (String.valueOf(R.string.detailmovie).equals(Tipe)) {
            dbData = appDatabase.favDataDao().getById(moviesItem.getId());
            if (!dbData.isEmpty()) favIcon = true;
        } else {
            dbData = appDatabase.favDataDao().getById(tvsItem.getId());
            if (!dbData.isEmpty()) favIcon = true;
        }

        setFavIcon();
        return super.onCreateOptionsMenu(menu);
    }

    private void setFavIcon() {
        if (favIcon) {
            menuIconfav.getItem(0).setIcon(R.drawable.ic_favorite_fill_24dp);
        } else {
            menuIconfav.getItem(0).setIcon(R.drawable.ic_favorite_border_24dp);
        }
    }

    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.fav_icon) {
            isChange = !isChange;
            if (String.valueOf(R.string.detailmovie).equals(Tipe)) {
                if (favIcon) {
                    deleteMovFav();
                    favIcon = false;
                    setFavIcon();
                } else {
                    saveFavMov();
                    favIcon = true;
                    setFavIcon();
                }
            } else {
                if (favIcon) {
                    deleteTvFav();
                    favIcon = false;
                    setFavIcon();
                } else {
                    saveFavTv();
                    favIcon = true;
                    setFavIcon();
                }
            }

        }
        return super.onOptionsItemSelected(item);
    }

    public void saveFavMov() {
        try {
            dbFavData.setId(moviesItem.getId());
            dbFavData.setTitle(moviesItem.getTitle());
            dbFavData.setOriTitle(moviesItem.getOriginalTitle());
            dbFavData.setDescripsi(moviesItem.getOverview());
            dbFavData.setImage(moviesItem.getPosterPath());
            dbFavData.setRating(moviesItem.getVoteAverage());
            dbFavData.setRelease(moviesItem.getReleaseDate());
            dbFavData.setCategory("movies");

            appDatabase.favDataDao().insertFavData(dbFavData);

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.succes_save_fav), Toast.LENGTH_SHORT).show();
            Log.d("Saved to Favorite", "Success save to favorite");

        } catch (Exception e) {
            Log.d("Save to Favorite", "failed save to favorite");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_save_fav), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteMovFav() {
        try {
            appDatabase.favDataDao().deleteFavdat(moviesItem.getId());
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.delete_from_fav), Toast.LENGTH_SHORT).show();
            Log.d("DeleteFromFavorite", "Success");

        } catch (Exception e) {
            Log.d("DeleteFromFavorite", "failed delete");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_delete_from_fav), Toast.LENGTH_SHORT).show();
        }
    }

    public void saveFavTv() {
        try {
            dbFavData.setId(tvsItem.getId());
            dbFavData.setTitle(tvsItem.getName());
            dbFavData.setOriTitle(tvsItem.getOriginalName());
            dbFavData.setDescripsi(tvsItem.getOverview());
            dbFavData.setImage(tvsItem.getPosterPath());
            dbFavData.setRating(tvsItem.getVoteAverage());
            dbFavData.setRelease(tvsItem.getFirstAirDate());
            dbFavData.setCategory("tvs");

            appDatabase.favDataDao().insertFavData(dbFavData);

            Toast.makeText(getApplicationContext(), getResources().getString(R.string.succes_save_fav), Toast.LENGTH_SHORT).show();
            Log.d("Saved to Favorite", "Success save to favorite");

        } catch (Exception e) {
            Log.d("Save to Favorite", "failed save to favorite");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failed_save_fav), Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteTvFav() {
        try {
            appDatabase.favDataDao().deleteFavdat(tvsItem.getId());
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.delete_from_fav), Toast.LENGTH_SHORT).show();
            Log.d("DeleteFromFavorite", "Success");

        } catch (Exception e) {
            Log.d("DeleteFromFavorite", "failed delete");
            Toast.makeText(getApplicationContext(), getResources().getString(R.string.fail_delete_from_fav), Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (fav && isChange) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("favorite", true);
            startActivity(intent);
        }
    }
}
