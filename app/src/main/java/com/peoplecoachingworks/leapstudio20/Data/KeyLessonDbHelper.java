package com.peoplecoachingworks.leapstudio20.Data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.peoplecoachingworks.leapstudio20.Data.KeyLessonContract.KeyLessonEntry;

public class KeyLessonDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = KeyLessonDbHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "key_lesson.db";
    private static final int DATABASE_VERSION = 1;

    public KeyLessonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Create a String that contains the SQL statement to create the pets table
        String SQL_CREATE_PETS_TABLE = "CREATE TABLE " + KeyLessonEntry.TABLE_NAME + " ("
                + KeyLessonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KeyLessonEntry.COLUMN_KEY_LESSON + " TEXT NOT NULL, "
                + KeyLessonEntry.COLUMN_ACTION_STEP + " TEXT NOT NULL, "
                + KeyLessonEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, "
                + KeyLessonEntry.COLUMN_ADD_NOTE + " TEXT, "
                + KeyLessonEntry.COLUMN_DATE + " TEXT, "
                + KeyLessonEntry.COLUMN_TIME + " TEXT);";

        // Execute the SQL statement
        db.execSQL(SQL_CREATE_PETS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // The database is still at version 1, so there's nothing to do be done here.
    }
}
