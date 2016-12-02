package com.example.richo_han.tutorialapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Richo_Han on 2016/12/2.
 */

public class ContactReaderDbHelper extends SQLiteOpenHelper{
    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "ContactReader.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + ContactReaderContract.ContactEntry.TABLE_NAME + " (" +
                    ContactReaderContract.ContactEntry._ID + " INTEGER PRIMARY KEY," +
                    ContactReaderContract.ContactEntry.COLUMN_NAME_NAME + TEXT_TYPE + COMMA_SEP +
                    ContactReaderContract.ContactEntry.COLUMN_NAME_PHONE+ TEXT_TYPE + COMMA_SEP +
                    ContactReaderContract.ContactEntry.COLUMN_NAME_GENDER + TEXT_TYPE + COMMA_SEP +
                    ContactReaderContract.ContactEntry.COLUMN_NAME_COMPANY + TEXT_TYPE + COMMA_SEP +
                    ContactReaderContract.ContactEntry.COLUMN_NAME_EMAIL + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + ContactReaderContract.ContactEntry.TABLE_NAME;

    public ContactReaderDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
