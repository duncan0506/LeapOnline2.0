package com.peoplecoachingworks.leapstudio20.Data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.peoplecoachingworks.leapstudio20.Data.GoalTipsContract.GoalTipsEntry;

/**
 * Created by Duncan on 21-Mar-18.
 */

public class GoalTipsDbHelper extends SQLiteOpenHelper {

    //Database File name
    private static final String DATABASE_NAME = "goal_tips.db";

    //Db version
    private static final int DATABASE_VERSION = 1;


    //Constructor
    public GoalTipsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Add database
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create TABLE Goal Tips
        String SQL_GOAL_TIPS_TABLE = "CREATE TABLE " + GoalTipsEntry.TABLE_NAME + "("
                + GoalTipsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + GoalTipsEntry.COLUMN_QUOTE + " TEXT NOT NULL, "
                + GoalTipsEntry.COLUMN_AUTHOR + " TEXT);";

        db.execSQL(SQL_GOAL_TIPS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
