package com.rickysn1007.moviecatalogue.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.model.TVshow;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class FavTvAdapter extends RecyclerView.Adapter<FavTvAdapter.FavViewHolder> {

    private final ArrayList<TVshow> tvList = new ArrayList<>();
    public final Activity activity;

    public FavTvAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvList(ArrayList<TVshow> tvList) {

        if (tvList.size() > 0) {
            this.tvList.clear();
        }
        this.tvList.addAll(tvList);

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
        holder.tvTitle.setText(tvList.get(position).getName());
        String poster = tvList.get(position).getPosterPathFav();
        Picasso.get().load(poster).into(holder.imgPhoto);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
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
