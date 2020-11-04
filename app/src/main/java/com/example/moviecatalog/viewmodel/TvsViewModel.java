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


public class TvsViewModel extends ViewModel {

    private TvsService tvsService;
    private MutableLiveData<ArrayList<TvsItem>> tvListData = new MutableLiveData<>();

    public void setTvs(String language) {

        if (tvsService == null) {
            tvsService = new TvsService();
        }

        tvsService.getTvsApi().getTvs(language).enqueue(new Callback<TvsResponse>() {
            @Override
            public void onResponse(@NonNull Call<TvsResponse> call, @NonNull Response<TvsResponse> response) {
                TvsResponse tvsResponse = response.body();
                if (tvsResponse != null && tvsResponse.getResults() != null) {
                    ArrayList<TvsItem> tvsItems = new ArrayList<>();
                    for (int i = 0; i < tvsResponse.getResults().size(); i++) {
                        if (!tvsResponse.getResults().get(i).getOverview().equals("")) {
                            tvsItems.add(tvsResponse.getResults().get(i));
                        }
                    }
                    Log.d("Tvs view Model", "onResponse: succes get " + tvsItems);
                    tvListData.postValue(tvsItems);
                }

            }

            @Override
            public void onFailure(@NonNull Call<TvsResponse> call, @NonNull Throwable t) {
                Log.d("Tvs view Model", "onFailure : because " + t.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TvsItem>> getTvs() {
        return tvListData;
    }
}
