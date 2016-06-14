package com.example.alsultan.soultomusica;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 FOR THE SAKE OF USING_YOUTBE
 * Representation of DAO(Data access object) pattern
 */

interface Columns {
    public static final String DB_NAME = "parser.sqlite3";
    public static final String TABLE_NAME = "entries";
    public static final int DB_VERSION = 1;
    public static final String KEY_ID = "_id";
    public static final int ID_COLUMN = 0;
    public static final String KEY_ENTRY = "entry";
    public static final int ENTRY_COLUMN = 1;
    public static final String KEY_LINK = "link";
    public static final int LINK_COLUMN = 2;
    public static final String KEY_ISYOUTUBE = "isyoutube";
    public static final int ISYOUTUBE_COLUMN = 3;
}

public class EntryDao implements Columns {
    private static final String TAG = "EntryDao";

    private Cursor cursor;
    private SQLiteDatabase database;
    private DBOpenHelper dbOpenHelper;
    private Context context;

    public EntryDao(Context context) {
        this.context = context;
    }

    public List<Entry> selectAll() {

        ArrayList<Entry> arrayList = new ArrayList<Entry>();
        dbOpenHelper = new DBOpenHelper(context, DB_NAME, null, DB_VERSION);
        try {
            database = dbOpenHelper.getWritableDatabase();
        } catch (SQLException e) {
            Log.e(this.toString(), "Error while getting database");
            throw new Error("The end");
        }
        cursor = getAllEntries();

        for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor.moveToNext())
            arrayList.add(new Entry(
                    cursor.getInt(ID_COLUMN),
                    cursor.getString(ENTRY_COLUMN),
                    cursor.getString(LINK_COLUMN),
                    cursor.getInt(ISYOUTUBE_COLUMN)));

        cursor.close();
        return arrayList;
    }

    public void insertEntriesToDB(List<Entry> arrayList) {
        long result;
        if (!database.isOpen()) {
            Log.d(TAG, "DB closed");

        }
        database.delete(TABLE_NAME, null, null);
        for (Entry entry : arrayList) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_ENTRY, entry.getName());
            contentValues.put(KEY_LINK, entry.getUrl());
            if (entry.isYoutube())
                contentValues.put(KEY_ISYOUTUBE, 1);
            else
                contentValues.put(KEY_ISYOUTUBE, 0);
            result = database.insert(TABLE_NAME, null, contentValues);

            if (result == -1) {
                throw new SQLException("Error during inserting entry to DB");
            }
        }

        closeAll();
    }

    public SQLiteDatabase getDatabase() {
        return database;
    }

    public void closeAll() {
        if (database.isOpen())
            dbOpenHelper.close();
        if (!cursor.isClosed())
            cursor.close();
    }

    public Cursor getAllEntries() {
        String[] columnsToTake = {KEY_ID, KEY_ENTRY, KEY_LINK, KEY_ISYOUTUBE};

        return database.query(TABLE_NAME, columnsToTake, null, null, null, null, KEY_ID);
    }

    private class DBOpenHelper extends SQLiteOpenHelper implements Columns {

        public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {

            final String CREATE_DB = "CREATE TABLE " + TABLE_NAME + " ("
                    + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_ENTRY + " TEXT NOT NULL, "
                    + KEY_LINK + " TEXT NOT NULL, "
                    + KEY_ISYOUTUBE + " INTEGER);";
            db.execSQL(CREATE_DB);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }


    }

}