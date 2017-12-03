package com.example.axis_of_no_talents.financialagregator.db;

/**
 * Created by Kamil on 03.12.2017.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DBHelper extends SQLiteOpenHelper {
    public static final String TAG = "DBHelper";



    private static DBHelper instance;

    public static synchronized DBHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DBHelper(context.getApplicationContext());
        }

        return instance;
    }

    public static final String DATABASE_NAME = "RssFeeder.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBEntity.TABLE_NAME + " (" +
                    DBEntity._ID + " INTEGER PRIMARY KEY," +
                    DBEntity.COLUMN_NAME_TITLE + " TEXT, " +
                    DBEntity.COLUMN_NAME_LINK + " TEXT, " +
                    DBEntity.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    DBEntity.COLUMN_NAME_PUB_DATE + " TEXT)";


    private DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DBEntity.TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
