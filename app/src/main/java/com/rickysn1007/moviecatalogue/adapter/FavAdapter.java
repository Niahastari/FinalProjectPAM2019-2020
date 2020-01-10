package com.rickysn1007.moviecatalogue.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.model.Movies;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;




public class FavAdapter extends RecyclerView.Adapter<FavAdapter.FavViewHolder> {

    private final ArrayList<Movies> movieList = new ArrayList<>();
    public final Activity activity;

    public FavAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setMovieList(ArrayList<Movies> movieList) {

        if (movieList.size() > 0) {
            this.movieList.clear();
        }
        this.movieList.addAll(movieList);

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_fav, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        holder.tvTitle.setText(movieList.get(position).getTitle());
        String poster = movieList.get(position).getPosterPathFav();
        Picasso.get().load(poster).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    class FavViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imgPhoto;
        FavViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            imgPhoto = itemView.findViewById(R.id.img_poster);
        }
    }
}

