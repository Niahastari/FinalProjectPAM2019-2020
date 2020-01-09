package com.rickysn1007.moviecatalogue.api;

import com.rickysn1007.moviecatalogue.BuildConfig;
import com.rickysn1007.moviecatalogue.model.GenresResponse;
import com.rickysn1007.moviecatalogue.model.Movies;
import com.rickysn1007.moviecatalogue.model.MoviesResponse;
import com.rickysn1007.moviecatalogue.model.TVshow;
import com.rickysn1007.moviecatalogue.model.TVshowResponse;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetDetailMovies;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetDetailTVshow;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetGenresCallback;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetMoviesCallback;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetTVshowCallback;

import androidx.annotation.NonNull;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String LANGUAGE = "en-US";
    private static ApiClient apiClient;
    private ApiEndpoint apiEndpoint;

    private ApiClient(ApiEndpoint apiEndpoint) {
        this.apiEndpoint = apiEndpoint;
    }

    public static ApiClient getClient() {
        if (apiClient == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            apiClient = new ApiClient(retrofit.create(ApiEndpoint.class));
        }

        return apiClient;
    }

    public void getGenres(final OnGetGenresCallback callback) {
        apiEndpoint.getGenres(BuildConfig.MovieCatalogue_ApiKey, LANGUAGE)
                .enqueue(new Callback<GenresResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<GenresResponse> call, @NonNull Response<GenresResponse> response) {
                        if (response.isSuccessful()) {
                            GenresResponse genresResponse = response.body();
                            if (genresResponse != null && genresResponse.getGenres() != null) {
                                callback.onSuccess(genresResponse.getGenres());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<GenresResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getMovie(int movieId, final OnGetDetailMovies callback) {
        apiEndpoint.getMovie(movieId, BuildConfig.MovieCatalogue_ApiKey, LANGUAGE)
                .enqueue(new Callback<Movies>() {
                    @Override
                    public void onResponse(@NonNull Call<Movies> call, @NonNull Response<Movies> response) {
                        if (response.isSuccessful()) {
                            Movies movie = response.body();
                            if (movie != null) {
                                callback.onSuccess(movie);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Movies> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getMoviesDiscover(int page, final OnGetMoviesCallback callback) {
        apiEndpoint.getDiscoverMovies(BuildConfig.MovieCatalogue_ApiKey, LANGUAGE, page)
                .enqueue(new Callback<MoviesResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<MoviesResponse> call, @NonNull Response<MoviesResponse> response) {
                        if (response.isSuccessful()) {
                            MoviesResponse moviesResponse = response.body();
                            if (moviesResponse != null && moviesResponse.getMovies() != null) {
                                callback.onSuccess(moviesResponse.getPage(), moviesResponse.getMovies());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<MoviesResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTv(int tvId, final OnGetDetailTVshow callback) {
        apiEndpoint.getTv(tvId, BuildConfig.MovieCatalogue_ApiKey, LANGUAGE)
                .enqueue(new Callback<TVshow>() {
                    @Override
                    public void onResponse(@NonNull Call<TVshow> call, @NonNull Response<TVshow> response) {
                        if (response.isSuccessful()) {
                            TVshow tv = response.body();
                            if (tv != null) {
                                callback.onSuccess(tv);
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TVshow> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

    public void getTvDiscover(int page, final OnGetTVshowCallback callback) {
        apiEndpoint.getDiscoverTV(BuildConfig.MovieCatalogue_ApiKey, LANGUAGE, page)
                .enqueue(new Callback<TVshowResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TVshowResponse> call, @NonNull Response<TVshowResponse> tvresponse) {
                        if (tvresponse.isSuccessful()) {
                            TVshowResponse tvResponse = tvresponse.body();
                            if (tvResponse != null && tvResponse.getTvs() != null) {
                                callback.onSuccess(tvResponse.getPage(), tvResponse.getTvs());
                            } else {
                                callback.onError();
                            }
                        } else {
                            callback.onError();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TVshowResponse> call, @NonNull Throwable t) {
                        callback.onError();
                    }
                });
    }

}
