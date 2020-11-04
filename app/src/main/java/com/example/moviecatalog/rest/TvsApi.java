package com.example.moviecatalog.rest;

import com.example.moviecatalog.datamodel.TvsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TvsApi {
    @GET("3/discover/tv?api_key=756efb55af6523a917cb1265597e1ff1")
    Call<TvsResponse> getTvs(@Query("language")String language);

    @GET("3/search/tv?api_key=756efb55af6523a917cb1265597e1ff1")
    Call<TvsResponse> getSearchTv(@Query("language") String language,@Query("query") String query);

    @GET("3/discover/tv?api_key=756efb55af6523a917cb1265597e1ff1")
    Call<TvsResponse> getReleaseTv(@Query("primary_release_date.gte")String dateG,@Query("primary_release_date.lte")String dateL);
}
