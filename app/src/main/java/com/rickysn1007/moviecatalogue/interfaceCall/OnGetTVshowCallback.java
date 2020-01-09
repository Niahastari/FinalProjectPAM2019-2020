package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.TVshow;

import java.util.ArrayList;

public interface OnGetTVshowCallback {
    void onSuccess(int page, ArrayList<TVshow> tvs);

    void onError();
}
