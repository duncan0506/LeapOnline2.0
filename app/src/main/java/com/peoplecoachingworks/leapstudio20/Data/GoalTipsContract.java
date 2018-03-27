package com.peoplecoachingworks.leapstudio20.Data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Duncan on 21-Mar-18.
 */

public final class GoalTipsContract {

    //Name for entire content provider, like domain name to website
    public static final String CONTENT_AUTHORITY = "com.peoplecoachingworks.leapstudio20";

    //Use Content Authority as a Base of all URI which apps will use to contact content provider
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_GOAL_TIPS = "goal_tips";

    public final static class GoalTipsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_GOAL_TIPS);

        public static final String TABLE_NAME = "goal_tips";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_QUOTE = "quote";
        public final static String COLUMN_AUTHOR = "author";

    }
}
