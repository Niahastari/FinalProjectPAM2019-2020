package com.rickysn1007.moviecatalogue.interfaceCall;

import com.rickysn1007.moviecatalogue.model.TVshow;

import java.util.ArrayList;

public interface LoadTVshowCallback {
    void preExecute();

    void postExecute2(ArrayList<TVshow> tvs);
}
