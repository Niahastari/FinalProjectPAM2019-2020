package com.rickysn1007.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rickysn1007.moviecatalogue.model.Movies;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_BACKDROP_PATH;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_MOVIEID;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_OVERVIEW;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_POSTER_PATH;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_REALISE;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_TITLE;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_USERRATING;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.COLUMN_VOTER;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteMovies.TABLE_NAME;

public class MoviesHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DbHelper dataBaseHelper;
    private static MoviesHelper INSTANCE;
    private static SQLiteDatabase database;

    private MoviesHelper(Context context) {
        dataBaseHelper = new DbHelper(context);
    }

    public static MoviesHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new MoviesHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

    /**public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }**/

    public ArrayList<Movies> getAllMovie() {
        ArrayList<Movies> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movies movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movies();
                movie.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                movie.setTitle(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_USERRATING))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                movie.setBackdropPath(cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP_PATH)));
                movie.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
                movie.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_VOTER))));
                movie.setReleaseDate(cursor.getString(cursor.getColumnIndex(COLUMN_REALISE)));

                arrayList.add(movie);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(Movies movie) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_MOVIEID, movie.getId());
        args.put(COLUMN_TITLE, movie.getTitle());
        args.put(COLUMN_OVERVIEW, movie.getOverview());
        args.put(COLUMN_BACKDROP_PATH, movie.getBackdropPath());
        args.put(COLUMN_POSTER_PATH, movie.getPosterPath());
        args.put(COLUMN_USERRATING, movie.getVoteAverage());
        args.put(COLUMN_VOTER, movie.getVoteCount());
        args.put(COLUMN_REALISE, movie.getReleaseDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public void deleteMovie(int id) {
        database = dataBaseHelper.getWritableDatabase();
        database.delete(DbContract.FavoriteMovies.TABLE_NAME, DbContract.FavoriteMovies.COLUMN_MOVIEID + "=" + id, null);
    }

    public boolean checkMovie(String id) {
        database = dataBaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + DbContract.FavoriteMovies.TABLE_NAME + " WHERE " + DbContract.FavoriteMovies.COLUMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkMovie = false;
        if (cursor.moveToFirst()) {
            checkMovie = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkMovie;
    }
}
