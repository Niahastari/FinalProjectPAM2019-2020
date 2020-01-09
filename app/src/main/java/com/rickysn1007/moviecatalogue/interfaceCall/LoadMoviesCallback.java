package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.Movies;

import java.util.ArrayList;

public interface LoadMoviesCallback {
    void preExecute();

    void postExecute(ArrayList<Movies> movies);
}
