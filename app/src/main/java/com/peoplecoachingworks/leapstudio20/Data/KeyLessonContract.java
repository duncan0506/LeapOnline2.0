package com.peoplecoachingworks.leapstudio20.Data;

import android.provider.BaseColumns;

/**
 * Created by Duncan on 13-Mar-18.
 */

public class KeyLessonContract {

    public static final class KeyLessonEntry implements BaseColumns {

        public final static String TABLE_NAME = "key_lesson";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_KEY_LESSON = "key_lesson";
        public final static String COLUMN_ACTION_STEP = "action_step";
        public final static String COLUMN_DATETIME = "datetime";
    }
}
