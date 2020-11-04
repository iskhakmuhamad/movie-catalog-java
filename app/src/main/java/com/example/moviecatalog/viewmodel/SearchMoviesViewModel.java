package com.example.moviecatalog.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalog.datamodel.MoviesItem;
import com.example.moviecatalog.datamodel.MoviesResponse;
import com.example.moviecatalog.rest.MoviesService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchMoviesViewModel extends ViewModel {

    private MoviesService moviesService;
    private MutableLiveData<ArrayList<MoviesItem>> movieSearchData = new MutableLiveData<>();

    public void setMovieSearchData(String lang, String query) {

        if (moviesService == null) {
            moviesService = new MoviesService();
        }

        moviesService.getMoviesApi().getSearchMovies(lang, query).enqueue(new Callback<MoviesResponse>() {
            @Override
            public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                MoviesResponse moviesResponse = response.body();
                if (moviesResponse != null && moviesResponse.getResults() != null) {
                    ArrayList<MoviesItem> moviesItems = new ArrayList<>();
                    for (int i = 0; i < moviesResponse.getResults().size(); i++) {
                        if (!moviesResponse.getResults().get(i).getOverview().equals("")) {
                            moviesItems.add(moviesResponse.getResults().get(i));
                        }
                    }
                    Log.d("SearchMovies view Model", "onResponse: succes get " + moviesItems);
                    movieSearchData.postValue(moviesItems);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                Log.d("SearchMovies view Model", "onFailure : because " + t.getMessage());
            }
        });

    }

    public LiveData<ArrayList<MoviesItem>> getSearchMovies() {
        return movieSearchData;
    }

}
