package com.rickysn1007.moviecatalogue.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.rickysn1007.moviecatalogue.R;

public class AboutActivity extends AppCompatActivity {

    ImageView imgRicky, imgAdnan, imgNia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        imgRicky = findViewById(R.id.imgRicky);
        imgAdnan = findViewById(R.id.imgAdnan);
        imgNia = findViewById(R.id.imgNia);

        Glide.with(this)
                .load(R.drawable.img_ricky)
                .apply(RequestOptions.circleCropTransform())
                .into(imgRicky);

        Glide.with(this)
                .load(R.drawable.img_adnan)
                .apply(RequestOptions.circleCropTransform())
                .into(imgAdnan);

        Glide.with(this)
                .load(R.drawable.img_nia)
                .apply(RequestOptions.circleCropTransform())
                .into(imgNia);
    }
}
