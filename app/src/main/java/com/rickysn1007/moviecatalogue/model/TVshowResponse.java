package com.rickysn1007.moviecatalogue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class TVshowResponse {

    @SerializedName("page")
    @Expose
    private int page;
    @SerializedName("results")
    @Expose
    private ArrayList<TVshow> tvs = null;

    public ArrayList<TVshow> getTvs() {
        return tvs;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
