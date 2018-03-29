package com.peoplecoachingworks.leapstudio20.Data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Duncan on 13-Mar-18.
 */

public class KeyLessonContract {

    public static final String CONTENT_AUTHORITY = "com.peoplecoachingworks.leapstudio20";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    public static final String PATH_KEY_LESSON = "key_lesson";

    private KeyLessonContract() {
    }

    public static final class KeyLessonEntry implements BaseColumns {

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KEY_LESSON;

        //The MIME type of the {@link #CONTENT_URI} for a single pet.
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_KEY_LESSON;

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KEY_LESSON);

        public final static String TABLE_NAME = "key_lesson";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_KEY_LESSON = "key_lesson";
        public final static String COLUMN_ACTION_STEP = "action_step";
        public final static String COLUMN_DATE = "date";
        public final static String COLUMN_TIME = "time";
        public final static String COLUMN_PRIORITY = "priority";
        public final static String COLUMN_NOTE = "note";

        public static final int PRIORITY_LOW = 0;
        public static final int PRIORITY_MID = 1;
        public static final int PRIORITY_HIGH = 2;
        public static final int PRIORITY_CRITICAL = 3;

        public static boolean isValidPriority(int priority) {
            if (priority == PRIORITY_LOW || priority == PRIORITY_MID || priority == PRIORITY_HIGH || priority == PRIORITY_CRITICAL) {
                return true;
            }
            return false;
        }



    }
}
