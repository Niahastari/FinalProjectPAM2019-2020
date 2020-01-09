package com.rickysn1007.moviecatalogue.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.activity.TVShowDetailActivity;
import com.rickysn1007.moviecatalogue.adapter.TvAdapter;
import com.rickysn1007.moviecatalogue.api.ApiClient;
import com.rickysn1007.moviecatalogue.model.TVshow;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetTVshowCallback;
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
public class TVshowFragment extends Fragment {

    private final static String LIST_STATE_KEY = "STATE";
    private ArrayList<TVshow> tvArrayList = new ArrayList<>();
    private ApiClient apiClient;
    private RecyclerView recyclerView;
    private boolean isFetchingMovies;
    private int currentPage = 1;
    private TvAdapter adapter;
    private ProgressBar progressBar;
    private final GridLayoutManager manager = new GridLayoutManager(getActivity(), 2);

    public TVshowFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshow, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        apiClient = ApiClient.getClient();
        progressBar = view.findViewById(R.id.progressBar);
        recyclerView = view.findViewById(R.id.rv_discover_tv);
        getTVs(currentPage);
        setupOnScrollListener();
        if (savedInstanceState != null) {
            progressBar.setVisibility(View.INVISIBLE);
            final ArrayList<TVshow> tvState = savedInstanceState.getParcelableArrayList(LIST_STATE_KEY);
            assert tvState != null;
            tvArrayList.addAll(tvState);
            adapter = new TvAdapter(getActivity());
            adapter.setTvList(tvState);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(manager);
            ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
                Intent intent = new Intent(getActivity(), TVShowDetailActivity.class);
                intent.putExtra(TVShowDetailActivity.EXTRA_TV, tvState.get(position));
                startActivity(intent);
            });
        } else {
            getTVs(currentPage);
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
                        getTVs(currentPage + 1);
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LIST_STATE_KEY,tvArrayList);
    }

    private void getTVs(int page) {
        isFetchingMovies = true;
        apiClient.getTvDiscover(page, new OnGetTVshowCallback() {
            @Override
            public void onSuccess(int page, ArrayList<TVshow> tv) {
                progressBar.setVisibility(View.INVISIBLE);
                adapter = new TvAdapter(getActivity());
                adapter.setTvList(tv);
                tvArrayList.addAll(tv);
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(manager);
                ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
                    Intent intent = new Intent(getActivity(), TVShowDetailActivity.class);
                    intent.putExtra(TVShowDetailActivity.EXTRA_TV, tv.get(position));
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