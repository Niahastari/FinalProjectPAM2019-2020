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

public class TvAdapter extends RecyclerView.Adapter<TvAdapter.MyViewHolder2> {

    private ArrayList<TVshow> tvList;

    public final Activity activity;

    public  TvAdapter(Activity activity) {
        this.activity = activity;
    }

    public void setTvList(ArrayList<TVshow> tvList) {
        this.tvList = tvList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TvAdapter.MyViewHolder2 onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_cardview, viewGroup, false);

        return new MyViewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TvAdapter.MyViewHolder2 holder, int i) {
        holder.tvTitle2.setText(tvList.get(i).getName());
        String poster = tvList.get(i).getPosterPath();
        Picasso.get().load(poster).placeholder(R.drawable.no_image).into(holder.imgPhoto2);
    }

    @Override
    public int getItemCount() {
        return tvList.size();
    }

    class MyViewHolder2 extends RecyclerView.ViewHolder {
        TextView tvTitle2;
        ImageView imgPhoto2;

        MyViewHolder2(View view) {
            super(view);

            tvTitle2 = itemView.findViewById(R.id.tv_title);
            imgPhoto2 = itemView.findViewById(R.id.img_poster);

        }
    }
}
