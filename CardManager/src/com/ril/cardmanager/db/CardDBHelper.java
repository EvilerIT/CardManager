package com.ril.cardmanager.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class CardDBHelper extends SQLiteOpenHelper {
    private static String TAG= "CardDBHelper";
    private static final int DATABASE_VERSION = 1; 
    private static final String carddb = "card.db";

    public CardDBHelper(Context context) {
        super(context, carddb, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "onCreate");
        String sql = "CREATE TABLE IF NOT EXISTS card " +
        		"(_id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT, number TEXT, owner TEXT,team TEXT, operator INTEGER, updatetime TEXT)";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldversion, int newversion) {
        db.execSQL("ALTER TABLE card ADD COLUMN other STRING"); 

    }

}
