package com.rickysn1007.moviecatalogue.api;

import com.rickysn1007.moviecatalogue.model.GenresResponse;
import com.rickysn1007.moviecatalogue.model.Movies;
import com.rickysn1007.moviecatalogue.model.MoviesResponse;
import com.rickysn1007.moviecatalogue.model.TVshow;
import com.rickysn1007.moviecatalogue.model.TVshowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiEndpoint {
    @GET("discover/movie")
    Call<MoviesResponse> getDiscoverMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("discover/tv")
    Call<TVshowResponse> getDiscoverTV(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int page
    );

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key") String apiKey,
            @Query("language") String language
    );

    @GET("movie/{movie_id}")
    Call<Movies> getMovie(
            @Path("movie_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );

    @GET("tv/{tv_id}")
    Call<TVshow> getTv(
            @Path("tv_id") int id,
            @Query("api_key") String apiKEy,
            @Query("language") String language
    );
}
