package com.kalinesia.login;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "auth.db"; // DATABASE NAME
    public static final int DATABASE_VERSION = 1; //DATABASE VERSION
    public static final String TABLE_USERS = "users"; //TABLE NAME

    //SQL for creating users table
    public static final String QUERY_TABLE_USERS = " CREATE TABLE " + TABLE_USERS
            + "  ( id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, email TEXT, password TEXT ) ";

    public DataHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(QUERY_TABLE_USERS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }
}
