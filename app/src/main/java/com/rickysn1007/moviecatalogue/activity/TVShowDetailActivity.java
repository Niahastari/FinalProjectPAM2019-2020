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
import com.rickysn1007.moviecatalogue.db.TVshowHelper;
import com.rickysn1007.moviecatalogue.model.Genres;
import com.rickysn1007.moviecatalogue.model.TVshow;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetDetailTVshow;
import com.rickysn1007.moviecatalogue.interfaceCall.OnGetGenresCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class TVShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TV = "extra_tv";
    public int tvId;
    private ProgressBar progressBar;
    private ImageView imgBanner, imgPoster;
    private String banner, poster, voteCount;
    private TextView tvTitle, tvOverview, tvRealiseFirst, tvGenre, tvRating, tvVoter, tvRealiseYear, tvTitleDesc;
    private ApiClient apiClient;
    private ImageButton btnFav, btnDel;
    private TVshowHelper tvHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TVshow selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
        String idTv = Integer.toString(selectedTv.getId());
        tvHelper = TVshowHelper.getInstance(getApplicationContext());
        tvHelper.open();
        setContentView(R.layout.activity_detail);
        btnFav = findViewById(R.id.btnFav);
        btnFav.setOnClickListener(this);
        btnDel = findViewById(R.id.btnDel);
        btnDel.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        if (tvHelper.checkTv(idTv)) {
            btnFav.setVisibility(View.GONE);
            btnDel.setVisibility(View.VISIBLE);
        }
        getTv();
    }

    private void getTv() {
        TVshow selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
        tvId = selectedTv.getId();
        apiClient = ApiClient.getClient();
        String reviewer = getString(R.string.reviewer);
        apiClient.getTv(tvId, new OnGetDetailTVshow() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onSuccess(TVshow tv) {
                getGenres2(tv);
                poster = tv.getPosterPath();
                tvGenre = findViewById(R.id.tv_genres_text);
                imgBanner = findViewById(R.id.img_backdrop);
                imgPoster = findViewById(R.id.img_poster);
                tvTitle = findViewById(R.id.tv_title);
                tvTitleDesc = findViewById(R.id.tv_title_text);
                tvTitleDesc.setText(tv.getName());
                banner = tv.getBackdropPath();
                tvTitle.setText(tv.getName());
                tvOverview = findViewById(R.id.tv_overview_text);
                tvOverview.setText(tv.getOverview());
                tvVoter = findViewById(R.id.tv_voter);
                voteCount = Integer.toString(tv.getVoteCount());
                tvVoter.setText(voteCount + " " + reviewer);
                tvRating = findViewById(R.id.tv_rating);
                tvRating.setText(String.valueOf(tv.getVoteAverage()));
                tvRealiseYear = findViewById(R.id.tv_realease_year);
                tvRealiseYear.setText(tv.getFirstAirDate().split("-")[0]);
                tvRealiseFirst = findViewById(R.id.tv_realease_text);
                tvRealiseFirst.setText(tv.getFirstAirDate());

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

    private void getGenres2(final TVshow tv) {
        apiClient.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genres> genres) {
                if (tv.getGenres() != null) {
                    List<String> currentGenres = new ArrayList<>();
                    for (Genres genre : tv.getGenres()) {
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
        Toast.makeText(TVShowDetailActivity.this, toast_msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btnFav) {
            TVshow selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
            String toastFav = getString(R.string.toastFav);
            String toastFavFail = getString(R.string.toastFavFail);
            long result = tvHelper.insertTv(selectedTv);
            if (result > 0) {
                btnFav.setVisibility(View.GONE);
                btnDel.setVisibility(View.VISIBLE);
                Toast.makeText(TVShowDetailActivity.this, toastFav, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(TVShowDetailActivity.this, toastFavFail, Toast.LENGTH_SHORT).show();
            }
        } else if (v.getId() == R.id.btnDel) {
            TVshow selectedTv = getIntent().getParcelableExtra(EXTRA_TV);
            String toastDel = getString(R.string.toastDel);
            tvHelper.deleteTv(selectedTv.getId());
            Toast.makeText(TVShowDetailActivity.this, toastDel, Toast.LENGTH_SHORT).show();
            btnFav.setVisibility(View.VISIBLE);
            btnDel.setVisibility(View.GONE);
        }
    }
}
