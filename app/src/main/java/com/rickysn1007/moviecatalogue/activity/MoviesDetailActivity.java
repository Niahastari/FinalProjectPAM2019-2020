package com.rickysn1007.moviecatalogue.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.rickysn1007.moviecatalogue.R;
import com.rickysn1007.moviecatalogue.api.ApiClient;
import com.rickysn1007.moviecatalogue.db.MoviesHelper;
import com.rickysn1007.moviecatalogue.model.Genres;
import com.rickysn1007.moviecatalogue.model.Movies;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetDetailMovies;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetGenresCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class MoviesDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String EXTRA_MOVIE = "extra_movie";

    public int movieId;
    private ProgressBar progressBar;
    private ImageView imgBanner, imgPoster;
    private String banner, poster, voteCount;
    private TextView tvTitle, tvOverview, tvRealise, tvGenre, tvRating, tvVoter, tvRealiseYear, tvTitleDesc;
    private ApiClient apiClient;
    private MoviesHelper movieHelper;
    private ImageButton btnFav, btnDel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        String idMovie = Integer.toString(selectedMovie.getId());
        movieHelper = MoviesHelper.getInstance(getApplicationContext());
        movieHelper.open();
        setContentView(R.layout.activity_detail);
        btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        if (movieHelper.checkMovie(idMovie)) {
            btnFav.setVisibility(View.GONE);
            btnDel.setVisibility(View.VISIBLE);
        }
        getMovie();
    }

    private void getMovie() {
        Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
        movieId = selectedMovie.getId();
        apiClient = ApiClient.getClient();
        String reviewer = getString(R.string.reviewer);
        apiClient.getMovie(movieId, new OnGetDetailMovies() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(Movies movie) {
                getGenres(movie);
                poster = movie.getPosterPath();
                tvGenre = findViewById(R.id.tv_genres_text);
                imgBanner = findViewById(R.id.img_backdrop);
                imgPoster = findViewById(R.id.img_poster);
                tvTitle = findViewById(R.id.tv_title);
                tvTitleDesc = findViewById(R.id.tv_title_text);
                tvTitleDesc.setText(movie.getTitle());
                banner = movie.getBackdropPath();
                tvTitle.setText(movie.getTitle());
                tvOverview = findViewById(R.id.tv_overview_text);
                tvOverview.setText(movie.getOverview());
                tvRating = findViewById(R.id.tv_rating);
                tvRating.setText(String.valueOf(movie.getVoteAverage()));
                tvVoter = findViewById(R.id.tv_voter);
                voteCount = Integer.toString(movie.getVoteCount());
                tvVoter.setText(voteCount + " " + reviewer);
                tvRealise = findViewById(R.id.tv_realease_text);
                tvRealiseYear = findViewById(R.id.tv_realease_year);
                tvRealiseYear.setText(movie.getReleaseDate().split("-")[0]);
                tvRealise.setText(movie.getReleaseDate());

                if (!isFinishing()) {
                    Picasso.get().load(poster).placeholder(R.drawable.no_image).into(imgPoster);
                    Picasso.get().load(banner).placeholder(R.drawable.no_image).into(imgBanner);
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getGenres(final Movies movie) {
        apiClient.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genres> genres) {
                if (movie.getGenres() != null) {
                    List<String> currentGenres = new ArrayList<>();
                    for (Genres genre : movie.getGenres()) {
                        currentGenres.add(genre.getName());
                    }
                    tvGenre.setText(TextUtils.join(", ", currentGenres));
                }
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }


    private void showError() {
        String toast_msg = getString(R.string.toastmsg);
        Toast.makeText(MoviesDetailActivity.this, toast_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFav) {
            Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String toastFav = getString(R.string.toastFav);
            String toastFavFail = getString(R.string.toastFavFail);
            long result = movieHelper.insertMovie(selectedMovie);
            if (result > 0) {
                btnFav.setVisibility(View.GONE);
                btnDel.setVisibility(View.VISIBLE);
                Toast.makeText(MoviesDetailActivity.this, toastFav, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MoviesDetailActivity.this, toastFavFail, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDel) {
            Movies selectedMovie = getIntent().getParcelableExtra(EXTRA_MOVIE);
            String toastDel = getString(R.string.toastDel);
            movieHelper.deleteMovie(selectedMovie.getId());
            Toast.makeText(MoviesDetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
            btnFav.setVisibility(View.VISIBLE);
            btnDel.setVisibility(View.GONE);
        }
    }
}
