package com.ril.cardmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TeamDBHelper extends SQLiteOpenHelper {
    private static String TAG= "TeamMDBHelper";
    private static final int DATABASE_VERSION = 1; 
    private static final String tmdb = "teammember.db";

    public TeamDBHelper(Context context) {
        super(context, tmdb, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        String sql = "CREATE TABLE IF NOT EXISTS team " +
        		"(_id INTEGER PRIMARY KEY AUTOINCREMENT,singleId INTEGER, name TEXT, phonenumber TEXT, updatetime TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("ALTER TABLE card ADD COLUMN other STRING"); 

    }

}