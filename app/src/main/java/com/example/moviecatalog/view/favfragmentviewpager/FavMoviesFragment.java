package com.example.moviecatalog.view.favfragmentviewpager;


import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviecatalog.R;
import com.example.moviecatalog.adapter.FavAdapter;
import com.example.moviecatalog.datamodel.MoviesItem;
import com.example.moviecatalog.db.AppDatabase;
import com.example.moviecatalog.db.DbDataModel;
import com.example.moviecatalog.view.activity.DetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.example.moviecatalog.view.activity.DetailActivity.EXTRA_DATA;
import static com.example.moviecatalog.view.activity.DetailActivity.EXTRA_TIPE;

public class FavMoviesFragment extends Fragment {

    private AppDatabase appDatabase;
    private List<DbDataModel> dataModelList = new ArrayList<>();


    public FavMoviesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fav_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.tv_null);

        if (appDatabase == null) {
            appDatabase = AppDatabase.getAppDatabase(getContext());
            Log.d("TABFAVMOVIE", "crete database");
        }

        dataModelList.addAll(appDatabase.favDataDao().getByCategory("movies"));
        FavAdapter favAdapter = new FavAdapter();
        favAdapter.setData(dataModelList);
        favAdapter.notifyDataSetChanged();

        RecyclerView rvFavMov = view.findViewById(R.id.rv_fav_mov);
        rvFavMov.setLayoutManager(new LinearLayoutManager(getContext()));
        rvFavMov.setAdapter(favAdapter);

        favAdapter.setOnItemClickCallback(new FavAdapter.OnItemClickCallback() {
            @Override
            public void onItemClicked(DbDataModel datas) {

                MoviesItem data = new MoviesItem();
                data.setTitle(datas.getTitle());
                data.setOriginalTitle(datas.getOriTitle());
                data.setOverview(datas.getDescripsi());
                data.setVoteAverage(datas.getRating());
                data.setReleaseDate(datas.getRelease());
                data.setPosterPath(datas.getImage());
                data.setId(datas.getId());

                Intent intent =new Intent(getActivity(), DetailActivity.class);
                intent.putExtra(EXTRA_DATA, data);
                intent.putExtra("fav",true);
                intent.putExtra(EXTRA_TIPE,String.valueOf(R.string.detailmovie));
                startActivity(intent);

            }
        });

        if (dataModelList.isEmpty()){
            textView.setVisibility(View.VISIBLE);
        }else {
            textView.setVisibility(View.GONE);
        }
    }

}
