package com.peoplecoachingworks.leapstudio20.Data;

/**
 * Created by Duncan on 29-Mar-18.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.peoplecoachingworks.leapstudio20.Data.KeyLessonContract.KeyLessonEntry;


public class KeyLessonDbHelper extends SQLiteOpenHelper {

    //Database File name
    private static final String DATABASE_NAME = "key_lesson.db";

    //Db version
    private static final int DATABASE_VERSION = 1;

    //Constructor
    public KeyLessonDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Add database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create TABLE Goal Tips
        String SQL_KEY_LESSON_TABLE = "CREATE TABLE " + KeyLessonEntry.TABLE_NAME + "("
                + KeyLessonEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KeyLessonEntry.COLUMN_KEY_LESSON + " TEXT NOT NULL, "
                + KeyLessonEntry.COLUMN_ACTION_STEP + " TEXT NOT NULL, "
                + KeyLessonEntry.COLUMN_DATE + " TEXT NOT NULL, "
                + KeyLessonEntry.COLUMN_TIME + " TEXT NOT NULL, "
                + KeyLessonEntry.COLUMN_PRIORITY + " INTEGER NOT NULL, "
                + KeyLessonEntry.COLUMN_NOTE + " TEXT);";

        db.execSQL(SQL_KEY_LESSON_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}

