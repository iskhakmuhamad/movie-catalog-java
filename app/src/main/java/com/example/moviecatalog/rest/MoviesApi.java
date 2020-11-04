package com.example.moviecatalog.rest;

import com.example.moviecatalog.datamodel.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesApi {
    @GET("3/discover/movie?api_key=756efb55af6523a917cb1265597e1ff1")
    Call<MoviesResponse> getMovies(@Query("language") String language);

    @GET("3/search/movie?api_key=756efb55af6523a917cb1265597e1ff1")
    Call<MoviesResponse> getSearchMovies(@Query("language") String language,@Query("query") String query);

    @GET("3/discover/movie?api_key=756efb55af6523a917cb1265597e1ff1")
    Call<MoviesResponse> getMoviesRelease(@Query("primary_release_date.gte")String dateG,@Query("primary_release_date.lte")String dateL);
}
