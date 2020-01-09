package com.rickysn1007.moviecatalogue.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class GenresResponse {

    @SerializedName("genres")
    @Expose
    private ArrayList<Genres> genres;

    public ArrayList<Genres> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genres> genres) {
        this.genres = genres;
    }
}
