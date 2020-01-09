package com.rickysn1007.moviecatalogue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.activity.MoviesDetailActivity;
import com.rickysn1007.moviecatalogue.adapter.MovieAdapter;
import com.rickysn1007.moviecatalogue.api.ApiClient;
import com.rickysn1007.moviecatalogue.model.Movies;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetMoviesCallback;
import com.rickysn1007.moviecatalogue.ItemClickSupport;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;

import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private final static String LIST_STATE_KEY = "STATE";
    private ArrayList<Movies> movieArrayList = new ArrayList<>();
    private ApiClient apiClient;
    private RecyclerView recyclerView;
    private boolean isFetchingMovies;
    private int currentPage = 1;
    private MovieAdapter adapter;
    private ProgressBar progressBar;
    private final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiClient = ApiClient.getClient();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rv_discover_movie);
        getMovies(currentPage);
        setupOnScrollListener();
        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            final ArrayList<Movies> moviesState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert moviesState != null;
            movieArrayList.addAll(moviesState);
            adapter = new MovieAdapter(getActivity());
            adapter.setMovieList(moviesState);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
                intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, moviesState.get(position));
                startActivity(intent);
            });
        } else {
            getMovies(currentPage);
            setupOnScrollListener();
        }
    }

    private void setupOnScrollListener() {
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();
                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies) {
                        progressBar.setVisibility(View.INVISIBLE);
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY, movieArrayList);
    }

    private void getMovies(int page) {
        isFetchingMovies = true;
        apiClient.getMoviesDiscover(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, ArrayList<Movies> movies) {
                    progressBar.setVisibility(View.INVISIBLE);
                    adapter = new MovieAdapter(getActivity());
                    adapter.setMovieList(movies);
                    movieArrayList.addAll(movies);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(manager);
                    ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
                        Intent intent = new Intent(getActivity(), MoviesDetailActivity.class);
                        intent.putExtra(MoviesDetailActivity.EXTRA_MOVIE, movies.get(position));
                        startActivity(intent);
                    });
            }
                @Override
                public void onError() {
                    String toast_msg = getString(R.string.toastmsg);
                    Toast.makeText(getActivity(), toast_msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
