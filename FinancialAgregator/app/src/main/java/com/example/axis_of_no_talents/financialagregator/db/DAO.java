package com.example.axis_of_no_talents.financialagregator.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import com.example.axis_of_no_talents.financialagregator.RssItem;
import static android.content.ContentValues.TAG;

public class DAO {
    private DBHelper dhelper;

    public DAO(Context c){
        dhelper = new DBHelper(c);
    }

    public void addItem(RssItem item) {
        SQLiteDatabase db = dhelper.getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(DBEntity.COLUMN_NAME_TITLE, item.getTitle());
            values.put(DBEntity.COLUMN_NAME_LINK, item.getLink());
            values.put(DBEntity.COLUMN_NAME_PUB_DATE, item.getPubDate());
            values.put(DBEntity.COLUMN_NAME_DESCRIPTION, item.getDescription());

            int rows = db.update(DBEntity.TABLE_NAME, values, DBEntity.COLUMN_NAME_TITLE + "= ?", new String[]{item.getTitle()});
            if(rows == 0) {
                db.insertOrThrow(DBEntity.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
    }
}
