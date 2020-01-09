package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.Genres;

import java.util.List;

public interface OnGetGenresCallback {
    void onSuccess(List<Genres> genres);

    void onError();
}
