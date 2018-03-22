package com.peoplecoachingworks.leapstudio20.Data;

import android.provider.BaseColumns;

/**
 * Created by Duncan on 21-Mar-18.
 */

public final class GoalTipsContract {

    public final static class GoalTipsEntry implements BaseColumns {

        public static final String TABLE_NAME = "goal_tips";
        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_QUOTE = "quote";
        public final static String COLUMN_AUTHOR = "author";

    }
}
