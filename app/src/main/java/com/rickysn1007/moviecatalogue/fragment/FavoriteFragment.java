package com.rickysn1007.moviecatalogue.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.activity.MoviesDetailActivity;
import com.rickysn1007.moviecatalogue.activity.TVShowDetailActivity;
import com.rickysn1007.moviecatalogue.adapter.FavAdapter;
import com.rickysn1007.moviecatalogue.adapter.FavTvAdapter;
import com.rickysn1007.moviecatalogue.db.MoviesHelper;
import com.rickysn1007.moviecatalogue.db.TVshowHelper;
import com.rickysn1007.moviecatalogue.model.Movies;
import com.rickysn1007.moviecatalogue.model.TVshow;
import com.rickysn1007.moviecatalogue.ItemClickSupport;
import com.rickysn1007.moviecatalogue.interfaceCall.LoadMoviesCallback;
import com.rickysn1007.moviecatalogue.interfaceCall.LoadTVshowCallback;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavoriteFragment extends Fragment implements LoadMoviesCallback, LoadTVshowCallback {
    private RecyclerView rvMovie, rvTv;
    private FavAdapter favAdapter;
    private FavTvAdapter favTvAdapter;
    private final static String LIST_STATE_KEY = "STATE";
    private final static String LIST_STATE_KEY2 = "STATE2";
    private ArrayList<Movies> movieArrayList = new ArrayList<>();
    private ArrayList<TVshow> tvArrayList = new ArrayList<>();

    public FavoriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvMovie = view.findViewById(R.id.rv_fav_movie);
        rvMovie.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMovie.setHasFixedSize(true);

        rvTv = view.findViewById(R.id.rv_fav_tv);
        rvTv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTv.setHasFixedSize(true);

        MoviesHelper movieHelper = MoviesHelper.getInstance(getActivity());
        movieHelper.open();

        TVshowHelper tvHelper = TVshowHelper.getInstance(getActivity());
        tvHelper.open();

        favAdapter = new FavAdapter(getActivity());
        rvMovie.setAdapter(favAdapter);

        favTvAdapter = new FavTvAdapter(getActivity());
        rvTv.setAdapter(favTvAdapter);

        if (savedInstanceState == null) {
            new LoadMoviesAsync(movieHelper, this).execute();
            new LoadTvAsync(tvHelper, this).execute();
        } else {
            final ArrayList<Movies> moviesState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert moviesState != null;
            movieArrayList.addAll(moviesState);
            favAdapter.setMovieList(moviesState);
            ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
                intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, moviesState.get(position));
                startActivity(intent);
            });
            final ArrayList<TVshow> tvState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY2);
            assert tvState != null;
            tvArrayList.addAll(tvState);
            favTvAdapter.setTvList(tvState);
            ItemClickSupport.addTo(rvTv).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getActivity(), TVShowDetailActivity.class);
                intent.putExtra(TVShowDetailActivity.EXTRA_TV, tvState.get(position));
                startActivity(intent);
            });
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, movieArrayList);
        outState.putParcelableArrayList(LIST_STATE_KEY2, tvArrayList);
    }

    @Override
    public void preExecute() {

    }

    @Override
    public void postExecute2(ArrayList<TVshow> tvs) {
        favTvAdapter.setTvList(tvs);
        rvTv.setAdapter(favTvAdapter);
        tvArrayList.addAll(tvs);
        ItemClickSupport.addTo(rvTv).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(getActivity(), TVShowDetailActivity.class);
            intent.putExtra(TVShowDetailActivity.EXTRA_TV, tvs.get(position));
            startActivity(intent);
        });
    }


    @Override
    public void postExecute(ArrayList<Movies> movies) {
        favAdapter.setMovieList(movies);
        rvMovie.setAdapter(favAdapter);
        movieArrayList.addAll(movies);
        ItemClickSupport.addTo(rvMovie).setOnItemClickListener((recyclerView, position, v) -> {
            Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
            intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movies.get(position));
            startActivity(intent);
        });
    }

    private static class LoadMoviesAsync extends AsyncTask<Void, Void, ArrayList<Movies>> {
        private final WeakReference<MoviesHelper> weakMovieHelper;
        private final WeakReference<LoadMoviesCallback> weakCallback;

        private LoadMoviesAsync(MoviesHelper movieHelper, LoadMoviesCallback callback) {
            weakMovieHelper = new WeakReference<>(movieHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<Movies> doInBackground(Void... voids) {
            return weakMovieHelper.get().getAllMovie();
        }


        @Override
        protected void onPostExecute(ArrayList<Movies> movies) {
            super.onPostExecute(movies);
            weakCallback.get().postExecute(movies);
        }
    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, ArrayList<TVshow>> {
        private final WeakReference<TVshowHelper> weakTvHelper;
        private final WeakReference<LoadTVshowCallback> weakCallback;

        private LoadTvAsync(TVshowHelper tvHelper, LoadTVshowCallback callback) {
            weakTvHelper = new WeakReference<>(tvHelper);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected ArrayList<TVshow> doInBackground(Void... voids) {
            return weakTvHelper.get().getAllTv();
        }


        @Override
        protected void onPostExecute(ArrayList<TVshow> tvs) {
            super.onPostExecute(tvs);
            weakCallback.get().postExecute2(tvs);
        }
    }
}
