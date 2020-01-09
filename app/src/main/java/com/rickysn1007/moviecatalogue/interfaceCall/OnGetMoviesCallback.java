package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.Movies;

import java.util.ArrayList;

public interface OnGetMoviesCallback {
    void onSuccess(int page, ArrayList<Movies> movies);

    void onError();
}
