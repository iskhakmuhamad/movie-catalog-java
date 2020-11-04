package com.example.moviecatalog.viewmodel;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.moviecatalog.datamodel.TvsItem;
import com.example.moviecatalog.datamodel.TvsResponse;
import com.example.moviecatalog.rest.TvsService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchTvViewModel extends ViewModel {
    private TvsService tvsService;
    private MutableLiveData<ArrayList<TvsItem>> tvSearchData = new MutableLiveData<>();

    public void setTvSearchData(String lang, String query) {

        if (tvsService == null) {
            tvsService = new TvsService();
        }

        tvsService.getTvsApi().getSearchTv(lang, query).enqueue(new Callback<TvsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvsResponse> call, @NonNull Response<TvsResponse> response) {
                TvsResponse tvsResponse = response.body();
                if (tvsResponse != null && tvsResponse.getResults() != null) {
                    ArrayList<TvsItem> moviesItems = new ArrayList<>();
                    for (int i = 0; i < tvsResponse.getResults().size(); i++) {
                        if (!tvsResponse.getResults().get(i).getOverview().equals("")) {
                            moviesItems.add(tvsResponse.getResults().get(i));
                        }
                    }
                    Log.d("SearchTv view Model", "onResponse: succes get " + moviesItems);
                    tvSearchData.postValue(moviesItems);
                }
            }

            @Override
            public void onFailure(@NonNull Call<TvsResponse> call, @NonNull Throwable t) {
                Log.d("SearchTv view Model", "onFailure : because " + t.getMessage());
            }
        });

    }

    public LiveData<ArrayList<TvsItem>> getSearchTv() {
        return tvSearchData;
    }

}
