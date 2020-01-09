package com.rickysn1007.moviecatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rickysn1007.moviecatalogue.MainActivity;
import com.rickysn1007.moviecatalogue.R;

public class SplashScreenActivity extends AppCompatActivity {

    ImageView imgSplash;
    TextView tvText1, tvText2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        tvText1 = findViewById(R.id.tvviewsplash);
        tvText2 = findViewById(R.id.tvdescsplash);
        imgSplash= findViewById(R.id.imgSplashscreen);
        Glide.with(this)
                .load(R.drawable.logo)
                .apply(RequestOptions.circleCropTransform())
                .into(imgSplash);

        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
            finish();
        },2000);
    }
}
