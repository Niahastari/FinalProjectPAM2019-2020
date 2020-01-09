package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.Movies;

public interface OnGetDetailMovies {
    void onSuccess(Movies movie);

    void onError();
}
