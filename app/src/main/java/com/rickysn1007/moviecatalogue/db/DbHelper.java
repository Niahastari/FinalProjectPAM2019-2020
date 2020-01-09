package com.rickysn1007.moviecatalogue.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {
    private static String DATABASE_NAME = "dbmoviecatalog";

    private static final int DATABASE_VERSION = 1;
    DbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    private static final String SQL_CREATE_TABLE_MOVIE =
            String.format("CREATE TABLE %s" +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL)",
                    DbContract.FavoriteMovies.TABLE_NAME,
                    DbContract.FavoriteMovies._ID,
                    DbContract.FavoriteMovies.COLUMN_MOVIEID,
                    DbContract.FavoriteMovies.COLUMN_TITLE,
                    DbContract.FavoriteMovies.COLUMN_REALISE,
                    DbContract.FavoriteMovies.COLUMN_BACKDROP_PATH,
                    DbContract.FavoriteMovies.COLUMN_POSTER_PATH,
                    DbContract.FavoriteMovies.COLUMN_USERRATING,
                    DbContract.FavoriteMovies.COLUMN_VOTER,
                    DbContract.FavoriteMovies.COLUMN_OVERVIEW
            );
    private static final String SQL_CREATE_TABLE_TV =
            String.format("CREATE TABLE %s" +
                            "(%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s TEXT NOT NULL," +
                            " %s INTEGER," +
                            " %s TEXT NOT NULL)",
                    DbContract.FavoriteTVshow.TABLE_NAME,
                    DbContract.FavoriteTVshow._ID,
                    DbContract.FavoriteTVshow.COLUMN_MOVIEID,
                    DbContract.FavoriteTVshow.COLUMN_TITLE,
                    DbContract.FavoriteTVshow.COLUMN_FIRST_REALISE,
                    DbContract.FavoriteTVshow.COLUMN_BACKDROP_PATH,
                    DbContract.FavoriteTVshow.COLUMN_POSTER_PATH,
                    DbContract.FavoriteTVshow.COLUMN_USERRATING,
                    DbContract.FavoriteTVshow.COLUMN_VOTER,
                    DbContract.FavoriteTVshow.COLUMN_OVERVIEW
            );
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_TV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.FavoriteMovies.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DbContract.FavoriteTVshow.TABLE_NAME);
        onCreate(db);
    }
}
