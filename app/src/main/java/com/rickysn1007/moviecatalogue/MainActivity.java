package com.rickysn1007.moviecatalogue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.rickysn1007.moviecatalogue.activity.SettingActivity;
import com.rickysn1007.moviecatalogue.fragment.FavoriteFragment;
import com.rickysn1007.moviecatalogue.fragment.MoviesFragment;
import com.rickysn1007.moviecatalogue.fragment.TVshowFragment;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
        Fragment fragment;
        switch (item.getItemId()) {
            case R.id.navigation_movies:
                fragment = new MoviesFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                return true;

            case R.id.navigation_tvshow:
                fragment = new TVshowFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();

                return true;
            case R.id.navigation_favorite:
                fragment = new FavoriteFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();

                return true;
        }
        return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        if (savedInstanceState == null) {
            navView.setSelectedItemId(R.id.navigation_movies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings) {
            Intent mIntent = new Intent(MainActivity.this, SettingActivity.class);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}
