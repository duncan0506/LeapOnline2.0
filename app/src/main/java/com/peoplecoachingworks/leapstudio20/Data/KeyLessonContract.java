package com.peoplecoachingworks.leapstudio20.Data;

import android.provider.BaseColumns;


/**
 * API Contract for the Pets app.
 */
public final class KeyLessonContract {

    private KeyLessonContract() {
    }

    public static final class KeyLessonEntry implements BaseColumns {

        public final static String TABLE_NAME = "key_lesson";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_KEY_LESSON = "key_lesson";
        public final static String COLUMN_ACTION_STEP = "action_step";
        public final static String COLUMN_PRIORITY = "priority";
        public final static String COLUMN_ADD_NOTE = "add_note";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_TIME = "time";


        public static final int PRIORITY_LOW = 0;
        public static final int PRIORITY_MEDIUM = 1;
        public static final int PRIORITY_HIGH = 2;
        public static final int PRIORITY_CRITICAL = 3;
    }

}


