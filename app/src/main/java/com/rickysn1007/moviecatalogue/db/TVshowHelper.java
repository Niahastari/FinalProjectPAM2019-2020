package com.rickysn1007.moviecatalogue.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.rickysn1007.moviecatalogue.model.TVshow;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static androidx.constraintlayout.widget.Constraints.TAG;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_BACKDROP_PATH;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_FIRST_REALISE;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_MOVIEID;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_OVERVIEW;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_POSTER_PATH;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_TITLE;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_USERRATING;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.COLUMN_VOTER;
import static com.rickysn1007.moviecatalogue.db.DbContract.FavoriteTVshow.TABLE_NAME;

public class TVshowHelper {
    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DbHelper dataBaseHelper;
    private static TVshowHelper INSTANCE;
    private static SQLiteDatabase database;

    private TVshowHelper(Context context) {
        dataBaseHelper = new DbHelper(context);
    }

    public static TVshowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new TVshowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }

   /** public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }**/

    public ArrayList<TVshow> getAllTv() {
        ArrayList<TVshow> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TVshow tv;
        if (cursor.getCount() > 0) {
            do {
                tv = new TVshow();
                tv.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_MOVIEID))));
                tv.setName(cursor.getString(cursor.getColumnIndex(COLUMN_TITLE)));
                tv.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_USERRATING))));
                tv.setPosterPath(cursor.getString(cursor.getColumnIndex(COLUMN_POSTER_PATH)));
                tv.setBackdropPath(cursor.getString(cursor.getColumnIndex(COLUMN_BACKDROP_PATH)));
                tv.setOverview(cursor.getString(cursor.getColumnIndex(COLUMN_OVERVIEW)));
                tv.setVoteCount(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_VOTER))));
                tv.setFirstAirDate(cursor.getString(cursor.getColumnIndex(COLUMN_FIRST_REALISE)));

                arrayList.add(tv);

                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TVshow tv) {
        ContentValues args = new ContentValues();
        args.put(COLUMN_MOVIEID, tv.getId());
        args.put(COLUMN_TITLE, tv.getName());
        args.put(COLUMN_OVERVIEW, tv.getOverview());
        args.put(COLUMN_BACKDROP_PATH, tv.getBackdropPath());
        args.put(COLUMN_POSTER_PATH, tv.getPosterPath());
        args.put(COLUMN_USERRATING, tv.getVoteAverage());
        args.put(COLUMN_VOTER, tv.getVoteCount());
        args.put(COLUMN_FIRST_REALISE, tv.getFirstAirDate());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public void deleteTv(int id) {
        database = dataBaseHelper.getWritableDatabase();
        database.delete(TABLE_NAME, COLUMN_MOVIEID + "=" + id, null);
    }

    public boolean checkTv(String id) {
        database = dataBaseHelper.getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_MOVIEID + " =?";
        Cursor cursor = database.rawQuery(selectString, new String[]{id});
        boolean checkTv = false;
        if (cursor.moveToFirst()) {
            checkTv = true;
            int count = 0;
            while (cursor.moveToNext()) {
                count++;
            }
            Log.d(TAG, String.format("%d records found", count));
        }
        cursor.close();
        return checkTv;
    }
}
