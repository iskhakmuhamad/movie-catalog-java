package com.example.moviecatalog.rest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesService {
    private Retrofit retrofit;
    private static final String BASE_URL = "https://api.themoviedb.org/";

    public MoviesApi getMoviesApi() {

        if (retrofit == null) {
            retrofit = new Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit.create(MoviesApi.class);
    }
}
